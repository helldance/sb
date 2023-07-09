package com.coordsafe.ward.service;

import java.util.List;

import com.coordsafe.ward.entity.Ward;

public interface WardService {
	Ward create(Ward ward);

	Ward update(Ward ward);
	
	void updateWardFenceStatus(Ward ward);

	void delete(Ward ward);

	void delete(String wardName);
	
	void deleteById(long wardId);

	Ward findByName(String WardName);

	List<Ward> findByType(String type);
	List<Ward> findByGuardian(String guardianId);

	List<Ward> findAll();

	Ward findByID(Long long1);
	
	Ward findByLocatorID(Long locatorid);

	void updateCache(Ward detachedInstance);
}
