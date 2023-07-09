/**
 * @author Yang Wei
 * @Date Sep 10, 2013
 */
package com.coordsafe.workorder.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.workorder.entity.BasicOrder;
import com.coordsafe.workorder.entity.SampleOrder;

/**
 * @author Yang Wei
 *
 */
@Transactional 
@Repository
public class SampleOrderDAOImpl implements OrderDAO{
	private static final Log logger = LogFactory.getLog(SampleOrderDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(BasicOrder order) {
		logger.info("create sample order " + order.getId());
		
		sessionFactory.getCurrentSession().save(order);
	}

	@Override
	public void update(BasicOrder order) {
		logger.info("update sample order " + order.getId());
		
		sessionFactory.getCurrentSession().update(order);
		
	}

	@Override
	public void delete(long orderId) {
		logger.info("delete sample order " + orderId);
		
		sessionFactory.getCurrentSession().delete(find(orderId));
		
	}

	@Override
	public SampleOrder find(long orderId) {
		logger.info("find sample order " + orderId);
		
		return (SampleOrder) sessionFactory.getCurrentSession().createQuery("from SampleOrder where id = :oid").setLong("oid", orderId).uniqueResult();
	}

	@Override
	public List<SampleOrder> findByCompany(long companyid) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from SampleOrder where company.id = :cid").setLong("cid", companyid).list();
	}

	@Override
	public List<SampleOrder> findUnassignedByCompany(long companyid) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from SampleOrder where company.id = :cid and status = 'NEW'").setLong("cid", companyid).list();
	}

}
