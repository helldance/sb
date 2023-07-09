/**
 * 
 */
package com.coordsafe.api.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.api.entity.ApiRequest;

/**
 * @author Yang Wei
 *
 */
@Repository
@Transactional
public class ApiRequestDAOImpl implements ApiRequestDAO {
	private static final Log log = LogFactory.getLog(ApiRequestDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(ApiRequest req) {
		sessionFactory.getCurrentSession().save(req);
	}

	@Override
	public void update(ApiRequest req) {
		sessionFactory.getCurrentSession().update(req);
	}

	@Override
	public void delete(ApiRequest req) {
		sessionFactory.getCurrentSession().delete(req);
	}

	@Override
	public ApiRequest findById(long id) {
		return (ApiRequest) sessionFactory.getCurrentSession().createQuery("from ApiRequest rq where rq.id = :rqid").setLong("rqid", id).uniqueResult();
	}

	@Override
	public List<ApiRequest> findRequestByTime(long keyId, Date start, Date end) {
		log.info("find request by key: " + keyId + " " + start + " " + end);
		
		return (List<ApiRequest>) sessionFactory.getCurrentSession().createQuery("from ApiRequest rq where rq.key.id = :kid" +
				" and rq.reqDt >= :sd and rq.reqDt <= :ed order by rq.reqDt desc").setLong("kid", keyId).setDate("sd", start).setDate("ed", end).list();

	}

	@Override
	public int findUseCountByTime(long keyId, Date _dtStart,
			Date _dtEnd) {
		log.info("find usage count for key: " + keyId + " " + _dtStart + " " + _dtEnd);
		
		List<ApiRequest> reqs = this.findRequestByTime(keyId, _dtStart, _dtEnd);
		
		if (reqs != null){
			return reqs.size();
		}
		
		return 0;

	}

}
