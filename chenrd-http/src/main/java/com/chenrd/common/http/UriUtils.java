/*
 * 文件名：UriUtils.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年6月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.common.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UriUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(UriUtils.class);
	
    public static String encodeUri(String url, String encode) {
    	int index = -1;
    	if ((index = url.indexOf("?")) == -1) return url;
    	String newUrl = url.substring(0, index);
    	
    	Pattern p = Pattern.compile("[&=]");
    	Matcher m = p.matcher(url.substring(index + 1));
    	if (m.find()) {
    		//m.group()
    	}
    	try {
			return newUrl.concat(URLEncoder.encode(url.substring(index + 1), encode));
		} catch (UnsupportedEncodingException e) {
			LOG.error("ecode uri fail [url={}]", url, e);
			return url;
		}
    }
    
    private UriUtils() {
    	
    }
}
