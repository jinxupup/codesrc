package com.jjb.ams.biz.service.index.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ams.biz.dao.index.AmsWelcomeDao;
import com.jjb.ams.biz.service.index.AmsWelcomeService;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;


/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
@Service("amsWelcomeService")
public class AmsWelcomeServiceImpl implements AmsWelcomeService {

	@Autowired
	AmsWelcomeDao welcomeDao;

	@Override
	@Transactional
	public List<IndexWorkCountDto> getWorkCount() {
		return welcomeDao.getWorkCount();
	}

	@Override
	@Transactional
	public List<IndexWorkCountDto> getWorkUntreatedCount() {
		// TODO Auto-generated method stub
		return welcomeDao.getWorkUntreatedCount();
	}
	
}
