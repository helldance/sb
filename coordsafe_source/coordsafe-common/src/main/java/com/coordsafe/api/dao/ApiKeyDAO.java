/**
 * ApiKeyDAO.java
 * Apr 2, 2013
 * Yang Wei
 */
package com.coordsafe.api.dao;

import java.util.List;

import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
public interface ApiKeyDAO {
	public ApiKey getByKey(String key);
	public ApiKey getKeyByDomain(String domain);
	public ApiKey getKeyByAppname(String appname);
	public void create (ApiKey key);
	public void update (ApiKey key);
	public void delete (ApiKey key);
	public ApiKey getById(long keyId);
	public List<ApiKey> getAll();
}
