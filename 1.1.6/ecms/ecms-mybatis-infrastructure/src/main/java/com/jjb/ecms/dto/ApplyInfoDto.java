package com.jjb.ecms.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmExtTriggerRules;

/**
 * 申请件信息 包括工作流阶段
 * 
 * @author JYData-R&D-Big Star
 * @date 2015年9月15日 下午8:55:01
 * @version V1.0
 */
public class ApplyInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String appNo;// 申请件编号
	private String remark;// 备注
	private String retrialFlag;// 重审标志
	private String oldAppNo;// 重审原appNo
	private String ipadApplyFalg;//
	private String batchInputFalg;// 申请件批量导入的标志
	// 申请件
	private TmAppMain tmAppMain;// 申请业务主表
	private TmAppAudit tmAppAudit;//进件审计记录
	private TmAppPrimCardInfo tmAppPrimCardInfo;// 申请件卡片、银行专用栏及其其他信息表
	private TmAppPrimAnnexEvi tmAppPrimAnnexEvi;// 申请附件表
	private Map<String, TmAppCustInfo> tmAppCustInfoMap;// 申请主附卡申请人信息表
	private List<TmAppCustInfo> tmAppCustInfoList;// 申请主附卡申请人信息表
	private Map<String, TmAppContact> tmAppContactMap;// 联系人信息表
	private List<TmAppHistory> tmAppHistoryList;// 历史申请审批记录
	private Map<String, Serializable> tmAppNodeInfoRecordMap; // 节点信息记录表
	private Map<String, List<TmAppMemo>> tmAppMemoMapAll;// 全部滴备注备忘
	private Map<String, TmAppMemo> tmAppMemoMapLast;// 各节点最新滴备注备忘
	private TmExtRiskInput tmExtRiskInput;
	private Map<String, TmExtTriggerRules> tmExtTriggerRulesMap;
//	private TmAppInstalLoan tmAppInstalLoan;// 信用卡分期贷款表	
//	private TmAppCreditLoan tmAppCreditLoan;//虚拟卡(信贷)借贷信息
//	private TmAppOrderInfo tmAppOrderInfo;//订单信息
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRetrialFlag() {
		return retrialFlag;
	}
	public void setRetrialFlag(String retrialFlag) {
		this.retrialFlag = retrialFlag;
	}
	public String getOldAppNo() {
		return oldAppNo;
	}
	public void setOldAppNo(String oldAppNo) {
		this.oldAppNo = oldAppNo;
	}
	public String getIpadApplyFalg() {
		return ipadApplyFalg;
	}
	public void setIpadApplyFalg(String ipadApplyFalg) {
		this.ipadApplyFalg = ipadApplyFalg;
	}
	public String getBatchInputFalg() {
		return batchInputFalg;
	}
	public void setBatchInputFalg(String batchInputFalg) {
		this.batchInputFalg = batchInputFalg;
	}
	public TmAppMain getTmAppMain() {
		return tmAppMain;
	}
	public void setTmAppMain(TmAppMain tmAppMain) {
		this.tmAppMain = tmAppMain;
	}
	public TmAppAudit getTmAppAudit() {
		return tmAppAudit;
	}
	public void setTmAppAudit(TmAppAudit tmAppAudit) {
		this.tmAppAudit = tmAppAudit;
	}
	public TmAppPrimCardInfo getTmAppPrimCardInfo() {
		return tmAppPrimCardInfo;
	}
	public void setTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) {
		this.tmAppPrimCardInfo = tmAppPrimCardInfo;
	}
	public TmAppPrimAnnexEvi getTmAppPrimAnnexEvi() {
		return tmAppPrimAnnexEvi;
	}
	public void setTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) {
		this.tmAppPrimAnnexEvi = tmAppPrimAnnexEvi;
	}
	public Map<String, TmAppCustInfo> getTmAppCustInfoMap() {
		return tmAppCustInfoMap;
	}
	public void setTmAppCustInfoMap(Map<String, TmAppCustInfo> tmAppCustInfoMap) {
		this.tmAppCustInfoMap = tmAppCustInfoMap;
	}
	public List<TmAppCustInfo> getTmAppCustInfoList() {
		return tmAppCustInfoList;
	}
	public void setTmAppCustInfoList(List<TmAppCustInfo> tmAppCustInfoList) {
		this.tmAppCustInfoList = tmAppCustInfoList;
	}
	public Map<String, TmAppContact> getTmAppContactMap() {
		return tmAppContactMap;
	}
	public void setTmAppContactMap(Map<String, TmAppContact> tmAppContactMap) {
		this.tmAppContactMap = tmAppContactMap;
	}
	public List<TmAppHistory> getTmAppHistoryList() {
		return tmAppHistoryList;
	}
	public void setTmAppHistoryList(List<TmAppHistory> tmAppHistoryList) {
		this.tmAppHistoryList = tmAppHistoryList;
	}
	public Map<String, Serializable> getTmAppNodeInfoRecordMap() {
		return tmAppNodeInfoRecordMap;
	}
	public void setTmAppNodeInfoRecordMap(Map<String, Serializable> tmAppNodeInfoRecordMap) {
		this.tmAppNodeInfoRecordMap = tmAppNodeInfoRecordMap;
	}
	public Map<String, List<TmAppMemo>> getTmAppMemoMapAll() {
		return tmAppMemoMapAll;
	}
	public void setTmAppMemoMapAll(Map<String, List<TmAppMemo>> tmAppMemoMapAll) {
		this.tmAppMemoMapAll = tmAppMemoMapAll;
	}
	public Map<String, TmAppMemo> getTmAppMemoMapLast() {
		return tmAppMemoMapLast;
	}
	public void setTmAppMemoMapLast(Map<String, TmAppMemo> tmAppMemoMapLast) {
		this.tmAppMemoMapLast = tmAppMemoMapLast;
	}
	public TmExtRiskInput getTmExtRiskInput() {
		return tmExtRiskInput;
	}
	public void setTmExtRiskInput(TmExtRiskInput tmExtRiskInput) {
		this.tmExtRiskInput = tmExtRiskInput;
	}
	public Map<String, TmExtTriggerRules> getTmExtTriggerRulesMap() {
		return tmExtTriggerRulesMap;
	}
	public void setTmExtTriggerRulesMap(Map<String, TmExtTriggerRules> tmExtTriggerRulesMap) {
		this.tmExtTriggerRulesMap = tmExtTriggerRulesMap;
	}
//	public TmAppInstalLoan getTmAppInstalLoan() {
//		return tmAppInstalLoan;
//	}
//	public void setTmAppInstalLoan(TmAppInstalLoan tmAppInstalLoan) {
//		this.tmAppInstalLoan = tmAppInstalLoan;
//	}
//	public TmAppCreditLoan getTmAppCreditLoan() {
//		return tmAppCreditLoan;
//	}
//	public void setTmAppCreditLoan(TmAppCreditLoan tmAppCreditLoan) {
//		this.tmAppCreditLoan = tmAppCreditLoan;
//	}
//	public TmAppOrderInfo getTmAppOrderInfo() {
//		return tmAppOrderInfo;
//	}
//	public void setTmAppOrderInfo(TmAppOrderInfo tmAppOrderInfo) {
//		this.tmAppOrderInfo = tmAppOrderInfo;
//	}
			
}