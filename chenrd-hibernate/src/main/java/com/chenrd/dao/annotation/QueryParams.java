/*
 * 文件名：QueryParams.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
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

import com.chenrd.dao.em.Nexus;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParams {
    
    String name() default ""; //VO中可能用不同的名称
    
    String value() default "and"; //关系 
    
    String hql() default "";
    
    /**
     * 添加括号
     * 
     * @return 
     * @see
     */
    String bracket() default "";
    
    Nexus nexus() default Nexus.EQUAL;
    
    boolean defaultNotQuery() default false;
    
    double defaultNotQueryValue() default 0.0;
}
