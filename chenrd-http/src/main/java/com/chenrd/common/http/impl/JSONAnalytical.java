/*
 * 文件名：JSONAnalytical.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.chenrd.common.http.AnalyParma;
import com.chenrd.common.http.AnalyticalHlep;
import com.chenrd.common.http.StrAnalytical;
import com.chenrd.common.http.exception.AnalysisException;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author chenrd
 * @version 2015年8月7日
 * @see JSONAnalytical
 * @since
 */
public class JSONAnalytical extends AnalyticalHlep implements StrAnalytical
{
    
    public JSONAnalytical()
    {
        super();
    }

    public JSONAnalytical(String str, AnalyParma analyParma)
    {
        super(str, analyParma);
    }

    @Override
    public List<List<String>> analytical() throws AnalysisException
    {
        String position = analyParma.getQueryName() == null ? analyParma.getAnalys()[0] : analyParma.getQueryName();
        if (buffer.indexOf(position) == -1) throw new AnalysisException("解析错误，解析的字符串中没有定位到字符串：" + position);
        int homeIndex = buffer.lastIndexOf("[", buffer.indexOf(position)), lastIndex = buffer.indexOf("]", buffer.lastIndexOf(position));
        JSONArray array = JSONArray.parseArray(buffer.substring(homeIndex, lastIndex + 1));
        List<List<String>> list = new ArrayList<List<String>>();
        if (array.size() > 0)
        {
            List<String> ls = new ArrayList<String>();
            for (Object key : array.getJSONObject(0).keySet())
            {
                ls.add((String) key);
            }
            list.add(ls);
            
            for (int i = 0; i < array.size(); i++)
            {
                ls = new ArrayList<String>();
                for (Object key : array.getJSONObject(i).keySet())
                {
                    ls.add(array.getJSONObject(i).get(key) == null ? "" : array.getJSONObject(i).get(key).toString());
                }
                list.add(ls);
            }
        }
        return list;
    }

    

}
