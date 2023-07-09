package com.coordsafe.core.rbac.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.coordsafe.core.rbac.entity.UserInformation;

@SuppressWarnings("serial")
@XmlRootElement
@Entity
public class UserInformation implements Serializable {

	private Long id;
	@Size(min = 2, max = 40, message = "First name must be between 2 and 40 characters long.")
	private String firstName;
	@Size(min = 1, max = 40, message = "Middle name must be between 1 and 40 characters long.")
	private String middleName;
	@Size(min = 2, max = 40, message = "Last name must be between 2 and 40 characters long.")
	private String lastName;
	@Size(min = 2, max = 30, message = "Nick name must be between 2 and 30 characters long.")
	private String nickName;
	@Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Invalid email address.")
	private String email;
	@Size(min = 7, max = 15, message = "Home contact number must be between 7 and 15 digits long.")
	@Pattern(regexp = "^[0-9]+$", message = "Home contact number must only contain digits.")
	private String homeContactNumber;
	@Size(min = 7, max = 15, message = "Mobile contact number must be between 7 and 15 digits long.")
	@Pattern(regexp = "^[0-9]+$", message = "Mobile contact number must only contain digits.")
	private String mobileContactNumber;
	@Size(min = 10, message = "Home address must be at least 10 chracters long.")
	private String homeAddress;
	@Past(message = "You can't be born tomorrow and typing now!")
	private Date dateOfBirth;
	@Size(min = 2, max = 50, message = "Next of kin name must be between 2 and 50 characters long.")
	private String nextOfKinName;
	@Size(min = 7, max = 15, message = "Next of kin home contact number must be between 7 and 15 digits long.")
	@Pattern(regexp = "^[0-9]+$", message = "Next of kin home contact number must only contain digits.")
	private String nextOfKinHomeContactNumber;
	@Size(min = 7, max = 15, message = "Next of kin mobile contact number must be between 7 and 15 digits long.")
	@Pattern(regexp = "^[0-9]+$", message = "Next of kin mobile contact number must only contain digits.")
	private String nextOfKinMobileContactNumber;
	@Size(min = 10, message = "Next of kin home address must be at least 10 chracters long.")
	private String nextOfKinHomeAddress;

	public UserInformation() {
		super();
	}

	public UserInformation(Long id, String firstName, String middleName,
			String lastName, String nickName, String email,
			String homeContactNumber, String mobileContactNumber,
			String homeAddress, Date dateOfBirth, String nextOfKinName,
			String nextOfKinHomeContactNumber,
			String nextOfKinMobileContactNumber, String nextOfKinHomeAddress) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.email = email;
		this.homeContactNumber = homeContactNumber;
		this.mobileContactNumber = mobileContactNumber;
		this.homeAddress = homeAddress;
		this.dateOfBirth = dateOfBirth;
		this.nextOfKinName = nextOfKinName;
		this.nextOfKinHomeContactNumber = nextOfKinHomeContactNumber;
		this.nextOfKinMobileContactNumber = nextOfKinMobileContactNumber;
		this.nextOfKinHomeAddress = nextOfKinHomeAddress;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomeContactNumber() {
		return homeContactNumber;
	}

	public void setHomeContactNumber(String homeContactNumber) {
		this.homeContactNumber = homeContactNumber;
	}

	public String getMobileContactNumber() {
		return mobileContactNumber;
	}

	public void setMobileContactNumber(String mobileContactNumber) {
		this.mobileContactNumber = mobileContactNumber;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNextOfKinName() {
		return nextOfKinName;
	}

	public void setNextOfKinName(String nextOfKinName) {
		this.nextOfKinName = nextOfKinName;
	}

	public String getNextOfKinHomeContactNumber() {
		return nextOfKinHomeContactNumber;
	}

	public void setNextOfKinHomeContactNumber(String nextOfKinHomeContactNumber) {
		this.nextOfKinHomeContactNumber = nextOfKinHomeContactNumber;
	}

	public String getNextOfKinMobileContactNumber() {
		return nextOfKinMobileContactNumber;
	}

	public void setNextOfKinMobileContactNumber(
			String nextOfKinMobileContactNumber) {
		this.nextOfKinMobileContactNumber = nextOfKinMobileContactNumber;
	}

	public String getNextOfKinHomeAddress() {
		return nextOfKinHomeAddress;
	}

	public void setNextOfKinHomeAddress(String nextOfKinHomeAddress) {
		this.nextOfKinHomeAddress = nextOfKinHomeAddress;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hash = 1;
		
		hash = hash * PRIME + ((id == null) ? 0 : id.hashCode());
		hash = hash * PRIME + ((firstName == null) ? 0 : firstName.hashCode());
		hash = hash * PRIME + ((middleName == null) ? 0 : middleName.hashCode());
		hash = hash * PRIME + ((lastName == null) ? 0 : lastName.hashCode());
		hash = hash * PRIME + ((nickName == null) ? 0 : nickName.hashCode());
		hash = hash * PRIME + ((email == null) ? 0 : email.hashCode());
		hash = hash * PRIME + ((homeContactNumber == null) ? 0 : homeContactNumber.hashCode());
		hash = hash * PRIME + ((mobileContactNumber == null) ? 0 : mobileContactNumber.hashCode());
		hash = hash * PRIME + ((homeAddress == null) ? 0 : homeAddress.hashCode());
		hash = hash * PRIME + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof UserInformation))
			return false;
		UserInformation other = (UserInformation) obj;
		if (other.getId() == null)
			return false;
		else if (other.getId() != this.id)
			return false;
		else
			return true;
		
	}
}
