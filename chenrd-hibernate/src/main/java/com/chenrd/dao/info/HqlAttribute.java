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

/**
 * 在PowerScan类被构建的时候，会去检索所有的@Entity实体，如果实体中的每一个成员变量拥有@QueryParam注解或者@LimitFieldPower注解，那么会生成一个这个对象
 * 这个类用来构建hql的时候，限定的查询条件属性
 * 
 * @author chenrd
 * @version 2017年4月10日
 * @see HqlAttribute
 * @since
 */
public class HqlAttribute implements Serializable {

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -4064894384599794720L;
    
    private String fieldName; // 成员变量的名称
    
    private QueryParams params; // 注解
    
    private Method getMethod; //这个缓存，一开始是没有的，第一次查询之后有这个方法缓存起来，一个非常重要的//缓存了这个方法之后，查询的QueryInfo类必须相同
    
    private boolean cachehqlSection = true; //是否缓存hql片段 看下面的注释
    
    private String hqlSection; //也是缓存，第一次查询之后缓存起来，这个要添加一些不缓存的情况。目前有两种情况：成员添加注解@LimitFieldPower，或者@QueryParam.nexus = Nexus.IN
    
    private DateFormat dateFormat;
    
    
    
    /**
     * @param fieldName
     * @param params
     */
    public HqlAttribute(String fieldName, boolean cachehqlSection, QueryParams params) {
        super();
        this.fieldName = fieldName;
        this.cachehqlSection = cachehqlSection;
        this.params = params;
        if (!params.hql().equals("")) {
        	this.fieldName = params.hql();
        }
    }

    /**
     * @param fieldName
     * @param params
     */
    public HqlAttribute(String fieldName, QueryParams params, DateFormat dateFormat) {
        super();
        this.fieldName = fieldName;
        this.params = params;
        this.dateFormat = dateFormat;
    }

    /**
     * @return Returns the fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName The fieldName to set.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return Returns the params.
     */
    public QueryParams getParams() {
        return params;
    }

    /**
     * @param params The params to set.
     */
    public void setParams(QueryParams params) {
        this.params = params;
    }

    /**
     * @return Returns the getMethod.
     */
    public Method getGetMethod() {
        return getMethod;
    }

    /**
     * @param getMethod The getMethod to set.
     */
    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    /**
     * @return Returns the hqlSection.
     */
    public String getHqlSection() {
        return hqlSection;
    }

    /**
     * @param hqlSection The hqlSection to set.
     */
    public void setHqlSection(String hqlSection) {
        this.hqlSection = hqlSection;
    }

    /**
     * @return Returns the dateFormat.
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormat The dateFormat to set.
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

	/**
	 * @return Returns the cachehqlSection.
	 */
	public boolean isCachehqlSection() {
		return cachehqlSection;
	}

	/**
	 * @param cachehqlSection The cachehqlSection to set.
	 */
	public void setCachehqlSection(boolean cachehqlSection) {
		this.cachehqlSection = cachehqlSection;
	}
    
}
