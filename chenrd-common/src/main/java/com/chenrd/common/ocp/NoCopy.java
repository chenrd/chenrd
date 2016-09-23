/*
 * 文件名：PerNoCopy.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.ocp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拷贝的时候分，永久不拷贝、当前不拷贝
 * 这个是标注永久不拷贝
 * 
 * @author chenrd
 * @version 2016年7月6日
 * @see NoCopy
 * @since
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoCopy {

    /**
     * 默认为永久不拷贝
     * 
     * @return 
     * @see
     */
    NoCopyType value() default NoCopyType.Per;
    
    public enum NoCopyType
    {
        Per, Current,
    }
}
