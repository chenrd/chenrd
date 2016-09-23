/*
 * 文件名：ColumnOption.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年4月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.columnOption;

public class ColumnOption
{
    
    private String fieldName;
    
    private String fieldDes;
    
    private boolean checked;

    
    /**
     * 
     */
    public ColumnOption()
    {
        super();
    }

    /**
     * @param fieldName
     * @param fieldDes
     * @param checked
     */
    public ColumnOption(String fieldName, String fieldDes, boolean checked)
    {
        super();
        this.fieldName = fieldName;
        this.fieldDes = fieldDes;
        this.checked = checked;
    }

    /**
     * @return Returns the fieldName.
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * @param fieldName The fieldName to set.
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * @return Returns the fieldDes.
     */
    public String getFieldDes()
    {
        return fieldDes;
    }

    /**
     * @param fieldDes The fieldDes to set.
     */
    public void setFieldDes(String fieldDes)
    {
        this.fieldDes = fieldDes;
    }

    /**
     * @return Returns the checked.
     */
    public boolean isChecked()
    {
        return checked;
    }

    /**
     * @param checked The checked to set.
     */
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
}
