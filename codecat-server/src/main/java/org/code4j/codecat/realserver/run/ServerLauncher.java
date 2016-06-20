package org.code4j.codecat.realserver.run;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午1:57
 */

import org.apache.log4j.Logger;
import org.code4j.codecat.commons.realserver.IRealServer;
import org.code4j.codecat.realserver.dynamicproxy.factory.ProxyFactory;
import org.code4j.codecat.realserver.load.JarLoader;
import org.code4j.codecat.realserver.server.RealServer;
import org.code4j.codecat.realserver.util.JarHelper;

import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午1:57
 */

class ServerLauncher {

    static Logger logger = Logger.getLogger(ServerLauncher.class);
    public static void main(String[] args) {
        if (args.length == 1){
            new RealServer().startup(Integer.valueOf(args[0]));
        }else{
            ProxyFactory factory = new ProxyFactory();
            RealServer server = new RealServer();
            List<String> classnames = JarHelper.getClassFileName(args[1]);
            IRealServer delegateServer = factory.getProxy(server
                    , new JarLoader(args[1]), classnames);
            delegateServer.initHandler();
            delegateServer.setup();
            delegateServer.launch(Integer.valueOf(args[0]));
        }
    }
}
