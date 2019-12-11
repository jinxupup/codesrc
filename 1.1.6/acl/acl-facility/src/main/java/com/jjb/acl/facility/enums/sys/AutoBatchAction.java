package com.jjb.acl.facility.enums.sys;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 关闭账户原因
 * @author H.N
 *
 */
@EnumInfo({
	"DS|日切 ",
	"F|启动批前",
	"M|启动批中",
	"B|启动批后",
	"MB|启动批中批后"
})
public enum AutoBatchAction
{
	/**
	 * 日切 
	 */
	DS("日切 "),
	/**
	 * 批前
	 */
	F("启动批前"),
	/**
	 * 批前
	 */
	M("启动批中"),
	/**
	 * 批前
	 */
	B("启动批后"),
	/**
	 * 批中批后
	 */
	MB("启动批中批后")
	;
	private String actionDesc;
	private AutoBatchAction(String actionDesc)
	{
		this.actionDesc = actionDesc;
	}
	public String getActionDesc()
	{
		return this.actionDesc;
	}
}
