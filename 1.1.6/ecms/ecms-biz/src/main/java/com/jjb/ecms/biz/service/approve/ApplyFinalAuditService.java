package com.jjb.ecms.biz.service.approve;


import com.jjb.unicorn.facility.model.Query;

/**
 * @description 终审service
 * @author hn
 * @date 2016-8-29 16:19:44 
 */
public interface ApplyFinalAuditService {

	/**
	 * 保存终审操作信息
	 * @param operateInfo
	 */
	void saveFinalAuditInfo(Query operateInfo);
}
