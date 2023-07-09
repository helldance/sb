package com.coordsafe.vehicle.service;

import java.util.List;

import com.coordsafe.vehicle.entity.VehicleGroup;

public interface VehicleGroupService {
	/*vehicle group CRUD service*/
	public void create(VehicleGroup group);
	public void update(VehicleGroup group);
	public void delete(long id);
	public VehicleGroup findVehicleGroupById(long id);
	
	public void delete(String name);
	public List<VehicleGroup> findAll();
	public VehicleGroup findByName(String name); 
	public List<VehicleGroup> findVehicleGroupsByCompany(String companyID);
}
