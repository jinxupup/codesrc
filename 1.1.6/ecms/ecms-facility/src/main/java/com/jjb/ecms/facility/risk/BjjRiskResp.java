package com.jjb.ecms.facility.risk;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 调用风控输出变量集
 * 
 * @Description:
 * @author JYData-R&D-HN
 * @date 2018年20月26日 下午7:57:18
 * @version V1.0
 */
public class BjjRiskResp implements Serializable {
	private static final long serialVersionUID = 1L;

	private String serialNumber;//决策系统流水号
	private String stateCode;// 服务错误码
	private String stateDesc;// 错误码描述
	private String score;//最终评分
	private String result;//决策结果
	private BigDecimal suggestAmt;//建议额度
	private String reasonCode;//建议额度
	private String reasonDesc;// 未通过原因描述
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public BigDecimal getSuggestAmt() {
		return suggestAmt;
	}
	public void setSuggestAmt(BigDecimal suggestAmt) {
		this.suggestAmt = suggestAmt;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BjjRiskRes [serialNumber=");
		builder.append(serialNumber);
		builder.append(", stateCode=");
		builder.append(stateCode);
		builder.append(", stateDesc=");
		builder.append(stateDesc);
		builder.append(", score=");
		builder.append(score);
		builder.append(", result=");
		builder.append(result);
		builder.append(", suggestAmt=");
		builder.append(suggestAmt);
		builder.append(", reasonCode=");
		builder.append(reasonCode);
		builder.append(", reasonDesc=");
		builder.append(reasonDesc);
		builder.append("]");
		return builder.toString();
	}
	
	
}