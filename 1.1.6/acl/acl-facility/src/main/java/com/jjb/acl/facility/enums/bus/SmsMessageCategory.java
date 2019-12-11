package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

@EnumInfo({ "ECMS01|审批补件类型", "ECMS02|拒绝申请短信", "ECMS03|渠道进件受理通知短信"})
public enum SmsMessageCategory {

	/**
	 * 审批补件类型
	 */
	ECMS01("applyPatchBoltType|补件类型"),

	/**
	 * 拒绝申请短信
	 */
	ECMS02("productCd|卡产品代码", "description|卡产品描述"),
	/**
	 * 渠道进件受理通知短信
	 */
	ECMS03("productCd|卡产品代码","description|卡产品描述");

	private String variables[];

	/**
	 * @param variables
	 *            以"|"分隔的一组字符串，写明该类型支持/需要提供的变量列表
	 */
	private SmsMessageCategory(String... variables) {
		this.variables = variables;
	}

	public String[] getVariables() {
		return variables;
	}
}
