package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 申请流程对象-电话调查结果信息
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:54:19
 * @version V1.0
 */
public class ApplyNodeTelCheckBisicData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2630776281172930068L;

	// 电话信息列表
	private List<ApplyTelInquiryRecordItem> telInquiryRecordItemList = new ArrayList<ApplyTelInquiryRecordItem>();
	// 电话确认信息列表
	private List<ApplyTelVerifyRecordItem> telVerifyRecordItemList = new ArrayList<ApplyTelVerifyRecordItem>();
	//电话调查核身问题
	private List<AllpyTelCheckRecordItem> idCheckRecordItem = new ArrayList<AllpyTelCheckRecordItem>();
	//必问问题
	private List<AllpyTelCheckRecordItem> mustCheckRecordItem = new ArrayList<AllpyTelCheckRecordItem>();
	//选核问题
	private List<AllpyTelCheckRecordItem> choiceCheckRecordItem = new ArrayList<AllpyTelCheckRecordItem>();

	private Map<String, Serializable> tmAppPrimApplicantInfo;

	private Map<String, Serializable> tmAppAttachApplierInfo;

	private Map<String, Map<String, Serializable>> tmAppContacts = new HashMap<String, Map<String, Serializable>>(); // 主卡联系人信息

	private Map<String, Map<String, Serializable>> tmAppAttachApplierInfos = new HashMap<String, Map<String, Serializable>>();;

	private Map<String, Serializable> tmAppPrimCardInfo;

	private String remark; // 备注

	private String memo; // 备忘

	private String result; // 调查结果
	
	private String[] refuseCodeList;//拒绝原因

	public ApplyNodeTelCheckBisicData() {
	}
	
	/**
	 * @return the refuseCodeList
	 */
	public String[] getRefuseCodeList() {
		return refuseCodeList;
	}

	/**
	 * @param refuseCodeList the refuseCodeList to set
	 */
	public void setRefuseCodeList(String[] refuseCodeList) {
		this.refuseCodeList = refuseCodeList;
	}

	public Map<String, Map<String, Serializable>> getTmAppContacts() {
		return tmAppContacts;
	}

	public void setTmAppContacts(
			Map<String, Map<String, Serializable>> tmAppContacts) {
		this.tmAppContacts = tmAppContacts;
	}

	public List<ApplyTelInquiryRecordItem> getTelInquiryRecordItemList() {
		return telInquiryRecordItemList;
	}

	public void setTelInquiryRecordItemList(
			List<ApplyTelInquiryRecordItem> telInquiryRecordItemList) {
		this.telInquiryRecordItemList = telInquiryRecordItemList;
	}

	public List<ApplyTelVerifyRecordItem> getTelVerifyRecordItemList() {
		return telVerifyRecordItemList;
	}

	public void setTelVerifyRecordItemList(
			List<ApplyTelVerifyRecordItem> telVerifyRecordItemList) {
		this.telVerifyRecordItemList = telVerifyRecordItemList;
	}
	public List<AllpyTelCheckRecordItem> getIdCheckRecordItem() {
		return idCheckRecordItem;
	}

	public void setIdCheckRecordItem(
			List<AllpyTelCheckRecordItem> idCheckRecordItem) {
		this.idCheckRecordItem = idCheckRecordItem;
	}

	public List<AllpyTelCheckRecordItem> getMustCheckRecordItem() {
		return mustCheckRecordItem;
	}

	public void setMustCheckRecordItem(
			List<AllpyTelCheckRecordItem> mustCheckRecordItem) {
		this.mustCheckRecordItem = mustCheckRecordItem;
	}

	public List<AllpyTelCheckRecordItem> getChoiceCheckRecordItem() {
		return choiceCheckRecordItem;
	}

	public void setChoiceCheckRecordItem(
			List<AllpyTelCheckRecordItem> choiceCheckRecordItem) {
		this.choiceCheckRecordItem = choiceCheckRecordItem;
	}

	public Map<String, Serializable> getTmAppPrimApplicantInfo() {
		return tmAppPrimApplicantInfo;
	}

	public void setTmAppPrimApplicantInfo(
			Map<String, Serializable> tmAppPrimApplicantInfo) {
		this.tmAppPrimApplicantInfo = tmAppPrimApplicantInfo;
	}

	public Map<String, Serializable> getTmAppAttachApplierInfo() {
		return tmAppAttachApplierInfo;
	}

	public void setTmAppAttachApplierInfo(
			Map<String, Serializable> tmAppAttachApplierInfo) {
		this.tmAppAttachApplierInfo = tmAppAttachApplierInfo;
	}

	public Map<String, Map<String, Serializable>> getTmAppAttachApplierInfos() {
		return tmAppAttachApplierInfos;
	}

	public void setTmAppAttachApplierInfos(
			Map<String, Map<String, Serializable>> tmAppAttachApplierInfos) {
		this.tmAppAttachApplierInfos = tmAppAttachApplierInfos;
	}

	public Map<String, Serializable> getTmAppPrimCardInfo() {
		return tmAppPrimCardInfo;
	}

	public void setTmAppPrimCardInfo(Map<String, Serializable> tmAppPrimCardInfo) {
		this.tmAppPrimCardInfo = tmAppPrimCardInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
