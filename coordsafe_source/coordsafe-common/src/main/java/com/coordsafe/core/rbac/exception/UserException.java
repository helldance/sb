package com.coordsafe.core.rbac.exception;

@SuppressWarnings("serial")
public class UserException extends Exception {

	private String reasonCode;

	public UserException() {
		
	}
	
	public UserException(String errMsg) {
		super(errMsg);
	}

	public UserException(String errMsg, String reasonCode) {
		super(errMsg);
		this.reasonCode = reasonCode;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
}
