/*
 * 文件名：DatabaseQueryException.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2015年11月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common;

/**
 * 执行异常
 * 
 * @author chenrd
 * @version 2015年11月12日
 * @see BaseExecuteException
 * @since
 */
public class BaseExecuteException extends RuntimeException
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 5697522937198492344L;
    
    /**
     * 
     * @param msg
     */
    public BaseExecuteException(String msg)
    {
        super(msg);
    }
    
    public BaseExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
