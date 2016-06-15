/**
 * Description : 
 * Created by YangZH on 16-6-16
 *  上午1:06
 */

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Description :
 * Created by YangZH on 16-6-16
 * 上午1:06
 */

public class TestDemo {

    @Test
    public void testProperties() throws IOException {
        Properties properties = new Properties();
//        System.out.println(String.valueOf(Boolean.TRUE));
        properties.load(new FileInputStream("/home/code4j/osproject/codecat/bin/../apps/app.properties"));
//        properties.load(new FileInputStream("/home/code4j/osproject/codecat/bin/../apps/app.properties"));
        System.out.println("ConfigExists = "+properties.getProperty("ConfigExists"));
        System.out.println("PluginExists = "+properties.getProperty("PluginExists"));
//
//        properties.setProperty("ConfigExists","true");
//        properties.setProperty("PluginExists",properties.getProperty("PluginExists"));
//        properties.store(new FileOutputStream("/home/code4j/osproject/codecat/bin/../apps/app.properties"),"");
    }
}
