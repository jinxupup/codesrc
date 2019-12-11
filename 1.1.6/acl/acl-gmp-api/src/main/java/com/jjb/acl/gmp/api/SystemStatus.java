package com.jjb.acl.gmp.api;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统状态相关数据
 * @author LI.J
 *
 */
public class SystemStatus implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Date businessDate;
	
	private Date lastBusinessDate;
	
	private Date processDate;
	
	private Date lastProcessDate;
	
	private boolean graceTime;
	
	private Date dateSwitchTime;
	
	private Date lastDateSwitchTime;
	
	/**
	 * 当前日切时间
	 */
	public Date getDateSwitchTime() {
		return dateSwitchTime;
	}

	public void setDateSwitchTime(Date dateSwitchTime) {
		this.dateSwitchTime = dateSwitchTime;
	}

	/**
	 * 上次日切时间
	 */
	public Date getLastDateSwitchTime() {
		return lastDateSwitchTime;
	}

	public void setLastDateSwitchTime(Date lastDateSwitchTime) {
		this.lastDateSwitchTime = lastDateSwitchTime;
	}

	/**
	 * 业务日期，即联机日期
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * 处理日期，即批量日期
	 * @return
	 */
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	/**
	 * 是否处于联机Grace时间，以便决定冲正和撤销的是否允许
	 */
	public boolean isGraceTime() {
		return graceTime;
	}

	public void setGraceTime(boolean graceTime) {
		this.graceTime = graceTime;
	}

	public Date getLastProcessDate() {
		return lastProcessDate;
	}

	public void setLastProcessDate(Date lastProcessDate) {
		this.lastProcessDate = lastProcessDate;
	}

	/**
	 * 上一业务日期，即上次日切前的联机日期
	 */
	public Date getLastBusinessDate() {
		return lastBusinessDate;
	}

	public void setLastBusinessDate(Date lastBusinessDate) {
		this.lastBusinessDate = lastBusinessDate;
	}
}
