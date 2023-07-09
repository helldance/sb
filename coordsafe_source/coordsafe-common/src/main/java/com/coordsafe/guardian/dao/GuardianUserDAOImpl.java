package com.coordsafe.guardian.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coordsafe.guardian.entity.Guardian;

@Repository("guardianDAO")
public class GuardianUserDAOImpl implements GuardianUserDAO {

	private static final Log log = LogFactory.getLog(GuardianUserDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Guardian guardian) {
		log.debug("saving Guardian instance");
		sessionFactory.getCurrentSession().saveOrUpdate(guardian);
	}

	@Override
	public void update(Guardian guardian) {
		log.debug("updating Guardian instance");
		sessionFactory.getCurrentSession().saveOrUpdate(guardian);
	}

	@Override
	public void delete(Guardian guardian) {
		log.debug("deleting Guardian instance");
		sessionFactory.getCurrentSession().delete(guardian);
	}
	


	@Override
	public Guardian findByGuardianname(String guardianname) {
		log.debug("finding Guardian instance with guardian name: " + guardianname);
		return (Guardian) sessionFactory.getCurrentSession()
				.createQuery("from Guardian as u where u.login=?")
				.setString(0, guardianname).uniqueResult();
	}
	
	@Override
	public Guardian findById(Integer id) {
		log.debug("finding Guardian instance with id: " + id);
		return (Guardian) sessionFactory.getCurrentSession()
				.createQuery("from Guardian as u where u.id=?")
				.setLong(0, id).uniqueResult();
	}





	@Override
	public Boolean updatePassword(String guardianname, String password, String salt) {
		Guardian guardian = findByGuardianname(guardianname);

		if (guardian != null) {
			guardian.setPasswd(password);//.setPassword(password);
			
			this.update(guardian);
			return true;
		} else {
			return false;
		}
	}




	@Override
	public Boolean lockGuardianAccount(String guardianname) {
		Guardian guardian = findByGuardianname(guardianname);

		if (guardian != null) {
			guardian.setEnabled(false);
			this.update(guardian);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean unlockGuardianAccount(String guardianname) {
		Guardian guardian = findByGuardianname(guardianname);

		if (guardian != null) {
			guardian.setEnabled(true);
			this.update(guardian);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Guardian> getAllGuardians() {
		// TODO Auto-generated method stub
		log.debug("finding all Guardians instance");
		return (List<Guardian>) sessionFactory.getCurrentSession()
				.createQuery("from Guardian").list();
	}

	@Override
	public Guardian getByEmail(String email) {
		log.debug("finding Guardian instance with email: " + email);
		return (Guardian) sessionFactory.getCurrentSession()
				.createQuery("from Guardian as u where u.email=?")
				.setString(0, email).uniqueResult();
	}

	@Override
	public Guardian getByApiKey(String apikey) {
		// TODO Auto-generated method stub
		return (Guardian) sessionFactory.getCurrentSession().createQuery("from Guardian g where g.key.key = :key")
				.setString("key", apikey).uniqueResult();
	}

}
