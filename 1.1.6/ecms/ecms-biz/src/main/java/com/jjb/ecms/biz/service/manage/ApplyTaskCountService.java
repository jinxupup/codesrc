package com.jjb.ecms.biz.service.manage;

import com.jjb.ecms.facility.dto.ApplyTaskCountDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 申请工作量查询
 * @author JYData-R&D-L.L
 * @date 2016年9月2日 下午2:37:47
 * @version V1.0  
 */
public interface ApplyTaskCountService {

	/**
	 * 申请工作量查询
	 * @param page
	 * @return
	 */
	Page<ApplyTaskCountDto> getTaskCountList(Page<ApplyTaskCountDto> page);
}
