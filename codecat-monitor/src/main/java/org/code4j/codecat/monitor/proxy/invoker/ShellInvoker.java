package org.code4j.codecat.monitor.proxy.invoker;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午7:46
 */

import java.io.IOException;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午7:46
 */

public class ShellInvoker {
    public static final String STARTUP_SERVER = System.getProperty("user.dir")+"/bootstrap.sh";

    public static void execute(String cmd,String param){
        try {
            Runtime.getRuntime().exec(cmd+" "+param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
