package com.jjb.ecms.service.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 
 * @Description: 如果联机外部的请求，可继承该类
 * @author JYData-R&D-HN
 * @date 2018年4月11日 下午9:35:09
 * @version V1.0
 */
public class BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	private String org;
	@XStreamOmitField
	private String tokenId;
	@XStreamOmitField
	private String appNo;//申请件编号
	@XStreamOmitField
	private String extHost;//渠道网络IP
	@XStreamOmitField
	private Integer extPort;//渠道网络端口
	@XStreamOmitField
	private String charset;//报文编码格式,多个编码已 “|”线隔开
	@XStreamOmitField
	private String lvMsgLength;//报文请求长度验证位
	@XStreamOmitField
	private String connectTimeOut;//超时时间
	
	@XStreamOmitField
	private String sourceSysId;//服务源发起系统ID
	@XStreamOmitField
	private String consumerId;//服务调用方系统ID
	@XStreamOmitField
	private String serviceCode;//服务码
	@XStreamOmitField
	private String serviceScene;//服务场景
	@XStreamOmitField
	private String ChannelId;//服务场景
	@XStreamOmitField
	private String branchId;//服务调用方系统机构号
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getExtHost() {
		return extHost;
	}
	public void setExtHost(String extHost) {
		this.extHost = extHost;
	}
	public Integer getExtPort() {
		return extPort;
	}
	public void setExtPort(Integer extPort) {
		this.extPort = extPort;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getLvMsgLength() {
		return lvMsgLength;
	}
	public void setLvMsgLength(String lvMsgLength) {
		this.lvMsgLength = lvMsgLength;
	}
	public String getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(String connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public String getSourceSysId() {
		return sourceSysId;
	}
	public void setSourceSysId(String sourceSysId) {
		this.sourceSysId = sourceSysId;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceScene() {
		return serviceScene;
	}
	public void setServiceScene(String serviceScene) {
		this.serviceScene = serviceScene;
	}
	public String getChannelId() {
		return ChannelId;
	}
	public void setChannelId(String channelId) {
		ChannelId = channelId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlineBasicRequest [tokenId=");
		builder.append(tokenId);
		builder.append(", appNo=");
		builder.append(appNo);
		builder.append(", extHost=");
		builder.append(extHost);
		builder.append(", extPort=");
		builder.append(extPort);
		builder.append(", charset=");
		builder.append(charset);
		builder.append(", lvMsgLength=");
		builder.append(lvMsgLength);
		builder.append(", connectTimeOut=");
		builder.append(connectTimeOut);
		builder.append("]");
		return builder.toString();
	}
	
}
