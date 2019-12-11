package com.jjb.dmp.engine.model.enums;

public enum OptionType {
	A("自定义输入"), 
	D("业务字典"),
	E("枚举"),
	T("数据表");
	
	private String value;
	
	OptionType(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
