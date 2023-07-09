package com.coordsafe.vehicle.dao;

import java.util.List;

import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;

public interface VehicleGroupDAO {
	/*vehicle group CRUD service*/
	public void create(VehicleGroup group);
	public void update(VehicleGroup group);
	public void delete(long id);
	public VehicleGroup findVehicleGroupById(long id);
	
	public void delete(String name);
	public List<VehicleGroup> findAll();
	public VehicleGroup findByName(String name);
	
	public abstract List<VehicleGroup> findVehicleGroupsByCompany(String companyID);

}
