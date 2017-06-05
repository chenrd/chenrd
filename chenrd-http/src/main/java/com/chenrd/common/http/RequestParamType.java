/*
 * 文件名：RquestType.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年2月29日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

/**
 * 请求参数类型（普通表单类型，json类型）
 *
 * @author chenrd
 * @version 2016年2月29日
 * @see RequestParamType
 * @since
 */
public enum RequestParamType {
    
    STRING("string"), JSON("json");
    
    public String type;
    
    
    RequestParamType(String type)
    {
        this.type =  type;
    }
    
    public static RequestParamType getType(String type)
    {
        if ("string".equalsIgnoreCase(type)) return STRING;
        else if ("json".equalsIgnoreCase(type)) return JSON;
        return STRING;
    }
    
}
