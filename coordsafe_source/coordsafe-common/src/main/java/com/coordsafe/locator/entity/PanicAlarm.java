package com.coordsafe.locator.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.coordsafe.ward.entity.Ward;

@Entity
@JsonIgnoreProperties({"ward"})
//@SequenceGenerator(name = "PANIC_SEQ", sequenceName = "panic_seq", allocationSize = 1)
public class PanicAlarm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String imei;
	private Date time;
	private String alarmType;
	private String alarmMessage;
	private Double lat;
	private Double lng;
	private boolean acknowledged;
	@ManyToOne
	private Ward ward;
	
	public PanicAlarm() {
		super();
	}

	public PanicAlarm(String imei, Double lat, Double lng, String alarmType,String alarmMessage, Ward ward) {
		super();
		this.imei = imei;
		this.lat = lat;
		this.lng = lng;
		this.time = new Date();
		this.alarmType = alarmType;
		this.alarmMessage = alarmMessage;
		this.acknowledged = false;
		this.ward = ward;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmMessage() {
		return alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}
}
