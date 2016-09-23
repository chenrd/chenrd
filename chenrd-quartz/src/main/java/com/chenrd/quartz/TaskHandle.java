/*
 * 文件名：Task.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年1月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz;

import com.chenrd.quartz.parameter.TaskParameter;

public interface TaskHandle
{
    /**
     *  
     * 
     * @param params 真正需要的参数
     * @see
     */
    String execute(String jobName, String jobGroup, TaskParameter parameter);
    
    public final static String EXECUTE_SUCCESS = "success";
    
}
