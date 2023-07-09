package com.coordsafe.httpgateway.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.coordsafe.httpgateway.entity.Locator;
import com.coordsafe.httpgateway.entity.PanicAlarm;
import com.coordsafe.httpgateway.service.WardMobileService;
import com.coordsafe.httpgateway.ws.WardMobileWebService;

public class WardMobileWebServiceImpl implements WardMobileWebService {
	static final Logger log = Logger.getLogger(WardMobileWebServiceImpl.class);
	
	@Autowired
	private WardMobileService wardMobileService;

	public Response sendLocation(Locator locator) {		
		if (wardMobileService.sendLocation(locator)){
			
			ResponseBuilder builder = Response.status(Status.OK);
			builder.entity(null);
			
			return builder.build();
		}

		return null;
	}

	public Response authenticate(Locator locator) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (wardMobileService.authenticate(locator)){
			resultMap.put("flag", "1");
			resultMap.put("service_id", "001");
			resultMap.put("msg_direction", "002");
			resultMap.put("msg_type", "001");
			resultMap.put("serv_ip", "0.0.0.0");
			resultMap.put("serv_time", System.currentTimeMillis());
		}
		
		ResponseBuilder builder = Response.status(Status.OK);
		builder.type("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String resultStr = mapper.writeValueAsString(resultMap);
			builder.entity(resultStr);
			
			System.out.println(resultStr);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.build();
	}

	@Override
	public Response sendPanicAlarm(PanicAlarm alarm) {
		
		if (wardMobileService.sendPanicAlarm(alarm)){
			ResponseBuilder builder = Response.status(Status.OK);
			builder.entity(null);
			
			return builder.build();		
		} 
	
		return null;
	}

	public Response sendStatus(com.coordsafe.httpgateway.entity.Status status) {
		if (wardMobileService.sendStatus(status)){
			ResponseBuilder builder = Response.status(Status.OK);
			builder.entity(null);
			
			return builder.build();
		} 
		
		return null;
	}
}