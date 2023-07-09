package com.coordsafe.httpgateway.ws;

import javax.jws.WebService;

import com.coordsafe.httpgateway.entity.GpsLocation;
import com.coordsafe.httpgateway.entity.Guardian;

@WebService
public interface GuardianWebService {
	public GpsLocation queryLocation(String locatorId);
	//public Locator[] queryLocation();
	public Guardian guardianLogin();
}
