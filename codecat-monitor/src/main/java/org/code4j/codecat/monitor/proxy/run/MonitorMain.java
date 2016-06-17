package org.code4j.codecat.monitor.proxy.run;/**
 * Description : 
 * Created by YangZH on 16-5-25
 *  下午2:28
 */


import org.code4j.codecat.monitor.listener.JarWatcher;
import org.code4j.codecat.monitor.proxy.server.MonitorServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description :
 * Created by YangZH on 16-5-25
 * 下午2:28
 */

public class MonitorMain {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        final int port = Integer.valueOf(args[0]);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                JarWatcher watcher = new JarWatcher();
                watcher.watchJarFolder();
            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                new MonitorServer().startup(port);
            }
        });
    }

}
