/*
 * 文件名：bufferAnalytical.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月6日
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
 * @version 2015年8月6日
 * @see bufferAnalytical
 * @since
 */
public class HtmlAnalytical extends AnalyticalHlep implements StrAnalytical
{
    
    /**
     * 
     */
    public HtmlAnalytical()
    {
        
    }
    
    public HtmlAnalytical(String str, AnalyParma analyParma) {
        super(str, analyParma);
    }

    @Override
    public List<List<String>> analytical() throws AnalysisException {
        //第一步
        firstCutContent();
        //第二步
        super.formatEmpty();
        
        return format();
    }
    
    /**
     * 第一步剪取需要的内容部分
     * 
     * @param str
     * @param position
     * @return 
     * @see
     */
    protected void firstCutContent() throws AnalysisException
    {
        int homeIndex = 0;
        String position = analyParma.getQueryName() == null ? analyParma.getAnalys()[0] : analyParma.getQueryName();
        if (buffer.indexOf(position) == -1)
        {
            //TODO 异常待处理
            log.error("========本次抓取的内容开始=======");
            log.error(buffer.toString());
            log.error("========本次抓取的内容结束=======");
            throw new AnalysisException("没有抓取到数据");
        }
        if ((homeIndex = buffer.substring(0, buffer.indexOf(position)).lastIndexOf("<tr")) != -1 || (homeIndex = buffer.substring(0, buffer.indexOf(position)).lastIndexOf("<table")) != -1) {
            //删掉最前面的部分
            buffer.delete(0, homeIndex);
            if (buffer.indexOf("</table>") != -1) {
                buffer.delete(buffer.indexOf("</table>"), buffer.length());
            }
            
        } else if (buffer.indexOf(position) != -1) {
            buffer.delete(0, buffer.indexOf(position) + position.length());
            firstCutContent();
        }
    }
    
    //格式化html
    protected List<List<String>> format() {
        String input = buffer.toString().replace("></td>", "> </td>").replace("></th>", "> </th>").replace("<td>", "\r\n<td>").replace("><", ">\r\n<");
        BufferedReader reader = new BufferedReader(new StringReader(input));
        try {
            //第一行不要
            List<List<String>> list = new ArrayList<List<String>>();
            List<String> ls = null;
            while (true) {
                input = readLine(reader);
                if (input == null) {
                    break;
                }
                if (input.indexOf("<tr") == 0) {
                    input = readLine(reader);
                    ls = new ArrayList<String>();
                    list.add(ls);
                }
                input = input.substring(input.indexOf(">") + 1, input.indexOf("</"));
                if (isCheckHtmlFontFormat(input)) {
                    input = changeEncoded(input, new StringBuilder());
                }
                if (analyParma.getFilter() != null && analyParma.getFilter().indexOf(input) != -1) {
                    continue;
                }
                if (ls == null) {
                    ls = new ArrayList<String>();
                    list.add(ls);
                }
                ls.add(input);
            }
            return list;
        } catch (Exception e) {
            log.error("格式化html数据失败：" + e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    /**
     * 获取下一行
     * 
     * @param reader
     * @return 
     * @see
     */
    protected static String readLine(BufferedReader reader) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
