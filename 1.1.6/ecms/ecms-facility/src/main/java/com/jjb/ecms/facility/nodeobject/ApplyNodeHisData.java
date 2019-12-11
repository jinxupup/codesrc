package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 申请历史对象
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:38:24
 * @version V1.0
 */
public class ApplyNodeHisData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 740627384029371683L;
	private Map<String, Map<String, Serializable>> tmAppHistorys; // 历史记录人表

	public Map<String, Map<String, Serializable>> getTmAppHistorys() {
		return tmAppHistorys;
	}

	public void setTmAppHistorys(
			Map<String, Map<String, Serializable>> tmAppHistorys) {
		this.tmAppHistorys = tmAppHistorys;
	}

}
