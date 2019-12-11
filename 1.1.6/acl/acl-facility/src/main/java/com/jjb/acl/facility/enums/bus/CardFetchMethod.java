package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

/*
 * 卡片领取方式
 */
@EnumInfo({ 
	"A|邮件寄送",
	"B|分支行领卡",
	"E|快递寄送"
})
public enum CardFetchMethod {
	/**
	 *	邮件寄送 
	 */
	A,
	/**
	 *	分支行领卡 
	 */
	B,
	/**
	 * 快递寄送
	 */
	E
}
