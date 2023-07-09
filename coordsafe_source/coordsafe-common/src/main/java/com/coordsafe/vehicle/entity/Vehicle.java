
package com.coordsafe.vehicle.entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.coordsafe.company.entity.Company;
import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.locator.entity.Locator;

@Entity
@Table(name = "tbl_vehicle", schema = "public")
@JsonIgnoreProperties({"vehicleGroup", "company"})
public class Vehicle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="vehicle_name", nullable = false,unique= true)
	private String name;
	
	@Column(name="vehicle_model")
	private String model;
	@Column(name="vehicle_type")
	private String type;
	@Column(name="vehicle_status")
	private String status;
	@Column(name="license_plate", nullable = false,unique= true)
	private String licensePlate;
	private Date onRoadFrom;
	private long mileage;
	
	//private byte [] photo;
	
	private Date lastMaintanence;
	private Date nextMaintanence;
	
	@OneToOne
	private Locator locator;
	
	@ManyToOne
	private VehicleGroup vehiclegroup;
	@ManyToOne
	private Company company;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Geofence> fences;
	
	/**
	 * @return the fences
	 */
	public Set<Geofence> getFences() {
		return fences == null? new HashSet<Geofence>() : fences;
	}

	/**
	 * @param fences the fences to set
	 */
	public void setFences(Set<Geofence> fences) {
		this.fences = fences;
	}

	public Vehicle() {
		super();
		
		this.mileage = 0;
	}

	/**
	 * @param name
	 * @param model
	 * @param type
	 * @param status
	 * @param licensePlate
	 * @param vehiclegroup
	 * @param company
	 */
	public Vehicle(String name, String model, String type, String status,
			String licensePlate, VehicleGroup vehiclegroup, Company company) {
		super();
		this.name = name;
		this.model = model;
		this.type = type;
		this.status = status;
		this.licensePlate = licensePlate;
		this.vehiclegroup = vehiclegroup;
		this.company = company;
		
		this.mileage = 0;
	}

	public Vehicle(long id, String model, String type, String status,
			Integer driverId, String licensePlate, Date onRoadFrom,
			long mileage, Date lastMaintanence, Date nextMaintanence,Company company,Locator locator,VehicleGroup vGroup) {
		this.id = id;
		this.model = model;
		this.type = type;
		this.status = status;
		this.licensePlate = licensePlate;
		this.onRoadFrom = onRoadFrom;
		this.mileage = mileage;
		this.lastMaintanence = lastMaintanence;
		this.nextMaintanence = nextMaintanence;
		this.company = company;
		this.locator = locator;
		this.vehiclegroup = vGroup;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Locator getLocator() {
		return locator;
	}

	public void setLocator(Locator locator) {
		this.locator = locator;
	}
	@Column(name = "model")
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Column(name = "license_plate")
	public String getLicensePlate() {
		return this.licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "on_road_from", length = 35)
	public Date getOnRoadFrom() {
		return this.onRoadFrom;
	}

	public void setOnRoadFrom(Date onRoadFrom) {
		this.onRoadFrom = onRoadFrom;
	}

	@Column(name = "mileage")
	public long getMileage() {
		return this.mileage;
	}

	public void setMileage(long mileage) {
		this.mileage = mileage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_maintanence", length = 35)
	public Date getLastMaintanence() {
		return this.lastMaintanence;
	}

	public void setLastMaintanence(Date lastMaintanence) {
		this.lastMaintanence = lastMaintanence;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "next_maintanence", length = 35)
	public Date getNextMaintanence() {
		return this.nextMaintanence;
	}

	public void setNextMaintanence(Date nextMaintanence) {
		this.nextMaintanence = nextMaintanence;
	}
	
	public VehicleGroup getVehiclegroup() {
		return vehiclegroup;
	}

	public void setVehiclegroup(VehicleGroup vehiclegroup) {
		this.vehiclegroup = vehiclegroup;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	/*
	 * Transient Fields
	 */
	
	/*
	 * Check whether the current operation is adding a new organization.
	 */
	@Transient
	public boolean isCreate() {
		return (this.id == null);
	}
	
	@Override
	public int hashCode() {
	    return id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof Vehicle) {
	        Vehicle thatVehicle= (Vehicle) obj;
	        return this.id.equals(thatVehicle.id);
	    }
	    return false;
	}

}
