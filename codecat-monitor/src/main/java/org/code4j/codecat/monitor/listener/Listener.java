package org.code4j.codecat.monitor.listener;

import net.contentobjects.jnotify.JNotifyAdapter;
import org.apache.log4j.Logger;
import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.commons.invoker.ShellInvoker;
import org.code4j.codecat.commons.realserver.IRealServer;
import org.code4j.codecat.commons.util.JedisUtil;
import org.code4j.codecat.commons.util.PortCounter;
import org.code4j.codecat.realserver.dynamicproxy.factory.ProxyFactory;
import org.code4j.codecat.realserver.load.JarLoader;
import org.code4j.codecat.realserver.server.RealServer;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description :
 * Created by YangZH on 16-6-13
 * 下午2:15
 */

public class Listener extends JNotifyAdapter {
    private Logger logger = Logger.getLogger(Listener.class);

    private Pattern pattern = Pattern.compile(Const.PLUGIN_PATTERN);

    ExecutorService pool = Executors.newCachedThreadPool();

    @Override
    public void fileCreated(int i, String s, String s1) {
        logger.info("创建了一个文件" + s + " | " + s1);
        handleNewFile(s + File.separator, s1);
    }

    @Override
    public void fileModified(int i, String s, String s1) {
        logger.info("修改了一个文件" + s + " | " + s1 + " | " + s1);
        //方案1，直接在发现修改jar的时候移除
//        handleUninstall(s, s1);
        //方案2，先记录要删除的jar，再删除完成被监听到后，从codecat中移除
//        new JarLoader(s,s1).recordTerminatedPair();
    }

    @Override
    public void fileRenamed(int i, String s, String s1, String s2) {
        logger.info("重命名了一个文件 " + s + " | " + s1 + " | " + s2);
    }

    @Override
    public void fileDeleted(int wd, String rootPath, String name) {
        logger.info("删除了一个文件 " + rootPath + name);
        //方案2，先记录要删除的jar，再删除完成被监听到后，从codecat中移除
        handleUninstall(rootPath,name);
    }

    private void handleUninstall(String path,String filename){
        File file = new File(path+File.separator+filename);
        System.out.println("is a file? "+file.getAbsolutePath());
        Matcher matcher = pattern.matcher(file.getName());
        System.out.println("is a jar file? "+matcher.matches());
        JedisUtil.showPairs();
//        PathPortPair.showPairs();
        if (matcher.matches()){
            new JarLoader(path,filename).unloadJar();
        }
    }

    private void handleNewFile(final String path, final String filename){
        File file = new File(path+filename);
        while (!file.exists()){
            System.out.println("文件还没准备好");
        }
        try{
            if (file.isFile()){
                Matcher matcher = pattern.matcher(file.getName());
                //插件类型的文件
                if (matcher.matches()){
                    String prefix = filename.substring(0, filename.lastIndexOf(Const.DOT));
                    System.out.println("load path --> "+prefix);
                    JedisUtil.set(prefix, String.valueOf(PortCounter.incr()));
//                    PathPortPair.storePair(prefix, PortCounter.incr());
                    JedisUtil.showPairs();
//                    PathPortPair.showPairs();
                    pool.submit(new Runnable() {
                        @Override
                        public void run() {
                            ShellInvoker.execute(ShellInvoker.STARTUP_SERVER, String.valueOf(PortCounter.getPort()) + " " + path + filename);
                        }
                    });
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }


    /**
     * 代理realserver的启动，启动前添加插件类
     * @param app_path
     * @param plugin_name
     * @param pluginName
     */
    private void delegateServerStartup(String app_path,String plugin_name,String ... pluginName){
        ProxyFactory factory = new ProxyFactory();
        RealServer server = new RealServer();
        IRealServer delegateServer = factory.getProxy(server
                ,new JarLoader(app_path,plugin_name),pluginName);
        delegateServer.initHandler();
        delegateServer.setup();
//        PathPortPair.storeServer(plugin_name, delegateServer);
//        PathPortPair.showServer();
        delegateServer.launch(PortCounter.getPort());
    }

    /**
     * 代理realserver的启动，启动前添加插件类
     * @param pluginpath
     * @param pluginName
     */
//    private void delegateServerStartup(String pluginpath,String pluginName){
//        ProxyFactory factory = new ProxyFactory();
//        RealServer server = new RealServer();
//        IRealServer delegateServer = factory.getProxy(server
//                ,new JarLoader(pluginpath),pluginName);
//        delegateServer.initHandler();
//        delegateServer.setup();
//        delegateServer.launch(PortCounter.incr());
//    }

//    class DelegateServerTask implements Runnable{
//
//        private String path;
//        private String pluginName;
//
//        private String[] className;
//
//        public DelegateServerTask(String path,String pluginName,String ... className){
//            this.path = path;
//            this.pluginName = pluginName;
//            this.className = className;
//        }
//
//        @Override
//        public void run() {
//            delegateServerStartup(path,pluginName,className);
//        }
//    }
}
