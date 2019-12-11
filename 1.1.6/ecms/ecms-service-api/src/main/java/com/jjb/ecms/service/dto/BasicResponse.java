package com.jjb.ecms.service.dto;

import java.io.Serializable;

/**
 * 
 * @Description: 调用外部交易后的响应信息
 * @author JYData-R&D-HN
 * @date 2018年4月11日 下午9:35:09
 * @version V1.0
 */
public class BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String serviceSn;
	private String serviceId;
	private String org;
	private String opId;
	private String channelId;
	private String requstTime;
	private String resServiceSn;
	private String resServiceTime;
	private String versionId;
	private String tonkenId;
	private String status;
	private String ReturnCode;
	private String ReturnMsg;
	private String sourceXmlResp;
	public String getReturnCode() {
		return ReturnCode;
	}

	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}

	public String getReturnMsg() {
		return ReturnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}
	public String getServiceSn() {
		return serviceSn;
	}
	public void setServiceSn(String serviceSn) {
		this.serviceSn = serviceSn;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getOpId() {
		return opId;
	}
	public void setOpId(String opId) {
		this.opId = opId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getRequstTime() {
		return requstTime;
	}
	public void setRequstTime(String requstTime) {
		this.requstTime = requstTime;
	}
	public String getResServiceSn() {
		return resServiceSn;
	}
	public void setResServiceSn(String resServiceSn) {
		this.resServiceSn = resServiceSn;
	}
	public String getResServiceTime() {
		return resServiceTime;
	}
	public void setResServiceTime(String resServiceTime) {
		this.resServiceTime = resServiceTime;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getTokenId() {
		return tonkenId;
	}
	public void setTokenId(String tonkenId) {
		this.tonkenId = tonkenId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceXmlResp() {
		return sourceXmlResp;
	}
	public void setSourceXmlResp(String sourceXmlResp) {
		this.sourceXmlResp = sourceXmlResp;
	}

}
