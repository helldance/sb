/**
 * ApiService.java
 * May 20, 2013
 * Yang Wei
 */
package com.coordsafe.api.service;

import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
public interface ApiService {
	public ApiKey getByKey(String key);
	public ApiKey getKeyByDomain(String domain);
	public ApiKey getKeyByAppname(String appname);
	public void create (ApiKey key);
	public void update (ApiKey key);
	public void delete (ApiKey key);
	ApiKey signupKey (String appName, int type, String namespace, String email);
	public ApiKey getById(long keyId);
}
