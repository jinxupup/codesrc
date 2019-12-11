package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 补件节点对象
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:21:59
 * @version V1.0
 */
public class ApplyNodePatchBoltData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2671959372369897754L;

	// 已补件类型信息
	private List<String> patchBoltList = new ArrayList<String>();

	// 附卡补件类型信息
	private Map<String, List<String>> attachPatchBoltList = new HashMap<String, List<String>>();

	// 增加补件时间
	private Integer applyPbAddDays;

	// 备注/备忘
	private String remark;

	private String memo;

	public Map<String, List<String>> getAttachPatchBoltList() {
		return attachPatchBoltList;
	}

	public void setAttachPatchBoltList(
			Map<String, List<String>> attachPatchBoltList) {
		this.attachPatchBoltList = attachPatchBoltList;
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

	public List<String> getPatchBoltList() {
		return patchBoltList;
	}

	public void setPatchBoltList(List<String> patchBoltList) {
		this.patchBoltList = patchBoltList;
	}

	public Integer getApplyPbAddDays() {
		return applyPbAddDays;
	}

	public void setApplyPbAddDays(Integer applyPbAddDays) {
		this.applyPbAddDays = applyPbAddDays;
	}

}
