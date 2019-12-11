package com.jjb.ecms.biz.service.approve;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.AppplyCompareInfoForReviewDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.model.Page;


/**
 * 录入复核
 * 
 * @author BIG.K.K
 *
 */
public interface ApplyInputReviewService {


	/**
	 * 保存复核信息
	 * 
	 * @param tmAppMain 更新状态
	 * @param tmAppPrimCardInfo 更新复核人信息
	 * @param tmAppMemo 保存复核人备注
	 * @param reviewMap 保存主附卡复合字段的值
	 * @return
	 */
	public void saveApplyReviewInfo(TmAppMain tmAppMain, TmAppPrimCardInfo tmAppPrimCardInfo, TmAppMemo tmAppMemo, Map<String , Map<String , String>>reviewMap);

	/**
	 * 提交复核信息
	 * @param taskId 任务Id
	 * @param tmAppMain 更新状态
	 * @param tmAppHistory 更新历史信息
	 * @param applyNodeCommonData 保存节点数据信息
	 * @param tmAppAudit 进件审计信息
	 */
	public void applyInputReviewSubmit(String taskId, TmAppMain tmAppMain, TmAppHistory tmAppHistory, ApplyNodeCommonData applyNodeCommonData,TmAppAudit tmAppAudit);

	/**
	 * 退回复核
	 * @param appNo
	 * @param taskId
	 * @param appType
	 * @param tmAppMemo 保存复核人备注信息
	 */
	public void returnBackApplyReviewInfo(String appNo, String taskId, String appType, TmAppMemo tmAppMemo);


	/**
	 * @param page
	 * @param appNo
	 * @return
	 */
	public Page<TmAppModifyHis> getModifyHistoryInfoPage(Page<TmAppModifyHis> page, String appNo);

	/**
	 * 比对信息
	 * @param appNo 申请件编号
	 * @param appType 申请类型
	 * @param attachNum 附卡的数目
	 * @return 
	 */
	public Map<String , List<AppplyCompareInfoForReviewDto>> compareInfo(String appNo, String appType);

}
