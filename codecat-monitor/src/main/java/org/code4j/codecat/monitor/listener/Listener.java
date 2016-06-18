package org.code4j.codecat.monitor.listener;

import net.contentobjects.jnotify.JNotifyAdapter;
import org.apache.log4j.Logger;
import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.monitor.util.JarHelper;
import org.code4j.codecat.commons.util.PropertyHelper;
import org.code4j.codecat.monitor.dynamicproxy.factory.ProxyFactory;
import org.code4j.codecat.monitor.load.JarLoader;
import org.code4j.codecat.monitor.service.ReadConfigService;
import org.code4j.codecat.realserver.server.IRealServer;
import org.code4j.codecat.realserver.server.RealServer;

import java.io.File;
import java.util.List;
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
    }

    @Override
    public void fileRenamed(int i, String s, String s1, String s2) {
        logger.info("重命名了一个文件 " + s + " | " + s1 + " | " + s2);
    }

    @Override
    public void fileDeleted(int wd, String rootPath, String name) {
        logger.info("删除了一个文件 " + rootPath + name);
    }

    private void handleNewFile(String path,String filename){
        File file = new File(path+filename);
        try{
            if (file.isFile()){
                Matcher matcher = pattern.matcher(file.getName());
                //插件类型的文件
                if (matcher.matches()){
                    List<String> classnames = JarHelper.getClassFileName(path+filename);
                    String[] clazznames = new String[classnames.size()];
                    pool.submit(new DelegateServerTask(path, filename, classnames.toArray(clazznames)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    @Deprecated
    private void dealNewFile(String path,String filename){
        //get current path to find real plugin's location
        //有可能你的插件是部署在app下面的某个文件夹下，
        //这样的话app.xml就要基于当前的路径寻找
        String configPath = path+Const.APPXML;
        logger.info("configPath : "+configPath);
        String propPath = path+File.separator+Const.PROPERTIESFILE;
        logger.info("propPath : "+propPath);
        //当前新增的文件
        File file = new File(path+File.separator+filename);
        //manifest文件元数据
        File propFile = new File(propPath);
        try{
            if (file.isFile()){
                PropertyHelper helper = new PropertyHelper(propFile);
                boolean hasConfig;
                boolean hasPlugin;
                Matcher matcher = pattern.matcher(file.getName());
                //插件类型的文件
                if (matcher.matches()){
                    helper.updateProperties(Const.PLUGINEXISTS, String.valueOf(Boolean.TRUE));
                    helper.updateProperties(Const.PLUGINNAME, filename);
                    logger.info("has config ? "+helper.getValue(Const.CONFIGEXISTS));
                    logger.info("has plugin ? "+helper.getValue(Const.PLUGINEXISTS));
                    hasConfig = Boolean.valueOf(helper.getValue(Const.CONFIGEXISTS));
                    if (hasConfig){
                        ReadConfigService service = new ReadConfigService(configPath);
                        logger.info("plugin's name : " + service.getFunctionClassName());
                        pool.submit(new DelegateServerTask(path, filename, service.getFunctionClassName()));
                    }else{
                        logger.info("config not exists");
                    }
                }else if (file.getName().endsWith(".xml")){
                    helper.updateProperties(Const.CONFIGEXISTS, String.valueOf(Boolean.TRUE));
                    logger.info("has config ? "+helper.getValue(Const.CONFIGEXISTS));
                    logger.info("has plugin ? "+helper.getValue(Const.PLUGINEXISTS));
                    hasPlugin = Boolean.valueOf(helper.getValue(Const.PLUGINEXISTS));
                    if (hasPlugin){
                        ReadConfigService service = new ReadConfigService(configPath);
                        logger.info("plugin's name : " + service.getFunctionClassName());
                        pool.submit(new DelegateServerTask(path, helper.getValue(Const.PLUGINNAME), service.getFunctionClassName()));
                    }else{
                        logger.info("plugin not exists");
                    }
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
        delegateServer.launch(PortCounter.getPort());
    }

    /**
     * 代理realserver的启动，启动前添加插件类
     * @param pluginpath
     * @param pluginName
     */
    private void delegateServerStartup(String pluginpath,String pluginName){
        ProxyFactory factory = new ProxyFactory();
        RealServer server = new RealServer();
        IRealServer delegateServer = factory.getProxy(server
                ,new JarLoader(pluginpath),pluginName);
        delegateServer.initHandler();
        delegateServer.setup();
        delegateServer.launch(PortCounter.incr());
    }

    class DelegateServerTask implements Runnable{

        private String path;
        private String pluginName;

        private String[] className;

        public DelegateServerTask(String path,String pluginName,String ... className){
            this.path = path;
            this.pluginName = pluginName;
            this.className = className;
        }

        @Override
        public void run() {
            delegateServerStartup(path,pluginName,className);
        }
    }
}
