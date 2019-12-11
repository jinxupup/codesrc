package com.jjb.unicorn.batch.exception;

import com.jjb.unicorn.facility.exception.UnicornException;

public class BatchException extends UnicornException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7485474077965212120L;

	public BatchException (String code,String message) {
		super(code,message);
	}
	
	public BatchException (String message) {
		super(message);
	}

}
