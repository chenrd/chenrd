/*
 * 文件名：FindConstructor.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface FindConstructor {
    /**
     * 对应方法名称
     * 
     * 两种，fangSelect,find 
     * 不分页，及分页
     * 
     * @return 
     * @see
     */
    String name();
    
    /**
     * 对应select语句
     * 
     * @return 
     * @see
     */
    String value();
}
