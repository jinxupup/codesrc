package com.jjb.ecms.biz.dao.apply;


import java.util.List;
import java.util.Map;

import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 申请录入信息申请主表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
public interface TmAppMainDao extends BaseDao<TmAppMain>{

	/**
	 * 影像转移获取main
	 * @param parameter
	 * @return
	 */
	public List<TmAppMain> select100(Map<String, Object> parameter);


	/**
	 * 根据申请件编号appNo获取申请主表的信息
	 * @param appNo
	 * @return
	 */
	public TmAppMain getTmAppMainByAppNo(String appNo);

	/**
	 * 批量时更新主申请表状态
	 * @param tmAppMain
	 */
	public void updateApplyStatus() ;

	/**
	 * 保存申请主表信息
	 * @param tmAppMain
	 */
	public void saveTmAppMain(TmAppMain tmAppMain) ;

	/**
	 * 更新申请主表信息
	 * @param tmAppMain
	 */
	public void updateTmAppMain(TmAppMain tmAppMain) ;

	/**
	 * 删除申请主表信息
	 * @param tmAppMain
	 */
	public void deleteTmAppMain(TmAppMain tmAppMain) ;

	public void updateNotNullTmAppMain(TmAppMain tmAppMain) ;


	/**
	 * 重复申请判定-是否已在征审系统中存在
	 * @param corpName
	 * @return
	 */
	public List<TmAppMain> getApsSystemApply(Map<String, Object> parameter);

	public Boolean fetchOldUserAndCheckParameter(String idType, String idNo);

	/**
	 * 根据证件类型或者证件证件号码获取客户最新的复核完成之后的申请件
	 * @param idType
	 * @param idNo
	 * @return
	 */
	public TmAppMain getReviewFinished(String idType, String idNo);

	/**
	 * 预录入重复判断
	 * @param corpName
	 * @return
	 */
	public List<TmAppMain> getTmAppMainByParam(Map<String, Object> parameter);

	/**
	 * 只查询tm_app_main表数据，只用TM_APP_MAIN做查询条件
	 * @param parameter
	 * @return
	 */
	public List<TmAppMain> getOnlyTmAppMainByParam(Map<String, Object> parameter);

	List<TmAppMain> getTmAppMainMkCarfEx();

	List<TmAppMain> getTmAppMianMkCardAgain();

	/**
	 * 获取在同卡同申的时候已经通过预审的父类申请件
	 * @return
	 */
	List<TmAppMain>  getApplyJobPreChecked();

	/**
	 * 多卡同申时查询满足taskNum字段条件的申请件
	 * @param taskNum
	 * @return
	 */
	List<TmAppMain>  getApplyJobToPreCheck(String taskNum);

	/**
	 * @Author:shiminghong
	 * @Description : 定时处理进件任务(查询每次处理的数量)
	 */
	Page<TmAppMain> getTheNumberOfTask(Page<TmAppMain>  page);

}