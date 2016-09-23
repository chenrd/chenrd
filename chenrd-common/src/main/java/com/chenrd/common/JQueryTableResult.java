/*
 * 文件名：JQueryDataTableResult.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.chenrd.example.PagingInfo;


/**
 * 
 * jquery的dataTable 格式数据返回
 * @author chenrd
 * @version 2015年5月18日
 * @see JQueryTableResult
 * @since
 */
public class JQueryTableResult implements Serializable
{
    
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -714343839160922482L;

    /**
     * 返回的数据
     */
    private List<? extends Serializable> rows;
    
    /**
     * 分页
     */
    private PagingInfo paging;
    
    /**
     * 
     */
    private int sEcho = 1;
    
    /**
     * 
     */
    private long iTotalRecords = 100;
    
    /**
     * 
     */
    private long iTotalDisplayRecords = 100;
    
    /**
     * 
     * @param list List<? extends Serializable>
     * @param paging Paging
     */
    public JQueryTableResult(List<? extends Serializable> rows, PagingInfo paging) 
    {
        this.rows = rows == null ? new ArrayList<Serializable>() : rows;
        this.paging = paging;
        this.sEcho = paging.getsEcho() == null ? 1 : paging.getsEcho();
        this.iTotalRecords = paging.getTotalRows();
        this.iTotalDisplayRecords = paging.getTotalRows();
    }

    /**
     * @return Returns the rows.
     */
    public List<? extends Serializable> getRows()
    {
        return rows;
    }

    /**
     * @param rows The rows to set.
     */
    public void setRows(List<? extends Serializable> rows)
    {
        this.rows = rows;
    }

    /**
     * @return Returns the paging.
     */
    public PagingInfo getPaging()
    {
        return paging;
    }

    /**
     * @param paging The paging to set.
     */
    public void setPaging(PagingInfo paging)
    {
        this.paging = paging;
    }

    /**
     * @return Returns the sEcho.
     */
    public int getsEcho()
    {
        return sEcho;
    }

    /**
     * @param sEcho The sEcho to set.
     */
    public void setsEcho(int sEcho)
    {
        this.sEcho = sEcho;
    }

    /**
     * @return Returns the iTotalRecords.
     */
    public long getiTotalRecords()
    {
        return iTotalRecords;
    }

    /**
     * @param iTotalRecords The iTotalRecords to set.
     */
    public void setiTotalRecords(long iTotalRecords)
    {
        this.iTotalRecords = iTotalRecords;
    }

    /**
     * @return Returns the iTotalDisplayRecords.
     */
    public long getiTotalDisplayRecords()
    {
        return iTotalDisplayRecords;
    }

    /**
     * @param iTotalDisplayRecords The iTotalDisplayRecords to set.
     */
    public void setiTotalDisplayRecords(long iTotalDisplayRecords)
    {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    
    
}
