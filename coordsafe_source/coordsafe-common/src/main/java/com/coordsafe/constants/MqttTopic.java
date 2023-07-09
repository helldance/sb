/**
 * @author Yang Wei
 * @Date Oct 31, 2013
 */
package com.coordsafe.constants;

/**
 * @author Yang Wei
 *
 */
public class MqttTopic {
	static final String location = "/CsMt/Location";
	static final String status = "/CsMt/Status"; // to append with deviceId
	static final String panic = "/CsMt/Panic"; // to append with deviceId
	static final String checkin = "/CsMt/Checkin"; // to append with deviceId
	static final String battery = "/CsMt/Power"; // to append with deviceId
	static final String chat = "/CsMt/Chat"; // to append with deviceId
}
