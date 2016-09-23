/*
 * 文件名：QueryParamsBaseDAO.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.abs;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.chenrd.common.Paging;
import com.chenrd.dao.QueryParamDAO;
import com.chenrd.dao.annotation.QueryParams;
import com.chenrd.dao.em.Nexus;
import com.chenrd.dao.info.QueryInfo;
import com.chenrd.example.Domain;
import com.chenrd.example.Example;

/**
 * 
 * @author chenrd
 * @version 2016年5月18日
 * @see QueryParamsBaseDAO
 * @since
 */
public abstract class QueryParamsBaseDAO<D extends Domain> extends AbstractBaseDAO implements QueryParamDAO
{
    
    public static final String AND = "and", OR = "or", COLON = ":", countSqlPrefix = "select count(*) ";
    
    public abstract Class<D> getDomClass();
    /**
     * 
     * @param paging
     * @param params
     * @return 
     * @see
     */
    @SuppressWarnings("unchecked")
    public <T extends Example> List<T> findPaging(String sql, QueryInfo info, Paging paging)
    {
        StringBuffer hql = new StringBuffer(" from ").append(getDomClass().getSimpleName()).append( " as po where 1=1");
        Map<String, Serializable> params = null;
        if (info != null)
        {
            params = new HashMap<String, Serializable>();
            Field[] fields = info.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                QueryParams queryParams = field.getAnnotation(QueryParams.class);
                if (queryParams == null) continue;
                Serializable val = (Serializable) info.get(field.getName());
                if (val == null || (val instanceof String && !StringUtils.isNotBlank((String) val))) continue;
                
                hql.append(" ").append(queryParams.value()).append(queryParams.bracket()).append(" po.").append(field.getName()).append(queryParams.nexus().sign).append(COLON).append(field.getName());
                if (queryParams.nexus() == Nexus.LIKE) params.put(field.getName(), val + "%");
                else params.put(field.getName(), val);
            }
        }
        String countHql = countSqlPrefix + hql.toString();
        if (StringUtils.isNotBlank(sql)) hql.insert(0, sql);
        return (List<T>) super.findPaging(hql.toString(), countHql, params, paging);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Example> List<T> find(String sql, QueryInfo info)
    {
        StringBuffer hql = new StringBuffer(sql == null ? "" : sql).append(" from ").append(getDomClass().getSimpleName()).append( " as po where 1=1");
        Map<String, Serializable> params = null;
        if (info != null)
        {
            params = new HashMap<String, Serializable>();
            Field[] fields = info.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                QueryParams queryParams = field.getAnnotation(QueryParams.class);
                if (queryParams == null) continue;
                Serializable val = (Serializable) info.get(field.getName());
                if (val == null || (val instanceof String && !StringUtils.isNotBlank((String) val))) continue;
                
                hql.append(" ").append(queryParams.value()).append(queryParams.bracket()).append(" po.").append(field.getName()).append(queryParams.nexus().sign).append(COLON).append(field.getName());
                if (queryParams.nexus() == Nexus.LIKE) params.put(field.getName(), val + "%");
                else params.put(field.getName(), val);
            }
        }
        return (List<T>) super.find(hql.toString(), params);
    }
    
}
