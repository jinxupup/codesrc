package com.jjb.ecms.biz.service.index.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.index.WelcomeDao;
import com.jjb.ecms.biz.service.index.WelcomeService;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;


/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
@Service("welcomeService")
public class WelcomeServiceImpl implements WelcomeService {

	@Autowired
	WelcomeDao welcomeDao;

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
