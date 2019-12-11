/**
 * 
 */
package com.jjb.ecms.biz.service.node;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 预录入申请信息列表
 * @author JYData-R&D-Big T.T
 * @date 2016年9月1日 上午9:08:40
 * @version V1.0  
 */

public interface ApplyInfoPreDtoService {

	/**
	 * 获取所有的预录入申请信息列表
	 * @param page
	 * @return
	 */
	public Page<ApplyInfoPreDto> getApplyInfoPreDtoPage(Page<ApplyInfoPreDto> page);
	
	/**
	 * 获取历史申请信息list
	 * @param applyInfoPreDto
	 * @return
	 */
	public List<ApplyInfoPreDto> getApplyInfoPreDtoList(ApplyInfoPreDto applyInfoPreDto);
}
