/**
 * @author Yang Wei
 * @Date Jul 11, 2013
 */
package com.coordsafe.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coordsafe.api.entity.ApiKey;
import com.coordsafe.api.entity.ApiRequest;
import com.coordsafe.api.service.ApiRequestService;
import com.coordsafe.api.service.ApiService;
import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.service.UserService;

/**
 * @author Yang Wei
 *
 */
@Controller
@RequestMapping("/web")
public class ApiRequestController {
	@Autowired
	private ApiRequestService arSvrs;
	
	@Autowired
	private UserService usrSvrs;
	
	@Autowired
	private ApiService keySvrs;
	
	@RequestMapping(value="/api-statistic", method=RequestMethod.GET)
	public String getPage (HttpServletRequest request, Model model){
		//model.addAttribute("key", keySvrs.getById(keyId));
		//model.addAttribute("usage", ars);
		User usr = usrSvrs.findByUsername(request.getUserPrincipal().getName());
		ApiKey key = null;
		
		if (usr != null){
			key = usr.getApiKey();
		}
		
		model.addAttribute("key", key);
		
		return "web/api-statistic";
	}
	
	@RequestMapping(value="/api-statistic/{keyId}", method=RequestMethod.GET)
	public @ResponseBody String getUsage (@PathVariable("keyId") long keyId, @RequestParam(value = "timeRange", required = false) String timeRange, Model model){
		Date _dtStart = null, _dtEnd = null;
		SimpleDateFormat sdf = new SimpleDateFormat(PortalAppConfig.SDF_DATE);
		
		if (timeRange != null && timeRange != ""){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf.parse(stm);
				_dtEnd = sdf.parse(etm);
				// search is beyond cur date
				_dtEnd = new Date(_dtEnd.getTime() + 24*3600*1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {//default will retrieve last 3 days
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE) + 1);
			_dtEnd = c.getTime();
			
			c.setTimeInMillis(c.getTimeInMillis() - 7 * 24 * 3600 * 1000);
			_dtStart = c.getTime();
		}
		
		List<ApiRequest> ars = arSvrs.findRequestByTime(keyId, _dtStart, _dtEnd);
		
		/*model.addAttribute("key", keySvrs.getById(keyId));
		model.addAttribute("usage", ars);
		
		return "web/api-statistic";*/
		ObjectMapper mapper = new ObjectMapper();
		String jstr = null;
				
		try {
			jstr = mapper.writeValueAsString(ars);
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
		
		return jstr;
	}
	
	@RequestMapping(value="/api-statistic/all", method=RequestMethod.GET)
	public @ResponseBody String getUsageforAll (@RequestParam(value = "timeRange", required = false) String timeRange){
		Date _dtStart = null, _dtEnd = null;
		SimpleDateFormat sdf = new SimpleDateFormat(PortalAppConfig.SDF_DATE);
		
		if (timeRange != null && timeRange != ""){
			String [] timeslice = timeRange.split(",");
		
			String stm = timeslice[0];
			String etm = timeslice[1];
			
			try {
				_dtStart = sdf.parse(stm);
				_dtEnd = sdf.parse(etm);
				// search is beyond cur date
				_dtEnd = new Date(_dtEnd.getTime() + 24*3600*1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {//default will retrieve last 3 days
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE) + 1);
			_dtEnd = c.getTime();
			
			c.setTimeInMillis(c.getTimeInMillis() - 7 * 24 * 3600 * 1000);
			_dtStart = c.getTime();
		}
		
		List<Map<ApiKey, Integer>> ars = arSvrs.findAllUsageByTime(_dtStart, _dtEnd);
		
		/*model.addAttribute("key", keySvrs.getById(keyId));
		model.addAttribute("usage", ars);
		
		return "web/api-statistic";*/
		ObjectMapper mapper = new ObjectMapper();
		
		// Json does not support object as key, so add custom built serializer
		SimpleModule module = new SimpleModule("ApiKeyModule", new Version(1, 0, 0, null));
		module.addKeySerializer(ApiKey.class, new ApiKeySerializer());
		mapper.registerModule(module);
		
		String jstr = null;
						
		try {
			jstr = mapper.writeValueAsString(ars);
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
		
		return jstr;
	}
	
	class ApiKeySerializer extends JsonSerializer<ApiKey> {

		@Override
		public void serialize(ApiKey key, JsonGenerator jgen, SerializerProvider provider) throws IOException,
				JsonProcessingException {
			// TODO Auto-generated method stub
			//jgen.writeObject(key);
			jgen.writeStartObject();
			jgen.writeNumberField("id", key.getId());
			jgen.writeStringField("key", key.getKey());
			jgen.writeEndObject();
		}
		
	}
}
