package org.code4j.codecat.monitor.load;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午7:52
 */


import org.code4j.codecat.api.service.BasicHttpHandler;

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
    public JarLoader(String app_path,String plugin_name) {
        this.pluginPath = app_path+File.separator+plugin_name;
    }

    public JarLoader(String pluginPath){
        this.pluginPath = pluginPath;
    }

    public BasicHttpHandler loadBasicService(String classname){
        try {
            // 创建一个URL数组
            URL[] urls = new URL[]{new URL("file:"+this.pluginPath)};
//            byte[] bytes = getBytes(app_path+plugin_name);
//            defineClass
            // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<? extends BasicHttpHandler> clazz =
                    (Class<? extends BasicHttpHandler>) classLoader.loadClass(classname);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
