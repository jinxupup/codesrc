
package com.jjb.ecms.biz.service.apply;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmExtTriggerRules;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

/**
 *
 * @Description: 申请件查询
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午6:51:03
 * @version V1.0
 */
public interface ApplyQueryService{
	/**
	 * 根据appNo获取申请件信息
	 * @param appNo
	 * @return
	 */
	public ApplyInfoDto getApplyInfoByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据申请件编号获取进件审计记录信息
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public TmAppAudit getTmAppAuditByAppNo(String appNo) throws ProcessException;
	/**
	 * 根据appNo获取节点信息
	 * @param appNo
	 * @return ApplyInfoDto
	 * @throws ProcessException
	 */
	public ApplyInfoDto getNodeInfoByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据申请件编号与节点类型获取节点信息
	 * @param appNo
	 * @param nodeType
	 * @return Map<String, Serializable>
	 * @throws ProcessException
	 */
	public Map<String, Serializable> getNodeInfoByAppNo(String appNo,String nodeType) throws ProcessException;

	/**
	 * 根据申请件编号获取节点列表数据
	 * @param appNo
	 * @param nodeType
	 * @return
	 * @throws ProcessException
	 */
	public List<Map<String, Serializable>> getNodeInfosByAppNo(String appNo, String nodeType);
	/**
	 * 根据appNo获取申请件主表信息
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public TmAppMain getTmAppMainByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据appNo获取申请件主卡、附卡客户信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public List<TmAppCustInfo> getTmAppCustInfoListByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据appNo获取申请件主卡、附卡客户信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public Map<String,TmAppCustInfo> getTmAppCustInfoMapByAppNo(String appNo) throws ProcessException;

	/**
	 * 获取附卡申请List
	 */
	public List<TmAppCustInfo> getTmAppAttachCustInfoListByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据appNo获取申请件主卡客户信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public TmAppCustInfo getTmAppPrimCustByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据appNo获取申请件主卡、附卡客户信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public Map<String,TmAppCustInfo> getTmAppAttachCustInfoMapByAppNo(String appNo) throws ProcessException;


	/**
	 * 根据appNo获取申请件联系人信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public Map<String, TmAppContact> getTmAppContactByAppNo(String appNo) throws ProcessException;

	/**
	 * 根据appNo获取申请件附件信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public TmAppPrimAnnexEvi getTmAppPrimAnnexEviByAppNo(String appNo) throws ProcessException;


	/**
	 * 根据参数获取备注备忘信息
	 * @param appMemo
	 * @return
	 */
	public List<TmAppMemo> getTmAppMemoByParam(TmAppMemo appMemo);

	/**
	 * 根据申请件编号获取申请件主卡卡片信息及申请件其他信息表数据
	 * @param appNo
	 * @return
	 */
	public TmAppPrimCardInfo getTmAppPrimCardInfoByAppNo(String appNo);

//	/**
//	 * 分期信息查询
//	 * @param appNo
//	 * @return
//	 */
//	public TmAppInstalLoan getTmAppInstalLoanByAppNo(String appNo);

	/**
	 * 第三方风控信息查询
	 * @param appNo
	 * @return
	 */
	public TmExtRiskInput getTmExtRiskInputByAppNo(String appNo);

	/**
	 * 第三方风控信息查询
	 * @param appNo
	 * @return
	 */
	public Map<String,TmExtTriggerRules> getTmExtTriggerRulesByAppNo(String appNo);

	/**
	 * 	获取在同卡同申的时候已经通过预审的父类申请件
	 * @return
	 */
	public List<TmAppMain>   getApplyJobPreChecked();

    /**
     *多卡同申时查询满足taskNum字段条件的申请件
     * @return
     */
	public List<TmAppMain> getApplyJobToPreCheck(String taskNum);
    

	/**
	 * 获取所有已申请的申请件信息
	 * @param applyInfoDto
	 */
	public List<TmAppMain> getTmAppMainByParm(Map<String, Object> parameter);
	/**
	 * @Author:shiminghong
	 * @Description : 定时处理进件任务(查询每次处理的数量)
	 */
	public Page<TmAppMain> getTheNumberOfTask(Page<TmAppMain>  page);
    /**
     * 查找申请件标签
     * @param appNo
     * @return
     */
    public List<TmAppFlag> getTmAppFlagListByAppNo(String appNo);

    /**
     * 根据参数查询申请件标签
     * @param tmAppFlag
     * @return Map<String,TmAppFlag>
     */
    public Map<String,TmAppFlag> getTmAppFlagByParm(TmAppFlag tmAppFlag);
    /**
     * 根据参数查询审批历史
     * @param history
     * @return
     */
    public List<TmAppHistory> getTmAppHistoryList(TmAppHistory history);
    /**
     * 根据申请件编号查询审批历史
     * @param history
     * @return
     */
    public List<TmAppHistory> getTmAppHistoryByAppNo(String appNo);
}