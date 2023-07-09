package com.coordsafe.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import org.slf4j.*;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.dao.VehicleNotificationSettingDAO;
import com.coordsafe.notification.entity.VehicleNotificationSetting;

@Transactional(propagation = Propagation.REQUIRED)
@Service("vehiclenotificationSettingService")
public class VehicleNotificationSettingServiceImpl implements VehicleNotificationSettingService {
	
	private static final Logger log = LoggerFactory.getLogger(VehicleNotificationSettingServiceImpl.class);
	@Autowired
	private VehicleNotificationSettingDAO notificationSettingDAO;


	@Override
	public void create(VehicleNotificationSetting notificationSetting) {
		log.info(notificationSetting.getEventType());

		notificationSettingDAO.save(notificationSetting);

	}

	@Override
	public void update(VehicleNotificationSetting notificationSetting)  {
		notificationSettingDAO.update(notificationSetting);
	}

	@Override
	public void delete(String notificationSettingName)  {

		//notificationSettingDAO.delete(notificationSettingDAO.findByCompanyName(notificationSettingName));

	}

	@Override
	public List<VehicleNotificationSetting> findByCompanyName(String companyname) {
		return notificationSettingDAO.findByCompanyName(companyname);
	}
	
	@Override
	public VehicleNotificationSetting findById(Long id) {
		return notificationSettingDAO.findById(id);
	}

	@Override
	public List<VehicleNotificationSetting> findAll() {
		return notificationSettingDAO.findAll();
	}

	@Override
	public VehicleNotificationSetting filterEventType(String eventType, Company company) {
		// TODO Auto-generated method stub
		
		return notificationSettingDAO.filterEventType(eventType, company);
		
	}

	@Override
	public List<VehicleNotificationSetting> findByVehicleName(String vehiclename) {
		// TODO Auto-generated method stub
		return notificationSettingDAO.findByVehicleName(vehiclename);
	}

}
