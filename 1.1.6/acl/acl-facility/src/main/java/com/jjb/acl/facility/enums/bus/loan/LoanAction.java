package com.jjb.acl.facility.enums.bus.loan;

import com.jjb.unicorn.facility.meta.EnumInfo;

@EnumInfo({ 
	"A|新建分期", 
	"R|展期", 
	"P|提前还款",
	"S|缩期"
})
public enum LoanAction {

	/**
	 * 新建分期
	 */
	A, 
	/**
	 * 展期
	 */
	R, 
	/**
	 * 提前还款
	 */
	P,
	/**
	 * 缩期
	 */
	S

}
