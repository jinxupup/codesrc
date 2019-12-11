package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

/*
 * 审批结果
 * 在终审时B：退回到初审 C：退回到电调
 */
@EnumInfo({ "P|通过", "R|拒绝", "O|补件", "B|退回","M|风险决策"})
public enum ApplyResultType {

	/**
	 * 通过
	 */
	P("通过"),
	/**
	 * 拒绝
	 */
	R("拒绝"),
	/**
	 * 补件
	 */
	O("补件"),
	/**
	 * 退回
	 */
	B("退回"),
	/**
	 * 风险决策
	 */
	M("风险决策");

	private String applyResultType;

	private ApplyResultType(String applyResultType) {
		this.applyResultType = applyResultType;
	}

	public String getApplyResultType() {
		return applyResultType;
	}
}
