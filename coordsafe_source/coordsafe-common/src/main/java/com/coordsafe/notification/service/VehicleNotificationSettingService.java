package com.coordsafe.notification.service;

import java.util.List;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.entity.VehicleNotificationSetting;


public interface VehicleNotificationSettingService {

	void create(VehicleNotificationSetting notificationSetting);

	void update(VehicleNotificationSetting notificationSetting) ;

	void delete(String notificationSettingName);

	List<VehicleNotificationSetting> findByCompanyName(String companyName);
	
	List<VehicleNotificationSetting> findByVehicleName(String vehiclename);
	
	VehicleNotificationSetting findById(Long id);

	List<VehicleNotificationSetting> findAll();

	VehicleNotificationSetting filterEventType(String eventType, Company company);
}
