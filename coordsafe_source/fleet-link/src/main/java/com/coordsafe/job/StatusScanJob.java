package com.coordsafe.job;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.common.entity.LatLng;
import com.coordsafe.company.service.CompanyService;
import com.coordsafe.config.PortalAppConfig;
import com.coordsafe.constants.UserMessage;
import com.coordsafe.event.entity.EventType;
import com.coordsafe.event.entity.VehicleEvent;
import com.coordsafe.event.service.EventService;
import com.coordsafe.locator.entity.DeviceStatus;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.LocatorLocationHistory;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.notification.producer.TopicMessageProducer;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.trip.service.TripService;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleStatus;
import com.coordsafe.vehicle.service.VehicleService;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class StatusScanJob {
	private static Logger logger = Logger.getLogger(StatusScanJob.class);
	
	@Autowired
	private LocatorService locatorService;

	@Autowired
	private TripService tripService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private TopicMessageProducer topicNotificationMessageProducer;
	
	@Scheduled(fixedRate= PortalAppConfig.JOB_LOCATOR_STATUS)
	public void scanAndUpdate (){
		logger.debug("locator status job is started.. ");
		
		java.util.List<Locator> locators = locatorService.findAllLocators();
		
		for (Locator locator : locators){
			Date lastUpdate = locator.getLastLocationUpdate();
			long locatorId = locator.getId();
			
			Vehicle vehicle = vehicleService.findVehicleByLocatorId(locatorId);
			
			// if last 4 location update speed around 0, it is idle
			//List<LocatorLocationHistory> last4 = locatorService.fi
			
			if (lastUpdate != null){
				long diff = new Date().getTime() - lastUpdate.getTime();
				
				if (diff > PortalAppConfig.LOCATOR_PAUSE_DURATION){
					// vehicle stopped sending updates
					DeviceStatus deviceStatus = locator.getDeviceStatus();
					
					/*if (deviceStatus.getIsGpsOn()){
						// TODO: broadcast vehicle stopped event
						// logger.info("Vechicle paused: " + locator.getLabel());
											
						deviceStatus.setIsGpsOn(false);
						locator.setDeviceStatus(deviceStatus);
						locatorService.updateLocator(locator);
						
						if (vehicle != null){
							vehicle.setStatus(VehicleStatus.PAUSED);
							vehicleService.update(vehicle);
							
							LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
							
							VehicleEvent ve = new VehicleEvent(locator.getId(), EventType.VHC_PAUSE, UserMessage.VHC_PAUSED,
									eventPlace, locator.getLastLocationUpdate(), vehicle.getName(), vehicle.getCompany());
							
							eventService.create(ve);
						}
					}*/
					
					if (diff > PortalAppConfig.LOCATOR_STOP_DURATION) // vehicle idle for more than half hour
					{
						if (deviceStatus.getIsGpsOn()){
							// TODO: broadcast vehicle stopped event
							// logger.info("Vechicle paused: " + locator.getLabel());
												
							deviceStatus.setIsGpsOn(false);
							locator.setDeviceStatus(deviceStatus);
							locatorService.updateLocator(locator);
							// mark last position as trip end
							// how to avoid duplicate marking??			
							Trip trip = tripService.findCurrentOrLastTrip(locatorId);
							LocatorLocationHistory history = locatorService.findLastLocationByLocatorId(locatorId);
							
							if (trip != null && history != null && trip.getTripStartId() != 0
									&& trip.getTripEndId() == 0){
								logger.info("Trip ended: " + locator.getLabel());
								
								// If no gps fix, lastupdate is always old time
								//if (history.isGpxFix()){
								if (lastUpdate.after(trip.getTripStartTime())){
									trip.setTripEndId(history.getId());
									//trip.setTripEndTime(history.getLocation_time());
									// Use in case gps not fix.
									trip.setTripEndTime(lastUpdate);
									
									tripService.update(trip);
									
									LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
									
									VehicleEvent ve = new VehicleEvent(locator.getId(), EventType.TRIP_END, UserMessage.TRIP_STOP,
											eventPlace, locator.getLastLocationUpdate(), vehicle.getName(), vehicle.getCompany());
									
									eventService.create(ve);
								}
								//}
							}
	
							// Vehicle stopped
							if (vehicle != null){
								vehicle.setStatus(VehicleStatus.STOPPED);
								vehicleService.update(vehicle);
								
								LatLng eventPlace = new LatLng(locator.getGpsLocation().getLatitude(), locator.getGpsLocation().getLongitude());
								
								VehicleEvent ve = new VehicleEvent(locator.getId(), EventType.VHC_STOP, UserMessage.VEHICLE_STOP,
										eventPlace, locator.getLastLocationUpdate(), vehicle.getName(), vehicle.getCompany());
	
								
								eventService.create(ve);
								
								//topicNotificationMessageProducer.filterEventType(ve);
							}
						}
					}
				}
			}
			else {
				Date now = new Date();
				
				locator.setLastLocationUpdate(now);
				locator.setLastStatusUpdate(now);
				
				locatorService.updateLocator(locator);
			}
		}
	}
}
