package com.coordsafe.safedistance.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.ward.entity.Ward;

/**
 * @author Yang Wei
 * @Date Feb 4, 2014
 */
@Entity
public class Safedistance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private double distance;
	private int mins;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "wardId")
	private Set<Ward> wards;
	
	@OneToOne(mappedBy="safedistance", fetch = FetchType.EAGER)
	private Guardian guardian;
	
	private boolean active;

	/**
	 * 
	 */
	public Safedistance() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param distance
	 * @param mins
	 * @param guardian
	 */
	public Safedistance(double distance, int mins, Guardian guardian) {
		super();
		this.distance = distance;
		this.mins = mins;
		this.guardian = guardian;
		
		this.active = false;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the mins
	 */
	public int getMins() {
		return mins;
	}

	/**
	 * @param mins the mins to set
	 */
	public void setMins(int mins) {
		this.mins = mins;
	}

	/**
	 * @return the guardian
	 */
	public Guardian getGuardian() {
		return guardian;
	}

	/**
	 * @param guardian the guardian to set
	 */
	public void setGuardian(Guardian guardian) {
		this.guardian = guardian;
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
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the wards
	 */
	public Set<Ward> getWards() {
		return wards;
	}

	/**
	 * @param wards the wards to set
	 */
	public void setWards(Set<Ward> wards) {
		this.wards = wards;
	}

	/**
	 * @return the expired
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param expired the expired to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
