package com.coordsafe.locator.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.ward.entity.Ward;

@Repository
public class LocatorDAOImpl implements LocatorDAO {
	private static final Logger logger = Logger.getLogger(LocatorDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;	
			
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Locator findByImeiCode(String imei) {			
		return (Locator)sessionFactory.getCurrentSession().createQuery("from Locator l where l.imeiCode=?").setString(0, imei).uniqueResult();
		/*if (!locators.isEmpty())
			return locators.get(0);
		return null;*/
	}

	public void create(Locator locator) {
		sessionFactory.getCurrentSession().save(locator);
	}

	public void update(Locator locator) {
		sessionFactory.getCurrentSession().saveOrUpdate(locator);
	}

	public void delete(Locator locator) {
		sessionFactory.getCurrentSession().delete(locator);
	}

	@Override
	public Locator findLocatorById(long locatorId) {
		return (Locator) sessionFactory.getCurrentSession().createQuery("from Locator l where l.id = ?").setLong(0, locatorId).uniqueResult();
	}

	/*@Override
	public List<Locator> findLocatorByUser(String userId) {
//		@SuppressWarnings("unchecked")
		List<Locator> locators = sessionFactory.getCurrentSession().createQuery("from Locator l where l.ownerId=?").setString(0, userId).list();
		//List<Locator> locators = sessionFactory.getCurrentSession().createQuery("from Locator l").list();
		return locators;
	}*/
	
	@Override
	public List<Locator> findAllLocators() {
		@SuppressWarnings("unchecked")
		List<Locator> locators = sessionFactory.getCurrentSession().createQuery("from Locator l").list();
		return locators;
	}

	@Override
	public void createPanicAlarm(PanicAlarm panicAlarm) {
		sessionFactory.getCurrentSession().save(panicAlarm);
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByImei(String imei) {
		List<PanicAlarm> panicAlarms = sessionFactory.getCurrentSession().createQuery("from PanicAlarm pa where pa.imei=?").setString(0, imei).list();
		return panicAlarms;
	}

	@Override
	public void updatePanicAlarm(PanicAlarm panicAlarm) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(panicAlarm);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public long createLocationHistory(LocatorLocationHistory history) {
		return (Long) sessionFactory.getCurrentSession().save(history);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocatorLocationHistory> getLocationHistroybyId(long locatorId,
			Date startTime, Date endTime) {
		return sessionFactory.getCurrentSession().createQuery("from LocatorLocationHistory history where history.lat > 0.01 and history.lng > 0.01 and history.locator_id = ? and history.location_time between ? and ?").setLong(0, locatorId).setDate(1, startTime).setDate(2, endTime).list();
		//return sessionFactory.getCurrentSession().createSQLQuery("select * from LocatorLocationHistory as a where a.locator_id = " + 
		//	locatorId + "and a.location_time between " + startTime + " and" + endTime);
	}	

	public List<Locator> findLocatorByAssignTo (long assignTo) {
		// shamir's hack
		if (assignTo == 2){
			return  ( List<Locator> )sessionFactory.getCurrentSession()
					.createQuery("from Locator l where l.assignedTo= :companyId or l.assignedTo = :anotherId").setLong("companyId", assignTo).setLong("anotherId", 3).list();
		}
		else {
			List<Locator> locators = sessionFactory.getCurrentSession().createQuery("from Locator l where l.assignedTo= :companyId").setLong("companyId", assignTo).list();
			return 	locators;
		}
	}

	public List<LocatorLocationHistory> findLocationHistoryByTime(
			String locatorId, String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		Date _startTime = null, _endTime = null;
			
		try {
			//_startTime = df.parse(URLDecoder.decode(startTime, "UTF-8"));
			//_endTime = df.parse(URLDecoder.decode(endTime, "UTF-8"));
			_startTime = df.parse(startTime.replace('+', ' '));
			_endTime = df.parse(endTime.replace('+', ' '));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(locatorId + " " + _startTime.toString() + " " + _endTime.toString());
		
		//int _locatorId = Integer.parseInt(locatorId);
		long _locatorId = Long.parseLong(locatorId);
		
		return sessionFactory.getCurrentSession().createQuery("from LocatorLocationHistory history where history.locator_id = ? and " +
				"history.location_time >= ? and history.location_time <= ? and history.lat>0.01 and history.lng>0.01 order by history.location_time asc").setLong(0, _locatorId).setTimestamp(1, _startTime).setTimestamp(2, _endTime).list();

	}

	@Override
	public LocatorLocationHistory findLocationByTime(String locatorId, String time) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");	
		Date q_time = null;
		
		try {
			q_time = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long _locatorId = Long.parseLong(locatorId);
		Date l_bound = new Date(q_time.getTime() - 5000);
		Date h_bound = new Date(q_time.getTime() + 5000);
		
		List<LocatorLocationHistory> locationList = sessionFactory.getCurrentSession().createQuery("from LocatorLocationHistory history where history.locator_id = ? and " +
				"history.location_time > ? and history.location_time < ? and history.lat>0.01 and history.lng>0.01 order by history.location_time asc").setLong(0, _locatorId).setTimestamp(1, l_bound).setTimestamp(2, h_bound).list();
		
		if (locationList != null && locationList.size() > 0){
			if (locationList.size() == 1){
				return locationList.get(0);
			}
			else { // size = 2
				if ((locationList.get(1).getLocation_time().getTime() - q_time.getTime()) > 
					q_time.getTime() - locationList.get(1).getLocation_time().getTime()){
						return locationList.get(0);
					}
				else {
					return locationList.get(1);
				}
			}
		}
		else{
			// return last location
			List<LocatorLocationHistory> locations = sessionFactory.getCurrentSession().createQuery("from LocatorLocationHistory history where history.locator_id = ? and " +
					"history.location_time < ? and history.lat>0.01 and history.lng>0.01 order by history.location_time desc").setLong(0, _locatorId).setTimestamp(1, q_time).setMaxResults(3).list();
			
			if (locations != null && locations.size() > 0){
				return locations.get(0);
			}
		}
		
		return null;
	}

	@Override
	public LocatorLocationHistory findLastLocationByLocatorId(long locatorId) {
		// no need for gps fix record
		List<LocatorLocationHistory> locationList = sessionFactory.getCurrentSession().createQuery("from LocatorLocationHistory history where history.locator_id = ? order by history.id desc").setLong(0, locatorId)
				.setMaxResults(3).list();

		if (locationList != null && locationList.size() > 0){
			return locationList.get(0);
		}
		
		return null;
	}

	@Override
	public List<LocatorLocationHistory> findInbetweenLocation(long locatorId, long startId, long endId) {
		List<LocatorLocationHistory> locationList = sessionFactory.getCurrentSession().createQuery
				("from LocatorLocationHistory history where history.locator_id = :lId and history.id >= :hId1 and history.id <= :hId2 and history.lat > 0.1 and history.lng > 0.1 order by history.id asc")
				.setLong("lId", locatorId).setLong("hId1", startId).setLong("hId2", endId).list();
		
		return locationList;
	}

	@Override
	public List<LocatorLocationHistory> findLocationHistoryByTrip(
			long locatorId, long tripStartId, long tripEndId) {
		logger.info("find locationHistory for trip: " + tripStartId + " " + tripEndId);
		
		// unfinished trip
		if (tripEndId == 0l){
			tripEndId = Long.MAX_VALUE;
		}
		
		@SuppressWarnings("unchecked")
		List<LocatorLocationHistory> locationList = sessionFactory.getCurrentSession().createQuery
				("from LocatorLocationHistory history where history.locator_id = :lid and history.id >= :sid and history.id <= :eid and history.lat > 0.1 and history.lng > 0.1 order by history.id asc")
				.setLong("lid", locatorId).setLong("sid", tripStartId).setLong("eid", tripEndId).list();

		return locationList;
	}

	@Override
	public List<LocatorLocationHistory> findCurTripLocationHistory(Trip trip) {
		// TODO Auto-generated method stub
		return findLocationHistoryByTrip(trip.getLocatorId(), trip.getTripStartId(), 0l);
	}

	@Override
	public List<LocatorLocationHistory> findHistoryByWardId(long wardId,
			Date startTime, Date endTime) {
		logger.info("find ward history: " + wardId + " " + startTime + " " + endTime);
		
		@SuppressWarnings("unchecked")
		List<LocatorLocationHistory> locationList = sessionFactory.getCurrentSession().createQuery
				("from LocatorLocationHistory history where history.wardId = :wId " +
						"and history.location_time > :startTime and history.location_time < :endTime " +
						"and history.lat>0.01 and history.lng>0.01 order by history.location_time asc")
				.setLong("wId", wardId).setTimestamp("startTime", startTime).setTimestamp("endTime", endTime).list();
		
		return locationList;
	}

	@Override
	public List<LocatorLocationHistory> findHistoryByVehicleId(long vehicleId,
			Date startTime, Date endTime) {
		List<LocatorLocationHistory> locationList = sessionFactory.getCurrentSession().createQuery
				("from LocatorLocationHistory history where history.vehicleId= :vId " +
						"and history.location_time > :startTime and history.location_time < :endTime " +
						"and history.lat>0.01 and history.lng>0.01 order by history.location_time asc")
				.setLong("vId", vehicleId).setDate("startTime", startTime).setDate("endTime", endTime).list();
		
		return locationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PanicAlarm> findNonAcknowledgePanicAlarmByWard(Ward ward,	boolean acknowledge) {
		return (List<PanicAlarm>) sessionFactory.getCurrentSession().createQuery("from PanicAlarm p where p.ward.id = :wardId and p.acknowledged = :acknowledge").setLong("wardId", ward.getId()).setBoolean("acknowledge", acknowledge).list();
	}

	@Override
	public PanicAlarm findPanicAlarmByID(String id) {
		return (PanicAlarm) sessionFactory.getCurrentSession().createQuery("from PanicAlarm p where p.id = :id").setLong("id", Long.valueOf(id)).uniqueResult();
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByTime(long wardId, Date startTime,
			Date endTime) {
		logger.info("find PanicAlarm by time: " + wardId + " " + startTime + " " + endTime);
		
		return (List<PanicAlarm>) sessionFactory.getCurrentSession().createQuery("from PanicAlarm p where p.ward.id = :wardId and p.time >= :_start and p.time <= :_end")
				.setLong("wardId", wardId).setTimestamp("_start", startTime).setTimestamp("_end", endTime).list();
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByCount(long[] wardIds, int count) {
		logger.info("find PanicAlarm by time: " + wardIds + " " + count);
		
		//Arrays.asList(wardIds);
		
		List<Long> _wardIds = new ArrayList<Long>();

		for (long ll : wardIds){
			_wardIds.add(ll);
		}
		
		return (List<PanicAlarm>) sessionFactory.getCurrentSession().createQuery("from PanicAlarm p where p.ward.id in (:wardIds) order by p.time desc")
				.setMaxResults(count).setParameterList("wardIds", _wardIds).list();
	}

	@Override
	public List<PanicAlarm> findPanicAlarmByRegion(long[] wardIds, int startIndex, int endIndex, int lastMin) {
		logger.info("find PanicAlarm by region: " + wardIds + " " + lastMin);
		
		//Arrays.asList(wardIds);
		
		List<Long> _wardIds = new ArrayList<Long>();

		for (long ll : wardIds){
			_wardIds.add(ll);
		}
		
		return (List<PanicAlarm>) sessionFactory.getCurrentSession().createQuery("from PanicAlarm p where p.ward.id in (:wardIds) order by p.time desc")
				.setFirstResult(lastMin).setMaxResults(endIndex - startIndex).setParameterList("wardIds", _wardIds).list();
	}
}
