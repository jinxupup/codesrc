package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class ApplyRiskInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String riskClassic; // 风险等级
	private String serialNumber;//风控决策系统执行业务流水号
	private String stateCode;//风控系统执行状态代码
	private String stateDesc;//风控系统执行状态代码
	private String content;//自动审批结果
	private String lookPboc;  //人行征信报告
	private Integer creditScore;//信用分
	private Integer extScore;//外部评分
	private Integer otherScore;//其他评分
	private Integer finalScore;//最终评分
	private Integer scoreLevel;//评分等级
	private BigDecimal suggestAmt;//建议额度
	private String unapprovedReasonCode;//未通过原因码
	private String unapprovedReasonDesc;//未通过原因描述
	private String riskContent;//决策系统返回的原Json数据
	private Map<String,Object> thirdDataProduct; //第三方数据产品
	private Map<String,Object> hitTheRule;  //命中规则
	private Map<String,Object> trackingProcess;//跟踪流程
	private Map<String, Object> showMap;     //决策信息展示项

	public String getRiskClassic() {
		return riskClassic;
	}
	public void setRiskClassic(String riskClassic) {
		this.riskClassic = riskClassic;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
	public Integer getExtScore() {
		return extScore;
	}
	public void setExtScore(Integer extScore) {
		this.extScore = extScore;
	}
	public Integer getOtherScore() {
		return otherScore;
	}
	public void setOtherScore(Integer otherScore) {
		this.otherScore = otherScore;
	}
	public Integer getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
	public Integer getScoreLevel() {
		return scoreLevel;
	}
	public void setScoreLevel(Integer scoreLevel) {
		this.scoreLevel = scoreLevel;
	}
	public BigDecimal getSuggestAmt() {
		return suggestAmt;
	}
	public void setSuggestAmt(BigDecimal suggestAmt) {
		this.suggestAmt = suggestAmt;
	}
	public String getUnapprovedReasonCode() {
		return unapprovedReasonCode;
	}
	public void setUnapprovedReasonCode(String unapprovedReasonCode) {
		this.unapprovedReasonCode = unapprovedReasonCode;
	}
	public String getUnapprovedReasonDesc() {
		return unapprovedReasonDesc;
	}
	public void setUnapprovedReasonDesc(String unapprovedReasonDesc) {
		this.unapprovedReasonDesc = unapprovedReasonDesc;
	}
	public String getRiskContent() {
		return riskContent;
	}
	public void setRiskContent(String riskContent) {
		this.riskContent = riskContent;
	}
	public Map getThirdDataProduct() {
		return thirdDataProduct;
	}
	public void setThirdDataProduct(Map thirdDataProduct) {
		this.thirdDataProduct = thirdDataProduct;
	}
	public Map getHitTheRule() {
		return hitTheRule;
	}
	public void setHitTheRule(Map hitTheRule) {
		this.hitTheRule = hitTheRule;
	}
	public Map getTrackingProcess() {
		return trackingProcess;
	}
	public void setTrackingProcess(Map trackingProcess) {
		this.trackingProcess = trackingProcess;
	}
	public String getLookPboc() {
		return lookPboc;
	}
	public void setLookPboc(String lookPboc) {
		this.lookPboc = lookPboc;
	}
	public Map<String, Object> getShowMap() {
		return showMap;
	}
	public void setShowMap(Map showMap) {
		this.showMap = showMap;
	}
}