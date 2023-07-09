package com.coordsafe.locator.dao;

import java.util.Date;
import java.util.List;

import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.ward.entity.Ward;

public interface LocatorDAO {
	public void create(Locator locator);
	public void update(Locator locator);
	public void delete(Locator locator);
	public Locator findLocatorById(long locatorId);
	public Locator findByImeiCode(String imei);
	/*public List<Locator> findLocatorByUser(String userId);*/
	public List<Locator> findLocatorByAssignTo (long assignTo);
	public List<Locator> findAllLocators();
	
	public void createPanicAlarm( PanicAlarm panicAlarm);
	public void updatePanicAlarm( PanicAlarm panicAlarm);
	public PanicAlarm findPanicAlarmByID(String id);
	public List<PanicAlarm> findPanicAlarmByImei(String imei);
	public List<PanicAlarm> findNonAcknowledgePanicAlarmByWard(Ward ward,	boolean acknowledge);
	
	public long createLocationHistory(LocatorLocationHistory history);
	public List<LocatorLocationHistory> getLocationHistroybyId(long locatiorId, Date startTime, Date endTime);	
	public List<LocatorLocationHistory> findLocationHistoryByTime(String locatorId, String startTime, String endTime);
	public List<LocatorLocationHistory> findInbetweenLocation(long locatorId, long startId, long endId);
	public LocatorLocationHistory findLocationByTime(String locatorId, String time);	
	public LocatorLocationHistory findLastLocationByLocatorId(long locatorId);	
	public List<LocatorLocationHistory> findLocationHistoryByTrip(long locatorId, long tripStartId, long tripEndId);
	public List<LocatorLocationHistory> findCurTripLocationHistory(Trip trip);
	
	public List<LocatorLocationHistory> findHistoryByWardId(long wardId, Date startTime, Date endTime);
	public List<LocatorLocationHistory> findHistoryByVehicleId(long vehicleId, Date startTime, Date endTime);
	
	public List<PanicAlarm> findPanicAlarmByTime(long wardId, Date startTime, Date endTime);
	public List<PanicAlarm> findPanicAlarmByCount(long[] wardIds, int count);
	public List<PanicAlarm> findPanicAlarmByRegion(long[] wardIds, int startIndex, int endIndex, int lastMin);
}
