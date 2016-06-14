package org.code4j.codecat.load;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午7:52
 */

import io.netty.channel.ChannelInboundHandlerAdapter;
import net.contentobjects.jnotify.JNotify;
import org.code4j.codecat.listener.Listener;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Description :
 * Created by YangZH on 16-6-13
 * 下午7:52
 */

public class JarLoader {

    public static String jar_path = System.getProperty("user.home")+"/osproject/handler.jar";

    public void watchJarFolder(){

    }

    public static ChannelInboundHandlerAdapter loadNewClass(String path){
        try {
            // 创建一个URL数组
            URL[] urls = {new URL("file:"+path)};
            // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
            URLClassLoader classLoader = new URLClassLoader(urls);

            // 加载MySQL的JDBC驱动，并创建默认实例
            Class clazz = classLoader.loadClass("org.code4j.test.MyHandler");
            return null;
            // 创建一个设置JDBC连接属性的Properties对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addNewFunction(){
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
//            Class clazz = classLoader.loadClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fileWatch(String monitedPath){
        int mask = JNotify.FILE_CREATED |
                    JNotify.FILE_DELETED |
                    JNotify.FILE_MODIFIED |
                    JNotify.FILE_RENAMED;
        // 是否监视子目录
        boolean watchSubtree = true;
        try {
            int watchID = JNotify.addWatch(monitedPath, mask, watchSubtree, new Listener());
            Thread.sleep(1000000);
            boolean res = JNotify.removeWatch(watchID);
            if (!res) {
                // invalid
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
