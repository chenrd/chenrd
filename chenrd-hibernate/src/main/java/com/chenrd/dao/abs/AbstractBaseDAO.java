/*
 * 文件名：AbstractBasicDAO.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年1月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.abs;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chenrd.common.Paging;
import com.chenrd.dao.BaseDAO;
import com.chenrd.example.Domain;
import com.chenrd.example.Example;

/**
 * 
 * @author chenrd
 * @version 2016年1月19日
 * @see AbstractBaseDAO
 * @since
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseDAO implements BaseDAO
{
    
    /**
     * 
     */
    private SessionFactory sessionFactory;
    
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    
    public SessionFactory getSessionFactory()
    {
        return this.sessionFactory;
    }
    
    @Override
    public List<? extends Example> find(String hql, Map<String, Serializable> params) 
    {
        Query query = createQuery(hql, params);
        return query.list();
    }
    
    @Override
    public <T extends Domain> List<T> findByProperty(Class<T> clazz, String paramName, Serializable paramValue, String orderName)
    {
        HashMap<String, Serializable> params = new HashMap<String, Serializable>();
        StringBuilder hql = new StringBuilder("from ").append(clazz.getSimpleName()).append(" as po where 1=1").append(" and po.").append(paramName).append("=:").append(paramName);
        params.put(paramName, paramValue);
        if (StringUtils.isNotBlank(orderName))
        {
            hql.append(" order by po.").append(orderName);
        }
        Query query = createQuery(hql.toString(), params);
        return query.list();
    }

    @Override
    public <T extends Domain> List<T> findByProperty(Class<T> clazz, String paramName, Serializable paramValue, String orderName, String descName)
    {
        HashMap<String, Serializable> params = new HashMap<String, Serializable>();
        StringBuilder hql = new StringBuilder("from ").append(clazz.getSimpleName()).append(" as po where 1=1").append(" and po.").append(paramName).append("=:").append(paramName);
        params.put(paramName, paramValue);
        if (StringUtils.isNotBlank(orderName))
        {
            hql.append(" order by po.").append(orderName).append(" ").append(descName);
        }
        Query query = createQuery(hql.toString(), params);
        return query.list();
    }

    @Override
    public <T extends Domain> List<T> findByProperty(Class<T> clazz, String[] paramNames, Object[] paramValues, String orderName, String descName)
    {
        HashMap<String, Serializable> params = new HashMap<String, Serializable>();
        StringBuilder hql = new StringBuilder("from ").append(clazz.getSimpleName()).append(" as po where 1=1");
        for (int i = 0; i < paramNames.length; i++)
        {
            hql.append(" and po.").append(paramNames[i]).append("=:").append(paramNames[i]);
            params.put(paramNames[i], (Serializable) paramValues[i]);
        }
        if (StringUtils.isNotBlank(orderName))
        {
            hql.append(" order by po.").append(orderName).append(" ").append(descName);
        }
        Query query = createQuery(hql.toString(), params);
        return query.list();
    }
    
    @Override
    public Long count(String hql, Map<String, Serializable> params)
    {
        return executeCount(hql, params);
    }

    @Override
    public <T extends Domain> Long countByProperty(Class<T> clazz, String paramName, Object paramValue)
    {
        String hql = "select count(*) from " + clazz.getSimpleName() + " as po where po." + paramName + "=:" + paramName;
        Query query = createQuery(hql, paramName, paramValue);
        return (Long) query.list().get(0);
    }

    @Override
    public <T extends Domain> Long countByProperty(Class<T> clazz, String[] paramNames, Object[] paramValues)
    {
        HashMap<String, Serializable> params = new HashMap<String, Serializable>();
        StringBuilder hql = new StringBuilder("from ").append(clazz.getSimpleName()).append(" as po where 1=1").insert(0, "select count(*) ");
        for (int i = 0; i < paramNames.length; i++)
        {
            hql.append(" and po.").append(paramNames[i]).append("=:").append(paramNames[i]);
            params.put(paramNames[i], (Serializable) paramValues[i]);
        }
        Query query = createQuery(hql.toString(), params);
        return (Long) query.list().get(0);
    }
    
    @Override
    public Object queryObject(String hql, Map<String, Serializable> params)
    {
        Query query = createQuery(hql, params);
        List<Object> list = query.list();
        return list != null && list.size() > 0 ? list.get(0) : 0;
    }

    @Override
    public <T extends Domain> List<T> findByParams(Class<T> clazz, Map<String, Serializable> params)
    {
        StringBuilder hql = new StringBuilder("from ").append(clazz.getSimpleName()).append(" as po where 1=1");
        if (params != null)
        {
            for (String key : params.keySet())
            {
                hql.append(" and po.").append(key).append("=:").append(key);
            }
        }
        return (List<T>) find(hql.toString(), params);
    }
    
    @Override
    public <T extends Serializable> List<T> findQueryNamePaging(String queryName, Map<String, Serializable> params, Paging paging)
    {
        String hql = sessionFactory.getCurrentSession().getNamedQuery(queryName).getQueryString();
        return (List<T>) findPaging(hql, "select count(*) " + hql.substring(hql.indexOf("from")), params, paging);
    }

    @Override
    public <T extends Serializable> List<T> findQueryName(String queryName, Map<String, Serializable> params)
    {
        Query qeury = getSession().getNamedQuery(queryName);
        qeury.setProperties(params);
        return qeury.list();
    }

    @Override
    public <T extends Domain> void saveOrUpdate(T bean)
    {
        sessionFactory.getCurrentSession().saveOrUpdate(bean);
    }
    
    @Override
    public <T extends Domain> void save(T bean)
    {
        sessionFactory.getCurrentSession().save(bean);
    }

    @Override
    public <T extends Domain> void update(T bean)
    {
        sessionFactory.getCurrentSession().update(bean);
    }

    @Override
    public <T extends Serializable> void delete(T t)
    {
        sessionFactory.getCurrentSession().delete(t);
    }

    @Override
    public <T extends Domain> T get(Class<T> clazz, Serializable t)
    {
        return (T) sessionFactory.getCurrentSession().get(clazz, t);
    }

    @Override
    public <T extends Domain> T load(Class<T> clazz, Serializable t)
    {
        return (T) sessionFactory.getCurrentSession().load(clazz, t);
    }

    @Override
    public <T extends Domain> T getByProperties(Class<T> t, String name, Object value)
    {
        String hql = "from " + t.getSimpleName() + " as po where po." + name + "=:" + name;
        Query query = createQuery(hql, name, value);
        List<T> list = query.list();
        return list != null && list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public <T extends Domain> T getByProperties(Class<T> t, String[] names, Object[] values)
    {
        Map<String, Serializable> params = new HashMap<String, Serializable>();
        StringBuilder hql = new StringBuilder("from ").append(t.getSimpleName()).append(" as po where 1=1");
        for (int i = 0; i < names.length;i++)
        {
            hql.append(" and po.").append(names[i]).append("=:").append(names[i].replaceAll("\\.", ""));
            params.put(names[i].replaceAll("\\.", ""), (Serializable) values[i]);
        }
        
        Query query = createQuery(hql.toString(), params);
        List<T> list = query.list();
        return list != null && list.size() == 1 ? list.get(0) : null;
    }
    
    public List<? extends Example> findPaging(String hql, String countHql, Map<String, Serializable> params, Paging paging)
    {
        paging.setTotalRows(executeCount(countHql, params));
        return executeFind(hql, params, paging);
    }
    
    /**
     * 
     * 查询总条数
     * @param hql HQL
     * @param params 参数
     * @return Long
     * @see
     */
    protected long executeCount(String hql, Map<String, Serializable> params) 
    {
        Query query = createQuery(hql, params);
        List<Long> list = query.list();
        return list != null && list.size() > 0 && list.get(0) != null ? list.get(0) : 0;
    }
    
    /**
     * 
     * 执行查询
     * @param hql HQL
     * @param params 参数集合
     * @param paging 分页条件
     * @return 结果集
     * @see
     */
    public List<? extends Domain> executeFind(String hql, Map<String, Serializable> params, Paging paging) 
    {
        Query query = createQuery(hql, params);
        query.setFirstResult((paging.getCurrent() - 1) * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());
        return query.list();
    }
    
    public <T extends Serializable> List<T> findSql(String sql, Map<String, Serializable> params)
    {
        Query query = getSession().createSQLQuery(sql);
        query.setProperties(params);
        return query.list();
    }
    
    /**
     * 
     * 
     * @param hql
     * @param params
     * @return 
     * @see
     */
    @Override
    public List<Object[]> querListObjects(String hql, Map<String, Serializable> params)
    {
        Query query = createQuery(hql, params);
        return query.list();
    }
    
    protected Query createQuery(String hql, Map<String, Serializable> params)
    {
        Query query = getSession().createQuery(hql);
        if (params != null && params.size() > 0) query.setProperties(params);
        return query;
    }
    
    protected Query createQuery(String hql, String name, Object value)
    {
        Query query = getSession().createQuery(hql);
        query.setParameter(name, value);
        return query;
    }
    
    /**
     * 
     * 
     * @param hql
     * @param params 
     * @see
     */
    @Override
    public int executeUpdate(String hql, Map<String, Serializable> params)
    {
        Query query = getSession().createQuery(hql);
        query.setProperties(params);
        return query.executeUpdate();
    }
    
    /**
     * 
     * 
     * @param hql
     * @param params 
     * @see
     */
    @Override
    public int executeSQLUpdate(String sql, Map<String, Serializable> params)
    {
        Query query = getSession().createSQLQuery(sql);
        query.setProperties(params);
        return query.executeUpdate();
    }
    
    @Override
    public int executeQueryName(String queryName, Map<String, Serializable> params)
    {
        Query query = getSession().getNamedQuery(queryName);
        query.setProperties(params);
        return query.executeUpdate();
    }
    
    @Override
    public int executeQueryNameArray(String queryName, Serializable... params)
    {
        Query query = getSession().getNamedQuery(queryName);
        int i = 0;
        if (params != null)
        {
            for (Serializable serializable : params)
            {
                if (serializable instanceof String) query.setString(i++, (String) serializable);
                else if (serializable instanceof Integer || serializable.getClass().isAssignableFrom(int.class)) query.setInteger(i++, (Integer) serializable);
                else if (serializable instanceof Long || serializable.getClass().isAssignableFrom(long.class)) query.setLong(i++, (Long) serializable);
                else if (serializable instanceof Float || serializable.getClass().isAssignableFrom(float.class)) query.setFloat(i++, (Float) serializable);
                else if (serializable instanceof Double || serializable.getClass().isAssignableFrom(double.class)) query.setDouble(i++, (Double) serializable);
                else if (serializable instanceof Date) query.setDate(i++, (Date) serializable);
                else if (serializable instanceof Boolean || serializable.getClass().isAssignableFrom(boolean.class)) query.setBoolean(i++, (Boolean) serializable);
            }
        }
        return query.executeUpdate();
    }
    
    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
    
}
