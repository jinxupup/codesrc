package com.jjb.ecms.biz.dao.query;

import com.jjb.ecms.facility.dto.ApplyLendingDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyLendingQueryDao extends BaseDao<ApplyLendingDto>{

	/**
	 * 放款进度查询,在申请进度查询上新加分期表查询放款状态
	 * @param page
	 * @return
	 */
	public Page<ApplyLendingDto> applyLendingQuery(Page<ApplyLendingDto> page);
}
