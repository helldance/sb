package com.coordsafe.gateway.entity;

public class VehicleParameter {
	private String start_internal;
	private String stop_internal;
	public VehicleParameter(String start_internal2, String stop_internal2) {
		this.start_internal = start_internal2;
		this.stop_internal = stop_internal2;
		// TODO Auto-generated constructor stub
	}
	public String getStart_internal() {
		return start_internal;
	}
	public void setStart_internal(String start_internal) {
		this.start_internal = start_internal;
	}
	public String getStop_internal() {
		return stop_internal;
	}
	public void setStop_internal(String stop_internal) {
		this.stop_internal = stop_internal;
	}
}
