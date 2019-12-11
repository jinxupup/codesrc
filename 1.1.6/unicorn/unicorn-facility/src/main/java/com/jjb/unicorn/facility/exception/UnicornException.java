package com.jjb.unicorn.facility.exception;

public class UnicornException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public String errorCode;

	public UnicornException() {

	}
	
	public UnicornException(Throwable cause) {
		super(cause);
	}

	public UnicornException(String message) {
		super(message);
	}
	
	public UnicornException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public UnicornException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	
}
