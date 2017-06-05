/*
 * 文件名：ControllerResult.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年7月3日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import com.alibaba.fastjson.JSONObject;

/**
 * chenrd-oss-ex
 * 
 * @author chenrd
 * @version 2015年7月3日
 * @see ControllerResult
 * @since
 */
public final class ControllerResult
{

    /**
     * 
     */
    public static final String STATUSCODE = "statusCode";
    
    /**
     * 返回json内容的名称
     */
    public static final String CENTENT_LABLE = "message";
    
    /**
     * 错误编码
     */
    public static final int ERRORCODE = 300;
    
    /**
     * 
     * @param message 错误原因
     * @return 
     * @see
     */
    public static String errorJSONString(String message)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUSCODE, ERRORCODE);
        return jsonObject.toString();
    }
    /**
     * 
     */
    private ControllerResult()
    {
        
    }
}
