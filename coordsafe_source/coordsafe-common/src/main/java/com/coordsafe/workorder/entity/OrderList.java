/**
 * @author Yang Wei
 * @Date Sep 15, 2013
 */
package com.coordsafe.workorder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.coordsafe.company.entity.Company;
import com.coordsafe.vehicle.entity.Vehicle;

/**
 * @author Yang Wei
 *
 */
@Entity
@JsonIgnoreProperties({"company", "assignee"})
public class OrderList implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	protected String taskListNum;
	
	@ManyToOne
	protected Vehicle assignee;
	
	@OneToMany(targetEntity=SampleOrder.class, mappedBy="orderList", fetch=FetchType.EAGER)
	protected List<? extends BasicOrder> orders;
	
	protected String createBy;
	protected Date createDt;
	protected double completion;
	
	@ManyToOne
	protected Company company;

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
	 * @return the taskListNum
	 */
	public String getTaskListNum() {
		return taskListNum;
	}

	/**
	 * @param taskListNum the taskListNum to set
	 */
	public void setTaskListNum(String taskListNum) {
		this.taskListNum = taskListNum;
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
	 * @return the orders
	 */
	public List<? extends BasicOrder> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<? extends BasicOrder> orders) {
		this.orders = orders;
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
	 * @return the createDt
	 */
	public Date getCreateDt() {
		return createDt;
	}

	/**
	 * @param createDt the createDt to set
	 */
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
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
	 * @return the completion
	 */
	public double getCompletion() {
		return completion;
	}

	/**
	 * @param completion the completion to set
	 */
	public void setCompletion(double completion) {
		this.completion = completion;
	}
	
	@Transient
	public boolean isCreate (){
		return !(this.id > 0);
	}
	
}
