package com.jjb.ecms.biz.service.manage;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 终审人员额度分配查询
 * @author J.J
 * @date 2017年7月12日上午10:47:32
 */
public interface TmAppAuditQuotaService {

	/** 
	 * 终审人员额度分配查询Page
	 * @param page
	 * @return
	 */
	public Page<TmAppAuditQuota> getPage(Page<TmAppAuditQuota> page, TmAppAuditQuota TmAppAuditQuota);
	
	/** 
	 * 根据条件获取终审人员额度分配清单
	 * @param TmAppAuditQuota
	 * @return
	 */
	public List<TmAppAuditQuota> getTmAppAuditQuotaList(TmAppAuditQuota TmAppAuditQuota);
	
	/** 
	 * 保存终审人员额度分配添加
	 * @param TmAppAuditQuota
	 * @return
	 */
	public void saveTmAppAuditQuota(TmAppAuditQuota TmAppAuditQuota) throws Exception;
	
	/** 
	 * 终审人员额度分配更新
	 * @param TmAppAuditQuota
	 * @return
	 */
	public void updateTmAppAuditQuota(TmAppAuditQuota TmAppAuditQuota);
	
	/** 
	 * 通过id查询终审人员额度分配
	 * @param TmAppAuditQuota
	 * @return
	 */
	public TmAppAuditQuota getTmAppAuditQuotaById(int id);
	
	/** 
	 * 删除终审人员额度分配查询
	 * @param id
	 * @return
	 */
	public void deleteTmAppAuditQuotaById(int id);


}
