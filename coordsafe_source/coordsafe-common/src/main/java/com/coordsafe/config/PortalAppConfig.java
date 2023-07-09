/**
 * AppConfig.java
 * May 22, 2013
 * Yang Wei
 */
package com.coordsafe.config;

/**
 * @author Yang Wei
 *
 */
public final class PortalAppConfig {
	/*Trip filtering*/
	public static final int TRIP_MIN_MILAGE = 1; // in KM
	public static final int TRIP_MIN_MOVING_TIME = 1*60*1000; 
	public static final int LOCATOR_STOP_DURATION = 5*60*1000;
	public static final int LOCATOR_PAUSE_DURATION = 20*1000;
	
	/*Service, batch Job frequency*/
	public static final int JOB_LOCATOR_STATUS = 20*1000; // run every 20 sec
	public static final int JOB_TRIP_SUMMRIZE = 30*60*1000; // evert 30 min
	public static final double MAX_SPEED = 110; // for test, exact number to be according to official or user configuration
	
	public static final String SIMPLE_DATE_FORMAT = "dd-MM-yyyy HH:mm";
	public static final String SDF_DATE = "dd-MM-yyyy";
}
