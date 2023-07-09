package com.coordsafe.notification.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

import com.coordsafe.vehicle.entity.Vehicle;


@Entity
@DynamicUpdate(value = true)
public class VehicleNotificationSetting extends AbstractNotificationSetting{
	

	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


}
