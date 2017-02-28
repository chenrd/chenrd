/*
 * 文件名：HqlAttribute.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.info;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.chenrd.common.ocp.DateFormat;
import com.chenrd.dao.annotation.QueryParams;

public class HqlAttribute implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -4064894384599794720L;
    
    private String fieldName;
    
    private QueryParams params;
    
    private Method getMethod;
    
    private String hqlSection;
    
    private DateFormat dateFormat;
    
    
    /**
     * @param fieldName
     * @param params
     */
    public HqlAttribute(String fieldName, QueryParams params)
    {
        super();
        this.fieldName = fieldName;
        this.params = params;
        if (!params.hql().equals("")) {
        	this.fieldName = params.hql();
        }
    }

    /**
     * @param fieldName
     * @param params
     */
    public HqlAttribute(String fieldName, QueryParams params, DateFormat dateFormat)
    {
        super();
        this.fieldName = fieldName;
        this.params = params;
        this.dateFormat = dateFormat;
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
     * @return Returns the params.
     */
    public QueryParams getParams()
    {
        return params;
    }

    /**
     * @param params The params to set.
     */
    public void setParams(QueryParams params)
    {
        this.params = params;
    }

    /**
     * @return Returns the getMethod.
     */
    public Method getGetMethod()
    {
        return getMethod;
    }

    /**
     * @param getMethod The getMethod to set.
     */
    public void setGetMethod(Method getMethod)
    {
        this.getMethod = getMethod;
    }

    /**
     * @return Returns the hqlSection.
     */
    public String getHqlSection()
    {
        return hqlSection;
    }

    /**
     * @param hqlSection The hqlSection to set.
     */
    public void setHqlSection(String hqlSection)
    {
        this.hqlSection = hqlSection;
    }

    /**
     * @return Returns the dateFormat.
     */
    public DateFormat getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @param dateFormat The dateFormat to set.
     */
    public void setDateFormat(DateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
    }
    
}
