/**
 * @author Yang Wei
 * @Date Sep 10, 2013
 */
package com.coordsafe.workorder.dao;

import java.util.List;

import com.coordsafe.workorder.entity.BasicOrder;

/**
 * @author Yang Wei
 *
 */
public interface OrderDAO {
	public void create(BasicOrder order);
	public void update(BasicOrder order);
	public void delete(long orderId);
	public BasicOrder find(long orderId);
	public List<? extends BasicOrder> findByCompany(long companyid);
	public List<? extends BasicOrder> findUnassignedByCompany(long companyid);
}
