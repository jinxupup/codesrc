package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 申请件类型
 * @author JYData-R&D-Big Star
 * @date 2016年9月8日 下午3:45:58
 * @version V1.0
 */
public enum AppType {

	/**
	 * 主附同申
	 */
	A("主附同申"),
	/**
	 * 独立主卡
	 */
	B("独立主卡"),
	/**
	 * 独立附卡
	 */
	S("独立附卡");

	public String state;
	public String lab;

	private AppType(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
