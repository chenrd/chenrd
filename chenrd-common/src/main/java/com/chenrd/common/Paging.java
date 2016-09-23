/*
 * 文件名：PageBean.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import com.chenrd.example.PagingInfo;

/**
 * 分页对象
 * @author chenrd
 * @version 2015年5月13日
 * @see Paging
 * @since
 */
public class Paging extends PagingInfo
{
    
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 4510629513641865755L;
    
    public Paging()
    {
        
    }

    public Paging(PagingInfo info)
    {
        this.setCurrent(info.getCurrent());
        this.setPageSize(info.getPageSize());
        this.setTotalRows(info.getTotalRows());
        this.setTotalPages(info.getTotalPages());
        this.setsEcho(info.getsEcho());
        this.setiDisplayLength(info.getiDisplayLength());
        this.setiDisplayStart(info.getiDisplayStart());
        this.setPrePage(info.isPrePage());
        this.setNextPage(info.isNextPage());
    }
}
