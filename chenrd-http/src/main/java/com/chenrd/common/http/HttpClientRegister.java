/*
 * 文件名：HttpClientRegister.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年8月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chenrd.common.http.exception.HttpFailException;


/**
 * 
 * @author chenrd
 * @version 2015年8月7日
 * @see HttpClientRegister
 * @since
 */
@SuppressWarnings("deprecation")
public class HttpClientRegister
{
    
    public final static int TYPE_SSL = 1;
    
    public final static int TYPE_BASE = 0;
    
    private final static Logger LOG = LoggerFactory.getLogger(HttpClientRegister.class);
    
    public final static String RES_STR_POST = "[POST]";
    
    public final static String RES_STR_GET = "[GET]";
    
    public final static int RES_INT_POST = 1;
    
    public final static int RES_INT_GET = 0;
    
    /**
     * 域名
     */
    private String realmName;
    
    private CloseableHttpClient client;
    
    /**
     * 这个参数的作用：有一些特殊情况，如查询请求的时候需要从登录操作中获取参数，这个属性可以保存这个属性在查询的时候使用
     */
    private Map<String, Object> nextReqParams = null;
    
    private Map<String, Object> nextReqHeads = null;
    
    /**
     * 
     * 
     * @param name
     * @param val 
     * @see
     */
    public void addNextReqHead(String name, String val)
    {
        if (nextReqHeads == null) nextReqHeads = new HashMap<String, Object>();
        nextReqHeads.put(name, val);
    }
    
    public void addAllReqHead(Map<String, Object> heads)
    {
        if (nextReqHeads == null) nextReqHeads = new HashMap<String, Object>();
        nextReqHeads.putAll(heads);
    }
    
    public void clearNextReqHead()
    {
        nextReqHeads.clear();
    }
    
    public void addNextReqParam(String name, Object val)
    {
        if (nextReqParams == null) nextReqParams = new HashMap<String, Object>();
        nextReqParams.put(name, val);
    }
    
    public void addAllNextReqParam(Map<String, Object> params)
    {
        if (nextReqParams == null) nextReqParams = new HashMap<String, Object>();
        nextReqParams.putAll(params);
    }
    
    public void clearNextReqParams()
    {
        nextReqParams.clear();
    }
    
    /**
     * 
     * @param realmName
     * @param type
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws Exception
     */
    public HttpClientRegister(String realmName, int type) throws GeneralSecurityException {
        this.realmName = realmName;
        if (type == TYPE_BASE) {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                .setStaleConnectionCheckEnabled(true).build();
            clientBuilder.setDefaultRequestConfig(defaultRequestConfig);
            client = clientBuilder.build();
        }
        else {
         // 相信自己的CA和所有自签名的证书  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                null, new TrustStrategy() {
                    // 信任所有
                    public boolean isTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                        return true;
                    }
                }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerifier());
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setStaleConnectionCheckEnabled(true).build();
            HttpClientBuilder clientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf);
            clientBuilder.setDefaultRequestConfig(defaultRequestConfig);
            client = clientBuilder.build(); 
        }
        
    }
    
    public byte[] call(String url) throws HttpFailException {
        CloseableHttpResponse response = null;
        HttpGet request = new HttpGet(url);
        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK && status != HttpStatus.SC_MOVED_TEMPORARILY && status != HttpStatus.SC_MOVED_PERMANENTLY) {
                throw new HttpFailException("请求失败");
            }
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            LOG.error("请求失败：{}", request.getURI().getPath(), e);
            return null;
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * 
     * @param url
     * @param params 
     * @param resType {@link HttpClientRegister.RES_INT_POST}
     * @return 
     * @see
     */
    public CallResult CallResult(String url, Map<String, Object> params, int resType, RequestParamType type) throws HttpFailException {
        if (resType == RES_INT_POST) {
            return this.post(url, params, type);
        } else {
            return this.get(url, params, type);
        }
    }
    
    /**
     * 
     * 
     * @param url
     * @param params
     * @param resType {@link HttpClientRegister.RES_STR_POST}
     * @return 
     * @see
     */
    public CallResult call(String url, Map<String, Object> params, String resType, RequestParamType type) throws HttpFailException {
        if (resType.equalsIgnoreCase(RES_STR_POST)) {
            return this.post(url, params, type);
        } else {
            return this.get(url, params, type);
        }
    }
    
    /**
     * 
     * 
     * @param url
     * @param params
     * @param resType {@link HttpClientRegister.RES_STR_POST}
     * @return 
     * @see
     */
    public CallResult call(String url, String strParams, String resType, RequestParamType type) throws HttpFailException {
        
        if (resType.equalsIgnoreCase(RES_STR_POST)) {
            return this.post(url, strToMap(strParams), type);
        } else {
            return this.get(url, strToMap(strParams), type);
        }
    }
    
    /**
     * 
     * 
     * @param url
     * @param params
     * @param resType {@link HttpClientRegister.RES_STR_POST}
     * @return 
     * @see
     */
    public CallResult call(String url, String strParams, int resType, RequestParamType type) throws HttpFailException {
        
        if (resType == RES_INT_POST) {
            return this.post(url, strToMap(strParams), type);
        } else {
            return this.get(url, strToMap(strParams), type);
        }
    }
    
    private Map<String, Object> strToMap(String str) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (!StringUtils.isNotBlank(str)) return params;
        for (String bean : str.split(",")) {
            String[] p = bean.split(":");
            params.put(p[0], p.length == 2 ? p[1] : null);
        }
        return params;
    }
    
    
    /**
     * 
     * 
     * @param url
     * @param params 
     * @see
     */
    public CallResult post(String url, Map<String, Object> params, RequestParamType type) throws HttpFailException {
        if (url.indexOf(realmName) == -1) {
            throw new RuntimeException("请求的地址与注册实例时的域名不一致，注册的地址：" + realmName + ",访问的地址：" + url);
        }
        HttpPost httppost = new HttpPost(url);
        if (nextReqParams != null && nextReqParams.size() > 0) {
            params.putAll(nextReqParams);
            nextReqParams.clear();
        }
        return this.call(httppost, params, type);
    }
    
    /**
     * 
     * 
     * @param url
     * @param params 
     * @see
     */
    public CallResult get(String url, Map<String, Object> params, RequestParamType type) throws HttpFailException {
        if (url.indexOf(realmName) == -1) {
            throw new RuntimeException("请求的地址与注册实例时的域名不一致");
        }
        if (params != null) {
            if (nextReqParams != null && nextReqParams.size() > 0) {
                params.putAll(nextReqParams);
                nextReqParams.clear();
            }
            for (String key : params.keySet()) {
                if (!((params.get(key)) instanceof String) || ((String) params.get(key)).indexOf(":") == -1) url += (url.indexOf("?") == -1 ? '?' : '&') + key + '=' + params.get(key);
                else {
                    for (String str : ((String) params.get(key)).split(":")) {
                    	url += (url.indexOf("?") == -1 ? '?' : '&') + key + '=';
                    	try {
                    		url += URLEncoder.encode(str, "utf-8");
                    	} catch (UnsupportedEncodingException e) {
                    		url += str;
                    		LOG.error("uri eccode fail, uri=[{}] case=[{}]", str, e.getMessage());
						}
                    }
                }
            }
        }
        HttpGet httppost = new HttpGet(url);
        return this.call(httppost, null, type);
    }
    
    private CallResult call(HttpRequestBase request, Map<String, Object> params, RequestParamType type) throws HttpFailException {
        HttpEntity uefEntity = null;
        CloseableHttpResponse response = null;
        try {
            if (type == RequestParamType.STRING) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                if (request instanceof HttpPost) {
                    if (params != null) {
                        for (String key : params.keySet()) {
                            if (!((params.get(key)) instanceof String) || ((String) params.get(key)).indexOf(":") == -1) formparams.add(new BasicNameValuePair(key, params.get(key).toString()));
                            else {
                                for (String str : ((String) params.get(key)).split(":")) {
                                    formparams.add(new BasicNameValuePair(key, str));
                                }
                            }
                        }
                    }
                    uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
                    ((HttpPost) request).setEntity(uefEntity);
                }
            }
            else if (type == RequestParamType.JSON) {
                request.setHeader("Accept", "application/json, text/plain, */*");
                request.setHeader("Content-Type", "application/json;charset=UTF-8");
                uefEntity = new StringEntity(params.toString());
                ((HttpPost) request).setEntity(uefEntity);
            }
            if (nextReqHeads != null && nextReqHeads.size() > 0) {
                for (Entry<String, Object> entry : nextReqHeads.entrySet())
                    request.setHeader(entry.getKey(), entry.getValue().toString());
                nextReqHeads.clear();
            }
            response = client.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK && status != HttpStatus.SC_MOVED_TEMPORARILY && status != HttpStatus.SC_MOVED_PERMANENTLY) {
                LOG.error(EntityUtils.toString(response.getEntity(), "UTF-8"));
                throw new HttpFailException("请求失败返回的代码不对：" + status);
            }
            return new CallResult(request, response);
        } catch (IOException e) {
            LOG.error("请求失败：{}", request.getURI().getPath(), e);
            throw new RuntimeException("请求异常：" + request.getURI().getPath(), e);
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getCookie(String name)
    {
        List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals(name)) return cookie.getValue();
        }
        return null;
    }
    
    public void close()
    {
        try
        {
            if (client != null)
            {
                this.client.close();
                this.client = null;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * 
     * @param loginUrl
     * @return 
     * @see
     */
    public boolean isRealmMatch(String loginUrl)
    {
        return loginUrl.contains(this.realmName);
    }
    
    /**
     * 判断本身类是否已经被close掉
     * 
     * @return 
     * @see
     */
    public boolean isThisClose()
    {
        return this.client == null ? true : false;
    }
    
    
    /**
     * 重置realmName
     * 
     * @param realmName 
     * @see
     */
    public void resetRealmName(String realmName)
    {
        this.realmName = realmName;
    }
}
