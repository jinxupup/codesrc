package com.jjb.dmp.engine.model.enums;

public enum ActionType {

	C("AssignConstAction","常量分配"),
	P("AssignPropertyAction","变量分配");

	private String fullName;
	private String desc;

	ActionType(String fullName,String desc) {
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
