/**
 * @author Yang Wei
 * @Date Nov 6, 2013
 */
package com.coordsafe.pet.entity;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.coordsafe.common.entity.Gender;
import com.coordsafe.locator.entity.Locator;

/**
 * @author Yang Wei
 *
 */
@MappedSuperclass
public abstract class Pet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nickName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private int age;
	
	@OneToOne
	private Locator locator;

	/**
	 * @param nickName
	 * @param gender
	 * @param age
	 */
	public Pet(String nickName, Gender gender, int age) {
		super();
		this.nickName = nickName;
		this.gender = gender;
		this.age = age;
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
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the locator
	 */
	public Locator getLocator() {
		return locator;
	}

	/**
	 * @param locator the locator to set
	 */
	public void setLocator(Locator locator) {
		this.locator = locator;
	}
}
