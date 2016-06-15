package org.code4j.codecat.constants;/**
 * Description : 
 * Created by YangZH on 16-6-14
 *  下午1:23
 */

import java.io.File;

/**
 * Description :
 * Created by YangZH on 16-6-14
 * 下午1:23
 */

public interface Const {

    public static final String LOCALHOST = "localhost";

    public static final String PROXY_PASS = "proxy_pass";
    public static final String LISTEN = "listen";
    public static final String WEIGHT = "weight";
    public static final String HOST = "host";
    public static final String PORT = "port";

    public static final String NEWLINE = System.getProperty("line.separator");


    //programme directory structure
    public static final String UP_LEVEL = ".."+ File.separator;
    public static final String BIN = System.getProperty("user.dir")+File.separator;
    public static final String CONF = BIN+UP_LEVEL+"conf"+File.separator;
    public static final String LIB = BIN+UP_LEVEL+"lib"+File.separator;
    public static final String LOGS = BIN+UP_LEVEL+"logs"+File.separator;
    public static final String APP = BIN+UP_LEVEL+"apps"+File.separator;

    public static final String PROPERTIESFILE = "app.properties";
    public static final String CONFIGFILE = "app.xml";
    public static final String CONFIGEXISTS = "Config-Exists";

    public static final String FUNCTIONCLASS = "function-class";

}
