package com.jjb.ecms.facility.nodeobject;

import java.math.BigDecimal;
import java.util.Date;

import com.jjb.unicorn.facility.cstruct.CChar;

/**
 * @Description: ipad进件参数类
 * @author JYData-R&D-Big Star
 * @date 2016年9月26日 下午5:35:18
 * @version V1.0
 */
public class ApplyMobilePadItem {
	/**
	 * 机构号
	 */
	@CChar(value = 12, order = 100)
	public String org;

	/**
	 * 申请信息类型
	 */
	@CChar(value = 1, order = 200)
	public String appType;

	/**
	 * 主附卡指示
	 */
	@CChar(value = 1, order = 300)
	public String bscSuppInd;

	/**
	 * pad申请ID
	 */
	@CChar(value = 20, order = 400)
	public String padAppId;

	/**
	 * pad附卡ID
	 */
	@CChar(value = 20, order = 500)
	public String padSupId;

	/**
	 * 产品代码
	 */
	@CChar(value = 6, order = 600)
	public String productCd;

	/**
	 * 卡面代码
	 */
	@CChar(value = 10, order = 700)
	public String pyhCd;

	/**
	 * 申请额度
	 */
	@CChar(value = 13, order = 800)
	public BigDecimal appLmt;

	/**
	 * 申请人主卡卡号
	 */
	@CChar(value = 19, order = 900)
	public String cardNo;

	/**
	 * 与主卡持卡人关系
	 */
	@CChar(value = 1, order = 1000)
	public String relationshipToBsc;

	/**
	 * 发卡网点
	 */
	@CChar(value = 9, order = 1100)
	public String owningBranch;

	/**
	 * 申请来源
	 */
	@CChar(value = 20, order = 1200)
	public String appSource;

	/**
	 * 促销码
	 */
	@CChar(value = 15, order = 1300)
	public String appPromotionCd;

	/**
	 * 姓名（中文）
	 */
	@CChar(value = 80, order = 1400)
	public String name;

	/**
	 * 客户类型
	 */
	@CChar(value = 1, order = 1500)
	public String clientType;

	/**
	 * 凸印名-姓名拼音
	 */
	@CChar(value = 26, order = 1600)
	public String embName;

	/**
	 * 证件类型
	 */
	@CChar(value = 1, order = 1700)
	public String idType;

	/**
	 * 证件号码
	 */
	@CChar(value = 30, order = 1800)
	public String idNo;

	/**
	 * 性别
	 */
	@CChar(value = 1, order = 1900)
	public String gender;

	/**
	 * 出生日期
	 */
	@CChar(value = 8, datePattern = "yyyyMMdd", order = 2000)
	public Date birthday;

	/**
	 * 国籍
	 */
	@CChar(value = 3, order = 2100)
	public String nationality;

	/**
	 * 婚姻状况
	 */
	@CChar(value = 1, order = 2200)
	public String maritalStatus;

	/**
	 * 教育程度
	 */
	@CChar(value = 1, order = 2300)
	public String qualification;

	/**
	 * 住宅状况
	 */
	@CChar(value = 1, order = 2400)
	public String houseOwnership;

	/**
	 * 个人年收入
	 */
	@CChar(value = 15, precision = 2, order = 2500)
	public BigDecimal yearIncome;

	/**
	 * 移动电话号码
	 */
	@CChar(value = 20, order = 2600)
	public String cellphone;

	/**
	 * 电子信箱
	 */
	@CChar(value = 80, order = 2700)
	public String email;

	/**
	 * 家庭人口
	 */
	@CChar(value = 2, order = 2800)
	public String familyMember;

	/**
	 * 家庭国家代码
	 */
	@CChar(value = 3, order = 2900)
	public String homeAddrCtryCd;

	/**
	 * 家庭省份
	 */
	@CChar(value = 40, order = 3000)
	public String homeState;

	/**
	 * 家庭城市
	 */
	@CChar(value = 40, order = 3100)
	public String homeCity;

	/**
	 * 家庭所在区县
	 */
	@CChar(value = 40, order = 3200)
	public String homeZone;

	/**
	 * 现住址
	 */
	@CChar(value = 200, order = 3300)
	public String homeAdd;

	/**
	 * 现住址邮编
	 */
	@CChar(value = 10, order = 3400)
	public String homePostcode;

	/**
	 * 现住址电话号码
	 */
	@CChar(value = 15, order = 3500)
	public String homePhone;

	/**
	 * 单位全称
	 */
	@CChar(value = 80, order = 3600)
	public String corpName;

	/**
	 * 单位性质
	 */
	@CChar(value = 1, order = 3700)
	public String empStructure;

	/**
	 * 行业类别
	 */
	@CChar(value = 2, order = 3800)
	public String empType;

	/**
	 * 公司国家代码
	 */
	@CChar(value = 3, order = 3900)
	public String empAddrCtryCd;

	/**
	 * 公司省份
	 */
	@CChar(value = 40, order = 4000)
	public String empProvince;

	/**
	 * 公司城市
	 */
	@CChar(value = 40, order = 4100)
	public String empCity;

	/**
	 * 公司行政区
	 */
	@CChar(value = 40, order = 4200)
	public String empZone;

	/**
	 * 单位地址
	 */
	@CChar(value = 200, order = 4300)
	public String empAdd;

	/**
	 * 单位邮编
	 */
	@CChar(value = 10, order = 4400)
	public String empPostcode;

	/**
	 * 单位电话号码
	 */
	@CChar(value = 20, order = 4500)
	public String empPhone;

	/**
	 * 职业
	 */
	@CChar(value = 1, order = 4600)
	public String occupation;

	/**
	 * 职务
	 */
	@CChar(value = 1, order = 4700)
	public String empPost;

	/**
	 * 职称
	 */
	@CChar(value = 1, order = 4800)
	public String titleOfTechnical;

	/**
	 * 任职部门
	 */
	@CChar(value = 80, order = 4900)
	public String empDepapment;

	/**
	 * 本行员工号
	 */
	@CChar(value = 20, order = 5000)
	public String bankmemberNo;

	/**
	 * 联系人姓名（中文）
	 */
	@CChar(value = 80, order = 5100)
	public String contactName;

	/**
	 * 联系人与申请人关系
	 */
	@CChar(value = 1, order = 5200)
	public String contactRelation;

	/**
	 * 联系人手机
	 */
	@CChar(value = 20, order = 5300)
	public String contactMobile;

	/**
	 * 联系人公司电话号码
	 */
	@CChar(value = 15, order = 5400)
	public String contactEmpPhone;

	/**
	 * 联系人公司名称
	 */
	@CChar(value = 80, order = 5500)
	public String contactEmpName;

	/**
	 * 是否在职
	 */
	@CChar(value = 1, order = 5600)
	public String empStatus;

	/**
	 * 消费凭密选择
	 */
	@CChar(value = 1, order = 5700)
	public String posPinVerifyInd;

	/**
	 * 介质卡领取方式
	 */
	@CChar(value = 1, order = 5800)
	public String cardFetchMethod;

	/**
	 * 卡片寄送地址标志
	 */
	@CChar(value = 1, order = 5900)
	public String cardMailerInd;

	/**
	 * 账单介质类型标志
	 */
	@CChar(value = 1, order = 6000)
	public String stmtMediaType;

	/**
	 * 账单寄送标志
	 */
	@CChar(value = 1, order = 6100)
	public String stmtMailAddrInd;

	/**
	 * 约定还款类型
	 */
	@CChar(value = 1, order = 6200)
	public String ddInd;

	/**
	 * 约定还款扣款账户姓名
	 */
	@CChar(value = 80, order = 6300)
	public String ddBankAcctName;

	/**
	 * 约定还款银行名称
	 */
	@CChar(value = 40, order = 6400)
	public String ddBankName;

	/**
	 * 约定还款开户行号
	 */
	@CChar(value = 20, order = 6500)
	public String ddBankBranch;

	/**
	 * 约定还款扣款账号
	 */
	@CChar(value = 40, order = 6600)
	public String ddBankAcctNo;

	/**
	 * 备注
	 */
	@CChar(value = 200, order = 6700)
	public String remark;

	/**
	 * 年收入
	 */
	@CChar(value = 15, precision = 2, order = 6800)
	public BigDecimal incomeEvi;

	/**
	 * 投保总月份
	 */
	@CChar(value = 2, order = 6900)
	public Integer insureMonthCount;

	/**
	 * 社保月缴存金额
	 */
	@CChar(value = 15, precision = 2, order = 7000)
	public BigDecimal socialInsAmt;

	/**
	 * 投保基数
	 */
	@CChar(value = 15, precision = 2, order = 7100)
	public BigDecimal insureBase;

	/**
	 * 是否企业法人
	 */
	@CChar(value = 1, order = 7200)
	public String isLegalPerson;

	/**
	 * 公司注册资金
	 */
	@CChar(value = 15, precision = 2, order = 7300)
	public BigDecimal registerFund;

	/**
	 * 个人占股比例
	 */
	@CChar(value = 5, precision = 2, order = 7400)
	public BigDecimal stockProp;

	/**
	 * 房产类型
	 */
	@CChar(value = 1, order = 7500)
	public String estateType;

	/**
	 * 房产总价值
	 */
	@CChar(value = 15, precision = 2, order = 7600)
	public BigDecimal estateValue;

	/**
	 * 房产贷款金额
	 */
	@CChar(value = 15, precision = 2, order = 7700)
	public BigDecimal loan;

	/**
	 * 自购车辆价值
	 */
	@CChar(value = 15, precision = 2, order = 7800)
	public BigDecimal carValue;

	/**
	 * 自购车牌号
	 */
	@CChar(value = 10, order = 7900)
	public String carLpn;

	/**
	 * 已持有卡片银行名称
	 */
	@CChar(value = 80, order = 8000)
	public String ctcBank;

	/**
	 * 已持有卡片信用额度
	 */
	@CChar(value = 15, precision = 2, order = 8100)
	public BigDecimal ctcCreditLimit;

	/**
	 * 已持有卡片发卡时间
	 */
	@CChar(value = 8, datePattern = "yyyyMMdd", order = 8200)
	public Date sendCardDate;

}
