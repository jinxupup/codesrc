package com.jjb.unicorn.facility.exception;

/**
 *@ClassName ActivitiException
 *@Description 调用工作流异常
 *@Author lixing
 *Date 2018/11/12 11:25
 *Version 1.0
 */
public class ActivitiException extends Exception {

	private static final long serialVersionUID = 1L;

	// 错误code
	public String errorCode;

	public ActivitiException() {

	}

	public ActivitiException(String message) {
		super(message);
	}

	public ActivitiException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ActivitiException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	
}
