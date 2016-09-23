/*
 * 文件名：AbstractBusiness.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.business.abs;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.chenrd.business.Business;
import com.chenrd.common.BeanCopyUtils;
import com.chenrd.common.Paging;
import com.chenrd.dao.BeanUtil;
import com.chenrd.dao.QueryParamDAO;
import com.chenrd.dao.annotation.FindConstructor;
import com.chenrd.dao.info.QueryInfo;
import com.chenrd.example.Domain;
import com.chenrd.example.Example;
import com.chenrd.example.PagingInfo;
import com.chenrd.example.Status;
import com.chenrd.example.VO;

@Transactional
public abstract class AbstractBusiness implements Business
{
    
    public abstract QueryParamDAO getQueryParamsBaseDAO();
    
    public boolean isLog()
    {
        return false;
    }
    
    /**
     * 查询方法
     * 
     * @param info
     * @param paging
     * @return 
     * @see
     */
    @SuppressWarnings("unchecked")
    public <T extends VO> List<T> find(Class<T> clazz, QueryInfo info, PagingInfo paging)
    {
        Constructor<?>[] constructors = clazz.getConstructors();
        FindConstructor findConstructor = null;
        for (Constructor<?> constructor : constructors)
            if ((findConstructor = constructor.getAnnotation(FindConstructor.class)) != null && "find".equals(findConstructor.name())) break;
        
        List<? extends Example> list = getQueryParamsBaseDAO().findPaging(findConstructor != null && "find".equals(findConstructor.name()) ? findConstructor.value() : null, info, (Paging) paging);
        if (list == null) return null;
        if (findConstructor != null && "find".equals(findConstructor.name())) return (List<T>) list;
        return BeanUtil.returnList(list, clazz);
    }
    
    /**
     * 
     * 
     * @param info
     * @return 
     * @see
     */
    @SuppressWarnings("unchecked")
    public <T extends VO> List<T> findSelect(Class<T> clazz, QueryInfo info)
    {
        Constructor<?>[] constructors = clazz.getConstructors();
        FindConstructor findConstructor = null;
        for (Constructor<?> constructor : constructors)
            if ((findConstructor = constructor.getAnnotation(FindConstructor.class)) != null && "findSelect".equals(findConstructor.name())) break;
        
        List<? extends Example> list = getQueryParamsBaseDAO().find(findConstructor != null && "findSelect".equals(findConstructor.name()) ? findConstructor.value() : null, info);
        if (list == null) return null;
        if (findConstructor != null && "findSelect".equals(findConstructor.name())) return (List<T>) list;
        return BeanUtil.returnList(list, clazz);
    }
    
    
    public void publish(Serializable id)
    {
        Domain d = getQueryParamsBaseDAO().get(getQueryParamsBaseDAO().getDomClass(), id);
        d.setStatus(Status.NO == d.getStatus() ? Status.OFF : Status.NO);
        getQueryParamsBaseDAO().update(d);
    }
    
    public void delete(Serializable id)
    {
        Domain d = getQueryParamsBaseDAO().get(getQueryParamsBaseDAO().getDomClass(), id);
        d.setStatus(Status.DELETED);
        getQueryParamsBaseDAO().update(d);
    }
    
    public <T extends VO> T get(Serializable id, Class<T> clazz)
    {
        Domain d = getQueryParamsBaseDAO().get(getQueryParamsBaseDAO().getDomClass(), id);
        if (d == null) {
            return null;
        }
        return BeanCopyUtils.copy(d, clazz, false);
    }
}
