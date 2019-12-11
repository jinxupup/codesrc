package com.jjb.ecms.service.dto.Trans0140;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 * 账户资产信息查询结果响应</br>
 * @author hejn
 *
 */
@Data
public class Trans0140Resp extends BasicResponse implements Serializable {

	public static final long serialVersionUID = 1L;

	public String bankInterviewFlag;//银行面签标识,0-无需面签1-需要面签
	public String dccAppFlag;//双核心_是否有当前正在处理的信用卡申请,1是0否无法获取（异常默认）-1
	public String dccAcctFlag;//双核心_是否有信用卡账户,1是0否无法获取（异常默认）-1
	public String dccAcctFix;//双核心_信用卡账户当前固定额度,无信用卡账户、无法获取（异常、默认）-1
	public String dccAcctTmp;//双核心_信用卡账户当前临时额度,无信用卡账户、无法获取（异常、默认）-1
	public String dccAcctTmpExp;//双核心_信用卡账户当前临时额度有效期,生成距离到期剩余天数，单位：天，到期、超过期限、无信用卡账户、法获取（异常、默认）-1
	public String dccAcctRevoLmt;//双核心_信用卡账户当前固定额度外循环额度,无信用卡账户、无法获取（异常、默认）-1
	public String dccAcctOnceLmt;//双核心_信用卡账户当前额度外一次性额度,无信用卡账户、无法获取（异常、默认）-1
	public String dccAcctDelq;//双核心_信用卡账户当前逾期等级,无信用卡账户、无法获取（异常、默认）-1，有账户，无逾期0,逾期等级0-7，超过7为7
	public String dccAcctBlk;//双核心_信用卡账户当前账户锁定码,无code、无信用卡账户、无法获取（异常、默认）空值
	public String dccAcctActFlag;//双核心_信用卡账户当前是否激活,1是0否无信用卡账户、无法获取（异常默认）-1
	public String dccAcctCancelFlag;//双核心_信用卡账户当前是否注销,1是0否无信用卡账户、无法获取（异常默认）-1
	public String bankWhitelistFlag;//银行白名单类型,无命中、无法获取（异常、默认）null
	public String bankWhitelistLmt;//银行白名单建议额度,无命中、无法获取（异常、默认）-1
	public String bankWhitelistExp;//银行白名单有效期,生成距离到期剩余天数，单位：天，到期、超过期限、无命中、无法获取（异常、默认）-1
	public String bankAcctInitialDateDayCnt;//银行最早开户日期距离申请天数,无账户、无法获取（异常、默认）-1
	public String bankDepositInitialDayCnt;//存款开户日期距今天数,无存款账户、无法获取（异常、默认）-1
	public String bankL1mAvgDeposit;//银行最近30天存款日均余额,币值人民币，保留2位小数，无存款账户、无法获取（异常、默认）-1
	public String bankL3mAvgDeposit;//银行最近90天存款日均余额,币值人民币，保留2位小数，无存款账户、无法获取（异常、默认）-1
	public String bankPayrollCompany;//代发工资单位名称,无代发、无法获取（异常、默认）返回null
	public String bankPayrollMonthCnt;//代发工资的起始时间距申请月份数,无代发、无法获取（异常、默认）返回-1
	public String bankL3mAvgPayrollAmt;//近3个月的平均代发工资金额,币值人民币，保留2位小数，无代发、无法获取（异常、默认）-1
	public String bankFinanceInitialDayCnt;//理财账户开户日期距距申请天数,无理财账户、无法获取（异常、默认）-1
	public String bankL1mAvgFinanceBalance;//理财产品近30天月日均余额,币值人民币，保留2位小数，无理财账户、无法获取（异常、默认）-1
	public String bankL3mAvgFinanceBalance;//理财产品近90天月日均余额,币值人民币，保留2位小数，无理财账户、无法获取（异常、默认）-1
	public String bankLoanBalance;//银行贷款总余额,币值人民币，保留2位小数，无贷款、无法获取（异常、默认）-1
	public String bankAutoloanInitialMonthCnt;//车贷开户日期距申请月数,无车贷账户、无法获取（异常、默认）-1
	public String bankAutoloanBalance;//车贷余额,币值人民币，保留2位小数，无车贷账户、无法获取（异常、默认）-1
	public String bankAutoloanMthpay;//车贷月还款额,币值人民币，保留2位小数，无车贷账户、无法获取（异常、默认）-1
	public String bankCurrAutoloanStatus;//车贷当前逾期状态,1-正常/2-结清/3-逾期/4-转出/5-呆账/6-核销，如逾期需返回逾期等级M1-M7,无车贷、无法获取（异常、默认）null
	public String bankL12mAutoloanDelqRank;//车贷历史12个月最大逾期等级,0-7,大于7为7,无车贷、无法获取（异常、默认）-1
	public String bankMortgageInitialMonthCnt;//房贷开户日期距申请月数,无房贷账户、无法获取（异常、默认）-1
	public String bankMortgageBalance;//房贷余额,币值人民币，保留2位小数，无房贷账户、无法获取（异常、默认）-1
	public String bankMortgageMthpay;//房贷月还款额,币值人民币，保留2位小数，无房贷账户、无法获取（异常、默认）-1
	public String bankCurrMortgageStatus;//房贷当前状态,1-正常/2-结清/3-逾期/4-转出/5-呆账/6-核销，如逾期需返回逾期等级M1-M7，无房贷null
	public String bankL12mMortgageDelqRank;//房贷历史12个月最大逾期等级,0-7,大于7为7,无房贷、无法获取（异常、默认）-1
	public String bankOtherguartInloanBalance;//其他未结清有抵押贷款余额,币值人民币，保留2位小数，无其他未结清抵押类贷款、无法获取（异常、默认）返回-1，有其他抵押类贷款但均已结清返回0
	public String bankOtherguartInloanMonthCnt;//其他未结清有抵押贷款开户日期距申请月数,无其他未结清抵押类贷款、无法获取（异常、默认）-1
	public String bankL12mOtherguartInloanDelqRank;//其他未结清有抵押贷款历史12个月最大逾期等级,0-7,大于7为7,无其他未结清有抵押贷款、无法获取（异常、默认）-1
	public String bankCurrOtherguartInloanDelqStatus;//其他未结清有抵押贷款当前状态,1-正常/2-结清/3-逾期/4-转出/5-呆账/6-核销，如逾期需返回逾期等级M1-M7无其他未结清有抵押贷款、无法获取（异常、默认）null
	public String currBankCreditloanStatus;//其他无抵押贷款当前状态,1-正常/2-结清/3-逾期/4-转出/5-呆账/6-核销，如逾期需返回逾期等级M1-M7无信用无抵押贷款、无法获取（异常、默认）null
	public String bankL12mCreditloanDelqRank;//无抵押贷款历史12个月最大逾期等级,0-7,大于7为7,无信用无抵押贷款、无法获取（异常、默认）-1
	public String bankCreditloanBalance;//无抵押贷款余额,币值人民币，保留2位小数，无其他未结清的信用无抵押贷款、无法获取（异常、默认）返回-1，有其他未结清的信用无抵押贷款但均已结清返回0
	public String bankCreditloanMonthCnt;//无抵押贷款开户日期距申请月数,无其他未结清有抵押贷款、无法获取（异常、默认）-1
	public String ifSelfEmployed;//是否自雇人士,1是，0否，异常-1
	
}
