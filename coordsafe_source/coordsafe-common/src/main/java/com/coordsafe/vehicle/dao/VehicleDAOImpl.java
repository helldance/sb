// Generated Jun 3, 2013 5:34:49 PM by Hibernate Tools 4.0.0
package com.coordsafe.vehicle.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.vehicle.entity.Vehicle;
@Transactional 
@Repository("vehicleDAO")
public class VehicleDAOImpl implements VehicleDAO {

	private static final Log log = LogFactory.getLog(VehicleDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#persist(com.coordsafe.core.vehicle.entity.Vehicle)
	 */
	@Override
	public void persist(Vehicle transientInstance) {
		log.debug("persisting Vehicle instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			//log.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#attachDirty(com.coordsafe.core.vehicle.entity.Vehicle)
	 */
	@Override
	public void attachDirty(Vehicle instance) {
		log.debug("attaching dirty Vehicle instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#attachClean(com.coordsafe.core.vehicle.entity.Vehicle)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void attachClean(Vehicle instance) {
		log.debug("attaching clean Vehicle instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#delete(com.coordsafe.core.vehicle.entity.Vehicle)
	 */
	@Override
	public void delete(Vehicle persistentInstance) {
		log.debug("deleting Vehicle instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#merge(com.coordsafe.core.vehicle.entity.Vehicle)
	 */
	@Override
	public Vehicle merge(Vehicle detachedInstance) {
		//log.debug("merging Vehicle instance");
		try {
			Vehicle result = (Vehicle) sessionFactory.getCurrentSession().merge(detachedInstance);
			//log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#findById(long)
	 */
	@Override
	public Vehicle findVehicleById(long id) {
		log.debug("getting Vehicle instance with id: " + id);
		try {
			Vehicle instance = (Vehicle) sessionFactory
					.getCurrentSession().get(Vehicle.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.coordsafe.core.vehicle.dao.VehicleDAO#findByExample(com.coordsafe.core.vehicle.entity.Vehicle)
	 */
	@Override
	public List<Vehicle> findByExample(Vehicle instance) {
		log.debug("finding Vehicle instance by example");
		try {
			@SuppressWarnings("unchecked")
			List<Vehicle> results = sessionFactory.getCurrentSession()
					.createCriteria("Vehicle").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Vehicle as o order by o.name").list();
		}

	@Override
	public void delete(String name) {
		// TODO Auto-generated method stub
		
		sessionFactory.getCurrentSession().delete(name);
		
	}

	@Override
	public Vehicle findVehicleByName(String name) {
		// TODO Auto-generated method stub

		log.debug("finding vehicle instance by vehicle name");
		
		Vehicle v = null;
		
		try {
			v = (Vehicle) sessionFactory.getCurrentSession().createQuery("from Vehicle as o where o.name=?")
				.setString(0, name).uniqueResult();
			
			return v;
		}
		catch (NonUniqueResultException ex){
			throw ex;
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> findVehiclesByCompany(String companyID) {
		// TODO Auto-generated method stub

		log.debug("finding vehicle instance by company");
		
		// shamir's hack
		// add more hacks for demo account - 20140107
		if (companyID.equalsIgnoreCase("2")){
			return  ( List<Vehicle> )sessionFactory.getCurrentSession()
					.createQuery("from Vehicle as o where o.company.id=? or o.company.id = ?")
					.setLong(0, new Long(companyID)).setLong(1, new Long(3)).list();
		}
		else if (companyID.equalsIgnoreCase("99")){
			// return 4 vehicles 
			return findVehicleByGroupId(999);
		}
		else {
			return ( List<Vehicle> )sessionFactory.getCurrentSession()
					.createQuery("from Vehicle as o where o.company.id=?")
					.setLong(0, new Long(companyID)).list();
		}
	}

	@Override
	public void update(Vehicle detachedInstance) {
		//log.debug("merging Vehicle instance");
		try {
			sessionFactory.getCurrentSession().update(detachedInstance);
			//log.debug("merge successful");
		} catch (RuntimeException re) {
			log.error("merge vehicle failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> findAllNoLocator() {
		log.info("find vehilces with no locator assigned..");

		return sessionFactory.getCurrentSession()
				.createQuery("from Vehicle as o where o.locator is null").list();
		}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> findNotAssignedVehiclesByCompany(String companyID) {
		// TODO Auto-generated method stub
		return (List<Vehicle>)sessionFactory.getCurrentSession().createQuery(
				"from Vehicle v where v.company.id =:id and v.vehiclegroup is null")
				.setLong("id", new Long(companyID)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> findVehicleByGroupId(long groupId) {
		log.debug("finding Vehicle instance by groupId " + groupId);
		
		List<Vehicle> results = new ArrayList<Vehicle>();
		
		try {
			// groupId 999 is for demo account, data is hardcoded. - 20140107
			if (groupId == 999){
				//log.info("find hardcoded: " + groupId);
				
				//long [] preset = new long [] {13, 36, 5, 42};
				long [] preset = new long [] {51, 52, 53, 54};
				
				for (Long l : preset){
					
					Vehicle v = findVehicleById(l);
					
					results.add(v);
				}
			}
			else {				
				results = sessionFactory.getCurrentSession()
					.createQuery("from Vehicle v where v.vehiclegroup.id = :vgId")
					.setLong("vgId", groupId).list();
			}
			
			//log.debug("find vehicles by groupId, result size: " + results.size());
			
		} catch (RuntimeException re) {
			log.error("find by groupId failed", re);
			//throw re;
		}
		
		return results;
	}

	@Override
	public Vehicle findVehicleByLocatorId(long locatorId) {
		try {
			Vehicle vh = (Vehicle) sessionFactory.getCurrentSession().createQuery("from Vehicle v where v.locator.id = :lcId")
					.setLong("lcId", locatorId).uniqueResult();
			
			return vh;
		}
		catch (Exception ex){
			log.error(ex);
			return null;
		}
	}

	@Override
	public long create(Vehicle persistentInstance) {
		return (Long) sessionFactory.getCurrentSession().save(persistentInstance);
	}
}
