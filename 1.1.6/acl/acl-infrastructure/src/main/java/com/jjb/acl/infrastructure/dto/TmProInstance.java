package com.jjb.acl.infrastructure.dto;

import java.io.Serializable;

/**
 * @ClassName TmProInstance
 * Company jydata-tech
 * @Description TODO
 * Author zhangwenlu
 * Date 2018/11/20 19:13
 * Version 1.0
 */
public class TmProInstance implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer processId;
    private String systemType;

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
}
