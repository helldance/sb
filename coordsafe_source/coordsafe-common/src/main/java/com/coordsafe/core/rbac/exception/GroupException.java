package com.coordsafe.core.rbac.exception;

@SuppressWarnings("serial")
public class GroupException extends Exception {

	public GroupException() {
		
	}
	
	public GroupException(String errMsg) {
		super(errMsg);
	}
}
