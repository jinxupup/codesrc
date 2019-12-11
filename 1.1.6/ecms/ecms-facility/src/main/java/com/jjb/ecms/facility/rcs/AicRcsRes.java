package com.jjb.ecms.facility.rcs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 调用风控输出变量集
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2018年1月26日 下午7:57:18
 * @version V1.0
 */
public class AicRcsRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reqSeq;//请求流水
	private String reqTimestamp;//请求时间戳
	private String respSeq;//响应流水
	private String receiveTimestamp;//服务响应时间戳
	private String respTimestamp;//返回值时间戳
	private String code;//服务错误码
	private String desc;//错误码描述
	private List<HitRuleDto> hitRuleDtoList;//命中规则清单列表
	
	private String content;//自动审批结果 -自动通过 DC:自动拒绝 MF:转人工欺诈调查 RF:转人工审核
	private String unapprovedReasonCode;//未通过原因码 -
	private String unapprovedReasonDesc;//未通过原因描述 -
	private String admissionFailReasonCode;//准入失败原因码表 -
	private String admissionFailReasonDesc;//准入失败原因描述表-
	private String fraudReasonCode;//欺诈原因编码表 -
	private String fraudReasonDesc;//欺诈原因详情表 -
	private String creditReasonCode;//信用规则原因码表 -益博睿
	private String creditReasonDesc;//信用规则原因描述表-益博睿
	private Float creditScore;//信用分 -
	private Integer creditScoreLevel;//评分等级 -评分的细项
	private String scoreElement;//评分项 -细项的取值
	private String scoreOutcomde;//评分项取值 -细项的得分
	private String scoreValue;//评分项得分 -
	private BigDecimal suggestAmt;//建议额度 -
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getReqTimestamp() {
		return reqTimestamp;
	}
	public void setReqTimestamp(String reqTimestamp) {
		this.reqTimestamp = reqTimestamp;
	}
	public String getRespSeq() {
		return respSeq;
	}
	public void setRespSeq(String respSeq) {
		this.respSeq = respSeq;
	}
	public String getReceiveTimestamp() {
		return receiveTimestamp;
	}
	public void setReceiveTimestamp(String receiveTimestamp) {
		this.receiveTimestamp = receiveTimestamp;
	}
	public String getRespTimestamp() {
		return respTimestamp;
	}
	public void setRespTimestamp(String respTimestamp) {
		this.respTimestamp = respTimestamp;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<HitRuleDto> getHitRuleDtoList() {
		return hitRuleDtoList;
	}
	public void setHitRuleDtoList(List<HitRuleDto> hitRuleDtoList) {
		this.hitRuleDtoList = hitRuleDtoList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getAdmissionFailReasonCode() {
		return admissionFailReasonCode;
	}
	public void setAdmissionFailReasonCode(String admissionFailReasonCode) {
		this.admissionFailReasonCode = admissionFailReasonCode;
	}
	public String getAdmissionFailReasonDesc() {
		return admissionFailReasonDesc;
	}
	public void setAdmissionFailReasonDesc(String admissionFailReasonDesc) {
		this.admissionFailReasonDesc = admissionFailReasonDesc;
	}
	public String getFraudReasonCode() {
		return fraudReasonCode;
	}
	public void setFraudReasonCode(String fraudReasonCode) {
		this.fraudReasonCode = fraudReasonCode;
	}
	public String getFraudReasonDesc() {
		return fraudReasonDesc;
	}
	public void setFraudReasonDesc(String fraudReasonDesc) {
		this.fraudReasonDesc = fraudReasonDesc;
	}
	public String getCreditReasonCode() {
		return creditReasonCode;
	}
	public void setCreditReasonCode(String creditReasonCode) {
		this.creditReasonCode = creditReasonCode;
	}
	public String getCreditReasonDesc() {
		return creditReasonDesc;
	}
	public void setCreditReasonDesc(String creditReasonDesc) {
		this.creditReasonDesc = creditReasonDesc;
	}
	public Float getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(Float creditScore) {
		this.creditScore = creditScore;
	}
	public Integer getCreditScoreLevel() {
		return creditScoreLevel;
	}
	public void setCreditScoreLevel(Integer creditScoreLevel) {
		this.creditScoreLevel = creditScoreLevel;
	}
	public String getScoreElement() {
		return scoreElement;
	}
	public void setScoreElement(String scoreElement) {
		this.scoreElement = scoreElement;
	}
	public String getScoreOutcomde() {
		return scoreOutcomde;
	}
	public void setScoreOutcomde(String scoreOutcomde) {
		this.scoreOutcomde = scoreOutcomde;
	}
	public String getScoreValue() {
		return scoreValue;
	}
	public void setScoreValue(String scoreValue) {
		this.scoreValue = scoreValue;
	}
	public BigDecimal getSuggestAmt() {
		return suggestAmt;
	}
	public void setSuggestAmt(BigDecimal suggestAmt) {
		this.suggestAmt = suggestAmt;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AicRcsRes [reqSeq=");
		builder.append(reqSeq);
		builder.append(", reqTimestamp=");
		builder.append(reqTimestamp);
		builder.append(", respSeq=");
		builder.append(respSeq);
		builder.append(", receiveTimestamp=");
		builder.append(receiveTimestamp);
		builder.append(", respTimestamp=");
		builder.append(respTimestamp);
		builder.append(", code=");
		builder.append(code);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", hitRuleDtoList=");
		builder.append(hitRuleDtoList);
		builder.append(", content=");
		builder.append(content);
		builder.append(", unapprovedReasonCode=");
		builder.append(unapprovedReasonCode);
		builder.append(", unapprovedReasonDesc=");
		builder.append(unapprovedReasonDesc);
		builder.append(", admissionFailReasonCode=");
		builder.append(admissionFailReasonCode);
		builder.append(", admissionFailReasonDesc=");
		builder.append(admissionFailReasonDesc);
		builder.append(", fraudReasonCode=");
		builder.append(fraudReasonCode);
		builder.append(", fraudReasonDesc=");
		builder.append(fraudReasonDesc);
		builder.append(", creditReasonCode=");
		builder.append(creditReasonCode);
		builder.append(", creditReasonDesc=");
		builder.append(creditReasonDesc);
		builder.append(", creditScore=");
		builder.append(creditScore);
		builder.append(", creditScoreLevel=");
		builder.append(creditScoreLevel);
		builder.append(", scoreElement=");
		builder.append(scoreElement);
		builder.append(", scoreOutcomde=");
		builder.append(scoreOutcomde);
		builder.append(", scoreValue=");
		builder.append(scoreValue);
		builder.append(", suggestAmt=");
		builder.append(suggestAmt);
		builder.append("]");
		return builder.toString();
	}
	
}
