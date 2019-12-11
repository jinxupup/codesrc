package com.jjb.ecms.biz.dao.manage;

import com.jjb.ecms.facility.dto.ApplyTaskCountDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
  * @Description: 申请工作量查询
  * @author JYData-R&D-L.L
  * @date 2016年9月2日 下午4:19:44
  * @version V1.0
 */
public interface ApplyTaskCountDao extends BaseDao<ApplyTaskCountDto>{

	/**
	 * 申请工作量查询
	 * @param page
	 * @return
	 */
	Page<ApplyTaskCountDto> getTaskCountList(Page<ApplyTaskCountDto> page);
}
