package com.jjb.ecms.service.dto.T9000;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 推送审批结论与风控结论请求参数
 * @author hp
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class T9000Req extends BasicRequest implements Serializable {
	@XStreamOmitField
	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="T9000";
	@XStreamOmitField
	public static final String servIdCcif="2004800067";
	
	public String AppNo;//申请件编号
	private String Buscore;//征信评分xxx
	private String FinalTotScore;// 风险评分
	private String FinalAuditresult;// 决策结果
	private String RejectReason;//拒绝原因（征信规则拒绝/综合评分拒绝）
	private String RiskLevel1;// 风险等级1xxx
	private String RiskLevel2;// 风险等级2xxx
	private String FinalCreditlimt;//申请人最终授信额度xxx
	private String FinalOtherLimit;//申请人他行最高授信额度
	private String AdjustIndex;// 申请人最终调整系数
	private String AuditisriskJj;// 是否触犯51电核规则
	private String VersionCode;// 版本号
	private String BrWorkPlace;//(百融)客户收入
	private String BrEdulevel;//(百融)学历-学信网
	private String BrScore;//百融评分
	private String JjAuditisrisk;//行内是否信审拒绝
	private String AuditReason;//九江返回人工信审拒绝原因
	private String PushType;//推送类型

}
