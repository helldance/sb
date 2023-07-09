/**
 * 
 */
package com.coordsafe.httpgateway.entity;

public class PanicAlarm {
	public int service_id; //1 for elderly
	public int msg_direction; // 0 - l-> s
	public int msg_type; // 0 : authentication
	public String imei;
	public String alarm_time;
	public GpsLocation location;
	/**
	 * @param service_id
	 * @param msg_direction
	 * @param msg_type
	 * @param imei
	 * @param alarm_time
	 * @param location
	 */
	public PanicAlarm(int service_id, int msg_direction, int msg_type,
			String imei, String alarm_time, GpsLocation location) {
		super();
		this.service_id = service_id;
		this.msg_direction = msg_direction;
		this.msg_type = msg_type;
		this.imei = imei;
		this.alarm_time = alarm_time;
		this.location = location;
	}

	public PanicAlarm(){}
}
