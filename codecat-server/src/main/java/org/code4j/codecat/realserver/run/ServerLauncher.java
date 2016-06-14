package org.code4j.codecat.realserver.run;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午1:57
 */

import org.code4j.codecat.realserver.server.RealServer;
/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午1:57
 */

class ServerLauncher {

    public static void main(String[] args) {
        new RealServer().startup(Integer.valueOf(args[0]));
    }
}
