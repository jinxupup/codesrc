package com.jjb.ams.app.controller.apply;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ams.app.controller.utils.AmsAppUtils;
import com.jjb.ams.app.controller.utils.AmsPagePathConstant;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.biz.dao.apply.TmAppMemoDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimAnnexEviDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyCardNoService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.ecms.util.ChineseToPinYin;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;


/**
 * @Description: 申请录入信息
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */

@Controller
@RequestMapping("/ams_applyInput")
public class ApplyInputController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ApplyCardNoService applyCardNoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplySpreaderInfoService applySpreaderInfoService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private AmsAppUtils applyInfoUtils;
	@Autowired
	private TmAppCustInfoDao tmAppCustInfoDao;
	@Autowired
	private TmAppPrimAnnexEviDao tmAppPrimAnnexEviDao;
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private TmAppMemoDao tmAppMemoDao;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;

	/**
	 * 根据卡产品productCd获取自选卡号校验位或卡bin
	 * 申请件编号appNo
	 * 卡号cardNo
	 * 附卡编号attachNo
	 * 获取tmProduct对象
	 */
	@ResponseBody
	@RequestMapping("/getTmProduct")
	public Json getTmProduct(String productCd,String appNo,String cardNo,String attachNo,String bscSuppInd){
		Json json = Json.newSuccess();
		if(StringUtils.isNotEmpty(productCd)){
			TmProduct tmProduct = null;
			try {
				tmProduct = cacheContext.getProduct(productCd);
				if(tmProduct != null && tmProduct.getApprovalMaximum() == null){
					tmProduct.setApprovalMaximum(new BigDecimal(999999999999999.00));
				}else if(tmProduct==null){
					tmProduct = new TmProduct();
					json.setS(false);
					json.setFail("获取卡产品"+productCd+"不存在！");
				}
				json.setObj(tmProduct);
			} catch (Exception e) {
				json.setS(false);
				json.setFail("获取卡产品"+productCd+"信息失败！");
				logger.error("获取卡产品"+productCd+"信息失败！",e);
			}
			if(StringUtils.isNotEmpty(cardNo)){//N位自选卡号
				try {
					Integer max = Integer.valueOf(tmProduct.getCardNoRangeCeil());
					Integer min = Integer.valueOf(tmProduct.getCardNoRangeFlr());
					Integer cardNoInteger = Integer.valueOf(cardNo);
					if(cardNoInteger >= min && cardNoInteger <= max){//判断自选卡号区间是否正确
						cardNo = tmProduct.getBin()+cardNo;//拼成N位自选卡号		
						/**
						 * 返回原卡号/原卡号+校验位的cardNo
						 * 原卡号:表示该自选卡号已使用
						 * 原卡号+校验位:表示该自选卡号未使用
						 */
						Integer attno = StringUtils.stringToInteger(attachNo);
						String cardNoValidBit = applyCardNoService.validateByCardNo(cardNo, appNo, attno, bscSuppInd);
						int cardNoLen = cardNo.length();
						if(StringUtils.isNotEmpty(cardNoValidBit)){
							if(cardNoValidBit.length() == cardNoLen){//卡号长度没变表示该自选卡号已使用
								json.setMsg("false");
								json.setFail("该卡号"+cardNo+"已使用");
								logger.info("该卡号[{}]已使用",cardNo);
							}
							if(cardNoValidBit.length() == cardNoLen + 1){//卡号长度+1表示该自选卡号未使用
								json.setMsg(cardNoValidBit.substring(15));//取校验位
							}
						}else {
							json.setS(false);
							json.setFail("自选卡号"+cardNo+"验证失败！");
						}
					}else {
						json.setMsg("false");
						json.setFail("该卡号"+cardNo+"不在该卡产品范围内");
						logger.info("该卡号[{}]不在该卡产品范围内",cardNo);
					}
				} catch (Exception e) {
					json.setS(false);
					json.setFail("自选卡号"+cardNo+"验证失败！");
					logger.error("自选卡号"+cardNo+"验证失败！",e);
				}
			}
		}
		return json;
	}
		
	/**
	 * 由于卡产品改变导致自选卡号解锁
	 * @param cardNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cardNoDelete")
	public Json cardNoDelete(String cardNo, String appNo, String attachNo, String validBit,String bscSuppInd){
		Json json = Json.newSuccess();
		if(StringUtils.isEmpty(appNo)){
			logger.info("自选卡号"+cardNo+"解锁appNo为空");
			json.setS(false);
			return json;
		}
		if(StringUtils.isNotEmpty(cardNo)){
			try {
				Integer attno = StringUtils.stringToInteger(attachNo);
				//核心解锁、申请人信息表更新卡号、更新锁表释放卡号
				applyCardNoService.unlockSelectedCardNo(cardNo+validBit, appNo, attno, bscSuppInd);
				json.setS(true);
				logger.info("自选卡号"+cardNo+"解锁成功！");
			} catch (Exception e) {
				json.setS(false);
				json.setFail(e.getMessage());
				logger.error("自选卡号"+cardNo+"解锁失败！",e);
			}
		}
		return json;
	}
	
	/**
	 * 根据姓名获取姓名拼音
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getEmbLogo")
	public Json getEmbLogo(String name){
		Json json = Json.newSuccess();
		try {
			String embLogo = ChineseToPinYin.getFullSpell(name);
			json.setMsg(embLogo);
			logger.info("姓名"+name+"转换拼音成功！");
		} catch (Exception e) {
			json.setFail(e.getMessage());
			logger.error("姓名"+name+"转换拼音失败！",e);
		}
		
		return json;
	}
	
	/**
	 * 验证身份证号的正确性
	 * @param idNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/idNoValid")
	public Json idNoValid(String idNo){
		Json json = Json.newSuccess();
		try {
			Boolean flag = IdentificationCodeUtil.isIdentityCode(idNo);
			json.setS(flag);			
			logger.info("身份号"+idNo+"验证成功！");
		} catch (Exception e) {
			json.setFail(e.getMessage());
			logger.error("身份号"+idNo+"不正确！",e);
		}
		
		return json;
	}
	
	/**
	 * 工号验证，返回用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/userIdValid")
	@ResponseBody
	public Json userIdValid(String userId){
		Json json = Json.newSuccess();
		logger.info("工号验证开始。。。。。。，userId="+userId);
		if(StringUtils.isNotBlank(userId)){
			try {
				TmDitDic useuUserInfo = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_useUserInfo);//是否开启推广人信息验证,A-使用用户信息,B-强制使用用户信息,C-使用推广人信息,D-强制使用推广人信息
				if (useuUserInfo != null && StringUtils.isNotEmpty(useuUserInfo.getRemark())) {
					/**
					 * 推广人校验有根据用户信息校验和推广人信息校验
					 */
					if (useuUserInfo.getRemark().equals("A") || useuUserInfo.getRemark().equals("B")) {//使用用户信息验证
						TmAclUser user = userService.getUserByUserNo(userId);
						if (user != null) {//如果用户信息存在，则带出用户相关信息
							json.setObj(user);
							json.setCode("user");
							TmAclBranch tmAclBranch = commonService.getTmAclBranch(user.getBranchCode());// 查询上级机构
							if (tmAclBranch != null && StringUtils.isNotEmpty(tmAclBranch.getParentCode())) {
								json.setMsg(tmAclBranch.getParentCode());// 上级分行编号.getParentMagBranch()
							}else {
								json.setMsg("");
							}
						} else {
							if (useuUserInfo.getRemark().equals("A")) {//如果没开启强制验证（A-使用户用信息），则不会清空数据，可继续录入
								json.setCode("A");
								json.setMsg("用户["+userId+"]不存在");
							} else if (useuUserInfo.getRemark().equals("B")) {//如果开启强制验证（B-强制使用用户信息）,则清空数据
								json.setCode("B");
								json.setMsg("用户["+userId+"]不存在,请添加该用户");
							}
						}
					}else if (useuUserInfo.getRemark().equals("C") || useuUserInfo.getRemark().equals("D")) {//使用推广人信息验证
						TmAppSprePerBank spreader = new TmAppSprePerBank();
						spreader.setSpreaderNum(userId);
						spreader.setSpreaderStatus("1");
						List<TmAppSprePerBank> spreaders = applySpreaderInfoService.getSpreaderByParam(spreader);
						if (CollectionUtils.sizeGtZero(spreaders)) {//如果推广人存在，则带出相关推广人信息
							spreader = spreaders.get(0);
							json.setCode("spreader");
							json.setObj(spreader);// 推广人信息
							TmAclBranch tmAclBranch = commonService.getTmAclBranch(spreader.getSpreaderBankId());// 查询上级机构
							if (tmAclBranch != null && StringUtils.isNotEmpty(tmAclBranch.getParentCode())) {
								json.setMsg(tmAclBranch.getParentCode());// 上级分行编号.getParentMagBranch()
							}else {
								json.setMsg("");
							}
							logger.info("推广人/客户经理工号验证成功，工号为"+userId+","+"姓名为"+spreader.getSpreaderName());
						}else {
							if (useuUserInfo.getRemark().equals("C")) {//如果没开启强制验证（C-使用推广人信息），则不会清空数据，可继续录入
								json.setCode("C");
								json.setMsg("推广人/客户经理["+userId+"]信息不存在");
							} else if (useuUserInfo.getRemark().equals("D")) {//如果开启强制验证（D-强制使用推广人信息）,则清空数据
								json.setCode("D");
								json.setMsg("推广人/客户经理["+userId+"]信息不存在，请新增该推广人/客户经理");
							}
						}
					}
				} else {
					logger.info("未配置工号验证，请在进阶参数管理页面中进行配置！");
				}
			} catch (Exception e) {
				json.setFail(e.getMessage());
				logger.error("推广人/客户经理工号验证失败，userId="+userId, e);
			}
		}
		
		return json;
	}
	
	/**
	 * 导入历史信息按钮
	 * @param 历史申请件编号historyAppNo
	 * @param 历史申请类型historyAppType
	 * @param 当前申请件编号appNo
	 * @param 当前申请类型appType
	 */
	@RequestMapping("/importHistoryInfo")
	public String importHistoryInfo(String historyAppNo ,String historyAppType ,String appNo ,String appType){
		if(StringUtils.isBlank(appType)){
			throw new ProcessException("导入历史信息时,申请类型为空!");
		}
							
 		ApplyInfoDto hisApplyInfoDto = applyQueryService.getApplyInfoByAppNo(historyAppNo);
 		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
 		applyInfoDto.setAppNo(appNo);
 		TmAppMain hisTmAppMain = hisApplyInfoDto.getTmAppMain();
 		TmAppMain tmAppMain = hisTmAppMain; //历史信息覆盖当前申请信息
 		//申请信息更新
 		tmAppMain.setAppNo(appNo);	//申请件编号更新
 		tmAppMain.setAppType(appType);//申请类型更新
 		tmAppMain.setRtfState(RtfState.A05.name());  //审批状态更新
 		
 		TmAppAudit auditHist = hisApplyInfoDto.getTmAppAudit();
 		TmAppAudit audit = auditHist;
 		
 		audit.setIsInstalment(null);//更新是否申请分期
 		audit.setIsRealtimeIssuing(auditHist.getIsRealtimeIssuing());//更新是否实时发卡
 		
 		//无论是主卡还是独立附卡，都设置默认的受理网点
		TmAclUser tmAclUser = commonService.getUserByUserNo(OrganizationContextHolder.getUserNo());
		if(tmAclUser != null){
			tmAppMain.setOwningBranch(tmAclUser.getBranchCode());//设置默认的受理网点
		}
		ApplyInfoDto newApplyInfoDto = new ApplyInfoDto();
		newApplyInfoDto.setAppNo(appNo);
		Integer attachNum = 0;
		if(appType.equals(AppType.A.name()) || appType.equals(AppType.B.name())){
			//卡产品更新
			tmAppMain.setProductCd(applyInfoDto.getTmAppMain().getProductCd());

			//主附同申时附卡信息处理
//			if(appType.equals(AppType.A.name())){
//				TmAppAttachApplierInfo attachCust = new TmAppAttachApplierInfo();
//				attachCust.setAppNo(appNo);
//				attachCust.setAttachNo(5);
//				attachCust.setCreateUser(OrganizationContextHolder.getUserNo());
//				attachCust.setCreateDate(new Date());
//				
//				Map<String, TmAppAttachApplierInfo> tmAppAttachInfoMap = new HashMap<String, TmAppAttachApplierInfo>();
//				tmAppAttachInfoMap.put(AppConstant.ATTATCH_TABS+"5",attachCust);
//				newApplyInfoDto.setTmAppAttachInfoMap(tmAppAttachInfoMap);
//				attachNum = 1;
//			}
//			//主卡信息处理
//			TmAppPrimApplicantInfo primCust = null;
//			if(historyAppType.equals(AppType.S.name())){
//				//主卡信息赋值
//				primCust = AppCommonUtil.setPrimAppData(hisApplyInfoDto, applyInfoDto);					
//			}else {
//				TmAppPrimApplicantInfo hisTmAppPrimApplicantInfo = hisApplyInfoDto.getTmAppPrimApplicantInfo();
//				primCust = hisTmAppPrimApplicantInfo;
//				TmAppPrimApplicantInfo priAppInfo = applyInfoDto.getTmAppPrimApplicantInfo();
//				if(priAppInfo != null){
//					primCust.setAppNo(appNo);
//					String name = priAppInfo.getName();
//					primCust.setName(name);
//					String embLogo = ChineseToPinYin.getFullSpell(name);
//					primCust.setEmbLogo(embLogo);//根据名字自动填充姓名拼音
//			 		String idType = priAppInfo.getIdType(); //获得证件类型
//			 		String idNo = priAppInfo.getIdNo();	//获得证件号码
//					primCust.setIdNo(idNo);
//					primCust.setIdType(idType);
//					if(StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name()) && StringUtils.isNotEmpty(idNo)){
//						//计算生日
//						primCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
//						//计算性别
//						primCust.setGender(IdentificationCodeUtil.getGender(idNo));
//						//计算年龄
//						primCust.setAge(IdentificationCodeUtil.getAge(idNo));
//					}else {
//						primCust.setBirthday(null);
//						primCust.setGender(null);
//						primCust.setAge(null);
//					}
//					primCust.setIfSelectedCard(null);//初始化自选卡号
//					primCust.setCardNo(null);
//					primCust.setCellphone(priAppInfo.getCellphone());	
//					primCust.setCreateUser(OrganizationContextHolder.getUserNo());
//					primCust.setCreateDate(new Date());				
//				}
//			}
//			newApplyInfoDto.setTmAppPrimApplicantInfo(primCust);
			
			if(hisApplyInfoDto.getTmAppContactMap() != null && hisApplyInfoDto.getTmAppContactMap().size() > 0){
				newApplyInfoDto.setTmAppContactMap(hisApplyInfoDto.getTmAppContactMap());
			}
			if(hisApplyInfoDto.getTmAppPrimAnnexEvi() != null){
				newApplyInfoDto.setTmAppPrimAnnexEvi(hisApplyInfoDto.getTmAppPrimAnnexEvi());
			}
		}else if(appType.equals(AppType.S.name())){
			//附卡信息处理
//			TmAppAttachApplierInfo attachCust = null;
//			if(historyAppType.equals(AppType.S.name())){
//				TmAppAttachApplierInfo hisTmAppAttachApplierInfo = hisApplyInfoDto.getTmAppCustInfoMap().get(AppConstant.ATTATCH_TABS+5);//获取第一张附卡信息
//				attachCust = hisTmAppAttachApplierInfo;
//				TmAppAttachApplierInfo attachApplierInfo = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.ATTATCH_TABS+5);
//				if(attachApplierInfo != null){
//					attachCust.setAppNo(appNo);
//					String name = attachApplierInfo.getName();
//					attachCust.setName(name);
//					String embLogo = ChineseToPinYin.getFullSpell(name);
//					attachCust.setEmbLogo(embLogo);//根据姓名自动填充姓名拼音
//					String idType = attachApplierInfo.getIdType(); //获得证件类型
//			 		String idNo = attachApplierInfo.getIdNo();	//获得证件号码
//					attachCust.setIdType(idType);
//					attachCust.setIdNo(idNo);
//					if(StringUtils.isNotEmpty(idNo) && StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name())){
//						//计算生日
//						attachCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
//						//计算性别
//						attachCust.setGender(IdentificationCodeUtil.getGender(idNo));
//						//计算年龄
//						attachCust.setAge(IdentificationCodeUtil.getAge(idNo));
//					}else {
//						attachCust.setBirthday(null);
//						attachCust.setGender(null);
//						attachCust.setAge(null);
//					}
//					attachCust.setIfSelectedCard(null);//初始化自选卡号
//					attachCust.setCardNo(null);
//					attachCust.setCellphone(attachApplierInfo.getCellphone());
//					attachCust.setPrimCardNo(attachApplierInfo.getPrimCardNo());
//					attachCust.setRelationshipToBsc(null);
//					attachCust.setAttachNo(5);
//					attachCust.setCreateUser(OrganizationContextHolder.getUserNo());
//					attachCust.setCreateDate(new Date());
//				}
//			}else {
//				//附属卡信息赋值
//				attachCust = AppCommonUtil.setAttachAppData(hisApplyInfoDto, applyInfoDto);
//			}
//			Map<String, TmAppAttachApplierInfo> tmAppAttachInfoMap = new HashMap<String, TmAppAttachApplierInfo>();
//			tmAppAttachInfoMap.put(AppConstant.ATTATCH_TABS+"5",attachCust);
//			newApplyInfoDto.setTmAppAttachInfoMap(tmAppAttachInfoMap);
//			
//			//设置回显主卡人主要信息
//			String primCardNo = attachCust.getPrimCardNo();
//			if(StringUtils.isNotEmpty(primCardNo)){
//				applyInfoDto = commonService.queryPrimApplicantInfoByPrimCardNo(primCardNo, applyInfoDto);
//				newApplyInfoDto.setTmAppPrimApplicantInfo(applyInfoDto.getTmAppPrimApplicantInfo());
//				newApplyInfoDto.setTmAppCardFaceInfoMap(applyInfoDto.getTmAppCardFaceInfoMap());
//			}
			attachNum = 1;
		}
		setAttr("attachNum", attachNum);//附卡数目
		setAttr("appNo", appNo);
		setAttr("appType", appType);
		setAttr("showBtnFlag", true);//是否显示附卡添加/删除按钮
		newApplyInfoDto.setTmAppMain(tmAppMain);
		
		//银行栏公共信息
		TmAppPrimCardInfo primCardInfo = hisApplyInfoDto.getTmAppPrimCardInfo();
		TmAppPrimCardInfo tmAppPrimCardInfo = new TmAppPrimCardInfo();
		tmAppPrimCardInfo.setInputName(OrganizationContextHolder.getUserNo());
		tmAppPrimCardInfo.setInputDate(new Date());
		if (primCardInfo != null) {
			tmAppPrimCardInfo.setBillingCycle(primCardInfo.getBillingCycle());
		}
		newApplyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);
		// 获取卡产品信息
		TmProduct tmProduct =  cacheContext.getProduct(tmAppMain.getProductCd());
		//申请录入页面必输项字段配置,默认只有一张附卡
		HashMap<String,List<ValidFieldInfoDto>> validFieldMap = commonService.validateField(appType, tmProduct, 1);
		setAttr("validFieldMap", validFieldMap);
		//页面字段配置
		newApplyInfoDto.setTmAppAudit(audit);
		//申请人信息表字段配置
		newApplyInfoDto.setTmAppCustInfoMap(hisApplyInfoDto.getTmAppCustInfoMap());
		setAttr("pageFieldMap", applyInfoUtils.getPageFieldMap(tmProduct, appType,
				AmsPagePathConstant.AMS_APP_INPUT_MODIFY, 0, attachNum, newApplyInfoDto));
		
		//是否显示分期信息
		if("Y".equals(audit.getIsInstalment())){
			setAttr("defaultInstalChoose", "Y");
			setAttr("ifNotApplyInstal", "false");
		}else {
			setAttr("defaultInstalChoose", "N");
			setAttr("ifNotApplyInstal", "true");
		}
//		if("Y".equals(tmProduct.getIsInstalment())){
//			setAttr("ifNotShowInstal", "false");
//			setAttr("defaultInstalChoose", "Y");
//		}else{
//			setAttr("ifNotShowInstal", "true");
//		}
		setAttr("ifNotShowInstal", "true");
		//是否使用自定义费率
//		if("Y".equals(tmProduct.getIfUseCustomRate())){
//			setAttr("ifNotUseCustomRate", "false");
//		}else {
//			setAttr("ifNotUseCustomRate", "true");
//		}
		setAttr("ifNotUseCustomRate", "true");
		//是否readonly公务卡公司信息相关栏位
		if("O".equals(tmProduct.getSubCardType())){
			setAttr("ifCorpReadonly", "true");
		}else {
			setAttr("ifCorpReadonly", "false");
		}
		setAttr("isEditMemo", true);//录入页面标志，用于推广注记是否可编辑（预录入和历史导入可编辑）
		
		return cacheContext.getPageConfig(AmsPagePathConstant.AMS_APP_INPUT_MODIFY);
	}
	
	/**
	 * 添加主附同申附卡部分
	 * @return
	 */
	@RequestMapping("/addAttachCard")
	public String addAttachCard(Integer num, String productCd){
		// 获取卡产品信息
		TmProduct tmProduct =  cacheContext.getProduct(productCd);
		setAttr("pageFieldMap", applyInfoUtils.getPageFieldMap(tmProduct, AppType.A.name(), 
				AmsPagePathConstant.AMS_APP_INPUT_MODIFY, 1, num, null));
		setAttr("atId", num);
		setAttr("showBtnFlag", true);//设置是否显示新增/删除附卡按钮的标志
		setAttr("addAttachFlag", true);//设置添加附卡的标志

		return "/amsTask/applyCC/applyInputOrModify/V1/applyAddAttachCard_V1.ftl";
	}

	
	/**
	 * set录入界面数据模型对象方法setApplyInfoDto()
	 * 
	 * @param applyInfoDto 录入界面数据模型对象
	 * @param retrialFlag 重审标志
	 * @param oldAppNo 重审件原appNo
	 * @param saveFlag 保存标示（区分提交和删除）
	 * @param rtfState 审批状态
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public void setApplyInfoDto(ApplyInfoDto applyInfoDto, String retrialFlag,
			String oldAppNo, String saveFlag, String rtfState, Json json)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		TmAppMain appMainPage = getBean(TmAppMain.class);
		TmAppAudit appAuditPage = getBean(TmAppAudit.class);
		String appNo = appMainPage.getAppNo();// 申请件编号
		long tokenId = System.currentTimeMillis();
		ApplyInfoDto applyInfoDtos = applyQueryService.getApplyInfoByAppNo(appNo);
		TmAppMain tmAppMainUpdate = applyInfoDtos.getTmAppMain();
		try {
			if(StringUtils.isEmpty(appNo)){
				throw new ProcessException("保存失败，申请进编号为空，请稍候再试！");
			}
			tmAppMainUpdate.setAppProperty(appMainPage.getAppProperty());//特批
			TmAppCustInfo primCustdb = applyInfoDtos.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_B_1);
			String appType = appMainPage.getAppType();
			if(StringUtils.isEmpty(appType)){
				throw new ProcessException("保存失败，申请类型为空，请稍候再试！");
			}
			//根据申请类型获取历史修改字段对比项
			Map<String,Map<String,String>> fieldsMap = commonService.getHisModifyFieldsMap(appType, appMainPage.getProductCd(),"I");
			Map<String, Object> modifiedFieldsMap = null;//发生修改的字段
			if (AppType.S.name().equals(appType)) {
				TmAppCustInfo attachCustPage = getBean(TmAppCustInfo.class);
				attachCustPage.setAppNo(appNo);
				if(StringUtils.isBlank(getPara("validBit"))){
					attachCustPage.setCardNo(null);//自选卡号
				}else {
					attachCustPage.setCardNo(getPara("cardBin")+attachCustPage.getCardNo()+getPara("validBit"));//自选卡号
				}
				List<TmAppCustInfo> custs = new ArrayList<TmAppCustInfo>();

				TmAppCustInfo attachCustUpdate = applyInfoDtos.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S_1);
				modifiedFieldsMap = commonService.compareValue(attachCustPage.convertToMap(),attachCustUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_CUST_INFO_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CUST_INFO));

				attachCustUpdate = commonService.getModifiedClazz(attachCustUpdate, modifiedFieldsMap);
				custs.add(attachCustUpdate);
				tmAppCustInfoDao.updateTmAppCustInfo(attachCustUpdate);
				//后期优化，待办任务和申请进度查询只查TM_APP_MAIN一张表
				if(attachCustUpdate!=null){
					//姓名，证件号码，移动电话,上一任务人......
					if(StringUtils.isNotEmpty(attachCustUpdate.getName())){
						tmAppMainUpdate.setName(attachCustUpdate.getName());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getIdNo())){
						tmAppMainUpdate.setIdNo(attachCustUpdate.getIdNo());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getIdType())){
						tmAppMainUpdate.setIdType(attachCustUpdate.getIdType());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getCellphone())){
						tmAppMainUpdate.setCellphone(attachCustUpdate.getCellphone());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getCorpName())){
						tmAppMainUpdate.setCorpName(attachCustUpdate.getCorpName());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getEmpPhone())){
						tmAppMainUpdate.setEmpPhone(attachCustUpdate.getEmpPhone());
					}
				}
			} else {
				if(AppType.A.name().equals(appType)){
					//是否拒绝附卡
					String[] ifAttachRefuses = getParas("ifAttachRefuse[]");
					if(ifAttachRefuses == null || ifAttachRefuses.length == 0){
						String ifAttachRefuse = getPara("ifAttachRefuse");
						if(StringUtils.isNotEmpty(ifAttachRefuse)){
							ifAttachRefuses = new String[]{ifAttachRefuse};
						}
					}
					Map<Integer,String> statuMap = new HashMap<Integer,String>();
					if(ifAttachRefuses != null && ifAttachRefuses.length > 0){
						for (String str : ifAttachRefuses) {
							statuMap.put(Integer.valueOf(str),str);
						}
					}
					List<TmAppCustInfo> attachCustList = getList(TmAppCustInfo.class, "attachCust");
					for (int i = 0; i<attachCustList.size(); i++) {
						TmAppCustInfo attachCust = attachCustList.get(i);
						attachCust.setAppNo(appNo);
						if(StringUtils.isBlank(getPara("validBit"+i))){
							attachCust.setCardNo(null);//自选卡号
						}else {
							attachCust.setCardNo(getPara("cardBin"+i)+attachCust.getCardNo()+getPara("validBit"+i));//自选卡号
						}
						TmAppCustInfo attachCustUpdate = applyInfoDtos.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S+(i+1));

						modifiedFieldsMap = commonService.compareValue(attachCust.convertToMap(),attachCustUpdate.convertToMap(),
								appNo,AppConstant.TM_APP_CUST_INFO_NAME+(i==0?"":i), "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CUST_INFO));

						attachCustUpdate = commonService.getModifiedClazz(attachCustUpdate, modifiedFieldsMap);
						attachCustUpdate.setRecordStatus(StringUtils.isBlank(statuMap.get(attachCust.getAttachNo()-4))?null:Indicator.N.name());//N表示拒绝
						tmAppCustInfoDao.updateTmAppCustInfo(attachCustUpdate);
					}
				}
				//第一联系人和其他联系人的处理
				Map<String , TmAppContact> tmAppContactInfoMap = new HashMap<String, TmAppContact>();
				List<TmAppContact> tmAppContactList = getList(TmAppContact.class, "tmAppContact");
				//验证并赋值联系信息
				tmAppContactInfoMap = AppCommonUtil.validAppContactEntityIsNull(tmAppContactInfoMap, tmAppContactList);
				TmAppContact tmAppContact1 = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
				TmAppContact tmAppContact2 = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");

				Map<String , TmAppContact> oldMap = applyInfoDtos.getTmAppContactMap();
				TmAppContact tmAppContactUpdate1 = oldMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
				TmAppContact tmAppContactUpdate2 = oldMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");
				if(tmAppContact1!=null&&(tmAppContact1.getContactName()!=null||
						tmAppContact1.getContactRelation()!=null||tmAppContact1.getContactMobile()!=null)){
					tmAppContact1.setAppNo(appNo);
					tmAppContact1.setContactType("1");
					if(tmAppContactUpdate1 !=null) {
						modifiedFieldsMap = commonService.compareValue(tmAppContact1.convertToMap(), tmAppContactUpdate1.convertToMap(),
								appNo, "亲属" + AppConstant.TM_APP_CONTACT_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CONTACT));

						tmAppContactUpdate1 = commonService.getModifiedClazz(tmAppContactUpdate1, modifiedFieldsMap);
					}else{
						Map<String, Serializable> map=new HashMap<String, Serializable>();
						modifiedFieldsMap = commonService.compareValue(tmAppContact1.convertToMap(),map,
								appNo, "其他"+AppConstant.TM_APP_CONTACT_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CONTACT));
						tmAppContactUpdate1 = new TmAppContact();
						tmAppContactUpdate1.setAppNo(appNo);
						tmAppContactUpdate1.setContactType("1");
						tmAppContactUpdate1 = commonService.getModifiedClazz(tmAppContactUpdate1, modifiedFieldsMap);
					}
					tmAppContactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+"1", tmAppContactUpdate1);
				}

				if(tmAppContact2!=null&&(tmAppContact2.getContactName()!=null||
						tmAppContact2.getContactRelation()!=null||tmAppContact2.getContactMobile()!=null)){
					if(tmAppContactUpdate2 != null){
						tmAppContact2.setAppNo(appNo);
						tmAppContact2.setContactType("2");
						modifiedFieldsMap = commonService.compareValue(tmAppContact2.convertToMap(),tmAppContactUpdate2.convertToMap(),
								appNo, "其他"+AppConstant.TM_APP_CONTACT_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CONTACT));

						tmAppContactUpdate2 = commonService.getModifiedClazz(tmAppContactUpdate2, modifiedFieldsMap);
					}else{
						Map<String, Serializable> map=new HashMap<String, Serializable>();
						modifiedFieldsMap = commonService.compareValue(tmAppContact2.convertToMap(),map,
								appNo, "其他"+AppConstant.TM_APP_CONTACT_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CONTACT));
						tmAppContactUpdate2 = new TmAppContact();
						tmAppContactUpdate2.setAppNo(appNo);
						tmAppContactUpdate2.setContactType("2");
						tmAppContactUpdate2 = commonService.getModifiedClazz(tmAppContactUpdate2, modifiedFieldsMap);
					}
					tmAppContactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+"2", tmAppContactUpdate2);
				}
				applyInputService.updateTmAppContactInfoMap(tmAppContactInfoMap, appNo);

				//TmAppAudit信息存储
				TmAppAudit tmAppAuditUpdate= applyInfoDtos.getTmAppAudit();
				modifiedFieldsMap = commonService.compareValue(appAuditPage.convertToMap(),tmAppAuditUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_AUDIT_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_AUDIT));
				tmAppAuditUpdate = commonService.getModifiedClazz(tmAppAuditUpdate, modifiedFieldsMap);
				if(tmAppAuditUpdate != null){
					if(tmAppAuditUpdate.getId() == null){
						tmAppAuditUpdate.setAppNo(appNo);
						tmAppAuditDao.saveTmAppAudit(tmAppAuditUpdate);
					}else {
						tmAppAuditDao.updateTmAppAudit(tmAppAuditUpdate);
					}
				}

				//附件证明信息
				TmAppPrimAnnexEvi annexEviPage = getBean(TmAppPrimAnnexEvi.class);
				annexEviPage.setAppNo(appNo);
				if(applyInfoDtos.getTmAppPrimAnnexEvi() != null && applyInfoDtos.getTmAppPrimAnnexEvi().getId() != null){
					annexEviPage.setId(applyInfoDtos.getTmAppPrimAnnexEvi().getId());
				}
				TmAppPrimAnnexEvi tmAppPrimAnnexEviUpdate = applyInfoDtos.getTmAppPrimAnnexEvi();
				if(tmAppPrimAnnexEviUpdate == null){
					tmAppPrimAnnexEviUpdate = new TmAppPrimAnnexEvi();
					annexEviPage.setAppNo(appNo);
				}
				modifiedFieldsMap = commonService.compareValue(annexEviPage.convertToMap(), tmAppPrimAnnexEviUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_PRIM_ANNEX_EVI_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_PRIM_ANNEX_EVI));

				tmAppPrimAnnexEviUpdate = commonService.getModifiedClazz(tmAppPrimAnnexEviUpdate, modifiedFieldsMap);
				if(tmAppPrimAnnexEviUpdate != null){
					if(tmAppPrimAnnexEviUpdate.getId() == null){
						tmAppPrimAnnexEviUpdate.setAppNo(appNo);
						tmAppPrimAnnexEviDao.save(tmAppPrimAnnexEviUpdate);
					}else {
						tmAppPrimAnnexEviDao.update(tmAppPrimAnnexEviUpdate);
					}
				}


				//主卡申请人信息
				TmAppCustInfo primCustPage = null;
				List<TmAppCustInfo> custList = getList(TmAppCustInfo.class, "tmAppCustInfo");
				if(CollectionUtils.sizeGtZero(custList)){
					for(int i=0;i<custList.size();i++){
						if(custList.get(i)!=null){
							primCustPage = custList.get(i);
							break;
						}
					}
				}
				if(primCustPage!=null){
					primCustPage.setAppNo(appNo);
					if(StringUtils.isBlank(getPara("validBit"))){
						primCustPage.setCardNo(null);//自选卡号
					}else {
						primCustPage.setCardNo(getPara("cardBin")+primCustPage.getCardNo()+getPara("validBit"));//自选卡号
					}
					modifiedFieldsMap = commonService.compareValue(primCustPage.convertToMap(),primCustdb.convertToMap(),
							appNo, AppConstant.TM_APP_CUST_INFO_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_CUST_INFO));

					primCustdb = commonService.getModifiedClazz(primCustdb, modifiedFieldsMap);
					tmAppCustInfoDao.updateTmAppCustInfo(primCustdb);
				}
				//后期优化，待办任务和申请进度查询只查TM_APP_MAIN一张表
				if(primCustdb!=null){
					//姓名，证件号码，移动电话,上一任务人......
					if(StringUtils.isNotEmpty(primCustdb.getName())){
						tmAppMainUpdate.setName(primCustdb.getName());
					}
					if(StringUtils.isNotEmpty(primCustdb.getIdNo())){
						tmAppMainUpdate.setIdNo(primCustdb.getIdNo());
					}
					if(StringUtils.isNotEmpty(primCustdb.getIdType())){
						tmAppMainUpdate.setIdType(primCustdb.getIdType());
					}
					if(StringUtils.isNotEmpty(primCustdb.getCellphone())){
						tmAppMainUpdate.setCellphone(primCustdb.getCellphone());
					}
					if(StringUtils.isNotEmpty(primCustdb.getCorpName())){
						tmAppMainUpdate.setCorpName(primCustdb.getCorpName());
					}
					if(StringUtils.isNotEmpty(primCustdb.getEmpPhone())){
						tmAppMainUpdate.setEmpPhone(primCustdb.getEmpPhone());
					}
				}
			}
			modifiedFieldsMap = commonService.compareValue(appMainPage.convertToMap(), tmAppMainUpdate.convertToMap(),
					appNo, AppConstant.TM_APP_MAIN_NAME, "申请录入修改", fieldsMap.get(AppConstant.TM_APP_MAIN));

			tmAppMainUpdate = commonService.getModifiedClazz(tmAppMainUpdate, modifiedFieldsMap);

			//卡片信息
			TmAppPrimCardInfo cardInfoPage = getBean(TmAppPrimCardInfo.class);
			//推广方式
			String[] spreaderMode = getParas("tmAppPrimCardInfo.spreaderMode[]");
			if(spreaderMode == null || spreaderMode.length == 0){
				spreaderMode = getParas("tmAppPrimCardInfo.spreaderMode");
			}
			if(spreaderMode != null && spreaderMode.length > 0){
				cardInfoPage.setSpreaderMode(commonService.arrayToString(spreaderMode,","));
			}
			TmAppPrimCardInfo tmAppPrimCardInfoUpdate = null;
			if (applyInfoDtos.getTmAppPrimCardInfo() != null && cardInfoPage != null) {
				cardInfoPage.setAppNo(appNo);
				cardInfoPage.setId(applyInfoDtos.getTmAppPrimCardInfo().getId());
				tmAppPrimCardInfoUpdate = applyInfoDtos.getTmAppPrimCardInfo();
				if(tmAppPrimCardInfoUpdate == null){
					tmAppPrimCardInfoUpdate = new TmAppPrimCardInfo();
					cardInfoPage.setAppNo(appNo);
				}
				modifiedFieldsMap = commonService.compareValue(cardInfoPage.convertToMap(), tmAppPrimCardInfoUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_PRIM_CARD_INFO_NAME,"申请录入修改", fieldsMap.get(AppConstant.TM_APP_PRIM_CARD_INFO));

				tmAppPrimCardInfoUpdate = commonService.getModifiedClazz(tmAppPrimCardInfoUpdate, modifiedFieldsMap);
				if(tmAppPrimCardInfoUpdate !=null) {
					if(tmAppPrimCardInfoUpdate.getId() == null){
						tmAppPrimCardInfoUpdate.setAppNo(appNo);
						tmAppPrimCardInfoDao.saveTmAppPrimCardInfo(tmAppPrimCardInfoUpdate);
					}else {
						tmAppPrimCardInfoDao.updateTmAppPrimCardInfo(tmAppPrimCardInfoUpdate);
					}
				}
			}

			//推广人注记
			TmAppMemo appMemoPage = getBean(TmAppMemo.class);
			TmAppMemo tmAppMemoUpdate = new TmAppMemo();
			Map<String , TmAppMemo> memoMap =applyInfoDtos.getTmAppMemoMapLast();
			if (memoMap ==null || memoMap.size()==0 || memoMap.get("MEMO_INPUT")==null) {
				tmAppMemoUpdate.setAppNo(appNo);
				tmAppMemoUpdate.setMemoInfo(appMemoPage.getMemoInfo());
				tmAppMemoUpdate.setRtfState(RtfState.A05.name());
				tmAppMemoUpdate.setMemoType(AppConstant.APP_MEMO);
				tmAppMemoUpdate.setTaskKey(EcmsAuthority.INPUT.name());
				tmAppMemoUpdate.setTaskDesc(EcmsAuthority.INPUT.lab);
				tmAppMemoDao.saveTmAppMemo(tmAppMemoUpdate);
			}else if(applyInfoDtos.getTmAppMemoMapLast() !=null){
				TmAppMemo tmAppMemo = memoMap.get("MEMO_INPUT");
				tmAppMemo.setMemoInfo(appMemoPage.getMemoInfo());
				tmAppMemoDao.update(tmAppMemo);
			}


		} catch (Exception e) {
			// TODO: handle exception
			logger.error("申请件保存数据异常,"+LogPrintUtils.printAppNoEndLog(appNo, tokenId,null),e);
			throw new ProcessException("申请件保存数据异常",e);
		}

		//只有提交时才会发起工作流（非保存）
		if(Indicator.N.name().equals(saveFlag)) {
			TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserNo(),
					RtfState.A10, "", "");
			tmAppHistory.setName(tmAppMainUpdate.getName());
			tmAppHistory.setIdNo(tmAppMainUpdate.getIdNo());
			tmAppHistory.setIdType(tmAppMainUpdate.getIdType());
			applyInputService.saveTmAppHistory(tmAppHistory);
			tmAppMainUpdate.setRtfState(rtfState);
			applyInputService.updateTmAppMain(tmAppMainUpdate);
			ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
			//节点公共数据 赋值
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMainUpdate);
			if(StringUtils.isNotEmpty(rtfState) && (rtfState.equals(RtfState.A10.name())||rtfState.equals(RtfState.A30.name())||rtfState.equals(RtfState.A40.name()))){ //提交操作
				activityStart(applyNodeCommonData ,appNo ,tmAppMainUpdate);  //启动工作流
			}
		}else{
			//历史记录信息
			TmAppHistory appHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserName(),
					RtfState.A05, "", "");
			appHistory.setName(tmAppMainUpdate.getName());
			appHistory.setIdNo(tmAppMainUpdate.getIdNo());
			appHistory.setIdType(tmAppMainUpdate.getIdType());
			applyInputService.saveTmAppHistory(appHistory);
			applyInputService.updateTmAppMain(tmAppMainUpdate);
		}
	}

	/**
	 * 发起工作流的方法
	 * 
	 */
	public void activityStart(ApplyNodeCommonData applyNodeCommonData ,String appNo ,TmAppMain tmAppMain) {
		
		Map<String, Serializable> vars = new HashMap<String, Serializable>();
		vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(appNo).singleResult();
		if(task!=null){
			activitiService.completeTask(task.getId(), vars,appNo);
		} else{
			String procdefKey = "";
			if(StringUtils.isNotBlank(tmAppMain.getProductCd())){
				String key = tmAppMain.getProductCd()+"-"+tmAppMain.getAppSource();//每个产品+申请渠道
				String defKey = tmAppMain.getProductCd()+"-def";//每个产品默认的流程图
				if(StringUtils.isEmpty(tmAppMain.getAppSource())) {
					key = tmAppMain.getProductCd()+"-def";
				}
				TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
				if(proProcess==null || StringUtils.isEmpty(proProcess.getProcdefKey())) {
					proProcess = cacheContext.getProductProcessByProduct(defKey);
					if(proProcess!=null && StringUtils.isNotEmpty(proProcess.getProcdefKey())) {
						procdefKey = proProcess.getProcdefKey();
					}
				}else {
					procdefKey = proProcess.getProcdefKey();
				}
				if(proProcess==null || StringUtils.isEmpty(proProcess.getProcdefKey())) {
					procdefKey = activitiService.getDefProcess();//系统设置总的默认流程图
				}
			}else {
				//设置默认流程
				procdefKey = activitiService.getDefProcess();
			}
			if(StringUtils.isNotEmpty(procdefKey)){
				activitiService.startNewProcess(procdefKey, appNo, vars);
			}else {
				logger.info("没有获取到默认的流程定义，请检查流程！"+LogPrintUtils.printAppNoLog(appNo, null,null));
				throw new ProcessException("系统未配置有效的流程图，请联系管理员检查系统流程配置及产品配置！");
			}
		}
	}
	
	/**
	 * 	申请录入信息更新(保存按钮)
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateApplyInputInfo")
	public Json updateApplyInputInfo(){
		long start = System.currentTimeMillis();
		String retrialFlag=getPara("retrialFlag");//重审标志
		String oldAppNo="";
		if (StringUtils.concat(retrialFlag,Indicator.Y.name())) {
			 oldAppNo = getPara("oldAppNo");//重审件原件appNo
		}
		TmAppMain tmAppMain = getBean(TmAppMain.class);
		logger.info("保存客户申请数据开始........"+LogPrintUtils.printAppNoLog((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), start,null));
		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		Json json = Json.newSuccess();
		try {
			setApplyInfoDto(applyInfoDto, retrialFlag, oldAppNo, Indicator.Y.name(), RtfState.A05.name(), json);

			// 申请贷款金额和商户额度的判断
//			if (applyInfoDto.getTmAppAudit() != null && "Y".equals(applyInfoDto.getTmAppAudit().getIsInstalment())) {
//				TmAppInstalLoan appInstalLoan = applyInfoDto.getTmAppInstalLoan();
//				out: if (appInstalLoan != null) {
//					TmAppInstalMerchant appInstalMerchant = new TmAppInstalMerchant();
//					if (appInstalLoan.getMccNo() == null || appInstalLoan.getCashAmt() == null) {
//						break out;
//					}
//					appInstalMerchant.setMerId(appInstalLoan.getMccNo());
//					appInstalMerchant = appInstalMerchantDao.queryForOne(appInstalMerchant);
//					// 授信总额
//					BigDecimal merAmt = new BigDecimal(0);
//					// 分期借款金额
//					BigDecimal instalAmt = appInstalLoan.getCashAmt() == null ? BigDecimal.ZERO
//							: appInstalLoan.getCashAmt();
//					if (appInstalMerchant != null) {
//						merAmt = appInstalMerchant.getMerLmt() == null ? BigDecimal.ZERO
//								: appInstalMerchant.getMerLmt();
//						if (merAmt.compareTo(instalAmt) < 0) {
//							json.setMsg("保存成功,警告:申请金额超出授信总额:" + merAmt.subtract(instalAmt));
//						}
//					}
//				}
//			}
		} catch (Exception e) {
				logger.error("保存客户申请数据失败!"+LogPrintUtils.printAppNoLog((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), start,null), e);
				json.setFail(e.getMessage());
				bizAuditHistoryUtils.saveAuditHistory((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), "退回件修改-保存客户申请数据失败");
		}
		logger.info("保存客户申请数据成功["+json.getMsg()+"]........"+LogPrintUtils.printAppNoEndLog((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), start,null));
		bizAuditHistoryUtils.saveAuditHistory((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), "退回件修改-保存客户申请数据");
		return json;
	}
	
/**
 * 	申请录入信息更新到下一个节点(提交按钮)
 * @return
 */
	 
	@ResponseBody
	@RequestMapping("/updateAndNextnode")
	public Json updateAndNextnode(){
		long start = System.currentTimeMillis();
		String retrialFlag=getPara("retrialFlag");//重审标志
		String oldAppNo="";
		if (StringUtils.concat(retrialFlag,Indicator.Y.name())) {
			oldAppNo = getPara("oldAppNo");//重审件原件appNo
		}
		TmAppMain  tmAppMain = getBean(TmAppMain.class);
		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		Json json = Json.newSuccess();
//		
//		// 分期贷款表 得到分期信息 判断是否超额 超额后不允许提交
//		TmAppMain appMain	= getBean(TmAppMain.class);
//		TmAppInstalLoan appInstalLoan= getBean(TmAppInstalLoan.class);
//		//分期商户表
//		TmAppInstalMerchant appInstalMerchant = null;
//		//本次申请额度
//		BigDecimal cashAmt = new BigDecimal(0);
//		//审批中金额
//		BigDecimal inAuditLmt = new BigDecimal(0);
//		TmAppAudit audit = getBean(TmAppAudit.class);
//		if("Y".equals(audit.getIsInstalment()) && appInstalLoan!=null){
//			String returnFlag=getPara("returnFlag");//退回标志
//			logger.info("退回标志returnFlag：{}，重审件标志retrialFlag：{}",returnFlag,retrialFlag);
//			if(!(StringUtils.isNotBlank(returnFlag) && StringUtils.isBlank(retrialFlag))){
//				//商户授信额度
//				BigDecimal merLmt = new BigDecimal(0);
//				
//				//已经用信金额
//				BigDecimal inAuditLmtNoton = new BigDecimal(0);
//				appInstalMerchant = appInstalMerchantDao.queryForByMercId(appInstalLoan.getMccNo());
//				if(appInstalMerchant!=null){
//					if(appInstalMerchant.getMerLmt()!=null){
//						merLmt = appInstalMerchant.getMerLmt();
//					}
//					if(appInstalLoan.getCashAmt()!=null){
//						cashAmt = appInstalLoan.getCashAmt();
//					}
//					
//					if(appInstalMerchant.getInAuditLmt()!=null){
//						inAuditLmt = appInstalMerchant.getInAuditLmt();
//					}
//					//每日审批通过累计金额
//					BigDecimal finishAuditLmt = new BigDecimal(0);
//					if(appInstalMerchant.getFinishAuditLmt()!=null){
//						finishAuditLmt = appInstalMerchant.getFinishAuditLmt();
//					}
//					//已入账未出账单金额
//					BigDecimal postingLmt = new BigDecimal(0);
//					if(appInstalMerchant.getPostingLmt()!=null){
//						postingLmt = appInstalMerchant.getPostingLmt();
//					}
//					//已经用信金额  (审批中金额+每日审批通过金额+已入账未出账单金额)
//					inAuditLmtNoton = inAuditLmt.add(finishAuditLmt).add(postingLmt);
//					//(授信额度-已经用信的额度)-审批中额度>0  把本次申请额度加入到审批中金额    汇总
//					logger.info("商户号：{}，已用信额度:{}=审批中金额:{}+每日审批通过金额:{}+已入账未出账单金额(当日之前):{}",appInstalMerchant.getMerId(),inAuditLmtNoton,inAuditLmt,finishAuditLmt,postingLmt);
//					logger.info("申请编号：{}，本次申请金额:{},商户授信额度:{}",appMain.getAppNo(),cashAmt,merLmt);
//					
//					if((merLmt.subtract(inAuditLmtNoton)).compareTo(cashAmt)>=0){
//					
//					}else{
//						logger.info("该商户可用额度不足");
//						json.setFail("商户["+appInstalMerchant.getMerId()+"-"+appInstalMerchant.getMerName()+"]可用额度不足!");
//						return json;
//					}
//				}
//			}
//		}		

		try {
			setApplyInfoDto(applyInfoDto ,retrialFlag ,oldAppNo ,Indicator.N.name(),RtfState.A10.name(),json);
			logger.info("保存客户申请数据成功["+json.getMsg()+"]........"+LogPrintUtils.printAppNoEndLog((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), start,null));
		} catch (Exception e) {
			logger.error("申请件["+(StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo())+"]"+"录入申请数据提交异常",e);
			bizAuditHistoryUtils.saveAuditHistory(StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo(), "退回件修改-保存客户申请数据失败");//保存审计历史
			json.setFail(e.getMessage());
			return json;
		}

//		if("Y".equals(audit.getIsInstalment()) && appInstalMerchant != null){
//			appInstalMerchant.setInAuditLmt(inAuditLmt.add(cashAmt));
//			logger.info("审批中金额:{}",inAuditLmt.add(cashAmt));
//			appInstalMerchantDao.updateNotNullable(appInstalMerchant);
//		}
		bizAuditHistoryUtils.saveAuditHistory((StringUtils.isNotBlank(oldAppNo)?oldAppNo:tmAppMain.getAppNo()), "退回件修改-提交客户申请数据");//保存审计历史
		return json;
	}
	/**
	 * 预录入页面信息删除(删除按钮)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyInputDelete")
	public Json deleteApplyQuery(String appNo){
		Long tokenId = System.currentTimeMillis();
		logger.info("删除申请件件========>"+ LogPrintUtils.printAppNoLog(appNo, tokenId,null));
		Json j = Json.newSuccess();
		try{
			if(StringUtils.isEmpty(appNo)){
				j.setMsg("未查询到申请件["+appNo+"]信息,无法执行后续操作");
			}
//			abnormalProcessAppService.delete(appNo);
			TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
			main.setRtfState(RtfState.A20.name());
			applyInputService.updateTmAppMain(main);
			TmAppHistory appHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserNo(),
					RtfState.A20, "", "[系统备注]申请件进度结果页面发起删除申请件操作");
			appHistory.setName(main.getName());
			appHistory.setIdNo(main.getIdNo());
			appHistory.setIdType(main.getIdType());
			applyInputService.saveTmAppHistory(appHistory);
			TmAppAudit audit = applyQueryService.getTmAppAuditByAppNo(appNo);
/*			if("Y".equals(audit.getIsInstalment())){
				TmAppInstalLoan appInstalLoan = new TmAppInstalLoan();
				appInstalLoan.setAppNo(appNo);
				appInstalLoan = appInstalLoanDao.queryForOne(appInstalLoan);
				if(appInstalLoan!=null){
					appInstalLoan.setStatus(RtfState.A20.name());
					appInstalLoanDao.updateByAppNo(appInstalLoan);
					logger.info("录入无效删除分期贷款信息,appNo:{}",appNo);
				}
			}*/
		}catch(Exception e){
			logger.error("删除申请件["+appNo+"]失败", e);
			j.setFail("删除申请件["+appNo+"]失败"+e.getMessage());
		}
		logger.info("删除申请件件========>"+ LogPrintUtils.printAppNoEndLog(appNo, tokenId,null));
		bizAuditHistoryUtils.saveAuditHistory(appNo, "删除申请件");//保存审计历史
		return j;
	}

/**
 * 	申请录入修改信息删除(删除按钮)
 * @return
 */
	 
	@ResponseBody
	@RequestMapping("/deleteApplyInputInfo")
	public Json deleteApplyInputInfo(){
		String retrialFlag=getPara("retrialFlag");//重审标志
		String oldAppNo=getPara("oldAppNo");//重审件原A
		
		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		Json json = Json.newSuccess();

		try {
			setApplyInfoDto(applyInfoDto ,retrialFlag ,oldAppNo,Indicator.N.name(),RtfState.A20.name(),json);
			logger.info("申请录入修改信息删除成功！");
		} catch (Exception e) {
			logger.error("=========》》》申请录入修改信息删除异常！《《《《=========",e);
			json.setFail(e.getMessage());
		}

		return json;
	}
	
	/**
	 * 约定还款账户有效性验证
	 * @param acctName	约定还款账户姓名
	 * @param idType	证件类型
	 * @param idNo	证件号码
	 * @param branchNo	开户行号
	 * @param acctNo	账户号码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkDdBankAcct")
	public Json checkDdBankAcct(){
		String ddBankAcctName = getPara("ddBankAcctName");
		String idType = getPara("idType");// 证件类型
		String idNo = getPara("idNo");// 证件号码
		String branchNo = getPara("branchNo");// 开户行号
		String ddBankAcctNo = getPara("ddBankAcctNo");
		String acctNoType = getPara("acctNoType");
		Json json = new Json();
		try {
			String result = commonService.checkAccountNo(ddBankAcctName, idType, idNo, branchNo, ddBankAcctNo, acctNoType);
			if (StringUtils.isNotBlank(result)) {//验证失败
				json.setMsg(result);
				json.setS(false);
			}else {
				json.setS(true);
				json.setMsg("["+ddBankAcctNo+"]验证成功");
			} 
		} catch (Exception e) {
			json.setS(false);
			json.setMsg("账户验证出现异常，请稍后再试");
			logger.error("账户验证出现异常，异常信息：{}", e);
		}
		
		return json;
	}
//	/**
//	 * 查询对应卡产品是否能做分期业务
//	 * @param productCd
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/checkInstal")
//	public Json checkInstal(String productCd){
//		Json json = Json.newSuccess();
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(StringUtils.isNotBlank(productCd)){
//			//查询卡产品是否支持分期
//			TmAppInstalProgram appInstalProgram = new TmAppInstalProgram();
//			appInstalProgram.setProductCd(productCd);
//			List<TmAppInstalProgram> list = appInstalProgramDao.queryForList(appInstalProgram);
//			if(list!=null && list.size()>0){
//				map.put("show", "true");
//			}else{
//				map.put("show", "false");
//			}
//		}
//		json.setObj(map);
//		return json;
//	}
	/**
	 * 根据约定还款开户行号获取银行名称
	 * @param ddBankBranch
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDdBankName")
	public Json getDdBankName(String ddBankBranch){
		Json json = Json.newSuccess();
		if(StringUtils.isNotBlank(ddBankBranch)){
			TmAclDict tmAclDict = cacheContext.getAclDictByTypeAndCode("DdBankBranch", ddBankBranch);
			if(tmAclDict != null && StringUtils.isNotBlank(tmAclDict.getCodeName())){
				json.setMsg(tmAclDict.getCodeName());
				return json;
			}
		}
		json.setMsg("");
		return json;
	}
	
//	/**
//	 * selectLink 获取活动号
//	 * @param param
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/getActicity")
//	public Json getActicityNoByProduct(Map<String, Object> param){
//		Json json = Json.newSuccess();
//		String product = null;
//		product = getPara("_PARENT_VALUE");
//		
//		TmAppInstalProgram appInstalProgram = new TmAppInstalProgram();
//		appInstalProgram.setProductCd(product);
//		appInstalProgram.setProgramStatus("A");
//		List<TmAppInstalProgram> list = appInstalProgramDao.queryForList(appInstalProgram);
//		
//		List<Map<String, Object>> objList = new ArrayList<Map<String,Object>>();
//		Map<String, Object> business =null;
//		for(TmAppInstalProgram obj:list){
//			business= new HashMap<String, Object>();
//			business.put("id", obj.getProgramId());
//			business.put("show",obj.getProgramDesc());
//			objList.add(business);
//		}
////		for(int i=0;i<10;i++){
////			business= new HashMap<String, Object>();
////			business.put("id", "huodong"+i);
////			business.put("show", "huodongshow"+i);
////			objList.add(business);
////		}
////		objList.add(business);
//		
//		json.setObj(objList);
//		
//		return json;
//		
//	} 
	
//	/**
//	 * selectLink 获取商户号
//	 * @param param
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/businessNo")
//	public Json getbusinessNoByActivityNo(Map<String, Object> param){
//		Json json = Json.newSuccess();
//		String programId = null;
//		programId = getPara("_PARENT_VALUE");
//		
//		TmAppInstalProgramMerchant appInstalProgramMerchant = new TmAppInstalProgramMerchant();
//		appInstalProgramMerchant.setProgramId(programId);;
//		List<TmAppInstalProgramMerchant> list = appInstalProgramMerchantDao.queryForList(appInstalProgramMerchant);
//		
//		List<Map<String, Object>> objList = new ArrayList<Map<String,Object>>();
//		Map<String, Object> business = null;
//		for(TmAppInstalProgramMerchant obj:list){
//			business = new HashMap<String, Object>();
//			business.put("id", obj.getMerId());
//			business.put("show",obj.getMerName());
//			objList.add(business);
//		}
////		for(int i=0;i<10;i++){
////			business= new HashMap<String, Object>();
////			business.put("id", "shanghu"+i);
////			business.put("show", "shanghushow"+i);
////			objList.add(business);
////		}
////		objList.add(business);
//		
//		json.setObj(objList);
//		
//		return json;
//		
//	}
//	
//	/**
//	 * 分期页面初始化操作
//	 * @param appNo
//	 * @param productCd
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/getAll")
//	public Json getALLInfoInstal(String appNo,String productCd){
////		tm_app_instalment_credit
//		Json json = Json.newSuccess();
//		
//		TmAppInstalProgram appInstalProgram = new TmAppInstalProgram();
//		appInstalProgram.setProductCd(productCd);
//		List<TmAppInstalProgram> list = appInstalProgramDao.queryForList(appInstalProgram);
//		
//		List<Map<String, Object>> objList = new ArrayList<Map<String,Object>>();
//		Map<String, Object> business =null;
//		for(TmAppInstalProgram obj:list){
//			business= new HashMap<String, Object>();
//			business.put("id", obj.getProgramId());
//			business.put("show",obj.getProgramDesc());
//			objList.add(business);
//		}
////		for(int i=0;i<10;i++){
////			business= new HashMap<String, Object>();
////			business.put("id", "huodong"+i);
////			business.put("show", "huodongshow"+i);
////			objList.add(business);
////		}
////		objList.add(business);
//
//		json.setObj(objList);
//		
//		
//		return json;
//	}
//	
//	/**
//	 * selectLink 获取分期期数
//	 * @param param
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/getTerms")
//	public Json getTermsBy(Map<String, Object> param){
//		Json json = Json.newSuccess();
//		String programId = null;
//		programId = getPara("_PARENT_VALUE");
//		
//		TmAppInstalProgramTerms appInstalProgramTerms = new TmAppInstalProgramTerms();
//		appInstalProgramTerms.setProgramId(programId);;
//		List<TmAppInstalProgramTerms> list = appInstalProgramTermsDao.queryForList(appInstalProgramTerms);
//		
//		List<Map<String, Object>> objList = new ArrayList<Map<String,Object>>();
//		Map<String, Object> business = null ;
//		for(TmAppInstalProgramTerms obj:list){
//			business= new HashMap<String, Object>();
//			business.put("id", obj.getProgramId());
//			business.put("show",obj.getTerm());
//			objList.add(business);
//		}
////		for(int i=0;i<10;i++){
////			business= new HashMap<String, Object>();
////			business.put("id", i);
////			business.put("show", i);
////			objList.add(business);
////		}
////		objList.add(business);
//		json.setObj(objList);
//		
//		return json;
//	}
//	
//	@ResponseBody
//	@RequestMapping("/judgeTimeAndStatus")
//	public Json judgeActity(String ActivityNo,String productCd){
//		Json json = Json.newSuccess();
//		if(ActivityNo==null||productCd==null){
//			json.setFail("活动号/产品请填写!");
//			json.setCode("0");
//			return json;
//		}
////		String branch = OrganizationContextHolder.getBranchCode();
//		
//		TmAppInstalProgram appInstalProgram = new TmAppInstalProgram();
//		appInstalProgram.setProgramId(ActivityNo);
//		appInstalProgram.setProductCd(productCd);
////		appInstalProgram.setOwningBranch(branch);
//		appInstalProgram = appInstalProgramDao.queryForOne(appInstalProgram);
//		if(appInstalProgram!=null){
//			String status = appInstalProgram.getProgramStatus();
//			if(!status.equals("A")){
//				json.setFail("活动未启用");
//				json.setCode("0");
//				return json;
//			}
//			Date statrDate = appInstalProgram.getProgramStartDate();
//			Date endDate= appInstalProgram.getProgramEndDate();
//			Date nowDate = new Date();
//			if(!(nowDate.after(statrDate)&&nowDate.before(endDate))){
//				json.setFail("活动时间不在允许范围内");
//				json.setCode("0");
//				return json;
//			}
//		}else{
//			json.setFail("无法关联到对应信息!请确认活动,机构,产品是否正确");
//			json.setCode("0");
//			return json;
//		}
//		return json;
//	}
//	/**
//	 * 
//	 * @param cashAmt 申请金额
//	 * @param loanInitTerm 分期期数
//	 * @param productCd 产品参数
//	 * @param activityNo 活动号
//	 * @return
//	 */
//	public Json judge(BigDecimal cashAmt,Integer loanInitTerm,String productCd,String activityNo){
//		Json json = Json.newSuccess();
//		if(cashAmt==null || loanInitTerm==null || productCd==null || activityNo==null){
//			json.setFail("分期金额/分期期数/产品参数/活动号/请填写完整");
//			json.setCode("0");
//			return json;
//		}
//		TmAppInstalProgram appInstalProgram = new TmAppInstalProgram();
////		String branch = OrganizationContextHolder.getBranchCode();
//		appInstalProgram.setProductCd(productCd);
//		appInstalProgram.setProgramId(activityNo);
////		appInstalProgram.setOwningBranch(branch);
//		appInstalProgram = appInstalProgramDao.queryForOne(appInstalProgram);
//		if(appInstalProgram!=null){
//			BigDecimal maxAmtActivity = appInstalProgram.getProgramMaxAmount();
//			BigDecimal minAmtActivity = appInstalProgram.getProgramMinAmount();
//			if(!(cashAmt.compareTo(maxAmtActivity)<=0 && cashAmt.compareTo(minAmtActivity)>=0)){
//				json.setCode("0");
//				json.setFail("申请金额不在活动允许范围内!");
//				return json;
//			}
//		}else{
//			json.setFail("查询不到对应的活动信息");
//			json.setCode("0");
//			return json;
//		}
//		TmAppInstalProgramTerms appInstalProgramTerms = new TmAppInstalProgramTerms();
//		appInstalProgramTerms.setTerm(loanInitTerm);
//		appInstalProgramTerms.setProgramId(activityNo);
////		appInstalProgramTerms.setLoanFeeTetchMethod(loanFeeMethod);
//		appInstalProgramTerms = appInstalProgramTermsDao.queryForOne(appInstalProgramTerms);	
//		if(appInstalProgramTerms!=null){
//			BigDecimal maxTermAmt = appInstalProgramTerms.getTermMaxAmt();
//			BigDecimal minTermAmt = appInstalProgramTerms.getTermMinAmt();
//			if(!(cashAmt.compareTo(maxTermAmt)<=0 && cashAmt.compareTo(minTermAmt)>=0)){
//				json.setFail("申请金额不在分期期数允许的范围内!");
//				json.setCode("0");
//				return json;
//			}
//		}else{
//			json.setFail("查询不到对应的分期信息");
//			json.setCode("0");
//			return json;
//		}
//		return json;
//	}
	
	@ResponseBody
	@RequestMapping("/getBranches")
	public Json getBranches(String query, @RequestParam(required=false,defaultValue="10") Integer maxSize, @RequestParam(required=false) String parentBranch
			, @RequestParam(required=false, defaultValue="false") Boolean isAll){
		Json json = Json.newSuccess();
		String userNo = OrganizationContextHolder.getUserNo();
		logger.info("获取受理网点....，userId="+userNo);
		LinkedHashMap<Object, Object> branches = null;
		if(isAll) {
			branches = cacheContext.getBranchMapByParam(AppConstant.allBranch);
		}else {
			 branches = cacheContext.getSubBranchByBranchOrUser(parentBranch, userNo);
		}
		LinkedHashMap<Object, Object> filteredBranches = new LinkedHashMap<Object, Object>();
		for(Entry entry :branches.entrySet()) {
			String label = (String)entry.getKey();
			String value = (String)entry.getValue();
			if(StringUtils.regexCheck(label, query) 
					|| StringUtils.regexCheck(value, query)) {
				filteredBranches.put(label,value);
				if(filteredBranches.size() >= maxSize) {
					break;
				}
			}
		}
		json.setObj(filteredBranches);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getBrancheByCode")
	public Json getBrancheByCode(String branchCode){
		Json json = Json.newSuccess();
		if(StringUtils.isNotBlank(branchCode)) {
			TmAclBranch branch = cacheContext.getTmAclBranchByCode(branchCode);
			if(branch != null) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("key", branch.getBranchCode());
				map.put("value", branch.getBranchName());
				json.setObj(map);
			}else {
				json.setFail("查询不到对应的营业网点");
				json.setCode("0");
			}
		}else {
			json.setFail("查询不到对应的营业网点");
			json.setCode("0");
		}
		return json;
	}
//	
//	@ResponseBody
//	@RequestMapping("/getInstalParam")
//	public Json getInstalParam(String type,String name,String parentValue,Boolean isCode){
///*		Json json = Json.newSuccess();
//		if(StringUtils.isEmpty(type)){
//			logger.error("异步加载下拉框[联动数据类型]为空");
//			json.setS(false);
//			return json;
//		}else {
//			try {
//				
//				StringBuffer optionBuffer = new StringBuffer();
//				StringBuffer searchBuffer = new StringBuffer();
//				
//				LinkedHashMap<Object,Object> map = null;
//				if("A".equals(type) && StringUtils.isNotBlank(parentValue)){
//					//获取分期活动下拉框
//					map = cacheContext.getActivityName(parentValue);
//				}else if("M".equals(type) && StringUtils.isNotBlank(parentValue)){
//					//商户号
//					map = cacheContext.getMersByProgId(parentValue);
//					
//				}else if("T".equals(type) && StringUtils.isNotBlank(parentValue)){
//					//期数
//					map = cacheContext.getTermsByTerm(parentValue);
//				}
//				else{
//					map = new LinkedHashMap<Object, Object>();
//				}
//				//设置空选项
//				optionBuffer.append("<option value='");
//				optionBuffer.append("'>");
//				optionBuffer.append("</option>");
//				//搜索框选项
//				searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
//				searchBuffer.append(name);
//				searchBuffer.append("' value='");
//				searchBuffer.append("'><span>");
//				searchBuffer.append("</span></label></li>");
//				if(isCode){//显示code
//					if (map != null && map.size() > 0){
//						for (Entry<Object, Object> entry : map.entrySet()) {
//							optionBuffer.append("<option value='");
//							optionBuffer.append(entry.getKey());
//							optionBuffer.append("'>");
//							optionBuffer.append(entry.getKey());
//							optionBuffer.append("-");
//							optionBuffer.append(entry.getValue()); 
//							optionBuffer.append("</option>");
//							//搜索框选项
//							searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
//							searchBuffer.append(name);
//							searchBuffer.append("' value='");
//							searchBuffer.append(entry.getKey());
//							searchBuffer.append("'><span>");
//							searchBuffer.append(entry.getKey());
//							searchBuffer.append("-");
//							searchBuffer.append(entry.getValue());
//							searchBuffer.append("</span></label></li>");
//							
//						}
//					}
//				}else {//不显示code
//					if (map != null && map.size() > 0){
//						for (Entry<Object, Object> entry : map.entrySet()) {
//							optionBuffer.append("<option value='");
//							optionBuffer.append(entry.getValue());
//							optionBuffer.append("'>");
//							optionBuffer.append(entry.getValue()); 
//							optionBuffer.append("</option>");
//							//搜索框选项
//							searchBuffer.append("<li class='' style='false'><label class=''><input type='radio data-name='selectItem");
//							searchBuffer.append(name);
//							searchBuffer.append("' value='");
//							searchBuffer.append(entry.getValue());
//							searchBuffer.append("'><span>");
//							searchBuffer.append(entry.getValue());
//							searchBuffer.append("</span></label></li>");
//							
//						}
//					}
//				}
//				json.setCode(searchBuffer.toString());
//				json.setMsg(optionBuffer.toString());
//			} catch (Exception e) {
//				json.setS(false);
//				logger.error("异步加载[{}]失败",type);
//				throw new ProcessException("异步加载["+type+"]失败",e);
//			}
//		}
//		
//		return json;*/
////		tm_app_instalment_credit
//		String productCd =  getPara("productCd");
//		String activityNo =  getPara("activityNo");
//		String changeType = getPara("type");
//		Json json = Json.newSuccess();
//		logger.info("获取分期参数，change类型-{}，产品编号-{}，分期活动号-{}",changeType,productCd,activityNo);
//		LinkedHashMap<Object,Object> activityNoMap = null;
//		LinkedHashMap<Object,Object> mccNoMap = null;
//		LinkedHashMap<Object,Object> termsMap = null;
//		Map<String, LinkedHashMap<Object, Object>> obj = new LinkedHashMap<String, LinkedHashMap<Object,Object>>();
//		if("P".equals(changeType) && StringUtils.isNotBlank(productCd)){
//			//产品代码change
//			activityNoMap = cacheContext.getActivityName(productCd);
//			mccNoMap = new LinkedHashMap<Object, Object>();
//			termsMap = new LinkedHashMap<Object, Object>();
//			obj.put("activityNoMap", activityNoMap);
//			obj.put("mccNoMap", mccNoMap);
//			obj.put("termsMap", termsMap);
//		}else if("A".equals(changeType) && StringUtils.isNotBlank(productCd) && StringUtils.isNotBlank(activityNo)){
//			//分期活动change,初始商户号和期数，验证分期活动有效性
//			
//			TmAppInstalProgram entity = new TmAppInstalProgram();
//			entity.setProgramId(activityNo);
//			entity.setProductCd(productCd);
//			TmAppInstalProgram appInstalProgram = appInstalProgramDao.queryForOne(entity);
//			if(appInstalProgram!=null){
//				String status = appInstalProgram.getProgramStatus();
//				if(!status.equals("A")){
//					json.setMsg("分期活动["+activityNo+"]未启用，请检查参数配置或刷新参数");
//					json.setCode("0");
//					logger.warn("分期活动["+activityNo+"]未启用");
//				}
//				Date statrDate = appInstalProgram.getProgramStartDate();
//				Date endDate= appInstalProgram.getProgramEndDate();
//				Date nowDate = new Date();
//				if(!(nowDate.after(statrDate)&&nowDate.before(endDate))){
//					json.setMsg("分期活动["+activityNo+"]活动时间不在允许范围内，请检查参数配置或刷新参数");
//					json.setCode("0");
//					logger.warn("分期活动["+activityNo+"]活动时间不在允许范围内");
//				}
//			}else{
//				json.setMsg("分期活动["+activityNo+"]无法关联到对应信息!请确认活动,机构,产品是否正确");
//				json.setCode("0");
//				logger.warn("分期活动["+activityNo+"]无法关联到对应信息");
//			}
//			if("0".equals(json.getCode())){
//				//分期活动验证未通过，不再查询商户号，期数
//				mccNoMap = new LinkedHashMap<Object, Object>();
//				termsMap = new LinkedHashMap<Object, Object>();
//			}else{
//				mccNoMap = cacheContext.getMersByProgId(activityNo);
//				termsMap = cacheContext.getTermsByTerm(activityNo);
//			}
//			
//			obj.put("mccNoMap", mccNoMap);
//			obj.put("termsMap", termsMap);
//		}else{
//			mccNoMap = new LinkedHashMap<Object, Object>();
//			termsMap = new LinkedHashMap<Object, Object>();
//			obj.put("mccNoMap", mccNoMap);
//			obj.put("termsMap", termsMap);
//		}
//		
//		json.setObj(obj);
//		
//		return json;
//	}
}