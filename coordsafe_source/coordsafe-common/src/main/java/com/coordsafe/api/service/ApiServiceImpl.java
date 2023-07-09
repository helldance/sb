/**
 * ApiServiceImpl.java
 * May 20, 2013
 * Yang Wei
 */
package com.coordsafe.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.api.dao.ApiKeyDAO;
import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
@Service
public class ApiServiceImpl implements ApiService{
	@Autowired
	private ApiKeyDAO apiDao;
	
	public void setApiKeyDAO (ApiKeyDAO apiDao){
		this.apiDao = apiDao;
	}

	@Override
	public ApiKey signupKey(String appName, int type, String namespace,
			String email) {
		ApiKey key = ApiKeyUtil.getKeyUtil().signupNewKey(appName, type, namespace, email);
		apiDao.create(key);
		
		return key;
	}

	@Override
	public ApiKey getByKey(String key) {
		// TODO Auto-generated method stub
		return apiDao.getByKey(key);
	}

	@Override
	public ApiKey getKeyByDomain(String domain) {
		// TODO Auto-generated method stub
		return apiDao.getKeyByDomain(domain);
	}

	@Override
	public ApiKey getKeyByAppname(String appname) {
		// TODO Auto-generated method stub
		return apiDao.getKeyByAppname(appname);
	}

	@Override
	public void create(ApiKey key) {
		// TODO Auto-generated method stub
		apiDao.create(key);
	}

	@Override
	public void update(ApiKey key) {
		// TODO Auto-generated method stub
		apiDao.update(key);
	}

	@Override
	public void delete(ApiKey key) {
		// TODO Auto-generated method stub
		apiDao.delete(key);
	}

	@Override
	public ApiKey getById(long keyId) {
		// TODO Auto-generated method stub
		return apiDao.getById(keyId);
	}

}