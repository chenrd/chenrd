/*
 * 文件名：DateFormat.java
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

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    
    Format value() default Format.yMd;
    
    public enum Format {
        yMd("yyyy-MM-dd"), yMdHm("yyyy-MM-dd HH:mm"), yMdHms("yyyy-MM-dd HH:mm:ss");
        public String format;
        
        Format(String format)
        {
            this.format = format;
        }
    }
}
