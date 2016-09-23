/*
 * 文件名：ColumsOptionUtil.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年4月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.columnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.chenrd.common.xls.XlsPortAnnotate;

/**
 * 
 * @author chenrd
 * @version 2016年4月20日
 * @see ColumsOptionUtil
 * @since
 */
public final class ColumsOptionUtil
{
    
    private ColumsOptionUtil()
    {
        
    }
    
    /**
     * 
     * 
     * @param clazz 
     * @param options 被选中过的字段
     * @return 
     * @see
     */
    public static List<ColumnOption> selectOptions(Class<?> clazz, String options)
    {
        options = "," + options + ",";
        Field[] fields = clazz.getDeclaredFields();
        XlsPortAnnotate imAndExport = null;
        
        List<ColumnOption> list = new ArrayList<ColumnOption>();
        ColumnOption json = null;
        for (Field field : fields)
        {
            imAndExport = field.getAnnotation(XlsPortAnnotate.class);
            if (imAndExport == null) continue;
            json = new ColumnOption(field.getName(), imAndExport.value(), (options.indexOf("," + field.getName() + ",") != -1 ? true : (imAndExport.columnOptions().length > 0 ? imAndExport.columnOptions()[0].checked() : false)));
            list.add(json);
        }
        return list;
    }
}
