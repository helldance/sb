package com.coordsafe.vehicle.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.vehicle.entity.VehicleGroup;
import org.slf4j.*;
@Repository
@Transactional
public class VehicleGroupDAOImpl implements VehicleGroupDAO {
	private static final Logger log = LoggerFactory.getLogger(VehicleGroupDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;	
	@Override
	public void create(VehicleGroup group) {
		log.info("create vechile group");
		
		sessionFactory.getCurrentSession().save(group);
	}

	@Override
	public void update(VehicleGroup group) {
		log.info("update vechile group");
		
		sessionFactory.getCurrentSession().update(group);
	}

	@Override
	public void delete(long id) {
		log.info("deactivate vechile group");
		
		VehicleGroup group = this.findVehicleGroupById(id);
		group.setValid(false);
		
		sessionFactory.getCurrentSession().update(group);
		
	}

	@Override
	public VehicleGroup findVehicleGroupById(long id) {
		log.info("find vechile group by Id: " + id);
		return (VehicleGroup) sessionFactory.getCurrentSession().get(VehicleGroup.class, id);
		//return (VehicleGroup) sessionFactory.getCurrentSession().createQuery("from VehicleGroup g where g.id = ?").setLong(0, id).uniqueResult();
	}

	@Override
	public void delete(String name) {
		sessionFactory.getCurrentSession().delete(this.findByName(name));
				
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleGroup> findAll() {
		return (List<VehicleGroup>)sessionFactory.getCurrentSession().createQuery("from VehicleGroup").list();
	}

	@Override
	public VehicleGroup findByName(String name) {
		// TODO Auto-generated method stub
		return (VehicleGroup)sessionFactory.getCurrentSession().createQuery("from VehicleGroup vg where vg.groupName =:name ").setString("name", name).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleGroup> findVehicleGroupsByCompany(String companyID) {

		// TODO Auto-generated method stub

		log.debug("finding vehiclegroup instance by company");
		
		// shamir's hack
		if (companyID.equalsIgnoreCase("2")){
			return ( List<VehicleGroup> )sessionFactory.getCurrentSession()
					.createQuery("from VehicleGroup as o where o.company.id=? or o.company.id = ?")
					.setLong(0, new Long(companyID)).setLong(1, new Long(3)).list();
		}
		else {
			return ( List<VehicleGroup> )sessionFactory.getCurrentSession()
				.createQuery("from VehicleGroup as o where o.company.id=?")
				.setLong(0, new Long(companyID)).list();
		}
		
	}

}
