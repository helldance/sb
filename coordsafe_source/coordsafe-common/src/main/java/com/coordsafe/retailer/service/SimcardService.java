package com.coordsafe.retailer.service;

import java.util.List;

import com.coordsafe.retailer.entity.Simcard;



public interface SimcardService {
	void create(Simcard ward) ;

	void update(Simcard ward) ;

	void delete(Simcard ward) ;

	void delete(String wardName);

	Simcard findByName(String SimcardName);

	List<Simcard> findByType(String type);
	List<Simcard> findByGuardian(String guardianId);

	List<Simcard> findAll();

	Simcard findByID(String imei);
	
	Simcard findByLocatorID(Long locatorid);
}
