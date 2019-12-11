/**
 * 
 */
package com.jjb.ecms.biz.service.query;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyLendingDto;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 申请进度查询
 * @author JYData-R&D-BigK.K
 * @date 2016年9月18日 上午10:02:50
 * @version V1.0  
 */

public interface ApplyProcessQueryService {

	/**
	 *申请进度查询
	 * @param page
	 * @return
	 */
	Page<ApplyProcessQueryDto> applyProcessList(Page<ApplyProcessQueryDto> page);
	
	public List<ApplyProcessQueryDto> applyProcessList(Map<String,Object> param);

	Page<ApplyLendingDto> appPlyLendingQuery(Page<ApplyLendingDto> page);

	/**
	 * 根据审批状态类型<通过、失败、审批中、预审>
	 * @param param
	 * @return
	 */
	public List<ApplyProcessQueryDto> getApplyByRtfStateType(Map<String,Object> param);
}
