package org.code4j.codecat.tests.monitor.dyproxy;/**
 * Description : 
 * Created by YangZH on 16-6-16
 *  下午11:02
 */

import org.code4j.codecat.tests.monitor.pojo.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 下午11:02
 */

public class Handler implements InvocationHandler {

    private User object ;

    public Handler(User object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals("sayHello")){
            System.out.println("代理前加点方法");
            result = method.invoke(object,args);
            System.out.println("代理后加点方法");
        }else{
            result = method.invoke(object,args);
        }
        return result;
    }

}
