/*
 * 文件名：FreemakerController.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author chenrd
 * @version 2015年5月15日
 * @see FreemarkerController
 * @since
 */
public class FreemarkerController
{
    protected Logger logger = null;
    
    public FreemarkerController()
    {
        logger = LoggerFactory.getLogger(this.getClass());
    }
    
    /**
     * 
     * 获取路径
     * @param name 
     * @return 页面路径
     * @see
     */
    public String getViewName(String name) 
    {
        return this.getClass().getResource("").getPath().split("classes")[1] + name;
    }
    
    /**
     * 
     * 
     * @param code 
     * @param message 
     * @return JSONObject
     * @see
     */
    public JSONObject jsonResult(int code, String message) 
    {
        JSONObject object = new JSONObject();
        object.put("statusCode", code);
        object.put("message", message);
        return object;
    }
}
