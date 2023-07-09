/**
 * @author Yang Wei
 * @Date Sep 16, 2013
 */
package com.coordsafe.workorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.workorder.dao.OrderListDAO;
import com.coordsafe.workorder.entity.OrderList;

/**
 * @author Yang Wei
 *
 */
@Service
public class OrderListServiceImpl implements OrderListService{
	@Autowired
	private OrderListDAO orderListDao;

	@Override
	public OrderList getById(long id) {
		// TODO Auto-generated method stub
		return orderListDao.getById(id);
	}

	@Override
	public void create(OrderList list) {
		orderListDao.create(list);
	}

	@Override
	public void update(OrderList list) {
		orderListDao.update(list);
	}

	@Override
	public void deleteById(long id) {
		orderListDao.deleteById(id);
	}

	@Override
	public List<OrderList> getByAssignee(Vehicle assignee) {
		return orderListDao.getByAssignee(assignee);
	}

	@Override
	public List<OrderList> findByCompany(Long companyId) {
		// TODO Auto-generated method stub
		return orderListDao.findByCompany(companyId);
	}

	@Override
	public OrderList getCurrentByAssignee(Vehicle v) {
		// TODO Auto-generated method stub
		return orderListDao.getCurrentByAssignee(v);
	}

	@Override
	public OrderList getLatestByAssignee(Vehicle v) {
		// TODO Auto-generated method stub
		return orderListDao.getLatestByAssignee(v);
	}

}
