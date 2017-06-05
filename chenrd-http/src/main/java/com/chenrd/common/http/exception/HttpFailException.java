/*
 * 文件名：HttpFailException.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http.exception;

/**
 * 
 * 
 * @author chenrd
 * @version 2015年8月7日
 * @see HttpFailException
 * @since
 */
public class HttpFailException extends Exception
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 3504919549826842979L;

    /**
     * 
     */
    public HttpFailException(String msg)
    {
        super(msg);
    }
    
}
