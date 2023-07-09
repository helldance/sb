package com.coordsafe.remote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.remote.dao.DeviceRegistrationDao;
import com.coordsafe.remote.entity.AndroidDeviceToken;
import com.coordsafe.remote.entity.IosDeviceToken;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
@Service
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService {
	@Autowired
	private DeviceRegistrationDao deviceRegistrationDao;	
	
	@Override
	public void registerDevice(String token, Guardian guardian) {
		// TODO Auto-generated method stub
		//if (deviceRegistrationDao.findByToken(token).size() > 0){
			deviceRegistrationDao.deleteByToken(token);
		//}
		
		deviceRegistrationDao.registerDevice(new IosDeviceToken(token, guardian));
	}

	@Override
	public void deregisterDevice(String token) {
		// TODO Auto-generated method stub
		deviceRegistrationDao.deleteByToken(token);
	}

	@Override
	public List<IosDeviceToken> findAllDevices() {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findAllDevices();
	}

	@Override
	public List<IosDeviceToken> findActiveDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IosDeviceToken> findByGuardian(long guardianId) {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findByGuardian(guardianId);
	}

	@Override
	public IosDeviceToken findByToken(String token) {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findByToken(token);
	}

	@Override
	public List<AndroidDeviceToken> findAllAndroidDevices() {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findAllAndroidDevices();
	}

	@Override
	public List<AndroidDeviceToken> findActiveAnddroidDevices() {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findActiveAnddroidDevices();
	}

	@Override
	public List<AndroidDeviceToken> findAndroidByGuardian(long guardianId) {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findAndroidByGuardian(guardianId);
	}

	@Override
	public AndroidDeviceToken findAndroidByToken(String token) {
		// TODO Auto-generated method stub
		return deviceRegistrationDao.findAndroidByToken(token);
	}

	@Override
	public void registerAndroidDevice(String token, Guardian guardian) {
		// TODO Auto-generated method stub
		deviceRegistrationDao.deleteByToken(token);
		
		deviceRegistrationDao.registerAndroidDevice(new AndroidDeviceToken(token, guardian));
	}

	@Override
	public void deregisterAndroidDevice(String token) {
		// TODO Auto-generated method stub
		deviceRegistrationDao.deregisterAndroidDevice(token);
	}

}
