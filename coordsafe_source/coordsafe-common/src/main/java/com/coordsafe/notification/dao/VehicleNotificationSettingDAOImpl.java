package com.coordsafe.notification.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.company.entity.Company;
import com.coordsafe.notification.entity.VehicleNotificationSetting;



@Repository("vehiclenotificationSettingDAO")
public class VehicleNotificationSettingDAOImpl implements VehicleNotificationSettingDAO {
	private static final Log log = LogFactory.getLog(VehicleNotificationSettingDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void save(VehicleNotificationSetting notificationSetting) {
		log.debug("saving VehicleNotificationSetting instance");
		sessionFactory.getCurrentSession().saveOrUpdate(notificationSetting);
	}

	@Override
	public void delete(VehicleNotificationSetting notificationSetting) {
		log.debug("deleting VehicleNotificationSetting instance");
		//clearForeignKeys(VehicleNotificationSetting);
		sessionFactory.getCurrentSession().delete(notificationSetting);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleNotificationSetting> findByCompanyName(String companyname) {
		log.debug("finding VehicleNotificationSetting instance by company name");
		return (List<VehicleNotificationSetting>) sessionFactory.getCurrentSession()
				.createQuery("from VehicleNotificationSetting as o where o.company.name=?")
				.setString(0, companyname).list();
	}
	
	@Override
	public VehicleNotificationSetting findById(Long id) {
		log.debug("finding VehicleNotificationSetting instance by id");
		return (VehicleNotificationSetting) sessionFactory.getCurrentSession()
				.createQuery("from VehicleNotificationSetting as o where o.id=?")
				.setLong(0, id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleNotificationSetting> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from VehicleNotificationSetting as o order by o.name").list();
	}

	@Override
	public void update(VehicleNotificationSetting notificationSetting) {
		sessionFactory.getCurrentSession().update(notificationSetting);
	}

	@Override
	public VehicleNotificationSetting filterEventType(String eventType, Company company) {
		// TODO Auto-generated method stub
		return (VehicleNotificationSetting)sessionFactory.getCurrentSession()
		.createQuery("from VehicleNotificationSetting as o where o.eventType=? and o.company.id=?")
		.setString(0, eventType).setLong(1, company.getId()).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleNotificationSetting> findByVehicleName(String vehiclename) {
		log.debug("finding VehicleNotificationSetting instance by vehicle name");
		return (List<VehicleNotificationSetting>) sessionFactory.getCurrentSession()
				.createQuery("from VehicleNotificationSetting as o where o.vehicle.name=?")
				.setString(0, vehiclename).list();
	}

}
