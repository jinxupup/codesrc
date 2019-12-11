package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 电话调查结果
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:58:06
 * @version V1.0
 */
public enum TelResult {

	/**
	 * 本人接听
	 */
	A("本人接听"),
	/**
	 * 无人接听
	 */
	B("无人接听"),
	/**
	 * 电话忙音
	 */
	C("电话忙音"),
	/**
	 * 停机
	 */
	D("停机"),
	/**
	 * 关机
	 */
	E("关机"),
	/**
	 * 他人接听
	 */
	F("他人接听"),
	/**
	 * 空号
	 */
	G("空号"),
	/**
	 * 传真
	 */
	H("传真"),
	/**
	 * 其他
	 */
	I("其他");

	public String telResult;

	public String lab;

	private TelResult(String lab) {

		this.telResult = name();
		this.lab = lab;
	}

}
