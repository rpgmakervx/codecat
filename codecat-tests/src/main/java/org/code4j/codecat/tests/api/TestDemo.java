package org.code4j.codecat.tests.api;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  下午9:52
 */

import org.code4j.codecat.tests.monitor.pojo.IUser;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 下午9:52
 */

public class TestDemo {

    @Test
    public void testClassEquals(){
        Class clazz = IUser.class;
        System.out.println(IUser.class.isAssignableFrom(clazz)&&IUser.class != clazz);
    }
    @Test
    public void testSeperator(){
//        System.out.println(getValue());
        String path = "lll/root";
        System.out.println(path.split(File.separator)[1]);
    }

    public Integer getValue(){
        Map<String,Integer> map = new HashMap<>();
        map.put("xingtianyu",100);
        return map.get("xx");
    }
}
