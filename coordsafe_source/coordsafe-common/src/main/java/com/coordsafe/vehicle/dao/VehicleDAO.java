package com.coordsafe.vehicle.dao;

import java.util.List;

import com.coordsafe.vehicle.entity.Vehicle;


public interface VehicleDAO {

	public abstract void persist(Vehicle transientInstance);

	public abstract void attachDirty(Vehicle instance);

	public abstract void attachClean(Vehicle instance);

	public abstract void delete(Vehicle persistentInstance);
	
	public void delete(String name);
	
	public abstract long create(Vehicle persistentInstance);

	public abstract Vehicle merge(Vehicle detachedInstance);
	public abstract void update(Vehicle detachedInstance);


	public abstract Vehicle findVehicleById(long id);
	
	public abstract Vehicle findVehicleByName(String name);


	public abstract List<Vehicle> findByExample(Vehicle instance);
	
	public abstract List<Vehicle> findAll();
	public abstract List<Vehicle> findAllNoLocator();

	public abstract List<Vehicle> findVehiclesByCompany(String companyID);
	public List<Vehicle> findNotAssignedVehiclesByCompany(String name);

	public abstract List<Vehicle> findVehicleByGroupId(long groupId);

	public abstract Vehicle findVehicleByLocatorId(long locatorId);
}