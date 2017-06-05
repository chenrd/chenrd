/*
 * 文件名：UrlLable.java
 * 描述：
 * 修改人：chenrd
 * 修改时间：2015年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.spring.lable;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 自定比标签<@url
 * @author chenrd
 * @version 2015年5月18日
 * @see UrlLable
 * @since
 */
public class UrlLable implements TemplateDirectiveModel {

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
        throws TemplateException, IOException {
        Writer out = env.getOut();
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();  
        HttpServletRequest request = attr.getRequest();
        StringBuilder url = new StringBuilder();
        Object paramValue = params.get("value");
        
        if (paramValue instanceof SimpleScalar) {
        	paramValue = ((SimpleScalar) paramValue).getAsString();
            if (!StringUtils.isNotBlank((String) paramValue)) {
                out.write("");
                return;
            }
        }
        url.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
        if (((String) paramValue).charAt(0) != '/') {
        	url.append('/');
        }
        url.append((String) paramValue);
        out.write(url.toString());
    }

}
