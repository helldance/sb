package com.coordsafe.trip.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trip {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private long locatorId;
	private long tripStartId;
	private long tripEndId;
	private Date tripStartTime;
	private Date tripEndTime;
	private long movingTime;
	private double mileage;
	private double mileage_manual;
	private double fuel;
	private double fuel_manual;
	private int pause;
	private int speeding;
	private int jameBreak;
	private String remarks;
	private boolean valid;
	
	/**
	 * 
	 */
	public Trip() {
		super();
		
		// initialize non-empty fields
		this.valid = true;
		this.movingTime = 0;
		this.pause = 0;
		this.speeding = 0;
		this.jameBreak = 0;
		this.mileage = this.mileage_manual = this.fuel = this.fuel_manual = 0;
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
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the locator_id
	 */
	public long getLocatorId() {
		return locatorId;
	}

	/**
	 * @param locator_id the locator_id to set
	 */
	public void setLocatorId(long locator_id) {
		this.locatorId = locator_id;
	}

	/**
	 * @return the trip_start_id
	 */
	public long getTripStartId() {
		return tripStartId;
	}

	/**
	 * @param trip_start_id the trip_start_id to set
	 */
	public void setTripStartId(long trip_start_id) {
		this.tripStartId = trip_start_id;
	}

	/**
	 * @return the trip_end_id
	 */
	public long getTripEndId() {
		return tripEndId;
	}

	/**
	 * @param l the trip_end_id to set
	 */
	public void setTripEndId(long l) {
		this.tripEndId = l;
	}

	/**
	 * @return the trip_start_time
	 */
	public Date getTripStartTime() {
		return tripStartTime;
	}

	/**
	 * @param trip_start_time the trip_start_time to set
	 */
	public void setTripStartTime(Date trip_start_time) {
		this.tripStartTime = trip_start_time;
	}

	/**
	 * @return the trip_end_time
	 */
	public Date getTripEndTime() {
		return tripEndTime;
	}

	/**
	 * @param trip_end_time the trip_end_time to set
	 */
	public void setTripEndTime(Date trip_end_time) {
		this.tripEndTime = trip_end_time;
	}	

	/**
	 * @return the movingTime
	 */
	public long getMovingTime() {
		return movingTime;
	}

	/**
	 * @param movingTime the movingTime to set
	 */
	public void setMovingTime(long movingTime) {
		this.movingTime = movingTime;
	}

	/**
	 * @return the mileage
	 */
	public double getMileage() {
		return mileage;
	}

	/**
	 * @param mileage the mileage to set
	 */
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	

	/**
	 * @return the mileage_manual
	 */
	public double getMileage_manual() {
		return mileage_manual;
	}

	/**
	 * @param mileage_manual the mileage_manual to set
	 */
	public void setMileage_manual(double mileage_manual) {
		this.mileage_manual = mileage_manual;
	}
	
	/**
	 * @return the fuel
	 */
	public double getFuel() {
		return fuel;
	}

	/**
	 * @param fuel the fuel to set
	 */
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return the fuel_manual
	 */
	public double getFuel_manual() {
		return fuel_manual;
	}

	/**
	 * @param fuel_manual the fuel_manual to set
	 */
	public void setFuel_manual(double fuel_manual) {
		this.fuel_manual = fuel_manual;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the pause
	 */
	public int getPause() {
		return pause;
	}

	/**
	 * @param pause the pause to set
	 */
	public void setPause(int pause) {
		this.pause = pause;
	}

	/**
	 * @return the speeding
	 */
	public int getSpeeding() {
		return speeding;
	}

	/**
	 * @param speeding the speeding to set
	 */
	public void setSpeeding(int speeding) {
		this.speeding = speeding;
	}

	/**
	 * @return the jameBreak
	 */
	public int getJameBreak() {
		return jameBreak;
	}

	/**
	 * @param jameBreak the jameBreak to set
	 */
	public void setJameBreak(int jameBreak) {
		this.jameBreak = jameBreak;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public void increatePause (){
		this.pause ++;
	}
	
	public void increateSpeeding (){
		this.speeding ++;
	}
	
	public void increateJamebreak(){
		this.jameBreak ++;
	}
}
