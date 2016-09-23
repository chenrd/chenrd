/*
 * 文件名：AbstractJob.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年3月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz;

import org.springframework.stereotype.Component;

/**
 * 抽象
 * 
 * @author chenrd
 * @version 2016年3月4日
 * @see AbstractJob
 * @since
 */
public abstract class AbstractJob
{
    
    protected String[] paramNames = null;
    
    /**
     * 
     * 
     * @param paramNames 
     * @see
     */
    public void setParamNames(String[] paramNames)
    {
        this.paramNames = paramNames;
    }
    
    /**
     * 
     * 
     * @return 
     * @see
     */
    public String getThisComponentName()
    {
        return this.getClass().getAnnotation(Component.class).value();
    }
}
