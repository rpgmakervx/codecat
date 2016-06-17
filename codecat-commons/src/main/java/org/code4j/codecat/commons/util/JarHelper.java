package org.code4j.codecat.commons.util;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  下午9:00
 */

import org.code4j.codecat.api.service.BasicHttpHandler;
import org.code4j.codecat.commons.constants.Const;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 下午9:00
 */

public class JarHelper {

    private static Pattern pattern = Pattern.compile(Const.CLASS_PATTERN);

    public static List<String> getClassFileName(String jarFilePath){
        Matcher matcher = null;
        List<String> classnames = new ArrayList<>();
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String filename = jarEntry.getName();
                if (matcher == null){
                    matcher = pattern.matcher(filename);
                }else{
                    matcher.reset(filename);
                }
                if (matcher.matches()){
                    filename = filename.substring(0, filename.lastIndexOf(Const.DOT)).replace(File.separator,Const.DOT);
                    URL[] urls = new URL[]{new URL("file:"+jarFilePath)};
                    // 以默认的ClassLoader作为父ClassLoader，创建URLClassLoader
                    URLClassLoader classLoader = new URLClassLoader(urls);
                    System.out.println("classname = "+filename);
                    Class clazz = classLoader.loadClass(filename);
                    //获得子类
                    boolean isSubClass = BasicHttpHandler.class.isAssignableFrom(clazz)
                                        &&BasicHttpHandler.class!=clazz;
                    if (isSubClass){
                        classnames.add(filename);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classnames;
    }
}
