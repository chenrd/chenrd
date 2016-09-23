/*
 * 文件名：CommonParameter.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年4月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.quartz.parameter;

public class CommonParameter extends AbstractParameter implements TaskParameter
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 2275508384156192265L;
    
    private String begin;
    
    private String end;
    
    /**
     * @return Returns the begin.
     */
    public String getBegin()
    {
        return begin;
    }

    /**
     * @param begin The begin to set.
     */
    public void setBegin(String begin)
    {
        this.begin = begin;
    }

    /**
     * @return Returns the end.
     */
    public String getEnd()
    {
        return end;
    }

    /**
     * @param end The end to set.
     */
    public void setEnd(String end)
    {
        this.end = end;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}
