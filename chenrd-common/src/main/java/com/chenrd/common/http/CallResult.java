/*
 * 文件名：CallResult.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年3月1日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

import java.io.IOException;
import java.io.Serializable;


import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author chenrd
 * @version 2016年3月1日
 * @see CallResult
 * @since
 */
public class CallResult implements Serializable 
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1281805247594012526L;
    
    private int statusCode;
    
    private String message;
    
    private Header[] reqHeads;
    
    private Header[] repHeads;
    
    public String getRepHeader(String name)
    {
        for (Header header : repHeads)
        {
            if (name.equals(header.getName())) return header.getValue();
        }
        return null;
    }
    
    /**
     * 
     * 
     * @param name
     * @return 
     * @see
     */
    public String getReqHeader(String name)
    {
        for (Header header : reqHeads)
        {
            if (name.equals(header.getName())) return header.getValue();
        }
        return null;
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws IOException 
     * @throws ParseException 
     */
    public CallResult(HttpUriRequest request, CloseableHttpResponse response) throws ParseException, IOException
    {
        statusCode = response.getStatusLine().getStatusCode();
        message = EntityUtils.toString(response.getEntity(), "UTF-8");
        reqHeads = request.getAllHeaders();
        repHeads = response.getAllHeaders();
    }
    /**
     * @return Returns the statusCode.
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * @param statusCode The statusCode to set.
     */
    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    /**
     * @return Returns the mssage.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param mssage The mssage to set.
     */
    public void setMessage(String mssage)
    {
        this.message = mssage;
    }

    /**
     * @return Returns the reqHeads.
     */
    public Header[] getReqHeads()
    {
        return reqHeads;
    }

    /**
     * @param reqHeads The reqHeads to set.
     */
    public void setReqHeads(Header[] reqHeads)
    {
        this.reqHeads = reqHeads;
    }

    /**
     * @return Returns the repHeads.
     */
    public Header[] getRepHeads()
    {
        return repHeads;
    }

    /**
     * @param repHeads The repHeads to set.
     */
    public void setRepHeads(Header[] repHeads)
    {
        this.repHeads = repHeads;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}
