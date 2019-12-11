package com.jjb.ecms.biz.service.manage;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyAbnormalProcessDto;
import com.jjb.ecms.infrastructure.TmAppExcePool;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.model.Page;


/**
  * @Description: 异常流程申请件管理Service
  * @author JYData-R&D-L.L
  * @date 2016年9月22日 下午5:00:28
  * @version V1.0
 */
public interface AbnormalProcessAppService {
	/**
	 * 查询异常流程申请件
	 * @return
	 */
	public List<ApplyAbnormalProcessDto> getAbnormalProcessApp(String appNo, String rtfState, String cardNo, String idNo, String idType);
	
	public Page<ApplyAbnormalProcessDto> getAbnormalProcessApp(Page<ApplyAbnormalProcessDto> page);
	
	public void delete(String appNo);
	
	/**
	 * 修改状态异常的申请件的状态
	 * @return
	 */
	List<TmAppMain> getTmAppMainMkCarfEx();

	/**
	 * 针对制卡异常的申请件超过两个批量日进行再次发起制卡交易
	 * @return
	 */
	List<TmAppMain> getTmAppMianMkCardAgain();


	/**
	 * 1.分页查询异常申请件列表数据
	 * @param page
	 * @return
	 */
	public Page<TmAppExcePool> getTmAppExcePoolPage(Page<TmAppExcePool>  page);

	/**
	 * 根据申请件编号获取所有异常申请件列表
	 *
	 * @param TmAppExcePool
	 * @return
	 */
	public List<TmAppExcePool> getTmAppExcePoolList(TmAppExcePool tmAppExcePool);

	public void saveTmAppExcePool(TmAppExcePool tmAppExcePool);

	public void updateTmAppExcePool(TmAppExcePool tmAppExcePool);

	/**
	 * 根据申请件编号删除异常申请件列表
	 *
	 * @param TmAppExcePool
	 * @return
	 */
	public void deleteTmAppExcePool(TmAppExcePool tmAppExcePool);
}
