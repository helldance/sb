package com.coordsafe.retailer.dao;

import java.util.List;

import com.coordsafe.retailer.entity.Simcard;


public interface SimcardDAO {
	
	public void create(Simcard Simcard);
	public void update(Simcard simcard);
	public void delete(Simcard Simcard);
	public List<Simcard> findAll();
	public List<Simcard> findByGuardianId(String guardianId);
	public List<Simcard> findByExample(Simcard Simcard);
	public Simcard findByName(String simcardName);
	public Simcard findByID(String simimei);
	public Simcard findByLocatorID(Long locatorid);
	

}
