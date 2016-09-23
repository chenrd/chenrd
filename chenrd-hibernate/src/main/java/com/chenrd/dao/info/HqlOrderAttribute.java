/*
 * 文件名：HqlOrderAttribute.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.info;

import java.io.Serializable;

public class HqlOrderAttribute implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 8742344316296041245L;
    
    private String fieldName;
    
    private int index;
    
    private String order;

    /**
     * @param fieldName
     * @param index
     * @param order
     */
    public HqlOrderAttribute(String fieldName, int index, String order)
    {
        super();
        this.fieldName = fieldName;
        this.index = index;
        this.order = order;
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
     * @return Returns the index.
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @param index The index to set.
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * @return Returns the order.
     */
    public String getOrder()
    {
        return order;
    }

    /**
     * @param order The order to set.
     */
    public void setOrder(String order)
    {
        this.order = order;
    }
}
