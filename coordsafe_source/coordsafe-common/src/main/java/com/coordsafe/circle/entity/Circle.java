package com.coordsafe.circle.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.ward.entity.Ward;

@Entity
public class Circle implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	@ManyToOne
	@JoinColumn(name="cirlce_admin_id")
	private Guardian admin;
	
	private Date createdTime;
	private String createdBy;
	private boolean isPaid;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Guardian> guardiansInCircle;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Ward> wardsInCircle;
	
	public Long getId() {
		return id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public Guardian getAdmin() {
		return admin;
	}
	public void setAdmin(Guardian admin) {
		this.admin = admin;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Set<Guardian> getGuardiansInCircle() {
		return guardiansInCircle;
	}
	public void setGuardiansInCircle(Set<Guardian> guardiansInCircle) {
		this.guardiansInCircle = guardiansInCircle;
	}
	public Set<Ward> getWardsInCircle() {
		return wardsInCircle;
	}
	public void setWardsInCircle(Set<Ward> wardsInCircle) {
		this.wardsInCircle = wardsInCircle;
	}


}
