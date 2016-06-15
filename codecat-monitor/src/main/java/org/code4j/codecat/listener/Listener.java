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
        dealNewFile(s,s1);
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

    private void dealNewFile(String path,String filename){
        //get current path to find real plugin's location
        //有可能你的插件是部署在app下面的某个文件夹下，
        //这样的话app.xml就要基于当前的路径寻找
        String configPath = path+File.separator+Const.CONFIGFILE;
        logger.info("configPath : "+configPath);
        String propPath = path+File.separator+Const.PROPERTIESFILE;
        logger.info("propPath : "+propPath);
        //当前新增的文件
        File file = new File(path+File.separator+filename);
        //manifest文件元数据
        File propFile = new File(propPath);
        //配置文件元数据
        File configFile = new File(configPath);
        try{
            if (file.isFile()){
//                FileOutputStream propOutput = new FileOutputStream(propFile);
                Properties properties = new Properties();
                if (! propFile.exists()){
                    propFile.createNewFile();
                    properties.load(new FileInputStream(propFile));
                    //init MANIFEST.MF,if app.xml exists, Config-Exists property's value is true
                    properties.setProperty(Const.CONFIGEXISTS, String.valueOf(configFile.exists()));
                    properties.setProperty(Const.PLUGINEXISTS, String.valueOf(file.getName().endsWith(".class")));
                    properties.store(new FileOutputStream(propFile), "");
                }
                boolean hasConfig;
                boolean hasPlugin;
                if (file.getName().endsWith(".class")){
                    if (propFile.exists()){
                        properties.load(new FileInputStream(propFile));
                    }
                    properties.setProperty(Const.PLUGINEXISTS, "true");
                    properties.setProperty(Const.CONFIGEXISTS, properties.getProperty(Const.CONFIGEXISTS));
                    properties.store(new FileOutputStream(propFile), "");
                    logger.info("has config ? "+properties.getProperty(Const.CONFIGEXISTS));
                    hasConfig = Boolean.valueOf(properties.getProperty(Const.CONFIGEXISTS));
                    if (hasConfig){
                        ReadConfigService service = new ReadConfigService(configPath);
                        logger.info("plugin's name : " + service.getFunctionClassName());
                        executeRealServer();
                    }else{
                        logger.info("config not exists");
                    }
                }else if (file.getName().endsWith(".xml")){
                    if (propFile.exists()){
                        properties.load(new FileInputStream(propFile));
                    }
                    properties.setProperty(Const.CONFIGEXISTS, "true");
                    properties.setProperty(Const.PLUGINEXISTS, properties.getProperty(Const.PLUGINEXISTS));
                    properties.store(new FileOutputStream(propFile), "");
                    logger.info("has plugin ? " + properties.getProperty(Const.PLUGINEXISTS));
                    hasPlugin = Boolean.valueOf(properties.getProperty(Const.PLUGINEXISTS));
                    if (hasPlugin){
                        ReadConfigService service = new ReadConfigService(configPath);
                        logger.info("plugin's name : " + service.getFunctionClassName());
                        executeRealServer();
                    }else{
                        logger.info("plugin not exists");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
