package com.jjb.ecms.biz.dao.approve.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.approve.TmExtTriggerRulesDao;
import com.jjb.ecms.infrastructure.TmExtTriggerRules;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @Description: 第三方风控结论标签信息 
 * @author JYData-R&D-HN
 * @date 2018年5月9日 下午5:29:48
 * @version V1.0
 */
@Repository("tmExtTriggerRulesDao")
public class TmExtTriggerRulesDaoImpl extends AbstractBaseDao<TmExtTriggerRules> implements TmExtTriggerRulesDao {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmExtTriggerRulesDao#getTmExtTriggerRulesByAppNo(java.lang.String)
	 */
	@Override
	public List<TmExtTriggerRules> getTmExtTriggerRulesByAppNo(String appNo) {
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmExtTriggerRules entity = new TmExtTriggerRules();
		entity.setAppNo(appNo);
		List<TmExtTriggerRules> list = queryForList(entity);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmExtTriggerRulesDao#saveTmExtTriggerRules(TmExtTriggerRules)
	 */
	@Override
	public void saveTmExtTriggerRules(TmExtTriggerRules tmExtTriggerRules) {
		save(tmExtTriggerRules);
	}

	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmExtTriggerRulesDao#updateTmExtTriggerRules(TmExtTriggerRules)
	 */
	@Override
	public void updateTmExtTriggerRules(TmExtTriggerRules tmExtTriggerRules) {
		update(tmExtTriggerRules);
	}

}