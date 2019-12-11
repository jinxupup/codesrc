package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 申请卡类型
 * @author JYData-BigZ.Y
 * @date 2016年9月21日10:40:01
 */
public enum CardType {

	/**
	 * 主卡
	 */
	MTAB("主卡"),
	/**
	 * 附卡
	 */
	STAB("附卡");

	public String state;
	public String lab;

	private CardType(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
