/**
 * TripSummaryJob.java
 * May 20, 2013
 * Yang Wei
 */
package com.coordsafe.job;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.service.TripService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;

/**
 * @author Yang Wei
 *
 */
@Transactional(propagation = Propagation.REQUIRED)
@Service
public class TripSummaryJob {
	private static Logger logger = Logger.getLogger(TripSummaryJob.class);
	private static DecimalFormat df = new DecimalFormat("#.##");
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private LocatorService locatorService;
	
	@Autowired
	private VehicleService vehicleSvrs;
	
	//@Autowired
	//private JavaMailSenderImpl mailSender;
	
	// functions to calcuate mileage for each trip. job runs at every 10 mins.
	@Scheduled(fixedRate=PortalAppConfig.JOB_TRIP_SUMMRIZE)
	public void updateMileage (){
		logger.info("batch job start..// updating mileages..");
		
		List<Trip> trips = tripService.findUnprocessed();
		
		for (Trip trip : trips){
			double distance = calculateDistance(trip);
			double mileage = Double.valueOf(df.format(distance * 1.609));
			
			trip.setMileage(mileage);
			
			Date dtStart = trip.getTripStartTime();
			Date dtEnd = trip.getTripEndTime();
			
			if (dtStart != null && dtEnd != null)
				trip.setMovingTime(trip.getTripEndTime().getTime() - trip.getTripStartTime().getTime());
			
			// add trip mileage to vehicle total mileage
			Vehicle v = vehicleSvrs.findVehicleByLocatorId(trip.getLocatorId());
			
			if (v != null){
				v.setMileage((long) (v.getMileage() + mileage));
				
				vehicleSvrs.update(v);
			}
		}
		
		// send Email notification.
		//mailSender.
	}

	private double calculateDistance(Trip trip) {
		List<LocatorLocationHistory> hist = locatorService.findInbetweenLocation(trip.getLocatorId(), trip.getTripStartId(), trip.getTripEndId());
				
		double miles = 0;
		
		if (hist.size() > 0){
			// export Trip to gpx - test
			//TripExporterTool.exportGpx(hist);
			
			ListIterator<LocatorLocationHistory> iterator = hist.listIterator();
			
			LocatorLocationHistory start = iterator.next();
			LocatorLocationHistory end = null;
			
			while (iterator.hasNext()){
				end = iterator.next();
				miles += distance2Position(start.getLat(), start.getLng(), end.getLat(), end.getLng());
				start = end;
			}
		}
				
		return miles;
	}
	
	// returns distance between 2 point in MILES
	private double distance2Position (double lat1, double lng1, double lat2, double lng2){
		double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
	}

	// filter trips with milage < 1km or duration < 5min
	// TODO
	//@Scheduled(fixedRate=PortalAppConfig.JOB_TRIP_SUMMRIZE)
	private void filterMergeTrips (){
		List<Trip> trips = tripService.findValid();
		
		for (Trip trip : trips){
			if (trip.getMileage() < PortalAppConfig.TRIP_MIN_MILAGE && (trip.getTripEndTime().getTime() - trip.getTripStartTime().getTime()) < 
					PortalAppConfig.TRIP_MIN_MOVING_TIME){
						trip.setValid(false);
						
						tripService.update(trip);
					}
		}
	}
}
