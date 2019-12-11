package com.jjb.ecms.biz.service.commonDialog.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.service.commonDialog.ApplyHistoryService;
import com.jjb.ecms.infrastructure.TmAppHistory;

/**
 * @description: 审批历史查询结果service实现类
 * @author -BigZ.Y
 * @date 2016年9月21日 上午11:47:00
 */
@Service("applyHistoryService")
public class ApplyHistoryServiceImpl implements ApplyHistoryService {

	@Autowired
	private TmAppHistoryDao tmAppHistoryDao;

	/**
	 * 查询审批历史集
	 * 
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public List<TmAppHistory> getTmAppHistoryList(String appNo) {
		// TODO Auto-generated method stub
		TmAppHistory tmAppHistory = new TmAppHistory();
		tmAppHistory.setAppNo(appNo);
		List<TmAppHistory> tmAppHistoryList = tmAppHistoryDao.getTmAppHistoryByAppNo(appNo);
		for (TmAppHistory tAppHistory : tmAppHistoryList) {
			tAppHistory.setRtfState(tAppHistory.getRtfState() + "-"+ RtfState.valueOf(tAppHistory.getRtfState()).lab);
		}

		return tmAppHistoryList;
	}

	/**
	 * 查询审批历史集合
	 * 
	 * @param TmAppHistory
	 * @return
	 */
	@Override
	@Transactional
	public List<TmAppHistory> getAppHistroyByParam(Map<String,Object> map) {
		return tmAppHistoryDao.getAppHistroyByParam(map);
	}
}
