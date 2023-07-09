package com.coordsafe.geofence;


import java.util.List;



import org.postgis.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.geofence.service.GeofenceService;


import org.slf4j.*;
@Service
public class GeofenceCommonService {
	private static final Logger log = LoggerFactory.getLogger(GeofenceCommonService.class);
	@Autowired
	GeofenceService geofenceService;
	
	public boolean within(Point point, String companyid, int zoneType){
		List<Geofence> geofences = geofenceService.within(point, companyid, zoneType);

		for(Geofence gf:geofences){
			
			log.info(gf.getGeometry().toText());
		}

		
		if(geofences.size() > 0)
			return true;
		return false;
		
	}

}
