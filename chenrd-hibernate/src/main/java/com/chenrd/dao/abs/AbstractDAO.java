/*
 * 文件名：AbstractDAO.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年3月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.dao.abs;

import java.io.Serializable;

import org.hibernate.Session;

import com.chenrd.dao.DAO;
import com.chenrd.example.Domain;

public abstract class AbstractDAO implements DAO {
	
	protected abstract Session getSession();
	
	@Override
	public Object get(Serializable id) {
		return getSession().get(this.getDomClass(), id);
	}

	@Override
	public Object load(Serializable id) {
		return getSession().load(this.getDomClass(), id);
	}

	@Override
	public <T extends Domain> void saveOrUpdate(T bean) {
		getSession().saveOrUpdate(bean);
	}

	@Override
	public <T extends Domain> void save(T bean) {
		getSession().save(bean);
	}

	@Override
	public <T extends Domain> void update(T bean) {
		getSession().update(bean);
	}

	@Override
	public <T extends Serializable> void delete(T t) {
		getSession().delete(t);
	}
}
