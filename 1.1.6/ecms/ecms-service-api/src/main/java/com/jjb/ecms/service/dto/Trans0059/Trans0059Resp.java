package com.jjb.ecms.service.dto.Trans0059;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 * @Description: 接收推送审批结论与风控结论响应数据
 * @author hp
 *
 */
@Data
public class Trans0059Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public String ProductCd;
	public String AppNo;
	public String IdType;
	public String IdNo;
	public String EmbName;
	public String BscSuppInd;
	public String CreditLimit;
	public String CompanyName;
	public String ReasonCd;
	public String ReasonDesc;
	public String IssueBranch;
	public String CardNo;
	public String LoanRegId;
	public String LoanFailureReasonCode;
	public String LoanFailureReasonDesc;
	public String LoanInitTerm;
	public String LoanFeeMethod;
	public String LoanInitPrin;
	public String LoanFixedPmtPrin;
	public String LoanFirstTermPrin;
	public String LoanFinalTermPrin;
	public String LoanInitFee1;
	public String LoanFixedFee1;
	public String LoanFirstTermFee1;
	public String LoanFinalTermFee1;
	public String SendMode;
	public String SendBankName;
	public String SendBankBranch;
	public String SendBankAcctNo;
	public String SendBankAcctName;
	public String LoanTarget;
	public String ReturnCode;
	public String ReturnMsg;

}
