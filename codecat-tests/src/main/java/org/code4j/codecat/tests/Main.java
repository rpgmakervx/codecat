package org.code4j.codecat.tests;/**
 * Description : 
 * Created by YangZH on 16-6-20
 *  上午2:15
 */

import org.code4j.codecat.realserver.util.JarHelper;

import java.io.IOException;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-20
 * 上午2:15
 */

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        String[] segement = "/home/code4j/osproject/codecat/bin/../apps/handler.jar".split(File.separator);
//        String filename = segement[segement.length-1].substring(0, segement[segement.length - 1].lastIndexOf(Const.DOT));
//        System.out.println(filename);
//        URL[] urls = new URL[]{new URL("file:"+"/home/code4j/osproject/codecat/bin/../apps/handler.jar")};
////            byte[] bytes = getBytes(app_path+plugin_name);
////            defineClass
//        // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
//        URLClassLoader classLoader = new URLClassLoader(urls);
//        Class<? extends BasicHttpHandler> clazz =
//                (Class<? extends BasicHttpHandler>) classLoader.loadClass("org.code4j.test.TimerHandler");
//        System.out.println(clazz.newInstance());
        List<String> classnames = JarHelper.getClassFileName("/home/code4j/osproject/codecat/bin/../apps/handler.jar");
        String[] clazznames = new String[classnames.size()];
        for (String name:classnames){
            System.out.println(name);
        }
    }
}
