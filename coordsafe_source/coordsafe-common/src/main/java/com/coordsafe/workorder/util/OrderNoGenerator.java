/**
 * @author Yang Wei
 * @Date Sep 15, 2013
 */
package com.coordsafe.workorder.util;

import java.util.Calendar;

/**
 * @author Yang Wei
 *
 */
public class OrderNoGenerator {
	public static String generate (){
		Calendar c = Calendar.getInstance();
		
		String curDt = c.get(Calendar.YEAR) + "" + String.valueOf(c.get(Calendar.MONTH) + 1) + "" + c.get(Calendar.DATE);
		String curTime = c.get(Calendar.HOUR_OF_DAY) + "" + c.get(Calendar.MINUTE) + "" + c.get(Calendar.SECOND);
		
		return curDt + curTime;
	}
}
