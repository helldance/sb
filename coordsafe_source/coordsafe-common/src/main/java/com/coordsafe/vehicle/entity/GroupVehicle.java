/**
 * 
 */
package com.coordsafe.vehicle.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Yang Wei
 *
 */
public class GroupVehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SimpleVehicleGroup group;
	public Set<SimpleVehicle> vehicles;
	
	/**
	 * 
	 */
	public GroupVehicle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param vehicles
	 */
	public GroupVehicle(SimpleVehicleGroup group, Set<SimpleVehicle> vehicles) {
		super();
		this.group = group;
		this.vehicles = vehicles;
	}

	public class SimpleVehicleGroup {
		public long groupId;
		public String groupName;
	}
	
	public class SimpleVehicle {
		public long vehicleId;
		public long locatorId;
		public String vehicleName;
	}
}
