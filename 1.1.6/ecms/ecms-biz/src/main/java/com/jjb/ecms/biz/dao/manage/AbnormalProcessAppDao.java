package com.jjb.ecms.biz.dao.manage;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyAbnormalProcessDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
  * @Description: 异常流程申请件管理dao
  * @author JYData-R&D-L.L
  * @date 2016年9月22日 下午5:09:49
  * @version V1.0
 */
public interface AbnormalProcessAppDao extends BaseDao<ApplyAbnormalProcessDto>{
	/**
	 * 查询异常流程申请件
	 * @return
	 */
	public List<ApplyAbnormalProcessDto> getAbnormalProcessApp(String appNo, String rtfState,String cardNo,String idNo,String idType);
	public Page<ApplyAbnormalProcessDto> getAbnormalProcessApp(Page<ApplyAbnormalProcessDto> page);
	/**
	 * 删除异常件
	 * @param appNo
	 */
	public void delete(String appNo);
	
	/**
	 * 删除工作流历史流程实例
	 * @param appNo
	 */
	public void deleteFromHiProcinst(String appNo);
	
	/**
	 * 根据条件删除 Identitylink无效数据
	 * @param taskId
	 * @param procInstId
	 */
	public void deleteFromIdentitylink(String taskId,String procInstId);
}
