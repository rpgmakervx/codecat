package org.code4j.codecat.monitor.dynamicproxy.handler;/**
 * Description : 
 * Created by YangZH on 16-6-16
 *  下午9:50
 */

import org.apache.log4j.Logger;
import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.monitor.load.JarLoader;
import org.code4j.codecat.commons.realserver.IRealServer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 下午9:50
 */

public class RealServerProxyHandler implements InvocationHandler {
    private Logger logger = Logger.getLogger(RealServerProxyHandler.class);
    private IRealServer delegatedServer;

    private JarLoader loader;

    private List<String> pluginNames;

    public RealServerProxyHandler(IRealServer delegatedServer, JarLoader loader,List<String> pluginNames){
        this.delegatedServer = delegatedServer;
        this.loader = loader;
        this.pluginNames = pluginNames;
    }
    public RealServerProxyHandler(IRealServer delegatedServer, JarLoader loader,String ... pluginNames){
        this.delegatedServer = delegatedServer;
        this.loader = loader;
        this.pluginNames = Arrays.asList(pluginNames);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals(Const.DELEGATEMATHOD_SETUP)){
            for (String pluginName:this.pluginNames){
                delegatedServer.addHandler(loader.loadJar(pluginName).getClass());
                System.out.println("invoke loadBasicService : "+pluginName);
            }
            result = method.invoke(delegatedServer,args);
        }else{
            result = method.invoke(delegatedServer,args);
        }
        return result;
    }
}
