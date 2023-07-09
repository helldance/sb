package com.coordsafe.retailer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Simcard implements java.io.Serializable{
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String simimei ;
	private String simphone;
	private String simtype;
	private String simowner;
	private String simretailer;
	private String userphone;
	private String deviceid;
	private Date issuedate;
	

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSimimei() {
		return simimei;
	}
	public void setSimimei(String simimei) {
		this.simimei = simimei;
	}
	public String getSimphone() {
		return simphone;
	}
	public void setSimphone(String simphone) {
		this.simphone = simphone;
	}
	public String getSimtype() {
		return simtype;
	}
	public void setSimtype(String simtype) {
		this.simtype = simtype;
	}
	public String getSimowner() {
		return simowner;
	}
	public void setSimowner(String simowner) {
		this.simowner = simowner;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getSimretailer() {
		return simretailer;
	}
	public void setSimretailer(String simretailer) {
		this.simretailer = simretailer;
	}
	public Date getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(Date issuedate) {
		this.issuedate = issuedate;
	}

	

}
