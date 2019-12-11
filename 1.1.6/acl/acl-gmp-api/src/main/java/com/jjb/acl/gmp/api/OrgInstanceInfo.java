package com.jjb.acl.gmp.api;

import java.io.Serializable;

public class OrgInstanceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4834330273670569141L;
	
	private int instId;

	/**
	 * 实例名
	 */
	private String instName;
	
	/**
	 * 系统类型 ,cas根据系统类型名显示图片
	 */
	private String systemType;
	
	private String instMemo;
	
	/**
	 * 实例访问路径
	 */
	private String instAccessAddress;

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getInstAccessAddress() {
		return instAccessAddress;
	}

	public void setInstAccessAddress(String instAccessAddress) {
		this.instAccessAddress = instAccessAddress;
	}

	public int getInstId() {
		return instId;
	}

	public void setInstId(int instId) {
		this.instId = instId;
	}

	public String getInstMemo() {
		return instMemo;
	}

	public void setInstMemo(String instMemo) {
		this.instMemo = instMemo;
	}

	public OrgInstanceInfo(int instId, String instName, String systemType,
			String instMemo, String instAccessAddress) 
	{
		this.instId = instId;
		this.instName = instName;
		this.systemType = systemType;
		this.instMemo = instMemo;
		this.instAccessAddress = instAccessAddress;
	}
	
	public OrgInstanceInfo(){
		
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
}
