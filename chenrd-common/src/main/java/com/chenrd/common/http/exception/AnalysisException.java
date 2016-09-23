/*
 * 文件名：AnalysisException.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年3月1日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http.exception;


/**
 * 解析发送异常
 * @author chenrd
 * @version 2016年3月1日
 * @see AnalysisException
 * @since
 */
public class AnalysisException extends Exception
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 4322876848931427146L;

    public AnalysisException(String msg)
    {
        super(msg);
    }

}
