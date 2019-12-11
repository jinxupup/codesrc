package com.jjb.ecms.facility.rcs;

import java.io.Serializable;

/**
 * 命中规则实体数据
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2018年3月1日 下午8:25:20
 * @version V1.0
 */
public class HitRuleDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hitRuleCode;//命中规则的代码
	
	private String hitRuleDisc;//命中规则的详细描述

	public String getHitRuleCode() {
		return hitRuleCode;
	}

	public void setHitRuleCode(String hitRuleCode) {
		this.hitRuleCode = hitRuleCode;
	}

	public String getHitRuleDisc() {
		return hitRuleDisc;
	}

	public void setHitRuleDisc(String hitRuleDisc) {
		this.hitRuleDisc = hitRuleDisc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HitRuleDto [hitRuleCode=");
		builder.append(hitRuleCode);
		builder.append(", hitRuleDisc=");
		builder.append(hitRuleDisc);
		builder.append("]");
		return builder.toString();
	}
}
