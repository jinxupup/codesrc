package com.jjb.acl.facility.enums.bus.loan;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 分期手续费收取方式
 * @author Big.R
 *
 */
@EnumInfo({
	"F|一次性收取",
	"E|分期收取",
	"C|自行指定"
})
public enum LoanFeeMethod {

	/**
	 *	一次性收取 
	 */
	F,

	/**
	 *	分期收取 
	 */
	E,
	/**
	 * 自行指定
	 */
	C
}
