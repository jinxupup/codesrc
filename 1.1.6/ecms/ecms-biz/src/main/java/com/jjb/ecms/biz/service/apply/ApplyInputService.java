package com.jjb.ecms.biz.service.apply;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmTaskTransfer;

/**
 * @Description: 申请件信息保存更新业务处理定义接口
 * @author JYData-R&D-Big Star
 * @date 2016年9月1日 下午7:11:07
 * @version V1.0
 */
public interface ApplyInputService {
	/**
	 * public List<TmAppMain> select100(Map<String, Object> parameter) throws ProcessException{
	 */
	public List<TmAppMain> select100(Map<String, Object> parameter);

	/**
	 * 修改或保存申请件信息
	 * @param applyInfoDto
	 */
	public void saveOrUpdateApplyInput(ApplyInfoDto applyInfoDto);

	/**
	 * 保存申请件信息
	 */
	public void saveApplyInput(ApplyInfoDto applyInfoDto)throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	/**
	 * 更新申请件信息
	 * @param applyInfoDto
	 * @param flag
	 */
	public void updateApplyInput(ApplyInfoDto applyInfoDto)throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	/**
	 * 删除预录入申请信息
	 * @param applyInfoPreDto
	 * 根据applyInfoPreDto获取申请件编号appNo
	 * 申请类型appType
	 * 姓名name
	 * 证件类型idType
	 * 证件号码idNo
	 */
	public void deleteApplyInput(ApplyInfoPreDto applyInfoPreDto);

	/**
	 * 跟新业务主表信息
	 * @param tmAppMain
	 */
	public void updateTmAppMain(TmAppMain tmAppMain);
	/**
	 * 获取appNo
	 * @return
	 */
	public String getAppNo();

	/**
	 * 插入备注备忘数据
	 * @param appMemo
	 */
	public void saveTmAppMemo(TmAppMemo appMemo);
	/**
	 * 插入或更新 任务转移记录表
	 * @param tmTaskTransfer
	 * @param source判断是获取还是完成
	 */
	public void saveOrUpdateTmTaskTransfer(TmTaskTransfer tmTaskTransfer, String source);

	/**
	 * 更新联系人信息
	 * @param tmAppContactInfoMap
	 * @param appNo
	 */
	public void updateTmAppContactInfoMap(Map<String , TmAppContact> tmAppContactInfoMap,String appNo);

	/**
	 * 保存或者更新第三方风控信息与处罚规则详情
	 * @param applyInfoDto
	 */
	public void saveOrUpdateTmExtRiskInfo(ApplyInfoDto applyInfoDto);


	/**
	 * 更新TmAppPrimAnnexEvi
	 * @param tmAppPrimAnnexEvi_new
	 */
	void updateTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi_new);
	/**
	 * 更新TmAppPrimCardInfo
	 * @param TmAppPrimCardInfo
	 */
	void updateTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo);

	/**
	 * 保存TmAppPrimAnnexEvi
	 * @param tmAppPrimAnnexEvi
	 */
	void saveTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi);
	/**
	 * 保存TmAppPrimCardInfo
	 * @param tmAppPrimCardInfo
	 */
	void saveTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo);
	/**
	 * 保存审批历史记录
	 * @param tmAppHistory
	 */
	public void saveTmAppHistory(TmAppHistory tmAppHistory);
	/**
	 * 更新客户信息
	 * @param custInfo
	 */
	public void updateTmAppCustInfo(TmAppCustInfo custInfo);
	 /**
     * 删除申请件标签
     *
     * @param tmAppFlagList
     */
    public void deleteTmAppFlag(List<TmAppFlag> tmAppFlagList);
    /**
     * 批量保存和删除申请件标签
     * 如果本次新增的标签列表在系统中已经存在，则不会再次新增或更新其原来的数据记录
     * @param tmAppFlag
     */
    public void saveOrDelTmAppFlagList(String appNo,List<TmAppFlag> tmAppFlagList);
    /**
     * 保存申请件标签
     *
     * @param tmAppFlag
     */
    public void saveTmAppFlag(TmAppFlag tmAppFlag);

}
