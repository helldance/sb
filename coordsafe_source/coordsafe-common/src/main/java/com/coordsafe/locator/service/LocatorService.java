package com.coordsafe.locator.service;

import java.util.Date;
import java.util.List;

import com.coordsafe.locator.entity.ExtraCommand;
import com.coordsafe.locator.entity.GpsLocation;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.ward.entity.Ward;

public interface LocatorService {
	public boolean authenticate(String imeiCode);
	
	/*locator function*/
	public void createLocator(Locator locator);
	public void deleteLocator(String imeiCode);
	public void updateLocator(Locator locator);
	public Locator findLocatorByImei(String imeiCode);
	public Locator findLocatorById(long locatorId);
	/*public List<Locator> findLocatorByUser(String userId);*/
	public List<Locator> findLocatorByAssignTo (long assignTo);
	public List<Locator> findAllLocators();
	
	/*locator update*/
	public void updateLocation(String imeiCode, double lat, double lng, long gpsTime, long hid);
	public void updateStatus(String imeiCode, String ip, Boolean isGsmOn, Boolean isGpsOn, Integer batteryLevel, Integer networkStatus, long gpsTime);
	
	public void createPanicAlarm( PanicAlarm panicAlarm);
	public void sendPanicAlarm(String imei, String time, Double lat, Double lng,String alarmType,String alarmMessage,Ward ward);
	public void acknowledgePanicAlarm( PanicAlarm panicAlarm) ;
	public PanicAlarm findPanicAlarmByID (String id);
	public List<PanicAlarm> findPanicAlarmByImei(String imei);
	public List<PanicAlarm> findNonAcknowledgePanicAlarmByWard(Ward ward,boolean acknowledge);
	public List<PanicAlarm> findPanicAlarmByTime(long wardId, Date startTime, Date endTime);
	public List<PanicAlarm> findPanicAlarmByCount(long[] wardIds, int count);
	public List<PanicAlarm> findPanicAlarmByRegion(long[] wardIds, int startIndex, int endIndex, int lastMin);
	
	public long createLocationHistory(LocatorLocationHistory history);
	public List<LocatorLocationHistory> getLocationHistroybyImei(String imei,Date startTime, Date endTime);
	public List<LocatorLocationHistory> findLocationHistoryByTime(String locatorId, String startTime, String endTime);	
	public LocatorLocationHistory findLocationByTime(String locatorId, String time);
	public List<LocatorLocationHistory> findLocationHistoryByTrip(long tripId);
	public List<LocatorLocationHistory> findLocationHistoryByTrip(long locatorId, long tripStartId, long tripEndId);
	public LocatorLocationHistory findLastLocationByLocatorId(long locatorId);
	public List<LocatorLocationHistory> findInbetweenLocation(long locatorId, long tripStartId, long tripEndId);

	public void updateLocation(String imeiCode, GpsLocation location, ExtraCommand ec, String ip, long hid);
	public void updateLocationForWARD(String imeiCode, GpsLocation location, ExtraCommand ec, String ip, long hid);


	public List<LocatorLocationHistory> findCurTripLocationHistory(Trip trip);
	
	public List<LocatorLocationHistory> findHistoryByWardId(long wardId, Date startTime, Date endTime);
	public List<LocatorLocationHistory> findHistoryByVehicleId(long vehicleId, Date startTime, Date endTime);
}
