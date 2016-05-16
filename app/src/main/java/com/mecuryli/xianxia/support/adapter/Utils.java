package com.mecuryli.xianxia.support.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mecuryli.xianxia.xianxiaApplication;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by 海飞 on 2016/5/9.
 * 用于解析得到的uews的xml文件
 */
public class Utils {

    private static Context mContext = xianxiaApplication.AppContext;

    //将要解析的xml文件转为输入流
    public static InputStream readFileFromRaw(int fileID){
        return mContext.getResources().openRawResource(fileID);
    }

    //通过文件的方式获取Document对象
    public static Document getDocmentByIS(InputStream is){
        //得到DocumentBuilderFactory对象，由该对象得到BuilderBuilder对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try{
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try{
            doc = builder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    //根据string来匹配解析regex得到的对象
    public static String RegexFind(String regex,String string){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String res = string;
        while (matcher.find()){
            res = matcher.group();
        }
        return res.substring(1,res.length() - 1);//截取一个字符串
    }

    //将所有匹配的对象替换为replace代表的对象
    public static String regexReplace(String regex, String string,String replace){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.replaceAll(replace);
    }

    public static boolean hasString (String str){
        if (str == null || str.equals("")){
            return false;
        }
        return true;
    }

    public static void showToast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void DLog(String text){
        Log.d("调试数据",text);
    }
}
