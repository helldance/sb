package com.coordsafe.notification.dao;

import java.util.List;

import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.ward.entity.Ward;


public interface WardNotificationSettingDAO {

	void save(WardNotificationSetting notificationSetting);

	void delete(WardNotificationSetting notificationSetting);

	List<WardNotificationSetting> findByCompanyName(String companyname);
	
	List<WardNotificationSetting> findByWardName(String vehiclename);


	WardNotificationSetting findById(Long id);

	List<WardNotificationSetting> findAll();

	void update(WardNotificationSetting notificationSetting);

	void delete(String wardid);

	WardNotificationSetting filterWardEventType(String eventType, Ward ward);


}
