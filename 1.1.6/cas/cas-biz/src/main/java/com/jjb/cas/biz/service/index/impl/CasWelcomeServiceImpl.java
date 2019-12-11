package com.jjb.cas.biz.service.index.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.cas.biz.dao.index.CasWelcomeDao;
import com.jjb.cas.biz.service.index.CasWelcomeService;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;


/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
@Service("casWelcomeService")
public class CasWelcomeServiceImpl implements CasWelcomeService {

	@Autowired
	CasWelcomeDao casWelcomeDao;

	@Override
	public List<IndexWorkCountDto> getWorkCount() {
		return casWelcomeDao.getWorkCount();
	}

	@Override
	public List<IndexWorkCountDto> getWorkUntreatedCount() {
		// TODO Auto-generated method stub
		return casWelcomeDao.getWorkUntreatedCount();
	}
	
}
