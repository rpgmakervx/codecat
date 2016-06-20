package org.code4j.codecat.realserver.dynamicproxy.factory;/**
 * Description : 
 * Created by YangZH on 16-6-17
 *  上午12:12
 */

import org.code4j.codecat.realserver.load.JarLoader;
import org.code4j.codecat.realserver.dynamicproxy.handler.RealServerProxyHandler;
import org.code4j.codecat.commons.realserver.IRealServer;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-17
 * 上午12:12
 */

public class ProxyFactory {


    public IRealServer getProxy(IRealServer server,JarLoader loader,String ... pluginName){
        RealServerProxyHandler handler =
                new RealServerProxyHandler(server,loader,pluginName);
        return (IRealServer) Proxy.newProxyInstance(server.getClass().getClassLoader()
                , server.getClass().getInterfaces(), handler);
    }
    public IRealServer getProxy(IRealServer server,JarLoader loader,List<String> pluginName){
        RealServerProxyHandler handler =
                new RealServerProxyHandler(server,loader,pluginName);
        return (IRealServer) Proxy.newProxyInstance(server.getClass().getClassLoader()
                , server.getClass().getInterfaces(), handler);
    }
}
