/*
 * 文件名：RegularExpressionUtil.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月3日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式帮助类
 * 
 * @author chenrd
 * @version 2015年8月3日
 * @see RegularExpressionUtil
 * @since
 */
public final class RegularExpressionUtil
{

    /**
     * 
     */
    public RegularExpressionUtil()
    {
        
    }
    
    /**
     * 
     * 
     * @param expression 表达式
     * @param str 验证的字段
     * @return true or false
     * @see
     */
    public static boolean matches(String expression, String str)
    {
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
}
