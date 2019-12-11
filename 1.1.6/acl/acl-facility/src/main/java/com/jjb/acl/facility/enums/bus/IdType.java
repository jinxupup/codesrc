/**
 * 
 */
package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * @Description: 证件类型枚举类
 * @author JYData-R&D-BigK.K
 * @date 2016年9月9日 下午4:37:38
 * @version V1.0
 */
@EnumInfo({ "I|身份证", "A|军官证", "S|士兵证", "P|护照", "O|其他有效证件", "R|户口簿",
		"H|港澳居民来往内地通行证", "W|台湾同胞来往内地通行证", "C|警官证", "L|香港身份证", "G|澳门身份证",
		"J|台湾身份证"

})
public enum IdType {
	/**
	 * 身份证
	 */
	I("身份证 ", "01"),

	/**
	 * 军官证
	 */
	A("军官证", "07"),
	/**
	 * 士兵证
	 */
	S("士兵证", "02"),
	/**
	 * 护照
	 */
	P("护照", "03"),

	/**
	 * 其他有效证件
	 */
	O("其他有效证件", "99"),
	/**
	 * 户口簿
	 */
	R("户口簿", "x3"),
	/**
	 * 港澳居民来往内地通行证
	 */
	H("港澳居民来往内地通行证", "04"),
	/**
	 * 台湾同胞来往内地通行证
	 */
	W("台湾同胞来往内地通行证", "05"),

	/**
	 * 警官证
	 */
	C("警官证", "06"),

	/**
	 * -香港身份证
	 */
	L("香港身份证", "x2"),

	/**
	 * 澳门身份证
	 */
	G("澳门身份证", "08"),

	/**
	 * 台湾身份证
	 */
	J("台湾身份证", "09");

	public String state;
	public String lab;

	private IdType(String state, String lab) {
		this.state = name();
		this.lab = lab;
	}

}
