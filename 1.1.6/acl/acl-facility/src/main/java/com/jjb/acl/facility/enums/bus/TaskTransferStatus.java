package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 任务转移状态
 * @author JYData-R&D-BigZ.Y
 * @date 2016年10月27日17:53:04
 */
public enum TaskTransferStatus {

	/**
	 * 完成
	 */
	COMPLETED("完成"),
	/**
	 * 未分配
	 */
	UNASSIGNED("未分配"),
	/**
	 * 已分配
	 */
	ASSIGNED("已分配");

	public String state;
	public String lab;

	private TaskTransferStatus(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
