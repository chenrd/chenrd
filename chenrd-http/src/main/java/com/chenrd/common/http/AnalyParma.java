/*
 * 文件名：AnalyParma.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

public class AnalyParma
{

    private String[] analys;
    
    /**
     * 从指定位置开始查询
     */
    private String queryName;
    
    /**
     * 过滤某些字段
     */
    private String filter;
    
    /**
     * 解析时返回的数据是否有字段名
     * php返回时经常是没有字段名的
     * 0:有字段名称
     * 1:无
     */
    private int type;
    
    /**
     * 发生数据重复时是否统计成一条数据
     * 0:不合计
     * 1:合计
     */
    private int total;
    
    /**
     * 过滤标签
     * 过滤返回的HTML中所有<span,div等开头的标签
     */
    private String filterLable;
    
    /**
     * 分页过滤
     * 较少用到
     * 例：乐米2
     * 
     */
    private String filterPage;


    /**
     * @return Returns the analys.
     */
    public String[] getAnalys()
    {
        return analys;
    }

    /**
     * @param analys The analys to set.
     */
    public void setAnalys(String[] analys)
    {
        this.analys = analys;
    }

    /**
     * @return Returns the queryName.
     */
    public String getQueryName()
    {
        return queryName;
    }

    /**
     * @param queryName The queryName to set.
     */
    public void setQueryName(String queryName)
    {
        this.queryName = queryName;
    }

    /**
     * @return Returns the filter.
     */
    public String getFilter()
    {
        return filter;
    }

    /**
     * @param filter The filter to set.
     */
    public void setFilter(String filter)
    {
        this.filter = filter;
    }

    /**
     * @return Returns the type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return Returns the total.
     */
    public int getTotal()
    {
        return total;
    }

    /**
     * @param total The total to set.
     */
    public void setTotal(int total)
    {
        this.total = total;
    }

    /**
     * @return Returns the filterLable.
     */
    public String getFilterLable()
    {
        return filterLable;
    }

    /**
     * @param filterLable The filterLable to set.
     */
    public void setFilterLable(String filterLable)
    {
        this.filterLable = filterLable;
    }

    /**
     * @return Returns the filterPage.
     */
    public String getFilterPage()
    {
        return filterPage;
    }

    /**
     * @param filterPage The filterPage to set.
     */
    public void setFilterPage(String filterPage)
    {
        this.filterPage = filterPage;
    }
    
    

}
