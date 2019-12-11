package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 申请流程对象-申请欺诈判定信息
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:01:37
 * @version V1.0
 */
public class ApplyNodeCheatCheckData implements Serializable {

	private static final long serialVersionUID = -6479203632706120010L;

	// 记录申请资料验证问题信息
	// Map<字段名称, 警告代码>
//	private Map<String, ApplyCheatCheckWarnCode> pplyNodeCheatCheckData = new HashMap<String, ApplyCheatCheckWarnCode>();
	private String riskClassic; // 风险等级
	private String serialNumber;//风控决策系统执行业务流水号
	private String stateCode;//风控系统执行状态代码
	private String stateDesc;//风控系统执行状态代码
	private String content;//自动审批结果
	private Integer creditScore;//信用分
	private Integer extScore;//外部评分
	private Integer otherScore;//其他评分
	private Integer finalScore;//最终评分
	private Integer scoreLevel;//评分等级
	private BigDecimal suggestAmt;//建议额度
	private BigDecimal baseLmt;//基础额度
	private BigDecimal incomeLmt;//收入水平额度
	private String interestRate;//利率
	private String unapprovedReasonCode;//未通过原因码
	private String unapprovedReasonDesc;//未通过原因描述
	private String riskContent;//决策系统返回的原Json数据
	private String fraudData;//反欺诈变量Json数据
	public String getRiskClassic() {
		return riskClassic;
	}
	public void setRiskClassic(String riskClassic) {
		this.riskClassic = riskClassic;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	public BigDecimal getBaseLmt() {
		return baseLmt;
	}
	public void setBaseLmt(BigDecimal baseLmt) {
		this.baseLmt = baseLmt;
	}
	public BigDecimal getIncomeLmt() {
		return incomeLmt;
	}
	public void setIncomeLmt(BigDecimal incomeLmt) {
		this.incomeLmt = incomeLmt;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
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
	public String getFraudData() {
		return fraudData;
	}
	public void setFraudData(String fraudData) {
		this.fraudData = fraudData;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplyNodeCheatCheckData [riskClassic=");
		builder.append(riskClassic);
		builder.append(", serialNumber=");
		builder.append(serialNumber);
		builder.append(", stateCode=");
		builder.append(stateCode);
		builder.append(", stateDesc=");
		builder.append(stateDesc);
		builder.append(", content=");
		builder.append(content);
		builder.append(", creditScore=");
		builder.append(creditScore);
		builder.append(", extScore=");
		builder.append(extScore);
		builder.append(", otherScore=");
		builder.append(otherScore);
		builder.append(", finalScore=");
		builder.append(finalScore);
		builder.append(", scoreLevel=");
		builder.append(scoreLevel);
		builder.append(", suggestAmt=");
		builder.append(suggestAmt);
		builder.append(", baseLmt=");
		builder.append(baseLmt);
		builder.append(", incomeLmt=");
		builder.append(incomeLmt);
		builder.append(", interestRate=");
		builder.append(interestRate);
		builder.append(", unapprovedReasonCode=");
		builder.append(unapprovedReasonCode);
		builder.append(", unapprovedReasonDesc=");
		builder.append(unapprovedReasonDesc);
		builder.append(", riskContent=");
		builder.append(riskContent);
		builder.append(", fraudData=");
		builder.append(fraudData);
		builder.append("]");
		return builder.toString();
	}
}
