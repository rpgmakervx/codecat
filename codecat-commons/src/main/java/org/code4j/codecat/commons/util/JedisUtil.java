package org.code4j.codecat.commons.util;/**
 * Description : 
 * Created by YangZH on 16-6-4
 *  上午10:50
 */

import org.code4j.codecat.commons.constants.Const;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-4
 * 上午10:50
 */

public class JedisUtil {

    public static Jedis jedisClient = new Jedis(Const.LOCALHOST);
    static {
        System.out.println("Server is running: "+jedisClient.ping());
    }
    public static String get(String key){
        return jedisClient.hget("codecat",key);
    }

    public static void set(String key,String value){
        jedisClient.hset("codecat",key,value);
        jedisClient.set(key, value);
    }

    public static void remove(String key){
        jedisClient.hdel("codecat",key);
        jedisClient.del(key);
    }

    public static void showPairs(){
        System.out.println("------pairs--------");
        for (String key:jedisClient.hkeys("codecat")){
            System.out.println("key -->"+key+",value -->"+get(key));
        }
        System.out.println("------pairs-end-------");
    }

    public static boolean hasKey(String key){
        return jedisClient.hexists("codecat",key);
    }

    public static void setList(String listname,List<String> list){
        for (String item:list){
            jedisClient.lpush(listname,item);
        }
    }

    public static List<String> getList(String listname){
        return jedisClient.lrange(listname,0,jedisClient.strlen(listname));
    }

}
