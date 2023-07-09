/**
 * ReportController.java
 * Jun 2, 2013
 * Yang Wei
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
@RequestMapping("/web")
public class ReportController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/report-overview")
	public String getOverview (){
		
		return "web/report-overview";
	}
}
