package com.coordsafe.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import org.slf4j.*;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.dao.NotificationSettingDAO;
import com.coordsafe.notification.entity.NotificationSetting;
@Transactional(propagation = Propagation.REQUIRED)
@Service("notificationSettingService")
public class NotificationSettingServiceImpl implements NotificationSettingService {
	
	private static final Logger log = LoggerFactory.getLogger(NotificationSettingServiceImpl.class);
	@Autowired
	private NotificationSettingDAO notificationSettingDAO;


	@Override
	public void create(NotificationSetting notificationSetting) {
		log.info(notificationSetting.getEventType());

		notificationSettingDAO.save(notificationSetting);

	}

	@Override
	public void update(NotificationSetting notificationSetting)  {
		notificationSettingDAO.update(notificationSetting);
	}

	@Override
	public void delete(String notificationSettingName)  {

		//notificationSettingDAO.delete(notificationSettingDAO.findByCompanyName(notificationSettingName));

	}

	@Override
	public List<NotificationSetting> findByCompanyName(String companyname) {
		return notificationSettingDAO.findByCompanyName(companyname);
	}
	
	@Override
	public NotificationSetting findById(Long id) {
		return notificationSettingDAO.findById(id);
	}

	@Override
	public List<NotificationSetting> findAll() {
		return notificationSettingDAO.findAll();
	}

	@Override
	public NotificationSetting filterEventType(String eventType, Company company) {
		// TODO Auto-generated method stub
		
		return notificationSettingDAO.filterEventType(eventType, company);
		
	}

}
