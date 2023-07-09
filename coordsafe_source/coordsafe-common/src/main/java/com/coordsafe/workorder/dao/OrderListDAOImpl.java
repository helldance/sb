/**
 * @author Yang Wei
 * @Date Sep 16, 2013
 */
package com.coordsafe.workorder.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.workorder.entity.OrderList;

/**
 * @author Yang Wei
 *
 */
@Repository
@Transactional
public class OrderListDAOImpl implements OrderListDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public OrderList getById(long id) {
		// TODO Auto-generated method stub
		return (OrderList) sessionFactory.getCurrentSession().createQuery("from OrderList where id = :orderId").setLong("orderId", id).uniqueResult();
	}

	@Override
	public void create(OrderList list) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(list);
	}

	@Override
	public void update(OrderList list) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(list);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(this.getById(id));
	}

	@Override
	public List<OrderList> getByAssignee(Vehicle assignee) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from OrderList where assignee.id = :assignee order by createDt desc").setLong("assignee", assignee.getId()).list();
	}

	@Override
	public List<OrderList> findByCompany(Long companyId) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from OrderList where company.id = :companyId").setLong("companyId", companyId).list();
	}

	@Override
	public OrderList getCurrentByAssignee(Vehicle v) {
		// TODO Auto-generated method stub
		List<OrderList> lists = this.getByAssignee(v);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (lists != null && lists.size() > 0){
			for (OrderList l : lists){
				try {
					if (l.getCreateDt().after(sdf.parse(sdf.format(new Date())))){
						return l;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	@Override
	public OrderList getLatestByAssignee(Vehicle v) {
		// TODO Auto-generated method stub
		List<OrderList> lists = this.getByAssignee(v);
		
		if (lists != null && lists.size() > 0){
			return lists.get(0);
		}
		
		return null;
	}

}
