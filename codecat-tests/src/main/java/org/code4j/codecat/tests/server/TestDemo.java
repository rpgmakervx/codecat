package org.code4j.codecat.tests.server;/**
 * Description : 
 * Created by YangZH on 16-6-16
 *  下午8:57
 */

import org.code4j.codecat.realserver.server.RealServer;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 下午8:57
 */

public class TestDemo {

    public void testServerStart(){
        new RealServer().startup(Integer.valueOf(9000));
    }
}
