/*
 * 文件名：AbstractBusiness.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：xuwenqiang
 * 修改时间：2016年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.business.abs;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.chenrd.business.Business;
import com.chenrd.common.BeanCopyUtils;
import com.chenrd.dao.BaseDAO;
import com.chenrd.example.Domain;
import com.chenrd.example.Status;
import com.chenrd.example.VO;

@Transactional
public abstract class AbstractBusiness implements Business {

	public abstract BaseDAO getDAO();

	public boolean isLog() {
		return false;
	}

	
	public void publish(Serializable id) {
		Domain d = getDAO().get(getDAO().getDomClass(), id);
		d.setStatus(Status.ON == d.getStatus() ? Status.OFF : Status.ON);
		getDAO().update(d);
	}

	public void delete(Serializable id) {
		Domain d = getDAO().get(getDAO().getDomClass(), id);
		d.setStatus(Status.DELETED);
		getDAO().update(d);
	}

	public <T extends VO> T get(Serializable id, Class<T> clazz) {
		Domain d = getDAO().get(getDAO().getDomClass(), id);
		if (d == null) {
			return null;
		}
		return BeanCopyUtils.copy(d, clazz, false);
	}
}
