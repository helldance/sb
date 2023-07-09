package com.coordsafe.vehicle.ws;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author Yang Wei
 * @Date Mar 11, 2014
 */
public interface VehicleWebService {
	public Response getVehicleByCompany(@PathParam("companyId") String companyId);
	
	//public Response getVehicleByCompanyMulti();
}
