package com.jjb.ecms.biz.service.commonDialog;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppModifyHis;

/**
 * @description: 申请信息修改弹窗serivce
 * @author -BigZ.Y
 * @date 2016年9月21日 下午3:00:27 
 */
public interface ApplyUpdateHistoryService {
	/**
	 * 获取修改历史信息
	 * @param appNo
	 * @return
	 */
	List<TmAppModifyHis> getTmAppModifyHisList(String appNo);

}
