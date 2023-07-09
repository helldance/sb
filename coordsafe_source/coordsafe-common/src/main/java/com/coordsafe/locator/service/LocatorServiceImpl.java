package com.coordsafe.locator.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.common.entity.LatLng;
import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.constants.UserMessage;
import com.coordsafe.core.rbac.dao.UserDAO;
import com.coordsafe.event.dao.EventDAO;
import com.coordsafe.event.entity.EventType;
import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.locator.dao.LocatorDAO;
import com.coordsafe.locator.entity.DeviceStatus;
import com.coordsafe.locator.entity.ExtraCommand;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.service.TripService;
import com.coordsafe.vehicle.dao.VehicleDAO;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleStatus;
//import com.google.code.geocoder.model.LatLng;
//import javax.xml.rpc.ServiceException;
import com.coordsafe.ward.entity.Ward;

@Transactional(propagation = Propagation.REQUIRED)
@Service("locatorService")
public class LocatorServiceImpl implements LocatorService {
	private static Logger logger = Logger.getLogger(LocatorServiceImpl.class);
	private Map<Long, Integer> gpsCounter = new HashMap<Long, Integer> ();

	@Autowired
	private LocatorDAO locatorDao;

	@Autowired
	private UserDAO userDao;
	
	/*@Autowired
	private TripDao tripDao;*/
	@Autowired
	private TripService tripSrvs;
	
	@Autowired
	private EventDAO eventDao;
	
	@Autowired
	private VehicleDAO vehicleDao;
	
	public boolean authenticate(String imeiCode) {
		Locator locator = locatorDao.findByImeiCode(imeiCode);
		if (locator != null)
			return true;
		else
			return false;
	}

	@Override
	public Locator findLocatorByImei(String imeiCode) {
		return locatorDao.findByImeiCode(imeiCode);
	}	

	@Override
	public Locator findLocatorById(long locatorId) {
		return locatorDao.findLocatorById(locatorId);
	}	

	@Override
	public void updateLocator(Locator locator) {
		locatorDao.update(locator);
	}
	
	@Override
	public List<Locator> findAllLocators(){
		return locatorDao.findAllLocators();
	}

	@Override
	public void createLocator(Locator locator) {
		GpsLocation gpsLocation = new GpsLocation();
		DeviceStatus deviceStatus = new DeviceStatus();
		locator.setGpsLocation(gpsLocation);
		locator.setDeviceStatus(deviceStatus);
		
		locatorDao.create(locator);
	}

	@Override
	public void updateLocation(String imeiCode, double lat, double lng, long gpsTime, long hid) {
		Locator locator = locatorDao.findByImeiCode(imeiCode);
		Date now = new Date();
		
		if (locator != null) {			
			DeviceStatus deviceStatus = locator.getDeviceStatus();	
			Vehicle vehicle = vehicleDao.findVehicleByLocatorId(locator.getId());
			
			if (vehicle == null){
				logger.error("No vehicle assigned: " + locator.getId());
				
				// only update locator status??
			}

			// check if vehicle status: stopped - start
			if (!deviceStatus.getIsGpsOn()){
				//TODO: sendNotification()
						
				logger.info("locator started: " + locator.getLabel());
				
				if (vehicle != null){
					vehicle.setStatus(VehicleStatus.MOVING);
					vehicleDao.update(vehicle);
					
					LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
					
					VehicleEvent ve = new VehicleEvent(locator.getId(), EventType.VHC_START, UserMessage.VEHICLE_START,
							eventPlace, now, vehicle.getName(), vehicle.getCompany());

					eventDao.create(ve);
				}
				
				deviceStatus.setIsGpsOn(true);
				
				if (locator.getLastLocationUpdate() == null){
					locator.setLastLocationUpdate(now);
				}
								
				long diff = now.getTime() - locator.getLastLocationUpdate().getTime();
				
				if (diff >= PortalAppConfig.LOCATOR_STOP_DURATION){
					// create new trip
					Trip trip = new Trip();
					
					trip.setLocatorId(locator.getId());
					//TODO: To monitor accuracy -- not accurate.
					//LocatorLocationHistory his = locatorDao.findLastLocationByLocatorId(locator.getId());
					
					//if (his != null){
						// For locator, gps time is current time, 
						// for mobile phone, if no fix obtained, gps time is last location time/1970..
						//trip.setTripStartTime(new Date(gpsTime));
						trip.setTripStartId(hid);
						trip.setTripStartTime(now);
						
						tripSrvs.create(trip);
					
						logger.info("Trip started: " + locator.getLabel());
					//}
					
						LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
						
						VehicleEvent ve = new VehicleEvent(locator.getId(), EventType.TRIP_START, UserMessage.TRIP_START,
								eventPlace, locator.getLastLocationUpdate(), vehicle.getName(), vehicle.getCompany());
					
					eventDao.create(ve);
					
					// Send notifications to correspondent
					//topicNotificationMessageProducer.filterEventType(ve);
				}
			
				locator.setDeviceStatus(deviceStatus);
			}
			
			GpsLocation loc = locator.getGpsLocation();
			
			if (loc==null) 
				loc = new GpsLocation();
			
			// if gps not fixed, discarded value
			if (lat > 0.1 && lng > 0.1){
				loc.setLatitude(lat);
				loc.setLongitude(lng);
			}
			else{
				//TODO lost gps event? or gps not fixed
				// event place should be last location
			}
			
			locator.setGpsLocation(loc);
			// update server time to locator table (for correct chcking)
			locator.setLastLocationUpdate(now);
			
			locatorDao.update(locator);
		}
		else {
			logger.info("========find unregistered locator: " + imeiCode);
		}
	}
	
	// entry point for doing location update
	@Override
	public void updateLocation(String imeiCode, GpsLocation location, ExtraCommand extras, String ip, long hid) {
		// TODO Auto-generated method stub	
		Locator locator = locatorDao.findByImeiCode(imeiCode);
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		double speed = location.getSpeed();
		
		Date now = new Date();
		boolean firstUpdate = false;		
		
		if (locator != null) {			
			DeviceStatus deviceStatus = locator.getDeviceStatus();	
			Vehicle vehicle = vehicleDao.findVehicleByLocatorId(locator.getId());
			long locatorId = locator.getId();			
			
			GpsLocation loc = locator.getGpsLocation();
			
			if (loc==null) {
				loc = new GpsLocation();
			}
			
			deviceStatus.setIp(ip);
			
			deviceStatus.setBatteryLeft(extras.powerInternal?50:0);
			
			/*if (extras.powerInternal){
				
			}*/
			
			if (vehicle == null){
				logger.error("No vehicle assigned: " + locator.getId());
				
				// only update locator status??
				locator.setLastLocationUpdate(now);
				locator.setGpsLocation(new GpsLocation(lat, lng));
				
				locator.setDeviceStatus(deviceStatus);
				locatorDao.update(locator);
				
				return;
			}

			// step 1: check if vehicle status: stopped - start
			if (!deviceStatus.getIsGpsOn() || extras.tripStartSignal 
					|| ((vehicle.getStatus().equals(VehicleStatus.STOPPED) || vehicle.getStatus().equals(VehicleStatus.IDLE)) 
							&& !extras.powerInternal)){
				//TODO: sendNotification()						
				logger.info("Vehicle started: " + locator.getLabel());
				
				firstUpdate = true;
				extras.tripStartSignal = true;
				
				if (vehicle != null){
					vehicle.setStatus(VehicleStatus.MOVING);
					vehicleDao.update(vehicle);
					
					// use last location as first location is 0
					LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
					
					if (vehicle != null){
						VehicleEvent ve = new VehicleEvent(locatorId, EventType.VHC_START, UserMessage.VEHICLE_START,
								eventPlace, now, vehicle.getName(), vehicle.getCompany());
	
						eventDao.create(ve);
					}
				}
				
				deviceStatus.setIsGpsOn(true);
				
				if (locator.getLastLocationUpdate() == null){
					locator.setLastLocationUpdate(now);
				}
								
				long diff = now.getTime() - locator.getLastLocationUpdate().getTime();
				
				if (diff >= PortalAppConfig.LOCATOR_STOP_DURATION || extras.tripStartSignal){
					// create new trip
					Trip trip = new Trip();
					
					trip.setLocatorId(locatorId);
					//TODO: To monitor accuracy -- not accurate.

					//trip.setTripStartTime(new Date(gpsTime));
					trip.setTripStartId(hid);
					trip.setTripStartTime(now);
					
					tripSrvs.create(trip);
				
					logger.info("Trip started: " + locator.getLabel());
				
					// use last location as first location is 0
					if (vehicle != null){
						LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
						
						VehicleEvent ve = new VehicleEvent(locatorId, EventType.TRIP_START, UserMessage.TRIP_START,
								eventPlace, now, vehicle.getName(), vehicle.getCompany(), trip);
					
						eventDao.create(ve);
					}
					
					// Send notifications to correspondent
					//topicNotificationMessageProducer.filterEventType(ve);
				}
			
				locator.setDeviceStatus(deviceStatus);
			}			
			
			// step 2: pause or stop
			if (speed <= 1){// potential pause
				if (!firstUpdate){
					Trip curTrip = tripSrvs.findCurrentOrLastTrip(locatorId);
					LatLng eventPlace;
										
					if (curTrip != null){
						String status = vehicle.getStatus();
						
						logger.info("extras: " + extras.stopSignal);
						
						// vehicle stop signal can be check here or #MOVED OUT#
						if (extras.stopSignal && !status.equals(VehicleStatus.STOPPED)){
							// update trip stop
							LocatorLocationHistory history = locatorDao.findLastLocationByLocatorId(locatorId);
							Date lastUpdate = locator.getLastLocationUpdate();
							
							if (curTrip != null && history != null && curTrip.getTripStartId() != 0
									&& curTrip.getTripEndId() == 0){
								logger.info("Trip ended: " + locator.getLabel());
								
								eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());

								if (lastUpdate.after(curTrip.getTripStartTime())){
									curTrip.setTripEndId(history.getId());
									//trip.setTripEndTime(history.getLocation_time());
									// Use in case gps not fix.
									curTrip.setTripEndTime(lastUpdate);		
									
									if (vehicle != null){
										VehicleEvent ve2 = new VehicleEvent(locator.getId(), EventType.TRIP_END, UserMessage.TRIP_STOP,
												eventPlace, locator.getLastLocationUpdate(), vehicle.getName(), vehicle.getCompany(), curTrip);
									
										eventDao.create(ve2);
									}
								}
								
								// Vehicle stopped
								if (vehicle != null){
									vehicle.setStatus(VehicleStatus.STOPPED);
									vehicleDao.update(vehicle);
																	
									VehicleEvent ve3 = new VehicleEvent(locator.getId(), EventType.VHC_STOP, UserMessage.VEHICLE_STOP,
											eventPlace, locator.getLastLocationUpdate(), vehicle.getName(), vehicle.getCompany());

									
									eventDao.create(ve3);
									
									//topicNotificationMessageProducer.filterEventType(ve);
								}
							}
						}
						else if (status.equals(VehicleStatus.MOVING)){
							// increase pause count - YW 2013/07/09
							vehicle.setStatus(VehicleStatus.PAUSED);
							
							if (!(lat == 0 || lng == 0)){
								eventPlace = new LatLng(lat, lng);
								
								VehicleEvent ve = new VehicleEvent(locatorId, EventType.VHC_PAUSE, UserMessage.VHC_PAUSED,
										eventPlace, new Date(location.getTime()), vehicle.getName(), vehicle.getCompany(), curTrip);
							
								eventDao.create(ve);
													
								curTrip.increatePause();
							}
						}
						
						tripSrvs.update(curTrip);
					}
				}
			}
			else{
				// increase speeding count - YW 2013/07/09
				if (speed > PortalAppConfig.MAX_SPEED && !vehicle.getStatus().equalsIgnoreCase(VehicleStatus.SPEEDING)){
					vehicle.setStatus(VehicleStatus.SPEEDING);
					Trip curTrip = tripSrvs.findCurrentOrLastTrip(locatorId);
					
					if (curTrip != null){
						curTrip.increateSpeeding();
						
						tripSrvs.update(curTrip);
					}
					
					//TODO add speeding event!!!
					LatLng eventPlace = new LatLng(lat, lng);
					
					VehicleEvent ve = new VehicleEvent(locatorId, EventType.VHC_SPEEDING, UserMessage.VHC_SPEEDING,
							eventPlace, new Date(location.getTime()), vehicle.getName(), vehicle.getCompany(), curTrip);
				
					eventDao.create(ve);
				}
				else if(!vehicle.getStatus().equalsIgnoreCase(VehicleStatus.MOVING)){
					vehicle.setStatus(VehicleStatus.MOVING);
				}
			}
			
			// if gps not fixed, discarded value
			if (lat > 0.1 && lng > 0.1){
				loc.setLatitude(lat);
				loc.setLongitude(lng);
				// also update speed.- 20130712 YW
				loc.setSpeed((float) speed); 

				int count = gpsCounter.get(locatorId) == null? 0: gpsCounter.get(locatorId);
				
				if (count > 40){
					logger.info("gps restored for locator: " + locatorId);
					
					LatLng eventPlace = new LatLng(lat, lng);
					Trip curTrip = tripSrvs.findCurrentOrLastTrip(locatorId);
					
					vehicle.setStatus(VehicleStatus.GPS_RESTORED);

					VehicleEvent ve = new VehicleEvent(locatorId, EventType.GPS_RESTORED, UserMessage.GPS_RESTORED,
							eventPlace, now, vehicle.getName(), vehicle.getCompany(), curTrip);
				
					eventDao.create(ve);
				}

				gpsCounter.remove(locatorId);
				
				//logger.info(String.format("gps fixed, remove from map, locatorid: %s", locatorId));
			}
			else{
				//TODO lost gps event? or gps not fixed
				// event place should be last location
				
				int count = gpsCounter.get(locatorId) == null? 0: gpsCounter.get(locatorId);
				
				gpsCounter.put(locatorId, ++count);			
								
				logger.info(String.format("no gps fix, count: %s, locatorid: %s", count, locatorId));
				
				LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
				Trip curTrip = tripSrvs.findCurrentOrLastTrip(locatorId);
				
				if (count > 120){ // arnd 10 min
					logger.info(String.format("gps might spoiled for locator: %s", locatorId));
					
					if (count == 121){
						vehicle.setStatus(VehicleStatus.BROKEN);
	
						VehicleEvent ve = new VehicleEvent(locatorId, EventType.GPS_BROKEN, UserMessage.GPS_BROKEN,
								eventPlace, now, vehicle.getName(), vehicle.getCompany(), curTrip);
					
						eventDao.create(ve);
					}
				}
				else if (count > 40){// arnd 3 mins
					logger.info(String.format("still no gps fix for locator: %s", locatorId));
					
					if (count == 41){
						vehicle.setStatus(VehicleStatus.NOFIX);
						
						VehicleEvent ve = new VehicleEvent(locatorId, EventType.GPS_LOST, UserMessage.GPS_LOST,
								eventPlace, now, vehicle.getName(), vehicle.getCompany(), curTrip);
					
						eventDao.create(ve);
					}
				}
			}
			
			locator.setGpsLocation(loc);
			
			// update server time to locator table (for correct chcking)
			locator.setLastLocationUpdate(now);
			locator.setDeviceStatus(deviceStatus);
			
			locatorDao.update(locator);
		}
		else {
			logger.info("========find unregistered locator: " + imeiCode);
		}
	}
	
	@Override
	public void updateStatus(String imeiCode, String ip, Boolean isGsmOn,
			Boolean isGpsOn, Integer batteryLevel, Integer networkStatus, long gpsTime) {
		Locator locator = locatorDao.findByImeiCode(imeiCode);
		if (locator != null) {
			DeviceStatus deviceStatus = locator.getDeviceStatus();
			deviceStatus.setIp(ip);			
			deviceStatus.setIsGpsOn(isGpsOn);
			deviceStatus.setIsGsmOn(isGsmOn);
			deviceStatus.setBatteryLeft(batteryLevel);
			deviceStatus.setNetworkAvailability(networkStatus);
			locator.setDeviceStatus(deviceStatus);
			locator.setLastStatusUpdate(new Date(gpsTime));
			locatorDao.update(locator);
		}
	}

	@Override
	@CacheEvict(value="guardianCache", key="#ward.getGuardians().toArray()[0].getId()") 
	public void sendPanicAlarm(String imei, String time, Double lat, Double lng,String alarmType, String alarmMessage,Ward ward) {
		PanicAlarm panicAlarm = new PanicAlarm(imei,lat,lng,alarmType,alarmMessage,ward);
		createPanicAlarm(panicAlarm);
		
	}

	@Override
	public void createPanicAlarm(PanicAlarm panicAlarm) {
		locatorDao.createPanicAlarm(panicAlarm);
	}

	@Override
	@CacheEvict(value="guardianCache", allEntries=true)
	public void acknowledgePanicAlarm(PanicAlarm panicAlarm) {
		panicAlarm.setAcknowledged(true);
		locatorDao.updatePanicAlarm(panicAlarm);
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByImei(String imei) {
		return null;
	}

	@Override
	public long createLocationHistory(LocatorLocationHistory history) {			
		return locatorDao.createLocationHistory(history);
	}

	@Override
	public List<LocatorLocationHistory> getLocationHistroybyImei(String imei, Date startTime, Date endTime) {
		Locator locator = locatorDao.findByImeiCode(imei);

		return locatorDao.getLocationHistroybyId(locator.getId(), startTime, endTime);
	}

	@Override
	public void deleteLocator(String imeiCode) {
		Locator locator = locatorDao.findByImeiCode(imeiCode);
		if (locator !=null)
			locatorDao.delete(locator);	
	}

	@Override
	public List<Locator> findLocatorByAssignTo (long assignTo) {
		return locatorDao.findLocatorByAssignTo (assignTo);
	}

	@Override
	public List<LocatorLocationHistory> findLocationHistoryByTime(
			String locatorId, String startTime, String endTime) {
		return locatorDao.findLocationHistoryByTime(locatorId, startTime, endTime);
	}

	@Override
	public LocatorLocationHistory findLocationByTime(String locatorId, String time) {		
		return locatorDao.findLocationByTime(locatorId, time);
	}

	@Override
	public List<LocatorLocationHistory> findLocationHistoryByTrip(long tripId) {
		Trip trip = tripSrvs.findById(tripId);
		
		if (trip == null)
			return null;
		
		return locatorDao.findLocationHistoryByTrip(trip.getLocatorId(), trip.getTripStartId(), trip.getTripEndId());
	}

	@Override
	public List<LocatorLocationHistory> findLocationHistoryByTrip(long locatorId, long tripStartId, long tripEndId) {
		return locatorDao.findLocationHistoryByTrip(locatorId, tripStartId, tripEndId);
	}

	@Override
	public LocatorLocationHistory findLastLocationByLocatorId(long locatorId) {
		// TODO Auto-generated method stub
		return locatorDao.findLastLocationByLocatorId(locatorId);
	}

	@Override
	public List<LocatorLocationHistory> findInbetweenLocation(long locatorId,
			long tripStartId, long tripEndId) {
		// TODO Auto-generated method stub
		return locatorDao.findInbetweenLocation(locatorId, tripStartId, tripEndId);
	}

	@Override
	public List<LocatorLocationHistory> findCurTripLocationHistory(Trip trip) {
		// TODO Auto-generated method stub
		//return locatorDao.findCurTripLocationHistory(trip);
		if (trip != null){
			return locatorDao.findLocationHistoryByTrip(trip.getLocatorId(), trip.getTripStartId(), trip.getTripEndId());
		}
		
		return null;
	}	

	@Override
	@CacheEvict(value="guardianCache", allEntries=true) 
	public void updateLocationForWARD(String imeiCode, GpsLocation location,
			ExtraCommand ec, String ip, long hid) {
		
		Locator locator = locatorDao.findByImeiCode(imeiCode);
		
		GpsLocation loc = locator.getGpsLocation();
		if (loc==null) 
			loc = new GpsLocation();
		
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		double speed = location.getSpeed();
		// if gps not fixed, discarded value
		if (lat > 0.1 && lng > 0.1){
			loc.setLatitude(lat);
			loc.setLongitude(lng);
			// also update speed.- 20130712 YW
			loc.setSpeed((float) speed); 
		}

		Date now = new Date();
		if(locator != null){
			
			locator.setGpsLocation(loc);
			// update server time to locator table (for correct chcking)
			locator.setLastLocationUpdate(now);
			
			locatorDao.update(locator);			
		}
		
	}

	@Override
	public List<LocatorLocationHistory> findHistoryByWardId(long wardId,
			Date startTime, Date endTime) {

		return locatorDao.findHistoryByWardId(wardId, startTime, endTime);
	}

	@Override
	public List<LocatorLocationHistory> findHistoryByVehicleId(long vehicleId,
			Date startTime, Date endTime) {

		return locatorDao.findHistoryByVehicleId(vehicleId, startTime, endTime);
	}

	@Override
	public List<PanicAlarm> findNonAcknowledgePanicAlarmByWard(Ward ward,
			boolean acknowledge) {
		return locatorDao.findNonAcknowledgePanicAlarmByWard(ward, acknowledge);
		
	}

	@Override
	public PanicAlarm findPanicAlarmByID(String id) {
		return locatorDao.findPanicAlarmByID(id);
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByTime(long wardId, Date startTime,
			Date endTime) {
		return locatorDao.findPanicAlarmByTime(wardId, startTime, endTime);
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByCount(long [] wardIds, int count) {
		return locatorDao.findPanicAlarmByCount(wardIds, count);
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByRegion(long[] wardIds,
			int startIndex, int endIndex, int lastMin) {
		// TODO Auto-generated method stub
		return locatorDao.findPanicAlarmByRegion(wardIds, startIndex, endIndex, lastMin);
	}
	
}
