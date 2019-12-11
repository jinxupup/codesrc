package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 卡类型子类型
 * @author JYData-BigZ.Y
 * @date 2016-11-18 09:59:07
 */
public enum SubCardType {

	/**
	 * 公务卡
	 */
	O("公务卡"),
	/**
	 * 普通卡
	 */
	N("普通卡");

	public String state;
	public String lab;

	private SubCardType(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
