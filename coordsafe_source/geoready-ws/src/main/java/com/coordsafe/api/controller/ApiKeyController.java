/**
 * ApiKeyController.java
 * Apr 21, 2013
 * Yang Wei
 */
package com.coordsafe.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.api.entity.ApiKey;
import com.coordsafe.api.service.ApiKeyUtil;
import com.coordsafe.api.service.ApiService;
import com.coordsafe.constants.Constants;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/apikey")
public class ApiKeyController {
	private static final Logger logger = Logger.getLogger(ApiKeyController.class);
	
	@Autowired
	private ApiService apiKeyService;
	
	@RequestMapping(method=RequestMethod.GET, value=Constants.CREATE)
	@ResponseBody
	public String signup (@RequestParam("appName") String appName, @RequestParam("type") String type,
			@RequestParam("namespace") String namespace, @RequestParam("email") String email,
			  org.springframework.web.context.request.WebRequest webRequest){
		//TODO appName, namespace should be unique
		ApiKey key = ApiKeyUtil.getKeyUtil().signupNewKey(appName, Integer.parseInt(type), namespace, email);
		
		apiKeyService.create(key);
		
		logger.info("returned API key: " + key.getKey());
		
		return key.getKey();		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public String createApiKey (@RequestParam("appName") String appName, @RequestParam("type") String type,
			@RequestParam("namespace") String namespace, @RequestParam("email") String email,
			  org.springframework.web.context.request.WebRequest webRequest){
		ApiKey key = ApiKeyUtil.getKeyUtil().signupNewKey(appName, Integer.parseInt(type), namespace, email);
		
		apiKeyService.create(key);
		
		logger.info("returned API key: " + key.getKey());
		// send email notifications.
		// or return key directly.
		
		return key.getKey();		
	}
	
	@RequestMapping(method=RequestMethod.GET, value=Constants.LOST)
	@ResponseBody
	public String recoverLostKey(@RequestParam("appName") String appName, @RequestParam("email") String email){
		ApiKey key = apiKeyService.getKeyByDomain(appName);
		
		if (key != null)
			return key.getKey();
		else
			return "";
	}
}
