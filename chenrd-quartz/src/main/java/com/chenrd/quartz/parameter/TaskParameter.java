/*
 * 文件名：TaskParameter.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年4月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz.parameter;

import java.io.Serializable;

/**
 * 
 * @author chenrd
 * @version 2016年4月6日
 * @see TaskParameter
 * @since
 */
public interface TaskParameter extends Serializable
{
    /**
     * 页面定义task参数的时候格式为json,固定参数用这个名称
     * 固定参数名称
     */
    public static final String fix_parameter_name = "fix";
    
    /**
     * 变量参数
     */
    public static final String var_parameter_name = "var";

    /**
     * 制定参数接受类
     */
    public static final String parameter_class = "clazz";
}
