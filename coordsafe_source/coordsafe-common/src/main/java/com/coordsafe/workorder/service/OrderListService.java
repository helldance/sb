/**
 * @author Yang Wei
 * @Date Sep 16, 2013
 */
package com.coordsafe.workorder.service;

import java.util.List;

import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.workorder.entity.OrderList;

/**
 * @author Yang Wei
 *
 */
public interface OrderListService {
	public OrderList getById(long id);
	public void create(OrderList list);
	public void update(OrderList list);
	public void deleteById(long id);
	
	public List<OrderList> getByAssignee(Vehicle v);
	public List<OrderList> findByCompany(Long companyId);
	public OrderList getCurrentByAssignee(Vehicle v);
	public OrderList getLatestByAssignee(Vehicle v);
}
