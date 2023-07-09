package com.coordsafe.remote.service;

import java.util.List;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.remote.entity.AndroidDeviceToken;
import com.coordsafe.remote.entity.IosDeviceToken;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
public interface DeviceRegistrationService {
	public void registerDevice(String token, Guardian guardian);
	public void deregisterDevice(String token);
	public List<IosDeviceToken> findAllDevices();
	public List<IosDeviceToken> findActiveDevices();
	public List<IosDeviceToken> findByGuardian(long guardianId);
	public IosDeviceToken findByToken(String token);
	
	// for android device
	public void registerAndroidDevice(String token, Guardian guardian);
	public void deregisterAndroidDevice(String token);
	public List<AndroidDeviceToken> findAllAndroidDevices();
	public List<AndroidDeviceToken> findActiveAnddroidDevices();
	public List<AndroidDeviceToken> findAndroidByGuardian(long guardianId);
	public AndroidDeviceToken findAndroidByToken(String token);
}
