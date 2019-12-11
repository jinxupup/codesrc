package com.jjb.ecms.biz.dao.apply;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;
/**
  * @Description: 申请历史记录dao
  * @author JYData-R&D-Big Star
  * @date 2016年9月2日 下午7:28:02
  * @version V1.0
 */
public interface TmAppHistoryDao extends BaseDao<TmAppHistory> {

	/**
	 * 根据申请件编号获取所有申请件历史信息
	 * 
	 * @param appNo
	 * @return
	 */
	public List<TmAppHistory> getTmAppHistoryByAppNo(String appNo);

	/**
	 * 查询申请件历史信息
	 * @param TmAppHistory
	 * @return
	 */
	public List<TmAppHistory> getTmAppHistoryList(TmAppHistory history);
	
	/**
	 * 保存历史信息
	 * @param tmAppHistory
	 */
	public void saveTmAppHistory(TmAppHistory tmAppHistory) ;

	/**
	 * 更新历史信息
	 * @param tmAppHistory
	 */
	public void updateTmAppHistory(TmAppHistory tmAppHistory) ;
	
	/**
	 * 删除历史信息
	 * @param tmAppHistory
	 */
	public void deleteTmAppHistory(TmAppHistory tmAppHistory) ;
	
	/**
	 * @param page
	 * @param appNo
	 * @return
	 */
	public Page<TmAppHistory> getapprovalHistoryInfoPage(
			Page<TmAppHistory> page, String appNo);
	
	/**
	 * 根据参数获取审批历史信息
	 * @param TmAppHistory
	 * @return
	 */
	public List<TmAppHistory> getAppHistroyByParam(Map<String,Object> map);
}