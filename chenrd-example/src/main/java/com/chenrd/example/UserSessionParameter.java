/*
 * 文件名：UserSessionParameter.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年1月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.example;

/**
 * 
 * @author chenrd
 * @version 2016年1月18日
 * @see UserSessionParameter
 * @since
 */
public interface UserSessionParameter
{
    
    public static final String OSS_DEFAULT_ADMIN = "root";
    
    /**
     * 
     */
    public static final String OSS_SESSION_USER_ID = "oss_session_user_id";
    
    /**
     * 
     */
    public static final String OSS_SESSION_USER_NAME = "oss_session_user_name";
    
    /**
     * 用户session存货时间
     */
    public static final String OSS_SESSION_USER_TOKEN = "oss_session_user_token";
    
    /**
     * 
     */
    public static final String OSS_SESSION_USER_SUBJECT = "oss_session_user_subject";
    
    
    public static final String OSS_SESSION_USER_TYPE = "oss_session_user_type";
}
