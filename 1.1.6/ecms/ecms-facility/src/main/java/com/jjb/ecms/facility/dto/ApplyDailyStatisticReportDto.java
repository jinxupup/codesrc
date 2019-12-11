package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

public class ApplyDailyStatisticReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;//日期
	private String applyTime;//自动审批次数
	private String sugSuccessTime;//建议通过次数
	private String sugSuccessTimePercent;//建议通过占比
	private String sugRejectTime;//建议拒绝次数
	private String sugRejectTimePercent;//建议拒绝占比
	private String sugPersonTime;//建议人工处理次数
	private String sugPersonTimePercent;//建议人工处理占比 
	
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the applyTime
	 */
	public String getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return the sugSuccessTime
	 */
	public String getSugSuccessTime() {
		return sugSuccessTime;
	}
	/**
	 * @param sugSuccessTime the sugSuccessTime to set
	 */
	public void setSugSuccessTime(String sugSuccessTime) {
		this.sugSuccessTime = sugSuccessTime;
	}
	/**
	 * @return the sugSuccessTimePercent
	 */
	public String getSugSuccessTimePercent() {
		return sugSuccessTimePercent;
	}
	/**
	 * @param sugSuccessTimePercent the sugSuccessTimePercent to set
	 */
	public void setSugSuccessTimePercent(String sugSuccessTimePercent) {
		this.sugSuccessTimePercent = sugSuccessTimePercent;
	}
	/**
	 * @return the sugRejectTime
	 */
	public String getSugRejectTime() {
		return sugRejectTime;
	}
	/**
	 * @param sugRejectTime the sugRejectTime to set
	 */
	public void setSugRejectTime(String sugRejectTime) {
		this.sugRejectTime = sugRejectTime;
	}
	/**
	 * @return the sugRejectTimePercent
	 */
	public String getSugRejectTimePercent() {
		return sugRejectTimePercent;
	}
	/**
	 * @param sugRejectTimePercent the sugRejectTimePercent to set
	 */
	public void setSugRejectTimePercent(String sugRejectTimePercent) {
		this.sugRejectTimePercent = sugRejectTimePercent;
	}
	/**
	 * @return the sugPersonTime
	 */
	public String getSugPersonTime() {
		return sugPersonTime;
	}
	/**
	 * @param sugPersonTime the sugPersonTime to set
	 */
	public void setSugPersonTime(String sugPersonTime) {
		this.sugPersonTime = sugPersonTime;
	}
	/**
	 * @return the sugPersonTimePercent
	 */
	public String getSugPersonTimePercent() {
		return sugPersonTimePercent;
	}
	/**
	 * @param sugPersonTimePercent the sugPersonTimePercent to set
	 */
	public void setSugPersonTimePercent(String sugPersonTimePercent) {
		this.sugPersonTimePercent = sugPersonTimePercent;
	}
	
	
	
	
}
