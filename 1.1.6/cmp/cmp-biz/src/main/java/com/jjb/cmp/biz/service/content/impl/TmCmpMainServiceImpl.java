package com.jjb.cmp.biz.service.content.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.cmp.biz.dao.TmCmpMainDao;
import com.jjb.cmp.biz.service.content.TmCmpMainService;
import com.jjb.cmp.infrastructure.TmCmpMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ImageMainServiceImpl
 * @Description TODO
 * @Author smh Date 2018/12/31 15:24 Version 1.0
 */
@Service("tmCmpMainService")
public class TmCmpMainServiceImpl implements TmCmpMainService {
	@Autowired
	private TmCmpMainDao tmCmpMainDao;

	@Override
	public TmCmpMain getTmCmpMain(TmCmpMain tmCmpMain) {
		if (tmCmpMain != null
				&& (StringUtils.isNotEmpty(tmCmpMain.getBatchNo()) 
						|| StringUtils.isNotEmpty(tmCmpMain.getIdType())
						|| StringUtils.isNotEmpty(tmCmpMain.getName()))) {
			return tmCmpMainDao.getTmCmpMain(tmCmpMain);
		}
		throw new ProcessException("无效的查询条件");
	}

	@Override
	public TmCmpMain   getTmCmpMainByBatchNo(String batchNo) {
		if (StringUtils.isEmpty(batchNo)) {
			throw new ProcessException("内容批次号不能为空!");
		}
		TmCmpMain tmCmpMain = new TmCmpMain();
		tmCmpMain.setBatchNo(batchNo);
		return tmCmpMainDao.queryByKey(tmCmpMain);
	}

	@Override
	public void updateTmCmpMain(TmCmpMain tmCmpMain) {
		if(tmCmpMain==null) {
			throw new ProcessException("无法更新空数据!");
		}
		tmCmpMainDao.updateTmCmpMain(tmCmpMain);
	}

	@Override
	public void saveTmCmpMain(TmCmpMain tmCmpMain) {
		tmCmpMainDao.saveTmCmpMain(tmCmpMain);
	}
	
	@Override
	public List<TmCmpMain>   getTmCmpMainByIdNo(String idNo) {
		if (StringUtils.isEmpty(idNo)) {
			throw new ProcessException("身份证号不能为空!");
		}
		TmCmpMain tmCmpMain = new TmCmpMain();
		tmCmpMain.setIdNo(idNo);
		return tmCmpMainDao.queryForList(tmCmpMain);
	}
}
