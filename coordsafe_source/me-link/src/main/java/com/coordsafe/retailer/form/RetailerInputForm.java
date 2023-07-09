package com.coordsafe.retailer.form;

import org.hibernate.validator.constraints.NotBlank;

public class RetailerInputForm {
	private long id;
	@NotBlank
	private String deviceid;
	@NotBlank
	private String devicepasswd;
	@NotBlank
	private String simphone;
	@NotBlank
	private String simimei;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getDevicepasswd() {
		return devicepasswd;
	}
	public void setDevicepasswd(String devicepasswd) {
		this.devicepasswd = devicepasswd;
	}
	public String getSimphone() {
		return simphone;
	}
	public void setSimphone(String simphone) {
		this.simphone = simphone;
	}
	public String getSimimei() {
		return simimei;
	}
	public void setSimimei(String simimei) {
		this.simimei = simimei;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
