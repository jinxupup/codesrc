package com.jjb.unicorn.facility.util;

public class OrgnationUtils {
	
	private ThreadLocal<String> orgInfo = new ThreadLocal<String> ();
	
	private static final OrgnationUtils instance = new OrgnationUtils ();
	
	private OrgnationUtils ()  {
		
	}
	
	public static  OrgnationUtils getInstance () {
		return instance;
	}
	
	public void setOrg (String org) {
		orgInfo.set(org);
	}
	
	public String getOrg () {
		
		return orgInfo.get();
	}

}
