package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description: 流程定义DTO
 * @author -BigZ.Y
 * @date 2016年9月19日 下午3:01:20 
 */
public class ProcessDefinitionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String id;//ID
	
	private String proName;//流程名
	
	private String proKey;//流程定义的KEY
	
	private int proVersion;//流程定义的版本
	
	private String resourceName;//文件名称
	
	private String diagramResourceName;//图片名称
	
	private String deploymentId;//部署ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProKey() {
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}

	public int getProVersion() {
		return proVersion;
	}

	public void setProVersion(int proVersion) {
		this.proVersion = proVersion;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDiagramResourceName() {
		return diagramResourceName;
	}

	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	
}
