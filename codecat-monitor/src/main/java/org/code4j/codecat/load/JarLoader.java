package org.code4j.codecat.load;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午7:52
 */

import io.netty.channel.ChannelInboundHandlerAdapter;
import org.code4j.codecat.constants.Const;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Description :
 * Created by YangZH on 16-6-13
 * 下午7:52
 */

public class JarLoader {

    public String app_path;

    public JarLoader(String app_path) {
        this.app_path = Const.APP+app_path;
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

    private void loadConfiguration(){
        File configFile = new File(this.app_path);

    }

    public static void addNewFunction(){
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
//            Class clazz = classLoader.loadClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
