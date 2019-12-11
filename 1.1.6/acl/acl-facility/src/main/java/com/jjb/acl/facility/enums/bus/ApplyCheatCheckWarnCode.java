package com.jjb.acl.facility.enums.bus;

/**
 * @Description: 警告信息
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午1:46:54
 * @version V1.0
 */
public enum ApplyCheatCheckWarnCode {

	/**
	 * 申请人手机号对应多个申请件
	 */
	CHECK_CHECK_W001("申请人手机号对应多个申请件"),
	/**
	 * 同一证件类型和证件号对应多个姓名
	 */
	CHECK_CHECK_W002("同一证件类型和证件号对应多个姓名"),
	/**
	 * 主卡申请人身份证件号码无效
	 */
	CHECK_CHECK_W003("同一住宅电话对应多个申请件"),
	/**
	 * 半年申请次数过多
	 */
	CHECK_CHECK_W004("半年申请次数过多"),
	/**
	 * 主卡申请人性别与身份证不同
	 */
	CHECK_CHECK_W005("半年内失败申请次数过多"),
	/**
	 * 怀疑个人黑名单
	 */
	CHECK_CHECK_W006("怀疑个人身份黑名单"),
	/**
	 * 怀疑个人移动电话黑名单
	 */
	CHECK_CHECK_W007("怀疑个人移动电话黑名单"),
	/**
	 * 怀疑个人家庭电话黑名单
	 */
	CHECK_CHECK_W008("怀疑个人家庭电话黑名单"),
	/**
	 * 怀疑个人所在公司电话黑名单
	 */
	CHECK_CHECK_W009("怀疑个人所在公司电话黑名单"),
	/**
	 * 怀疑个人家庭地址黑名单
	 */
	CHECK_CHECK_W010("怀疑个人家庭地址黑名单"),
	/**
	 * 怀疑个人所在公司黑名单
	 */
	CHECK_CHECK_W011("怀疑个人所在公司黑名单"),

	/**
	 * 怀疑个人所在公司黑名单
	 */
	CHECK_CHECK_W012("重复申请-已在审批系统中申请"),

	/**
	 * 怀疑个人所在公司地址黑名单
	 */
	CHECK_CHECK_W013("怀疑个人所在公司地址黑名单"),

	/**
	 * 申请人的联系人（包括直系和其他联系人）对应多个申请人的联系人
	 */
	CHECK_CHECK_W014("申请人的联系人（包括直系和其他联系人）对应多个申请人的联系人"), 
	
	/**
	 * 申请人的家庭地址对应多个申请人的家庭地址
	 */
	CHECK_CHECK_W015("申请人的家庭地址对应多个申请人的家庭地址"), 
	
	/**
	 * 申请人的单位地址对应多个申请人的单位地址
	 */
	CHECK_CHECK_W016("申请人的单位地址对应多个申请人的单位地址"), 
	
	/**
	 * 同一单位电话对应多个单位名称
	 */
	CHECK_CHECK_W017("同一单位电话对应多个单位名称"),

	/**
	 * 同一单位电话对应多个申请件
	 */
	CHECK_CHECK_W018("同一单位电话对应多个申请件"),
	/**
	 * 申请人邮箱多人匹配
	 */
	CHECK_CHECK_W019("申请人邮箱多人匹配"),
	/**
	 * 申请人单位地址对住宅地址单人匹配
	 */
	CHECK_CHECK_W020("申请人单位地址对住宅地址单人匹配 "),
	/**
	 * 申请人宅电对单电多人匹配
	 */
	CHECK_CHECK_W021("申请人宅电对单电多人匹配  "),
	/**
	 * 申请人联系人姓名多人匹配（电话相同姓名不同）
	 */
	CHECK_CHECK_W022("申请人联系人姓名多人匹配（电话相同姓名不同）  "),
	/**
	 * 申请人邮寄住宅地址（不为空）多人匹配
	 */
	CHECK_CHECK_W023("申请人邮寄住宅地址（不为空）多人匹配  "),
	/**
	 * 怀疑个人电子邮箱黑名单
	 */
	CHECK_CHECK_W024("怀疑个人电子邮箱黑名单 "),
	/**
	 * 申请人单位电话和联系人多人匹配
	 */
	CHECK_CHECK_W025("申请人单位电话和联系人多人匹配"),
	/**
	 * 申请人住宅电话和联系人多人匹配
	 */
	CHECK_CHECK_W026("申请人住宅电话和联系人多人匹配"),
	/**
	 *手机号连续7位与其他人的重复
	 */
	CHECK_CHECK_W027("手机号连续7位与其他人的重复");
	

	public String warnCode;
	public String warnDesc;

	ApplyCheatCheckWarnCode(String warnDesc) {
		this.warnCode = name();
		this.warnDesc = warnDesc;
	}

	public String getWarnDesc() {
		return warnDesc;
	}

}
