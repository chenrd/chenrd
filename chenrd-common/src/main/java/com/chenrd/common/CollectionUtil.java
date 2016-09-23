/*
 * 文件名：CollectionUtil.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2015年11月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 集合帮组类
 * 
 * @author chenrd
 * @version 2015年11月4日
 * @see CollectionUtil
 * @since
 */
public abstract class CollectionUtil
{
    
    public static <T> void sort(List<T> ls, final Field field)
    {
        field.setAccessible(true);
        Comparator<T> comparator = new Comparator<T>()
        {
            @Override
            public int compare(T o1, T o2)
            {
                try
                {
                    if (new BigDecimal(field.get(o1) + "").compareTo(new BigDecimal(field.get(o1) + "")) > 0)
                    {  
                        return 1;  
                    }
                    else if (new BigDecimal(field.get(o1) + "").compareTo(new BigDecimal(field.get(o1) + ""))< 0)
                    {  
                        return -1;  
                    }
                }
                catch (IllegalArgumentException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        field.setAccessible(false);
      //开始排序
        Collections.sort(ls, comparator);
    }
}
