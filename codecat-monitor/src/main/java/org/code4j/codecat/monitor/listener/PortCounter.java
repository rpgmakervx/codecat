package org.code4j.codecat.monitor.listener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午10:30
 */

public class PortCounter {
    private static AtomicInteger port_incr = new AtomicInteger(20000);
    private PortCounter(){}
    public static int incr(){
        return port_incr.incrementAndGet();
    }

    public static int getPort(){
        return port_incr.get();
    }
}
