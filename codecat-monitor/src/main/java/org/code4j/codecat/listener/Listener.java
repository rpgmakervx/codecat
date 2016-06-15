package org.code4j.codecat.listener;/**
 * Description : 
 * Created by YangZH on 16-6-13
 *  下午2:15
 */

import net.contentobjects.jnotify.JNotifyAdapter;
import org.apache.log4j.Logger;
import org.code4j.codecat.constants.Const;
import org.code4j.codecat.monitor.invoker.ShellInvoker;
import org.code4j.codecat.service.ReadConfigService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

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
        //get current path to find real plugin's location
        //有可能你的插件是部署在app下面的某个文件夹下，
        //这样的话app.xml就要基于当前的路径寻找
        String configPath = s+File.separator+Const.CONFIGFILE;
        String propPath = s+File.separator+Const.PROPERTIESFILE;
        //当前新增的文件
        File file = new File(s+File.separator+s1);
        //manifest文件元数据
        File propFile = new File(propPath);
        //配置文件元数据
        File configFile = new File(configPath);
        Properties properties = new Properties();
        try{
            if (! propFile.exists()){
                propFile.createNewFile();
            }
            properties.load(new FileInputStream(propFile));
            if (file.isFile()){
                //init MANIFEST.MF,if app.xml exists, Config-Exists property's value is true
                properties.setProperty(Const.CONFIGEXISTS, String.valueOf(configFile.exists()));
                properties.store(new FileOutputStream(propFile),"");
//                if (configFile.exists()){
//                }else{
//                    properties.setProperty()
//                }
                if (file.getName().endsWith(".class")){
                    boolean hasConfig = Boolean.valueOf(properties.getProperty(Const.CONFIGEXISTS));
//                    boolean hasConfig = manifest.getBoolValue(Const.CONFIGEXISTS);
                    if (hasConfig){
                        ReadConfigService service = new ReadConfigService(configPath);
                        logger.debug("plugin's name : "+service.getFunctionClassName());
                        executeRealServer();
                    }else{
                        logger.info("app.xml not present");
                    }
                }else if (file.getName().endsWith(".xml")){
                    ReadConfigService service = new ReadConfigService(configPath);
                    logger.debug("plugin's name : "+service.getFunctionClassName());
                }else{
                    logger.info("file is not a plugin or a config file");
                }
            }else{
                logger.info("this is not a file");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
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

    private void executeRealServer(){
        ShellInvoker.execute(ShellInvoker.STARTUP_SERVER, String.valueOf(PortCounter.incr()));
        logger.info("opened a new server, port is : "+PortCounter.getPort());
    }
}
