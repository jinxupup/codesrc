package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

@EnumInfo({
	"C|贷记",
	"D|借记",
	"L|贷款"
})
public enum AcctTypeGroup {
	/**
	 * 贷记账户类型组
	 */
	C,
	/**
	 * 借记账户类型组
	 */
	D,
	/**
	 * 贷款账户类型组
	 */
	L
}
