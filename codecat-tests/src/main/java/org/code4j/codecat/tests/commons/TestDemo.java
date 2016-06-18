package org.code4j.codecat.tests.commons; /**
 * Description : 
 * Created by YangZH on 16-6-15
 *  下午2:23
 */

import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.monitor.util.JarHelper;
import org.code4j.codecat.commons.util.XmlUtil;
import org.junit.Test;

import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-6-15
 * 下午2:23
 */

public class TestDemo {

    @Test
    public void testXmlUtil(){
        String text = new XmlUtil(
                XmlUtil.class.getResourceAsStream("/app.xml"))
                .getTextByTagName(Const.FUNCTIONCLASS);
        System.out.println(Const.FUNCTIONCLASS +" : "+text);
    }

    @Test
    public void testJarUtil(){
        List<String> classnames = JarHelper.getClassFileName("/home/code4j/osproject/codecat/apps/handler.jar");
        for (String name:classnames){
            System.out.println(name);
        }
    }

}
