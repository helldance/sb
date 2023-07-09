package com.coordsafe.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.coordsafe.common.entity.LatLng;
import com.coordsafe.event.entity.GenericEvent;
import com.coordsafe.event.entity.WardEvent;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.notification.entity.EventMessage;
import com.coordsafe.notification.entity.WardNotificationSetting;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.safedistance.entity.Safedistance;
import com.coordsafe.ward.entity.Ward;

/**
 * @author Yang Wei
 * @Date Feb 6, 2014
 */
public class SafedistanceTask implements Runnable {
	private Safedistance dist;
	private static Map<Long, Integer> counterMap;
	
	private static Log log = LogFactory.getLog(SafedistanceTask.class);
	private TopicMessageProducer producer;
	
	public SafedistanceTask (Safedistance dist, TopicMessageProducer producer){
		this.dist = dist;
		this.producer = producer;
	}
		
	@Override
	public void run() {
		Guardian g = dist.getGuardian();
		Set<Ward> wards = dist.getWards();
		
		for (Ward w : wards){
			Locator loc = w.getLocator();
			
			if (loc != null){
				long diffInMins = (Math.abs(loc.getLastLocationUpdate().getTime() - new Date().getTime()) / 1000) / 60;
				
				if (diffInMins > 30){
					// ward location is 30 mins ago, notify user
					log.info("Ward " + w.getName() + " location is " + diffInMins + " mins ago");
					
					GenericEvent event = new WardEvent();
					event.setType("Safedistance");
					
					// showing distance to user?
					String errorMsg = String.format("Safedistance is activated but %s last location is %s mins ago", w.getName(), diffInMins);
					
					event.setMessage(errorMsg);
					((WardEvent) event).setWard(w);
					
					WardNotificationSetting wns = new WardNotificationSetting();				
					wns.setEventType("Safedistance");
					
					wns.setWard(w);
					wns.setAppPush(true);
					wns.setEmailEnable(false);
					wns.setSmsEnable(false);
					
					try {
						producer.sendMessages(new EventMessage(event, wns));
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
				else {
					GpsLocation l = loc.getGpsLocation();
				
					double lat2 = l.getLatitude();
					double lng2 = l.getLongitude();
					
					LatLng glaln = g.getLocation();
					
					double dis = calcDistance(glaln.getLongitude(), glaln.getLatitude(), lng2, lat2);
					long wd = w.getId();
					
					if (counterMap == null){
						counterMap = new HashMap<Long, Integer>();
					}
					
					log.info(counterMap);
					
					// allow +- 10m fault tolerance?
					if (dis > (dist.getDistance() + 10)){
						// update counter map
						synchronized(SafedistanceTask.class) {												
							int c = counterMap.containsKey(wd)?counterMap.get(wd):0;
								
							if (c > 0){
								//counterMap.put(wd, ++c);								
	
								// push notification to app;
								log.info("Ward " + w.getName() + " is beyond safedistance! ");
								
								GenericEvent event = new WardEvent();
								event.setType("Safedistance");
								
								// showing distance to user?
								String alarmMsg = String.format("%s is out of your reach", w.getName());
								
								event.setMessage(alarmMsg);
								((WardEvent) event).setWard(w);
								
								WardNotificationSetting wns = new WardNotificationSetting();				
								wns.setEventType("Safedistance");
								
								wns.setWard(w);
								wns.setAppPush(true);
								wns.setEmailEnable(false);
								wns.setSmsEnable(false);
								
								try {
									producer.sendMessages(new EventMessage(event, wns));
								} catch (JMSException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								// insert into db as an alarm
								//ApplicationContext ctx= new ClassPathXmlApplicationContext("spring/root-context.xml");
								/*ApplicationContext ctx= new FileSystemXmlApplicationContext("file:/opt/apache-tomcat-7.0.42/webapps/geoready/WEB-INF/spring/root-context.xml");
								LocatorService locatorSvrs = (LocatorService) ctx.getBean("LocatorService");
								
								PanicAlarm alarm = new PanicAlarm(w.getLocator().getImeiCode(), w.getLocator().getGpsLocation().getLatitude(), 
										w.getLocator().getGpsLocation().getLongitude(), "Safe-Distance", alarmMsg, w);
								
								locatorSvrs.createPanicAlarm(alarm);*/
							}
	
							counterMap.put(wd, ++c);	
						}
					}
					else {
						// remove safedistance
						counterMap.remove(wd);
					}
				}
			}
		}
	}

	private double calcDistance (double long1, double lat1, double long2, double lat2) {
		double _radiusA = 6378137;
		
		double dLat = (lat2 - lat1) * (Math.PI / 180);
		double dLon = (long2 - long1) * (Math.PI / 180);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(lat1 * (Math.PI / 180))
				* Math.cos(lat2 * (Math.PI / 180)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		double dis = _radiusA * c;
		
		log.info("distance:  " + dis);
		
		return dis;
	}
	
	private double calcDistance2(double lng1, double lat1, double lng2, double lat2){
		double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    
	    return earthRadius * c;
	}
}
