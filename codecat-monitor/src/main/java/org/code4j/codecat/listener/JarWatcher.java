package org.code4j.codecat.listener;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午10:54
 */

import net.contentobjects.jnotify.JNotify;
import org.code4j.codecat.constants.Const;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午10:54
 */

public class JarWatcher {

    private static final int mask = JNotify.FILE_CREATED |
                                    JNotify.FILE_DELETED |
                                    JNotify.FILE_MODIFIED |
                                    JNotify.FILE_RENAMED;
    private boolean watchSubtree = true;

    public synchronized void watchJarFolder(){
        try {
            int watchID = JNotify.addWatch(Const.CURR_PATH+Const.UP_LEVEL+Const.APP, mask, watchSubtree, new Listener());
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JarWatcher().watchJarFolder();
    }
}
