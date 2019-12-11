/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 附件额度计算模型
 * @author JYData-R&D-Big T.T
 * @date 2018年07月05日 下午5:33:02
 * @version V1.0  
 */

public class PointRuleAttachDetailDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String pointNo;//评分规则码
	
	private String pointName;//评分规则名称
	
	private String mixPointSet;//评分项

	private String mixPointSetDesc;//评分项描述
    
    private Integer defaultPoint;//评分项分值
    
    private BigDecimal weight;//权重
    
    private Integer pointValue;//子项分值
    
    private String ruleDesc;//子项描述
    
    private String markType;//计算类型
    
    private String pointSetIndexFirst;//区间值1
    
    private String pointSetIndexSecond;//区间值1

	/**
	 * @return the pointNo
	 */
	public String getPointNo() {
		return pointNo;
	}

	/**
	 * @param pointNo the pointNo to set
	 */
	public void setPointNo(String pointNo) {
		this.pointNo = pointNo;
	}

	/**
	 * @return the pointName
	 */
	public String getPointName() {
		return pointName;
	}

	/**
	 * @param pointName the pointName to set
	 */
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	/**
	 * @return the mixPointSet
	 */
	public String getMixPointSet() {
		return mixPointSet;
	}

	/**
	 * @param mixPointSet the mixPointSet to set
	 */
	public void setMixPointSet(String mixPointSet) {
		this.mixPointSet = mixPointSet;
	}

	/**
	 * @return the mixPointSetDesc
	 */
	public String getMixPointSetDesc() {
		return mixPointSetDesc;
	}

	/**
	 * @param mixPointSetDesc the mixPointSetDesc to set
	 */
	public void setMixPointSetDesc(String mixPointSetDesc) {
		this.mixPointSetDesc = mixPointSetDesc;
	}

	/**
	 * @return the defaultPoint
	 */
	public Integer getDefaultPoint() {
		return defaultPoint;
	}

	/**
	 * @param defaultPoint the defaultPoint to set
	 */
	public void setDefaultPoint(Integer defaultPoint) {
		this.defaultPoint = defaultPoint;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the pointValue
	 */
	public Integer getPointValue() {
		return pointValue;
	}

	/**
	 * @param pointValue the pointValue to set
	 */
	public void setPointValue(Integer pointValue) {
		this.pointValue = pointValue;
	}

	/**
	 * @return the ruleDesc
	 */
	public String getRuleDesc() {
		return ruleDesc;
	}

	/**
	 * @param ruleDesc the ruleDesc to set
	 */
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	/**
	 * @return the markType
	 */
	public String getMarkType() {
		return markType;
	}

	/**
	 * @param markType the markType to set
	 */
	public void setMarkType(String markType) {
		this.markType = markType;
	}

	/**
	 * @return the pointSetIndexFirst
	 */
	public String getPointSetIndexFirst() {
		return pointSetIndexFirst;
	}

	/**
	 * @param pointSetIndexFirst the pointSetIndexFirst to set
	 */
	public void setPointSetIndexFirst(String pointSetIndexFirst) {
		this.pointSetIndexFirst = pointSetIndexFirst;
	}

	/**
	 * @return the pointSetIndexSecond
	 */
	public String getPointSetIndexSecond() {
		return pointSetIndexSecond;
	}

	/**
	 * @param pointSetIndexSecond the pointSetIndexSecond to set
	 */
	public void setPointSetIndexSecond(String pointSetIndexSecond) {
		this.pointSetIndexSecond = pointSetIndexSecond;
	}
    
}
