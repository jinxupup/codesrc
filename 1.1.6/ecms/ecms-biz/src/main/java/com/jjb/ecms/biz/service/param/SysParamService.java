package com.jjb.ecms.biz.service.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.facility.model.Page;


/**
 * @description cas系统参数配置
 * @author hn
 * @date 2016年8月29日14:38:00 
 */
public interface SysParamService {

	/*
	 * 获取所有的cas系统参数 
	 */
	Page<TmDitDic> getPage(Page<TmDitDic> page,TmDitDic ditDic);
	
	/*
	 * 保存系统参数
	 */
	void saveTmDitDic(TmDitDic tmDitDic);
	/*
	 * 查询一条参数 
	 */
	TmDitDic getTmDitDic(int id);
	/*
	 * 编辑参数 
	 */
	void editTmDitDic(TmDitDic tmDitDic);
	/*
	 * 删除参数 
	 */
	void deleteTmDitDic(int id);

	/**
	 * 根据条件获取参数数据
	 * @param ditDic
	 * @return
	 */
	List<TmDitDic> getTmDitDic(TmDitDic ditDic);

	/**
	 * @Author smh
	 * @Description TODO 获取自动分配任务列表
	 * @Date 2018/12/20 14:20
	 */
	public Page<TmDitDic> getListPage(Page<TmDitDic> page);

}
