package com.coordsafe.notification.service;

import java.util.List;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.entity.NotificationSetting;


public interface NotificationSettingService {

	void create(NotificationSetting notificationSetting);

	void update(NotificationSetting notificationSetting) ;

	void delete(String notificationSettingName);

	List<NotificationSetting> findByCompanyName(String companyName);
	
	NotificationSetting findById(Long id);

	List<NotificationSetting> findAll();

	NotificationSetting filterEventType(String eventType, Company company);
}
