package com.jjb.cmp.biz.service.content;

import java.util.List;

import com.jjb.cmp.infrastructure.TmCmpMain;

/**
 * @ClassName ImageMainService
 * @Description TODO
 * @Author smh
 * Date 2018/12/31 15:23
 * Version 1.0
 */
public interface TmCmpMainService {
	/**
	 * 根据批次号获取内容主表信息
	 *
	 * @param batchNo
	 * @return
	 */
	public TmCmpMain getTmCmpMainByBatchNo(String batchNo);

	TmCmpMain getTmCmpMain(TmCmpMain tmCmpMain);

	public void saveTmCmpMain(TmCmpMain tmCmpMain);

	void updateTmCmpMain(TmCmpMain tmCmpMain);

	public List<TmCmpMain>   getTmCmpMainByIdNo(String idNo);

}
