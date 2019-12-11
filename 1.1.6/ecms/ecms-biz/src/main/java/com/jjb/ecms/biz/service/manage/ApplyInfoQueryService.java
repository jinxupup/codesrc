package com.jjb.ecms.biz.service.manage;

import com.jjb.ecms.facility.dto.ApplyInfoQueryDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 申请件信息查询
 * @author J.J
 * @date 2017年10月30日上午10:01:06
 */
public interface ApplyInfoQueryService {

	/**
	 *申请进度查询
	 * @param page
	 * @return
	 */
	Page<ApplyInfoQueryDto> applyInfoList(Page<ApplyInfoQueryDto> page);
}
