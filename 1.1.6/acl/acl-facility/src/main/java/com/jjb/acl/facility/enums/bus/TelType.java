package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 电话调查类型（与业务字典一样）
 * @author JYData-R&D-BigZ.Y
 * @date 2016年10月20日14:07:14
 */
public enum TelType {

	/**
	 * 本人电话
	 */
	A("本人电话"),
	/**
	 * 住宅电话
	 */
	B("住宅电话"),
	/**
	 * 单位电话
	 */
	C("单位电话"),
	/**
	 * 联系人联系电话
	 */
	D("联系人联系电话"),
	/**
	 * 联系人公司电话
	 */
	E("联系人公司电话"),
	/**
	 * 其他联系人联系电话
	 */
	F("其他联系人联系电话"),
	/**
	 * 其他联系人公司电话
	 */
	G("其他联系人公司电话"),
	/**
	 * 其他
	 */
	H("其他");

	public String telType;

	public String lab;

	private TelType(String lab) {

		this.telType = name();
		this.lab = lab;
	}

}
