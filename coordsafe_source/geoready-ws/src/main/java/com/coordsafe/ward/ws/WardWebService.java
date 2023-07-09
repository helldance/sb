package com.coordsafe.ward.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import com.coordsafe.ward.entity.Ward;

/**
 * @author Yang Wei
 * @Date Dec 5, 2013
 */
@Path("/ward")
public interface WardWebService {
	@GET
	@Path("/list/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGuardianWards(@HeaderParam("guardianId") long guardianId, @PathParam("type") String type);
	
	@GET
	@Path("/location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refreshWards(@HeaderParam("key") String key);
	
	@GET
	@Path("{wardId}/location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWardLocation(@HeaderParam("key") String key, @PathParam("wardId") long wardId);
	
	@POST
	@Consumes("multipart/form-data")
	public Response addWard(@Context MessageContext ctx, @Multipart(value = "name", required = true) String name, 
			@Multipart(value = "imei", required = false) String imei, 
			@Multipart(value = "extension", required = false) String extension, 
			@Multipart(value = "photo", required = false) byte [] imgData);
	
	@DELETE
	@Path("/{wardId}")
	public Response removeWard(@Context MessageContext ctx, @PathParam("wardId") long wardId);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateWard(Ward ward);
	
	@PUT
	@Path("/{wardId}")
	public Response updateWard(@Context MessageContext ctx, @PathParam("wardId") long wardId, @FormParam("name") String name, @FormParam("imei") String imei);
	
	@POST
	@Path("/{wardId}/photo")
	@Consumes("multipart/form-data")
	public Response updateWardPic(@Context MessageContext ctx, @PathParam("wardId") long wardId, @Multipart("extension") String extension, @Multipart("photo") byte [] imgData);
	
	@POST
	@Path("/{wardId}/deviceByImei")
	public Response updateWardDeviceByImei(@PathParam("wardId") long wardId, @HeaderParam("imei") String imei);
	
	@POST
	@Path("/{wardId}/deviceById")
	public Response updateWardDeviceById(@PathParam("wardId") long wardId, @HeaderParam("locatorId") String locatorId);
	
	@POST
	@Path("/devices/allocate")
	public Response randomAssignDevice (@HeaderParam("guardianId") long guardianId);
	
	@GET
	@Path("/{wardId}/history/{timeRange}")
	public String findWardHistoryByTime(@PathParam("wardId") long wardId, @PathParam("timeRange") String timeRange);
	
	@GET
	@Path("/{wardId}")
	public String getSingleWard(@PathParam("wardId") long wardId);
}
