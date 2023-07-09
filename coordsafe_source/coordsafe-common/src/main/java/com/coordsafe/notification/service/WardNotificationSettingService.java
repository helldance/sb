package com.coordsafe.notification.service;

import java.util.List;

import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.ward.entity.Ward;


public interface WardNotificationSettingService {

	void create(WardNotificationSetting notificationSetting);

	void update(WardNotificationSetting notificationSetting) ;

	void delete(String wardid);


	List<WardNotificationSetting> findByWardName(String vehiclename);
	
	WardNotificationSetting findById(Long id);

	List<WardNotificationSetting> findAll();

	WardNotificationSetting filterWardEventType(String eventType, Ward ward);

}
