package com.jjb.ecms.biz.dao.manage;

import com.jjb.ecms.facility.dto.ApplyInfoQueryDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 申请信息查询
 * @author J.J
 * @date 2017年10月30日上午10:13:49
 */
public interface ApplyInfoQueryDao extends BaseDao<ApplyInfoQueryDto> {

	/**
	 * 申请信息查询
	 * @param page
	 * @return
	 */
	public Page<ApplyInfoQueryDto> applyInfoList(Page<ApplyInfoQueryDto> page);
}
