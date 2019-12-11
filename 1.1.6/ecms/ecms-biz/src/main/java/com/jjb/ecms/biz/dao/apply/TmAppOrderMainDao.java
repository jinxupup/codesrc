package com.jjb.ecms.biz.dao.apply;


import java.util.List;

import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 多卡同申
 * @author xiaolei
 * @date 2019年06月15日 上午11:49:40
 * @version V1.0
 */
public interface TmAppOrderMainDao extends BaseDao<TmAppOrderMain>{
	
	/**
	 * 根据申请件编号appNo获取数据
	 * @param AppNo
	 * @return TmAppOrderMain
	 */
	TmAppOrderMain getTmAppOrderMainByAppNo(String AppNo);

	/**
	 * 根据身份证idNo获取数据
	 * @param idNo
	 * @return List<TmAppOrderMain>
	 */
	List<TmAppOrderMain> getTmAppOrderMainByIdNo(String idNo);

	//通过timerstate获取多卡同申记录
	List<TmAppOrderMain> getTmAppOrderMainByTimerState(String state);
	//通过timerstate获取多卡同申记录 获取指定数量(10条)
	List<TmAppOrderMain> getAppOrderMainByTimerState(String state);

	/**
	 * 保存
	 * @param tmAppOrderMain
	 */
	void saveTmAppOrderMain(TmAppOrderMain tmAppOrderMain);
	

	/**
	 * 更新
	 * @param tmAppOrderMain
	 */
	void updateTmAppOrderMain(TmAppOrderMain tmAppOrderMain);


	/**
	 * 分页查询TM_APP_ORDER_MAIN
	 * @param page
	 * @return
	 */
	public Page<TmAppOrderMain> queryTmAppOrderMainPage(Page<TmAppOrderMain> page);

	/**
	 * 修改多卡同申状态
	 * @param taskNum
	 * @param appNo
	 */
	public void updateTmAppOrderMainToTimerState(String taskNum, String appNo) ;

	/**
	 * 查找多卡同申审批失败(M05)的主件并且多卡同申表里定时器状态为W的件,并将其多卡同申表里的定时器状态修改为P
	 * @param timerState
	 * @return
	 */
	public void updateByRtfAndTimerState(String timerState);

	/**
	 * 查询时多卡同申的主件并且状态为B16，F03，B28
	 * @param appNo
	 * @return
	 */
	public TmAppOrderMain getTmAppOrderMainByAppNoAndRtf(String appNo);
}