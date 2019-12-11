package com.jjb.acl.facility.enums.bus;

/**
 * @Description:工作节点名称类型
 * @author JYData-R&D-BigK.K
 * @date 2016年9月1日 上午10:30:29
 * @version V1.0
 */
public enum EnumsActivitiNodeType {
	/**
	 * 复核节点信息
	 */
	A005("复核节点信息"),
	/**
	 * 申请资料校验信息
	 */
	A010("申请资料校验信息"),
	/**
	 * 重复申请验证结果信息
	 */
	A015("重复申请验证结果信息"),
	/**
	 * 申请欺诈调查结果信息
	 */
	A020("申请欺诈调查结果信息"),
	/**
	 * 初审调查节点信息
	 */
	A025("初审调查节点信息"),
	/**
	 * 初审电话调查信息
	 */
	A030("初审电话调查信息"),
	/**
	 * 初审欺诈调查信息
	 */
	A035("初审欺诈调查信息"),
	/**
	 * 补件信息
	 */
	A040("补件信息"),
	/**
	 * 终审调查信息
	 */
	A045("初审调查要求补件"),
	/**
	 * 录入人行征信信息
	 */
	A050("录入人行征信信息"),
	/**
	 * 联机人行征信信息
	 */
	A051("联机人行征信信息"),
	/**
	 * 行内存款信息
	 */
	A055("行内存款信息"),
	/**
	 * 行内贷款信息
	 */
	A060("行内贷款信息"),
	/**
	 * 评分过程信息
	 */
	A065("评分过程信息"),
	/**
	 * 申请流程公共信息
	 */
	A070("申请流程公共信息"),
	/**
	 * 申请人基本信息
	 */
	A075("申请人基本信息"),
	/**
	 * 自定义规则变量
	 */
	A080("自定义规则变量"),
	/**
	 * 自定义规则变量
	 */
	A085("预审确认信息"),
	/**
	 * 自定义规则变量
	 */
	A090("外部决策结果"),
	/**
	 * 反欺诈数据变量
	 */
	A095("反欺诈数据变量");
	private String activitiNodeType;

	private EnumsActivitiNodeType(String activitiNodeType) {
		this.activitiNodeType = activitiNodeType;
	}

	public String getActivitiNodeType() {
		return activitiNodeType;
	}
}
