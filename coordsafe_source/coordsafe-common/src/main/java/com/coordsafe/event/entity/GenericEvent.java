/**
 * GenericEvent.java
 * May 3, 2013
 * Yang Wei
 */
package com.coordsafe.event.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.coordsafe.common.entity.LatLng;
/**
 * @author Yang Wei
 *
 */
@MappedSuperclass
public abstract class GenericEvent implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	public long locatorId;
	public String bearerName;
	public String type;
	public Date eventTime;
	public LatLng location;
	public String message;
	public boolean notifRequired;
	public boolean isNotifRequired() {
		return notifRequired;
	}
	public void setNotifRequired(boolean notifRequired) {
		this.notifRequired = notifRequired;
	}
	public boolean notifSent;
	
	public boolean isNotifSent() {
		return notifSent;
	}
	public void setNotifSent(boolean notifSent) {
		this.notifSent = notifSent;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLocatorId() {
		return locatorId;
	}
	public void setLocatorId(long locatorId) {
		this.locatorId = locatorId;
	}
	/**
	 * @return the bearerName
	 */
	public String getBearerName() {
		return bearerName;
	}
	/**
	 * @param bearerName the bearerName to set
	 */
	public void setBearerName(String bearerName) {
		this.bearerName = bearerName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public LatLng getLocation() {
		return location;
	}
	public void setLocation(LatLng location) {
		this.location = location;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}



}
