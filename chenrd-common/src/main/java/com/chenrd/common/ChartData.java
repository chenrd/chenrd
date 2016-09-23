/*
 * 文件名：ChartData.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2015年11月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

import java.math.BigDecimal;
import java.util.Date;

import com.chenrd.example.VO;

/**
 * 
 * 
 * 
 * @author chenrd
 * @version 2015年11月11日
 * @see ChartData
 * @since
 */
public class ChartData extends VO
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -3023903516746267174L;
    
    private String xVal;
    
    private String yVal;
    
    public ChartData(Date statDate, Double income, long downSendNumber)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = (downSendNumber == 0 ? "0" : new BigDecimal(income).divide(new BigDecimal(downSendNumber), 2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
    }
    
    public ChartData(Date statDate, long reqNumber, long downSendNumber)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = (downSendNumber == 0 ? "0" : new BigDecimal(reqNumber).divide(new BigDecimal(downSendNumber), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue() + "");
    }
    
    public ChartData(Date statDate, Double val)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = val + "";
    }
    
    public ChartData(Date statDate, double val)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = val + "";
    }
    
    public ChartData(Date statDate, Long val)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = val + "";
    }
    
    public ChartData(Date statDate, Integer val)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = val + "";
    }
    
    public ChartData(Date statDate, int val)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = val + "";
    }
    
    public ChartData(Date statDate, long val)
    {
        this.xVal = DateUtil.formatDate(statDate);
        this.yVal = val + "";
    }

    /**
     * @param xVal
     * @param yVal
     */
    public ChartData(String xVal, String yVal)
    {
        super();
        this.xVal = xVal;
        this.yVal = yVal;
    }

    /**
     * 
     */
    public ChartData()
    {
        super();
    }

    /**
     * @return Returns the xVal.
     */
    public String getxVal()
    {
        return xVal;
    }

    /**
     * @param xVal The xVal to set.
     */
    public void setxVal(String xVal)
    {
        this.xVal = xVal;
    }

    /**
     * @return Returns the yVal.
     */
    public String getyVal()
    {
        return yVal;
    }

    /**
     * @param yVal The yVal to set.
     */
    public void setyVal(String yVal)
    {
        this.yVal = yVal;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    
}
