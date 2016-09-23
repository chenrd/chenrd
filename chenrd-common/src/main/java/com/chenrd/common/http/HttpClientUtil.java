/*
 * 文件名：HttpClientUtil.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年6月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chenrd
 * @version 2015年6月25日
 * @see HttpClientUtil
 * @since
 */
public final class HttpClientUtil
{

    /**
     * 
     */
    private static Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
    
    /**
     * 
     * @param url 请求地址
     * @param params 参数
     * @return ""
     * @see
     */
    public static String post(String url, Map<String, String> params)
    {
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(5000).setConnectionRequestTimeout(5000)
            .setStaleConnectionCheckEnabled(true).build();
        clientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        CloseableHttpClient httpclient = clientBuilder.build();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (params != null)
        {
            for (String key : params.keySet())
            {
                formparams.add(new BasicNameValuePair(key, params.get(key))); 
            }
        }
        UrlEncodedFormEntity uefEntity = null;
        try
        {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        catch (Exception e)
        {
            LOG.error("发送请求：{}失败", url, e);
            throw new RuntimeException(e);
        } 
        finally 
        {
            try
            {
                if (httpclient != null)
                {
                    httpclient.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * 
     */
    private HttpClientUtil()
    {
        
    }
}
