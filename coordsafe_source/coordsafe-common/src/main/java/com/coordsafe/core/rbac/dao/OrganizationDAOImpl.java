package com.coordsafe.core.rbac.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.core.rbac.entity.Group;
import com.coordsafe.core.rbac.entity.Organization;
import com.coordsafe.core.rbac.entity.User;
import com.coordsafe.core.rbac.dao.OrganizationDAO;
import com.coordsafe.core.rbac.dao.OrganizationDAOImpl;

@Repository("organizationDAO")
public class OrganizationDAOImpl implements OrganizationDAO {
	private static final Log log = LogFactory.getLog(OrganizationDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Organization organization) {
		log.debug("saving organization instance");
		sessionFactory.getCurrentSession().saveOrUpdate(organization);
	}

	@Override
	public void delete(Organization organization) {
		log.debug("deleting organization instance");
		//clearForeignKeys(organization);
		sessionFactory.getCurrentSession().delete(organization);
	}
	
	/*
	 * Organization is the inverse end of these relationship with user, group
	 * and its parent.
	 * Hence need to clear out all foreign keys before deleting the 
	 * organization itself.
	 */
	private void clearForeignKeys(Organization organization) {
		// Get the users currently related to this role.
		
		for (User user : organization.getUsers()) {
			user.getOrganizations().remove(organization);
		}
		
		for (Group group : organization.getGroups()) {
			group.getOrganizations().remove(organization);
		}
		try{
			if(organization.hasParentOrganization()){
				Organization org = organization.getParentOrganization();
				org.getChildOrganizations().remove(organization);
			}else{
				
			}
		}catch(NullPointerException e){
			log.error(e.getMessage());
		}
		

	}

	@Override
	public Organization findByName(String name) {
		log.debug("finding organization instance by name");
		return (Organization) sessionFactory.getCurrentSession()
				.createQuery("from Organization as o where o.name=?")
				.setString(0, name).uniqueResult();
	}
	
	@Override
	public Organization findById(Long id) {
		log.debug("finding organization instance by id");
		return (Organization) sessionFactory.getCurrentSession()
				.createQuery("from Organization as o where o.id=?")
				.setLong(0, id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> findAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Organization as o order by o.name").list();
	}

	@Override
	public void update(Organization organization) {
		sessionFactory.getCurrentSession().merge(organization);
	}

}
