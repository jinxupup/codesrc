package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 初审调查节点对象
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:17:59
 * @version V1.0
 */
public class ApplyNodeInquiryData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5995939691612080007L;

	// 存放页面核实信息的对象
	private ApplyCheckAffirmItem applyCheckAffirmItem;

	// 补件类型信息
	private List<String> patchBoltList = new ArrayList<String>();

	// 附卡补件类型信息
	private Map<String, List<String>> attachPatchBoltList = new HashMap<String, List<String>>();

	// 附卡信息核实信息
	private Map<String, ApplyCheckAffirmItem> attachCheckAffirmItem = new HashMap<String, ApplyCheckAffirmItem>();

	// 备注/备忘
	private String remark;

	private String memo;

	private String finalRemark;
	// 是否发送补件短信
	private Boolean sendSmsForPatchBolt;

	// 是否发送拒绝短信
	private Boolean sendSmsForRefuse;

	// 初审结果
	private String checkResult;

	// 拒绝原因
	private List<String> rejectReasonList = new ArrayList<String>();

	public Map<String, List<String>> getAttachPatchBoltList() {
		return attachPatchBoltList;
	}

	public void setAttachPatchBoltList(
			Map<String, List<String>> attachPatchBoltList) {
		this.attachPatchBoltList = attachPatchBoltList;
	}

	public Map<String, ApplyCheckAffirmItem> getAttachCheckAffirmItem() {
		return attachCheckAffirmItem;
	}

	public void setAttachCheckAffirmItem(
			Map<String, ApplyCheckAffirmItem> attachCheckAffirmItem) {
		this.attachCheckAffirmItem = attachCheckAffirmItem;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public ApplyCheckAffirmItem getApplyCheckAffirmItem() {
		return applyCheckAffirmItem;
	}

	public void setApplyCheckAffirmItem(
			ApplyCheckAffirmItem applyCheckAffirmItem) {
		this.applyCheckAffirmItem = applyCheckAffirmItem;
	}

	public List<String> getPatchBoltList() {
		return patchBoltList;
	}

	public void setPatchBoltList(List<String> patchBoltList) {
		this.patchBoltList = patchBoltList;
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

	public List<String> getRejectReasonList() {
		return rejectReasonList;
	}

	public void setRejectReasonList(List<String> rejectReasonList) {
		this.rejectReasonList = rejectReasonList;
	}

	public String getFinalRemark() {
		return finalRemark;
	}

	public void setFinalRemark(String finalRemark) {
		this.finalRemark = finalRemark;
	}

	public Boolean getSendSmsForPatchBolt() {
		return sendSmsForPatchBolt;
	}

	public void setSendSmsForPatchBolt(Boolean sendSmsForPatchBolt) {
		// TODO Auto-generated method stub
		this.sendSmsForPatchBolt = sendSmsForPatchBolt;
	}

	public Boolean getSendSmsForRefuse() {
		return sendSmsForRefuse;
	}

	public void setSendSmsForRefuse(Boolean sendSmsForRefuse) {
		this.sendSmsForRefuse = sendSmsForRefuse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
