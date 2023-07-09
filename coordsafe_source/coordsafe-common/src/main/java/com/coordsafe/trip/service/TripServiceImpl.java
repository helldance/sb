/**
 * TripServiceImpl.java
 * Mar 17, 2013
 * Yang Wei
 */
package com.coordsafe.trip.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.constants.Constants;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.trip.dao.TripDao;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.entity.TripDetail;

/**
 * @author Yang Wei
 *
 */
@Transactional(propagation = Propagation.REQUIRED)
@Service
public class TripServiceImpl implements TripService{
	private static Log log = LogFactory.getLog(TripServiceImpl.class);
	
	@Autowired
	private TripDao tripDao;

	/**
	 * @param tripDao the tripDao to set
	 */
	public void setTripDao(TripDao tripDao) {
		this.tripDao = tripDao;
	}
	
	@Autowired
	private LocatorService locatorService;

	/**
	 * @param locatorService the locatorService to set
	 */
	public void setLocatorService(LocatorService locatorService) {
		this.locatorService = locatorService;
	}

	@Override
	public Trip findById(long id) {
		return tripDao.findById(id);
	}

	@Override
	public List<Trip> findByTime(long locatorId, Date start, Date end) {
		return tripDao.findByTime(locatorId, start, end);
	}

	@Override
	public void create(Trip trip) {
		tripDao.create(trip);		
	}

	@Override
	public void update(Trip trip) {
		tripDao.update(trip);
	}

	@Override
	public void delete(Trip trip) {
		tripDao.delete(trip);		
	}
	
	@Override
	public List<Trip> findTripBtwTime(long locatorId, String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		Date _startTime = null, _endTime = null;
				
		try {
			_startTime = df.parse(startTime);
			_endTime = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return tripDao.findByTime(locatorId, _startTime, _endTime);
	}

	@Override
	public List<Trip> findDailyTripsByLocator(long locatorId, String date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date shortDt = null;
		
		try {
			shortDt = df.parse(date);
		}
		catch (ParseException e){
			e.printStackTrace();
		}
		
		// returns trips in between the whole day
		return tripDao.findByTime(locatorId, shortDt, new Date(shortDt.getTime() + 3600*24*1000));
	}

	@Override
	public List<TripDetail> findTripDetailBtwTimeFleetLink(long locatorId,
			String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		Date _startTime = null, _endTime = null;
				
		try {
			_startTime = df.parse(startTime);
			_endTime = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Trip> trips = tripDao.findByTime(locatorId, _startTime, _endTime);
		List<TripDetail> tripDetails = new ArrayList<TripDetail>();
		
		for (Trip t: trips){
			TripDetail tripDetail = new TripDetail();
			//tripDetail.tripId = t.getId();
			tripDetail.trip = t;
			List<LocatorLocationHistory> llhs = locatorService.findLocationHistoryByTrip(locatorId, t.getTripStartId(), t.getTripEndId());
			int count = llhs.size();
			
			if (count == 0){// if this happens, find out why???
				log.info("0 history found for trip " + t.getId());
				continue;
			}
			
			// need monitor params and finetune 
			if (count <= 200) // no reduce
				tripDetail.history = llhs;
			if (count <= 400) // around 40 mins
				tripDetail.history = optimizeHistory(llhs, 2);
			else if (count <= 1000)
				tripDetail.history = optimizeHistory(llhs, 4);
			else if (count <= 2000)
				tripDetail.history = optimizeHistory(llhs, 6);
			else if (count <= 4000)
				tripDetail.history = optimizeHistory(llhs, 8);
			else if (count <= 6000)
				tripDetail.history = optimizeHistory(llhs, 12);
			else if (count <= 8000)
				tripDetail.history = optimizeHistory(llhs, 16);
			else if (count > 8000) // more than 10 hour
				tripDetail.history = optimizeHistory(llhs, 20);
			else 
				tripDetail.history = llhs;
			
			tripDetails.add(tripDetail);
		}
		
		return tripDetails;
	}

	@Override
	public List<TripDetail> findTripDetailBtwTime(long locatorId,
			String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date _startTime = null, _endTime = null;
				
		try {
			_startTime = df.parse(startTime);
			_endTime = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Trip> trips = tripDao.findByIDTime(locatorId, startTime, endTime);
		Trip currentTrip = tripDao.findCurrentOrLastTrip(locatorId);
		
		
		
		List<TripDetail> tripDetails = new ArrayList<TripDetail>();
		
		
		
		for (Trip t: trips){
			TripDetail tripDetail = new TripDetail();
			//tripDetail.tripId = t.getId();
			if (t.getMileage() < 0.5) 
				continue;
			tripDetail.trip = t;
			tripDetail.tripType = Constants.TRIP_TYPE_FINISHED;
			List<LocatorLocationHistory> llhs = locatorService.findLocationHistoryByTrip(locatorId, t.getTripStartId(), t.getTripEndId());
			int count = llhs.size();
			log.info("Locator History found for trip =" + count);
			if (count == 0){// if this happens, find out why???
				log.info("0 history found for trip " + t.getId());
				continue;
			}
			
			// need monitor params and finetune 
/*			if (count <= 200) // no reduce
				tripDetail.history = llhs;
			if (count <= 400) // around 40 mins
				tripDetail.history = optimizeHistory(llhs, 2);
			else if (count <= 1000)
				tripDetail.history = optimizeHistory(llhs, 4);
			else if (count <= 2000)
				tripDetail.history = optimizeHistory(llhs, 6);
			else if (count <= 4000)
				tripDetail.history = optimizeHistory(llhs, 8);
			else if (count <= 6000)
				tripDetail.history = optimizeHistory(llhs, 12);
			else if (count <= 8000)
				tripDetail.history = optimizeHistory(llhs, 16);
			else if (count > 8000) // more than 10 hour
				tripDetail.history = optimizeHistory(llhs, 20);
			else 
				tripDetail.history = llhs;*/
			
			tripDetail.history = llhs;
			tripDetails.add(tripDetail);
		}
		
		if(currentTrip == null){
			log.info("There is no current Trip for " + locatorId + " during " + startTime + "--" + endTime);
		}else{
			if(currentTrip.getTripEndId()==0 || currentTrip.getTripEndTime() == null){
				TripDetail tripDetail = new TripDetail();
				tripDetail.trip = currentTrip;
				tripDetail.tripType = Constants.TRIP_TYPE_UNFINISHED;
				List<LocatorLocationHistory> llhs = locatorService.findCurTripLocationHistory(currentTrip);
				tripDetail.history = llhs;
				tripDetails.add(tripDetail);
			}
			
		}

		return tripDetails;
	}
	
	private List<LocatorLocationHistory> optimizeHistory(List<LocatorLocationHistory> historys, int divider){
		List<LocatorLocationHistory> opted = new ArrayList<LocatorLocationHistory>();
		int total = historys.size();
		int target = total/divider;
		
		for (int x = 0; x < target; x ++){
			opted.add(historys.get(x*divider));
		}
		
		log.info("after optimize: " + total + " " + target);
				
		return opted;
	}

	@Override
	public String findGroupTripByTime(long groupId, String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat(PortalAppConfig.SDF_DATE);
		Date _startTime = null, _endTime = null;
				
		try {
			_startTime = df.parse(startTime);
			_endTime = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<HashMap<String, List<Trip>>> grpTrips = tripDao.findGroupTripByTime(groupId, _startTime, _endTime);
		
		// build json string
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		
		try {
			jsonStr = mapper.writeValueAsString(grpTrips);
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
		
		return jsonStr;
	}

	@Override
	public double getMileageByTime(long locatorId, String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		Date _startTime = null, _endTime = null;
				
		try {
			_startTime = df.parse(startTime);
			_endTime = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Trip> trips = tripDao.findByTime(locatorId, _startTime, _endTime);
		double mileage = 0;
		
		for (Trip trip : trips){
			mileage += trip.getMileage();
		}
		
		return mileage;
	}

	@Override
	public double getMovingTimeByTime(long locatorId, String startTime,
			String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date _startTime = null, _endTime = null;
				
		try {
			_startTime = df.parse(startTime);
			_endTime = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Trip> trips = tripDao.findByTime(locatorId, _startTime, _endTime);
		double moving = 0;
		
		for (Trip trip : trips){
			if (trip.getTripEndTime() != null)
				moving += trip.getTripEndTime().getTime() - trip.getTripStartTime().getTime();
		}
		
		return moving;
	}

	@Override
	public Trip findCurrentOrLastTrip(long locatorId) {
		// TODO Auto-generated method stub
		return tripDao.findCurrentOrLastTrip(locatorId);
	}

	@Override
	public List<Trip> findUnprocessed() {
		// TODO Auto-generated method stub
		return tripDao.findUnprocessed();
	}

	@Override
	public List<Trip> findValid() {
		// TODO Auto-generated method stub
		return tripDao.findValid();
	}

	@Override
	public TripDetail findCurOrLastTripDetail(long locatorId) {
		Trip trip = tripDao.findCurrentOrLastTrip(locatorId);
		TripDetail tripDetail = new TripDetail();

		tripDetail.trip = trip;
		List<LocatorLocationHistory> llhs = locatorService.findCurTripLocationHistory(trip);
		
		log.info("find curtrip history count: " + llhs.size());
		
		tripDetail.history = llhs;
		
		return tripDetail;
	}

	@Override
	public TripDetail findTripDetailById(long tripId) {
		Trip trip = tripDao.findById(tripId);
		TripDetail tripDetail = new TripDetail();

		tripDetail.trip = trip;
		List<LocatorLocationHistory> llhs = locatorService.findLocationHistoryByTrip(tripId);
		
		log.info("find trip history count: " + llhs.size());
		
		tripDetail.history = llhs;
		
		return tripDetail;
	}
}
