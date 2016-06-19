package org.code4j.codecat.monitor.load;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午7:52
 */


import org.code4j.codecat.api.service.BasicHttpHandler;
import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.commons.util.PathPortPair;
import org.code4j.codecat.monitor.listener.PortCounter;
import org.code4j.codecat.monitor.proxy.invoker.ShellInvoker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Description :
 * Created by YangZH on 16-6-13
 * 下午7:52
 */

public class JarLoader {


    public String pluginPath;

    public String filename;

    public boolean xmlLoaded = false;

    public JarLoader(String app_path,String plugin_name) {
        this.filename = plugin_name.substring(0, plugin_name.lastIndexOf(Const.DOT));
        this.pluginPath = app_path+File.separator+plugin_name;
    }

    public JarLoader(String pluginPath){
        String[] segement = pluginPath.split(File.separator);
        this.filename = segement[segement.length-1].substring(0, segement[segement.length - 1].lastIndexOf(Const.DOT));
        this.pluginPath = pluginPath;
    }

    public BasicHttpHandler loadJar(String classname){
        try {
            // 创建一个URL数组
            URL[] urls = new URL[]{new URL("file:"+this.pluginPath)};
//            byte[] bytes = getBytes(app_path+plugin_name);
//            defineClass
            // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<? extends BasicHttpHandler> clazz =
                    (Class<? extends BasicHttpHandler>) classLoader.loadClass(classname);
            if (!xmlLoaded){
//                XmlUtil util = new XmlUtil(JarHelper.readConfig(this.pluginPath,Const.APPXML));
//                String path = util.getTextByTagName(Const.ROOT_PATH);
                if (PathPortPair.hasPath(this.filename)){
                    return null;
                }
                PathPortPair.storePair(this.filename, PortCounter.incr());
                System.out.println("load path --> "+this.filename);
                xmlLoaded = true;
                PathPortPair.showPairs();
            }
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Deprecated
    public void recordTerminatedPair(){
//        XmlUtil util = new XmlUtil(JarHelper.readConfig(this.pluginPath, Const.APPXML));
//        String path = util.getTextByTagName(Const.ROOT_PATH);
        PathPortPair.terminatedQueue(pluginPath,PathPortPair.getPort(this.filename));
        PathPortPair.showTerminatedQueue();
    }


    public void unloadJar(){
//        ShellInvoker.execute(ShellInvoker.KILL_SERVER,
//                String.valueOf(PathPortPair.getPort(this.filename)));
        PathPortPair.showPairs();
        PathPortPair.getServer(this.filename).close();
        PathPortPair.removePair(this.filename);
//        Set<Map.Entry<String,Integer>> entrys =
//                PathPortPair.getTerminatedPair();
//        for (Map.Entry<String,Integer> entry : entrys){
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//            if (entry.getKey().equals(this.filename)){
//                ShellInvoker.execute(ShellInvoker.KILL_SERVER,
//                        String.valueOf(entry.getValue()));
//                PathPortPair.removePair(PathPortPair.getPath(entry.getValue()));
//            }
//        }
    }

    public void uninstallJar(){
//        XmlUtil util = new XmlUtil(JarHelper.readConfig(this.pluginPath,Const.APPXML));
//        String path = util.getTextByTagName(Const.ROOT_PATH);
        if (PathPortPair.hasPath(this.filename)){
            ShellInvoker.execute(ShellInvoker.KILL_SERVER,
                    String.valueOf(PathPortPair.getPort(this.filename)));
            PathPortPair.removePair(this.filename);
        }
    }
    
    // 读取一个文件的内容
    private byte[] getBytes(String filename)
            throws IOException{
        File file = new File(filename);
        long len = file.length();
        byte[] raw = new byte[(int)len];
        try(
                FileInputStream fin = new FileInputStream(file))
        {
            // 一次读取class文件的全部二进制数据
            int r = fin.read(raw);
            if(r != len)
                throw new IOException("无法读取全部文件："
                        + r + " != " + len);
            return raw;
        }
    }

}
