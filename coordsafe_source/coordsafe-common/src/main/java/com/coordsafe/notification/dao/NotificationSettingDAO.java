package com.coordsafe.notification.dao;

import java.util.List;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.entity.NotificationSetting;


public interface NotificationSettingDAO {

	void save(NotificationSetting notificationSetting);

	void delete(NotificationSetting notificationSetting);

	List<NotificationSetting> findByCompanyName(String companyname);

	NotificationSetting findById(Long id);

	List<NotificationSetting> findAll();

	void update(NotificationSetting notificationSetting);

	NotificationSetting filterEventType(String eventType, Company company);

}
