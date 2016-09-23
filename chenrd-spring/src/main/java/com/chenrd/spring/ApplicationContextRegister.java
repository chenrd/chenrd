/*
 * 文件名：ApplicationContextRegister.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年6月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author chenrd
 * @version 2015年6月24日
 * @see ApplicationContextRegister
 * @since
 */
@Component
public class ApplicationContextRegister implements ApplicationContextAware
{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        SpringBeanUtil.setApplicationContext(applicationContext);
    }

}
