package com.coordsafe.core.codetable.exception;

@SuppressWarnings("serial")
public class CodeTableException extends Exception {

	public CodeTableException() {
		super();
	}
	
	public CodeTableException(String errMsg) {
		super(errMsg);
	}
}
