package org.code4j.codecat.monitor.dynamicproxy.factory;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  上午12:12
 */

import org.code4j.codecat.monitor.dynamicproxy.handler.RealServerProxyHandler;
import org.code4j.codecat.monitor.load.JarLoader;
import org.code4j.codecat.realserver.server.IRealServer;

import java.lang.reflect.Proxy;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午12:12
 */

public class ProxyFactory {


    public IRealServer getProxy(IRealServer server,JarLoader loader,String pluginName){
        RealServerProxyHandler handler =
                new RealServerProxyHandler(server,loader,pluginName);
        return (IRealServer) Proxy.newProxyInstance(server.getClass().getClassLoader()
                , server.getClass().getInterfaces(), handler);
    }
}
