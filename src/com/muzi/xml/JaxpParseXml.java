package com.muzi.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by muzi on 2017/10/18.
 * Dom和sax解析xml的区别：
 *  1.dom解析的优点是对文档的curd比较方便，但是占用内存比较大
 *  2.sax解析的优点是占用内存少，解析速度快，但是缺点是只适合做文档的读取，不适合做文档的crud操作
 */
public class JaxpParseXml {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException,
            TransformerException {
        //jvm默认的最多开辟64M的内存大小
        //1.创建工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.得到dom解析器
        DocumentBuilder builder = factory.newDocumentBuilder();
        //3.解析xml，得到代表文档的document
        Document document = builder.parse("src/book");
        //read(document);
        //read1(document);
        //read2(document);
        //add(document);
        //delete(document);
        update(document);
    }

    /**
     * 使用dom方式对xml文档进行curd操作
     */
    private static void read(Document document) {
        NodeList list = document.getElementsByTagName("书名");
        Node node = list.item(1);
        System.out.println(node.getTextContent());
    }

    //遍历
    private static void read1(Document document) {
        //得到根节点
        Node root = document.getElementsByTagName("书架").item(0);
        list(root);
    }

    //递归
    private static void list(Node node) {
        if (node instanceof Element) {
            System.out.println(node.getNodeName());
        }
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            list(child);
        }
    }

    //获取标签的属性值
    private static void read2(Document document) {
        Element node = (Element) document.getElementsByTagName("书名").item(0);
        System.out.println(node.getAttribute("name"));
    }

    //向文档中添加节点 向节点添加属性
    private static void add(Document document) throws TransformerException, FileNotFoundException {
        //创建节点
        Element price = document.createElement("售价");
        price.setTextContent("250元");
        //挂载
        Element book = (Element) document.getElementsByTagName("书").item(0);
        book.appendChild(price);
        //往指定位置添加节点
        //得到参考节点
        Element refNode = (Element) document.getElementsByTagName("作者").item(1);
        Element book1 = (Element) document.getElementsByTagName("书").item(1);
        book1.insertBefore(price, refNode);
        //往节点上添加属性
        Element bookName = (Element) document.getElementsByTagName("书名").item(1);
        bookName.setAttribute("name", "就是我");
        //把更新后的内存书写回到文档
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document),
                new StreamResult(new FileOutputStream("src/book")));
    }

    //删除节点
    private static void delete(Document document) throws FileNotFoundException, TransformerException {
        Element child = (Element) document.getElementsByTagName("售价").item(1);
        Element father = (Element) document.getElementsByTagName("书").item(0);
        //father.removeChild(child);
        child.getParentNode().getParentNode().removeChild(child.getParentNode());
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document),
                new StreamResult(new FileOutputStream("src/book")));
    }

    //更新节点
    private static void update(Document document) throws TransformerException, FileNotFoundException {
        Element element = (Element) document.getElementsByTagName("售价").item(0);
        element.setTextContent("1024元");
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document),
                new StreamResult(new FileOutputStream("src/book")));
    }
}
