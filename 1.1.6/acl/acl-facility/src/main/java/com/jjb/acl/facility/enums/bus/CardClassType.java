package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 卡等级
 * @author JYData-BigZ.Y
 * @date 2016年11月18日09:45:34
 */
public enum CardClassType {

	/**
	 * DIAM|钻石卡
	 */
	D("DIAM|钻石卡"),
	/**
	 * PLTM|白金卡
	 */
	P("PLTM|金卡"),
	/**
	 * GOLD|金卡
	 */
	G("GOLD|金卡"),
	/**
	 * SLVR|普卡
	 */
	N("SLVR|普卡");

	public String state;
	public String lab;

	private CardClassType(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
