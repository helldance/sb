/**
 * 
 */
package com.coordsafe.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.exception.kinds.CoordSafeResponse;

/**
 * @author Yang Wei
 *
 */
public abstract class CoordSafeExceptionHandler {
	@ExceptionHandler
	@ResponseBody
	public CoordSafeResponse handleException(CoordSafeResponse ex){
		return ex;		
	}
	
	@ExceptionHandler
	public String handleException(CoordSafeResponse ex, Model model){
		model.addAttribute("message", ex.userMsg);
		return "error";
	}
}
