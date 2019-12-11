package com.jjb.acl.facility.enums.bus.loan;


/**
 * 
 * @ClassName ApsLoanRegStatus
 * @Description TODO ECMS分期注册状态
 * @author H.N
 * @Date 2018年1月9日 上午10:38:02
 * @version 1.0.0
 */
public enum LoanRegStatus {

	/**
	 * 待审批
	 */
	U("待审批"),
	/**
	 * 申请拒绝
	 */
	D("申请拒绝"),
	/**
	 * 待放款
	 */
	A("待放款"),
	/**
	 * 放款失败
	 */
	F("放款失败"),
	/**
	 * 放款成功
	 */
	S("放款成功"),
	/**
	 * 放款超时
	 */
	O("放款超时"),
	/**
	 * 分期申请失败
	 */
	I("分期申请失败"),
	/**
	 * 建卡失败
	 */
	J("建卡失败"),
	/**
	 * 分期失效
	 */
	T("分期失效"),
	/**
	 * 分期撤销
	 */
	V("分期撤销"),
	/**
	 * 批量放款
	 */
	B("批量放款");
	public String desc;
	private LoanRegStatus(String desc){
		this.desc=desc;
	}
}
