package com.coordsafe.ward.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.coordsafe.geofence.entity.Geofence;
import com.coordsafe.guardian.entity.Guardian;
import com.coordsafe.locator.entity.Locator;
import com.coordsafe.locator.entity.PanicAlarm;
import com.coordsafe.safedistance.entity.Safedistance;
import com.coordsafe.wardevent.entity.WardEvent;


@Entity

public class Ward implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String gender;
	private String photourl;
	private String photo64;
	private String photo32;
	private String photo32Grey;
	private Date birthDate;
	private String email;
	private String nric;
	private String status;
	private boolean isNotified;
	private Date notificationDate;
	private String fenceStatus;
	@Transient
	private int count = 0;
	
	@OneToOne
	private Locator locator;
	
	@OneToMany(cascade={ javax.persistence.CascadeType.ALL }, mappedBy="ward",fetch = FetchType.EAGER)
	private Set<WardEvent> wardEvents;
	
	@OneToMany(cascade={ javax.persistence.CascadeType.ALL }, mappedBy="ward",fetch = FetchType.EAGER)
	private Set<PanicAlarm> panicAlarms;
	
	@JsonIgnore
	@ManyToMany(mappedBy="wards",fetch = FetchType.EAGER)
	private Set<Guardian> guardians;
	
	@ManyToMany(mappedBy="wards",fetch = FetchType.EAGER)
	private Set<Geofence> geofences;
	
	@ManyToMany
	private Set<Safedistance> safedistances;
	
	public Set<Guardian> getGuardians() {
		return guardians == null? new HashSet<Guardian> () : guardians;
	}

	public void setGuardians(Set<Guardian> guardians) {
		this.guardians = guardians;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Locator getLocator() {
		return locator;
	}

	public void setLocator(Locator locator) {
		this.locator = locator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}
	
	/*
	 * Check whether the current operation is adding a new user.
	 */
	@Transient
	public boolean isCreate() {
		return (this.name== null);
	}


	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Geofence> getGeofences() {
		return geofences;
	}

	public void setGeofences(Set<Geofence> geofences) {
		this.geofences = geofences;
	}

	public String getPhoto64() {
		return photo64;
	}

	public void setPhoto64(String photo64) {
		this.photo64 = photo64;
	}

	public String getPhoto32() {
		return photo32;
	}

	public void setPhoto32(String photo32) {
		this.photo32 = photo32;
	}

	public String getPhoto32Grey() {
		return photo32Grey;
	}

	public void setPhoto32Grey(String photo32Grey) {
		this.photo32Grey = photo32Grey;
	}

	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}

	public Set<WardEvent> getWardEvents() {
		return wardEvents;
	}

	public void setWardEvents(Set<WardEvent> wardEvents) {
		this.wardEvents = wardEvents;
	}

	public Set<PanicAlarm> getPanicAlarms() {
		return panicAlarms;
	}

	public void setPanicAlarms(Set<PanicAlarm> panicAlarms) {
		this.panicAlarms = panicAlarms;
	}

	public String getFenceStatus() {
		return fenceStatus;
	}

	public void setFenceStatus(String fenceStatus) {
		this.fenceStatus = fenceStatus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the safedistances
	 */
	public Set<Safedistance> getSafedistances() {
		return safedistances;
	}

	/**
	 * @param safedistances the safedistances to set
	 */
	public void setSafedistances(Set<Safedistance> safedistances) {
		this.safedistances = safedistances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ward other = (Ward) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
