package com.jjb.acl.gmp.api;

public class ManagementException extends Exception {
	private static final long serialVersionUID = -448003635884798802L;

	public ManagementException(String message, Throwable t)
	{
		super(message, t);
	}
	
	public ManagementException(String message)
	{
		super(message);
	}
	
}
