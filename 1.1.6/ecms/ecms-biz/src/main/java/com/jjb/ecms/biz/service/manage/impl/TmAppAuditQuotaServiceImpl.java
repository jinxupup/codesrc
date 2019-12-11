package com.jjb.ecms.biz.service.manage.impl;


import java.util.Date;
import java.util.List;

import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.manage.TmAppAuditQuotaDao;
import com.jjb.ecms.biz.service.manage.TmAppAuditQuotaService;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 
 * @author J.J
 * @date 2017年7月14日下午2:33:43
 */

@Service("tmAppAuditQuotaService")
public class TmAppAuditQuotaServiceImpl implements TmAppAuditQuotaService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmAppAuditQuotaDao tmAppAuditQuotaDao;
	
	@Autowired
	private CacheContext cacheContext;
	
	@Override
	@Transactional
	public Page<TmAppAuditQuota> getPage(Page<TmAppAuditQuota> page,
										 TmAppAuditQuota TmAppAuditQuota) {
		return tmAppAuditQuotaDao.getPage(page, TmAppAuditQuota);
	}

	/** 
	 * 根据条件获取终审人员额度分配清单
	 * @param TmAppAuditQuota
	 * @return
	 */
	public List<TmAppAuditQuota> getTmAppAuditQuotaList(TmAppAuditQuota TmAppAuditQuota){
		if(TmAppAuditQuota == null){
			TmAppAuditQuota = new TmAppAuditQuota();
			TmAppAuditQuota.setOrg(OrganizationContextHolder.getOrg());
			TmAppAuditQuota.setOperatorId(OrganizationContextHolder.getUserNo());
		}

		return tmAppAuditQuotaDao.queryForList(TmAppAuditQuota);
	}

	
	/** 
	 * 新增审批额度并保存操作记录
	 * @param TmAppAuditQuota
	 * @return
	 */
	@Override
	@Transactional
	public void saveTmAppAuditQuota(TmAppAuditQuota TmAppAuditQuota) throws ProcessException {
		if(TmAppAuditQuota != null) {
			//根据operatorId和taskName查看是否已经存在相同数据;若存在，则不再插入该条数据
			TmAppAuditQuota TmAppAuditQuota1 = new TmAppAuditQuota();
			TmAppAuditQuota1.setOperatorId(TmAppAuditQuota.getOperatorId());
			TmAppAuditQuota1.setTaskName(TmAppAuditQuota.getTaskName());
			List<TmAppAuditQuota> TmAppAuditQuotaList = tmAppAuditQuotaDao.queryForList(TmAppAuditQuota1);
			
			if (TmAppAuditQuotaList != null && TmAppAuditQuotaList.size() > 0) {
				throw new ProcessException("操作员 [" + TmAppAuditQuota.getOperatorId() + "] 已存在！");
			} else {
				logger.info("保存终审人员额度" + TmAppAuditQuota.getOperatorId());
				TmAppAuditQuota.setOrg(OrganizationContextHolder.getOrg());
				TmAppAuditQuota.setUpdateDate(new Date());
				TmAppAuditQuota.setUpdateUser(OrganizationContextHolder.getUserNo());
				tmAppAuditQuotaDao.save(TmAppAuditQuota);
				cacheContext.initTmAppAuditQuota();
			}
		}
	}

	
	/**
	 * 修改审批额度并保存操作记录
	 * @param TmAppAuditQuota
	 * @return
	 */
	@Override
	@Transactional
	public void updateTmAppAuditQuota(TmAppAuditQuota TmAppAuditQuota) throws ProcessException{
		if(TmAppAuditQuota != null && TmAppAuditQuota.getId() != null) {
			logger.info("更新终审人员额度" + TmAppAuditQuota.getOperatorId());
			TmAppAuditQuota.setOrg(OrganizationContextHolder.getOrg());
			TmAppAuditQuota.setUpdateDate(new Date());
			TmAppAuditQuota.setUpdateUser(OrganizationContextHolder.getUserNo());
			tmAppAuditQuotaDao.update(TmAppAuditQuota);
			cacheContext.initTmAppAuditQuota();
		} else {
			throw new ProcessException("未查到该条信息！");
		}
	}

	/**
	 * 根据id查询审批额度
	 * @param TmAppAuditQuota
	 * @return
	 */
	@Override
	@Transactional
	public TmAppAuditQuota getTmAppAuditQuotaById(int id) {
		TmAppAuditQuota TmAppAuditQuota = new TmAppAuditQuota();
		TmAppAuditQuota.setId(id);
		return tmAppAuditQuotaDao.queryByKey(TmAppAuditQuota);
	}

	/**
	 * 根据id删除审批额度并保存操作记录
	 * @param TmAppAuditQuota
	 * @return
	 */
	@Override
	@Transactional
	public void deleteTmAppAuditQuotaById(int id) {
		if (StringUtils.isNotEmpty(id)) {
			TmAppAuditQuota TmAppAuditQuota = new TmAppAuditQuota();
			TmAppAuditQuota.setId(id);
			tmAppAuditQuotaDao.deleteByKey(TmAppAuditQuota);
			cacheContext.initTmAppAuditQuota();
		}
	}
}
