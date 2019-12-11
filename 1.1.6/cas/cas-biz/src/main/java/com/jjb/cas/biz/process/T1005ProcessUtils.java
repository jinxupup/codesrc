package com.jjb.cas.biz.process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.CardFetchMethod;
import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cas.biz.util.CheckReqT1005;
import com.jjb.ecms.biz.activiti.ActivitiUtils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppnoSeqDao;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.dao.commonDialog.AlreadyCardDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.service.dto.T1005.T1005Resp;
import com.jjb.ecms.util.ApplyInfoValidityUtil;
import com.jjb.ecms.util.ChineseToPinYin;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 申请件T1005联机交易提交
 * @author Administrator
 *
 */
@Service
public class T1005ProcessUtils {

	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private TmAppnoSeqDao tmAppnoSeqDao;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private AlreadyCardDao alreadyCardDao;
	@Autowired
	private TmMirCardService tmMirCardService;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private ActivitiUtils activitiUtils;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private TmMirCardDao tmMirCardDao;
//    @Autowired
//    private SendSmsSupport sendSmsSupport;


	private int corePoolSize = 20;
	private int maxPoolSize = 50;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 执行
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Transactional
	public T1005Resp execute(T1005Req req) throws ProcessException {
		logger.debug("T1005Process-处理申请件开始...：");
		T1005Resp resp = new T1005Resp();
		if (req != null && StringUtils.isEmpty(req.getOrg())) {
			req.setOrg(OrganizationContextHolder.getOrg());
		}
		ApplyInfoDto applyInfoDto = buildApplyInfoDto(req);// 构建ApplyNodeLoggingData对象

		String checkMsg = CheckReqT1005.logicCheck(applyInfoDto, req, cacheContext, tmMirCardService, logger);//数据逻辑联动验证
		if (!checkMsg.equals("")) {
			logger.error("验证申请资料信息失败,Msg[" + checkMsg + "]");
			throw new ProcessException(checkMsg);
		}
		try {
			saveApplyInput(applyInfoDto);
		} catch (ProcessException e) {
			logger.error("提交申请件处理失败" + LogPrintUtils.printAppNoEndLog(applyInfoDto.getAppNo(), null, null), e);
			throw new ProcessException(e.getMessage());
		} catch (Exception e) {
			logger.error("提交申请件异常" + LogPrintUtils.printAppNoEndLog(applyInfoDto.getAppNo(), null, null), e);
			throw new ProcessException("系统保存客户申请资料失败，请重试!");
		}
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
		if (tmAppMain == null || StringUtils.isEmpty(tmAppMain.getAppNo())) {

			logger.error("系统未生成申请件编号" + LogPrintUtils.printAppNoEndLog(applyInfoDto.getAppNo(), null, null));
			throw new ProcessException("系统未生成申请件编号，请确认申请相关资料并再次提交！");
		} else {
			resp.setAppNo(tmAppMain.getAppNo());
		}
		// TODO　发送到审批小助手

		return resp;
	}

	/**
	 * 录入保存或提交
	 *
	 * @throws ProcessException
	 */
	private void saveApplyInput(ApplyInfoDto applyInfoDto) throws ProcessException {

		final String org = OrganizationContextHolder.getOrg();
		String contUser = OrganizationContextHolder.getUserNo();
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
		TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();

		TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
		if (tmAppPrimCardInfo != null && StringUtils.isEmpty(contUser)) {
			if (StringUtils.isNotEmpty(tmAppPrimCardInfo.getInputNo())) {
				contUser = tmAppPrimCardInfo.getInputNo();
			} else if (StringUtils.isNotEmpty(tmAppPrimCardInfo.getInputName())) {
				contUser = tmAppPrimCardInfo.getInputName();
			}
			OrganizationContextHolder.setUserNo(contUser);
		}
		final String operId = OrganizationContextHolder.getUserNo(); // 操作员ID
		final String appNo = tmAppMain.getAppNo();
		//卡产品代码
		final String productCd = tmAppMain.getProductCd();
//		final String appType = tmAppMain.getAppType();
//		TmProduct tmProduct = null;
//		if (StringUtils.isNotBlank(productCd)) {
//			tmProduct = cacheContext.getProduct(productCd);
//		}
//		tmAppMain.setRtfState(RtfState.B10.toString());
		tmAppMain.setUpdateDate(new Date()); // 修改日期
		tmAppMain.setCreateDate(new Date()); // 创建日期
		if (StringUtils.isNotEmpty(operId)) {
			tmAppMain.setUpdateUser(operId);
			tmAppMain.setCreateUser(operId); // 创建人
		} else {
			tmAppMain.setUpdateUser(AppConstant.SYS_AUTO.toString());
			tmAppMain.setCreateUser(AppConstant.SYS_AUTO.toString()); // 创建人
		}
		if (tmAppAudit != null && StringUtils.isBlank(tmAppAudit.getIsRealtimeIssuing()))
			tmAppAudit.setIsRealtimeIssuing(Indicator.Y.name());
		applyInfoDto.setTmAppAudit(tmAppAudit);
		// 主卡联系人卡片信息
		if (tmAppPrimCardInfo == null) {
			tmAppPrimCardInfo = new TmAppPrimCardInfo();
		}
		tmAppPrimCardInfo.setAppNo(appNo);
		tmAppPrimCardInfo.setOrg(org);
		tmAppPrimCardInfo.setCreateUser(tmAppMain.getCreateUser());
		tmAppPrimCardInfo.setCreateDate(new Date());

		// 附件信息
		TmAppPrimAnnexEvi tmAppPrimAnnexEvi = applyInfoDto.getTmAppPrimAnnexEvi();
		if (tmAppPrimAnnexEvi != null) {
			tmAppPrimAnnexEvi.setAppNo(appNo);
			tmAppPrimAnnexEvi.setOrg(org);
			tmAppPrimAnnexEvi.setCreateUser(tmAppMain.getCreateUser());
			tmAppPrimAnnexEvi.setCreateDate(new Date());
			applyInfoDto.setTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
		}
		List<TmAppCustInfo> custs = applyInfoDto.getTmAppCustInfoList();
		if (CollectionUtils.sizeGtZero(custs)) {
			for (TmAppCustInfo cust : custs) {
				cust.setAppNo(appNo);
				cust.setOrg(org);
				cust.setCreateUser(tmAppMain.getCreateUser());
				cust.setCreateDate(new Date());
			}
		}

		// ************************
		final Map<String, Serializable> vars = new HashMap<String, Serializable>();
		applyInfoDto.setTmAppNodeInfoRecordMap(new HashMap<String, Serializable>());
		// 设置历史信息
		List<TmAppHistory> tmAppHistoryList = new ArrayList<TmAppHistory>();
		TmAppHistory tmAppHistory = new TmAppHistory();
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, operId == null ? AppConstant.SYS_AUTO : operId,
				RtfState.valueOf(tmAppMain.getRtfState()), null, tmAppMain.getRemark());
		tmAppHistory.setName(tmAppMain.getName());
		tmAppHistory.setIdType(tmAppMain.getIdType());
		tmAppHistory.setIdNo(tmAppMain.getIdNo());
		tmAppHistoryList.add(tmAppHistory);
		applyInfoDto.setTmAppHistoryList(tmAppHistoryList);

		// 设置公共节点信息
		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		if (applyInfoDto.getTmAppNodeInfoRecordMap() == null) {
			applyInfoDto.setTmAppNodeInfoRecordMap(new HashMap<String, Serializable>());
		}
		applyInfoDto.getTmAppNodeInfoRecordMap().put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		try {
			logger.info("保存申请件数据开始----申请编号：" + appNo);
			applyInfoDto.setIpadApplyFalg(Indicator.Y.name());// pad进件标志
			applyInputService.saveApplyInput(applyInfoDto);
			logger.info("保存申请件数据结束----申请编号：" + appNo);
		} catch (ProcessException e) {
			// 如果业务数据提交失败，要把申请状态置为预录入状态（A05），且不发起工作流
			logger.error("申请提交失败-appNo[" + appNo + "]", e);
			exceptionProcess(applyInfoDto, operId, appNo);
			throw new ProcessException("保存客户申请资料失败-AppNo[" + appNo + "]"
					+ e.getMessage());
		} catch (Exception e) {
			// 如果业务数据提交失败，要把申请状态置为预录入状态（A05），且不发起工作流
			logger.error("申请提交失败-appNo[" + appNo + "]", e);
			exceptionProcess(applyInfoDto, operId, appNo);
			throw new ProcessException("保存客户申请资料失败-AppNo" + appNo);
		}
		//非02渠道（微信渠道）的进件放在任务池
		if (tmAppMain != null && StringUtils.equals(tmAppMain.getRtfState(), "B10")
				&& !StringUtils.equals(tmAppMain.getAppSource(), "02")) {
			tmAppMain.setRefuseCode3(StringUtils.valueOf(0));
			applyInputService.updateTmAppMain(tmAppMain);
			return;
		}
		if (tmAppMain != null && !StringUtils.equals(tmAppMain.getRtfState(), "M05")
				&& !StringUtils.equals(tmAppMain.getRtfState(), "A20")) {
			//如果是多卡同申则同步执行，子件不使用线程池
			if (tmAppMain!=null && StringUtils.isNotBlank(tmAppMain.getTaskNum())
					&& !StringUtils.equals(tmAppMain.getTaskNum(), "0")) {
				long start = System.currentTimeMillis();
				try {
					logger.info("发起流程----" + LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
					appCommonUtil.setOrg(org);
					if (StringUtils.isNotEmpty(operId)) {
						OrganizationContextHolder.setUserNo(operId);
					} else {
						OrganizationContextHolder.setUserNo(AppConstant.SYS_AUTO.toString());
					}
					//获取使用流程
					String procdefKey = activitiUtils.getTaskProcessKeyByProductAndAppSource(
							appNo, start, productCd, tmAppMain.getAppSource(), T1005Req.servId);
					if (StringUtils.isNotEmpty(procdefKey)) {
						activitiService.startNewProcess(procdefKey, appNo, vars);
						logger.info("流程结束---" + LogPrintUtils.printAppNoEndLog(appNo, start, T1005Req.servId));
					} else {
						logger.info("没有获取到默认的流程定义，请检查流程！" + LogPrintUtils.printAppNoLog(appNo, null, null));
					}
				} catch (ProcessException e) {
					logger.error("申请审批流程处理失败-appNo[" + appNo + "], tokenId[" + start + "]", e);
//						throw new ProcessException("申请审批流程处理失败-" + e.getMessage());
				}
			} else {
				setThreadSize();//设置线程连接数
				taskExecutor.execute(new Runnable() {
					public void run() {
						long start = System.currentTimeMillis();
						try {
							logger.info("发起流程----" + LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
							appCommonUtil.setOrg(org);
							if (StringUtils.isNotEmpty(operId)) {
								OrganizationContextHolder.setUserNo(operId);
							} else {
								OrganizationContextHolder.setUserNo(AppConstant.SYS_AUTO.toString());
							}
							//获取使用流程
							String procdefKey = activitiUtils.getTaskProcessKeyByProductAndAppSource(
									appNo, start, productCd, tmAppMain.getAppSource(), T1005Req.servId);
							if (StringUtils.isNotEmpty(procdefKey)) {
								activitiService.startNewProcess(procdefKey, appNo, vars);
								logger.info("流程结束---" + LogPrintUtils.printAppNoEndLog(appNo, start, T1005Req.servId));
							} else {
								logger.info("没有获取到默认的流程定义，请检查流程！" + LogPrintUtils.printAppNoLog(appNo, null, null));
							}
						} catch (ProcessException e) {
							logger.error("申请审批流程处理失败-appNo[" + appNo + "], tokenId[" + start + "]", e);
//						throw new ProcessException("申请审批流程处理失败-" + e.getMessage());
						}
					}
				});
			}
		}
	}


	/**
	 * 设置线程池数量
	 */
	private void setThreadSize() {
		TmDitDic threadSize = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_threadPoolSize);
		if (threadSize != null) {
			try {
				corePoolSize = StringUtils.stringToInteger(StringUtils.setValue(threadSize.getRemark(), "20"));
			} catch (Exception e) {
				logger.warn("转换数据库配置的[核心线程数]异常,故默认[20],ErMsg:" + e.getMessage());
			}
			try {
				maxPoolSize = StringUtils.stringToInteger(StringUtils.setValue(threadSize.getIfUsed(), "50"));
			} catch (Exception e) {
				logger.warn("转换数据库配置的[最大线程数]异常,故默认[50],ErMsg:" + e.getMessage());
			}
		}
		// 使用线程发起工作流
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
	}

	/**
	 * 发生了异常处理方式
	 *
	 * @param applyInfoDto
	 * @param operId
	 * @param appNo
	 */
	public void exceptionProcess(ApplyInfoDto applyInfoDto,
								 final String operId, final String appNo) {
		ApplyNodeCommonData applyNodeCommonData = null;
		// 如果业务数据提交失败，要把申请状态置为预录入状态（A05），且不发起工作流
		if (applyInfoDto != null) {
			TmAppMain appMain = applyInfoDto.getTmAppMain();
			TmAppMain dbMian = applyQueryService.getTmAppMainByAppNo(appNo);

			if (dbMian != null) {
				dbMian.setRtfState(RtfState.A05.toString());
				tmAppMainDao.updateTmAppMain(dbMian);
			} else {
				appMain = new TmAppMain();
				appMain.setRtfState(RtfState.A05.toString());
				appMain.setUpdateDate(new Date()); // 修改日期
				appMain.setCreateDate(new Date()); // 创建日期
				if (StringUtils.isNotEmpty(operId)) {
					appMain.setUpdateUser(operId);
					appMain.setCreateUser(operId); // 创建人
				} else {
					appMain.setUpdateUser(AppConstant.SYS_AUTO.toString());
					appMain.setCreateUser(AppConstant.SYS_AUTO.toString()); // 创建人(AppConstant.SYS_AUTO.toString());
				}

				tmAppMainDao.saveTmAppMain(appMain);
			}
			if (applyInfoDto.getTmAppNodeInfoRecordMap() != null) {
				applyNodeCommonData = (ApplyNodeCommonData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA);
			}
			// 设置公共信息
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, appMain);
			applyNodeCommonData.setRtfStateType(RtfState.A05.toString());
			applyInfoDto.getTmAppNodeInfoRecordMap().put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			nodeObjectUtil.insertAllNodeRec(applyInfoDto.getTmAppNodeInfoRecordMap(), appNo);
		}
	}

	/**
	 * 构建申请主表
	 *
	 * @param req
	 * @param appNo
	 * @return
	 */
	private Map<String, Serializable> buildTmAppMain(T1005Req req, String appNo) {
		Map<String, Serializable> map = ApplyInfoValidityUtil.fillEntityFieldMap(req,
				TmAppMain.class, logger);
		map.put(AppConstant.APPNO, appNo);
//		map.put(AppConstant.ApplyFromType, ApplyFromType.B.name());//字段已去掉

		return map;
	}

	/**
	 * 构建申请主表
	 *
	 * @param req
	 * @param appNo
	 * @return
	 */
	private Map<String, Serializable> buildTmAppAudit(T1005Req req, String appNo) {
		Map<String, Serializable> map = ApplyInfoValidityUtil.fillEntityFieldMap(req,
				TmAppAudit.class, logger);
		map.put(AppConstant.APPNO, appNo);

		return map;
	}

	/**
	 * 构建外部风控信息信息
	 *
	 * @param req
	 * @param appNo
	 * @return
	 */
	private TmExtRiskInput buildTmExtRiskInput(T1005Req req, String appNo) {
		TmExtRiskInput tmExtRiskInput = new TmExtRiskInput();
		tmExtRiskInput.setAppNo(req.getAppNo());
		tmExtRiskInput.setExtAuditsRisk(req.getExtAuditsRisk());
		tmExtRiskInput.setExtMultiLoan1m(req.getExtMultiLoan1M());
		tmExtRiskInput.setExtBillCnt6m(req.getExtBillCnt6M());
		tmExtRiskInput.setExtCarrierHitB16m(req.getExtCarrierHitBl6M());
		tmExtRiskInput.setExtGrade(req.getExtScore());
		tmExtRiskInput.setExtAuditsRisk(req.getExtAuditsRisk());
		tmExtRiskInput.setExtIflmk(req.getExtIfLmk());
		tmExtRiskInput.setExtLmkLmtTotal(req.getExtLmkLmtTotal());
		tmExtRiskInput.setExtRefuseCode(req.getExtRefuseCode());
		tmExtRiskInput.setExtEqBehAbn(req.getExtEqBehAbn());
		tmExtRiskInput.setExtHighRiskBl(req.getExtHighRiskBl());
		tmExtRiskInput.setExtContactAbn(req.getExtContactAbn());
		tmExtRiskInput.setExtBl(req.getExtBl());
		tmExtRiskInput.setExtHighRisk(req.getExtHighRisk());
		tmExtRiskInput.setTelOperatorsAbn(req.getTelOperatorsAbn());
		tmExtRiskInput.setInvalidApply(req.getInvalidApply());
		tmExtRiskInput.setExtIsRefuse(req.getExtReseField1());
		tmExtRiskInput.setExtRiskType(req.getExtReseField2());
		tmExtRiskInput.setExtCheckIdFlag(req.getExtCheckIdFlag());  //渠道核身标志
		tmExtRiskInput.setExtCheckIdRs(req.getExtCheckIdRs());   // 渠道核身结果
		tmExtRiskInput.setDeviceType(req.getDeviceType());//设备类型,比如：IOS、安卓、PC
		tmExtRiskInput.setDeviceGps(req.getDeviceGps());//设备GPS定位,	经纬度； S-{经度}N-{纬度}
		tmExtRiskInput.setDeviceGpsCn(req.getDeviceGpsCN());//设备GPS定位地址描述,根据经纬度得到的中文地址描述
		tmExtRiskInput.setWeChatNickName(req.getWeChatNickName());//微信昵称
		tmExtRiskInput.setTelOperatorsType(req.getTelOperatorsType());//运营商类型
		tmExtRiskInput.setTelBelong(req.getTelBelong());//运营商所属地

		return tmExtRiskInput;
	}

	/**
	 * 构建主卡联系人信息
	 *
	 * @param req
	 * @param appNo
	 * @return
	 */
	private Map<String, TmAppContact> buildTmAppContacts(
			T1005Req req, String appNo) {
		Map<String, TmAppContact> map = new HashMap<String, TmAppContact>();

//		// 第一联系人
		Map<String, Serializable> map1 = ApplyInfoValidityUtil.fillEntityFieldMap(
				req, TmAppContact.class, logger);
		map1.put(AppConstant.APPNO, appNo);

		TmAppContact tmAppContactOne = new TmAppContact();
		tmAppContactOne.updateFromMap(map1);

		// 其他联系人
		Map<String, Serializable> map2 = new HashMap<String, Serializable>();
		map2.put(AppConstant.ORG, req.getOrg());
		map2.put(AppConstant.APPNO, appNo);
		map2.put(AppConstant.ContactName, req.getContactOname());
		map2.put(AppConstant.ContactRelation, req.getContactOrelation());
		map2.put(AppConstant.ContactMobile, req.getContactOmobile());
		map2.put(AppConstant.ContactTelephone, req.getContactOtelephone());
		map2.put(AppConstant.contactEmpPhone, req.getContactOempPhone());
		map2.put(AppConstant.ContactGender, req.getContactOgender());

		TmAppContact tmAppContactTwo = new TmAppContact();
		tmAppContactTwo.updateFromMap(map2);
		tmAppContactOne.setContactType("1");
		tmAppContactTwo.setContactType("2");
		map.put(AppConstant.M_CON_ITEM_INFO_PREFIX + 1, tmAppContactOne);
		map.put(AppConstant.M_CON_ITEM_INFO_PREFIX + 2, tmAppContactTwo);
		return map;
	}

	/**
	 * 通过T1005Req构建成ApplyNodeLoggingData对象
	 *
	 * @param req
	 * @return
	 */
	public ApplyInfoDto buildApplyInfoDto(T1005Req req) {
		logger.debug("构建ApplyInfoDto对象...：");
		String appNo = tmAppnoSeqDao.getAppNo(req.getOrg());// 生成appNo
		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		applyInfoDto.setAppNo(appNo);

		Map<String, Serializable> appMainMap = buildTmAppMain(req, appNo);// 申请主表
		TmAppMain tmAppMain = new TmAppMain();
		tmAppMain.updateFromMap(appMainMap);
		if (StringUtils.equals(req.getExtReseField1(), "2")) {
			tmAppMain.setRefuseCode(req.getExtRefuseCode());
			tmAppMain.setRtfState(RtfState.M05.name());
		} else {
			tmAppMain.setRtfState(RtfState.B10.name());
		}


		//外部风控信息
		TmExtRiskInput tmExtRiskInputc = buildTmExtRiskInput(req, appNo);
//		boolean isSendFailureSms=false;//发送拒绝短信
		boolean checkCustIdFial=false;//渠道核身失败
		//渠道核身标志; 0:失败 ；1:通过；2:未核验；3:异常跳过
		String extCheckIdFlag = tmExtRiskInputc.getExtCheckIdFlag();
		//非51进件，则验证渠道是否已经核身
		if(!StringUtils.equals(tmAppMain.getAppSource(), "51")) {
			/*if (StringUtils.equals(extCheckIdFlag,"1")){
					tmAppMain.setRtfState(RtfState.B10.name());
			}else */
			if (StringUtils.equals(extCheckIdFlag,"0") || StringUtils.equals(extCheckIdFlag,"3")){
//				tmAppMain.setRtfState(RtfState.M05.name());
				tmAppMain.setRemark("[系统备注]渠道核身失败");
//				isSendFailureSms=true;
				checkCustIdFial = true;
			}else if (StringUtils.isEmpty(extCheckIdFlag) || StringUtils.equals(extCheckIdFlag,"2")){
//				tmAppMain.setRtfState(RtfState.M05.name());
				tmAppMain.setRemark("[系统备注]渠道未进行有效的核身");
				checkCustIdFial = true;
			}
		}
		if(checkCustIdFial) {
			TmAppFlag flag = new TmAppFlag();
			flag.setAppNo(appNo);
			flag.setFlagType(AppDitDicConstant.FLAG_TYEP_SYS);
			flag.setFlagCode(AppDitDicConstant.FLAG_SYS_005);
			flag.setFlagStatus("A");
			if(StringUtils.isNotEmpty(checkCustIdFial)) {
				TmAclDict dict2 = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.FLAG_TYEP_SYS,AppDitDicConstant.FLAG_SYS_005);
				TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.DICT_TYPE_ExtCheckIdRs,extCheckIdFlag);
				if(dict!=null && StringUtils.isNotEmpty(dict.getCodeName())) {
					flag.setFlagDesc("渠道核身结果:"+dict.getCodeName());
					if(dict2!=null && StringUtils.isNotEmpty(dict2.getValue2())) {
						flag.setFlagLevel(dict2.getValue2());
					}
				}else {
					flag.setFlagDesc("渠道核身结果:"+extCheckIdFlag);
				}
			}else {
				flag.setFlagDesc("渠道未正常进行客户身份核身");
			}
			flag.setCreateUser(AppConstant.SYS_AUTO);
			applyInputService.saveTmAppFlag(flag);
		}


		Map<String, TmAppCustInfo> allCustMap = applyInfoDto.getTmAppCustInfoMap();
		if (allCustMap == null) {
			allCustMap = new HashMap<String, TmAppCustInfo>();
		}
		String appType = req.getAppType();
		String productCd = req.getProductCd();
		if (StringUtils.isNotBlank(appType)) {
			TmAppAudit audit = new TmAppAudit();
			Map<String, Serializable> auditMap = buildTmAppAudit(req, appNo);// 申请主表
			audit.updateFromMap(auditMap);
			audit.setIsRealtimeIssuing(StringUtils.setValue(audit.getIsRealtimeIssuing(), "Y"));
			List<TmAppCustInfo> custList = new ArrayList<TmAppCustInfo>();
			List<TCustInfo> custs = req.getCusts();
			TmAppCustInfo primCust = new TmAppCustInfo();
			if (CollectionUtils.sizeGtZero(custs)) {
				for (int i = 0; i < custs.size(); i++) {
					TCustInfo cust = custs.get(i);
					if (StringUtils.equals(cust.getBscSuppInd(), AppConstant.bscSuppInd_B)) {
						Map<String, Serializable> map = ApplyInfoValidityUtil.fillEntityFieldMap(cust,
								TmAppCustInfo.class, logger);
						map.put(AppConstant.APPNO, appNo);
						map.put(AppConstant.IfSelectedCard, Indicator.N.name());// ipad不支持自选卡号
						TmAppCustInfo dbCust = new TmAppCustInfo();
						if (map != null) {
							dbCust.updateFromMap(map);
							if (StringUtils.isNotEmpty(dbCust.getIdType()) && IdType.I.name().equals(dbCust.getIdType())
									&& StringUtils.isNotEmpty(dbCust.getIdNo())) {
								if (dbCust.getBirthday() == null) {
									//计算生日
									dbCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(dbCust.getIdNo()));
								}
								if (StringUtils.isEmpty(dbCust.getGender())) {
									//计算性别
									dbCust.setGender(IdentificationCodeUtil.getGender(dbCust.getIdNo()));
								}
								if (StringUtils.isEmpty(dbCust.getAge())) {
									//计算年龄
									dbCust.setAge(IdentificationCodeUtil.getAge(dbCust.getIdNo()));
								}
								if (dbCust.getAttachNo() == null) {
									dbCust.setAttachNo(1);
								}
								String ecmsEmb = ChineseToPinYin.getFullSpell(dbCust.getName());
								if (!StringUtils.equals(dbCust.getEmbLogo(), ecmsEmb)) {
									logger.warn("客户" + dbCust.getName() + ",渠道端[" + req.getAppSource() + "]的凸印姓名[" + dbCust.getEmbLogo() + "]与系统侦测到凸印姓名[" + ecmsEmb + "]");
									String remark = "[系统备注]渠道上送的凸印姓名(" + dbCust.getEmbLogo() + ")与系统检测不一致;";
									tmAppMain.setRemark(remark + tmAppMain.getRemark());
									//存入申请件标签
									TmAppFlag tmAppFlag = new TmAppFlag();
									tmAppFlag.setAppNo(appNo);
									tmAppFlag.setFlagType(AppDitDicConstant.FLAG_TYEP_SYS);
									tmAppFlag.setFlagCode(AppDitDicConstant.FLAG_SYS_001);
									tmAppFlag.setFlagDesc("检测凸印姓名不一致(" + dbCust.getEmbLogo() + ")");
									tmAppFlag.setCreateUser(AppConstant.SYS_AUTO);
									try {
										applyInputService.saveTmAppFlag(tmAppFlag);
									} catch (Exception e) {
										logger.error("申请件标签保存失败! "+e.getMessage());
									}
//									dbCust.setEmbLogo(ecmsEmb);
								}

								allCustMap.put(dbCust.getBscSuppInd() + dbCust.getAttachNo(), dbCust);
							}
							if (StringUtils.equals(dbCust.getBscSuppInd(), AppConstant.bscSuppInd_B)) {
								primCust = dbCust;
							}
							custList.add(dbCust);
						}
					}
				}
			}
			applyInfoDto.setTmAppCustInfoMap(allCustMap);
			applyInfoDto.setTmAppCustInfoList(custList);

			//外部风控信息
			TmExtRiskInput tmExtRiskInput = new TmExtRiskInput();
			tmExtRiskInput.updateFromMap(tmExtRiskInputc.convertToMap());
			applyInfoDto.setTmExtRiskInput(tmExtRiskInput);


			//附件证明信息
			Map<String, Serializable> tmAppPrimAnnexEviMap = buildTmAppPrimAnnexEvi(req, appNo);
			TmAppPrimAnnexEvi tmAppPrimAnnexEvi = new TmAppPrimAnnexEvi();
			tmAppPrimAnnexEvi.updateFromMap(tmAppPrimAnnexEviMap);
			applyInfoDto.setTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);

			// 主卡联系人信息 (第一联系人和其他联系人) TmAppContact
			Map<String, TmAppContact> tmAppContactMap = buildTmAppContacts(req, appNo);
			applyInfoDto.setTmAppContactMap(tmAppContactMap);

			//优化，由于待办任务和申请进度查询现至查询tm_app_main表,现为tm_app_main赋值
			tmAppMain.setName(primCust.getName());//主卡姓名
			tmAppMain.setIdNo(primCust.getIdNo());//主卡证件号码
			tmAppMain.setIdType(primCust.getIdType());//主卡证件类型
			tmAppMain.setCellphone(primCust.getCellphone());//主卡移动电话
			tmAppMain.setCorpName(primCust.getCorpName());//主卡公司名称
			tmAppMain.setEmpPhone(primCust.getEmpPhone());//主卡公司电话
			List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = alreadyCardDao.getAlreadyCardsCardInfoDtos(tmAppMain.getIdType(), tmAppMain.getIdNo());
			if (CollectionUtils.sizeGtZero(alreadyCardsCardInfoDtos)) {
				audit.setIsOldCust(Indicator.Y.name());//是否是老客户的标志
			}
			applyInfoDto.setTmAppAudit(audit);
			applyInfoDto.setTmAppMain(tmAppMain);

			// 主卡申请人卡片信息 TM_APP_PRIM_CARD_INFO
			TmAppPrimCardInfo tmAppPrimCardInfo = new TmAppPrimCardInfo();
			Map<String, Serializable> tmAppPrimCardInfoMap = buildTmAppPrimCardInfo(req, appNo);
			tmAppPrimCardInfo.updateFromMap(tmAppPrimCardInfoMap);

			TmProduct tmProduct = cacheContext.getProduct(productCd);
			if (tmProduct == null) {
				throw new ProcessException("没找到对应的卡产品!");
			}
			Map<Object, Object> cacheCFMap = null;
			if (StringUtils.isNotEmpty(productCd)) {
				cacheCFMap = cacheContext.getSimpleProductCardFaceLinkedMap(productCd);
			}
			String cardFace = null;
			for (Object key : cacheCFMap.keySet()) {
				cardFace = StringUtils.valueOf(key);
				break;
			}
			if (StringUtils.isNotEmpty(cardFace)) {
				tmAppPrimCardInfo.setCardfaceCd(cardFace);
			}
			applyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);
		} else {
			throw new ProcessException("申请件[" + appNo + "]申请类型为空!");
		}
//		if(isSendFailureSms) {
//			//插入拒绝短信给TmAppMsgSend
//	        sendSmsSupport.setSmsToDB(tmAppMain, SmsMessageCategory.ECMS02.name());
//		}
		if (StringUtils.isNotEmpty(tmAppMain.getRemark())) {
			TmAppMemo tmAppMemo = new TmAppMemo();
			tmAppMemo.setAppNo(tmAppMain.getAppNo());
			tmAppMemo.setOrg(tmAppMain.getOrg());
			tmAppMemo.setCreateUser(OrganizationContextHolder.getUserNo());
			tmAppMemo.setCreateDate(new Date());
			tmAppMemo.setMemoType(AppConstant.APP_REMARK);
			tmAppMemo.setRtfState(tmAppMain.getRtfState());
			tmAppMemo.setMemoInfo(tmAppMain.getRemark());
			tmAppMemo.setTaskKey("SYSAUTO");
			tmAppMemo.setTaskDesc("系统自动备注");
			Map<String, TmAppMemo> tmAppMemoMapLast = new HashMap<>();
			String key = tmAppMemo.getMemoType() + "_" + tmAppMemo.getTaskKey();
			tmAppMemoMapLast.put(key, tmAppMemo);
			applyInfoDto.setTmAppMemoMapLast(tmAppMemoMapLast);
		}
		return applyInfoDto;
	}


	/**
	 * @param req
	 * @param appNo
	 * @return
	 */
	private Map<String, Serializable> buildTmAppPrimAnnexEvi(T1005Req req, String appNo) {

		Map<String, Serializable> map = ApplyInfoValidityUtil.fillEntityFieldMap(req,
				TmAppPrimAnnexEvi.class, logger);

		map.put(AppConstant.APPNO, appNo);

		return map;
	}

	/**
	 * 构建主卡申请人卡片信息
	 *
	 * @param req
	 * @param appNo
	 * @return
	 */
	private Map<String, Serializable> buildTmAppPrimCardInfo(T1005Req req,
															 String appNo) {

		Map<String, Serializable> map = ApplyInfoValidityUtil.fillEntityFieldMap(req,
				TmAppPrimCardInfo.class, logger);
		String userNo = StringUtils.setValue(req.getInputNo(), req.getSpreaderNo());
		String userNmae = StringUtils.setValue(req.getInputName(), req.getSpreaderName());
		map.put(AppConstant.APPNO, appNo);
		map.put(AppConstant.InputNo, StringUtils.setValue(userNo, AppConstant.SYS_AUTO));
		map.put(AppConstant.InputName, StringUtils.setValue(userNmae, AppConstant.SYS_AUTO));
		if (StringUtils.isNotBlank(userNo)) {
			OrganizationContextHolder.setUserNo(userNo);
		}
		if (StringUtils.isNotBlank(req.getInputName())) {
			OrganizationContextHolder.setUserName(req.getInputName());
		}
		if (req.getInputDate() != null) {
			map.put(AppConstant.InputDate, req.getInputDate());
		} else {
			map.put(AppConstant.InputDate, new Date());
		}
		if (StringUtils.isNotBlank(req.getCardFetchMethod()) && CardFetchMethod.B.toString().equals(req.getCardFetchMethod())) {
			map.put(AppConstant.FetchBranch, req.getOwningBranch());
		}
		map.put(AppConstant.spreaderBranchThree, StringUtils.setValue(req.getSpreaderOrg(), "06101"));
		map.put(AppConstant.spreaderBranchTwo, StringUtils.setValue(req.getSpreaderBank(), ""));
		return map;
	}

	/**
	 * 判断产品是否重复
	 *
	 * @param tokenId
	 * @param idType
	 * @param idNo
	 * @param name
	 * @param productCd
	 * @return
	 */
	public String isExistProduct(String tokenId,String idType, String idNo, String name, String productCd) {
		// 0：未匹配；1：没有有效的客户信息信息；2：同产品存在审批中的申请件；
		// 3：存在审批中产品类型相同的申请；4：核心存在相同的产品卡；5：核心存在相同类型的产品卡
		String exitApply = "0";
		if (StringUtils.isEmpty(idType) && StringUtils.isEmpty(idNo) && StringUtils.isEmpty(name)) {
			logger.warn(LogPrintUtils.printCommonEndLog(tokenId, null)+"无有效客户信息");
			return "无有效客户信息";
		}
		TmDitDic ditOnline = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_repeatIdentityVerification);
		if (ditOnline != null && StringUtils.isNotEmpty(ditOnline.getRemark())
				&& ditOnline.getRemark().equals("Y")) {
			//判断申请卡产品重复
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("idType", idType);
			parameter.put("idNo", idNo);
//			parameter.put("productCd", name);
			List<TmAppMain> tmAppMainList = applyQueryService.getTmAppMainByParm(parameter);
			if (null != tmAppMainList && tmAppMainList.size() > 0) {
				TmDitDic busSuperCard = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_busSuperCardList);
				String businessSuperCard = "500003";//默认产品代码
				if(busSuperCard!=null && StringUtils.isNotEmpty(busSuperCard.getRemark())) {
					businessSuperCard = busSuperCard.getRemark();
				}
				for (int i = 0; i < tmAppMainList.size(); i++) {
					TmAppMain tm1 = tmAppMainList.get(i);
					if(tm1!=null && StringUtils.equals(tm1.getProductCd(), productCd)) {
						logger.warn(LogPrintUtils.printCommonEndLog(tokenId, null)+"同产品存在审批中的申请件");
						return "卡产品重复申请";
					}
					//悦动卡 重复申请校验
					List<String> sportList=new ArrayList<String>();
					sportList.add("000007");
					sportList.add("000008");
					if (sportList.contains(tm1.getProductCd())&&sportList.contains(productCd)){
						logger.warn(LogPrintUtils.printCommonEndLog(tokenId, null)+"悦动卡，同产品存在审批中的申请件");
						return "卡产品重复申请";
					}
					List<String> sameProductList = new ArrayList<String>() ;
					sameProductList.add("000002");
					sameProductList.add("000003");
					sameProductList.add("000005");
					sameProductList.add("000006");

					//如果当前申请是标准信用卡（包括金卡、白金卡），那么判断该客户是否有审批中的商超金卡
					if(tm1!=null && StringUtils.isNotEmpty(productCd)
							&& sameProductList.contains(productCd)
							&& StringUtils.equals(tm1.getProductCd(), businessSuperCard)) {
						TmProduct p1 = cacheContext.getProduct(tm1.getProductCd());
						logger.warn(LogPrintUtils.printCommonEndLog(tokenId, null)+"系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！");
						return "系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！";
					}
					//如果当前是商超金卡申请，那么判断该客户是否有审批中的标准信用卡（包括金卡、白金卡）
					if(tm1!=null && StringUtils.equals(productCd, businessSuperCard)
							&& StringUtils.isNotEmpty(tm1.getProductCd())
							&& sameProductList.contains(tm1.getProductCd())) {
						TmProduct p1 = cacheContext.getProduct(tm1.getProductCd());
						logger.warn("系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！");
						return "系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！";
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("idType", idType);
			map.put("idNo", idNo);
			map.put("name", name);
			map.put("productCd", productCd);

			List<TmMirCard> tmMirCardList1 = tmMirCardDao.getTmMirCardList(map);
			if (null != tmMirCardList1 && tmMirCardList1.size() > 0) {
				return "卡产品重复申请";
			}
		}
		return exitApply;
	}
}
