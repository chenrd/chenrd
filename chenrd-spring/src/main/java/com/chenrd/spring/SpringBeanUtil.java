/*
 * 文件名：StringBeanUtil.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.spring;

import org.springframework.context.ApplicationContext;

/**
 * 
 * 通过ID获取spring bean
 * @author chenrd
 * @version 2015年5月14日
 * @see SpringBeanUtil
 * @since
 */
public final class SpringBeanUtil
{
    /**
     * 
     */
    private static ApplicationContext applicationContext;

    /**
     * 
     * @param applicationContext 
     * @see
     */
    public static void setApplicationContext(ApplicationContext applicationContext)
    {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * @return Returns the applicationContext.
     */
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
    
    /**
     * 
     * 获取一个spring bean
     * @param id spring bean id
     * @return Object
     * @see
     */
    public static Object getObject(String id) 
    {
        return applicationContext.getBean(id);
    }
    
}
