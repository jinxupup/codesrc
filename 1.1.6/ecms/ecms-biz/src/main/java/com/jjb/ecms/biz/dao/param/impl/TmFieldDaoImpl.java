package com.jjb.ecms.biz.dao.param.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmFieldDao;
import com.jjb.ecms.infrastructure.TmField;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("tmFieldDao")
public class TmFieldDaoImpl extends AbstractBaseDao<TmField> implements TmFieldDao {
	private static final String SELECTAll = "com.jjb.ecms.infrastructure.mapping.TmFieldMapper.selectAll";

	@Override
	public Page<TmField> getTmFiledPage(Page<TmField> page) {
		page = queryForPageList(SELECTAll, page.getQuery(), page);
		return page;
	}

}
