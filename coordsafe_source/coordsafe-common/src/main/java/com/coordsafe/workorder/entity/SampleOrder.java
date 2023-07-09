/**
 * @author Yang Wei
 * @Date Sep 10, 2013
 */
package com.coordsafe.workorder.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.coordsafe.common.entity.LatLng;

/**
 * @author Yang Wei
 *
 */
@Entity
public class SampleOrder extends BasicOrder {
	private static final long serialVersionUID = 1L;

	private int quantity;	
	
	public SampleOrder(LatLng place, String address, String remark,
			String orderFrom, int quantity, Date orderDt, Date targetDt) {
		super();
		
		this.place = place;
		this.address = address;
		this.remark = remark;
		this.orderFrom = orderFrom;
		this.orderDt = orderDt;
		this.targetCompletionDt = targetDt;
		
		this.quantity = quantity;
		// TODO Auto-generated constructor stub
	}

	public SampleOrder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
