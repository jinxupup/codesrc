package com.jjb.acl.facility.enums.bus.loan;

public enum LendingType {
	
	O("实时放款"),
	
	B("批量放款");
	
	public String state;
	
	public String lab;
	
	private LendingType(String lab){
		this.state=name();
		this.lab=lab;
	}

}
