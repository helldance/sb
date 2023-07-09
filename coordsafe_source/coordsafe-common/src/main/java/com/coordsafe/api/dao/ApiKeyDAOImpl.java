/**
 * ApiKeyDAOImpl.java
 * Apr 2, 2013
 * Yang Wei
 */
package com.coordsafe.api.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
@Repository
@Transactional
public class ApiKeyDAOImpl implements ApiKeyDAO {
	private static final Log log = LogFactory.getLog(ApiKeyDAOImpl.class);

	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ApiKey getByKey(String key) {
		return (ApiKey)sessionFactory.getCurrentSession().createQuery("from ApiKey where key = ?").setString(0, key).uniqueResult();
	}

	@Override
	public ApiKey getKeyByDomain(String domain) {
		return (ApiKey)sessionFactory.getCurrentSession().createQuery("from ApiKey where domain = ?").setString(0, domain).uniqueResult();
	}

	@Override
	public void create(ApiKey key) {
		sessionFactory.getCurrentSession().save(key);
	}

	@Override
	public void update(ApiKey key) {
		sessionFactory.getCurrentSession().update(key);
	}

	@Override
	public void delete(ApiKey key) {
		sessionFactory.getCurrentSession().delete(key);
	}

	@Override
	public ApiKey getKeyByAppname(String appname) {
		// TODO Auto-generated method stub
		return (ApiKey)sessionFactory.getCurrentSession().createQuery("from ApiKey where appName = ?").setString(0, appname).uniqueResult();
	}

	@Override
	public ApiKey getById(long keyId) {
		// TODO Auto-generated method stub
		return (ApiKey)sessionFactory.getCurrentSession().createQuery("from ApiKey where id = :kid").setLong("kid", keyId).uniqueResult();
	}

	@Override
	public List<ApiKey> getAll() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from ApiKey").list();
	}

}
