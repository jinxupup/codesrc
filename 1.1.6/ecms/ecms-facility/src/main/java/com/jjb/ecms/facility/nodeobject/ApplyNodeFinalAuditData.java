/**
 * 
 */
package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 终审操作节点对象
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:02:14
 * @version V1.0
 */
public class ApplyNodeFinalAuditData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4511478431962405146L;

	// 拒绝原因类型信息
	private List<String> resultReasonList = new ArrayList<String>();

	private String finalResult;// 终审结果
	private Boolean ifdemotionProduct;// 是否更改卡产品
	private Boolean innerCreditCheck;// 是否调用行内授信信息
	private String productCd;// 终审更改卡产品
	private String remark;// 备注
	private Boolean sendSmsForRefuse;// 是否发送拒绝短信
	private String  refuseCodeTypeSmall;////拒绝原因小类代码

	public String getRefuseCodeTypeSmall() {
		return refuseCodeTypeSmall;
	}

	public void setRefuseCodeTypeSmall(String refuseCodeTypeSmall) {
		this.refuseCodeTypeSmall = refuseCodeTypeSmall;
	}
	public Boolean getSendSmsForRefuse() {
		return sendSmsForRefuse;
	}

	public void setSendSmsForRefuse(Boolean sendSmsForRefuse) {
		this.sendSmsForRefuse = sendSmsForRefuse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getInnerCreditCheck() {
		return innerCreditCheck;
	}

	public void setInnerCreditCheck(Boolean innerCreditCheck) {
		this.innerCreditCheck = innerCreditCheck;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public Boolean getIfdemotionProduct() {
		return ifdemotionProduct;
	}

	public void setIfdemotionProduct(Boolean ifdemotionProduct) {
		this.ifdemotionProduct = ifdemotionProduct;
	}

	public List<String> getResultReasonList() {
		return resultReasonList;
	}

	public void setResultReasonList(List<String> resultReasonList) {
		this.resultReasonList = resultReasonList;
	}

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
