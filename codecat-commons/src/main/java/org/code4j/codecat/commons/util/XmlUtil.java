package org.code4j.codecat.commons.util;/**
 * Description : 
 * Created by YangZH on 16-6-15
 *  下午12:10
 */

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Description :
 * Created by YangZH on 16-6-15
 * 下午12:10
 */
@Deprecated
public class XmlUtil {

    private static SAXReader reader = new SAXReader();
    private Document document = null;
    private Element root = null;

    public XmlUtil(InputStream is){
        try {
            document = reader.read(is);
            root = document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XmlUtil(String xml_path){
        File xml = new File(xml_path);
        try {
            document = reader.read(xml);
            root = document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element getElementByKey(String key,Element ele){
        Iterator<Element> elementIterator = null;
        if (ele.isRootElement()){
            elementIterator = root.elementIterator();
        }else{
            elementIterator = ele.elementIterator();
        }
        while (elementIterator.hasNext()){
            Element element = elementIterator.next();
            if (element.getName().equals(key)){
                return element;
            }else{
                getElementByKey(key,element);
            }
        }
        return null;
    }

    public String getTextByTagName(String tagName){
        if (tagName == null || tagName.isEmpty()){
            return "";
        }
        return getElementByKey(tagName,root).getText();
    }
}
