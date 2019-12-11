package com.jjb.ecms.biz.dao.query.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.query.ApplyLendingQueryDao;
import com.jjb.ecms.facility.dto.ApplyLendingDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
@Repository
public class ApplyLendingQueryDaoImpl extends AbstractBaseDao<ApplyLendingDto> implements ApplyLendingQueryDao{

	public static final String NS = "com.jjb.ecms.ApplyLendingQuery.selectMain";//查询独立主卡或者主附卡同申放款进度
	
	@Override
	public Page<ApplyLendingDto> applyLendingQuery(
			Page<ApplyLendingDto> page) {
		// TODO Auto-generated method stub
		return queryForPageList(NS, page.getQuery(), page);
	}

}
