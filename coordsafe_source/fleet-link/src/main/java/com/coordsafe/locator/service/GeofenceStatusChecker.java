package com.coordsafe.locator.service;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.service.GeofenceService;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;

/**
 * @author Yang Wei
 * @Date Jun 30, 2014
 */
@Service
public class GeofenceStatusChecker {
	private Log logger = LogFactory.getLog(GeofenceStatusChecker.class);
	
	@Autowired private VehicleService vehicleSvrs;
	
	@Autowired private GeofenceService geofenceSvrs;
	
	public void checkFenceStatus (Locator l, Point p){
		Vehicle v = vehicleSvrs.findVehicleByLocatorId(l.getId());
		Set<Geofence> fences = v.getFences();
		
		logger.info("fences: " + fences.size());
		
		for (Geofence gf : fences){
			if (geofenceSvrs.withinWard(p, gf)){
				logger.info("--------------------- " + v.getName() + " in " + gf.getDescription());
			}
			else {
				logger.info("--------------------- " + v.getName() + " out " + gf.getDescription());
			}
		}
	}
}
