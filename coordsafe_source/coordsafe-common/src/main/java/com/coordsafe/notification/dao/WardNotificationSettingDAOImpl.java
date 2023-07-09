package com.coordsafe.notification.dao;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.ward.entity.Ward;



@Repository("wardnotificationSettingDAO")
public class WardNotificationSettingDAOImpl implements WardNotificationSettingDAO {
	private static final Log log = LogFactory.getLog(WardNotificationSettingDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void save(WardNotificationSetting notificationSetting) {
		log.debug("saving WardNotificationSetting instance");
		sessionFactory.getCurrentSession().save(notificationSetting);
	}

	@Override
	public void delete(WardNotificationSetting notificationSetting) {
		log.debug("deleting WardNotificationSetting instance");
		//clearForeignKeys(WardNotificationSetting);
		sessionFactory.getCurrentSession().delete(notificationSetting);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<WardNotificationSetting> findByCompanyName(String companyname) {
		log.debug("finding WardNotificationSetting instance by company name");
		return (List<WardNotificationSetting>) sessionFactory.getCurrentSession()
				.createQuery("from WardNotificationSetting as o where o.company.name=?")
				.setString(0, companyname).list();
	}
	
	@Override
	public WardNotificationSetting findById(Long id) {
		log.debug("finding WardNotificationSetting instance by id");
		return (WardNotificationSetting) sessionFactory.getCurrentSession()
				.createQuery("from WardNotificationSetting as o where o.id=?")
				.setLong(0, id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WardNotificationSetting> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from WardNotificationSetting as o order by o.name").list();
	}

	@Override
	public void update(WardNotificationSetting notificationSetting) {
		sessionFactory.getCurrentSession().update(notificationSetting);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<WardNotificationSetting> findByWardName(String wardname) {
		log.debug("finding WardNotificationSetting instance by ward name");
		return (List<WardNotificationSetting>) sessionFactory.getCurrentSession()
				.createQuery("from WardNotificationSetting as o where o.ward.name=?")
				.setString(0, wardname).list();
	}

	@Override
	public void delete(String wardid) {
		log.debug("Deleting WardNotificationSetting instance by wardid");
		sessionFactory.getCurrentSession().createQuery("delete from WardNotificationSetting where ward_id = :wardid")	
				.setParameter("wardid", Long.valueOf(wardid)).executeUpdate();
		
	}

	@Override
	public WardNotificationSetting filterWardEventType(String eventType,
			Ward ward) {
		log.debug("finding WardNotificationSetting instance by eventType and ward name: " + ward.getName());
		
		WardNotificationSetting setting = (WardNotificationSetting) sessionFactory.getCurrentSession()
				.createQuery("from WardNotificationSetting as o where o.ward.name=? and o.eventType = ?")
				.setString(0, ward.getName()).setString(1, eventType).uniqueResult();
		
		if (setting == null){
			Guardian g = ward.getGuardians().iterator().next();
			
			Set<Ward> wards = (g == null)? null: g.getWards();
			
			for (Ward w : wards){				
				setting = (WardNotificationSetting) sessionFactory.getCurrentSession()
						.createQuery("from WardNotificationSetting as o where o.ward.name=? and o.eventType = ?")
						.setString(0, w.getName()).setString(1, eventType).uniqueResult();
				
				if (setting != null){
					return setting;
				}		
			}
		}
		
		return setting;
	}

}
