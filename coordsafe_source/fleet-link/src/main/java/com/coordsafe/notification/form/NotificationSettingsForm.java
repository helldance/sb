package com.coordsafe.notification.form;

import java.util.List;

import com.coordsafe.notification.entity.NotificationSetting;
import com.coordsafe.notification.entity.VehicleNotificationSetting;

public class NotificationSettingsForm {
	
	
	private List<NotificationSetting> notificationSettings;
	private List<NotificationSetting> eventTypes;//blank event types
	private List<VehicleNotificationSetting> vehicleNotificationSettings ;

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






}
