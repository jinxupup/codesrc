package com.jjb.ecms.biz.dao.apply.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;


@Repository("tmAppMainDao")
public class TmAppMainDaoImpl extends AbstractBaseDao<TmAppMain> implements TmAppMainDao{
	
	//private static final String selectAll = "com.jjb.ecms.infrastructure.mapping.TmAppMainMapper.selectAll";
	//private static final String selectS4000 = "com.jjb.ecms.infrastructure.mapping.TmAppMainMapper.selectS4000";
	private static final String updateApplyStatus = "com.jjb.ecms.biz.ApplyTmAppMain.updateApplyStatus";
	public static final String selectApsSystemAppByParam = "com.jjb.ecms.biz.ApplyTmAppMain.selectApsSystemAppByParam";//重复申请判定-是否已在征审系统中存在
	public static  final String  fetchOldUserInfo="com.jjb.ecms.biz.ApplyTmAppMain.fetchOldUserInfo";
	private static final String selectReviewFinished = "com.jjb.ecms.biz.ApplyTmAppMain.selectReviewFinished";// 获取复核完成的申请件
	private static final String selecTmAppMainByParam = "com.jjb.ecms.biz.ApplyTmAppMain.selecTmAppMainByParam";// 预录入重复判断
	// 根据条件查询tm_app_main表，后续有需要的同志可直接在后面加条件，请不要再加方法了
	private static final String selectOnlyTmAppMainByParam = "com.jjb.ecms.biz.ApplyTmAppMain.selectOnlyTmAppMainByParam";
	//分步查询
	private static final String select100 = "com.jjb.ecms.biz.ApplyTmAppMain.select100";
	private final String MkCardExSqlId = "com.jjb.ecms.biz.ApplyTmAppMain.selectMkCardexList";
	private final String MkCardAgainSqlId = "com.jjb.ecms.biz.ApplyTmAppMain.selectMkCardAgain";
	private final String PreCheckedSqlId="com.jjb.ecms.biz.ApplyTmAppMain.PreCheckedSqlId";
	private final String ToPreCheckId ="com.jjb.ecms.biz.ApplyTmAppMain.ToPreCheckId";
	private static final String selectApplyTask = "com.jjb.ecms.biz.ApplyTmAppMain.selectApplyTask";


	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 影像转移  分步查询
	 * wxl
	 * @param parameter
	 * @return
	 * @throws ProcessException
	 */
    @Override
	public List<TmAppMain> select100(Map<String, Object> parameter) throws ProcessException{
		if(StringUtils.isEmpty(parameter)){
			logger.info("rownum,为空!!!");
			throw new ProcessException("rownum,为空!!!");
		}
		List<TmAppMain> tmAppMainList=queryForList(select100,parameter);

		return tmAppMainList;
	}



	@Override
	public TmAppMain getTmAppMainByAppNo(String appNo) throws ProcessException{
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmAppMain tmAppMain = new TmAppMain();
		tmAppMain.setAppNo(appNo);		

		return queryByKey(tmAppMain);
	}

	@Override
	public void saveTmAppMain(TmAppMain tmAppMain) {
		if(tmAppMain!=null){
			if(tmAppMain.getCreateDate()==null){
				tmAppMain.setCreateDate(new Date());
			}
			if(StringUtils.isEmpty(tmAppMain.getCreateUser())){
				tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
			}
			save(tmAppMain);
		}
		
	}

	@Override
	public void updateTmAppMain(TmAppMain tmAppMain) {
		// TODO Auto-generated method stub
		tmAppMain.setUpdateDate(new Date());
		tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
		update(tmAppMain);
	}

	@Override
	public void deleteTmAppMain(TmAppMain tmAppMain) {
		// TODO Auto-generated method stub		
		if(tmAppMain!=null && StringUtils.isNotEmpty(tmAppMain.getAppNo())){
			deleteByKey(tmAppMain);
		}
		
	}

	/* (non-Javadoc)
	 * @see TmAppMainDao#updateNotNullTmAppMain(TmAppMain)
	 */
	@Override
	public void updateNotNullTmAppMain(TmAppMain tmAppMain) {
		updateNotNullable(tmAppMain);
		
	}
	/**
	 * 重复申请判定-是否已在征审系统中存在
	 * @param corpName
	 * @return
	 */
	@Override
	public List<TmAppMain> getApsSystemApply(Map<String, Object> parameter) {
		if(parameter!=null && parameter.size()>0){
			return queryForList(selectApsSystemAppByParam, parameter);
		}
		return null;
	}
	
	
	/***
	 * 查询该申请人是否以前有过成功申请
	 */
	@Override
	public Boolean fetchOldUserAndCheckParameter(String idType, String idNo) {
		Map<String, Object> parameters=new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(idType)){
			parameters.put("idType", idType);
		}
		if(StringUtils.isNotEmpty(idNo)){
			parameters.put("idNo", idNo);
		}
		if(!parameters.isEmpty()&&parameters.size()>0){
			List<TmAppMain> tmAppMainList=queryForList(fetchOldUserInfo,parameters);
			if(tmAppMainList!=null&&tmAppMainList.size()>0){
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see TmAppMainDao#updateApplyStatus()
	 */
	@Override
	public void updateApplyStatus() {
		TmAppMain tmAppMain =new TmAppMain();
		update(updateApplyStatus, tmAppMain);
		
	}
	
	/**
	 * 根据证件类型或者证件证件号码获取客户最新的复核完成之后的申请件
	 */
	@Override
	public TmAppMain getReviewFinished(String idType, String idNo){
		Map<String, Object> params=new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(idType) && StringUtils.isNotEmpty(idNo)){
			params.put("idType", idType);
			params.put("idNo", idNo);
			params.put("_SORT_NAME", "createDate");
			params.put("_SORT_ORDER", "desc");
			return queryForOne(selectReviewFinished, params);
		}
		return null;
	}
	/**
	 * 预录入重复判断
	 */
	@Override
	public List<TmAppMain> getTmAppMainByParam(Map<String, Object> parameter) {
		if(parameter!=null && parameter.size()>0){
			return queryForList(selecTmAppMainByParam, parameter);
		}
		return null;
	}

	/**
	 * 只查询tm_app_main表数据，只用TM_APP_MAIN做查询条件
	 * @param parameter
	 * @return
	 */
	@Override
	public List<TmAppMain> getOnlyTmAppMainByParam(Map<String, Object> parameter) {
		if(parameter!=null && parameter.size()>0){
			return queryForList(selectOnlyTmAppMainByParam, parameter);
		}
		return null;
	}
	@Override
	public List<TmAppMain> getTmAppMainMkCarfEx() {
		Map<String, Object> map = new HashMap<>();
		List<TmAppMain> tmAppMainList = queryForList(MkCardExSqlId, map);
		return tmAppMainList;
	}

	@Override
	public List<TmAppMain> getTmAppMianMkCardAgain() {
		Map<String,Object> map = new HashMap<>();
		List<TmAppMain> tmAppMainList=queryForList(MkCardAgainSqlId,map);
		return tmAppMainList;
	}

	/**
	 * 获取在同卡同申的时候已经通过预审的父类申请件
	 * @return
	 */
	@Override
	public List<TmAppMain> getApplyJobPreChecked() {
		Map<String,Object> map = new HashMap<>();
		List<TmAppMain> tmAppMainList = queryForList(PreCheckedSqlId,map);
		return  tmAppMainList;
	}

	/**
	 * 多卡同申时查询满足taskNum字段条件的申请件
	 * @param taskNum
	 * @return
	 */
	@Override
	public List<TmAppMain> getApplyJobToPreCheck(String taskNum) {
		Map<String,Object> params  = new HashMap<>();
		if (StringUtils.isNotBlank(taskNum)){
			params.put("taskNum",taskNum);
			return queryForList(ToPreCheckId, params);
		}
		return null;
	}

	@Override
	public Page<TmAppMain> getTheNumberOfTask(Page<TmAppMain> page) {
		// TODO Auto-generated method stub
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmAppMain> p = queryForPageList(selectApplyTask, page.getQuery(), page);
		return p;
	}


}

