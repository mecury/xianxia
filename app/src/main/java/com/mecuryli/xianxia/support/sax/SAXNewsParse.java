package com.mecuryli.xianxia.support.sax;

import com.mecuryli.xianxia.model.news.NewsBean;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by 海飞 on 2016/5/9.
 */
public class SAXNewsParse {
    public static List<NewsBean> items;
    public static List<NewsBean> parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        SAXNewsHandler saxHandler = new SAXNewsHandler();
        xmlReader.setContentHandler(saxHandler); //解析器注册一个事件
        xmlReader.parse(new InputSource(is)); //读取文件流
        items = saxHandler.getItems();
        return items;
    }
}
