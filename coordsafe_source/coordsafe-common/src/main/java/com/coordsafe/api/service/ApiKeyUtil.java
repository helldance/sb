/**
 * ApiKeyUtil.java
 * Apr 21, 2013
 * Yang Wei
 */
package com.coordsafe.api.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.coordsafe.api.entity.ApiKey;

/**
 * @author Yang Wei
 *
 */
@Component
public class ApiKeyUtil {
	private static ApiKeyUtil keyUtil;
	static final Log log = LogFactory.getLog(ApiKeyUtil.class);	
	
	private ApiKeyUtil(){}
	public static ApiKeyUtil getKeyUtil (){
		if (keyUtil == null)
			keyUtil = new ApiKeyUtil();
		
		return keyUtil;
	}
	
	public ApiKey signupNewKey (String appName, int type, String namespace,
			String email){
		String keyStr = randomString(15);
		
		Calendar c = Calendar.getInstance(); 
		
		Date now = c.getTime();
		c.setTime(now);
		c.add(Calendar.DATE, 365);
		Date future = c.getTime();
		
		ApiKey key = new ApiKey(keyStr, appName, type, namespace, email);		
		
		key.setCreateDt(now);
		key.setValidUntilDt(future);
		
		return key;
		//return new ApiKey(keyStr, domain, 0, now, future);
	}
	
	private String randomString(final int length) {
		String alphanumeric = "0123456789-abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		Random r= new Random();
	    StringBuilder sb = new StringBuilder();
	    
	    for(int i = 0; i < length; i++) {
	        char c = alphanumeric.charAt(r.nextInt(alphanumeric.length()));
	        sb.append(c);
	    }
	    
	    return sb.toString();
	}
}
