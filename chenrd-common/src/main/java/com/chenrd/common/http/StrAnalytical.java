/*
 * 文件名：StrAnalytical.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

import java.util.List;

import com.chenrd.common.http.exception.AnalysisException;


/**
 * 
 * 返回字符串的解析
 * 
 * @author chenrd
 * @version 2015年8月6日
 * @see StrAnalytical
 * @since
 */
public interface StrAnalytical
{
    
    /**
     * 解析
     * 
     * @param str
     * @return 
     * @see
     */
    List<List<String>> analytical() throws AnalysisException;
    
    /**
     * 初始化
     * @param str
     * @param analyParma 
     * @see
     */
    void init(String str, AnalyParma analyParma);
}
