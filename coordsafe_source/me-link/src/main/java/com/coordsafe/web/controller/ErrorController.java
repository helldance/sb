/**
 * @author Yang Wei
 * @Date Jul 17, 2013
 */
package com.coordsafe.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/web/error")
public class ErrorController {
	@RequestMapping(method = RequestMethod.GET)
	public String getOverview (){
		
		return "web/error";
	}
}
