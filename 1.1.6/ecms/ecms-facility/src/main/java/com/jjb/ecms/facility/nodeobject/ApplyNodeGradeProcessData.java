package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 申请流程对象-评分过程记录
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:09:53
 * @version V1.0
 */
public class ApplyNodeGradeProcessData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8846927738427965085L;
	// 评分明细列表
	private List<RulePointResultItem> rulePointResultItemList = new ArrayList<RulePointResultItem>();

	public List<RulePointResultItem> getRulePointResultItemList() {
		return rulePointResultItemList;
	}

	public void setRulePointResultItemList(
			List<RulePointResultItem> rulePointResultItemList) {
		this.rulePointResultItemList = rulePointResultItemList;
	}
}
