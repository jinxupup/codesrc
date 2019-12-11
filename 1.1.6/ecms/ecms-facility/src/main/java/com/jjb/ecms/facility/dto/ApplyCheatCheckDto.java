/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description: 申请欺诈显示模型
 * @author -BigZ.Y
 * @date 2016年9月9日 下午5:20:13 
 */
public class ApplyCheatCheckDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cheatItem;//疑似欺诈项
	
	private String cheatRuleMemo;//欺诈风险信息调查规则描述
	
	private String cheatRuleMemoCN;//欺诈风险信息调查规则中文描述

	public String getCheatItem() {
		return cheatItem;
	}

	public void setCheatItem(String cheatItem) {
		this.cheatItem = cheatItem;
	}

	public String getCheatRuleMemo() {
		return cheatRuleMemo;
	}

	public void setCheatRuleMemo(String cheatRuleMemo) {
		this.cheatRuleMemo = cheatRuleMemo;
	}

	public String getCheatRuleMemoCN() {
		return cheatRuleMemoCN;
	}

	public void setCheatRuleMemoCN(String cheatRuleMemoCN) {
		this.cheatRuleMemoCN = cheatRuleMemoCN;
	}

}
