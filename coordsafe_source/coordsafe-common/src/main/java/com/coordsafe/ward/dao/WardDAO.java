package com.coordsafe.ward.dao;

import java.util.List;

import com.coordsafe.ward.entity.Ward;

public interface WardDAO {
	public void create(Ward ward);
	public void update(Ward ward);
	public void delete(Ward ward);
	public List<Ward> findAll();
	public List<Ward> findByGuardianId(String guardianId);
	public List<Ward> findByExample(Ward ward);
	public Ward findByName(String wardName);
	public Ward findByID(Long wardID);
	public Ward findByLocatorID(Long locatorid);
}
