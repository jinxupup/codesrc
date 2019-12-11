package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

/*
 * 账单介质类型
 */
@EnumInfo({
	"B|纸质+电子账单",
	"E|电子账单",
	"P|纸质账单"
})
public enum StmtMediaType {

	/**
	 * 纸质+电子账单
	 */
	B,
	/**
	 * 电子账单
	 */
	E,
	/**
	 * 纸质账单
	 */
	P
}