/*
 * 文件名：DAO.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年9月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao;

import java.io.Serializable;
import java.util.Map;

import com.chenrd.example.Domain;

public interface DAO {
    
    /**
     * 
     * 
     * @param hql
     * @param params
     * @return 
     * @see
     */
    Long count(String hql, Map<String, Serializable> params);
    
    Object get(Serializable id);
    
    Object load(Serializable id);
    
    /**
     * 
     * 保存或更新
     * @param <T>  Domain子类
     * @param bean 单个对象
     * @see
     */
    <T extends Domain> void saveOrUpdate(T bean);
    
    /**
     * 
     * 保存或更新
     * @param <T>  Domain子类
     * @param bean 单个对象
     * @see
     */
    <T extends Domain> void save(T bean);
    
    /**
     * 
     * 更新
     * @param <T>  Domain子类
     * @param bean 单个对象
     * @see
     */
    <T extends Domain> void update(T bean);
    
    /**
     * 
     * 通过id 或者 实体对象删除
     * @param <T>  Domain子类
     * @param t Object
     * @see
     */
    <T extends Serializable> void delete(T t);
    
    Class<? extends Domain> getDomClass();
}
