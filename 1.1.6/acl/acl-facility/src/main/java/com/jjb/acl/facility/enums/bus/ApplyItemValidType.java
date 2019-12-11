package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 电话调查信息有效性确认
 * @author JYData-R&D-Big Star
 * @date 2014年12月17日 下午2:39:34
 * @version V1.0
 */
public enum ApplyItemValidType {

	/**
	 * 有效
	 */
	Y("有效"),
	/**
	 * 无效
	 */
	N("无效"),
	/**
	 * 可疑
	 */
	Q("可疑"),
	/**
	 * 伪造
	 */
	F("伪造"),
	/**
	 * 非调查附件
	 */
	O("非调查附件"),
	/**
	 * 待补件确认
	 */
	P("待补件确认");

	private String applyItemValidType;

	private ApplyItemValidType(String applyItemValidType) {
		this.applyItemValidType = applyItemValidType;
	}

	public String getApplyItemValidType() {
		return applyItemValidType;
	}
}
