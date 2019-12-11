package com.jjb.dmp.engine.model.enums;

public enum RuleSetType {
	
	S("SimpleRuleSet","简单规则集"),
	D("DecisionTable","决策表");

	private String fullName;
	private String desc;

	RuleSetType(String fullName,String desc) {
        this.fullName = fullName;
        this.desc = desc;
    }

    public String getFullName() {
        return this.fullName;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
}
