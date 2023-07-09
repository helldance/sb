package com.coordsafe.notification.form;

import java.util.List;

import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.VehicleNotificationSetting;
import com.coordsafe.notification.entity.WardNotificationSetting;

public class NotificationSettingsForm {
	
	
	private List<NotificationSetting> notificationSettings;
	private List<NotificationSetting> eventTypes;//blank event types
	private List<VehicleNotificationSetting> vehicleNotificationSettings ;
	private List<WardNotificationSetting> wardNotificationSettings;
	private List<WardNotificationSetting> wardEventTypes;//blank event types

	
	public List<NotificationSetting> getNotificationSettings() {
		return notificationSettings;
	}

	public void setNotificationSettings(List<NotificationSetting> notificationSettings) {
		this.notificationSettings = notificationSettings;
	}

	public List<NotificationSetting> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(List<NotificationSetting> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public List<VehicleNotificationSetting> getVehicleNotificationSettings() {
		return vehicleNotificationSettings;
	}

	public void setVehicleNotificationSettings(
			List<VehicleNotificationSetting> vehicleNotificationSettings) {
		this.vehicleNotificationSettings = vehicleNotificationSettings;
	}

	public List<WardNotificationSetting> getWardNotificationSettings() {
		return wardNotificationSettings;
	}

	public void setWardNotificationSettings(List<WardNotificationSetting> wardNotificationSettings) {
		this.wardNotificationSettings = wardNotificationSettings;
	}

	public List<WardNotificationSetting> getWardEventTypes() {
		return wardEventTypes;
	}

	public void setWardEventTypes(List<WardNotificationSetting> wardEventTypes) {
		this.wardEventTypes = wardEventTypes;
	}






}
