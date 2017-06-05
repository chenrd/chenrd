/*
 * 文件名：BaseDAO.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.chenrd.common.Paging;
import com.chenrd.example.Domain;
import com.chenrd.example.Example;
/**
 * 
 * @author chenrd
 * @version 2015年5月15日
 * @param <D>
 * @see BaseDAO
 * @since
 */
public interface BaseDAO extends DAO {
	
	List<? extends Example> find(String hql, Map<String, Serializable> params);
	
	List<? extends Example> findPaging(String hql, String countHql, Map<String, Serializable> params, Paging paging);
    
	
	<T extends Domain> List<T> findByProperty(Class<T> clazz, String paramName, Serializable paramValue);
	
	<T extends Domain> List<T> findByProperty(Class<T> clazz, String paramName, Serializable paramValue, String orderStr, String descStr);
    /**
     * 
     * @return 
     * @see
     */
    <T extends Domain> List<T> findByPropertys(Class<T> clazz, String[] paramNames, Object[] paramValues);
    
    <T extends Domain> List<T> findByPropertys(Class<T> clazz, String[] paramNames, Object[] paramValues, String orderStr, String descStr);
    /**
     * 
     * @return 
     * @see
     */
    <T extends Domain> Long countByProperty(Class<T> clazz, String paramName, Object paramValue);
    
    /**
     * @return 
     * @see
     */
    <T extends Domain> Long countByPropertys(Class<T> clazz, String[] paramNames, Object[] paramValues);
    
    /**
     * 
     * 用queryName的方式查询
     * @param <T> 
     * @param queryName 查询NAME
     * @param params 参数
     * @param paging 分页条件
     * @return List<T>
     * @see
     */
    <T extends Serializable> List<T> findQueryNamePaging(String queryName, Map<String, Serializable> params, Paging paging);
    
    /**
     * 
     * 用queryName的方式查询
     * @param <T> 
     * @param queryName 查询NAME
     * @param params 参数
     * @return List<T>
     * @see
     */
    <T extends Serializable> List<T> findQueryName(String queryName, Map<String, Serializable> params);
    
    Long countQueryName(String queryName, Map<String, Serializable> params);
    
    
    /**
     * 
     * 通过ID获取数据
     * @param clazz 查询的实体类
     * @param t  Domain子类
     * @param <T>  Domain子类
     * @return T
     * @see
     */
    <T extends Domain> T get(Class<T> clazz, Serializable t);
    
    /**
     * 
     * 通过ID获取数据
     * @param clazz 查询的实体类
     * @param t  Domain子类
     * @param <T>  Domain子类
     * @return T
     * @see
     */
    <T extends Domain> T load(Class<T> clazz, Serializable t);
    
    
    /**
     * 
     * 通过属性获取对象
     * @param <T> 
     * @param t 实体类
     * @param name 属性名称
     * @param value 属性值
     * @return T
     * @see
     */
    <T extends Domain> T getByProperties(Class<T> t, String name, Object value);
    
    /**
     * 
     * 
     * @param t
     * @param names
     * @param values
     * @return 
     * @see
     */
    <T extends Domain> T getByProperties(Class<T> t, String[] names, Object[] values);
    
    /**
     * 
     * 
     * @param hql
     * @param params
     * @return 
     * @see
     */
    Object queryObject(String hql, Map<String, Serializable> params);
    
    /**
     * 
     * 
     * @param hql
     * @param params
     * @return 
     * @see
     */
    List<Object[]> querListObjects(String hql, Map<String, Serializable> params);
    
    <T extends Serializable> List<T> findSql(String sql, Map<String, Serializable> params);
    
    /**
     * 
     * 
     * @param hql
     * @param params 
     * @see
     */
    int executeUpdate(String hql, Map<String, Serializable> params);
    
    /**
     * 
     * 
     * @param hql
     * @param params 
     * @see
     */
    int executeSQLUpdate(String hql, Map<String, Serializable> params);
    
    /**
     * 
     * @param qeuryName
     * @param params
     * @return 
     * @see
     */
    int executeQueryName(String queryName, Map<String, Serializable> params);
    
    /**
     * 
     * @param qeuryName
     * @param params
     * @return 
     * @see
     */
    int executeQueryNameArray(String queryName, Serializable... params);

}
