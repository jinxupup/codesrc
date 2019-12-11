package com.jjb.ecms.service.dto.T1002;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;
import com.jjb.ecms.service.dto.T1005.T1005Req;

/**
 * 准入及老客户查询响应类
 * @author hp
 *
 */
public class T1002Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *  是否准入
	 * A：无风险
	 * B：低风险
	 * C：直接拒绝
	 * D：相同产品重复申请
	 */
	private String admittance;
	private String customerType;//客户类型,O：老客户 ; 	N：新客户
	private T1005Req apply;
	private String exitsProducts;

	public String getAdmittance() {
		return admittance;
	}
	public void setAdmittance(String admittance) {
		this.admittance = admittance;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public T1005Req getApply() {
		return apply;
	}
	public void setApply(T1005Req apply) {
		this.apply = apply;
	}
	public String getExitsProducts(){return exitsProducts;}
	public void setExitsProducts(String exitsProducts){this.exitsProducts=exitsProducts;}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("T1002Resp [admittance=");
		builder.append(admittance);
		builder.append(", customerType=");
		builder.append(customerType);
		builder.append(", exitsProducts=");
		builder.append(exitsProducts);
		builder.append("]");
		return builder.toString();
	}

}
