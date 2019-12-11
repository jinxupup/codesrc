package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 约定还款类型
 * @author Big.R
 *
 */
@EnumInfo({
	"M|最小额扣款",
	"F|全额扣款",
	"C|最小额储蓄购汇",
	"E|全额储蓄购汇",
	"N|未设置"
})
public enum DdIndicator {

	/**
	 *	最小额扣款 
	 */
	M,

	/**
	 *	全额扣款 
	 */
	F,

	/**
	 *	最小额储蓄购汇 
	 */
	C,

	/**
	 *	全额储蓄购汇 
	 */
	E,
	/**
	 * 未设置
	 */
	N

}
