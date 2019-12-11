package com.jjb.ecms.biz.dao.approve.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 进件审计记录
 * @author hp
 *
 */
@Repository("tmAppAuditDao")
public class TmAppAuditDaoImpl extends AbstractBaseDao<TmAppAudit> implements TmAppAuditDao {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmAppAuditDao#getTmAppAuditByAppNo(java.lang.String)
	 */
	@Override
	public TmAppAudit getTmAppAuditByAppNo(String appNo) {
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmAppAudit entity = new TmAppAudit();
		entity.setAppNo(appNo);
		List<TmAppAudit> list = queryForList(entity);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmAppAuditDao#saveTmAppAudit(TmAppAudit)
	 */
	@Override
	public void saveTmAppAudit(TmAppAudit tmAppAudit) {
		if(tmAppAudit!=null){
			if(tmAppAudit.getUpdateDate()==null){
				tmAppAudit.setUpdateDate(new Date());
			}
			if(StringUtils.isEmpty(tmAppAudit.getUpdateUser())){
				tmAppAudit.setUpdateUser(OrganizationContextHolder.getUserNo());
			}
			save(tmAppAudit);
		}
	}

	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmAppAuditDao#updateTmAppAudit(TmAppAudit)
	 */
	@Override
	public void updateTmAppAudit(TmAppAudit tmAppAudit) {
		update(tmAppAudit);
	}

}