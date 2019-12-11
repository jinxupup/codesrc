package com.jjb.ecms.biz.service.commonDialog.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.apply.TmAppModifyHisDao;
import com.jjb.ecms.biz.service.commonDialog.ApplyUpdateHistoryService;
import com.jjb.ecms.infrastructure.TmAppModifyHis;

/**
 * @description: TODO
 * @author -BigZ.Y
 * @date 2016年9月21日 下午3:02:42 
 */
@Service("applyUpdateHistoryService")
public class ApplyUpdateHistoryServiceImpl implements ApplyUpdateHistoryService {

	@Autowired
	private TmAppModifyHisDao tmAppModifyHisDao;
	
	/**
	 * 获取修改历史信息
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public List<TmAppModifyHis> getTmAppModifyHisList(String appNo) {
		// TODO Auto-generated method stub
		List<TmAppModifyHis> tmAppModifyHisList = tmAppModifyHisDao.getTmAppModifyHisList(appNo);

		return tmAppModifyHisList;
	}

}
