/**
 * @author Yang Wei
 * @Date Jul 11, 2013
 */
package com.coordsafe.api.entity;

/**
 * @author Yang Wei
 *
 */
public final class ApiMessage {
	public static final String NO_KEY_APPEND = "0. no request key specified";
	public static final String TEST_KEY_RCVED = "3. test key received";
	public static final String MASTER_KEY_RCVED = "5. master key received";
	public static final String KEY_VERIFIED = "2. request key verified";
	public static final String KEY_UNMATCH = "4. request key found but does not match domain";
	public static final String KEY_INVALID = "6. stored key found but is not valid"; // expired
	public static final String KEY_NOT_EXIST = "1. request key does not exist";
}
