package com.jjb.cas.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.cas.biz.dao.TmAppBatchDao;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: 批量步骤用到的查询数据库
 * @author JYData-R&D-Big Star
 * @date 2016年10月9日 下午1:46:08
 * @version V1.0  
 */
@Repository("r2501BatchDao")
public class TmAppBatchDaoImpl extends AbstractBaseDao<TmAppMain> implements TmAppBatchDao {
	
	private static final String r0001CreateApply = "com.jjb.ecms.biz.TmAppBatchMapper.r0001CreateApply";
	private static final String r2001ApplySucceedReport = "com.jjb.ecms.biz.TmAppBatchMapper.r2001ApplySucceedReport";
	private static final String tmAppMainByRtfState = "com.jjb.ecms.biz.TmAppBatchMapper.tmAppMainByRtfState";
	private static final String tmAppAttachFail = "com.jjb.ecms.biz.TmAppBatchMapper.tmAppAttachFail";
	private static final String tmAppRfe = "com.jjb.ecms.biz.TmAppBatchMapper.tmAppRfe";

	@Override
	public List<String> getTmAppMainByRtfState() {
		Query query = new Query();
		List<String> q = getSqlSession().selectList(tmAppMainByRtfState,query);
		return q;
	}
	

	@Override
	public List<String> getR2001AppNoList() {
		Query query = new Query();
		List<String> appNoList = getSqlSession().selectList(r2001ApplySucceedReport,query);
		return appNoList;
	}
	
	@Override
	public List<String> getR0001AppNoList() {
		Query query = new Query();
		List<String> appNoList = getSqlSession().selectList(r0001CreateApply,query);
		return appNoList;
	}

	@Override
	public List<String> getTmAppAttachFail() {
		Query query = new Query();
		List<String> appNoList = getSqlSession().selectList(tmAppAttachFail,query);
		return appNoList;
	}


	@Override
	public List<Integer> getTmAppRfe() {
		Query query = new Query();
		List<Integer> appNoList = getSqlSession().selectList(tmAppRfe,query);
		return appNoList;
	}


}
