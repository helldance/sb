package com.coordsafe.notification.entity;

import java.util.List;

/**
 * @author Yang Wei
 * @Date Jan 24, 2014
 */
public class ApnsPayload {
	//public static final String aps = "aps";
	public List<ApsMessage> aps;
	
	class ApsMessage {
		public String alert;
		public int badge;
		public String sound;
		/**
		 * 
		 */
		public ApsMessage() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
	}
}
