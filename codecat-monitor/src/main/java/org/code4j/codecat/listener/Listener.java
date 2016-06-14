package org.code4j.codecat.listener;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午2:15
 */

import net.contentobjects.jnotify.JNotifyAdapter;
import org.apache.log4j.Logger;
import org.code4j.codecat.monitor.invoker.ShellInvoker;

/**
 * Description :
 * Created by YangZH on 16-6-13
 * 下午2:15
 */

public class Listener extends JNotifyAdapter {
    private Logger logger = Logger.getLogger(Listener.class);

    @Override
    public void fileCreated(int i, String s, String s1) {
        logger.info("创建了一个文件" + s + " | " + s1);
        ShellInvoker.execute(ShellInvoker.STARTUP_SERVER,String.valueOf(PortCounter.incr()));
        logger.info("opened a new server, port is : "+PortCounter.getPort());
    }

    @Override
    public void fileModified(int i, String s, String s1) {
        logger.info("修改了一个文件" + s + " | " + s1 + " | " + s1);
    }

    @Override
    public void fileRenamed(int i, String s, String s1, String s2) {
        logger.info("重命名了一个文件 " + s + " | " + s1 + " | " + s2);
    }

    @Override
    public void fileDeleted(int wd, String rootPath, String name) {
        logger.info("删除了一个文件 " + rootPath + name);
    }
}
