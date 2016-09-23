/*
 * 文件名：PagingInfo.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2015年11月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.example;

import java.io.Serializable;

/**
 * 
 * 
 * 
 * @author chenrd
 * @version 2015年11月5日
 * @see PagingInfo
 * @since
 */
public class PagingInfo implements Serializable
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -6274032827880059725L;
    
    /**
     * 当前页
     */
    private int current = 1;
    
    /**
     * 每页显示
     */
    private int pageSize = 20;
    
    /**
     * 总行数
     */
    private long totalRows;
    
    /**
     * 总页数
     */
    private int totalPages;
    
    /**
     * 上一页
     */
    private boolean prePage;
    
    /**
     * 下一页
     */
    private boolean nextPage;
    
    /**
     * 
     */
    private Integer sEcho;
    
    private int iDisplayLength;
    
    private int iDisplayStart;
    

    /**
     * @param current
     * @param pageSize
     */
    public PagingInfo(int current, int pageSize)
    {
        super();
        this.current = current;
        this.pageSize = pageSize;
    }

    /**
     * 
     */
    public PagingInfo()
    {
        super();
    }

    /**
     * @return Returns the current.
     */
    public int getCurrent()
    {
        return current;
    }

    /**
     * @param current The current to set.
     */
    public void setCurrent(int current)
    {
        this.current = current;
    }

    /**
     * @return Returns the pageSize.
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize The pageSize to set.
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return Returns the totalRows.
     */
    public long getTotalRows()
    {
        return totalRows;
    }

    /**
     * @param totalRows The totalRows to set.
     */
    public void setTotalRows(long totalRows)
    {
        this.totalRows = totalRows;
    }

    /**
     * @return Returns the totalPages.
     */
    public int getTotalPages()
    {
        return totalPages;
    }

    /**
     * @param totalPages The totalPages to set.
     */
    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }

    /**
     * @return Returns the prePage.
     */
    public boolean isPrePage()
    {
        return prePage;
    }

    /**
     * @param prePage The prePage to set.
     */
    public void setPrePage(boolean prePage)
    {
        this.prePage = prePage;
    }

    /**
     * @return Returns the nextPage.
     */
    public boolean isNextPage()
    {
        return nextPage;
    }

    /**
     * @param nextPage The nextPage to set.
     */
    public void setNextPage(boolean nextPage)
    {
        this.nextPage = nextPage;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    /**
     * @return Returns the sEcho.
     */
    public Integer getsEcho()
    {
        return sEcho;
    }

    /**
     * @param sEcho The sEcho to set.
     */
    public void setsEcho(Integer sEcho)
    {
        this.sEcho = sEcho;
    }

    /**
     * @return Returns the iDisplayLength.
     */
    public int getiDisplayLength()
    {
        return iDisplayLength;
    }

    /**
     * @param iDisplayLength The iDisplayLength to set.
     */
    public void setiDisplayLength(int iDisplayLength)
    {
        this.iDisplayLength = iDisplayLength;
        this.pageSize = iDisplayLength;
    }

    /**
     * @return Returns the iDisplayStart.
     */
    public int getiDisplayStart()
    {
        return iDisplayStart;
    }

    /**
     * @param iDisplayStart The iDisplayStart to set.
     */
    public void setiDisplayStart(int iDisplayStart)
    {
        this.iDisplayStart = iDisplayStart;
        this.current = (this.iDisplayStart / this.pageSize) + 1;
    }
}
