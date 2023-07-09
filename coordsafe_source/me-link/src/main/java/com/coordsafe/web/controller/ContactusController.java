/**
 * ContactusController.java
 * May 27, 2013
 * Yang Wei
 */
package com.coordsafe.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.constants.UserMessage;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/contactus")
public class ContactusController {
	private static final Logger logger = Logger.getLogger(ContactusController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String sendEmail(@RequestParam("from") String from, @RequestParam("email") String email,
			@RequestParam("message") String message, org.springframework.web.context.request.WebRequest webRequest){
		logger.info("Received contact us email from: " + email);
		
		// send email
		
		return UserMessage.MAIL_DELIVERED;
	}
}
