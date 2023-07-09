package com.coordsafe.httpgateway.service;

import com.coordsafe.httpgateway.entity.Locator;
import com.coordsafe.httpgateway.entity.PanicAlarm;
import com.coordsafe.httpgateway.entity.Status;

public interface WardMobileService {
	public boolean sendLocation (Locator locator);
	public boolean authenticate (Locator locator);
	public boolean sendPanicAlarm (PanicAlarm alarm);
	public boolean sendStatus (Status status);
}
