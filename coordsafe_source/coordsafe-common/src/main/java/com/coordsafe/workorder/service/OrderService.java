/**
 * @author Yang Wei
 * @Date Sep 10, 2013
 */
package com.coordsafe.workorder.service;

import java.util.List;

import com.coordsafe.workorder.entity.BasicOrder;

/**
 * @author Yang Wei
 *
 */
public interface OrderService {
	public void create(BasicOrder order);
	public void update(BasicOrder order);
	public void delete(long orderId);
	public BasicOrder find(long orderId);
	
	public List<? extends BasicOrder> findByCompany(long companyid);
	public List<? extends BasicOrder> findUnassignedByCompany(long companyid);
	public void autoPlanAll(long companyId);
}
