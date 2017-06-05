/*
 * 文件名：Copy.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年6月8日
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

/**
 * 拷贝属性时用到
 * @author chenrd
 * @version 2015年6月5日
 * @see XlsImport
 * @since
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Copy {
    /**
     * 
     * 
     * @return 
     * @see
     */
    String value();
}
