package org.code4j.codecat.commons.util;/**
 * Description : 
 * Created by YangZH on 16-6-16
 *  上午1:35
 */

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 上午1:35
 */

public class PropertyHelper {

    private Logger logger = Logger.getLogger(PropertyHelper.class);
    private Properties properties;
    private File propFile;
    public PropertyHelper(File propFile){
        this.propFile = propFile;
        this.properties = new Properties();
    }

    public String getValue(String key){
        dealCommon();
        return properties.getProperty(key);
    }

    public void updateProperties(String key,String value){
        dealCommon();
        boolean notNUll = key != null && value != null;
        if (notNUll && value.equals(properties.getProperty(key))){
            logger.info("update 失败，新的值和旧的值相等");
            return ;
        }
        Set<String> names = properties.stringPropertyNames();
        try {
            if (names == null || names.isEmpty()|| !names.contains(key)){
                properties.setProperty(key,value);
            }else{
                for (String name:names){
                    if (name.equals(key)){
                        properties.setProperty(key, value);
                    }else{
                        String currentValue = properties.getProperty(name);
                        properties.setProperty(name, currentValue);
                    }
                }
            }
            properties.store(new FileOutputStream(propFile), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("update 成功 "+key+" = "+properties.getProperty(key));
    }

    private void dealCommon(){
        try {
            if (! propFile.exists()){
                propFile.createNewFile();
            }
            properties.load(new FileInputStream(propFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
