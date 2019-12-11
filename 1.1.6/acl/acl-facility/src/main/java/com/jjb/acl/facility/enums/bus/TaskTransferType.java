package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 任务转移方式
 * @author JYData-R&D-BigZ.Y
 * @date 2016年10月27日18:00:34
 */
public enum TaskTransferType {

	/**
	 * 自动分配
	 */
	AUTO_ASSIGNE("自动分配"),
	/**
	 * 定时分配
	 */
	QUARTZ_ASSIGNE("定时分配"),
	/**
	 * 本人获取
	 */
	SELF_CLAIM("本人获取"),
	/**
	 * 转分配
	 */
	TRANSFER("转分配"),
	/**
	 * 本人取消
	 */
	SELF_CANCEL("本人取消");
	

	public String state;
	public String lab;

	private TaskTransferType(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
