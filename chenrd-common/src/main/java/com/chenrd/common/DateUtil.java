/*
 * 文件名：DateUtil.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 时间帮组类
 * @author chenrd
 * @version 2015年5月18日
 * @see DateUtil
 * @since
 */
public final class DateUtil
{

    /**
     * 
     */
    private static Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    
    /**
     * 精确到日期的格式
     */
    public static final String DATEFORMAT = "yyyy-MM-dd";
    
    /**
     * 
     */
    private final static ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>();
    
    /**
     * 精确到时间的格式
     */
    public static final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 
     */
    public static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>();
    
    
    /**
     * 
     * 默认格式DateUtil.DATEFORMAT
     * @param date 格式化时间
     * @return ""
     * @see
     */
    public static String formatDate(Date date) 
    {
        if (date == null) 
        {
            return null;
        }
        if (DATE_FORMAT.get() == null) DATE_FORMAT.set(new SimpleDateFormat(DATEFORMAT));
        return DATE_FORMAT.get().format(date);
    }
    
    /**
     * 
     * 默认格式DateUtil.DATEFORMAT
     * @param date 格式化时间
     * @return ""
     * @see
     */
    public static String formatDate(Date date, String format) 
    {
        if (date == null) 
        {
            return null;
        }
        if (DATEFORMAT.equals(format)) return formatDate(date);
        if (TIMEFORMAT.equals(format)) return formatDateTime(date);
        return new SimpleDateFormat(format).format(date);
    }
    
    /**
     * DATE_TIME_FORMAT
     * @param date 时间
     * @return ""
     * @see DATE_TIME_FORMAT
     */
    public static String formatDateTime(Date date)
    {
        if (date == null) 
        {
            return null;
        }
        if (DATE_TIME_FORMAT.get() == null) DATE_TIME_FORMAT.set(new SimpleDateFormat(TIMEFORMAT));
        return DATE_TIME_FORMAT.get().format(date);
    }
    
    
    /**
     * 
     * @param date 时间字符串
     * @return 日期
     * @see
     */
    public static Date parseDate(String date) 
    {
        if (!StringUtils.isNotBlank(date)) return null;
        try
        {
            if (DATE_FORMAT.get() == null) DATE_FORMAT.set(new SimpleDateFormat(DATEFORMAT));
            return DATE_FORMAT.get().parse(date);
        }
        catch (ParseException e)
        {
            LOG.error("日期格式转换失败：{}-->{}", date, DATEFORMAT);
            return null;
        }
    }
    
    public static Date parseDate(String date, String format) 
    {
        if (date == null) 
        {
            return null;
        }
        if (DATEFORMAT.equals(format)) return parseDate(date);
        if (TIMEFORMAT.equals(format)) return parseDateTime(date);
        try
        {
            return new SimpleDateFormat(format).parse(date);
        }
        catch (ParseException e)
        {
            LOG.info("日期格式转换失败：{}-->{}", date, DATEFORMAT);
            return null;
        }
    }
    
    /**
     * 
     * @param date 时间字符串
     * @return 日期
     * @see
     */
    public static Date parseDateTime(String date) 
    {
        try
        {
            if (DATE_TIME_FORMAT.get() == null) DATE_TIME_FORMAT.set(new SimpleDateFormat(TIMEFORMAT));
            return DATE_TIME_FORMAT.get().parse(date);
        }
        catch (ParseException e)
        {
            LOG.info("日期格式转换失败：{}-->{}", date, TIMEFORMAT);
            return null;
        }
    }
    
    
    private DateUtil() 
    {
        
    }
}


