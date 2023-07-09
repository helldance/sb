/**
 * @author Yang Wei
 * @Date Sep 10, 2013
 */
package com.coordsafe.workorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coordsafe.vehicle.dao.VehicleDAO;
import com.coordsafe.vehicle.entity.Vehicle;
import com.coordsafe.workorder.dao.OrderDAO;
import com.coordsafe.workorder.dao.OrderListDAO;
import com.coordsafe.workorder.entity.BasicOrder;
import com.coordsafe.workorder.entity.OrderList;
import com.coordsafe.workorder.entity.OrderStatus;
import com.coordsafe.workorder.entity.SampleOrder;

/**
 * @author Yang Wei
 *
 */
@Service
public class SampleOrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDAO soDao;
	
	@Autowired
	private VehicleDAO vehicleDao;
	
	@Autowired
	private OrderListDAO orderListDao;

	@Override
	public void create(BasicOrder order) {
		soDao.create((SampleOrder) order);		
	}

	@Override
	public void update(BasicOrder order) {
		soDao.update((SampleOrder) order);		
	}

	@Override
	public void delete(long orderId) {
		soDao.delete(orderId);
	}

	@Override
	public SampleOrder find(long orderId) {
		// TODO Auto-generated method stub
		return (SampleOrder) soDao.find(orderId);
	}

	@Override
	public List<SampleOrder> findByCompany(long companyid) {
		// TODO Auto-generated method stub
		return (List<SampleOrder>) soDao.findByCompany(companyid);
	}

	@Override
	public List<? extends BasicOrder> findUnassignedByCompany(long companyid) {
		// TODO Auto-generated method stub
		return soDao.findUnassignedByCompany(companyid);
	}

	@Override
	public void autoPlanAll(long companyId) {
		// TODO Auto-generated method stub
		List<? extends BasicOrder> orders = soDao.findByCompany(companyId);
		List<Vehicle> vehicles = vehicleDao.findVehiclesByCompany(String.valueOf(companyId));
		List<OrderList> existTask = orderListDao.findByCompany(companyId);
		
		int avgCarryOrders = 1;
		
		if (vehicles != null && orders != null && orders.size() > 0 && vehicles.size() > 0){
			avgCarryOrders = orders.size() / vehicles.size();
		}
		
		for (BasicOrder order: orders){
			if (order.getStatus() == OrderStatus.NEW){
				for (Vehicle v : vehicles){
					for (OrderList o : existTask){
						if (o.getAssignee().equals(v) && o.getOrders() != null){
							int alrdyhave = o.getOrders().size();
						}
					}
				}
			}
		}
	}

}
