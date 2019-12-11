package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

@EnumInfo({
	"H|家庭地址",
	"C|公司地址",
	"S|户籍地址",
	"R|单位注册地址",
	"O|单位运营地址",
	"M|其他地址1",
	"N|其他地址2"
})
public enum AddressType {

	/**
	 * 家庭地址
	 */
	H,
	/**
	 * 公司地址
	 */
	C,
	/**
	 * 户籍地址
	 */
	S,
	/**
	 * 单位注册地址
	 */
	R,
	/**
	 * 单位运营地址
	 */
	O,
	/**
	 * 其他地址1
	 */
	M,
	/**
	 * 其他地址2
	 */
	N
}
