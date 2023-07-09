package com.coordsafe.notification.common;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;

@Service
public class PushService {
	static final String APNS_HOST = "gateway.sandbox.push.apple.com";
	static final int APNS_PORT = 2195;
	//static final String APNS_CERT = File.separator + "resources" + File.separator + "cert" + File.separator + "apns-dev-cert.p12";
	static final String APNS_CERT = "/opt/apache-tomcat-7.0.42/webapps/safe-link/resources/cert/apns-pro-cert.p12";
	//static final String APNS_CERT = "C:\\Users\\Ray\\Workspace\\safe-link\\src\\main\\webapp\\resources\\cert\\apns-dev-cert.p12";
	static final String GCM_ENDPOINT = "https://android.googleapis.com/gcm/send";
	static final String APNS_KEY = "http://";
	static final String GCM_KEY = "AIzaSyDeHj18BIH3lwNeUTmjzEe7pq4LjuE4vBs";
	
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost;
	HttpResponse response;
	
	static final Log log = LogFactory.getLog(PushService.class);
	
	public void pushToApns (List<String> deviceTokens, String payload){
		try {
			ApnsService service = APNS.newService()
				    .withCert(APNS_CERT, "123456")
				    .withProductionDestination()
				    .build();
			
			//String payload = APNS.newPayload().alertBody("Can't be simpler than this!").build();
			//String token = "fedfbcfb....";
			Map<String, Date> inactiveDevices = service.getInactiveDevices();
			
			// NO NEED !filter out inactive devices

			Collection<? extends ApnsNotification> pushes = service.push(deviceTokens, payload);
			
			log.info("send to APNS..\n" + pushes + "\n" + inactiveDevices);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void pushToGcm (List<String> deviceTokens, String payload){
		httppost = new HttpPost(GCM_ENDPOINT);
		
		httppost.setHeader("Authorization", "key=" + GCM_KEY);
		httppost.setHeader("Content-Type", "application/json");
		
		//put registration ids
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		dataMap.put("message", payload);
		
		map.put("registration_ids", deviceTokens);
		map.put("data", dataMap);
		
		ObjectMapper mapper = new ObjectMapper();
		StringEntity strEntity = null;
		
		try {
			String s = mapper.writeValueAsString(map);			
			
			log.info("GCM Message" + s);
			
			strEntity= new StringEntity(s);			
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		

		httppost.setEntity(strEntity);
		
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			log.info("send to GCM: " + EntityUtils.toString(entity, "UTF-8"));
			
			// just for consume the entity to release current thread
			EntityUtils.consume(response.getEntity());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pushToMqtt (){
		
	}
}
