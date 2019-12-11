package com.jjb.ecms.biz.dao.approve;

import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 进件审计记录
 * @author hp
 *
 */
public interface TmAppAuditDao extends BaseDao<TmAppAudit>{
	/**
	 * 根据申请件编号获取所有联系人信息
	 * 
	 * @param appNo
	 * @return
	 */
	public TmAppAudit getTmAppAuditByAppNo(String appNo);

	public void saveTmAppAudit(TmAppAudit tmAppAudit);

	public void updateTmAppAudit(TmAppAudit tmAppAudit);

}