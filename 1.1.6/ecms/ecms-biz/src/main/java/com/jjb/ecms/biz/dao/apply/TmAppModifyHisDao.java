package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;
/**
  * @Description: 修改历史记录dao
  * @author JYData-R&D-Big Star
  * @date 2016年9月2日 下午7:28:02
  * @version V1.0
 */
public interface TmAppModifyHisDao extends BaseDao<TmAppModifyHis> {

	/**
	 * 根据申请件编号获取所有申请件历史信息
	 * 
	 * @param appNo
	 * @return
	 */
	public List<TmAppModifyHis> getTmAppModifyHisList(String appNo);
	
	/**
	 * 保存历史信息
	 * @param tmAppExtend
	 */
	public void saveTmAppModifyHis(TmAppModifyHis tmAppModifyHis) ;

	/**
	 * 查看修改历史信息
	 * @param page
	 * @param appNo
	 * @return
	 */
	public Page<TmAppModifyHis> getModifyHistoryInfoPage(
			Page<TmAppModifyHis> page, String appNo);

}