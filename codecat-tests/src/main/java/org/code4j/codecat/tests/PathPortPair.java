package org.code4j.codecat.tests;

import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.commons.realserver.IRealServer;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description :
 * Created by code4j on 16-6-17
 * 下午11:44
 *
 * key-value pair to store uri-port
 * user uri's root to be key,and port to be value
 * rpgmakervx
 */
@Deprecated
public class PathPortPair {

    private static Map<Integer,String> portpath = Collections.synchronizedMap(new HashMap<Integer,String>());
    private static Map<String,Integer> pathport = Collections.synchronizedMap(new HashMap<String,Integer>());
    @Deprecated
    private static Map<String,Integer> terminated = Collections.synchronizedMap(new HashMap<String,Integer>());

    private static Map<String,IRealServer> pathserver = Collections.synchronizedMap(new HashMap<String,IRealServer>());

    public static void storePair(String path,Integer port){
        path = handlePath(path);
        pathport.put(path,port);
        portpath.put(port,path);
        System.out.println("pair stored!! ");
    }

    public static void storeServer(String path,IRealServer server){
        path = path.substring(0, path.lastIndexOf(Const.DOT));
        pathserver.put(path,server);
    }

    public static IRealServer getServer(String path){
        return pathserver.get(path);
    }

    public static void showServer(){
        System.out.println("-----------path server pairs-----------");
        for (Map.Entry<String,IRealServer> entry : pathserver.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        System.out.println("-----------path server pairs-----------");
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
        path = handlePath(path);
        System.out.println("handled path --> "+path + "  "+(pathport.containsKey(path)
                &&portpath.containsValue(path)));
        showPairs();
        return pathport.containsKey(path)
                &&portpath.containsValue(path);
    }


    public static boolean hasPort(Integer port){
        return pathport.containsValue(port)&&portpath.containsKey(port);
    }

    public static void removePair(String path){
        pathport.remove(handlePath(path));
        portpath.remove(getPort(path));
    }

    public static void removePair(Integer port){
        pathport.remove(getPath(port));
        portpath.remove(port);
    }

    public static void showPairs(){
        System.out.println("-----------path port pairs-----------");
        for (Map.Entry<String,Integer> entry : pathport.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        System.out.println("-----------path port pairs-----------");
    }

    @Deprecated
    public static void terminatedQueue(String path,Integer port){
        terminated.put(path,port);
    }
    @Deprecated
    public static Set<Map.Entry<String,Integer>> getTerminatedPair(){
        return terminated.entrySet();
    }

    public static void showTerminatedQueue(){
        System.out.println("------被删除队列-----");
        for (Map.Entry<String,Integer> entry : getTerminatedPair()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        System.out.println("------被删除队列-----");
    }

    private static String handlePath(String path){
        System.out.println("handlePath() path is"+path+"|");
        path = path.trim();
        if (!path.contains(File.separator)){
            return path.split(File.separator)[0];
        }else if (File.separator.equals(path) || path.isEmpty()){
            return File.separator;
        }else{
            return path.split(File.separator)[1];
        }
    }
}
