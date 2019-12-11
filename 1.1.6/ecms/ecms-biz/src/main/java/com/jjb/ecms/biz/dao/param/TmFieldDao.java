package com.jjb.ecms.biz.dao.param;

import com.jjb.ecms.infrastructure.TmField;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmFieldDao extends BaseDao<TmField> {
	public Page<TmField> getTmFiledPage(Page<TmField> page);
}
