
package com.jjb.ecms.biz.dao.node;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyCardAcctInfoDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 客户、账户和已发卡信息关联查询Dao
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:17:59
 * @version V1.0  
 */

public interface ApplyCardAcctInfoDtoDao extends BaseDao<ApplyCardAcctInfoDto> {

	/**
	 * 获取所有符合条件的关联信息ApplyCardAcctInfoDto
	 * @param page
	 * @return
	 */
	public Page<ApplyCardAcctInfoDto> getApplyCardAcctInfoDtoPage(Page<ApplyCardAcctInfoDto> page);
	
	/**
	 * 根据条件获取对象列表
	 * @param applyCardAcctInfoDto
	 * @return
	 */
	public List<ApplyCardAcctInfoDto> getApplyCardAcctInfoDtoList(ApplyCardAcctInfoDto applyCardAcctInfoDto);
}
