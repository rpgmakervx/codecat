package org.code4j.codecat.commons.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by code4j on 16-6-17
 * 下午11:44
 *
 * key-value pair to store uri-port
 * user uri's root to be key,and port to be value
 */

public class PathPortPair {

    private static Map<String,Integer> pathport = new HashMap<>();
    private static Map<Integer,String> portpath = new HashMap<>();

    public static void storePair(String path,Integer port){
        path = handlePath(path);
        pathport.put(path,port);
        portpath.put(port,path);
    }

    public static Integer getPort(String path){
        System.out.println("getPort() path is"+path+"| "+path.isEmpty()+" | "+path == File.separator );
        path = handlePath(path);
        return pathport.get(path);
    }

    public static String getPath(int port){
        return portpath.get(port);
    }

    public static boolean hasPath(String path){
        System.out.println("hasPath() path is"+path+"| "+path.isEmpty()+" | "+path == File.separator );
        path = handlePath(path);
        return pathport.containsKey(path)
                &&portpath.containsValue(path);
    }

    public static boolean hasPort(Integer port){
        return pathport.containsValue(port)&&portpath.containsKey(port);
    }

    private static String handlePath(String path){
        System.out.println("handlePath() path is"+path+"|");
        path = path.trim();
        if (!path.contains(File.separator)){
            return path.split(File.separator)[0];
        }else if (File.separator.equals(path) || path.isEmpty()){
            return File.separator;
        }else{
            return path.split(File.separator)[0];
        }
    }
}
