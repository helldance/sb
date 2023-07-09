package com.coordsafe.vehicle.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.NonUniqueResultException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.common.entity.LatLng;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.vehicle.dao.VehicleDAO;
import com.coordsafe.vehicle.dao.VehicleGroupDAO;
import com.coordsafe.vehicle.entity.MinimalVehicle;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;


@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private VehicleGroupDAO vgroupDao;


	@Override
	public void persist(Vehicle transientInstance) {
		// TODO Auto-generated method stub
		try {
			vehicleDAO.persist(transientInstance);
		}
		catch (RuntimeException ex){
			throw ex;
		}
	}

	@Override
	public void attachDirty(Vehicle instance) {
		// TODO Auto-generated method stub
		vehicleDAO.attachClean(instance);
	}

	@Override
	public void attachClean(Vehicle instance) {
		// TODO Auto-generated method stub
		vehicleDAO.attachClean(instance);
	}

	@Override
	public void delete(Vehicle persistentInstance) {
		// TODO Auto-generated method stub
		vehicleDAO.delete(persistentInstance);
	}

	@Override
	public Vehicle merge(Vehicle detachedInstance) {
		// TODO Auto-generated method stub
		return vehicleDAO.merge(detachedInstance);
	}

	@Override
	public Vehicle findVehicleById(long id) {
		// TODO Auto-generated method stub
		return vehicleDAO.findVehicleById(id);
	}

	@Override
	public List<Vehicle> findByExample(Vehicle instance) {
		// TODO Auto-generated method stub
		return vehicleDAO.findByExample(instance);
	}

	@Override
	public List<Vehicle> findAll() {
		// TODO Auto-generated method stub
		return vehicleDAO.findAll();
	}

	@Override
	public void delete(String name) {
		if (vehicleDAO.findVehicleByName(name) == null) {
			try {
				throw new Exception("Vehicle " + name
						+ " does not exists.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				vehicleDAO.delete(vehicleDAO.findVehicleByName(name));
			}
			catch (NonUniqueResultException ex){
				throw ex;
			}
		}
	}

	@Override
	public Vehicle findVehicleByName(String name) {
		// TODO Auto-generated method stub
		return vehicleDAO.findVehicleByName(name);
	}

	@Override
	public List<Vehicle> findVehiclesByCompany(String companyID) {
		// TODO Auto-generated method stub
		return vehicleDAO.findVehiclesByCompany(companyID);
	}

	@Override
	public void update(Vehicle detachedInstance) {
		
		vehicleDAO.update(detachedInstance);
		
		
	}

	@Override
	public List<Vehicle> findAllNoLocator() {
		// TODO Auto-generated method stub
		return vehicleDAO.findAllNoLocator();
	}

	@Override
	public List<Vehicle> findNotAssignedVehiclesByCompany(String name) {
		// TODO Auto-generated method stub
		return vehicleDAO.findNotAssignedVehiclesByCompany(name);
	}
	
	@Override
	public List<Vehicle> findVehicleByGroupId(long groupId) {
		// TODO Auto-generated method stub
		return vehicleDAO.findVehicleByGroupId(groupId);
	}

	@Override
	public String findMinimalVehicleByCompany(String companyId) {		
		List<HashMap<String, List<MinimalVehicle>>> grpVehicles = new ArrayList<HashMap<String, List<MinimalVehicle>>>();
		List<VehicleGroup> vgs = vgroupDao.findVehicleGroupsByCompany(companyId);
		
		for (VehicleGroup vg : vgs){
			List<Vehicle> vcs = vehicleDAO.findVehicleByGroupId(vg.getId());
			List<MinimalVehicle> mvcs = new ArrayList<MinimalVehicle>();
			
			if (vcs != null){
				for (Vehicle v : vcs){
					Locator loc = v.getLocator();
					MinimalVehicle mv = null;
					
					// vehicle isntalled locator
					if (loc != null){
						GpsLocation gps = loc.getGpsLocation();
						
						if (gps != null){
							Long l = v.getMileage();
							
							if (null == l){
								l = 0L;
							}
							
							mv = new MinimalVehicle(v.getId(), v.getName(), v.getType(), v.getModel(), 
									v.getLicensePlate(), "", new LatLng(gps.getLatitude(), gps.getLongitude()), 
									v.getStatus(), gps.getSpeed(), l, gps.getAltitude(), loc.getLastLocationUpdate(), loc.getId());
						}
						
						else {
							mv = new MinimalVehicle(v.getId(), v.getName(), v.getType(), v.getModel(), 
								v.getLicensePlate(), "", new LatLng(0d, 0d), 
								v.getStatus(), 0, v.getMileage(), 0, 
								loc.getLastLocationUpdate(), loc.getId());
						}
						// only vehicles with locator installed is returned.
						mvcs.add(mv);
					}
					/*//vehicle is not installed with locator
					else{
						mv = new MinimalVehicle(v.getId(), v.getName(), v.getType(), v.getModel(), 
								v.getLicensePalette(), "DUMMY", null, false, 0, 0, null, -1);
					}*/
					
					//mvcs.add(mv);
				}
			}
			
			HashMap<String, List<MinimalVehicle>> hm = new HashMap<String, List<MinimalVehicle>>();
			hm.put(vg.getGroupName(), mvcs);
			
			grpVehicles.add(hm);
		}
				
		// build json string
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		
		try {
			jsonStr = mapper.writeValueAsString(grpVehicles);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonStr;	
	}

	@Override
	public Vehicle findVehicleByLocatorId(long locatorId) {
		return vehicleDAO.findVehicleByLocatorId(locatorId);
	}

	@Override
	public long create(Vehicle persistentInstance) {
		return vehicleDAO.create(persistentInstance);
	}
}
