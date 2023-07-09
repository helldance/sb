package com.coordsafe.vehicle.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.locator.entity.Locator;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;

/**
 * @author Yang Wei
 * @Date Mar 11, 2014
 */

@Path("/vehicle")
public class VehicleWebServiceImpl implements VehicleWebService {
	static final Log logger = LogFactory.getLog(VehicleWebServiceImpl.class);
	
	@Autowired
	private VehicleService vehicleSvrs;
	
	/* (non-Javadoc)
	 * @see com.coordsafe.vehicle.ws.VehicleWebService#getVehicleByCompany(long)
	 */
	@Override
	@GET
	@Path("/company/{companyId}")
	@Produces("application/json")
	public Response getVehicleByCompany(@PathParam("companyId") String companyId) {
		// TODO Auto-generated method stub
		String [] companies = companyId.split(",");
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for (int i = 0; i < companies.length; i ++){
			logger.info("find Vehicle by company " + companies[i]);
			
			vehicles.addAll(vehicleSvrs.findVehiclesByCompany(companies[i]));
		}
		
		//List<Vehicle> vehicles = vehicleSvrs.findVehiclesByCompany(String.valueOf(companyId));
		List<HashMap<String, String>> vehicleMap = new ArrayList<HashMap<String, String>>();
		
		ResponseBuilder builder = Response.status(Status.OK);
		
		for (Vehicle v : vehicles){
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("id", v.getId() + "");
			map.put("name", v.getName());
			map.put("license", v.getLicensePlate());
			map.put("model", v.getModel());
			map.put("type", v.getType());
			map.put("status", v.getStatus());
			map.put("group", v.getVehiclegroup().getGroupName());
			map.put("company", v.getCompany().getName());
			
			Locator l = v.getLocator();
			
			if (l != null){
				map.put("locatorModel", l.getModel());
				map.put("locatorId", l.getId() + "");
				map.put("locatorImei", l.getImeiCode());
				
				map.put("lat", l.getGpsLocation().getLatitude() + "");
				map.put("lng", l.getGpsLocation().getLongitude() + "");
			}
			
			vehicleMap.add(map);			
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		
		try {
			jsonStr = mapper.writeValueAsString(vehicleMap);
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
		
		builder.entity(jsonStr);
		
		return builder.build();
	}

}
