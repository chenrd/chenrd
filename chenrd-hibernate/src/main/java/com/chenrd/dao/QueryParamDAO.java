/*
 * 文件名：QueryParamDAO.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao;

import java.util.List;

import com.chenrd.common.Paging;
import com.chenrd.dao.info.QueryInfo;
import com.chenrd.example.Domain;
import com.chenrd.example.Example;

public interface QueryParamDAO extends BaseDAO
{
    /**
     * 
     * 
     * @param info
     * @param paging
     * @return 
     * @see
     */
    <T extends Example> List<T> findPaging(String sql, QueryInfo info, Paging paging);
    
    /**
     * 
     * @param clazz
     * @param info
     * @return 
     * @see
     */
    <T extends Example> List<T> find(String sql, QueryInfo info);
    
    Class<? extends Domain> getDomClass();
}
