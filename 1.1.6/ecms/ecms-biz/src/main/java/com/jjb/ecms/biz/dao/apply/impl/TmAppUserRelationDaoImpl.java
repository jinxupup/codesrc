package com.jjb.ecms.biz.dao.apply.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppUserRelationDao;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @Description: 附卡申请人信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
@Repository("tmAppUserRelationDao")
public class TmAppUserRelationDaoImpl extends AbstractBaseDao<TmAppUserRelation> implements TmAppUserRelationDao {
	public static final String  selectAll = "com.jjb.ecms.infrastructure.mapping.TmAppUserRelationMapper.selectAll";
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public TmAppUserRelation getTmAppUserRelationByUserNo(String userNo){
		TmAppUserRelation entity = new TmAppUserRelation();
		if(StringUtils.isEmpty(userNo)){
			logger.info("业务员编号为空！");
			throw new ProcessException("业务员编号为空！");
		}
		entity.setUserNo(userNo);
		TmAppUserRelation tmAppUserRelation = queryForOne(entity);
		return tmAppUserRelation;
	}

	@Override
	public Page<TmAppUserRelation> getPage(Page<TmAppUserRelation> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page = queryForPageList(selectAll, page.getQuery(), page);
		return page;
	}



}