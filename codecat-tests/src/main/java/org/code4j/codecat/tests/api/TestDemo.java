package org.code4j.codecat.tests.api;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  下午9:52
 */

import org.code4j.codecat.tests.monitor.pojo.IUser;
import org.junit.Test;

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
}
