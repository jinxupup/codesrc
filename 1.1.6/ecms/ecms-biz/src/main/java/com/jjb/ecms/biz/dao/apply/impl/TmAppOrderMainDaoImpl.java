package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jjb.unicorn.facility.util.StringUtils;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: 多卡同申
 * @author xiaolei
 * @date 2019年06月15日 上午11:49:40
 * @version V1.0
 */
@Repository("tmAppOrderMainDao")
public class TmAppOrderMainDaoImpl extends AbstractBaseDao<TmAppOrderMain> implements TmAppOrderMainDao {


	public static final String  selectpage = "com.jjb.ecms.biz.TmAppOrderMainDtoMapper.selectpage";//条件查询


	public static final String  updateByRtfAndTimerState = "com.jjb.ecms.biz.TmAppOrderMainDtoMapper.updateByRtfAndTimerState";

	public static final String  selectByTimerState = "com.jjb.ecms.biz.TmAppOrderMainDtoMapper.selectByTimerState";

	public static final String  selectByAppNoAndRtf = "com.jjb.ecms.biz.TmAppOrderMainDtoMapper.selectByAppNoAndRtf";


	@Override
	public TmAppOrderMain getTmAppOrderMainByAppNo(String AppNo) {
		TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
		tmAppOrderMain.setAppNo(AppNo);
		return queryForOne(tmAppOrderMain);
	}

	@Override
	public List<TmAppOrderMain> getTmAppOrderMainByIdNo(String idNo) {
		TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
		tmAppOrderMain.setIdNo(idNo);
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "desc");
		return queryForList(tmAppOrderMain, queryMap);
	}

	@Override
	public List<TmAppOrderMain> getTmAppOrderMainByTimerState(String state) {
		TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
		tmAppOrderMain.setTimerState(state);
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "desc");
		return queryForList(tmAppOrderMain, queryMap);
	}

	/**
	 * 	//通过timerstate获取多卡同申记录 获取指定数量(10条)
	 * @param state
	 * @return
	 */
	@Override
	public List<TmAppOrderMain> getAppOrderMainByTimerState(String state) {
		Map<String,Object> params  = new HashMap<>();
		if (StringUtils.isNotBlank(state)){
			params.put("timerState",state);
			return queryForList(selectByTimerState, params);
		}
		return null;
	}

	@Override
	public void saveTmAppOrderMain(TmAppOrderMain tmAppOrderMain) {
		if(tmAppOrderMain != null) {
			tmAppOrderMain.setCreateDate(new Date());
			tmAppOrderMain.setUpdateDate(new Date());
			tmAppOrderMain.setJpaVersion(0);
			int i = save(tmAppOrderMain);
			System.out.println(i);
		}
	}

	@Override
	public void updateTmAppOrderMain(TmAppOrderMain tmAppOrderMain) {
		tmAppOrderMain.setUpdateDate(new Date());
		tmAppOrderMain.setJpaVersion(tmAppOrderMain.getJpaVersion() + 1);
		updateNotNullable(tmAppOrderMain);
	}


	/**
	 * 分页查询TM_APP_ORDER_MAIN
	 * @param page 分页类
	 * @return
	 */
	@Override
	public Page<TmAppOrderMain> queryTmAppOrderMainPage(Page<TmAppOrderMain> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmAppOrderMain> p = queryForPageList(selectpage, page.getQuery(), page);
		return p;
	}

	@Override
	public void updateTmAppOrderMainToTimerState(String taskNum, String appNo) {
		if (StringUtils.isBlank(appNo)) {
			return ;
		}
		if (StringUtils.equals(taskNum, "0")) {
			TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
			tmAppOrderMain.setAppNo(appNo);
			TmAppOrderMain orderMain = queryForOne(tmAppOrderMain);
			if (orderMain != null) {
				orderMain.setTimerState("P");
				orderMain.setUpdateDate(new Date());
				orderMain.setJpaVersion(tmAppOrderMain.getJpaVersion() + 1);
				updateNotNullable(orderMain);
			}
		}
	}

	@Override
	public void updateByRtfAndTimerState(String timerState) {
		TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
		tmAppOrderMain.setTimerState(timerState);
		update(updateByRtfAndTimerState, tmAppOrderMain);
	}

	@Override
	public TmAppOrderMain getTmAppOrderMainByAppNoAndRtf(String appNo) {
		Map<String,Object> params  = new HashMap<>();
		if (StringUtils.isNotBlank(appNo)){
			params.put("appNo",appNo);
			List<TmAppOrderMain> tmAppOrderMainList= queryForList(selectByAppNoAndRtf, params);
			for (int i = 0; i<tmAppOrderMainList.size()  ; i++) {
				return  tmAppOrderMainList.get(0);
			}
		}
		return null;
	}


}