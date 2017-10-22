package com.muzi.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by muzi on 2017/10/21.
 * Xpath提取xml的文件内容
 */
public class XpathDemo {

    public static void main(String[] args) throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/book"));
        String author = document.selectSingleNode("//作者").getText();
        System.out.println(author);
    }
}
