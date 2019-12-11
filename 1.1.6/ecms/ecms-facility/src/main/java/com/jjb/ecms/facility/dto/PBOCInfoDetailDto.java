/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 人行报告展示实体类
 * @author JYData-R&D-Big T.T
 * @date 2018年3月20日 上午10:58:02
 * @version V1.0
 */

public class PBOCInfoDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	         
	
	private String loanG;//贷款结束
	private String loanDelay;//贷款展期(延期)
	private String loanD;//贷款担保人代还
	private String loanZ;//贷款以资抵债
	private String loanFiveCategory;//贷款五级分类
	
	private String ccG;//贷记卡结束
	private String ccDelay;//贷记卡展期(延期)
	private String ccD;//贷记卡担保人代还
	private String ccZ;//贷记卡以资抵债
	private String ccFiveCategory;//贷记卡五级分类
	
	private String scG;//准贷记卡结束
	private String scDelay;//准贷记卡展期(延期)
	private String scD;//准贷记卡担保人代还
	private String scZ;//准贷记卡以资抵债
	private String scFiveCategory;//准贷记卡五级分类
	
	private String house50StartDate;//0-50万房贷发放时间
	private String house50Count;//0-50万房贷笔数
	private String house100StartDate;//50-100万房贷发放时间
	private String house100Count;//50-100万房贷笔数
	private String house200StartDate;//100-200万房贷发放时间
	private String house200Count;//100-200万房贷笔数
	private String house200OverStartDate;//200万以上房贷发放时间
	private String house200OverCount;//200万以上房贷笔数
	private String houseMoneySum;//房贷本金余额总额
	private String houseCountSum;//房贷发放总笔数
	private String housePayMoney;//房贷应还款总额
	
	private String business20StartDate;//0-20万经营贷发放时间
	private String business20Count;//0-20万经营贷笔数
	private String business50StartDate;//20-50万经营贷发放时间
	private String business50Count;//20-50万经营贷笔数
	private String business100StartDate;//50-100万经营贷发放时间
	private String business100Count;//50-100万经营贷笔数
	private String business100OverStartDate;//100万以上经营贷发放时间
	private String business100OverCount;//100万以上经营贷笔数
	private String businessMoneySum;//经营贷本金余额总额
	private String businessCountSum;//经营贷发放总笔数
	private String businessPayMoney;//经营贷应还款总额
	
	private String other5StartDate;//0-5万其他贷款发放时间
	private String other5Count;//0-5万其他贷款笔数
	private String other10StartDate;//5-10万其他贷款发放时间
	private String other10Count;//5-10万其他贷款笔数
	private String other20StartDate;//10-20万其他贷款发放时间
	private String other20Count;//10-20万其他贷款笔数
	private String other20OverStartDate;//20万以上其他贷款发放时间
	private String other20OverCount;//20万以上其他贷款笔数
	private String otherMoneySum;//其他贷款本金余额总额
	private String otherCountSum;//其他贷款发放总笔数
	private String otherPayMoney;//其他贷款应还款总额
	
	private String moneySum;//本金余额总额
	private String countSum;//发放总笔数
	private String payMoneySum;//应还款总额
	
	private String query3Times;//近3个月查询次数
	private String query6Times;//近6个月查询次数
	private String query12Times;//近12个月查询次数
	
	Map<String,String> taxesInfoMap;//欠税记录
	Map<String,String> judgmentsInfoMap;//民事判决记录
	Map<String,String> enforceRecordInfoMap;//强制执行记录
	Map<String,String> punishmentRecordsInfoMap;//行政处罚记录
	Map<String,String> houseFundRecordsInfoMap;//住房公积金记录
	Map<String,String> pensionIssueRecordsInfoMap;//养老保险金记录
	Map<String,String> thresholdRecordInfoMap;//低保救助记录
	Map<String,String> qualificationInfoMap;//职业资格记录
	Map<String,String> adminRewardInfoMap;//行政奖励记录
	Map<String,String> carTransactionsInfoMap;//车辆交易记录
	Map<String,String> telecomInfoMap;//电信缴费记录
	/**
	 * @return the loanG
	 */
	public String getLoanG() {
		return loanG;
	}
	/**
	 * @param loanG the loanG to set
	 */
	public void setLoanG(String loanG) {
		this.loanG = loanG;
	}
	/**
	 * @return the loanDelay
	 */
	public String getLoanDelay() {
		return loanDelay;
	}
	/**
	 * @param loanDelay the loanDelay to set
	 */
	public void setLoanDelay(String loanDelay) {
		this.loanDelay = loanDelay;
	}
	/**
	 * @return the loanD
	 */
	public String getLoanD() {
		return loanD;
	}
	/**
	 * @param loanD the loanD to set
	 */
	public void setLoanD(String loanD) {
		this.loanD = loanD;
	}
	/**
	 * @return the loanZ
	 */
	public String getLoanZ() {
		return loanZ;
	}
	/**
	 * @param loanZ the loanZ to set
	 */
	public void setLoanZ(String loanZ) {
		this.loanZ = loanZ;
	}
	/**
	 * @return the loanFiveCategory
	 */
	public String getLoanFiveCategory() {
		return loanFiveCategory;
	}
	/**
	 * @param loanFiveCategory the loanFiveCategory to set
	 */
	public void setLoanFiveCategory(String loanFiveCategory) {
		this.loanFiveCategory = loanFiveCategory;
	}
	/**
	 * @return the ccG
	 */
	public String getCcG() {
		return ccG;
	}
	/**
	 * @param ccG the ccG to set
	 */
	public void setCcG(String ccG) {
		this.ccG = ccG;
	}
	/**
	 * @return the ccDelay
	 */
	public String getCcDelay() {
		return ccDelay;
	}
	/**
	 * @param ccDelay the ccDelay to set
	 */
	public void setCcDelay(String ccDelay) {
		this.ccDelay = ccDelay;
	}
	/**
	 * @return the ccD
	 */
	public String getCcD() {
		return ccD;
	}
	/**
	 * @param ccD the ccD to set
	 */
	public void setCcD(String ccD) {
		this.ccD = ccD;
	}
	/**
	 * @return the ccZ
	 */
	public String getCcZ() {
		return ccZ;
	}
	/**
	 * @param ccZ the ccZ to set
	 */
	public void setCcZ(String ccZ) {
		this.ccZ = ccZ;
	}
	/**
	 * @return the ccFiveCategory
	 */
	public String getCcFiveCategory() {
		return ccFiveCategory;
	}
	/**
	 * @param ccFiveCategory the ccFiveCategory to set
	 */
	public void setCcFiveCategory(String ccFiveCategory) {
		this.ccFiveCategory = ccFiveCategory;
	}
	/**
	 * @return the scG
	 */
	public String getScG() {
		return scG;
	}
	/**
	 * @param scG the scG to set
	 */
	public void setScG(String scG) {
		this.scG = scG;
	}
	/**
	 * @return the scDelay
	 */
	public String getScDelay() {
		return scDelay;
	}
	/**
	 * @param scDelay the scDelay to set
	 */
	public void setScDelay(String scDelay) {
		this.scDelay = scDelay;
	}
	/**
	 * @return the scD
	 */
	public String getScD() {
		return scD;
	}
	/**
	 * @param scD the scD to set
	 */
	public void setScD(String scD) {
		this.scD = scD;
	}
	/**
	 * @return the scZ
	 */
	public String getScZ() {
		return scZ;
	}
	/**
	 * @param scZ the scZ to set
	 */
	public void setScZ(String scZ) {
		this.scZ = scZ;
	}
	/**
	 * @return the scFiveCategory
	 */
	public String getScFiveCategory() {
		return scFiveCategory;
	}
	/**
	 * @param scFiveCategory the scFiveCategory to set
	 */
	public void setScFiveCategory(String scFiveCategory) {
		this.scFiveCategory = scFiveCategory;
	}
	/**
	 * @return the house50StartDate
	 */
	public String getHouse50StartDate() {
		return house50StartDate;
	}
	/**
	 * @param house50StartDate the house50StartDate to set
	 */
	public void setHouse50StartDate(String house50StartDate) {
		this.house50StartDate = house50StartDate;
	}
	/**
	 * @return the house50Count
	 */
	public String getHouse50Count() {
		return house50Count;
	}
	/**
	 * @param house50Count the house50Count to set
	 */
	public void setHouse50Count(String house50Count) {
		this.house50Count = house50Count;
	}
	/**
	 * @return the house100StartDate
	 */
	public String getHouse100StartDate() {
		return house100StartDate;
	}
	/**
	 * @param house100StartDate the house100StartDate to set
	 */
	public void setHouse100StartDate(String house100StartDate) {
		this.house100StartDate = house100StartDate;
	}
	/**
	 * @return the house100Count
	 */
	public String getHouse100Count() {
		return house100Count;
	}
	/**
	 * @param house100Count the house100Count to set
	 */
	public void setHouse100Count(String house100Count) {
		this.house100Count = house100Count;
	}
	/**
	 * @return the house200StartDate
	 */
	public String getHouse200StartDate() {
		return house200StartDate;
	}
	/**
	 * @param house200StartDate the house200StartDate to set
	 */
	public void setHouse200StartDate(String house200StartDate) {
		this.house200StartDate = house200StartDate;
	}
	/**
	 * @return the house200Count
	 */
	public String getHouse200Count() {
		return house200Count;
	}
	/**
	 * @param house200Count the house200Count to set
	 */
	public void setHouse200Count(String house200Count) {
		this.house200Count = house200Count;
	}
	/**
	 * @return the house200OverStartDate
	 */
	public String getHouse200OverStartDate() {
		return house200OverStartDate;
	}
	/**
	 * @param house200OverStartDate the house200OverStartDate to set
	 */
	public void setHouse200OverStartDate(String house200OverStartDate) {
		this.house200OverStartDate = house200OverStartDate;
	}
	/**
	 * @return the house200OverCount
	 */
	public String getHouse200OverCount() {
		return house200OverCount;
	}
	/**
	 * @param house200OverCount the house200OverCount to set
	 */
	public void setHouse200OverCount(String house200OverCount) {
		this.house200OverCount = house200OverCount;
	}
	/**
	 * @return the houseMoneySum
	 */
	public String getHouseMoneySum() {
		return houseMoneySum;
	}
	/**
	 * @param houseMoneySum the houseMoneySum to set
	 */
	public void setHouseMoneySum(String houseMoneySum) {
		this.houseMoneySum = houseMoneySum;
	}
	/**
	 * @return the houseCountSum
	 */
	public String getHouseCountSum() {
		return houseCountSum;
	}
	/**
	 * @param houseCountSum the houseCountSum to set
	 */
	public void setHouseCountSum(String houseCountSum) {
		this.houseCountSum = houseCountSum;
	}
	/**
	 * @return the housePayMoney
	 */
	public String getHousePayMoney() {
		return housePayMoney;
	}
	/**
	 * @param housePayMoney the housePayMoney to set
	 */
	public void setHousePayMoney(String housePayMoney) {
		this.housePayMoney = housePayMoney;
	}
	/**
	 * @return the business20StartDate
	 */
	public String getBusiness20StartDate() {
		return business20StartDate;
	}
	/**
	 * @param business20StartDate the business20StartDate to set
	 */
	public void setBusiness20StartDate(String business20StartDate) {
		this.business20StartDate = business20StartDate;
	}
	/**
	 * @return the business20Count
	 */
	public String getBusiness20Count() {
		return business20Count;
	}
	/**
	 * @param business20Count the business20Count to set
	 */
	public void setBusiness20Count(String business20Count) {
		this.business20Count = business20Count;
	}
	/**
	 * @return the business50StartDate
	 */
	public String getBusiness50StartDate() {
		return business50StartDate;
	}
	/**
	 * @param business50StartDate the business50StartDate to set
	 */
	public void setBusiness50StartDate(String business50StartDate) {
		this.business50StartDate = business50StartDate;
	}
	/**
	 * @return the business50Count
	 */
	public String getBusiness50Count() {
		return business50Count;
	}
	/**
	 * @param business50Count the business50Count to set
	 */
	public void setBusiness50Count(String business50Count) {
		this.business50Count = business50Count;
	}
	/**
	 * @return the business100StartDate
	 */
	public String getBusiness100StartDate() {
		return business100StartDate;
	}
	/**
	 * @param business100StartDate the business100StartDate to set
	 */
	public void setBusiness100StartDate(String business100StartDate) {
		this.business100StartDate = business100StartDate;
	}
	/**
	 * @return the business100Count
	 */
	public String getBusiness100Count() {
		return business100Count;
	}
	/**
	 * @param business100Count the business100Count to set
	 */
	public void setBusiness100Count(String business100Count) {
		this.business100Count = business100Count;
	}
	/**
	 * @return the business100OverStartDate
	 */
	public String getBusiness100OverStartDate() {
		return business100OverStartDate;
	}
	/**
	 * @param business100OverStartDate the business100OverStartDate to set
	 */
	public void setBusiness100OverStartDate(String business100OverStartDate) {
		this.business100OverStartDate = business100OverStartDate;
	}
	/**
	 * @return the business100OverCount
	 */
	public String getBusiness100OverCount() {
		return business100OverCount;
	}
	/**
	 * @param business100OverCount the business100OverCount to set
	 */
	public void setBusiness100OverCount(String business100OverCount) {
		this.business100OverCount = business100OverCount;
	}
	/**
	 * @return the businessMoneySum
	 */
	public String getBusinessMoneySum() {
		return businessMoneySum;
	}
	/**
	 * @param businessMoneySum the businessMoneySum to set
	 */
	public void setBusinessMoneySum(String businessMoneySum) {
		this.businessMoneySum = businessMoneySum;
	}
	/**
	 * @return the businessCountSum
	 */
	public String getBusinessCountSum() {
		return businessCountSum;
	}
	/**
	 * @param businessCountSum the businessCountSum to set
	 */
	public void setBusinessCountSum(String businessCountSum) {
		this.businessCountSum = businessCountSum;
	}
	/**
	 * @return the businessPayMoney
	 */
	public String getBusinessPayMoney() {
		return businessPayMoney;
	}
	/**
	 * @param businessPayMoney the businessPayMoney to set
	 */
	public void setBusinessPayMoney(String businessPayMoney) {
		this.businessPayMoney = businessPayMoney;
	}
	/**
	 * @return the other5StartDate
	 */
	public String getOther5StartDate() {
		return other5StartDate;
	}
	/**
	 * @param other5StartDate the other5StartDate to set
	 */
	public void setOther5StartDate(String other5StartDate) {
		this.other5StartDate = other5StartDate;
	}
	/**
	 * @return the other5Count
	 */
	public String getOther5Count() {
		return other5Count;
	}
	/**
	 * @param other5Count the other5Count to set
	 */
	public void setOther5Count(String other5Count) {
		this.other5Count = other5Count;
	}
	/**
	 * @return the other10StartDate
	 */
	public String getOther10StartDate() {
		return other10StartDate;
	}
	/**
	 * @param other10StartDate the other10StartDate to set
	 */
	public void setOther10StartDate(String other10StartDate) {
		this.other10StartDate = other10StartDate;
	}
	/**
	 * @return the other10Count
	 */
	public String getOther10Count() {
		return other10Count;
	}
	/**
	 * @param other10Count the other10Count to set
	 */
	public void setOther10Count(String other10Count) {
		this.other10Count = other10Count;
	}
	/**
	 * @return the other20StartDate
	 */
	public String getOther20StartDate() {
		return other20StartDate;
	}
	/**
	 * @param other20StartDate the other20StartDate to set
	 */
	public void setOther20StartDate(String other20StartDate) {
		this.other20StartDate = other20StartDate;
	}
	/**
	 * @return the other20Count
	 */
	public String getOther20Count() {
		return other20Count;
	}
	/**
	 * @param other20Count the other20Count to set
	 */
	public void setOther20Count(String other20Count) {
		this.other20Count = other20Count;
	}
	/**
	 * @return the other20OverStartDate
	 */
	public String getOther20OverStartDate() {
		return other20OverStartDate;
	}
	/**
	 * @param other20OverStartDate the other20OverStartDate to set
	 */
	public void setOther20OverStartDate(String other20OverStartDate) {
		this.other20OverStartDate = other20OverStartDate;
	}
	/**
	 * @return the other20OverCount
	 */
	public String getOther20OverCount() {
		return other20OverCount;
	}
	/**
	 * @param other20OverCount the other20OverCount to set
	 */
	public void setOther20OverCount(String other20OverCount) {
		this.other20OverCount = other20OverCount;
	}
	/**
	 * @return the otherMoneySum
	 */
	public String getOtherMoneySum() {
		return otherMoneySum;
	}
	/**
	 * @param otherMoneySum the otherMoneySum to set
	 */
	public void setOtherMoneySum(String otherMoneySum) {
		this.otherMoneySum = otherMoneySum;
	}
	/**
	 * @return the otherCountSum
	 */
	public String getOtherCountSum() {
		return otherCountSum;
	}
	/**
	 * @param otherCountSum the otherCountSum to set
	 */
	public void setOtherCountSum(String otherCountSum) {
		this.otherCountSum = otherCountSum;
	}
	/**
	 * @return the otherPayMoney
	 */
	public String getOtherPayMoney() {
		return otherPayMoney;
	}
	/**
	 * @param otherPayMoney the otherPayMoney to set
	 */
	public void setOtherPayMoney(String otherPayMoney) {
		this.otherPayMoney = otherPayMoney;
	}
	/**
	 * @return the moneySum
	 */
	public String getMoneySum() {
		return moneySum;
	}
	/**
	 * @param moneySum the moneySum to set
	 */
	public void setMoneySum(String moneySum) {
		this.moneySum = moneySum;
	}
	/**
	 * @return the countSum
	 */
	public String getCountSum() {
		return countSum;
	}
	/**
	 * @param countSum the countSum to set
	 */
	public void setCountSum(String countSum) {
		this.countSum = countSum;
	}
	/**
	 * @return the payMoneySum
	 */
	public String getPayMoneySum() {
		return payMoneySum;
	}
	/**
	 * @param payMoneySum the payMoneySum to set
	 */
	public void setPayMoneySum(String payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	/**
	 * @return the query3Times
	 */
	public String getQuery3Times() {
		return query3Times;
	}
	/**
	 * @param query3Times the query3Times to set
	 */
	public void setQuery3Times(String query3Times) {
		this.query3Times = query3Times;
	}
	/**
	 * @return the query6Times
	 */
	public String getQuery6Times() {
		return query6Times;
	}
	/**
	 * @param query6Times the query6Times to set
	 */
	public void setQuery6Times(String query6Times) {
		this.query6Times = query6Times;
	}
	/**
	 * @return the query12Times
	 */
	public String getQuery12Times() {
		return query12Times;
	}
	/**
	 * @param query12Times the query12Times to set
	 */
	public void setQuery12Times(String query12Times) {
		this.query12Times = query12Times;
	}
	/**
	 * @return the taxesInfoMap
	 */
	public Map<String, String> getTaxesInfoMap() {
		return taxesInfoMap;
	}
	/**
	 * @param taxesInfoMap the taxesInfoMap to set
	 */
	public void setTaxesInfoMap(Map<String, String> taxesInfoMap) {
		this.taxesInfoMap = taxesInfoMap;
	}
	/**
	 * @return the judgmentsInfoMap
	 */
	public Map<String, String> getJudgmentsInfoMap() {
		return judgmentsInfoMap;
	}
	/**
	 * @param judgmentsInfoMap the judgmentsInfoMap to set
	 */
	public void setJudgmentsInfoMap(Map<String, String> judgmentsInfoMap) {
		this.judgmentsInfoMap = judgmentsInfoMap;
	}
	/**
	 * @return the enforceRecordInfoMap
	 */
	public Map<String, String> getEnforceRecordInfoMap() {
		return enforceRecordInfoMap;
	}
	/**
	 * @param enforceRecordInfoMap the enforceRecordInfoMap to set
	 */
	public void setEnforceRecordInfoMap(Map<String, String> enforceRecordInfoMap) {
		this.enforceRecordInfoMap = enforceRecordInfoMap;
	}
	/**
	 * @return the punishmentRecordsInfoMap
	 */
	public Map<String, String> getPunishmentRecordsInfoMap() {
		return punishmentRecordsInfoMap;
	}
	/**
	 * @param punishmentRecordsInfoMap the punishmentRecordsInfoMap to set
	 */
	public void setPunishmentRecordsInfoMap(
			Map<String, String> punishmentRecordsInfoMap) {
		this.punishmentRecordsInfoMap = punishmentRecordsInfoMap;
	}
	/**
	 * @return the houseFundRecordsInfoMap
	 */
	public Map<String, String> getHouseFundRecordsInfoMap() {
		return houseFundRecordsInfoMap;
	}
	/**
	 * @param houseFundRecordsInfoMap the houseFundRecordsInfoMap to set
	 */
	public void setHouseFundRecordsInfoMap(
			Map<String, String> houseFundRecordsInfoMap) {
		this.houseFundRecordsInfoMap = houseFundRecordsInfoMap;
	}
	/**
	 * @return the pensionIssueRecordsInfoMap
	 */
	public Map<String, String> getPensionIssueRecordsInfoMap() {
		return pensionIssueRecordsInfoMap;
	}
	/**
	 * @param pensionIssueRecordsInfoMap the pensionIssueRecordsInfoMap to set
	 */
	public void setPensionIssueRecordsInfoMap(
			Map<String, String> pensionIssueRecordsInfoMap) {
		this.pensionIssueRecordsInfoMap = pensionIssueRecordsInfoMap;
	}
	/**
	 * @return the thresholdRecordInfoMap
	 */
	public Map<String, String> getThresholdRecordInfoMap() {
		return thresholdRecordInfoMap;
	}
	/**
	 * @param thresholdRecordInfoMap the thresholdRecordInfoMap to set
	 */
	public void setThresholdRecordInfoMap(Map<String, String> thresholdRecordInfoMap) {
		this.thresholdRecordInfoMap = thresholdRecordInfoMap;
	}
	/**
	 * @return the qualificationInfoMap
	 */
	public Map<String, String> getQualificationInfoMap() {
		return qualificationInfoMap;
	}
	/**
	 * @param qualificationInfoMap the qualificationInfoMap to set
	 */
	public void setQualificationInfoMap(Map<String, String> qualificationInfoMap) {
		this.qualificationInfoMap = qualificationInfoMap;
	}
	/**
	 * @return the adminRewardInfoMap
	 */
	public Map<String, String> getAdminRewardInfoMap() {
		return adminRewardInfoMap;
	}
	/**
	 * @param adminRewardInfoMap the adminRewardInfoMap to set
	 */
	public void setAdminRewardInfoMap(Map<String, String> adminRewardInfoMap) {
		this.adminRewardInfoMap = adminRewardInfoMap;
	}
	/**
	 * @return the carTransactionsInfoMap
	 */
	public Map<String, String> getCarTransactionsInfoMap() {
		return carTransactionsInfoMap;
	}
	/**
	 * @param carTransactionsInfoMap the carTransactionsInfoMap to set
	 */
	public void setCarTransactionsInfoMap(Map<String, String> carTransactionsInfoMap) {
		this.carTransactionsInfoMap = carTransactionsInfoMap;
	}
	/**
	 * @return the telecomInfoMap
	 */
	public Map<String, String> getTelecomInfoMap() {
		return telecomInfoMap;
	}
	/**
	 * @param telecomInfoMap the telecomInfoMap to set
	 */
	public void setTelecomInfoMap(Map<String, String> telecomInfoMap) {
		this.telecomInfoMap = telecomInfoMap;
	}
	
	
}
