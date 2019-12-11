package com.jjb.cas.service.impl;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.cas.biz.process.T1005ProcessUtils;
import com.jjb.cas.biz.process.T1011ProcessUtils;
import com.jjb.cas.biz.process.T1012ProcessUtils;
import com.jjb.cas.biz.process.T1013ProcessUtils;
import com.jjb.cas.biz.util.CheckReqT1005;
import com.jjb.cas.service.util.ConvertT1005ReqUtil;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeExtDecisionData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePreCheckData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.api.IPadApplyService;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.service.dto.T1005.T1005Resp;
import com.jjb.ecms.service.dto.T1006.T1006Req;
import com.jjb.ecms.service.dto.T1006.T1006Resp;
import com.jjb.ecms.service.dto.T1008.T1008Req;
import com.jjb.ecms.service.dto.T1008.T1008Resp;
import com.jjb.ecms.service.dto.T1010.T1010Req;
import com.jjb.ecms.service.dto.T1010.T1010Resp;
import com.jjb.ecms.service.dto.T1011.T1011Req;
import com.jjb.ecms.service.dto.T1011.T1011Resp;
import com.jjb.ecms.service.dto.T1012.T1012Req;
import com.jjb.ecms.service.dto.T1012.T1012Resp;
import com.jjb.ecms.service.dto.T1013.T1013Req;
import com.jjb.ecms.service.dto.T1013.T1013Resp;
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author JYData-R&D-Big Star
 * @version V1.0
 * @Description: 征审系统ipad申请联机服务
 * @date 2015年10月12日 上午11:16:51
 */
@Service("iPadApplyServiceImpl")
public class IPadApplyServiceImpl implements IPadApplyService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApplySpreaderInfoService spreaderInfoService;
	@Autowired
	private TmExtRiskInputDao tmExtRiskInputDao;
	@Autowired
	private TmAppOrderMainDao tmAppOrderMainDao;
	private int corePoolSize = 20;
	private int maxPoolSize = 50;
	@Autowired
	private T1005ProcessUtils t1005ProcessUtils;
	@Autowired
	private T1013ProcessUtils t1013ProcessUtils;
	@Autowired
	private T1011ProcessUtils t1011ProcessUtils;
	@Autowired
	private T1012ProcessUtils t1012ProcessUtils;

	@Override
	public T1005Resp T1005(T1005Req reqPrim) {
		logger.debug("ipad申请联机服务开始...：");

		T1005Req req = ConvertT1005ReqUtil.convertReq(reqPrim);//非必输项设置默认值
		appCommonUtil.setOrg(req.getOrg());
		String idType = null;
		String idNo = null;
		String name = null;
		String tokenId = StringUtils.setValue(req.getTokenId(), System.currentTimeMillis());
		// 取出主卡用户二要素
		List<TCustInfo> custs = req.getCusts();
		if (CollectionUtils.sizeGtZero(custs)) {
			for (TCustInfo cust : custs) {
				if (StringUtils.equals(cust.getBscSuppInd(), AppConstant.bscSuppInd_B)) {
					idType = cust.getIdType();
					idNo = cust.getIdNo();
					name = cust.getName();
				}
			}
		}

		// 判断是否多卡重申
		if (req.getProductCd().contains(",")) {
			// 可以拆分的产品编号
			StringBuilder productCds = new StringBuilder();
			StringBuilder productType = new StringBuilder("1");
			StringBuilder productException = new StringBuilder();
			String allProductCd = reqPrim.getProductCd();
			String[] productCdArray = allProductCd.split(",");
			for (String productCd : productCdArray) {
				String existProduct = t1005ProcessUtils.isExistProduct(tokenId,idType, idNo, name, productCd);
				//如果是0则没有相同卡和卡类型的申请
				if (StringUtils.equals(existProduct, "0")) {
					try {
						req.setProductCd(productCd);
						CheckReqT1005.checkNull(req, cacheContext, spreaderInfoService);//必输字段验证和内容格式、长度验证
					} catch (Exception e) {
						productException.append(e.getMessage()).append(",");
						continue;
					}
					productCds.append(productCd).append(",");
					productType.append("0");
				} else {
					productException.append(existProduct).append(",");
				}
			}
			// 保存多卡同申信息基本信息
			TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
			tmAppOrderMain.setIdNo(idNo);
			tmAppOrderMain.setOrg(req.getOrg());
			tmAppOrderMain.setAllProductCds(allProductCd);

			if ("".equals(productCds.toString())){
                tmAppOrderMain.setTimerState("E");
                tmAppOrderMain.setExceptionMsg(productException.toString());
                tmAppOrderMainDao.saveTmAppOrderMain(tmAppOrderMain);
                throw new ProcessException("所有卡产品无法申请，请检查您是否已经持有相同或相同类型的卡产品");
            }

			tmAppOrderMain.setValidProductType(productType.substring(0, productType.length() - 1));
			tmAppOrderMain.setValidProductCds(productCds.substring(0, productCds.length() - 1));
			// 第一笔多卡同申taskNum为0，之后的多卡同申，taskNum为第一笔件的appNo
			req.setTaskNum("0");
			req.setProductCd(productCds.toString().split(",")[0]);
			T1005Resp resp = null;
			try {
				resp = t1005ProcessUtils.execute(req);
			} catch (Exception e) {
				// 保存异常信息
				tmAppOrderMain.setTimerState("E");
				tmAppOrderMain.setExceptionMsg(productException + e.getMessage());
				tmAppOrderMainDao.saveTmAppOrderMain(tmAppOrderMain);
				throw new ProcessException("系统T1005异常", e);
			}
			// 更新多卡同步信息状态和appNo
			tmAppOrderMain.setAppNo(resp.getAppNo());
			tmAppOrderMain.setTimerState("W");
			tmAppOrderMain.setReqJson(JSON.toJSONString(req));
			tmAppOrderMain.setExceptionMsg(productException.toString());
			tmAppOrderMainDao.saveTmAppOrderMain(tmAppOrderMain);
			return resp;
		}
		// 单卡申请流程
		String existProduct = t1005ProcessUtils.isExistProduct(tokenId,idType, idNo, name, req.getProductCd());
		//如果是0则没有相同卡和卡类型的申请
		if (StringUtils.equals(existProduct, "0")) {
			CheckReqT1005.checkNull(req, cacheContext, spreaderInfoService);//必输字段验证和内容格式、长度验证
			return t1005ProcessUtils.execute(req);
		} else {
			throw new ProcessException(existProduct);
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
	 * 接收渠道端提交的“预审操作”
	 *
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Override
	public T1006Resp T1006(T1006Req req) throws ProcessException {
		long start = System.currentTimeMillis();
		if (req == null) {
			logger.error("PID[" + start + "],T1006请求参数不能为空！");
			throw new ProcessException("T1006-请求参数不能为空！");
		}
		appCommonUtil.setOrg(req.getOrg());
		T1006Resp resp = new T1006Resp();
		logger.debug("[T1006]...处理预审确认提交开始...");
		Long tokenId = System.currentTimeMillis();
		String appNo = "";
		String user = "";
		String preRemark = "";
		TmAppMain main = null;
		try {
			checkT1006Req(req);//验证请求参数
			appNo = req.getAppNo();
			user = req.getOpUserNo();
			preRemark = req.getPreRemark();
			OrganizationContextHolder.setUserNo(user);
			String taskId = "";
			main = applyQueryService.getTmAppMainByAppNo(appNo);
			if (main == null) {
				throw new ProcessException("申请件[" + appNo + "]编号无效，未查询到有效申请信息!");
			}
			boolean notActProcess = false;
			//=====验证当前申请件是否可以接受预审结果
			List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(appNo).list();
			if (CollectionUtils.sizeGtZero(taskList)) {
				for (int i = 0; i < taskList.size(); i++) {
					Task task = taskList.get(i);
					taskId = task.getId();
					if (task != null && task.getTaskDefinitionKey() != null
							&& task.getTaskDefinitionKey().equals("applyinfo-pre-check")) {
						notActProcess = true;
						break;
					}
				}
			}
			if (!notActProcess) {
				//TODO: 暂时注释，待上线时放开
				throw new ProcessException("任务或已被更新，请确认当前申请件[" + appNo + "]状态，请勿重复推送!");
			}
			ApplyInfoDto applyInfoDto = new ApplyInfoDto();
			applyInfoDto.setAppNo(appNo);
			applyInfoDto.setTmAppMain(main);
			if (StringUtils.isNotEmpty(req.getCfOwningBranch())) {
				TmAclBranch dbBranch = cacheContext.getTmAclBranchByCode(req.getCfOwningBranch());
				if (dbBranch != null && StringUtils.isNotEmpty(dbBranch.getBranchCode())) {
					main.setOwningBranch(req.getCfOwningBranch());
				}
			}

			//设置附件证明信息数据
			setEviData(req, main);
			setCardInfoData(req, main);

			RtfState nextRtfState = RtfState.B20;//默认通过
			//如果预审提交--确认通过：P
			if (StringUtils.equals(req.getConfirmType(), "R")) {
				//预审拒绝
				nextRtfState = RtfState.A20;
			}
			Map<String, Serializable> A020Map = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A020.name());
			ApplyNodeCheatCheckData cheat = null;
			if (A020Map != null && A020Map.containsKey(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA)) {
				cheat = (ApplyNodeCheatCheckData) A020Map.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA);
			}
			TmProduct product = cacheContext.getProduct(main.getProductCd());
			if (product == null || !StringUtils.equals(product.getProductStatus(), "A")) {
				throw new ProcessException("产品" + main.getProductCd() + "已经失效!");
			}
			if (cheat != null && StringUtils.equals(cheat.getContent(), "通过")) {
				if (main.getSugLmt() != null) {
					main.setAccLmt(main.getSugLmt());
					//nextRtfState = RtfState.H18;
					if (product.getApprovalMaximum() == null || product.getApprovalMaximum().compareTo(main.getAccLmt()) >= 0) {
						nextRtfState = RtfState.B25;
						main.setFileFlag("N");
					} else {
						nextRtfState = RtfState.B20;
						main.setRemark("预审备注-" + preRemark + ";" + "系统备注-系统建议额度[" + main.getAccLmt() + "]大于产品上限额度[" + product.getApprovalMaximum() + "]，故转人工审批");
					}
				} else {
					main.setRemark("预审备注-" + preRemark + ";" + "系统备注-系统建议额度为空，转人工审批");
				}
			}
			main.setImageNum(StringUtils.setValue(req.getImageNum(), main.getImageNum()));
			ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
			ApplyNodePreCheckData preCheckData = new ApplyNodePreCheckData();
			preCheckData.setOpUserNo(req.getOpUserNo());
			preCheckData.setConfirmType(req.getConfirmType());
			preCheckData.setSignFileInd(req.getSignFileInd());
			preCheckData.setIdFileInd(req.getIdFileInd());
			preCheckData.setJobFileInd(req.getJobFileInd());
			preCheckData.setCfOwningBranch(req.getCfOwningBranch());
			preCheckData.setSpreaderType(req.getSpreaderType());
			preCheckData.setPics(JSON.toJSONString(req.getPics()));
			Map<String, Serializable> resultMap = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A070.name());
			if (resultMap != null && resultMap.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)) {
				applyNodeCommonData = (ApplyNodeCommonData) resultMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
			}
			main.setRtfState(nextRtfState.name());
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, main);
			// 插入节点信息ZY
			Map<String, Serializable> nodeData = new HashMap<String, Serializable>();
			nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			nodeData.put(AppConstant.APPLY_NODE_PRE_CHECK_DATA, preCheckData);
			nodeObjectUtil.insertAllNodeRec(nodeData, appNo);
			TmAppHistory history = AppCommonUtil.insertApplyHist(appNo,
					user, nextRtfState, main.getRefuseCode(), preRemark);
			history.setName(main.getName());
			history.setIdType(main.getIdType());
			history.setIdNo(main.getIdNo());
			applyInputService.updateTmAppMain(main);
			applyInputService.saveTmAppHistory(history);
			//保存预审备注
			if (StringUtils.isNotBlank(preRemark)) {
				TmAppMemo tmAppMemo = new TmAppMemo();
				tmAppMemo.setAppNo(appNo);
				tmAppMemo.setMemoType(AppConstant.APP_REMARK);
				tmAppMemo.setTaskKey(EcmsAuthority.CAS_APPLY_PRE_CHECK.name());
				tmAppMemo.setTaskDesc(EcmsAuthority.CAS_APPLY_PRE_CHECK.lab);
				tmAppMemo.setRtfState(nextRtfState.toString());
				tmAppMemo.setMemoInfo(preRemark);
				applyInputService.saveTmAppMemo(tmAppMemo);
			}
			//=======发起后续流程=======//
			logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId, null) + "是否发起工作流:" + notActProcess);
			Map<String, Serializable> vars = new HashMap<String, Serializable>();
			vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			if (notActProcess) {
				activitiService.completeTask(taskId, vars, appNo);
			}
			// 多卡同申预审通过时：修改定时任务状态
			if (main!=null&&StringUtils.isNotBlank(appNo)){
				if (StringUtils.equals(main.getTaskNum(), "0")) {
					logger.info("多卡同申 TmAppOrderMain修改状态开始");
					TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
					tmAppOrderMain.setAppNo(appNo);
					TmAppOrderMain orderMain = tmAppOrderMainDao.queryForOne(tmAppOrderMain);
					if (orderMain != null) {
						orderMain.setTimerState("P");
						orderMain.setUpdateDate(new Date());
						orderMain.setJpaVersion(orderMain.getJpaVersion() + 1);
						tmAppOrderMainDao.updateNotNullable(orderMain);
						logger.info("多卡同申 TmAppOrderMain修改状态结束");
					}
				}
				/*tmAppOrderMainDao.updateTmAppOrderMainToTimerState(main.getTaskNum(), appNo);*/
			}
		} catch (Exception e) {
			logger.error("处理外部风控系统结论数据失败", e);
			throw new ProcessException("T1006-处理失败," + e.getMessage());
		} finally {
			logger.info("[T1006ExtRisk]...处理预审确认提交结束..." + LogPrintUtils.printAppNoEndLog(appNo, tokenId, null));
		}
		return resp;
	}

	private void setEviData(T1006Req req, TmAppMain main) {
		TmAppPrimAnnexEvi evi = applyQueryService.getTmAppPrimAnnexEviByAppNo(main.getAppNo());
		boolean eviNoDB = false;
		if (evi == null) {
			eviNoDB = true;
			evi = new TmAppPrimAnnexEvi();
		}
		evi.setAppNo(main.getAppNo());
		evi.setOrg(main.getOrg());

		evi.setIndSignFile(req.getSignFileInd());
		evi.setIndIdFile(req.getIdFileInd());
		evi.setIndJobFile(req.getJobFileInd());
		if (eviNoDB) {
			evi.setCreateDate(new Date());
			evi.setCreateUser(req.getOpUserNo());
			applyInputService.saveTmAppPrimAnnexEvi(evi);
		} else {
			evi.setUpdateDate(new Date());
			evi.setUpdateUser(req.getOpUserNo());
			applyInputService.updateTmAppPrimAnnexEvi(evi);
		}
	}

	private void setCardInfoData(T1006Req req, TmAppMain main) {
		TmAppPrimCardInfo card = applyQueryService.getTmAppPrimCardInfoByAppNo(main.getAppNo());
		boolean cardInfoNoDB = false;
		if (card == null) {
			cardInfoNoDB = true;
			card = new TmAppPrimCardInfo();
		}
		card.setAppNo(main.getAppNo());
		card.setOrg(main.getOrg());
		card.setSpreaderNo(req.getOpUserNo());
		card.setSpreaderType(StringUtils.setValue(req.getSpreaderType(), card.getSpreaderType()));
		TmAppSprePerBank spreader = new TmAppSprePerBank();
		spreader.setSpreaderNum(req.getOpUserNo());
		spreader.setSpreaderStatus("1");
		//推广人处理
		List<TmAppSprePerBank> sprList = spreaderInfoService.getSpreaderByParam(spreader);
		if (CollectionUtils.sizeGtZero(sprList)) {
			spreader = sprList.get(0);
			if (spreader != null) {
				card.setSpreaderName(StringUtils.setValue(spreader.getSpreaderName(), card.getSpreaderName()));
				card.setSpreaderBranchThree(StringUtils.setValue(spreader.getSpreaderBankId(), card.getSpreaderBranchThree()));
				card.setSpreaderBranchTwo(StringUtils.setValue(spreader.getSpreaderOrg(), card.getSpreaderBranchTwo()));
				card.setSpreaderTelephone(StringUtils.setValue(spreader.getSpreaderPhone(), card.getSpreaderTelephone()));
				card.setSpreaderIsBankEmployee("Y");
			}
		}
		card.setSpreaderBranchThree(StringUtils.setValue(spreader.getSpreaderBankId(), card.getSpreaderBranchThree()));
		if (StringUtils.isEmpty(card.getSpreaderBranchThree())) {
			card.setSpreaderBranchThree(main.getOwningBranch());
		}
		StringBuffer spreaderMode = new StringBuffer();
		if (StringUtils.equals(req.getIdFileInd(), "A")) {
			spreaderMode.append("A");
			spreaderMode.append(",");
			spreaderMode.append("B");
		}
		if (StringUtils.equals(req.getSignFileInd(), "A")) {
			if (StringUtils.isNotBlank(spreaderMode.toString())) {
				spreaderMode.append(",");
			}
			spreaderMode.append("C");
		}
		if (StringUtils.equals(req.getJobFileInd(), "A")) {
			if (StringUtils.isNotBlank(spreaderMode.toString())) {
				spreaderMode.append(",");
			}
			spreaderMode.append("D");
		}
		card.setSpreaderMode(StringUtils.setValue(spreaderMode.toString(), card.getSpreaderMode()));
		card.setPreNo(card.getSpreaderNo());
		card.setPreName(card.getSpreaderName());
		card.setPreTelephone(card.getSpreaderTelephone());
		card.setPreBranchThree(card.getSpreaderBranchThree());
		if (cardInfoNoDB) {
			card.setCreateDate(new Date());
			card.setCreateUser(req.getOpUserNo());
			applyInputService.saveTmAppPrimCardInfo(card);
		} else {
			applyInputService.updateTmAppPrimCardInfo(card);
		}
	}

	/**
	 * 验证-T1006-请求参数
	 *
	 * @param req
	 */
	private void checkT1006Req(T1006Req req) {
		if (req == null) {
			throw new ProcessException("T1006-请求参数不能为空");
		}
		if (StringUtils.isEmpty(req.getAppNo())) {
			throw new ProcessException("T1006-请求参数-[申请件编号]不能为空");
		}
		if (StringUtils.isEmpty(req.getOpUserNo())) {
			throw new ProcessException("T1006-请求参数-[操作员工号]不能为空");
		}
		if (StringUtils.isEmpty(req.getConfirmType())) {
			throw new ProcessException("T1006-请求参数-[操作员类型]不能为空");
		}

	}

	/**
	 * @Author smh
	 * @Description TODO T1008接收渠道端提交的“外部决策结果”
	 * @Date 2018/11/23 15:11
	 */
//	@Override
	public T1008Resp T1008(T1008Req t1008Req) throws ProcessException {
		long start = System.currentTimeMillis();
		if (t1008Req == null) {
			logger.error("PID[" + start + "],T1008请求参数不能为空！");
			throw new ProcessException("T1008-请求参数不能为空！");
		}
		appCommonUtil.setOrg(t1008Req.getOrg());
		T1008Resp resp = new T1008Resp();
		logger.debug("[T1008]...处理外部决策结果提交开始...");
		Long tokenId = System.currentTimeMillis();
		String appNo = "";
//		String user = "";
		TmAppMain main = null;
		try {
			checkT1008Req(t1008Req);//验证请求参数
			appNo = t1008Req.getAppNo();
			String taskId = "";
			main = applyQueryService.getTmAppMainByAppNo(appNo);
			if (main == null) {
				throw new ProcessException("申请件[" + appNo + "]编号无效，未查询到有效申请信息!");
			}
			TmProduct product = cacheContext.getProduct(main.getProductCd());
			if (product == null) {
				throw new ProcessException("产品无效，未查询到有效产品信息!");
			}
			boolean notActProcess = false;
			//=====验证当前申请件是否可以接受预审结果
			List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(appNo).list();
			if (CollectionUtils.sizeGtZero(taskList)) {
				for (int i = 0; i < taskList.size(); i++) {
					Task task = taskList.get(i);
					taskId = task.getId();
					if (task != null && task.getTaskDefinitionKey() != null
							&& task.getTaskDefinitionKey().equals("applyinfo-51cc-risk")) {
						notActProcess = true;
						break;
					}
				}
			}
			if (!notActProcess) {
				if (StringUtils.equals(main.getRtfState(), RtfState.M05.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.H22.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.H25.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.H17.name())) {
					throw new ProcessException("当前申请件已被终审拒绝，请勿重复推送!");
				} else if (StringUtils.equals(main.getRtfState(), RtfState.P05.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.N05.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.Q05.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.L05.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.H21.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.H26.name())
						|| StringUtils.equals(main.getRtfState(), RtfState.H18.name())) {
					throw new ProcessException("当前申请件已审批通过，请勿重复推送!");
				} else {
					throw new ProcessException("任务流程已不存在，请确认当前申请件[" + appNo + "]状态，请勿重复推送!");
				}
			}
			//默认外部决策成功,外部决策结果提交--确认通过：1
			RtfState nextRtfState = RtfState.H20;
			//如果外部决策建议额度为空，且系统内部客户授信额度为空，则去行内决策建议额度
			if (t1008Req.getFinalCreditLmt() == null && main.getAccLmt() == null) {
				main.setAccLmt(main.getSugLmt());
			} else if (t1008Req.getFinalCreditLmt() != null && t1008Req.getFinalCreditLmt().compareTo(new BigDecimal(0)) > 0) {
				main.setAccLmt(t1008Req.getFinalCreditLmt());
			}
			//外部决策结果提交--通过但是额度为零或额度超过产品最高额度-转至人工
			//1:通过 ，2：拒绝
			if ((StringUtils.equals(t1008Req.getFinalResult(), "1"))
					&& t1008Req.getFinalCreditLmt() != null && product.getApprovalMaximum() != null
					&& (t1008Req.getFinalCreditLmt().compareTo(product.getApprovalMaximum()) == 1)) {
				main.setAccLmt(product.getApprovalMaximum());
				main.setRemark("系统备注[外部决策通过,终审额度大于产品最大授信额度，设置客户当前授信额度为产品可授信最大额度峰值]");
				nextRtfState = RtfState.H21;
			} else if (StringUtils.equals(t1008Req.getFinalResult(), "1")) {
				if (product.getApprovalMaximum() == null) {
					main.setAccLmt(t1008Req.getFinalCreditLmt());
				}
				nextRtfState = RtfState.H21;
			}
			//决策结果提交--但是拒绝：2
			if (StringUtils.equals(t1008Req.getFinalResult(), "2")) {
				//决策结果拒绝
				nextRtfState = RtfState.H22;
				if (!StringUtils.equals(main.getRefuseCode(), t1008Req.getFinalReason())) {
					if (StringUtils.isNotEmpty(main.getRefuseCode())) {
						main.setRefuseCode(main.getRefuseCode() + "," + t1008Req.getFinalReason());
					} else {
						main.setRefuseCode(t1008Req.getFinalReason());
					}
				}
			}
			//决策结果提交--但是转至人工：3
			if (StringUtils.equals(t1008Req.getFinalResult(), "3")) {
				//决策结果转至人工
				nextRtfState = RtfState.H20;
				main.setRemark("系统备注[外部决策结果直接转人工]");
			}
			ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
			ApplyNodeExtDecisionData applyNodeExtDecision = new ApplyNodeExtDecisionData();
			applyNodeExtDecision.setFinalConfirmTime(t1008Req.getFinalConfirmTime());
			applyNodeExtDecision.setFinalCreditLmt(t1008Req.getFinalCreditLmt());
			applyNodeExtDecision.setFinalReason(t1008Req.getFinalReason());
			applyNodeExtDecision.setFinalResult(t1008Req.getFinalResult());
			Map<String, Serializable> resultMap = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A070.name());
			if (resultMap != null && resultMap.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)) {
				applyNodeCommonData = (ApplyNodeCommonData) resultMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
			}
			main.setRtfState(nextRtfState.name());
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, main);
			// 插入节点信息
			Map<String, Serializable> nodeData = new HashMap<String, Serializable>();
			nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			nodeData.put(AppConstant.APPLY_NODE_EXT_RISK, applyNodeExtDecision);
			nodeObjectUtil.insertAllNodeRec(nodeData, appNo);
			//更新TmExtRiskInput
			TmExtRiskInput tmExtRiskInput = applyQueryService.getTmExtRiskInputByAppNo(appNo);
			boolean isExitRisk = true;
			if (tmExtRiskInput == null) {
				tmExtRiskInput = new TmExtRiskInput();
				tmExtRiskInput.setOrg(main.getOrg());
				tmExtRiskInput.setCreateTime(new Date());
				isExitRisk = false;
			}
			//TmExtRiskInput tmExtRiskInput = new TmExtRiskInput();
			tmExtRiskInput.setAppNo(t1008Req.getAppNo());
			tmExtRiskInput.setUpdateTime(t1008Req.getFinalConfirmTime());
			tmExtRiskInput.setExtSugLmt(t1008Req.getFinalCreditLmt());
			tmExtRiskInput.setExtSugDecision(t1008Req.getFinalResult());
			if (isExitRisk) {
				tmExtRiskInputDao.updateTmExtRiskInput(tmExtRiskInput);
			} else {
				tmExtRiskInputDao.save(tmExtRiskInput);
			}
			//保存历史记录 TmAppHistory
			TmAppHistory history = AppCommonUtil.insertApplyHist(appNo,
					AppConstant.SYS_AUTO, nextRtfState, main.getRefuseCode(), "外部决策结果");
			history.setName(main.getName());
			history.setIdType(main.getIdType());
			history.setIdNo(main.getIdNo());
			applyInputService.saveTmAppHistory(history);
			//更新TmAppMain表
			applyInputService.updateTmAppMain(main);
			//=======发起后续流程=======//
			Map<String, Serializable> vars = new HashMap<String, Serializable>();
			vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			if (notActProcess) {
				setThreadSize();//设置线程连接数
				final String fAppNo = appNo;
				final String fTaskId = taskId;
				taskExecutor.execute(new Runnable() {
					public void run() {
						long start = System.currentTimeMillis();
						try {
							logger.info("处理外部决策结果后发起流程----" + LogPrintUtils.printAppNoLog(fAppNo, start, T1008Req.servId));
							activitiService.completeTask(fTaskId, vars, fAppNo);
							logger.info("处理外部决策结果后发起流程----" + LogPrintUtils.printAppNoEndLog(fAppNo, start, T1008Req.servId));
						} catch (ProcessException e) {
							logger.error("申请审批流程处理失败-appNo[" + fAppNo + "], tokenId[" + start + "]", e);
						}
					}
				});
			}
		} catch (Exception e) {
			logger.error("T1008处理外部决策结果异常", e);
			throw new ProcessException(e.getMessage());
		} finally {
			logger.info("[T1008ExtRisk]...处理决策结果提交结束..." + LogPrintUtils.printAppNoEndLog(appNo, tokenId, null));
		}
		return resp;
	}

	/**
	 * @Author smh
	 * @Description TODO T1008参数校验
	 * @Date 2018/11/23 17:21
	 */
	private void checkT1008Req(T1008Req t1008Req) {
		if (t1008Req == null) {
			throw new ProcessException("T1008-请求参数不能为空");
		}
		if (StringUtils.isEmpty(t1008Req.getAppNo())) {
			throw new ProcessException("T1008-请求参数-[申请件编号]不能为空");
		}
		if (StringUtils.isEmpty(t1008Req.getFinalResult())) {
			throw new ProcessException("T1008-请求参数-[终审结论]不能为空");
		}
		if (StringUtils.equals(t1008Req.getFinalResult(), "1")) {
			if (t1008Req.getFinalCreditLmt() == null) {
				throw new ProcessException("T1008-请求参数-[授信额度]不能为空");
			}
		}
	}

	/**
	 * 用于支持客户经理自主分配到其指定的其他客户经理名下
	 *
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Override
	public T1010Resp T1010(T1010Req req) throws ProcessException {
		long tokenId = System.currentTimeMillis();
		String appNo = "";
		String oldSpreaderNo = "";
		String newSpreaderNo = "";
		try {
			checkT1010Req(req);
			appNo = req.getAppNo();
			TmAppPrimCardInfo cardInfo = applyQueryService.getTmAppPrimCardInfoByAppNo(req.getAppNo());
			if (cardInfo == null) {
				throw new ProcessException("无效申请件信息,请重试！");
			}
			if (StringUtils.isNotBlank(cardInfo.getSpreaderNo())) {
				oldSpreaderNo = cardInfo.getSpreaderNo();
			}
			if (!StringUtils.equals(req.getCurOpUser(), cardInfo.getSpreaderNo())) {
				throw new ProcessException("当前推广人与系统记录不匹配，该申请件或已不在您名下!");
			}
			TmAppSprePerBank spre = new TmAppSprePerBank();
			if (StringUtils.isNotEmpty(req.getAccepUser()) && StringUtils.equals(req.getOpType(), "1")) {
				spre.setSpreaderNum(req.getAccepUser());
				List<TmAppSprePerBank> list = spreaderInfoService.getSpreaderByParam(spre);
				if (CollectionUtils.sizeGtZero(list)) {
					spre = list.get(0);
				} else {
					throw new ProcessException("未检索到有效推广人或客户经理信息");
				}
			}
			if (spre != null) {
				cardInfo.setSpreaderNo(spre.getSpreaderNum());
				cardInfo.setSpreaderName(spre.getSpreaderName());
				cardInfo.setSpreaderTelephone(spre.getSpreaderPhone());

				cardInfo.setSpreaderBranchThree(spre.getSpreaderBankId());
				cardInfo.setSpreaderBranchTwo(spre.getSpreaderOrg());

				applyInputService.updateTmAppPrimCardInfo(cardInfo);
			}
			if (StringUtils.isNotBlank(spre.getSpreaderNum())) {
				newSpreaderNo = spre.getSpreaderNum();
			}
			String remark = req.getRemark();
			TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
			main.setUpdateDate(new Date());
			main.setUpdateUser(req.getCurOpUser());
			main.setRemark(main.getRemark() + "; " + remark);
			applyInputService.updateTmAppMain(main);
			TmAppMemo tmAppRemark = new TmAppMemo();
			tmAppRemark.setAppNo(appNo);
			tmAppRemark.setMemoType(AppConstant.APP_REMARK);
			tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_PRE_CHECK.name());
			tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_PRE_CHECK.lab);
			tmAppRemark.setRtfState(main.getRtfState());
			tmAppRemark.setMemoInfo(req.getRemark());
			applyInputService.saveTmAppMemo(tmAppRemark);
			//保存历史记录 TmAppHistory
			String user = StringUtils.setValue(req.getCurOpUser(), AppConstant.SYS_AUTO);
			TmAppHistory history = AppCommonUtil.insertApplyHist(appNo,
					user, RtfState.valueOf(main.getRtfState()), main.getRefuseCode(), "推广人" + oldSpreaderNo + "转分配至" + newSpreaderNo + ";" + "操作备注：" + remark);
			history.setName(main.getName());
			history.setIdType(main.getIdType());
			history.setIdNo(main.getIdNo());
			applyInputService.saveTmAppHistory(history);
		} catch (Exception e) {
			logger.error("T1010处理外部决策结果异常", e);
			throw new ProcessException(e.getMessage());
		} finally {
			logger.info("[T1010转分配]...处理决策结果提交结束..." + LogPrintUtils.printAppNoEndLog(appNo, tokenId, null));
		}
		return null;
	}

	private void checkT1010Req(T1010Req t1010Req) {
		if (t1010Req == null) {
			throw new ProcessException("T1010-请求参数不能为空");
		}
		if (StringUtils.isEmpty(t1010Req.getAppNo())) {
			throw new ProcessException("T1010-请求参数-[申请件编号]不能为空");
		}
		if (StringUtils.isEmpty(t1010Req.getCurOpUser())) {
			throw new ProcessException("T1010-请求参数-[当前推广人或客户经理]不能为空");
		}
	}

	/**
	 * 审批系统前置服务提供联机接口给微信公众号上送大额分期准入申请信息
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Override
	public T1011Resp T1011(T1011Req req) throws ProcessException {
		//检查请求参数
		t1011ProcessUtils.checkT1011Req(req);
		String tokenId = StringUtils.setValue(req.getTokenId(), System.currentTimeMillis());
		logger.info(LogPrintUtils.printCommonStartLog(tokenId, T1011Req.servId)+"...开始");
		T1011Resp t1011Resp = null;
		try {
			t1011Resp = t1011ProcessUtils.executeT1011(req);
		} catch (Exception e) {
			logger.error(LogPrintUtils.printCommonStartLog(tokenId, T1011Req.servId)+e.getMessage(), e);
			throw new ProcessException("调用["+T1011Req.servId+"]处理失败,"+e.getMessage());
		} finally {
			logger.info(LogPrintUtils.printCommonEndLog(tokenId, T1011Req.servId)+"...结束");
		}
		return t1011Resp;
	}


	/**
	 * 审批系统前置服务提供联机接口给中联惠捷系统调用查询客户“大额分期准入申请”结果
	 *
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Override
	public T1012Resp T1012(T1012Req req) {

		t1012ProcessUtils.checkT1012Req(req);
		String tokenId = StringUtils.setValue(req.getTokenId(), System.currentTimeMillis());
		logger.info(LogPrintUtils.printCommonStartLog(tokenId, T1012Req.servId) + "...开始");
		T1012Resp t1012Resp = null;
		try {
			t1012Resp = t1012ProcessUtils.executeT1012(req);
		} catch (Exception e) {
			logger.error("调用[ProcessT1012]接口服务 Resp 返回结果失败------" + e.getMessage(), e);
			throw new ProcessException("调用[ProcessT1012]接口服务 返回结果失败 " + e.getMessage());
		} finally {
			logger.info(LogPrintUtils.printCommonEndLog(tokenId, T1012Req.servId) + "...结束");
		}
		return t1012Resp;

	}

	/**
	 * 合伙人准入资格申请
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Override
	public T1013Resp T1013(T1013Req req) throws ProcessException {
		logger.info("[T1013]...处理合伙人准入资格验证开始...");
		Long start = System.currentTimeMillis();
		String tokenId = "";
		T1013Resp resp = new T1013Resp();
		try {
			appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
			tokenId = StringUtils.setValue(req.getTokenId(), start);
			resp = t1013ProcessUtils.executeT1013(req,tokenId);
		} catch (Exception e) {
			logger.error("T1013处理合伙人准入资格验证异常", e);
			resp.setRiskResult("R");//异常就返回拒绝
		} finally {
			logger.info("[T1013]...处理合伙人准入资格验证结束..." + LogPrintUtils.printCommonEndLog(tokenId, null));
		}
		return resp;
	}

}

