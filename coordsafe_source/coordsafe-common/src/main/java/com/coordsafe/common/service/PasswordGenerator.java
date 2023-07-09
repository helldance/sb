package com.coordsafe.common.service;

import java.util.Random;

/**
 * @author Yang Wei
 * @Date Dec 27, 2013
 */
public class PasswordGenerator {
	static final String lLetter = "abcdefghijklmnopqrstuvwxyz";
	static final String uletter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String digits = "0123456789";
	static final String alphanumeric = "0123456789-abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String getSimpleLowercasePassword (int length){
		final StringBuilder builder = new StringBuilder();
		Random rdm = new Random();
		
		for (int i = 0; i < length; i ++){
			builder.append(lLetter.charAt(rdm.nextInt(26)));
		}
		
		return builder.toString();
	}
	
	public static String getMixAlphaNumericPassword (int length){
		final StringBuilder builder = new StringBuilder();
		Random rdm = new Random();
		
		for (int i = 0; i < length; i ++){
			builder.append(alphanumeric.charAt(rdm.nextInt(alphanumeric.length())));
		}
		
		return builder.toString();
	}
}
