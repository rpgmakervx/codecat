package org.code4j.codecat.service;/**
 * Description : 
 * Created by YangZH on 16-6-15
 *  下午1:53
 */

import org.code4j.codecat.constants.Const;
import org.code4j.codecat.util.XmlUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Description :
 * Created by YangZH on 16-6-15
 * 下午1:53
 */

public class ReadConfigService {

    private XmlUtil util;

    public ReadConfigService(String configPath){
        util = new XmlUtil(configPath);
    }

    public ReadConfigService(File configFile){
        try {
            util = new XmlUtil(new FileInputStream(configFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getFunctionClassName(){
        return util.getTextByTagName(Const.FUNCTIONCLASS);
    }
}
