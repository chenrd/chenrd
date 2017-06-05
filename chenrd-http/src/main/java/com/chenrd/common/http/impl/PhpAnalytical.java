/*
 * 文件名：PhpAnalytical.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.chenrd.common.http.AnalyParma;
import com.chenrd.common.http.AnalyticalHlep;
import com.chenrd.common.http.StrAnalytical;
import com.chenrd.common.http.exception.AnalysisException;

/**
 * 
 * @author chenrd
 * @version 2015年8月7日
 * @see PhpAnalytical
 * @since
 */
public class PhpAnalytical extends AnalyticalHlep implements StrAnalytical
{

    public PhpAnalytical(String str, AnalyParma analyParma)
    {
        super(str, analyParma);
    }

    @Override
    public List<List<String>> analytical() throws AnalysisException
    {
        formatEmpty();
        return formatPhp();
    }
    
    /**
     * php格式化方式
     * @param buffer
     * @param config
     * @param mapping
     * @return
     */
    private List<List<String>> formatPhp() {
        String input = buffer.toString().replace("<![CDATA[", "").replace("]]>", "").replaceAll("><", ">\r\n<");
        BufferedReader reader = new BufferedReader(new StringReader(input));
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            List<String> ls = null;
            reader.readLine();
            reader.readLine();
            while (true) {
                input = readLine(reader);
                if (input == null) {
                    break;
                }
                if (input.indexOf("<row") == 0) {
                    input = readLine(reader);
                    ls = new ArrayList<String>();
                    list.add(ls);
                }
                input = input.substring(input.indexOf(">") + 1, input.indexOf("</"));
                ls.add(input);
            }
            return list;
        } catch (Exception e) {
            log.error("格式化PHP数据失败：" + e.getMessage());
        }
        return null;
    }
    
    /**
     * 获取下一行
     * 
     * @param reader
     * @return 
     * @throws IOException 
     * @see
     */
    private static String readLine(BufferedReader reader) throws IOException {
        String input = reader.readLine();
        if (input == null) {
            return null;
        }
        if (input.indexOf("<cell") == -1 && input.indexOf("<row") == -1 && input.indexOf("<tr") == -1 && input.indexOf("<td") == -1 && (input.indexOf("<th") == -1 || input.indexOf("<thead") == 0)) {
            return readLine(reader);
        } else {
            if (input.indexOf("<td") != -1 && input.indexOf("</td>") != input.length() - 5) {
                input += "</td>";
            }
            return input;
        }
    }

}
