/**
 * TripDaoImpl.java
 * Mar 17, 2013
 * Yang Wei
 */
package com.coordsafe.trip.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.service.LocatorService;
import com.coordsafe.trip.entity.Trip;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.service.VehicleService;

/**
 * @author Yang Wei
 *
 */
@Repository
public class TripDaoImpl implements TripDao {
	private static final Logger logger = Logger.getLogger(TripDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
		
	@Autowired
	private VehicleService vehicleService;

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	
	@Autowired
	private LocatorService locatorService;

	public void setVehicleService(LocatorService locatorService) {
		this.locatorService = locatorService;
	}
	
	@Override
	public Trip findById(long id) {
		logger.info("find trip by id: " + id);
		return (Trip) sessionFactory.getCurrentSession().createQuery("from Trip t where t.id=?").setLong(0, id).uniqueResult();
	}	

	@Override
	public Trip findCurrentOrLastTrip(long locatorId) {
		List<Trip> trips = (List<Trip>) sessionFactory.getCurrentSession().createQuery("from Trip t where t.locatorId = ? order by id desc").setLong(0, locatorId).list();
		
		if (trips != null && trips.size() > 0){
			return trips.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trip> findByTime(long locatorId, Date start, Date end) {
		logger.info("find trip by time: " + locatorId + " " + start.toString() + " " + end.toString());
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Trip.class);
		Criterion c1 = Restrictions.and(Restrictions.ge("tripStartTime", start), Restrictions.le("tripStartTime", end));
		Criterion c2 = Restrictions.and(Restrictions.le("tripStartTime", start), Restrictions.ge("tripEndTime", start));
		Criterion cr = Restrictions.and(Restrictions.le("tripStartTime", end), Restrictions.ge("tripEndTime", start));
		//criteria.add(Restrictions.and(Restrictions.or(c1, c2), Restrictions.eq("locatorId", locatorId)));
		criteria.add(Restrictions.and(cr, Restrictions.eq("locatorId", locatorId)));
		
		//order by trip id to show sequence
		criteria.addOrder(Order.asc("id"));
		
		/*return (List<Trip>) sessionFactory.getCurrentSession().createQuery("from Trip t where t.locatorId=? and t.tripStartTime > ? and t.tripStartTime < ?").setLong(0, locatorId)
				.setDate(1, start).setDate(2, end).list();*/
		logger.debug(criteria.toString());
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Trip> findByIDTime(long locatorId, String start, String end) {
/*			SQLQuery query = (SQLQuery) sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM TBL_TRIP T WHERE T.locator_Id=? and t.trip_Start_Time > to_timestamp(?,'YYYY-MM-DD HH24:MI') and t.trip_End_Time < to_timestamp(?,'YYYY-MM-DD HH24:MI')")
				.setLong(0, locatorId)
				.setString(1,start)
				.setString(2, end);
			logger.debug("The SQL Query =" + query.getQueryString());
			return (List<Trip>) query.list();*/
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");


		try {
			Query query = sessionFactory.getCurrentSession().createQuery("from Trip t where t.locatorId=:lid and t.tripStartTime > :stime and t.tripEndTime < :etime").setLong("lid", locatorId)
					.setTimestamp("stime", df.parse(start)).setTimestamp("etime", df.parse(end));
			logger.debug("The SQL Query =" + query.getQueryString());

			return (List<Trip>) query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	public void create(Trip trip) {
		logger.info("created trip " + trip.getLocatorId());
		trip.setValid(true);
		sessionFactory.getCurrentSession().save(trip);
	}

	@Override
	public void update(Trip trip) {
		sessionFactory.getCurrentSession().saveOrUpdate(trip);
	}

	@Override
	public void delete(Trip trip) {
		sessionFactory.getCurrentSession().delete(trip);
	}

	@Override
	public List<Trip> findUnprocessed() {
		Date threeDaysAgo = new Date(System.currentTimeMillis() - 3 * 24 * 3600 * 1000);
		
		return (List<Trip>) sessionFactory.getCurrentSession().createQuery("from Trip t where t.mileage = 0 and t.tripEndId != 0 and t.tripEndTime > :endDt")
				.setTimestamp("endDt", threeDaysAgo).list(); 		
	}

	@Override
	public List<Trip> findValid() {
		//TODO date subtract problem
		//return (List<Trip>) sessionFactory.getCurrentSession().createQuery("from Trip t where t.mileage < ? and t.trip_end_time - t.trip_start_time < ?").list();
		return (List<Trip>) sessionFactory.getCurrentSession().createQuery("from Trip t where t.valid = true and t.tripEndId != 0").list();
	}

	@Override
	public List<HashMap<String, List<Trip>>> findGroupTripByTime(long groupId, Date start, Date end) {
		List<HashMap<String, List<Trip>>> groupTripList = new ArrayList<HashMap<String, List<Trip>>>();
		List<Vehicle> vehicles = vehicleService.findVehicleByGroupId(groupId);

		//TODO: change locatorId to vehicle label.
		for (Vehicle v : vehicles){
			Locator locator = v.getLocator();
			
			if (locator != null){
				List<Trip> trips = this.findByTime(locator.getId(), start, end);
				
				HashMap<String, List<Trip>> map = new HashMap<String, List<Trip>>();
				map.put(v.getName() + " - " + v.getLicensePlate(), trips);
				
				groupTripList.add(map);
			}
		}
		
		return groupTripList;
	}

}
