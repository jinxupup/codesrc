package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

@EnumInfo({
	"R00|成功",
	"R01|姓名不存在",
	"R02|证件号码不存在",
	"R03|家庭地址与公司地址均不存在",
	"R04|电话不存在",
	"R05|发卡网点不存在",
	"R06|无发卡权限",
	"R07|主附卡标识错误",
	"R08|机构代码异常",
	"R09|同机构同证件类型同证件号码,姓名不相同",
	"R10|卡产品不存在",
	"R11|外币账户属性不存在",
	"R12|对应主卡不存在",
	"R13|主附卡卡产品不匹配",
	"R14|币种不存在",
	"R15|介质卡号不存在",
	"R16|申请编号为空",
	"R17|消费凭密选择为空",
	"R18|性别为空",
	"R19|生日为空",
	"R20|婚姻状况为空",
	"R21|家庭人口为空",
	"R22|证件类型为空",
	"R23|教育程度为空",
	"R24|住宅状况为空",
	"R25|自动还款方式为空",
	"R26|约定还款扣款账号为空",
	"R27|联系人姓名为空",
	"R28|联系人与申请人关系为空",
	"R29|联系人身份证件类型为空",
	"R30|联系人证件号码为空",
	"R31|联系人性别为空",
	"R32|联系人生日为空",
	"R33|建议信用额度为空",
	"R34|周期限额为空",
	"R35|周期取现限额为空",
	"R36|网银周期交易限额为空",
	"R37|单笔交易限额为空",
	"R38|单笔取现交易限额为空",
	"R39|网银单笔交易为空",
	"R40|发卡网点为空",
	"R41|与主卡持卡人关系为空",
	"R42|工作稳定性为空",
	"R43|就业状态为空",
	"R44|住宅类型为空",
	"R45|个人资产类型为空",
	"R46|是否永久居住为空",
	"R47|社保缴存金额为空",
	"R48|风险情况为空",
	"R49|账单周期为空",
	"R50|家庭国家代码为空",
	"R51|家庭城市为空",
	"R52|现住址电话号码为空",
	"R53|公司国家代码为空",
	"R54|公司城市为空",
	"R55|单位电话号码为空",
	"R56|账单寄送标识对应的地址不存在",
	"R57|账户信息和客户信息不符",
	"R58|卡产品参数设置有误,开启随用金功能的卡产品请关闭【指定POS分期开卡】功能,且账户类型必须为A独立账户.请联系行方(授权)修改参数"
})
/**
 * 建账制卡申请单拒绝原因
 * 
 * @author H.N
 *
 */
public enum AppRejectReason {
	/**
	 * 成功
	 */
	R00("成功"),
	/**
	 * 姓名为空
	 */
	R01("姓名为空"),
	/**
	 * 证件号码为空
	 */
	R02("证件号码为空"),
	/**
	 * 家庭地址与公司地址均为空
	 */
	R03("家庭地址与公司地址均为空"),
	/**
	 * 电话为空
	 */
	R04("电话为空"),
	/**
	 * 发卡网点不存在
	 */
	R05("发卡网点不存在"),
	/**
	 * 无发卡权限
	 */
	R06("无发卡权限"),
	/**
	 * 主附卡标识为空
	 */
	R07("主附卡标识为空"),
	/**
	 * 机构代码为空
	 */
	R08("机构代码为空"),
	/**
	 * 无效申请
	 */
	R09("同机构同证件类型同证件号码,姓名不相同"),
	/**
	 * 卡产品不存在
	 */
	R10("卡产品不存在"),
	/**
	 * 外币账户属性不存在
	 */
	R11("外币账户属性不存在"),
	/**
	 * 对应主卡不存在
	 */
	R12("对应主卡不存在"),
	/**
	 * 主附卡卡产品不匹配
	 */
	R13("主附卡卡产品不匹配"),
	/**
	 * 币种不存在
	 */
	R14("币种不存在"),
	/**
	 * 介质卡号不存在
	 */
	R15("介质卡号不存在"),
	/**
	 * 申请编号为空
	 */
	R16("申请编号为空"),
	/**
	 * 消费凭密选择为空
	 */
	R17("消费凭密选择为空"),
	/**
	 * 性别为空
	 */
	R18("性别为空"),
	/**
	 * 生日为空
	 */
	R19("生日为空"),
	/**
	 * 婚姻状况为空
	 */
	R20("婚姻状况为空"),
	/**
	 * 家庭人口为空
	 */
	R21("家庭人口为空"),
	/**
	 * 证件类型为空
	 */
	R22("证件类型为空"),
	/**
	 * 教育程度为空
	 */
	R23("教育程度为空"),
	/**
	 * 住宅状况为空
	 */
	R24("住宅状况为空"),
	/**
	 * 自动还款方式为空
	 */
	R25("自动还款方式为空"),
	/**
	 * 约定还款扣款账号为空
	 */
	R26("约定还款扣款账号为空"),
	/**
	 * 联系人姓名为空
	 */
	R27("联系人姓名为空"),
	/**
	 * 联系人与申请人关系为空
	 */
	R28("联系人与申请人关系为空"),
	/**
	 * 联系人身份证件类型为空
	 */
	R29("联系人身份证件类型为空"),
	/**
	 * 联系人证件号码为空
	 */
	R30("联系人证件号码为空"),
	/**
	 * 联系人性别为空
	 */
	R31("联系人性别为空"),
	/**
	 * 联系人生日为空
	 */
	R32("联系人生日为空"),
	/**
	 * 建议信用额度为空
	 */
	R33("建议信用额度为空"),
	/**
	 * 周期限额为空
	 */
	R34("周期限额为空"),
	/**
	 * 周期取现限额为空
	 */
	R35("周期取现限额为空"),
	/**
	 * 网银周期交易限额为空
	 */
	R36("网银周期交易限额为空"),
	/**
	 * 单笔交易限额为空
	 */
	R37("单笔交易限额为空"),
	/**
	 * 单笔取现交易限额为空
	 */
	R38("单笔取现交易限额为空"),
	/**
	 * 网银单笔交易为空
	 */
	R39("网银单笔交易为空"),
	/**
	 * 发卡网点为空
	 */
	R40("发卡网点为空"),
	/**
	 * 与主卡持卡人关系为空
	 */
	R41("与主卡持卡人关系为空"),
	/**
	 * 工作稳定性为空
	 */
	R42("工作稳定性为空"),
	/**
	 * 就业状态为空
	 */
	R43("就业状态为空"),
	/**
	 * 住宅类型为空
	 */
	R44("住宅类型为空"),
	/**
	 * 个人资产类型为空
	 */
	R45("个人资产类型为空"),
	/**
	 * 是否永久居住为空
	 */
	R46("是否永久居住为空"),
	/**
	 * 社保缴存金额为空
	 */
	R47("社保缴存金额为空"),
	/**
	 * 风险情况为空
	 */
	R48("风险情况为空"),
	/**
	 * 账单周期为空
	 */
	R49("账单周期为空"),
	/**
	 * 家庭国家代码为空
	 */
	R50("家庭国家代码为空"),
	/**
	 * 家庭城市为空
	 */
	R51("家庭城市为空"),
	/**
	 * 现住址电话号码为空
	 */
	R52("现住址电话号码为空"),
	/**
	 * 公司国家代码为空
	 */
	R53("公司国家代码为空"),
	/**
	 * 公司城市为空
	 */
	R54("公司城市为空"),
	/**
	 * 单位电话号码为空
	 */
	R55("单位电话号码为空"),
	/**
	 * 账单寄送标识对应的地址不存在
	 */
	R56("账单寄送标识对应的地址不存在"),
	/**
	 * 账户信息和客户信息不符
	 */
	R57("账户信息和客户信息不符"),
	/**
	 * 卡产品参数设置有误,开启随用金功能的卡产品请关闭【指定POS分期开卡】功能,且账户类型必须为A独立账户.请联系行方(授权)修改参数
	 */
	R58("卡产品参数设置有误,开启随用金功能的卡产品请关闭【指定POS分期开卡】功能,且账户类型必须为A独立账户.请联系行方(授权)修改参数")
	;
	
	private String  reasonDesc;
	
	private AppRejectReason(String reasonDesc)
	{
		this.reasonDesc = reasonDesc;
	}
	
	public String getReasonDesc() {
		return reasonDesc;
	}
	
}
