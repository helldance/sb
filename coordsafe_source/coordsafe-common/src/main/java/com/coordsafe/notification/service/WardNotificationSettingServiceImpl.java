package com.coordsafe.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import org.slf4j.*;

import com.coordsafe.notification.dao.WardNotificationSettingDAO;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.ward.entity.Ward;

@Transactional(propagation = Propagation.REQUIRED)
@Service("wardnotificationSettingService")
public class WardNotificationSettingServiceImpl implements WardNotificationSettingService {
	
	private static final Logger log = LoggerFactory.getLogger(WardNotificationSettingServiceImpl.class);
	@Autowired
	private WardNotificationSettingDAO notificationSettingDAO;


	@Override
	public void create(WardNotificationSetting notificationSetting) {
		log.info(notificationSetting.getEventType());

		notificationSettingDAO.save(notificationSetting);

	}

	@Override
	public void update(WardNotificationSetting notificationSetting)  {
		notificationSettingDAO.update(notificationSetting);
	}

	@Override
	public void delete(String wardid)  {

		notificationSettingDAO.delete(wardid);

	}


	
	@Override
	public WardNotificationSetting findById(Long id) {
		return notificationSettingDAO.findById(id);
	}

	@Override
	public List<WardNotificationSetting> findAll() {
		return notificationSettingDAO.findAll();
	}



	@Override
	public List<WardNotificationSetting> findByWardName(String wardname) {
		// TODO Auto-generated method stub
		return notificationSettingDAO.findByWardName(wardname);
	}

	@Override
	public WardNotificationSetting filterWardEventType(String eventType,
			Ward ward) {
		// TODO Auto-generated method stub
		return notificationSettingDAO.filterWardEventType(eventType,ward);
	}

}
