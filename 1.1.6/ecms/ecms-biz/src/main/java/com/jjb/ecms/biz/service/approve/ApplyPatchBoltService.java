package com.jjb.ecms.biz.service.approve;

import java.util.Map;

import com.jjb.unicorn.facility.model.Query;

/**
 * @description: 补件操作service
 * @author -BigZ.Y
 * @date 2016年9月25日 上午11:55:49 
 */
public interface ApplyPatchBoltService {

	/**
	 * 补件提交
	 * @param query
	 * @param status
	 */
	void submitPatchBoltInfo(Query query, String status);
	/**
	 * 获取补件发起时间及补件时长
	 * @param appNo
	 * @return
	 */
	Map<String, String> getPatchTimeMap(String appNo);
}
