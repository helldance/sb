package com.coordsafe.core.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coordsafe.constants.Constants;
import org.slf4j.*;
@Controller
@RequestMapping("/" + Constants.LOGINHOME)
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET)
	public void showLogin() {
		log.info("Coordsafe is ready to login ...");
	}
}
