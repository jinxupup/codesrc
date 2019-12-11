package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 部署流程定义DTO
 * @author -BigZ.Y
 * @date 2016年9月19日 下午3:01:20 
 */
public class DeploymentDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//ID
	
	private String deploymentName;//部署名
	
	private Date deploymentTime;//发布时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeploymentName() {
		return deploymentName;
	}

	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	public Date getDeploymentTime() {
		return deploymentTime;
	}

	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	

	
}
