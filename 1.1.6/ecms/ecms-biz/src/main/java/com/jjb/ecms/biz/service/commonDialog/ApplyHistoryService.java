package com.jjb.ecms.biz.service.commonDialog;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.infrastructure.TmAppHistory;

/**
 * @description: 查询审批历史弹窗service
 * @author -BigZ.Y
 * @date 2016年9月21日 上午11:45:43 
 */
public interface ApplyHistoryService {

	/**
	 * 查询审批历史集
	 * @param appNo
	 * @return
	 */
	List<TmAppHistory> getTmAppHistoryList(String appNo);
	/**
	 * 查询审批历史集
	 * @param TmAppHistory
	 * @return
	 */
	List<TmAppHistory> getAppHistroyByParam(Map<String,Object> map);
}
