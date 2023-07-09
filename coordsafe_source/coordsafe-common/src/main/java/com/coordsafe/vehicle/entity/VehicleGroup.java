/**
 * VehicleGroup.java
 * Jun 4, 2013
 * Yang Wei
 */
package com.coordsafe.vehicle.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.coordsafe.company.entity.Company;

/**
 * @author Yang Wei
 *
 */
@Entity
@JsonIgnoreProperties({"company"})
public class VehicleGroup implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String groupName;
	private String description;
	private boolean valid;
	
	@ManyToOne
	private Company company;
	
	@OneToMany(mappedBy = "vehiclegroup", fetch=FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE})
	//@ElementCollection(targetClass=Vehicle.class)
	private Set<Vehicle> vehicles;

	/**
	 * @param groupName
	 * @param companyId
	 */
	public VehicleGroup(String groupName, Company company) {
		super();
		this.groupName = groupName;
		this.company = company;
	}
	/**
	 * @param groupName
	 * @param description
	 * @param companyId
	 */
	public VehicleGroup(String groupName, String description, Company company) {
		super();
		this.groupName = groupName;
		this.description = description;
		this.company = company;
	}
	/**
	 * 
	 */
	public VehicleGroup() {
		super();
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the companyId
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompany(Company company) {
		this.company = company;
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
	

	public Set<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
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
	
/*	public boolean removeVehicles(String[] vehicleIDs){
		
		for(Iterator<Vehicle> it=this.getVehicles().iterator(); it.hasNext();){
			it.next().getId()
		}
		if(this.id == vehicle.getId())
			return true;
		return false;
		
	}*/

}
