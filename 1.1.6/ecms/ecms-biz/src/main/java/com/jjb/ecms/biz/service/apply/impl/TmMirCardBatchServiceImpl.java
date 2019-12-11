package com.jjb.ecms.biz.service.apply.impl;


import com.jjb.ecms.biz.dao.apply.TmEcmsBatchDao;
import com.jjb.ecms.biz.dao.apply.TmExcMirCardErrorDao;
import com.jjb.ecms.biz.dao.apply.TmMirCardBatchDao;
import com.jjb.ecms.biz.service.apply.TmMirCardBatchService;
import com.jjb.ecms.infrastructure.TmEcmsBatch;
import com.jjb.ecms.infrastructure.TmMirCardBatch;
import com.jjb.ecms.infrastructure.TmMirCardExce;
import com.jjb.unicorn.facility.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tmMirCardBatchService")
public class TmMirCardBatchServiceImpl implements TmMirCardBatchService {
	
	@Autowired
	private TmMirCardBatchDao tmMirCardBatchDao;
	@Autowired
	private TmEcmsBatchDao tmEcmsBatchDao;
	@Autowired
	private TmExcMirCardErrorDao tmExcMirCardErrorDao;



	@Override
	@Transactional
	public List<TmMirCardBatch> getTmMirCardBatchList(){
		List<TmMirCardBatch> list = tmMirCardBatchDao.getTmMirCardBatchList();
		return list;
	}

	@Override
	@Transactional
	public Page<TmMirCardBatch> getTmMirCardBatchListPage(Page<TmMirCardBatch> page){
		page = tmMirCardBatchDao.queryTmMirCardBatchListPage(page);
		return page;
	}

	@Override
	@Transactional
	public TmEcmsBatch getTmEcmsBatch(TmEcmsBatch tmEcmsBatchs){
		TmEcmsBatch tmEcmsBatch = tmEcmsBatchDao.getTmEcmsBatch(tmEcmsBatchs);
		return tmEcmsBatch;
	}

	@Override
	@Transactional
	public List<TmEcmsBatch> getTmEcmsBatchList(){
		List<TmEcmsBatch> list = tmEcmsBatchDao.getTmEcmsBatchList();
		return list;
	}

	@Override
	@Transactional
	public void saveTmMirCardBatch(TmMirCardBatch tmMirCardBatch){
		tmMirCardBatchDao.saveTmMirCardBatch(tmMirCardBatch);
	}

	@Override
	@Transactional
	public void saveTmEcmsBatch(TmEcmsBatch tmEcmsBatch){
		tmEcmsBatchDao.saveTmEcmsBatch(tmEcmsBatch);
	}

	@Override
	@Transactional
	public void updateTmEcmsBatch(TmEcmsBatch tmEcmsBatch){
		tmEcmsBatchDao.updateTmEcmsBatch(tmEcmsBatch);
	}

	@Override
	@Transactional
	public void saveTmExcMirCardError(TmMirCardExce tmExcMirCardError){
		tmExcMirCardErrorDao.saveTmExcMirCardError(tmExcMirCardError);
	}

}
