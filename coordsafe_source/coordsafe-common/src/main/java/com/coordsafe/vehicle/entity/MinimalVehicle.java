/**
 * 
 */
package com.coordsafe.vehicle.entity;

import java.util.Date;

import com.coordsafe.common.entity.LatLng;

/**
 * @author Yang Wei
 *
 */
public class MinimalVehicle {
	public long vehicleId;
	public String vehicleName;
	public String vehicleType;
	public String vehicleModel;
	public String licensePalette;
	public String driver;
	public LatLng latLng;
	public String status;
	public double speed;
	public long mileage;
	public double altitude;
	public Date lastUpdate;
	public long locatorId;
	
	/**
	 * @param vehicleId
	 * @param vechileName
	 * @param vechileType
	 * @param vehicleModel
	 * @param licensePalette
	 * @param driver
	 * @param latLng
	 * @param status
	 * @param speed
	 * @param altitude
	 * @param lastUpdate
	 * @param locatorId
	 */
	public MinimalVehicle(long vehicleId, String vechileName,
			String vechileType, String vehicleModel, String licensePalette,
			String driver, LatLng latLng, String status, double speed, long mileage,
			double altitude, Date lastUpdate, long locatorId) {
		super();
		this.vehicleId = vehicleId;
		this.vehicleName = vechileName;
		this.vehicleType = vechileType;
		this.vehicleModel = vehicleModel;
		this.licensePalette = licensePalette;
		this.driver = driver;
		this.latLng = latLng;
		this.status = status;
		this.speed = speed;
		this.mileage = mileage;
		this.altitude = altitude;
		this.lastUpdate = lastUpdate;
		this.locatorId = locatorId;
	}

	/**
	 * 
	 */
	public MinimalVehicle() {
		super();
		// TODO Auto-generated constructor stub
	}
}
