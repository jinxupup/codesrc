package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 
 * @Description: 申请资料验证-警告码
 * @author JYData-R&D-Big Star
 * @date 2016年9月5日 下午7:03:32
 * @version V1.0
 */
@EnumInfo({ "INFO_CHECK_W001|发卡网点无发卡权限", "INFO_CHECK_W002|推广人手机号无效",
		"INFO_CHECK_W003|主卡申请人身份证件号码无效", "INFO_CHECK_W004|附卡申请人身份证件号码无效",
		"INFO_CHECK_W005|主卡申请人性别与身份证不同", "INFO_CHECK_W006|附卡申请人性别与身份证不同",
		"INFO_CHECK_W007|主卡申请人生日与身份证不同", "INFO_CHECK_W008|附卡申请人生日与身份证不同",
		"INFO_CHECK_W009|主卡申请人移动电话无效", "INFO_CHECK_W010|附卡申请人移动电话无效",
		"INFO_CHECK_W011|主卡申请人家庭电话无效", "INFO_CHECK_W012|附卡申请人家庭电话无效",
		"INFO_CHECK_W013|主卡申请人公司电话无效", "INFO_CHECK_W014|主卡申请人联系人手机号无效",
		"INFO_CHECK_W015|附卡申请人主卡卡号无效", "INFO_CHECK_W016|主卡申请人电子邮箱无效",
		"INFO_CHECK_W017|附卡申请公司庭电话无效", "INFO_CHECK_W018|约定还款账户无效",
		"INFO_CHECK_W019|主申请人自选卡号无效", "INFO_CHECK_W020|附卡申请人自选卡号无效",
		"INFO_CHECK_W021|附卡申请人电子邮箱无效", "INFO_CHECK_W022|第一联系人移动电话无效",
		"INFO_CHECK_W023|其他联系人移动电话无效", })
public enum ApplyInfoValidityWarnCode {

	/**
	 * 发卡网点无发卡权限
	 */
	INFO_CHECK_W001("发卡网点无发卡权限"),
	/**
	 * 推广人手机号无效
	 */
	INFO_CHECK_W002("推广人手机号无效"),
	/**
	 * 主卡申请人身份证件号码无效
	 */
	INFO_CHECK_W003("主卡申请人身份证件号码无效"),
	/**
	 * 附卡申请人身份证件号码无效
	 */
	INFO_CHECK_W004("附卡申请人身份证件号码无效"),
	/**
	 * 主卡申请人性别与身份证不同
	 */
	INFO_CHECK_W005("主卡申请人性别与身份证不同"),
	/**
	 * 附卡申请人性别与身份证不同
	 */
	INFO_CHECK_W006("附卡申请人性别与身份证不同"),
	/**
	 * 主卡申请人生日与身份证不同
	 */
	INFO_CHECK_W007("主卡申请人生日与身份证不同"),
	/**
	 * 附卡申请人生日与身份证不同
	 */
	INFO_CHECK_W008("附卡申请人生日与身份证不同"),
	/**
	 * 主卡申请人移动电话无效
	 */
	INFO_CHECK_W009("主卡申请人移动电话无效"),
	/**
	 * 附卡申请人移动电话无效
	 */
	INFO_CHECK_W010("附卡申请人移动电话无效"),
	/**
	 * 主卡申请人家庭电话无效
	 */
	INFO_CHECK_W011("主卡申请人家庭电话无效"),
	/**
	 * 附卡申请人家庭电话无效
	 */
	INFO_CHECK_W012("附卡申请人家庭电话无效"),
	/**
	 * 主卡申请人公司电话无效
	 */
	INFO_CHECK_W013("主卡申请人公司电话无效"),
	/**
	 * 主卡申请人联系人手机号无效
	 */
	INFO_CHECK_W014("主卡申请人联系人手机号无效"),
	/**
	 * 附卡申请人主卡卡号无效
	 */
	INFO_CHECK_W015("附卡申请人主卡卡号无效"),
	/**
	 * 主卡申请人电子邮箱无效
	 */
	INFO_CHECK_W016("主卡申请人电子邮箱无效"),
	/**
	 * 附卡申请公司庭电话无效
	 */
	INFO_CHECK_W017("附卡申请公司庭电话无效"),
	/**
	 * 约定还款账户无效
	 */
	INFO_CHECK_W018("约定还款账户无效"),

	INFO_CHECK_W019("主申请人自选卡号无效"), INFO_CHECK_W020("附卡申请人自选卡号无效"), INFO_CHECK_W021(
			"附卡申请人电子邮箱无效"), INFO_CHECK_W022("第一联系人移动电话无效"), INFO_CHECK_W023(
			"其他联系人移动电话无效");

	public String code;
	public String lab;

	private ApplyInfoValidityWarnCode(String lab) {
		this.code = name();
		this.lab = lab;
	}

	public String getLab() {
		return lab;
	}
}
