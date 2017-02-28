/*
 * 文件名：BeanUtil.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chenrd.common.DateUtil;
import com.chenrd.common.ocp.DateFormat;
import com.chenrd.dao.annotation.Copy;
import com.chenrd.dao.annotation.NoCopy;
import com.chenrd.example.Domain;
import com.chenrd.example.Example;

/**
 * 
 * @author chenrd
 * @version 2015年5月18日
 * @see BeanUtil
 * @since
 */
public final class BeanUtil
{
    /**
     * 
     */
    private static Logger LOG = LoggerFactory.getLogger(BeanUtil.class);
    
    /**
     * 
     * @param <F> 被拷贝的对象
     * @param <T> 被赋值的对象
     * @param list 
     * @param toClass 
     * @return List<to>
     * @see
     */
    public static <F extends Example, T extends Example>  List<T> returnList(List<F> list, Class<T> toClass) 
    {
        List<T> vos = new ArrayList<T>();
        for (F f : list)
        {
            T t;
            try
            {
                t = toClass.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                LOG.error("拷贝错误，实例化{}的时候抛出了一个异常：{}", toClass.getName(), e.getMessage());
                return null;
            }
            copyProperties(f, t);
            vos.add(t);
        }
        return vos;
    }
    
    /**
     * 
     * 拷贝属性，实体
     * @param form 从当前bean拷贝
     * @param to 拷贝到该bean
     * @see
     */
    public static void copyProperties(Serializable form, Serializable to) 
    {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : ((Class<?>) to.getClass().getGenericSuperclass()).getDeclaredFields())
        {
            if (field.getAnnotation(NoCopy.class) == null) fields.add(field);
        }
        for (Field field : to.getClass().getDeclaredFields())
        {
            if (field.getAnnotation(NoCopy.class) == null) fields.add(field);
        }
        Object value = null;
        for (Field field : fields) 
        {
            if (Modifier.isStatic(field.getModifiers())) continue;
            Copy copy = field.getAnnotation(Copy.class);
            if (copy != null)
            {
                String[] s = copy.value().split("\\.");
                Domain domain = (Domain) form;
                for (int i = 0; i < s.length - 1; i++) 
                {
                    domain = (Domain) domain.get(s[i]);
                }
                if (domain != null) value = domain.get(s[s.length - 1]);
            } 
            else
            {
                Field formField = null;
                try {
                    formField = form.getClass().getDeclaredField(field.getName());
                } catch (NoSuchFieldException | SecurityException e) {
                    try {
                        formField = ((Class<?>) form.getClass().getGenericSuperclass()).getDeclaredField(field.getName());
                    } catch (NoSuchFieldException | SecurityException e1) {
                        LOG.debug("拷贝属性：" + field.getName() + "失败，case：源拷贝实例中没有这个属性");
                        continue;
                    }
                }
                if (formField == null) continue;
                String getMethod = getFieldMethodName(formField);
                value = getValue(form, getMethod, formField);
                if (value == null) continue;
                if (field.getType().isAssignableFrom(String.class) && value instanceof Date)
                {
                    DateFormat dateFormat = field.getAnnotation(DateFormat.class);
                    if (dateFormat != null)
                        value = DateUtil.formatDate((Date) value, dateFormat.value().format);
                    else
                        value = field.getName().lastIndexOf("Time") == field.getName().length() - 4 ? DateUtil.formatDateTime((Date) value) : DateUtil.formatDate((Date) value);
                }
                if (field.getType().isAssignableFrom(Date.class) && value instanceof String)
                {
                    DateFormat dateFormat = field.getAnnotation(DateFormat.class);
                    if (dateFormat != null) 
                        value = DateUtil.parseDate((String) value, dateFormat.value().format);
                    else
                        value = field.getName().lastIndexOf("Time") == field.getName().length() - 4 ? DateUtil.parseDateTime((String) value) : DateUtil.parseDate((String) value);
                }
            }
            invoke(field.getName(), field.getType(), value, to);
        }
    }
    
    /**
     * 
     * 
     * @param fieldName 
     * @param value 
     * @param n 
     * @see
     */
    private static void invoke(String fieldName, Class<?> type, Object value, Serializable n)
    {
        try
        {
            Method method = n.getClass().getMethod("set" + firstLetterUpperCase(fieldName), type);
            method.invoke(n, value);
        }
        catch (Exception e)
        {
            LOG.debug("拷贝属性：" + fieldName + "失败，case：" + e.getMessage());
        }
    }
    
    
    
    /**
     * 
     * 
     * @param form 实体
     * @param getMethod 方法名称
     * @param field 属性
     * @return Object
     * @see
     */
    private static Object getValue(Serializable form, String getMethod, Field field) 
    {
        try
        {
            Method method = form.getClass().getMethod(getMethod);
            return method.invoke(form);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            LOG.info("拷贝属性：{}失败，方法：{} 不存在", field.getName(), getMethod);
            return null;
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            LOG.info("拷贝属性：{}失败，message:{}", field.getName(), e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * 获取方法名称
     * @param field Field
     * @return 方法名称
     * @see
     */
    private static String getFieldMethodName(Field field) 
    {
        String getMethod = "";
        if (field.getType().isAssignableFrom(boolean.class)) 
        {
            getMethod = "is";
        } 
        else
        {
            getMethod = "get";
        }
        getMethod += firstLetterUpperCase(field.getName());
        return getMethod;
    }
    
    /**
     * 首字母大写
     * @param str 
     * @return 新字符串
     * @see
     */
    private static String firstLetterUpperCase(String str) 
    {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * 
     */
    private BeanUtil() 
    {
        
    }
}
