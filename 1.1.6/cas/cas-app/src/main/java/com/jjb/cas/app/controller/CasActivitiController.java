package com.jjb.cas.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.ApplyPatchBoltType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.cas.app.controller.utils.CasExceptionPageUtils;
import com.jjb.cas.app.controller.utils.CasPagePathConstant;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmLuckyCardService;
import com.jjb.ecms.biz.service.approve.ApplyPatchBoltService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.commonDialog.AlreadyCardService;
import com.jjb.ecms.biz.service.node.ApplyInfoPreDtoService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.ApplyInfoSupport;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.ecms.facility.dto.ApplyOperateDto;
import com.jjb.ecms.facility.dto.ApplyPatchBoltDto;
import com.jjb.ecms.facility.dto.ApplyRiskInfoDto;
import com.jjb.ecms.facility.dto.ProcessDefinitionDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeFinalAuditData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePatchBoltData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.facility.nodeobject.ApplyTelInquiryRecordItem;
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
import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 用于任务执行时的页面跳转
 * @author hn
 * @date 2016年8月27日12:55:50
 */
@Controller
@RequestMapping("/cas_activiti")
public class CasActivitiController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ApplyPatchBoltService applyPatchBoltService;//补件
	@Autowired
	private AlreadyCardService alreadyCardService;//已有卡
	@Autowired
	private CasAppUtils applyInfoUtils;//申请件处理工具类
	@Autowired
	private TmLuckyCardService tmLuckyCardService;
	@Autowired
	private TmAclDictDao tmAclDictDao;
	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private TmExtRiskInputDao tmExtRiskInputDao;
	@Autowired
	private ApplyInfoSupport applyInfoSupport;
	@Autowired
	private CasExceptionPageUtils exceptionPageUtils;
	@Autowired
	private ApplyInfoPreDtoService applyInfoPreDtoService;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private CasAppUtils casAppUtils;

	/*
	 * 用于跳转到人工页面，并且获取页面所需的值
	 */
	@RequestMapping("/handleTask")
	public String handleTask() throws SecurityException,
			IllegalArgumentException,
			NoSuchFieldException,
			IllegalAccessException {
		Long tokenId = System.currentTimeMillis();
		Boolean showBtnFlag = false;//录入页面(新增按钮、删除按钮)默认设置不显示
		String appNo = getPara("appNo");
		String updateFlag = getPara("updateFlag");//申请件进度查询修改操作标志“Y”
		String detailFlag = getPara("detailFlag");//申请件进度查询查看详情操作标志“Y”
		String detailBtnFlag = getPara("detailBtnFlag");//区分弹框页面和进度查询页面的标志；“Y”：弹框页面
//		String fileFlag = getPara("fileFlag");//区分归档操作标志
		if(StringUtils.isNotBlank(detailBtnFlag) && detailBtnFlag.equals(Indicator.Y.name())){
			setAttr("detailBtnFlag", detailBtnFlag);//用来选择显示关闭或返回按钮
		}
		String isEditMemo = getPara("isEditMemo");//录入页面标志，用于推广注记是否可编辑（预录入和历史导入可编辑）
		if(StringUtils.isNotEmpty(isEditMemo) && isEditMemo.equals("true")){
			setAttr("isEditMemo", true);
		}else {
			setAttr("isEditMemo", false);
		}
		Task task = null;
		String taskId = "";
		if(StringUtils.isEmpty(updateFlag) && StringUtils.isEmpty(detailFlag)){
			task = activitiService.getTaskId(appNo);
		}
		if(task!=null){
			taskId = task.getId();
		}
		String pagePath = null;// 定义页面路径
		String formKey = "";

		if(StringUtils.isNotBlank(taskId)){
			formKey = activitiService.handleTask(taskId,appNo);
		}

		logger.info("开始设置节点页面信息"+LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"taskId["+taskId+"]");
		try {
			// 获取节点信息
			ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
			if(applyInfoDto==null){
				logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",ApplyInfoDto 表信息为空");
				throw new ProcessException("未查询到相关申请件信息,请刷新后重试!");
			}

			//如果是独立附卡申请则根据主卡卡号拷贝主卡信息，目的是更新卡产品、主卡申请人信息、卡面信息
			applyInfoDto = applyInfoSupport.setPrimApplicantInfo(applyInfoDto, tokenId);
			// 客户申请信息
			TmAppMain tmAppMain = applyInfoDto.getTmAppMain();//申请业务主表
			if(tmAppMain == null){
				logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",TmAppMain 表信息为空");
				throw new ProcessException("未查询到相关申请件信息,请刷新后重试!");
			}
			String productCd = tmAppMain.getProductCd(); //根据产品代码获取卡产品信息
			String appType = tmAppMain.getAppType(); //获取申请类型
			// 获取卡产品信息
			TmProduct tmProduct = cacheContext.getProduct(productCd);
			if(tmProduct==null && StringUtils.isNotEmpty(productCd)){
				throw new ProcessException("系统未获取到产品["+productCd+"]信息，您的登录或产品配置已失效，请重新登录系统后再试!");
			}else if(StringUtils.isEmpty(productCd)) {
				throw new ProcessException("产品编号为空，请联系管理员!");
			}
			setAttr("tmProductApprovalMaximum",tmProduct.getApprovalMaximum());
			String cardBin = tmProduct.getBin();
			int cardBinLen = 6;//卡bin长度,默认6位长度
			if(StringUtils.isNotEmpty(cardBin)){
				cardBinLen=cardBin.trim().length();
			}
			int cardNoLen = cardBinLen+tmProduct.getCardNoRangeCeil().length()+1;//卡号长度默认16位
			setAttr("editCardNoLen", cardNoLen-cardBinLen-1);//可填卡号长度
			//根据申请人身份证号提取性别、出生日期、年龄信息,前提条件是数据库中这些值为空
			applyInfoDto = applyInfoSupport.setAppAttributeValue(applyInfoDto,tmProduct,cardNoLen);
			setValidBitCard(applyInfoDto, cardBin);
			TmAppPrimCardInfo tmAppPrimCardInfo=applyInfoDto.getTmAppPrimCardInfo();//申请其他信息表
			TmAppPrimAnnexEvi tmAppPrimAnnexEvi=applyInfoDto.getTmAppPrimAnnexEvi();//申请附件表
			Map<String, TmAppContact> tmAppContactInfoMap=applyInfoDto.getTmAppContactMap();//联系人信息表
			Map<String,List< TmAppMemo>> tmAppMemoMapAll=applyInfoDto.getTmAppMemoMapAll();//全部的备注备忘
			Map<String, TmAppMemo> tmAppMemoMapLast=applyInfoDto.getTmAppMemoMapLast();//各节点最新的备注备忘
			List< TmAppHistory> tmAppHistoryList=applyInfoDto.getTmAppHistoryList();//历史申请审批记录
			Map<String,TmAppCustInfo> allCustMap = applyInfoDto.getTmAppCustInfoMap();//附卡
			TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();   //
			if(tmAppAudit==null) {
				tmAppAudit = new TmAppAudit();
				tmAppAudit.setAppNo(appNo);
			}
			if(allCustMap==null){
				allCustMap = new HashMap<String, TmAppCustInfo>();
			}
//   List<TmAppCustInfo> allCustList= applyInfoDto.getTmAppCustInfoList();//申请客户信息
//   if(CollectionUtils.isEmpty(allCustList)){
//    allCustList = new ArrayList<TmAppCustInfo>();
//   }
//   TmAppCustInfo primCust;//主卡
//   Map<String,TmAppCustInfo> attachMap;//附卡
//   if (applyInfoDto.getTmAppCustInfoMap() != null) {
//    primCust = allCustMap.get(AppConstant.bscSuppInd_B);
//    attachMap = applyInfoDto.getTmAppCustInfoMap();
//   }

			TmExtRiskInput tmExtRiskInput = tmExtRiskInputDao.getTmExtRiskInputByAppNo(appNo);//第三方或行内风控
			if(tmExtRiskInput != null){
				setAttr("tmExtRiskInput",tmExtRiskInput);
			}
			//如果是重审，将新的appNo赋值到各个子表
			String retrialFlag = getPara("retrialFlag");//重审件标志“Y”
			if(StringUtils.isNotEmpty(retrialFlag) && retrialFlag.equals(Indicator.Y.name())){
				applyInfoDto.setRetrialFlag(retrialFlag);
				applyInfoDto.setOldAppNo(appNo);
				tmAppAudit.setAppNoHis(appNo);
				tmAppAudit.setIsRetrialApp("Y");//是否属于重审(新)件
				tmAppMain.setRtfState(RtfState.A05.name());//重审到录入修改
				String retrialAppNo=applyInputService.getAppNo();//获取新申请件编号
				tmAppAudit.setAppNo(retrialAppNo);
				if(StringUtils.isNotEmpty(retrialAppNo)){
					applyInfoDto.setAppNo(retrialAppNo);
					tmAppMain.setAppNo(retrialAppNo);
					if(tmAppPrimAnnexEvi == null){
						tmAppPrimAnnexEvi = new TmAppPrimAnnexEvi();
					}
					tmAppPrimAnnexEvi.setAppNo(retrialAppNo);
					// 老件自选卡号解锁，新件自选卡号上锁
					List<String> cardNoList = new ArrayList<String>();// 待处理卡号列表
					for (TmAppCustInfo cust : allCustMap.values()) {
						if(cust==null){
							continue;
						}
						cust.setAppNo(retrialAppNo);
						if(cust.getCardNo()!=null){
							cardNoList.add(cust.getCardNo());// 老件解锁
						}
						allCustMap.put(cust.getBscSuppInd()+cust.getAttachNo(), cust);
					}
					//先解锁后上锁
					for (String cardNo : cardNoList) {
						List<TmLuckyCard> oldLockCards = tmLuckyCardService.getByCardNo(cardNo,appNo);//查出所有的已锁住卡cardNo信息
						if(CollectionUtils.sizeGtZero(oldLockCards)){
							for (TmLuckyCard oldLockCard : oldLockCards) {
								// 老件自选卡号本地解锁
								oldLockCard.setStatus(Indicator.N.name());
								oldLockCard.setOperId(OrganizationContextHolder.getUserNo());
								oldLockCard.setUpdateTime(new Date());
								tmLuckyCardService.updateTmLuckyCard(oldLockCard);//更新

								// 新件本地上锁
								TmLuckyCard newLockCard = new TmLuckyCard();
								newLockCard.setAppNo(retrialAppNo);
								newLockCard.setOrg(oldLockCard.getOrg());
								newLockCard.setCardNo(oldLockCard.getCardNo());
								newLockCard.setLockReason(oldLockCard.getLockReason());
								newLockCard.setName(oldLockCard.getName());
								newLockCard.setIdType(oldLockCard.getIdType());
								newLockCard.setIdNo(oldLockCard.getIdNo());
								newLockCard.setCellphone(oldLockCard.getCellphone());
								newLockCard.setStatus(Indicator.Y.name());
								tmLuckyCardService.saveTmLuckyCard(newLockCard);
								logger.info("老件[{}]自选卡号解锁成功，新件[{}]卡号上锁成功，卡号[{}]", appNo, retrialAppNo, cardNo);
							}
						}
					}

					tmAppPrimCardInfo.setAppNo(retrialAppNo);
					for (TmAppContact contactInfo : tmAppContactInfoMap.values()) {
						contactInfo.setAppNo(retrialAppNo);
					}
					setAttr("retrialFlag", retrialFlag);
					bizAuditHistoryUtils.saveAuditHistory(appNo, "重审操作-原AppNo");
					bizAuditHistoryUtils.saveAuditHistory(retrialAppNo, "重审操作-新AppNo");
				}else {
					logger.error("重申件的新申请件编号为空，原appNo="+appNo);
					throw new ProcessException("重申件获取新申请件编号失败,原appNo="+appNo);
				}

				logger.info("开始进入重申件录入修改页面，appNo="+retrialAppNo);
			}

			if (StringUtils.isNotBlank(taskId)) {
				formKey = activitiService.handleTask(taskId,appNo);
			} else {
				if(StringUtils.isNotBlank(updateFlag) && updateFlag.equals(Indicator.Y.name())){//申请件修改
					formKey = CasPagePathConstant.casApplyUpdatePage;
					setAttr("updateFlag", updateFlag);
				}else if(StringUtils.isNotBlank(detailFlag) && detailFlag.equals(Indicator.Y.name())) {//申请件详情
					formKey = CasPagePathConstant.casApplyProcessDetailPage;
				}else {
					//非重审 或者 非A05-申请录入, 则标注为异常.
					if(tmAppMain.getRtfState().equals(RtfState.A05.toString())
							|| (StringUtils.isNotEmpty(retrialFlag) && retrialFlag.equals(Indicator.Y.name()))){
						formKey = CasPagePathConstant.APPLICATION_INPUT_MODIFY;
					}else{
						logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",taskId 信息为空");
						throw new ProcessException("执行申请件["+appNo+"]该任务无效，请重新选择！");
					}
				}
			}
			if (StringUtils.isEmpty(formKey)) {
				logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",formKey 信息为空");
				throw new ProcessException("未找到申请件["+appNo+"]相应任务节点,请刷新后重试!");
			}
			logger.info("开始设置["+formKey+"]页面"+LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"taskId["+taskId+"]");

			// 初审结果信息
			ApplyNodeInquiryData applyNodeInquiryData = new ApplyNodeInquiryData();
			//风险信息
			ApplyRiskInfoDto applyRiskInfoDto = new ApplyRiskInfoDto();
			// 欺诈与人工查证信息
//   ApplyNodeCheatCheckData applyNodeCheatCheckData = new ApplyNodeCheatCheckData();
			// 电话调查信息
			ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = new ApplyNodeTelCheckBisicData();
			// 获取补件信息
			ApplyNodePatchBoltData applyNodePatchBoltData = new ApplyNodePatchBoltData();
			//申请电话调查记录
//			List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = null;
			//申请电话调查记录
//			Map<String,ApplyTelInquiryRecordItem> applyTelInquiryRecordMap = new HashMap<String, ApplyTelInquiryRecordItem>();
//			//申请电话调查记录列表
//			LinkedHashMap<String,ApplyTelInquiryRecordItem> applyTelInquiryMap = new LinkedHashMap<String,ApplyTelInquiryRecordItem>();
//			//核身问题调查记录
//			List<AllpyTelCheckRecordItem> idCheckList = null;
//			//必问问题调查记录
//			List<AllpyTelCheckRecordItem> mustCheckList = null;
//			//选核问题调查记录
//			List<AllpyTelCheckRecordItem> choiceCheckList = null;
			// 待补件列表
			List<ApplyPatchBoltDto> applyPatchBoltDtos = new ArrayList<ApplyPatchBoltDto>();
			//终审信息
			ApplyNodeFinalAuditData applyNodeFinalAuditData = new ApplyNodeFinalAuditData();
			String patchBoltString = "";//补件信息
			//拒绝原因（初审）
			List<String> checkRefuseCodeList = new ArrayList<String>();
			//拒绝原因（终审）
			List<String> finalRefuseCodeList = new ArrayList<String>();
//			//致电类型信息
//			List<TmAclDict> callTypeList = new ArrayList<TmAclDict>();
//			TmAclDict callType = new TmAclDict();
			TmAppCustInfo tmAppCustInfo = new TmAppCustInfo();
			List<TmAppCustInfo> tmAppCustList = applyInfoDto.getTmAppCustInfoList();
			if(tmAppCustList !=null && tmAppCustList.size()>0){
				tmAppCustInfo = tmAppCustList.get(0);
			}
			//申请电话调查记录列表
			LinkedHashMap<String,ApplyTelInquiryRecordItem> applyTelInquiryMap = 
					casAppUtils.setCallTelphoneRecordTypeListData(tmAppMain, tmAppPrimCardInfo, tmAppContactInfoMap, tmAppCustInfo);
			//节点信息处理
			Map<String,Serializable> tmAppNodeInfoRecordMap = applyInfoDto.getTmAppNodeInfoRecordMap();//节点信息记录表
			if (tmAppNodeInfoRecordMap != null) {
				//初审调查节点信息
				if(tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_INQUIRY_DATA)){
					applyNodeInquiryData = (ApplyNodeInquiryData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_INQUIRY_DATA);
					if(applyNodeInquiryData != null && CollectionUtils.isNotEmpty(applyNodeInquiryData.getRejectReasonList())){
						checkRefuseCodeList = applyNodeInquiryData.getRejectReasonList();
					}

					ApplyPatchBoltDto applyPatchBoltDto = new ApplyPatchBoltDto();
					if(applyNodeInquiryData != null && CollectionUtils.isNotEmpty(applyNodeInquiryData.getPatchBoltList())){
						for (int i1 = 0; i1 < applyNodeInquiryData.getPatchBoltList().size(); i1++) {
							String patchBolt = applyNodeInquiryData.getPatchBoltList().get(i1);
							patchBoltString = patchBoltString + "," +patchBolt;

							// 有补件项
							applyPatchBoltDto = new ApplyPatchBoltDto();
							String picKey = appCommonUtil.getPatchBoltTypeEnumValueByCH(patchBolt);
							applyPatchBoltDto.setApplyPatchBoltType(patchBolt);// 设置补件类型的英文名
							applyPatchBoltDto.setApplyPatchBoltTitle(picKey);// 设置补件类型的中文名

							//补件结果节点信息
							if(tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_PATCHBOLT_DATA)){
								applyNodePatchBoltData = (ApplyNodePatchBoltData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_PATCHBOLT_DATA);
							}
							// 有做过补件
							if (applyNodePatchBoltData != null) {
								if (CollectionUtils.isNotEmpty(applyNodePatchBoltData.getPatchBoltList())) {// 有主卡补件信息
									if (applyNodePatchBoltData.getPatchBoltList().contains(patchBolt)) {// 说明这个已经补过件
										applyPatchBoltDto.setIfPathBolt(true);
									}
								}
								if (applyNodePatchBoltData.getAttachPatchBoltList() != null
										&& CollectionUtils.isNotEmpty(applyNodePatchBoltData.getAttachPatchBoltList().get(AppConstant.bscSuppInd_S))) {// 有附卡卡补件信息
									if (applyNodePatchBoltData.getAttachPatchBoltList().get(AppConstant.bscSuppInd_S).contains(patchBolt)) {// 说明这个已经补过件(取值的方法对应ApplyNodePatchBoltData存放方式)
										applyPatchBoltDto.setIfPathBolt(true);
									}
								}
							}
							applyPatchBoltDtos.add(applyPatchBoltDto);
						}
					}else if(applyNodeInquiryData != null && applyNodeInquiryData.getAttachPatchBoltList() != null && applyNodeInquiryData.getAttachPatchBoltList().size() > 0){
						List<String> attachPatchBoltMap = applyNodeInquiryData.getAttachPatchBoltList().get(AppConstant.bscSuppInd_S);
						for (String patchBolt : attachPatchBoltMap) {
							patchBoltString = patchBoltString + "," +patchBolt;

							// 有补件项
							applyPatchBoltDto = new ApplyPatchBoltDto();
							applyPatchBoltDto.setApplyPatchBoltType(ApplyPatchBoltType.valueOf(patchBolt).name());// 设置补件类型的英文名
							applyPatchBoltDto.setApplyPatchBoltTitle(ApplyPatchBoltType.valueOf(patchBolt).getApplyPatchBoltType());// 设置补件类型的中文名

							//补件结果节点信息
							if(tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_PATCHBOLT_DATA)){
								applyNodePatchBoltData = (ApplyNodePatchBoltData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_PATCHBOLT_DATA);
							}
							// 有做过补件
							if (applyNodePatchBoltData != null) {
								if (CollectionUtils.isNotEmpty(applyNodePatchBoltData.getPatchBoltList())) {// 有主卡补件信息
									if (applyNodePatchBoltData.getPatchBoltList().contains(patchBolt)) {// 说明这个已经补过件
										applyPatchBoltDto.setIfPathBolt(true);
									}
								}
								if (applyNodePatchBoltData.getAttachPatchBoltList() != null
										&& CollectionUtils.isNotEmpty(applyNodePatchBoltData.getAttachPatchBoltList().get(AppConstant.bscSuppInd_S))) {// 有附卡卡补件信息
									if (applyNodePatchBoltData.getAttachPatchBoltList().get(AppConstant.bscSuppInd_S).contains(patchBolt)) {// 说明这个已经补过件(取值的方法对应ApplyNodePatchBoltData存放方式)
										applyPatchBoltDto.setIfPathBolt(true);
									}
								}
							}
							applyPatchBoltDtos.add(applyPatchBoltDto);
						}
					}

					if(StringUtils.isNotEmpty(patchBoltString) && patchBoltString.length()>0){
						patchBoltString = patchBoltString.substring(1, patchBoltString.length());
					}
				}
				// 欺诈与人工查证信息
				commonService.setApplyRiskInfoDto(applyRiskInfoDto, tmAppNodeInfoRecordMap);
				//查询申请件标签
				List<TmAppFlag> tmAppFlagList = applyQueryService.getTmAppFlagListByAppNo(appNo);
				List<String> appFlagList = new ArrayList<String>();
				if (tmAppFlagList.size() != 0) {
					setAttr("tmAppFlagList", tmAppFlagList);
					for (TmAppFlag tmAppFlag : tmAppFlagList) {
						if (tmAppFlag != null) {
							appFlagList.add(tmAppFlag.getFlagCode());
						}
					}
					setAttr("appFlagList", commonService.listToString(appFlagList, ","));
				}
				// 电话调查信息getApplyInfoByAppNo
//				if(tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA)){
//					applyNodeTelCheckBisicData = (ApplyNodeTelCheckBisicData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA);
//					if (applyNodeTelCheckBisicData != null) {
//						applyTelInquiryRecordDtoList = applyNodeTelCheckBisicData.getTelInquiryRecordItemList();//拨打电话信息
//						if(StringUtils.isNotEmpty(applyTelInquiryRecordDtoList) && applyTelInquiryRecordDtoList.get(0).getTelType()!=null) {
//							for (int i=0;i<applyTelInquiryRecordDtoList.size();i++) {
//								ApplyTelInquiryRecordItem applyTelInquiryRecordItem = applyTelInquiryRecordDtoList.get(i);
//								applyTelInquiryRecordMap.put(applyTelInquiryRecordItem.getTelType(),applyTelInquiryRecordItem);
//							}
//						}
//						idCheckList = applyNodeTelCheckBisicData.getIdCheckRecordItem();//核身问题
//						mustCheckList = applyNodeTelCheckBisicData.getMustCheckRecordItem();//必问问题
//						choiceCheckList = applyNodeTelCheckBisicData.getChoiceCheckRecordItem();//选核问题
//					}
//				}

				//终审信息
				if(tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_FINAL_AUDIT_DATA)){
					applyNodeFinalAuditData = (ApplyNodeFinalAuditData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_FINAL_AUDIT_DATA);
					if (applyNodeFinalAuditData != null) {
						finalRefuseCodeList = applyNodeFinalAuditData.getResultReasonList();
					}
				}
			}

			// 人工查证结果
//   List<PersonCheckResultDto> personCheckResultDtos = personCheckService.getPersonCheckResultDtos(appNo);
			List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = alreadyCardService.getAlreadyCardsCardInfoDtos(appNo);
			if(CollectionUtils.sizeGtZero(alreadyCardsCardInfoDtos)){
				setAttr("alreadyCards","true");
				//显示已有卡信息,在sql查询的时候，是按照时间的降序来的，所以第一条为最新的卡片信息
				String alreadyCardAppNo = alreadyCardsCardInfoDtos.get(0).getAppNo();//已有卡卡号
				TmAppCustInfo alreadyPrimCust = applyQueryService.getTmAppPrimCustByAppNo(alreadyCardAppNo);
				if (alreadyPrimCust != null && StringUtils.isNotBlank(alreadyPrimCust.getCellphone())) {
					setAttr("existingPhone", alreadyPrimCust.getCellphone());
				}
			}else{
				setAttr("alreadyCards","false");
			}

			//计算申请附卡的数量
			Integer attachNum = null;
			//设置附属卡返回到页面的值
			if(appType.equals(AppType.A.name())){//如果是主附同申或者独立附卡
				Map<String, String> attachNoMap = new HashMap<String, String>();
				String ifAttachRefuse = null; //拒绝附卡
				for (TmAppCustInfo cust : allCustMap.values()) {
					if(cust==null){
						continue;
					}
					if(StringUtils.equals(cust.getBscSuppInd(), AppConstant.bscSuppInd_S)){

						Integer ifAttachRefuseNum = cust.getAttachNo()-4;
						attachNoMap.put(ifAttachRefuseNum.toString(), cust.getName());//从0开始,N表示拒绝
						if(StringUtils.equals(cust.getRecordStatus(),Indicator.N.name())){
							if(StringUtils.isBlank(ifAttachRefuse)){
								ifAttachRefuse = ifAttachRefuseNum.toString();
							}else {
								ifAttachRefuse = ifAttachRefuse + "," + ifAttachRefuseNum.toString();
							}
						}
					}
//     allCustMap.put(cust.getBscSuppInd()+cust.getAttachNo(), cust);
				}
				attachNum = attachNoMap.size();
				setAttr("attachNoMap", attachNoMap);
				setAttr("ifAttachRefuse", ifAttachRefuse);
			}
			if(appType.equals(AppType.S.name())){//如果是独立附卡
				attachNum = 0;
			}
			//备注、备忘
			if(tmAppMemoMapLast != null){
				for (String key : tmAppMemoMapLast.keySet()) {
					TmAppMemo tam = applyInfoDto.getTmAppMemoMapLast().get(key);
					if(tam!=null){
						System.out.println(tam.getMemoType()+tam.getTaskKey());
						setAttr(tam.getMemoType()+tam.getTaskKey(), tam);
					}
				}
			}
			//如果在电话调查节点，得到需要核身及必问问题(初始化)
//			boolean ifIdCheckListNull = CollectionUtils.isEmpty(idCheckList);
//			boolean ifMustCheckListNull = CollectionUtils.isEmpty(mustCheckList);
//			if(formKey.equals("application-telephonesurvey") && (ifIdCheckListNull|| ifMustCheckListNull)
//					&& allCustMap.get(AppConstant.bscSuppInd_B_1)!=null){
//				idCheckList = new ArrayList<AllpyTelCheckRecordItem>();
//				mustCheckList = new ArrayList<AllpyTelCheckRecordItem>();
//				Map<String, Serializable> mainMap = allCustMap.get(AppConstant.bscSuppInd_B_1).convertToMap();
//				List<TmDitDic> tmDitDics = cacheContext.getTelCheckParam(AppConstant.TEL_CHECK_INFO);
//				if(CollectionUtils.sizeGtZero(tmDitDics)){
//					for(TmDitDic tmDitDic :tmDitDics){
//						AllpyTelCheckRecordItem telCheckRecordItem = new AllpyTelCheckRecordItem();
//						telCheckRecordItem.setAskContent(tmDitDic.getRemark());
//						telCheckRecordItem.setAnswer(mainMap.get(tmDitDic.getItemName())==null?null:mainMap.get(tmDitDic.getItemName()).toString());
//						String fromName = tmDitDic.getFormName();
//						if(StringUtils.isNotBlank(fromName)){
//							if(fromName.equals(AppConstant.ID_CHECK_ITEM)){
//								idCheckList.add(telCheckRecordItem);
//							}
//							if(fromName.equals(AppConstant.MUST_CHECK_ITEM)){
//								mustCheckList.add(telCheckRecordItem);
//							}
//						}
//					}
//				}
//			}
			String pbstartTime = null;// 定义补件开始时间
			String pbtimeWait = null;// 定义补件等待时间
			String pbendtime = null;// 定义补件结束时间
			//如果是补件，从TM_APP_RFE中获取 （补件时间）\ 以前是在工作流变量中获取
			if(formKey.equals("application-patchblot")){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<String> filedNames = new ArrayList<String>();
				filedNames.add(AppConstant.APPLY_PB_STTIME);
				filedNames.add(AppConstant.APPLY_PB_TIMEWAIT);
				//取出补件时间
				Map<String, String> patchTimeMap = applyPatchBoltService.getPatchTimeMap(tmAppMain == null ? null : tmAppMain.getAppNo());
				if(patchTimeMap != null){
					pbstartTime = patchTimeMap.get(AppConstant.APPLY_PB_STTIME);
					pbtimeWait = patchTimeMap.get(AppConstant.APPLY_PB_TIMEWAIT);
				}

				if (StringUtils.isNotBlank(pbstartTime) && StringUtils.isNotBlank(pbtimeWait)) {
					pbstartTime = pbstartTime.replaceAll("T", " ");// 去掉日期中的“T”
//      pbtimeWait = pbtimeWait.substring(1, 2);// 去除中间的日期（P1D）
					pbtimeWait=pbtimeWait.substring(pbtimeWait.indexOf("P")+1,pbtimeWait.indexOf("D"));
					try {
						pbendtime = df.format(new Date(df.parse(pbstartTime).getTime() + Integer.valueOf(pbtimeWait) * 24 * 60 * 60 * 1000L));
					} catch (ParseException e) {
						logger.error("时间转换异常", e);
					}
				}
			}
			//录入、录入修改、申请件修改页面必输项设置
			if(StringUtils.isBlank(detailFlag)){
				HashMap<String,List<ValidFieldInfoDto>> validFieldMap = commonService.validateField(appType, tmProduct, attachNum);
				setAttr("validFieldMap", validFieldMap);
			}

//   setAttr("pbocEdit", pbocEdit);//是否可以修改人行报告信息（目前初审可以修改）
			//回显征信查询结果
//   Map<String, Object> queryMap = new HashMap<String, Object>();
//   queryMap.put("_SORT_NAME", "queryTime");
//   queryMap.put("_SORT_ORDER", "desc");
//   TmCreditHistory tmCreditHistory = new TmCreditHistory();
//   tmCreditHistory.setAppNo(appNo);
//   tmCreditHistory.setOpType(AppConstant.ICSRO1);
//   String retMsg = null;
//   List<TmCreditHistory> tmCreditHistoryList = tmCreditHistoryDao.queryForList(tmCreditHistory, queryMap);
//   if(CollectionUtils.sizeGtZero(tmCreditHistoryList)){
//    retMsg = tmCreditHistoryList.get(0).getRetMsg();
//   }else {
//    retMsg = "系统自动查询人行征信发生异常，请手动查询";
//   }
//   setAttr("retMsg", retMsg);
			//是否readonly公务卡公司信息相关栏位
			if(tmProduct!=null && StringUtils.equals(tmProduct.getSubCardType(), "O")){
				setAttr("ifCorpReadonly", "true");
			}else {
				setAttr("ifCorpReadonly", "false");
			}

			setAttr("pageOnOffParamDto", commonService.getApplyPageOnOffParamDto());//页面开关参数
			setAttr("taskId", taskId);//工作流任务id
			//重审时需要取新获取的appNo
			setAttr("appNo", Indicator.Y.name().equals(retrialFlag) ? tmAppMain.getAppNo() : appNo);//申请件编号
			setAttr("appType", appType);//申请类型
			setAttr("appSource", tmAppMain.getAppSource());//申请类型
			setAttr("productCd", productCd);//卡产品
			setAttr("oldAppNo",applyInfoDto.getOldAppNo());//原申请件appNo(重审前)
			setAttr("pbstartTime", pbstartTime);// 定义补件开始时间
			setAttr("pbtimeWait", pbtimeWait);// 定义补件等待时间
			setAttr("pbendtime", pbendtime);// 定义补件结束时间
			if(tmAppMain.getAccLmt() != null){//金额大写处理
				setAttr("bigLimit", tmAppMain.getAccLmt().toString() );
			}
			String isReturned = tmAppAudit.getIsReturned();//可能值为A|B|C|D中的一种或多种
			if(StringUtils.isNotBlank(isReturned)){//退回件的处理
				String[] isReturneds = isReturned.split("\\|");
				String returnFlag = "退回件：";
				for (int i=0; i<isReturneds.length; i++) {
					if(StringUtils.isNotBlank(isReturneds[i])){
						returnFlag = returnFlag + AppCommonUtil.getReturnCnStateByCode(isReturneds[i]);
					}
					if(i<isReturneds.length-1){
						returnFlag = returnFlag + "-";
					}
				}
				setAttr("returnFlag", returnFlag);
			}

			//根据appNo获取历史申请信息
			String idType = tmAppMain.getIdType();
			String idNo = tmAppMain.getIdNo();
			if (StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)) {
				throw new ProcessException("历史申请查看出现异常！appNo=[" + appNo + "],idType=" + idType + ",idNo=" + idNo);
			}
			ApplyInfoPreDto applyInfoPreDto = new ApplyInfoPreDto();
			applyInfoPreDto.setAppNo(appNo);
			applyInfoPreDto.setIdType(idType);
			applyInfoPreDto.setIdNo(idNo);
			List<ApplyInfoPreDto> hisApplyInfoList = applyInfoPreDtoService.getApplyInfoPreDtoList(applyInfoPreDto);
			setAttr("hisApplyInfoList",hisApplyInfoList);
			//设置默认本行还款账户银行名称和开户行号
			if(tmAppPrimCardInfo != null && StringUtils.isBlank(tmAppPrimCardInfo.getDdBankBranch())){
				TmAclDict tmAclDict = new TmAclDict();
				tmAclDict.setOrg(OrganizationContextHolder.getOrg());
				tmAclDict.setType("DdBankBranch");
				tmAclDict.setValue2(Indicator.Y.name());
				List<TmAclDict> tmAclDicts = tmAclDictDao.queryForList(tmAclDict);
				if(CollectionUtils.sizeGtZero(tmAclDicts)){
					tmAppPrimCardInfo.setDdBankBranch(tmAclDicts.get(0).getCode());
					tmAppPrimCardInfo.setDdBankName(tmAclDicts.get(0).getCodeName());
				}
			}
			applyInfoDto.setTmAppMain(tmAppMain);
			applyInfoDto.setTmAppCustInfoMap(allCustMap);
			applyInfoDto.setTmAppPrimAnnexEvi(tmAppPrimAnnexEvi==null?new TmAppPrimAnnexEvi():tmAppPrimAnnexEvi);
			applyInfoDto.setTmAppContactMap(tmAppContactInfoMap);
			applyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);
			setAttr("tmAppPrimAnnexEvi",tmAppPrimAnnexEvi);
			setAttr("tmAppPrimCardInfo",tmAppPrimCardInfo);
			setAttr("tmAppAudit",tmAppAudit);
			setAttr("tmAppMain",tmAppMain);
			//设置自选卡号格式匹配
			Integer len = cardNoLen - cardBinLen - 1;
			setAttr("selectCardNoRegexp", "^(\\d{"+len+"}$)");
			setAttr("tmProduct",tmProduct);
			setAttr("pageFieldMap", applyInfoUtils.getPageFieldMap(tmProduct, appType, formKey, 0, attachNum, applyInfoDto));
			setAttr("applyOperateDto", setApplyOperateDto(applyInfoDto,appType));
			setAttr("attachNum", attachNum);
			setAttr("showBtnFlag", showBtnFlag);//设置录入页面新增按钮、删除按钮是否显示(true：显示；false：不显示)
			if(tmAppContactInfoMap!=null){
				for (String key : tmAppContactInfoMap.keySet()){
					setAttr(key,tmAppContactInfoMap.get(key));
				}
			}
//   setAttr("tmAppPrimCreditResult", tmAppPrimCreditResult);// 人工征信结果
			setAttr("tmAppMemoMapAll", tmAppMemoMapAll);// 全部滴备注备忘
			setAttr("tmAppMemoMapLast", tmAppMemoMapLast);// 各节点最新滴备注备忘
			setAttr("tmAppHistoryList", tmAppHistoryList);// 历史申请审批记录
			setAttr("applyNodeInquiryData", applyNodeInquiryData);// 初审结果
			setAttr("checkRefuseCodes", commonService.listToString(checkRefuseCodeList, ","));// 初审拒绝原因
			setAttr("patchBoltString",patchBoltString);//补件信息
			setAttr("applyRiskInfoDto", applyRiskInfoDto);//风险信息

//   setAttr("personCheckResultDtos", personCheckResultDtos);// 人工查证结果
			setAttr("applyPatchBoltDtos", applyPatchBoltDtos);// 待补件列表
//   setAttr("applyTelInquiryRecordDtoList", applyTelInquiryRecordDtoList);//申请电话调查记录
//   setAttr("applyTelInquiryRecordMap", applyTelInquiryRecordMap);//拨打电话信息
			setAttr("applyTelInquiryMap", applyTelInquiryMap);//致电信息
//			setAttr("idCheckList", idCheckList);//核身问题记录
//			setAttr("mustCheckList", mustCheckList);//必问问题记录
//			setAttr("choiceCheckList", choiceCheckList);//选核问题
			setAttr("applyNodeFinalAuditData", applyNodeFinalAuditData);//终审结果
			setAttr("finalRefuseCodes", commonService.listToString(finalRefuseCodeList, ","));//终审拒绝原因
			setAttr("applyNodeTelCheckBisicData", applyNodeTelCheckBisicData);//电话调查结果
			setAttr("formKey", formKey);
			TmDitDic tmDitDic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_sendRefuseMessage);//发送短信开关
			if(tmDitDic != null){
				setAttr("sendRefuseMessage", tmDitDic.getRemark());
			}
			pagePath = cacheContext.getPageConfig(formKey);
			//操作人员的相关信息
			setAttr("userNo",OrganizationContextHolder.getUserNo());
			setAttr("branchCode",OrganizationContextHolder.getBranchCode());
			OrganizationContextHolder.getBranchCode();
		} catch (Exception e) {
			logger.error("设置["+formKey+"]页面失败.."+LogPrintUtils.printAppNoEndLog(appNo, tokenId,null), e);
//   throw new ProcessException(e.getMessage());
			pagePath = exceptionPageUtils.doExcepiton(e.getMessage(),appNo);
		}
		logger.info("结束设置["+formKey+"]页面,跳转路径["+pagePath+"]..."+LogPrintUtils.printAppNoEndLog(appNo, tokenId,null));
		return pagePath;
	}

	

	/**
	 * 国家代码、省、市、区、网点等下拉框异步加载
	 * @param dicType 数据字典类型
	 * @param parentValue 联动父级的值
	 * @param name 组件的name属性或value值
	 * @param isCode 是否显示code
	 * @param fieldType 字段类型(A：省市区、B:国家代码、C:领卡/受理网点、D：推广人网点（按等级）、E：推广人机构网点（按下级）)
	 * @return
	 */
	@RequestMapping("/addrQuery")
	@ResponseBody
	public Json addrQuery(String dicType,String name,Boolean isCode,String parentValue,String fieldType){
		Json json = Json.newSuccess();
		if(StringUtils.isEmpty(dicType)){
			logger.error("异步加载下拉框[数据字典类型]为空");
			json.setS(false);
			return json;
		}else {
			try {
				if(StringUtils.isEmpty(fieldType)){
					logger.error("异步加载下拉框[字段类型]为空");
					json.setS(false);
					return json;
				}
				StringBuffer optionBuffer = new StringBuffer();
				StringBuffer searchBuffer = new StringBuffer();
				List<TmAclDict> list = null;
				LinkedHashMap<Object, Object> branchMap = null;
				if("A".equals(fieldType)){
					TmAclDict tmAclDict = new TmAclDict();
					tmAclDict.setType(dicType);
					if(StringUtils.isBlank(parentValue)){
						list = accessDictService.getByType(dicType);
					}else {
						tmAclDict.setValue2(parentValue);
						list = cacheContext.getAclDictAddress(tmAclDict);
					}
				}else if("B".equals(fieldType)) {
					list = accessDictService.getByType(dicType);
				}else if("C".equals(fieldType)) {
					branchMap = cacheContext.getBranchMapByParam(dicType);
				}else if("D".equals(fieldType)) {//网点按等级查
					branchMap = cacheContext.getBranchMapByLevel(dicType);
				}else if ("E".equals(fieldType)) {//网点按网点查下级网点机构
					branchMap = cacheContext.getSubBranchByBranchOrUser(parentValue, null);
				}

				String[] defValue = {""};//联动为子级组件设置默认值
				int i = 0;
				if("ZONE".equals(dicType)){//添加空行选项
					optionBuffer.append("<option value='");
					optionBuffer.append("'>");
					optionBuffer.append("</option>");
					//搜索框选项
					searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
					searchBuffer.append(name);
					searchBuffer.append("' value='");
					searchBuffer.append("'><span>");
					searchBuffer.append("</span></label></li>");
					i++;
				}
				if(isCode){//显示code
					if("C".equals(fieldType) || "D".equals(fieldType) || "E".equals(fieldType)){//网点
						if (branchMap != null && branchMap.size() > 0){
							for (Entry<Object, Object> entry : branchMap.entrySet()) {
								optionBuffer.append("<option value='");
								optionBuffer.append(entry.getKey());
								optionBuffer.append("'>");
								optionBuffer.append(entry.getKey());
								optionBuffer.append("-");
								optionBuffer.append(entry.getValue());
								optionBuffer.append("</option>");
								//搜索框选项
								searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
								searchBuffer.append(name);
								searchBuffer.append("' value='");
								searchBuffer.append(entry.getKey());
								searchBuffer.append("'><span>");
								searchBuffer.append(entry.getKey());
								searchBuffer.append("-");
								searchBuffer.append(entry.getValue());
								searchBuffer.append("</span></label></li>");

								if(i == 0){
									defValue[0] = entry.getKey()==null?"":entry.getKey().toString();
									i++;
								}
							}
						}
					}else {//国家代码、省市区
						if(CollectionUtils.sizeGtZero(list)){
							for (TmAclDict tmAclDict : list) {
								optionBuffer.append("<option value='");
								optionBuffer.append(tmAclDict.getCode());
								optionBuffer.append("'>");
								optionBuffer.append(tmAclDict.getCode());
								optionBuffer.append("-");
								optionBuffer.append(tmAclDict.getCodeName());
								optionBuffer.append("</option>");
								//搜索框选项
								searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
								searchBuffer.append(name);
								searchBuffer.append("' value='");
								searchBuffer.append(tmAclDict.getCode());
								searchBuffer.append("'><span>");
								searchBuffer.append(tmAclDict.getCode());
								searchBuffer.append("-");
								searchBuffer.append(tmAclDict.getCodeName());
								searchBuffer.append("</span></label></li>");

								if(i == 0){
									defValue[0] = tmAclDict.getCode();
									i++;
								}
							}
						}
					}
				}else {//不显示code
					if("C".equals(fieldType) || "D".equals(fieldType) || "E".equals(fieldType)){//网点
						if (branchMap != null && branchMap.size() > 0){
							for (Entry<Object, Object> entry : branchMap.entrySet()) {
								optionBuffer.append("<option value='");
								optionBuffer.append(entry.getValue());
								optionBuffer.append("'>");
								optionBuffer.append(entry.getValue());
								optionBuffer.append("</option>");
								//搜索框选项
								searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
								searchBuffer.append(name);
								searchBuffer.append("' value='");
								searchBuffer.append(entry.getValue());
								searchBuffer.append("'><span>");
								searchBuffer.append(entry.getValue());
								searchBuffer.append("</span></label></li>");

								if(i == 0){
									defValue[0] = entry.getValue()==null?"":entry.getValue().toString();
									i++;
								}
							}
						}
					}else {//国家代码、省市区
						if(CollectionUtils.sizeGtZero(list)){
							for (TmAclDict tmAclDict : list) {
								optionBuffer.append("<option value='");
								optionBuffer.append(tmAclDict.getCodeName());
								optionBuffer.append("'>");
								optionBuffer.append(tmAclDict.getCodeName());
								optionBuffer.append("</option>");
								//搜索框选项
								searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
								searchBuffer.append(name);
								searchBuffer.append("' value='");
								searchBuffer.append(tmAclDict.getCodeName());
								searchBuffer.append("'><span>");
								searchBuffer.append(tmAclDict.getCodeName());
								searchBuffer.append("</span></label></li>");

								if(i == 0){
									defValue[0] = tmAclDict.getCodeName();
									i++;
								}
							}
						}
					}
				}
				json.setCode(searchBuffer.toString());
				json.setMsg(optionBuffer.toString());
				json.setObj(defValue);
			} catch (Exception e) {
				json.setS(false);
				logger.error("异步加载[{}]失败",dicType);
				throw new ProcessException("异步加载["+dicType+"]失败",e);
			}
		}

		return json;
	}

	/**
	 * 设置页面的必要信息
	 * @param applyInfoDto
	 * @param appType
	 * @return
	 */
	public ApplyOperateDto setApplyOperateDto(ApplyInfoDto applyInfoDto,String appType){
		ApplyOperateDto applyOperateDto = new ApplyOperateDto();
		if(applyInfoDto != null){
			TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
			TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();
			Map<String, TmAppCustInfo> custInfoMap=applyInfoDto.getTmAppCustInfoMap();//申请附卡信息表

			if(tmAppMain != null){
				applyOperateDto.setRtfState(tmAppMain.getRtfState());
//    applyOperateDto.setApproveQuickFlag(tmAppMain.getApproveQuickFlag());
//    applyOperateDto.setIsPriority(tmAppMain.getIsPriority());
				applyOperateDto.setSugLmt(tmAppMain.getSugLmt());
				applyOperateDto.setPointResult(tmAppMain.getPointResult());
				applyOperateDto.setIsFreeTelCheck(tmAppAudit.getIsFreeTelCheck());
				applyOperateDto.setChkLmt(tmAppMain.getChkLmt());
				applyOperateDto.setIsSendSmsRefused(tmAppAudit.getIsSendSmsRefused());
				applyOperateDto.setIsSendSmsPatch(tmAppAudit.getIsSendSmsPatch());
//    applyOperateDto.setSpecialApprove(tmAppMain.getSpecialApprove());
//    applyOperateDto.setBankInnerSugLmt(tmAppMain.getBankInnerSugLmt());
//    applyOperateDto.setBankInnerPointResult(tmAppMain.getBankInnerPointResult());
//    applyOperateDto.setBankInnerBreakRule(tmAppMain.getBankInnerBreakRule());
//    applyOperateDto.setBankInnerMome(tmAppMain.getBankInnerMome());
				applyOperateDto.setProductCd(tmAppMain.getProductCd());
				applyOperateDto.setAccLmt(tmAppMain.getAccLmt());
				applyOperateDto.setAppLmt(tmAppMain.getAppLmt());
				applyOperateDto.setRefuseCode(tmAppMain.getRefuseCode());
				applyOperateDto.setRiskClassic(tmAppAudit.getCfRiskClassic());//风险等级
				applyOperateDto.setIsOldCust(tmAppAudit.getIsOldCust());//是否是老客户
			}
			TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
			if(tmAppPrimCardInfo != null){
				applyOperateDto.setReviewNo(tmAppPrimCardInfo.getReviewNo());
				applyOperateDto.setReviewName(tmAppPrimCardInfo.getReviewName());
				applyOperateDto.setStmtMediaType(tmAppPrimCardInfo.getStmtMediaType());
				applyOperateDto.setStmtMailAddrInd(tmAppPrimCardInfo.getStmtMailAddrInd());
				applyOperateDto.setSpreaderName(tmAppPrimCardInfo.getSpreaderName());
				applyOperateDto.setSpreaderTelephone(tmAppPrimCardInfo.getSpreaderTelephone());
				applyOperateDto.setSpreaderType(tmAppPrimCardInfo.getSpreaderType());
				applyOperateDto.setSpreaderMode(tmAppPrimCardInfo.getSpreaderMode());
				applyOperateDto.setSpreaderNo(tmAppPrimCardInfo.getSpreaderNo());
				applyOperateDto.setSpreaderBranchOne(tmAppPrimCardInfo.getSpreaderBranchOne());
				applyOperateDto.setIsPrdChange(tmAppPrimCardInfo.getIsPrdChange());
			}
			if(StringUtils.isNotEmpty(appType)){
				if(AppType.S.name().equals(appType) && custInfoMap.containsKey(AppConstant.bscSuppInd_S_1)){
					TmAppCustInfo attachCust = custInfoMap.get(AppConstant.bscSuppInd_S_1);
					if(attachCust != null){
						applyOperateDto.setHomeState(attachCust.getHomeState());
						applyOperateDto.setHomeCity(attachCust.getHomeCity());
						applyOperateDto.setHomeZone(attachCust.getHomeZone());
						applyOperateDto.setHomeAdd(attachCust.getHomeAdd());
						applyOperateDto.setName(attachCust.getName());
						applyOperateDto.setEmbLogo(attachCust.getEmbLogo());
						applyOperateDto.setIdNo(attachCust.getIdNo());
						applyOperateDto.setIdType(attachCust.getIdType());
						applyOperateDto.setCellphone(attachCust.getCellphone());
						applyOperateDto.setEmail(attachCust.getEmail());
						applyOperateDto.setBirthday(attachCust.getBirthday());
						applyOperateDto.setHomePhone(attachCust.getHomePhone());
//      applyOperateDto.setGender(tmAppAttachInfo.getGender());
//      applyOperateDto.setEmpAdd(tmAppAttachInfo.getEmpAdd());
//      applyOperateDto.setEmpPhone(tmAppAttachInfo.getEmpPhone());
					}
				}else if(custInfoMap.containsKey(AppConstant.bscSuppInd_B_1)){
					TmAppCustInfo primCust = custInfoMap.get(AppConstant.bscSuppInd_B_1);
					TmAppContact tmAppContactInfo = applyInfoDto.getTmAppContactMap().get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
					if(primCust != null){
						applyOperateDto.setHomeState(primCust.getHomeState());
						applyOperateDto.setHomeCity(primCust.getHomeCity());
						applyOperateDto.setHomeZone(primCust.getHomeZone());
						applyOperateDto.setHomeAdd(primCust.getHomeAdd());
						applyOperateDto.setName(primCust.getName());
						applyOperateDto.setIdNo(primCust.getIdNo());
						applyOperateDto.setIdType(primCust.getIdType());
						applyOperateDto.setCellphone(primCust.getCellphone());
						applyOperateDto.setEmail(primCust.getEmail());
						applyOperateDto.setGender(primCust.getGender());
						applyOperateDto.setBirthday(primCust.getBirthday());
						applyOperateDto.setHomePhone(primCust.getHomePhone());
						applyOperateDto.setEmpAdd(primCust.getEmpAdd());
						applyOperateDto.setEmpPhone(primCust.getEmpPhone());
						applyOperateDto.setEmbLogo(primCust.getEmbLogo());
					}
					if(tmAppContactInfo != null){
						applyOperateDto.setContactName(tmAppContactInfo.getContactName());
						applyOperateDto.setContactMobile(tmAppContactInfo.getContactMobile());
						applyOperateDto.setContactRelation(tmAppContactInfo.getContactRelation());
					}
					TmAppContact tmAppOContactInfo = applyInfoDto.getTmAppContactMap().get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");
					if(tmAppOContactInfo != null){
						applyOperateDto.setContactOMobile(tmAppOContactInfo.getContactMobile());//其他联系人电话
					}
					if(applyInfoDto.getTmAppPrimCardInfo()!=null){
						applyOperateDto.setCreateDate(applyInfoDto.getTmAppPrimCardInfo().getInputDate());//主卡申请时间
						applyOperateDto.setPyhCd(applyInfoDto.getTmAppPrimCardInfo().getCardfaceCd());//主卡卡面
					}
				}
			}
		}
		return applyOperateDto;
	}
	// 获取完任务直接返回到当前页面
	@ResponseBody
	@RequestMapping("/claimTask")
	public Json claimTask(String taskId,String appNo) {
		Json j = Json.newSuccess();
		try {
			activitiService.claimTask(taskId,appNo);
		} catch (Exception e) {
			j.setFail(e.getMessage());
		}
		return j;
	}


	/*
	 * 获取流程定义图片(通过taskId获取)
	 */
	@RequestMapping("/showProDefImg")
	public ModelAndView showProDefImg(HttpServletRequest request,
									  HttpServletResponse response) throws IOException {

		String taskId = getPara("taskId");// 从页面上获取taskId
		InputStream is = activitiService.getProImgPath(taskId);// 从service中获取流
		if (is == null) {
			logger.info("未获取到对应的流程图！");
		}
		OutputStream os = response.getOutputStream();
		try {
			IOUtils.copy(is, os);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
		response.setContentType("image/png");// 设置输出类型

		return null;

	}

	/**
	 * 跳转到工作流程部署页面
	 *
	 * @return
	 */
	@RequestMapping("/activitiDeployePage")
	public String activitiDeployePage() {
		// 获取默认流程图的部署ID
		String deploymentId = activitiService.getDefProcessDepId();
		setAttr("defDeploymentId", deploymentId);
//  return "activitiDeploy/activitiDeployPage.ftl";
		return cacheContext.getPageConfig(CasPagePathConstant.applyActivitiDeployPage);
	}

	/**
	 * 上传提交流程图
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitDiagrams")
	public Json submitDiagrams() {
		Json j = Json.newSuccess();
		try {
			MultipartFile diagramFile = getFile("file");
			String fileName = (diagramFile == null) ? null : diagramFile.getOriginalFilename();
			if(fileName!=null){
				String suffix = fileName.substring(fileName.indexOf(".") + 1);// 没有“.”也不会抛异常
				if (!"zip".equals(suffix)) {
					j.setFail("流程图上传失败，必须压缩为zip格式");
					return j;
				}
				String proName = getPara("proName");
				activitiService.saveDeployment(diagramFile, proName);
			}
		} catch (Exception e) {
			j.setFail("流程图解析失败，请检查流程图");
		}
		return j;
	}

	/**
	 * 流程定义列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/processDefList")
	public Page<ProcessDefinitionDto> processDefList(){
		// 获取到所有的流程定义信息
		Page<ProcessDefinitionDto> page = new Page<ProcessDefinitionDto>();
		List<ProcessDefinitionDto> processDefinitionDtos = activitiService.getProcessDefinitionDtos();
		page.setRows(processDefinitionDtos);
		page.setTotal(processDefinitionDtos==null ? 0 : processDefinitionDtos.size());
		return page;
	}

	/*
	 * 获取流程定义图片(通过deploymentId和图片路径获取)
	 */
	@RequestMapping("/getProDefImg")
	public ModelAndView getProDefImg(HttpServletRequest request,
									 HttpServletResponse response) throws IOException {

		String diagramResourceName = getPara("diagramResourceName");// 从页面上获取diagramResourceName
		String deploymentId = getPara("deploymentId");// 从页面上获取deploymentId

		InputStream is = activitiService.getProImgPathByDeploymentId(
				diagramResourceName, deploymentId);// 从service中获取流
		if (is == null) {
			logger.info("未获取到对应的流程图！");
		}
		OutputStream os = response.getOutputStream();
		try {
			IOUtils.copy(is, os);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
		response.setContentType("image/png");// 设置输出类型

		return null;
	}

	/*
	 * 删除流程图
	 */
	@ResponseBody
	@RequestMapping("/deleteDeployment")
	public Json deleteDeployment(String deploymentId) {
		Json j = Json.newSuccess();
		try {
			activitiService.deleteDeployment(deploymentId);
		} catch (Exception e) {
			j.setFail("流程图删除出现异常");
		}
		return j;
	}

	/**
	 * 设置默认流程
	 *
	 * @param procdefKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initDeployment")
	public Json initDeployment(String procdefKey, @RequestParam(required=false) String deploymentId) {
		Json j = Json.newSuccess();
		try {
			activitiService.initDeployment(procdefKey, deploymentId);
		} catch (Exception e) {
			j.setFail(e.getMessage());
		}
		return j;
	}


	/**
	 * 设置自选卡号验证
	 * @param applyInfoDto
	 * @param cardBin
	 */
	private void setValidBitCard(ApplyInfoDto applyInfoDto, String cardBin) {
		Map<String, String> validBitCardMap = new HashMap<>();
		if (applyInfoDto == null || applyInfoDto.getTmAppCustInfoMap() == null) {
			return;
		}
		for (TmAppCustInfo cust : applyInfoDto.getTmAppCustInfoMap().values()) {
			if (cust == null) {
				continue;
			}
			logger.info("设置申请人[" + cust.getName() + "-" + cust.getBscSuppInd() + "]自选卡号开始《《《《《《《《《");
			Integer attachNo = cust.getAttachNo();
			if (attachNo == null) {
				attachNo = 1;
			}
			String ifSelectCard = cust.getIfSelectedCard();
			if (StringUtils.isNotEmpty(ifSelectCard) && ifSelectCard.equals(Indicator.Y.name())) {
				validBitCardMap.put(cust.getBscSuppInd() + attachNo, cardBin);// 回显附卡卡bin
			} else {
				cust.setCardNo(null);
			}
		}
		setAttr("validBitCardMap", validBitCardMap);// 自选卡号
	}
}