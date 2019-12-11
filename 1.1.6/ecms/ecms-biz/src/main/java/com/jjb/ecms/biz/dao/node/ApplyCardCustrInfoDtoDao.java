
package com.jjb.ecms.biz.dao.node;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyCardCustrInfoDto;

/**
 * @Description: 客户和已发卡信息关联查询Dao
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:17:59
 * @version V1.0  
 */

public interface ApplyCardCustrInfoDtoDao {

	/**
	 * 获取所有符合条件的关联信息ApplyCardCustrInfoDto
	 * @param ApplyCardCustrInfoDto
	 * @return
	 */
	public List<ApplyCardCustrInfoDto> getApplyCardCustrInfoDtoList(ApplyCardCustrInfoDto applyCardCustrInfoDto);
}
