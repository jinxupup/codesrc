package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;

/**
 * @Description: 评分明细
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:11:48
 * @version V1.0
 */
public class RulePointResultItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3641549011270360665L;
	public String objItem;
	public String ruleInfor;
	public String ruleResult;
	public String ruleSubId;

	public String getRuleSubId() {
		return ruleSubId;
	}

	public void setRuleSubId(String ruleSubId) {
		this.ruleSubId = ruleSubId;
	}

	public String getObjItem() {
		return objItem;
	}

	public void setObjItem(String objItem) {
		this.objItem = objItem;
	}

	public String getRuleInfor() {
		return ruleInfor;
	}

	public void setRuleInfor(String ruleInfor) {
		this.ruleInfor = ruleInfor;
	}

	public String getRuleResult() {
		return ruleResult;
	}

	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}
}
