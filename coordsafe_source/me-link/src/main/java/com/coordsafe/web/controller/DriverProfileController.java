/**
 * @author Yang Wei
 * @Date Jul 11, 2013
 */
package com.coordsafe.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/web")
public class DriverProfileController {
	@RequestMapping(value="/vehicle-summary", method=RequestMethod.GET)
	public String getPage (HttpServletRequest request, Model model){
		return "web/vehicle-summary";
	}
}
