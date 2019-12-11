package com.jjb.unicorn.facility.exception;

/**
 * 业务异常
 * @author jjb
 *
 */
public class BizException extends UnicornException {

	private static final long serialVersionUID = 1L;

	public BizException () {
		super ();
	}
	
	public BizException (String message) {
		super (message);
	}
	
	public BizException (String message, Throwable cause) {
		
		super(message,cause);
	}
	
	public BizException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
}
