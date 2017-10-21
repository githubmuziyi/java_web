package com.muzi.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * Created by muzi on 2017/10/21.
 * dom4j解析xml文件
 */
public class Dom4JParseXml {

    /**
     * 读取
     * @throws DocumentException
     */
    @Test
    public void read() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/book"));
        String bookName = document.getRootElement().elements("书").get(1).element("书名").getText();
        String bookNameAtt = document.getRootElement().elements("书").get(1).element("书名").attributeValue("name");
        System.out.println(bookName);
        System.out.println(bookNameAtt);
    }

    /**
     * 添加
     */
    @Test
    public void add() throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document document =reader.read(new File("src/book"));
        //默认追加到末尾
        document.getRootElement().element("书").addElement("页数").setText("105页");
        //在指定位置添加
        List list = document.getRootElement().element("书").elements();
        org.dom4j.Element lan = DocumentHelper.createElement("语言");
        lan.setText("汉语");
        list.add(2, lan);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("gb2312");
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/book"), "gb2312"), format);
        writer.write(document);
        writer.close();
    }

    /**
     * 删除
     */
    @Test
    public void delete() throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document document =reader.read(new File("src/book"));
        Element lan = document.getRootElement().element("书").element("语言");
        lan.getParent().remove(lan);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream("src/book"), format);
        writer.write(document);
        writer.close();
    }

    /**
     * 更新
     */
    @Test
    public void update() throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document document =reader.read(new File("src/book"));
        document.getRootElement().elements("书").get(1).element("作者").setText("19921203");
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream("src/book"), format);
        writer.write(document);
        writer.close();
    }

}
