/**
 * @author Yang Wei
 * @Date Sep 10, 2013
 */
package com.coordsafe.workorder.entity;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.coordsafe.common.entity.LatLng;
import com.coordsafe.company.entity.Company;
import com.coordsafe.vehicle.entity.Vehicle;

/**
 * @author Yang Wei
 *
 */
@MappedSuperclass
@JsonIgnoreProperties({"company", "assignee", "orderList"})
public abstract class BasicOrder implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	protected String orderNumber;
	@Enumerated(EnumType.STRING)
	protected OrderStatus status;
	protected LatLng place;
	protected String address;
	protected String remark;
	protected String orderFrom;
	protected Date orderDt;
	protected Date targetCompletionDt;
	protected Date completionDt;
	protected String createBy;
	
	@ManyToOne
	protected Company company;
	
	@ManyToOne
	protected Vehicle assignee;
	
	@ManyToOne
	protected OrderList orderList;
	
	
	/*@ManyToOne
	protected Driver assignTo;*/
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the status
	 */
	public OrderStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	/**
	 * @return the place
	 */
	public LatLng getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(LatLng place) {
		this.place = place;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the orderFrom
	 */
	public String getOrderFrom() {
		return orderFrom;
	}
	/**
	 * @param orderFrom the orderFrom to set
	 */
	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	/**
	 * @return the orderDt
	 */
	public Date getOrderDt() {
		return orderDt;
	}
	/**
	 * @param orderDt the orderDt to set
	 */
	public void setOrderDt(Date orderDt) {
		this.orderDt = orderDt;
	}
	/**
	 * @return the targetCompletionDt
	 */
	public Date getTargetCompletionDt() {
		return targetCompletionDt;
	}
	/**
	 * @param targetCompletionDt the targetCompletionDt to set
	 */
	public void setTargetCompletionDt(Date targetCompletionDt) {
		this.targetCompletionDt = targetCompletionDt;
	}
	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	/**
	 * @return the completionDt
	 */
	public Date getCompletionDt() {
		return completionDt;
	}
	/**
	 * @param completionDt the completionDt to set
	 */
	public void setCompletionDt(Date completionDt) {
		this.completionDt = completionDt;
	}
	
	/**
	 * @return the assignee
	 */
	public Vehicle getAssignee() {
		return assignee;
	}
	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(Vehicle assignee) {
		this.assignee = assignee;
	}
	
	/**
	 * @return the orderList
	 */
	public OrderList getOrderList() {
		return orderList;
	}
	/**
	 * @param orderList the orderList to set
	 */
	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}
	@Transient
	public boolean isCreate (){
		return !(this.id > 0);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((orderNumber == null) ? 0 : orderNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicOrder other = (BasicOrder) obj;
		if (id != other.id)
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		return true;
	}
}
