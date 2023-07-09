package com.coordsafe.notification.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.entity.NotificationSetting;



@Repository("notificationSettingDAO")
public class NotificationSettingDAOImpl implements NotificationSettingDAO {
	private static final Log log = LogFactory.getLog(NotificationSettingDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void save(NotificationSetting notificationSetting) {
		log.debug("saving NotificationSetting instance");
		sessionFactory.getCurrentSession().saveOrUpdate(notificationSetting);
	}

	@Override
	public void delete(NotificationSetting notificationSetting) {
		log.debug("deleting NotificationSetting instance");
		//clearForeignKeys(NotificationSetting);
		sessionFactory.getCurrentSession().delete(notificationSetting);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationSetting> findByCompanyName(String companyname) {
		log.debug("finding NotificationSetting instance by name");
		return (List<NotificationSetting>) sessionFactory.getCurrentSession()
				.createQuery("from NotificationSetting as o where o.company.name=?")
				.setString(0, companyname).list();
	}
	
	@Override
	public NotificationSetting findById(Long id) {
		log.debug("finding NotificationSetting instance by id");
		return (NotificationSetting) sessionFactory.getCurrentSession()
				.createQuery("from NotificationSetting as o where o.id=?")
				.setLong(0, id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationSetting> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from NotificationSetting as o order by o.name").list();
	}

	@Override
	public void update(NotificationSetting notificationSetting) {
		sessionFactory.getCurrentSession().update(notificationSetting);
	}

	@Override
	public NotificationSetting filterEventType(String eventType, Company company) {
		// TODO Auto-generated method stub
		return (NotificationSetting)sessionFactory.getCurrentSession()
		.createQuery("from NotificationSetting as o where o.eventType=? and o.company.id=?")
		.setString(0, eventType).setLong(1, company.getId()).uniqueResult();

	}

}
