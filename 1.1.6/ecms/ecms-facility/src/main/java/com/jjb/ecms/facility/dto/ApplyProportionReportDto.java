package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class ApplyProportionReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;//日期
	private String sugSuccessTime;//建议通过次数
	private String realSuccessTime;//实际通过次数
	private String successTimePercent;//建议通过占比
	private String sugRejectTime;//建议拒绝次数
	private String realRejectTime;//实际拒绝次数
	private String rejectTimePercent;//建议拒绝占比
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
	 * @return the realSuccessTime
	 */
	public String getRealSuccessTime() {
		return realSuccessTime;
	}
	/**
	 * @param realSuccessTime the realSuccessTime to set
	 */
	public void setRealSuccessTime(String realSuccessTime) {
		this.realSuccessTime = realSuccessTime;
	}
	/**
	 * @return the successTimePercent
	 */
	public String getSuccessTimePercent() {
		return successTimePercent;
	}
	/**
	 * @param successTimePercent the successTimePercent to set
	 */
	public void setSuccessTimePercent(String successTimePercent) {
		this.successTimePercent = successTimePercent;
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
	 * @return the realRejectTime
	 */
	public String getRealRejectTime() {
		return realRejectTime;
	}
	/**
	 * @param realRejectTime the realRejectTime to set
	 */
	public void setRealRejectTime(String realRejectTime) {
		this.realRejectTime = realRejectTime;
	}
	/**
	 * @return the rejectTimePercent
	 */
	public String getRejectTimePercent() {
		return rejectTimePercent;
	}
	/**
	 * @param rejectTimePercent the rejectTimePercent to set
	 */
	public void setRejectTimePercent(String rejectTimePercent) {
		this.rejectTimePercent = rejectTimePercent;
	}
	
	
}
