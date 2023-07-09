package com.coordsafe.notification.dao;

import java.util.List;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.entity.VehicleNotificationSetting;


public interface VehicleNotificationSettingDAO {

	void save(VehicleNotificationSetting notificationSetting);

	void delete(VehicleNotificationSetting notificationSetting);

	List<VehicleNotificationSetting> findByCompanyName(String companyname);
	
	List<VehicleNotificationSetting> findByVehicleName(String vehiclename);


	VehicleNotificationSetting findById(Long id);

	List<VehicleNotificationSetting> findAll();

	void update(VehicleNotificationSetting notificationSetting);

	VehicleNotificationSetting filterEventType(String eventType, Company company);

}
