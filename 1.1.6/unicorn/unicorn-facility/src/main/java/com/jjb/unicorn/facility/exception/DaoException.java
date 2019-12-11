package com.jjb.unicorn.facility.exception;

/**
 * 数据库操作异常
 * @author jjb
 *
 */
public class DaoException extends UnicornException {

	private static final long serialVersionUID = 1L;
	
	public DaoException () {
		
	}
	
	public DaoException (String message) {
		
		super(message);
	}

}
