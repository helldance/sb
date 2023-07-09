package com.coordsafe.tcpgateway.locator;

import com.coordsafe.tcpgateway.util.CommonConstants;

public class LocationMsg extends LocatorMessage{
	private GpsPayload gpsPayload;
	
	public LocationMsg (){
		super();
		if (locatorMsgHeader != null) {
			locatorMsgHeader.setMsgTypeId(CommonConstants.LOC_MSG);
		}
	}

	/**
	 * @return the gpsPayload
	 */
	public GpsPayload getGpsPayload() {
		return gpsPayload;
	}

	/**
	 * @param gpsPayload the gpsPayload to set
	 */
	public void setGpsPayload(GpsPayload gpsPayload) {
		this.gpsPayload = gpsPayload;
	}
}
