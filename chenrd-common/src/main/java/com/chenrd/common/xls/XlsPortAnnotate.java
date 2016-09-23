/*
 * 文件名：XlsPortAnnotate.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.xls;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导入导出注解
 * 
 * @author chenrd
 * @version 2016年6月8日
 * @see XlsPortAnnotate
 * @since
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XlsPortAnnotate
{
    
    /**
     * 表格头名称
     */
    String value();
    /**
     * 单元格宽度
     */
    int width() default 100;
    
    /**
     * 列是否导入
     */
    boolean isImport() default true;
    
    /**
     * 列是否导出
     */
    boolean isExport() default true;
    
    ColumnsOptionAnnotate[] columnOptions() default {};
}
