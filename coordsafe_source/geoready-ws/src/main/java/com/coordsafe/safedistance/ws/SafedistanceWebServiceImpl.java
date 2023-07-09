package com.coordsafe.safedistance.ws;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.guardian.service.GuardianService;
import com.coordsafe.safedistance.entity.Safedistance;
import com.coordsafe.safedistance.service.SafedistanceService;
import com.coordsafe.ward.entity.Ward;
import com.coordsafe.ward.service.WardService;

/**
 * @author Yang Wei
 * @Date Feb 3, 2014
 */
@Path("/safedistance")
public class SafedistanceWebServiceImpl implements SafedistanceWebService {
	private ResponseBuilder builder = Response.status(Status.OK);
	
	@Autowired 
	private GuardianService guardianSvrs;
	
	@Autowired
	private WardService wardSvrs;
	
	@Autowired
	private SafedistanceService distanceSvrs;
	
	@Override
	@POST
	public Response addSafedistance(@Context MessageContext ctx, @FormParam("duration") int mins, @FormParam("distance") double distance,
			@FormParam("wardId") String ward) {		
		String requestKey = ctx.getHttpHeaders().getRequestHeader("key").get(0);
		
		Guardian g = guardianSvrs.getByApiKey(requestKey);
		
		Safedistance dist = new Safedistance(distance, mins, g);
		
		String[] wardIds = ward.split(",");
		Set<Ward> wards = new HashSet<Ward>();
		
		for (String wId : wardIds){
			Ward w = wardSvrs.findByID(Long.valueOf(wId));
			
			wards.add(w);
		}
		
		dist.setWards(wards);
		dist.setActive(true);
		
		distanceSvrs.create(dist);
		
		g.setSafedistance(dist);
		
		guardianSvrs.update(g);
		
		builder.entity("Safedistance activated");
		
		return builder.build();
	}

	@Override
	@GET
	@Path("deactivate/{guardianId}")
	public Response deactivateSafedistance(@PathParam("guardianId") long guardianId) {
		Safedistance dist = distanceSvrs.findByGuardianId(guardianId);
		//dist.setActive(false);
		
		if (dist != null){		
			dist.setWards(null);
			//dist.setGuardian(null);
			
			Guardian g = guardianSvrs.get((int) guardianId);
			g.setSafedistance(null);
			
			guardianSvrs.update(g);
			
			distanceSvrs.update(dist);
			
			distanceSvrs.delete(dist.getId());
		}
		
		return builder.build();
	}

}
