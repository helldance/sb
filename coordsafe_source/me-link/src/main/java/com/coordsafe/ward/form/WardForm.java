package com.coordsafe.ward.form;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



public class WardForm {
	long id;
	@NotBlank
	String name;
	@NotBlank
	String deviceid;
	@NotBlank
	String devicepassword;

	String phone;
	String photourl;
	String photo64;

	String photo32;
	
	List<String> gfs = new ArrayList<String>();
	
	private CommonsMultipartFile  photo;
	private String lat;
	private String lon;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getDevicepassword() {
		return devicepassword;
	}
	public void setDevicepassword(String devicepassword) {
		this.devicepassword = devicepassword;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public CommonsMultipartFile  getPhoto() {
		return photo;
	}
	public void setPhoto(CommonsMultipartFile  photo) {
		this.photo = photo;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getPhoto64() {
		return photo64;
	}
	public void setPhoto64(String photo64) {
		this.photo64 = photo64;
	}
	public String getPhoto32() {
		return photo32;
	}
	public void setPhoto32(String photo32) {
		this.photo32 = photo32;
	}
	public List<String> getGfs() {
		return gfs;
	}
	public void setGfs(List<String> gfs) {
		this.gfs = gfs;
	}
	

}
