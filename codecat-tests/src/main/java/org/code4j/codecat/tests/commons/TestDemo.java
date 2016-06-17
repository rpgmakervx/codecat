package org.code4j.codecat.tests.commons; /**
 * Description : 
 * Created by YangZH on 16-6-15
 *  下午2:23
 */

import org.code4j.codecat.commons.constants.Const;
import org.code4j.codecat.commons.util.XmlUtil;
import org.junit.Test;

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

}
