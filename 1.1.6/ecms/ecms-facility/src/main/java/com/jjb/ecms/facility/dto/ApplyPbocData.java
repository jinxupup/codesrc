package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Map;

/**
  * @Description: 人行征信报告
  * 该信息不保存到节点表中 ，因已经入库的
  * @author JYData-R&D-HN
  * @date 2016年9月1日 下午1:54:14
  * @version V1.0
 */
public class ApplyPbocData implements Serializable {
	

	private static final long serialVersionUID = -2950643408714917637L;
	/**
	 * 是否有人行征信记录
	 */
	public boolean hasRecord;
	//人行征信报告html字符串
	public String netPbocStr;
	//人行征信报告html字符串
	public String localPbocStr;
	public Map<String, Serializable> tmCisStatisticsSummary ;
	
	public boolean isHasRecord() {
		return hasRecord;
	}
	public void setHasRecord(boolean hasRecord) {
		this.hasRecord = hasRecord;
	}
	public String getNetPbocStr() {
		return netPbocStr;
	}
	public void setNetPbocStr(String netPbocStr) {
		this.netPbocStr = netPbocStr;
	}
	public String getLocalPbocStr() {
		return localPbocStr;
	}
	public void setLocalPbocStr(String localPbocStr) {
		this.localPbocStr = localPbocStr;
	}
	public Map<String, Serializable> getTmCisStatisticsSummary() {
		return tmCisStatisticsSummary;
	}
	public void setTmCisStatisticsSummary(
			Map<String, Serializable> tmCisStatisticsSummary) {
		this.tmCisStatisticsSummary = tmCisStatisticsSummary;
	}
	
}
