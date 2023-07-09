/**
 * 
 */
package com.coordsafe.exception.kinds;

import java.util.List;


/**
 * @author Yang Wei
 *
 */
public class CoordSafeResponse extends Exception{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long statusCode;
	public long csCode;
	public String userMsg;
	public String machineMsg;
	private List<ErrorMessage> errorMessageList;
	/**
	 * 
	 */
	public CoordSafeResponse() {
		super();
	}

	/**
	 * @param statusCode
	 * @param csCode
	 * @param userMsg
	 * @param machineMsg
	 */
	public CoordSafeResponse(long statusCode, long csCode, String userMsg,
			String machineMsg) {
		super();
		this.statusCode = statusCode;
		this.csCode = csCode;
		this.userMsg = userMsg;
		this.machineMsg = machineMsg;
	}

	/**
	 * @param csCode
	 * @param userMsg
	 */
	public CoordSafeResponse(long csCode, String userMsg) {
		super();
		this.csCode = csCode;
		this.userMsg = userMsg;
	}

	public List<ErrorMessage> getErrorMessageList() {
		return errorMessageList;
	}

	public void setErrorMessageList(List<ErrorMessage> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}	
}
