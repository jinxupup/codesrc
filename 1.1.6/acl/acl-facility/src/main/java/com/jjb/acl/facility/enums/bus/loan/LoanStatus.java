package com.jjb.acl.facility.enums.bus.loan;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 分期状态
 * @author Big.R
 *
 */
@EnumInfo({
	"I|注册但未活动",
	"A|活动状态(active)",
	"R|分期展期",
	"T|终止(terminate)",
	"F|完成(finish)",
	"S|缩期(systole)"
})
public enum LoanStatus {

	/**
	 *	注册但未活动 
	 */
	I,
	/**
	 *	活动状态(active) 
	 */
	A,
	/**
	 * 分期展期
	 */
	R,
	/**
	 *	终止(terminate) 
	 */
	T,
	/**
	 *	完成(finish) 
	 */
	F,
	/**
	 * 缩期(systole)
	 */
	S
}
