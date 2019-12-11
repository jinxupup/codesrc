package com.jjb.ams.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ams.app.controller.utils.AmsAppUtils;
import com.jjb.ams.app.controller.utils.AmsExceptionPageUtils;
import com.jjb.ams.app.controller.utils.AmsPagePathConstant;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.commonDialog.AlreadyCardService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.ApplyInfoSupport;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.ecms.facility.dto.ApplyOperateDto;
import com.jjb.ecms.facility.dto.FieldPageDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeReviewData;
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
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 用于任务执行时的页面跳转
 * @author hn
 * @date 2016年8月27日12:55:50
 */
@Controller
@RequestMapping("/ams_activiti")
public class AmsActivitiController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CacheContext cacheContext; 
	@Autowired
	private CommonService commonService;
	@Autowired
	private AlreadyCardService alreadyCardService;//已有卡
	@Autowired
	private AmsAppUtils amsAppUtils;//申请件处理工具类
	@Autowired
	private UserService userService;
	@Autowired
	private TmAclDictDao tmAclDictDao;
	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private TmExtRiskInputDao tmExtRiskInputDao;
	@Autowired
	private ApplyInfoSupport applyInfoSupport;
	@Autowired
	private AmsExceptionPageUtils exceptionPageUtils;
	@Autowired
	private TmMirCardDao tmMirCardDao;
	@Autowired
	private TmAppMainDao tmAppMainDao;

	/*
	 * 用于跳转到人工页面，并且获取页面所需的值
	 */
	@RequestMapping("/handleTask")
	public String handleTask() throws SecurityException, 
			IllegalArgumentException, 
			NoSuchFieldException, 
			IllegalAccessException {
		Long tokenId = System.currentTimeMillis();
		String appNo = getPara("appNo");
		String taskId = "";
		String detailFlag = getPara("detailFlag");//申请件进度查询查看详情操作标志“Y”
		String pagePath = null;// 定义页面路径
		String formKey = "";
 		logger.info("开始设置节点页面信息"+LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"taskId["+taskId+"]");
		try {
			Task task = activitiService.getTaskId(appNo);
			if(task!=null){
				taskId = task.getId();
			}
			if(StringUtils.isNotBlank(taskId)){
				formKey = activitiService.handleTask(taskId,appNo);
			}
			// 获取节点信息
			ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
			if(applyInfoDto==null){
				logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",ApplyInfoDto 表信息为空");
				throw new ProcessException("未查询到相关申请件信息,请刷新后重试!");
			}
			// 客户申请信息
			TmAppMain tmAppMain = applyInfoDto.getTmAppMain();//申请业务主表
			if(tmAppMain == null){
				logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",TmAppMain 表信息为空");
				throw new ProcessException("未查询到相关申请件信息,请刷新后重试!");
			}
			//如果是独立附卡申请则根据主卡卡号拷贝主卡信息，目的是更新卡产品、主卡申请人信息、卡面信息
			applyInfoDto = applyInfoSupport.setPrimApplicantInfo(applyInfoDto, tokenId);
			String productCd = tmAppMain.getProductCd(); //根据产品代码获取卡产品信息
			String appType = tmAppMain.getAppType(); //获取申请类型
			//无论是主卡还是独立附卡，都设置默认受理网点
	 		if(StringUtils.isEmpty(tmAppMain.getOwningBranch())){
	 			TmAclUser tmAclUser = commonService.getUserByUserNo(OrganizationContextHolder.getUserNo());
	 			if(tmAclUser != null){
	 				tmAppMain.setOwningBranch(tmAclUser.getBranchCode());//设置默认的受理网点
	 			}else {
	 				logger.info("找不到默认的受理网点信息，appNo="+appNo);
				}
	 		}
	 		// 获取卡产品信息
	 		TmProduct tmProduct = cacheContext.getProduct(productCd);
	 		if(tmProduct==null){
	 			throw new ProcessException("系统未获取到产品["+productCd+"]信息，您的登录或产品配置已失效，请重新登录系统后再试!");
	 		}
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
			Map<String,TmAppCustInfo> allCustMap = applyInfoDto.getTmAppCustInfoMap();//客户信息（包括主卡、附卡）
			TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();
			if(allCustMap==null){
				allCustMap = new HashMap<String, TmAppCustInfo>();
			}
//			List<TmAppCustInfo> allCustList= applyInfoDto.getTmAppCustInfoList();//申请客户信息
//			if(CollectionUtils.isEmpty(allCustList)){
//				allCustList = new ArrayList<TmAppCustInfo>();
//			}
//			TmAppCustInfo primCust;//主卡
//			Map<String,TmAppCustInfo> attachMap;//附卡
//			if (applyInfoDto.getTmAppCustInfoMap() != null) {
//				primCust = allCustMap.get(AppConstant.bscSuppInd_B);
//				attachMap = applyInfoDto.getTmAppCustInfoMap();
//			}
			TmExtRiskInput tmExtRiskInput = tmExtRiskInputDao.getTmExtRiskInputByAppNo(appNo);//第三方或行内风控
			if(tmExtRiskInput != null){
				setAttr("tmExtRiskInput",tmExtRiskInput);
			}
			if(StringUtils.isNotBlank(detailFlag) && detailFlag.equals(Indicator.Y.name())) {//申请件详情
				formKey = AmsPagePathConstant.AMS_APP_DETAIL;
			}else if (StringUtils.isNotBlank(taskId)) {
				formKey = activitiService.handleTask(taskId,appNo);
			} else {
				/*if(StringUtils.isNotBlank(updateFlag) && updateFlag.equals(Indicator.Y.name())){//申请件修改
					formKey = AmsPagePathConstant.updateApplyInfoPage;
				}else if(StringUtils.isNotBlank(detailFlag) && detailFlag.equals(Indicator.Y.name())) {//申请件详情
					formKey = AmsPagePathConstant.applyProcessQueryDetailPage;
				}else {
					//非重审 或者 非A05-申请录入, 则标注为异常.
					if(tmAppMain.getRtfState().equals(RtfState.A05.toString())){
						formKey = AmsPagePathConstant.AMS_APP_INPUT_MODIFY;
					}else{
						logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",taskId 信息为空");
						throw new ProcessException("执行申请件["+appNo+"]该任务无效，请重新选择！");
					}
				}*/
				if(tmAppMain.getRtfState().equals(RtfState.A05.toString())){
					formKey = AmsPagePathConstant.AMS_APP_INPUT_MODIFY;
				}else{
					logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",taskId 信息为空");
					throw new ProcessException("执行申请件["+appNo+"]该任务无效，请重新选择！");
				}
			}
			
			if (StringUtils.isEmpty(formKey)) {
				logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+",formKey 信息为空");
				throw new ProcessException("未找到申请件["+appNo+"]相应任务节点,请刷新后重试!");
			}
			logger.info("开始设置["+formKey+"]页面"+LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"taskId["+taskId+"]");
			
//			// 初审结果信息
//			ApplyNodeInquiryData applyNodeInquiryData = new ApplyNodeInquiryData();
			// 查询节点数据如果之前做过复核保存，就会存在applyNodeReviewData
			ApplyNodeReviewData applyNodeReviewData = new ApplyNodeReviewData();
//			//风险信息
//			ApplyRiskInfoDto applyRiskInfoDto = new ApplyRiskInfoDto();
//			// 获取补件信息
//			ApplyNodePatchBoltData applyNodePatchBoltData = new ApplyNodePatchBoltData();
//			// 待补件列表
//			List<ApplyPatchBoltDto> applyPatchBoltDtos = new ArrayList<ApplyPatchBoltDto>();
//			String patchBoltString = "";//补件信息
//			//拒绝原因（初审）
//			List<String> checkRefuseCodeList = new ArrayList<String>();
//			//拒绝原因（终审）
//			List<String> finalRefuseCodeList = new ArrayList<String>();
			
			//节点信息处理
			Map<String,Serializable> tmAppNodeInfoRecordMap = applyInfoDto.getTmAppNodeInfoRecordMap();//节点信息记录表
			if (tmAppNodeInfoRecordMap != null) {
				//复核
				if(tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_REVIEW_DATA)){
					applyNodeReviewData = (ApplyNodeReviewData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_REVIEW_DATA);
				}
			}
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
						Integer ifAttachRefuseNum = cust.getAttachNo();
						attachNoMap.put(ifAttachRefuseNum.toString(), cust.getName());//从0开始,N表示拒绝
						if(StringUtils.equals(cust.getRecordStatus(),Indicator.N.name())){
							if(StringUtils.isBlank(ifAttachRefuse)){
								ifAttachRefuse = ifAttachRefuseNum.toString();
							}else {
								ifAttachRefuse = ifAttachRefuse + "," + ifAttachRefuseNum.toString();
							}
						}
					}
//					allCustMap.put(cust.getBscSuppInd()+cust.getAttachNo(), cust);
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
						setAttr(tam.getMemoType()+tam.getTaskKey(), tam);
					}
				}
			}
			//如果是复核，则需要特殊处理，主要是复核项设置
			if(formKey.equals("application-review")){
				logger.info("开始设置复核页面信息==>"+LogPrintUtils.printAppNoLog(appNo, tokenId,null));
				LinkedHashMap<String, TreeMap<String, FieldPageDto>> reviewFieldMap = amsAppUtils.getReviewFields(productCd, tmAppMain.getAppType(), attachNum);
				setAttr("reviewFieldMap", reviewFieldMap);
				//回显复核项数据    map<申请类型，map<复核字段名,复核字段值>>
				if(applyNodeReviewData != null){
					Map<String , Map<String , String >> reviewsMap = applyNodeReviewData.getReviewsMap();
					if(reviewsMap != null && reviewsMap.size() > 0){
						for (String reviewKeys : reviewsMap.keySet()) {
							if(reviewsMap.get(reviewKeys)!=null && reviewsMap.get(reviewKeys).size()>0){
								setAttr(reviewKeys, reviewsMap.get(reviewKeys));
							}
						}
					}
				}
				//复核人信息处理
				if(StringUtils.isBlank(tmAppPrimCardInfo.getReviewNo())){
					TmAclUser tmAclUser = userService.getUserByUserNo(OrganizationContextHolder.getUserNo());
					tmAppPrimCardInfo.setReviewNo(tmAclUser.getUserNo());
					tmAppPrimCardInfo.setReviewName(tmAclUser.getUserName());
				}
				logger.info("结束设置复核页面信息==>"+LogPrintUtils.printAppNoEndLog(appNo, tokenId,null));
			}
			//查询申请件标签
			List<TmAppFlag> tmAppFlagList = applyQueryService.getTmAppFlagListByAppNo(appNo);
			List<String> appFlagList = new ArrayList<String>();
			if (tmAppFlagList.size() != 0) {
				for (TmAppFlag tmAppFlag : tmAppFlagList) {
					if (tmAppFlag != null) {
						appFlagList.add(tmAppFlag.getFlagCode());
					}
				}
				setAttr("appFlagList", commonService.listToString(appFlagList, ","));
			}

			//非复核，则需要验证录入必填项
			if(!formKey.equals(AmsPagePathConstant.AMS_APP_REVIEW)){
				HashMap<String,List<ValidFieldInfoDto>> validFieldMap = commonService.validateField(appType, tmProduct, attachNum);
				setAttr("validFieldMap", validFieldMap);
			}
			//是否readonly公务卡公司信息相关栏位
			if(tmProduct!=null && StringUtils.equals(tmProduct.getSubCardType(), "O")){
				setAttr("ifCorpReadonly", "true");
			}else {
				setAttr("ifCorpReadonly", "false");
			}
			setAttr("taskId", taskId);//工作流任务id
			//重审时需要取新获取的appNo
			setAttr("appNo",tmAppMain.getAppNo());//申请件编号
			setAttr("appType", appType);//申请类型
			setAttr("productCd", productCd);//卡产品
			setAttr("oldAppNo",applyInfoDto.getOldAppNo());//原申请件appNo(重审前)
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
			//设置自选卡号格式匹配
			Integer len = cardNoLen - cardBinLen - 1;
			setAttr("selectCardNoRegexp", "^(\\d{"+len+"}$)");
			setAttr("tmProduct",tmProduct);
			setAttr("pageFieldMap", amsAppUtils.getPageFieldMap(tmProduct, appType, formKey, 0, attachNum, applyInfoDto));
			setAttr("applyOperateDto", setApplyOperateDto(applyInfoDto,appType));
			setAttr("attachNum", attachNum);
			if(tmAppContactInfoMap!=null){
				for (String key : tmAppContactInfoMap.keySet()){
					setAttr(key,tmAppContactInfoMap.get(key));
				}
			}
			setAttr("tmAppMemoMapAll", tmAppMemoMapAll);// 全部滴备注备忘
			setAttr("tmAppMemoMapLast", tmAppMemoMapLast);// 各节点最新滴备注备忘
			setAttr("tmAppHistoryList", tmAppHistoryList);// 历史申请审批记录
			setAttr("formKey", formKey);
			TmDitDic tmDitDic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_sendRefuseMessage);//发送短信开关
			if(tmDitDic != null){
				setAttr("sendRefuseMessage", tmDitDic.getRemark());
			}
			pagePath = cacheContext.getPageConfig("ams-"+formKey);
		} catch (Exception e) {
			logger.error("设置["+formKey+"]页面失败.."+LogPrintUtils.printAppNoEndLog(appNo, tokenId,null), e);
//			throw new ProcessException(e.getMessage());
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
	 * 如果是独立附卡申请则根据主卡卡号拷贝主卡信息
	 * @param applyInfoDto
	 * @param tokenId
	 * @return
	 */
	private ApplyInfoDto setPrimApplicantInfo(ApplyInfoDto applyInfoDto,Long tokenId){
		if(applyInfoDto==null){
			logger.error("独立附卡申请则根据主卡卡号拷贝主卡信息失败!ApplyInfoDto is null");
			return applyInfoDto;
		}
		TmAppMain tmAppMain=applyInfoDto.getTmAppMain();//申请业务主表
		if(tmAppMain==null){
			logger.error(LogPrintUtils.printAppNoLog(applyInfoDto.getAppNo(), tokenId,null)+",TmAppMain 表信息为空");
			throw new ProcessException("未查询到相关申请件信息,请刷新后重试!");
		}
		Map<String, TmAppCustInfo> tmAppAttachInfoMap=applyInfoDto.getTmAppCustInfoMap();//申请附卡信息表
		//如果是独立附卡申请，就带出主卡信息
		if(tmAppMain!=null && AppType.S.name().equals(tmAppMain.getAppType())){
			//判断附卡是否存在 
			if (tmAppAttachInfoMap != null && tmAppAttachInfoMap.get(AppConstant.bscSuppInd_S_1)!=null) {
				//获取第一张附卡
				TmAppCustInfo attachCust = tmAppAttachInfoMap.get(AppConstant.bscSuppInd_S_1);
				String primCardNo = attachCust.getPrimCardNo();
				if(StringUtils.isNotEmpty(primCardNo)){
					logger.info("开始设置-独立附卡根据主卡卡号拷贝主卡信息"+LogPrintUtils.printAppNoLog(applyInfoDto.getAppNo(), tokenId,null)+"主卡卡号["+primCardNo+"]");
					applyInfoDto = commonService.queryPrimApplicantInfoByPrimCardNo(primCardNo, applyInfoDto);
					if(tmAppAttachInfoMap.containsKey(AppConstant.bscSuppInd_B_1)){
						logger.info("设置-独立附卡根据主卡卡号拷贝主卡信息-成功"+LogPrintUtils.printAppNoLog(applyInfoDto.getAppNo(), tokenId,null)+"主卡卡号["+primCardNo+"]");
					}
				}
			}
		}
		return applyInfoDto;
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
//				applyOperateDto.setApproveQuickFlag(tmAppMain.getApproveQuickFlag());
//				applyOperateDto.setIsPriority(tmAppMain.getIsPriority());
				applyOperateDto.setSugLmt(tmAppMain.getSugLmt());
				applyOperateDto.setPointResult(tmAppMain.getPointResult());
				applyOperateDto.setIsFreeTelCheck(tmAppAudit.getIsFreeTelCheck());
				applyOperateDto.setChkLmt(tmAppMain.getChkLmt());
				applyOperateDto.setIsSendSmsRefused(tmAppAudit.getIsSendSmsRefused());
				applyOperateDto.setIsSendSmsPatch(tmAppAudit.getIsSendSmsPatch());
//				applyOperateDto.setSpecialApprove(tmAppMain.getSpecialApprove());
//				applyOperateDto.setBankInnerSugLmt(tmAppMain.getBankInnerSugLmt());
//				applyOperateDto.setBankInnerPointResult(tmAppMain.getBankInnerPointResult());
//				applyOperateDto.setBankInnerBreakRule(tmAppMain.getBankInnerBreakRule());
//				applyOperateDto.setBankInnerMome(tmAppMain.getBankInnerMome());
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
					//获取副卡的信息
					TmAppCustInfo tmAppCustInfo = custInfoMap.get(AppConstant.bscSuppInd_S_1);
					//获取主卡的信息
					if(tmAppCustInfo != null){
						String cardNo=tmAppCustInfo.getPrimCardNo();
						TmMirCard tmMirCard=tmMirCardDao.getTmMirCardByCardNo(cardNo);
						String  appNo=tmMirCard.getAppNo();
						TmAppMain appMain = tmAppMainDao.getTmAppMainByAppNo(appNo);
						applyOperateDto.setName(appMain.getName());
						applyOperateDto.setIdType(appMain.getIdType());
						applyOperateDto.setIdNo(appMain.getIdNo());
						applyOperateDto.setCreateDate(appMain.getCreateDate());
/*						applyOperateDto.setHomeState(mainCustInfo.getHomeState());
						applyOperateDto.setHomeCity(mainCustInfo.getHomeCity());
						applyOperateDto.setHomeZone(mainCustInfo.getHomeZone());
						applyOperateDto.setHomeAdd(mainCustInfo.getHomeAdd());
						applyOperateDto.setName(mainCustInfo.getName());
						applyOperateDto.setEmbLogo(mainCustInfo.getEmbLogo());
						applyOperateDto.setIdNo(mainCustInfo.getIdNo());
						applyOperateDto.setIdType(mainCustInfo.getIdType());
						applyOperateDto.setCellphone(mainCustInfo.getCellphone());
						applyOperateDto.setEmail(mainCustInfo.getEmail());
						applyOperateDto.setBirthday(mainCustInfo.getBirthday());
						applyOperateDto.setHomePhone(mainCustInfo.getHomePhone());
						applyOperateDto.setCreateDate(mainCustInfo.getCreateDate());*/
//						applyOperateDto.setGender(tmAppAttachInfo.getGender());
//						applyOperateDto.setEmpAdd(tmAppAttachInfo.getEmpAdd());
//						applyOperateDto.setEmpPhone(tmAppAttachInfo.getEmpPhone());


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