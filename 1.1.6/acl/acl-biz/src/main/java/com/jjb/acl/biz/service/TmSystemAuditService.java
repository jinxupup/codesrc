package com.jjb.acl.biz.service;


import com.jjb.acl.infrastructure.TmSystemAudit;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 修改记录Service
 * @author J.J
 * @date 2017年7月14日下午3:52:32
 */
public interface TmSystemAuditService {

	/** 
	 * 保存修改记录
	 * @param Json
	 */
	public void saveTmSystemAuditField(TmSystemAudit tmSystemAudit);
	/** 
	 * 修改记录Page
	 * @param operatorId 
	 * @param Page
	 * @return Page
	 */
	public Page<TmSystemAudit> getPage(Page<TmSystemAudit> page, String operatorId);
	/** 
	 * 通过id获取修改记录
	 * @param ID
	 * @return TmSystemAudit
	 */
	public TmSystemAudit getTmSystemAuditById(int id);
}
