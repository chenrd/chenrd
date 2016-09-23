/*
 * 文件名：BeanCopyUtils.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chenrd.common.ocp.DateFormat;
import com.chenrd.common.ocp.DateFormat.Format;
import com.chenrd.common.ocp.NoCopy;
import com.chenrd.common.ocp.NoCopy.NoCopyType;

/**
 * 
 * @author chenrd
 * @version 2016年7月6日
 * @see BeanCopyUtils
 * @since
 */
public final class BeanCopyUtils
{
    
    //日志
    public static final Logger LOG = LoggerFactory.getLogger(BeanCopyUtils.class);
    
    @SuppressWarnings("unchecked")
    public static <F, T> T copy(F form, T to, boolean currentNoCopy)
    {
        return (T) copy(form, form.getClass(), to, to.getClass(), currentNoCopy);
    }
    
    public static <T> T copy(Object form, Class<T> toClass, boolean currentNoCopy)
    {
        return copy(form, form.getClass(), null, toClass, currentNoCopy);
    }
    
    private static <F, T> T copy(Object form, Class<F> formClass, T toObject, Class<? extends T> toClass, boolean currentNoCopy)
    {
        NoCopy noCopy = null;Method setMethod = null;
        if (toObject == null) try
        {
            toObject = toClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException("拷贝失败，toClass{" + toClass.getName() + "}没有公共的无参数构造方法");
        }
        for (Field field : formClass.getDeclaredFields())
        {
            //如果是一个静态属性，那么不需要拷贝
            if (Modifier.isStatic(field.getModifiers())) continue;
            //属性被标注了永久不拷贝
            noCopy = field.getAnnotation(NoCopy.class);
            if (noCopy != null && noCopy.value() == NoCopyType.Per) continue;
            //属性为当前不拷贝，并且方法被调用时，标注当前不拷贝
            if (noCopy != null && noCopy.value() == NoCopyType.Current && currentNoCopy) continue;
            //如果Object中没有与当前属性符合的set方法那么不拷贝
            setMethod = getMethod(toClass, generateMethodName("set", field.getName()), ((Class<?>) field.getType()).isAssignableFrom(Date.class) ? String.class : field.getType());
            if (setMethod == null) continue;
            Object fieldValue = getFieldValue(form, formClass, field);
            if (fieldValue == null) continue;
            
            DateFormat dateFormat = field.getAnnotation(DateFormat.class);
            if (fieldValue instanceof Date) fieldValue = DateUtil.formatDate((Date) fieldValue, dateFormat != null ? dateFormat.value().format : Format.yMd.format);
            try
            {
                setMethod.invoke(toObject, fieldValue);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                LOG.info("拷贝属性时候发送错误，错误原因：没有权限执行目标:{}的方法:{}, ..{}", toClass.getName(), setMethod.getName(), e.getMessage());
            }
        }
        if (formClass.getGenericSuperclass() != null)
            copy(form, (Class<?>) formClass.getGenericSuperclass(), toObject, toClass, currentNoCopy);
        return toObject;
    }
    
    public static Object getFieldValue(Object form, Class<?> formClass, Field field)
    {
        String prefix = "get";
        if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class))
            prefix = "is";
        Method mehtod = getMethod(formClass, generateMethodName(prefix, field.getName()));
        if (mehtod == null) return null;
        try
        {
            return mehtod.invoke(form);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            LOG.info("拷贝属性时候发送错误，错误原因：没有权限执行目标:{}的方法:{}, ..{}", formClass.getName(), mehtod.getName(), e.getMessage());
        }
        return null;
    }
    
    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... types)
    {
        try
        {
            return clazz.getMethod(methodName, types);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            LOG.info("拷贝属性时候发送错误，错误原因：没有找到目标:{}.{}({}) ", clazz.getName(), methodName, types);
        }
        return null;
    }
    
    private static String generateMethodName(String prefix, String fieldName)
    {
        if (fieldName.charAt(1) <= 90)
            return prefix + fieldName;
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
    
    /**
     * 帮组类不被new
     */
    private BeanCopyUtils()
    {
        
    }
}
