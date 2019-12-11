package com.jjb.ecms.biz.dao.approve.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @Description: 第三方风控结论
 * @author JYData-R&D-HN
 * @date 2018年5月9日 下午5:30:04
 * @version V1.0
 */
@Repository("tmExtRiskInputDao")
public class TmExtRiskInputDaoImpl extends AbstractBaseDao<TmExtRiskInput> implements TmExtRiskInputDao{
	private Logger logger = LoggerFactory.getLogger(getClass());
	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmExtRiskInputDao#getTmExtRiskInputByAppNo(java.lang.String)
	 */
	@Override
	public TmExtRiskInput getTmExtRiskInputByAppNo(String appNo) {
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmExtRiskInput entity = new TmExtRiskInput();
		entity.setAppNo(appNo);
		List<TmExtRiskInput> list = queryForList(entity);
		if(CollectionUtils.isNotEmpty(list) && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmExtRiskInputDao#saveTmExtRiskInput(TmExtRiskInput)
	 */
	@Override
	public void saveTmExtRiskInput(TmExtRiskInput tmExtRiskInput) {
		save(tmExtRiskInput);
	}

	/* (non-Javadoc)
	 * @see com.jjb.ecms.biz.dao.apply.TmExtRiskInputDao#updateTmExtRiskInput(TmExtRiskInput)
	 */
	@Override
	public void updateTmExtRiskInput(TmExtRiskInput tmExtRiskInput) {
		update(tmExtRiskInput);
	}

}