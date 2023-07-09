/**
 * 
 */
package com.coordsafe.event.entity;


import com.coordsafe.ward.entity.Ward;

/**
 * @author Yang Wei
 *
 */
public class WardEvent extends GenericEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Ward ward;
	private String sms;

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}
	
}
