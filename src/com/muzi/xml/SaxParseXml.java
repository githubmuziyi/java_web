package com.muzi.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muzi on 2017/10/19.
 * sax解析xml文档
 */
public class SaxParseXml {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //创建解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //得到解析器
        SAXParser parser = factory.newSAXParser();
        //得到读取器
        XMLReader reader = parser.getXMLReader();
        //设置内容处理器
        //reader.setContentHandler(new ListXmlHandler());
        reader.setContentHandler(new TagValueHandler());
        //读取xml文档内容
        reader.parse("src/book");
    }
}

/**
 * 把xml文档中的书封装到book对象，并把多个book对象放到list中
 */
class BeanLisrHandler extends DefaultHandler {

    private List<Book> list = new ArrayList<>();
    private String currentTag;
    private Book book;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        currentTag = qName;
        if ("书".equals(currentTag)) {
            book = new Book();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if ("书".equals(currentTag)) {
            list.add(book);
            book = null;
        }
        currentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if ("书名".equals(currentTag)) {
            book.setName(new String(ch, start, length));
        }
        if ("售价".equals(currentTag)) {
            book.setPrice(new String(ch, start, length));
        }
        if ("作者".equals(currentTag)) {
            book.setAuthor(new String(ch, start, length));
        }
    }
}

/**
 * 获取指定标签的值
 */
class TagValueHandler extends DefaultHandler {

    private String currentTag; //当前解析的是什么标签
    private int index = 1; //获取第几个作者标签的值
    private int currentIndex; //当前解析到第几个

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        currentTag = qName;
        if ("作者".equals(currentTag)) {
            currentIndex++;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        currentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if ("作者".equals(currentTag) && currentIndex == index) {
            System.out.println(new String(ch, start, length));
        }
    }
}

/**
 * 得到xml文档的所用内容
 */
class ListXmlHandler implements ContentHandler {

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        System.out.println("<" + qName + ">");
        for (int i = 0; atts != null && i < atts.getLength(); i++) {
            String attName = atts.getQName(i);
            String attValue = atts.getValue(i);
            System.out.println(attName + ":" + attValue);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("<" + qName + ">");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println(new String(ch, start, length));
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }
}
