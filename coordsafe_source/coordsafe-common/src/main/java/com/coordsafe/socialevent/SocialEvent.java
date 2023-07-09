/**
 * @author Yang Wei
 * @Date Oct 29, 2013
 */
package com.coordsafe.socialevent;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.coordsafe.common.entity.LatLng;

/**
 * @author Yang Wei
 *
 */
@Entity
public class SocialEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String place;
	private LatLng addr;
	
	private Date startTime;
	private Date endTime;
	
	private String remark;
	
	//private EventStatus status;

	/**
	 * @param name
	 * @param place
	 * @param addr
	 * @param startTime
	 * @param endTime
	 * @param remark
	 */
	public SocialEvent(String name, String place, LatLng addr, Date startTime,
			Date endTime, String remark) {
		super();
		this.name = name;
		this.place = place;
		this.addr = addr;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remark = remark;
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the addr
	 */
	public LatLng getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(LatLng addr) {
		this.addr = addr;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
}
