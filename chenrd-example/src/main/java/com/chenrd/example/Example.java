/*
 * 文件名：Example.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.example;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 
 * @author chenrd
 * @version 2016年6月7日
 * @see Example
 * @since
 */
public abstract class Example implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 181863675499396177L;
    
    /**
     * 获取指定数属性值
     * @param fieldName 
     * @return Object
     * @see
     */
    public Object get(String fieldName)
    {
        try
        {
            Method method = null;
            try
            {
                method = this.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
            }
            catch (Exception e)
            {
                method = this.getClass().getMethod("is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
            }
            return method.invoke(this);
        }
        catch (ReflectiveOperationException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public void set(String fieldName, Object value)
    {
        Method method = null;
        try
        {
            method = this.getClass().getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), value.getClass());
            method.invoke(this, value);
        }
        catch (ReflectiveOperationException e)
        {
            e.printStackTrace();
        }
    }
}
