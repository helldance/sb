package com.coordsafe.remote.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.remote.entity.AndroidDeviceToken;
import com.coordsafe.remote.entity.IosDeviceToken;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
@Repository
@Transactional 
public class DeviceRegistrationDaoImpl implements DeviceRegistrationDao {
	@Autowired SessionFactory sessionFactory;
	
	@Override
	public void registerDevice(IosDeviceToken token) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(token);
	}

	@Override
	public void deregisterDevice(String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IosDeviceToken> findAllDevices() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from IosDeviceToken t").list();
	}

	@Override
	public List<IosDeviceToken> findActiveDevices() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from IosDeviceToken t").list();
	}

	@Override
	public List<IosDeviceToken> findByGuardian(long guardianId) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from IosDeviceToken t where t.guardian.id = :gId")
				.setLong("gId", guardianId).list();
	}

	@Override
	public IosDeviceToken findByToken(String token) {
		// TODO Auto-generated method stub
		return (IosDeviceToken) sessionFactory.getCurrentSession().createQuery("from IosDeviceToken t where t.token = :token")
				.setString("token", token).uniqueResult();
	}

	@Override
	public void deleteByToken(String token) {
		IosDeviceToken t = this.findByToken(token);
				
		if (t != null){
			sessionFactory.getCurrentSession().delete(t);
		}		
	}

	@Override
	public void registerAndroidDevice(AndroidDeviceToken token) {
		sessionFactory.getCurrentSession().save(token);
		
	}

	@Override
	public void deregisterAndroidDevice(String token) {
		AndroidDeviceToken t = this.findAndroidByToken(token);
		
		if (t != null){
			sessionFactory.getCurrentSession().delete(t);
		}		
	}

	@Override
	public List<AndroidDeviceToken> findAllAndroidDevices() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from AndroidDeviceToken t").list();
	}

	@Override
	public List<AndroidDeviceToken> findActiveAnddroidDevices() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from AndroidDeviceToken t").list();
	}

	@Override
	public List<AndroidDeviceToken> findAndroidByGuardian(long guardianId) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from AndroidDeviceToken t where t.guardian.id = :gId")
				.setLong("gId", guardianId).list();
	}

	@Override
	public AndroidDeviceToken findAndroidByToken(String token) {
		// TODO Auto-generated method stub
		return (AndroidDeviceToken) sessionFactory.getCurrentSession().createQuery("from AndroidDeviceToken t where t.token = :token")
				.setString("token", token).uniqueResult();
	}

}
