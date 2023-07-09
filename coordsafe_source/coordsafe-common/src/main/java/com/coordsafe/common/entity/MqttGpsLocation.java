package com.coordsafe.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yang Wei
 * @Date Dec 4, 2013
 */
public class MqttGpsLocation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public double latitude;
    public double longitude;
    public double altitude;
    public double accuracy;
    public Date time;
    public String ip;
    public String imei;
    
	/**
	 * 
	 */
	public MqttGpsLocation() {
		super();
		// TODO Auto-generated constructor stub
	}
}
