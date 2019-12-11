package com.jjb.acl.facility.enums.bus;

import com.jjb.unicorn.facility.meta.EnumInfo;

// FIXME 账户类型需包含3个维度，业务类型、入账币种、是否共享额度
@EnumInfo({
	"A|人民币独立基本信用账户",
	"B|美元独立基本信用账户",
	"C|人民币共享基本信用账户",
	"D|美元共享基本信用账户",
	"E|人民币独立小额贷款账户"
})
public enum AccountType {
	/**
	 * 人民币独立基本信用账户
	 */
	A("156", "A(人民币独立基本信用账户)",false, AcctTypeGroup.C),
	/**
	 * 美元独立基本信用账户
	 */
	B("840", "B(美元独立基本信用账户)",false, AcctTypeGroup.C),
	
	/**
	 * 人民币共享基本信用账户
	 */
	C("156", "C(人民币共享基本信用账户)",true, AcctTypeGroup.C),
	
	/**
	 * 美元共享基本信用账户
	 */
	D("840", "D(美元共享基本信用账户)",true, AcctTypeGroup.C),
	
	/**
	 * 人民币独立贷款账户
	 */
	E("156","E(人民币独立小额贷款账户)", false, AcctTypeGroup.L),
	;
	
	
	
	private String currencyCode;
	private boolean sharedCredit;
	private AcctTypeGroup acctTypeGroup;
	private String  description;

	private AccountType(String currencyCode, String description, boolean sharedCredit, AcctTypeGroup acctTypeGroup)
	{
		this.currencyCode = currencyCode;
		this.sharedCredit = sharedCredit;
		this.acctTypeGroup = acctTypeGroup;
		this.description=description;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
	public String  getDescription(){
		return description;
	}

	public boolean isSharedCredit() {
		return sharedCredit;
	}
	
	public AcctTypeGroup getAcctTypeGroup() {
		return acctTypeGroup;
	}

}
