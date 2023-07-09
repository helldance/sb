package com.coordsafe.vehicle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.vehicle.dao.VehicleGroupDAO;
import com.coordsafe.vehicle.entity.VehicleGroup;

@Service
public class VehicleGroupServiceImpl implements VehicleGroupService {
	@Autowired
	VehicleGroupDAO vgDAO;
	@Override
	public void create(VehicleGroup group) {
		vgDAO.create(group);
	}

	@Override
	public void update(VehicleGroup group) {
		vgDAO.update(group);
	}

	@Override
	public void delete(long id) {
		vgDAO.delete(id);
	}

	@Override
	public VehicleGroup findVehicleGroupById(long id) {
		return vgDAO.findVehicleGroupById(id);
	}

	@Override
	public void delete(String name) {
		// TODO Auto-generated method stub
		vgDAO.delete(name);
	}

	@Override
	public List<VehicleGroup> findAll() {
		// TODO Auto-generated method stub
		return vgDAO.findAll();
	}

	@Override
	public VehicleGroup findByName(String name) {
		// TODO Auto-generated method stub
		return vgDAO.findByName(name);
	}

	@Override
	public List<VehicleGroup> findVehicleGroupsByCompany(String companyID) {
		// TODO Auto-generated method stub
		return vgDAO.findVehicleGroupsByCompany(companyID);
	}

}
