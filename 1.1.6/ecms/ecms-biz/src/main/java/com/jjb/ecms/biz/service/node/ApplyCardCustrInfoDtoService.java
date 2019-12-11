/**
 * 
 */
package com.jjb.ecms.biz.service.node;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyCardCustrInfoDto;

/**
 * @Description: 客户和已发卡信息关联查询Service
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:15:11
 * @version V1.0  
 */

public interface ApplyCardCustrInfoDtoService {
	/**
	 * 获取所有符合条件的关联信息ApplyCardCustrInfoDto
	 * @param applyCardCustrInfoDto
	 * @return
	 */
	public List<ApplyCardCustrInfoDto> getApplyCardCustrInfoDtoList(ApplyCardCustrInfoDto applyCardCustrInfoDto);
}
