/**
 * 
 */
package com.jjb.ecms.biz.dao.query;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: TODO
 * @author JYData-R&D-BigK.K
 * @date 2016年9月18日 上午11:55:05
 * @version V1.0  
 */
public interface ApplyProcessQueryDao extends BaseDao<ApplyProcessQueryDto>{

	/**
	 * 申请进度查询
	 * @param page
	 * @return
	 */
	public Page<ApplyProcessQueryDto> applyProcessList(Page<ApplyProcessQueryDto> page);
	
	public List<ApplyProcessQueryDto> applyProcessList(Map<String,Object> param);

	/**
	 * 根据审批状态类型<通过、失败、审批中、预审>
	 * @param param
	 * @return
	 */
	public List<ApplyProcessQueryDto> getApplyByRtfStateType(Map<String,Object> param);
	
}
