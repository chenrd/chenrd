/*
 * 文件名：AnalyticalHlep.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析公共的抽象帮助类
 * 
 * @author chenrd
 * @version 2015年8月6日
 * @see AnalyticalHlep
 * @since
 */
public abstract class AnalyticalHlep
{
    
    protected StringBuffer buffer = new StringBuffer();
    
    protected AnalyParma analyParma;
    
    protected Logger log;
    
    /**
     * 
     */
    public AnalyticalHlep()
    {
        
    }
    
    /**
     * 手动初始化，不是通过构造函数初始化
     * 
     * @param str
     * @param analyParma 
     * @see
     */
    public void init(String str, AnalyParma analyParma)
    {
        log = LoggerFactory.getLogger(this.getClass());
        buffer.append(str);
        this.analyParma = analyParma;
    }
    
    public AnalyticalHlep(String str, AnalyParma analyParma)
    {
        log = LoggerFactory.getLogger(this.getClass());
        buffer.append(str);
        this.analyParma = analyParma;
    }
    /**
     * 验证是不是一个json字符串的格式数据
     * @param str
     * @return
     */
    protected boolean isCheckJsonFormat(String str) {
        Pattern p = Pattern.compile("^\\{.*\\}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * HTML页面中文编号验证
     * @return
     */
    protected boolean isCheckHtmlFontFormat(String str) {
        Pattern p = Pattern.compile(".*&#\\d+;.*");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 验证是否由数字组成
     */
    protected boolean isNumber(String str) {
        Pattern p = Pattern.compile("^\\d+");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 格式化注释部门内容及<span><div>常用的标签
     * @param html
     * @return
     */
    protected void formatNote(StringBuffer html) {
        if (html.indexOf("<!--") != -1) {
            html.delete(html.indexOf("<!--"), html.indexOf("-->") + 3);
            formatNote(html);
        }
    }
    
    /**
     * 格式化其它的标签
     * @param str
     * @param html
     */
    private void formatDiv(String str, StringBuffer html) {
        if (html.indexOf(str) != -1) {
            html.delete(html.indexOf(str), html.indexOf(">", html.indexOf(str)) + 1);
            String temp = str.substring(0, 1) + "/" + str.substring(1) + ">";
            if (html.indexOf(temp) != -1) {
                html.delete(html.indexOf(temp), html.indexOf(temp) + temp.length());
            }
            formatDiv(str, html);
        }
    }
    
  //格式化html数据
    //格式化第一步去处空字符串
    protected void formatEmpty() {
        BufferedReader reader = new BufferedReader(new StringReader(buffer.toString()));
        String input = null;
        StringBuffer html = new StringBuffer();
        try {
            while ((input = reader.readLine()) != null) {
                html.append(input.trim().replace("<br/>", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //从下往上格式化空格
        for (int i = html.length() - 1; i > 0;) {
            if ((i < html.length() - 3 && html.substring(i + 1, i + 3).equals("</")) && (html.charAt(i) == 32 || html.charAt(i) == 12288 || html.charAt(i) == 9)) {
                html.deleteCharAt(i);
            } else {
                i--;
            }
        }
        //从上往下格式化空格
        for (int i = 1; i < html.length();) {
            if (html.substring(i - 1, i).equals(">") && (html.charAt(i) == 32 || html.charAt(i) == 12288 || html.charAt(i) == 9)) {
                html.deleteCharAt(i);
            } else {
                i++;
            }
        }
        
        formatNote(html);
        
        if (StringUtils.isNotBlank(analyParma.getFilterLable())) {
            String[] str = analyParma.getFilterLable().split(",");
            for (String s : str) {
                formatDiv(s, html);
            }
        }
        buffer = html;
    }
    
    protected String changeEncoded(String str, StringBuilder result) {
        StringBuilder builder = new StringBuilder(str);
        if (builder.indexOf("&#") == -1) {
            return result.append(builder.toString()).toString();
        }
        result.append(builder.substring(0, builder.indexOf("&#")));
        builder.delete(0, builder.indexOf("&#"));
        result.append((char) Integer.parseInt(builder.substring(builder.indexOf("&#") + 2, builder.indexOf(";"))));
        builder.delete(builder.indexOf("&#"), builder.indexOf(";") + 1);
        return changeEncoded(builder.toString(), result);
    }
}
