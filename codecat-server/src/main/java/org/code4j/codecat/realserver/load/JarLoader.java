package org.code4j.codecat.realserver.load;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午7:52
 */


import org.code4j.codecat.api.service.BasicHttpHandler;
import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.commons.invoker.ShellInvoker;
import org.code4j.codecat.commons.util.JedisUtil;

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
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<? extends BasicHttpHandler> clazz =
                    (Class<? extends BasicHttpHandler>) classLoader.loadClass(classname);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void unloadJar(){
        System.out.println("当前要kill掉的项目 ： "+JedisUtil.get(this.filename));
        if (JedisUtil.hasKey(this.filename)){
            ShellInvoker.execute(ShellInvoker.KILL_SERVER,
                    JedisUtil.get(this.filename));
            JedisUtil.remove(this.filename);
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
