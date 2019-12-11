package com.jjb.ams.app.controller.apply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.unicorn.web.controller.BaseController;


/**
 * 虚拟卡申请录入操作
 * @author hp
 *
 */
@Controller
@RequestMapping("/ams_creditInput")
public class CreditInputController extends BaseController{
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private ApplyInputService applyInfoService;
//	@Autowired
//	private ApplyQueryService applyQueryService;
//	@Autowired
//	private ActivitiService activitiService;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private ApplyCardNoService applyCardNoService;
//	@Autowired
//	private TaskService taskService;
//	@Autowired
//	private TmMirCardService tmMirCardService;
//	@Autowired
//	private CacheContext cacheContext;
//	@Autowired
//	private ApplyHistoryService applyHistoryService;
//	@Autowired
//	private TmLuckyCardService tmLuckyCardService;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private ApplySpreaderInfoService applySpreaderInfoService;
//	@Autowired
//	private TmAclDictDao tmAclDictDao;
//	@Autowired
//	private TmAppHistoryDao tmAppHistoryDao;
//	@Autowired
//	private ApplyInputService applyInputService;
//	@Autowired
//	private TmAppInstalLoanDao appInstalLoanDao;
//	@Autowired
//	private TmAppInstalProgramDao appInstalProgramDao;
//	@Autowired
//	private TmAppInstalProMerDao appInstalProgramMerchantDao;
//	@Autowired
//	private TmAppInstalProTermsDao appInstalProgramTermsDao;
//	@Autowired
//	private TmAppInstalMerchantDao appInstalMerchantDao;
//	@Autowired
//	private AmsAppUtils applyInfoUtils;
////	@Autowired
////	private TmAppMainDao tmAppMainDao;
//
//	/**
//	 * 根据卡产品productCd获取自选卡号校验位或卡bin
//	 * 申请件编号appNo
//	 * 卡号cardNo
//	 * 附卡编号attachNo
//	 * 获取tmProduct对象
//	 */
//	@ResponseBody
//	@RequestMapping("/getTmProduct")
//	public Json getTmProduct(String productCd,String appNo,String cardNo,String attachNo,String bscSuppInd){
//		Json json = Json.newSuccess();
//		if(StringUtils.isNotEmpty(productCd)){
//			TmProduct tmProduct = null;
//			try {
//				tmProduct = cacheContext.getProduct(productCd);
//				if(tmProduct != null && tmProduct.getApprovalMaximum() == null){
//					tmProduct.setApprovalMaximum(new BigDecimal(999999999999999.00));
//				}else if(tmProduct==null){
//					tmProduct = new TmProduct();
//					json.setS(false);
//					json.setFail("获取卡产品"+productCd+"不存在！");
//				}
//				json.setObj(tmProduct);
//			} catch (Exception e) {
//				json.setS(false);
//				json.setFail("获取卡产品"+productCd+"信息失败！");
//				logger.error("获取卡产品"+productCd+"信息失败！",e);
//			}
//			if(StringUtils.isNotEmpty(cardNo)){//N位自选卡号
//				try {
//					Integer max = Integer.valueOf(tmProduct.getCardNoRangeCeil());
//					Integer min = Integer.valueOf(tmProduct.getCardNoRangeFlr());
//					Integer cardNoInteger = Integer.valueOf(cardNo);
//					if(cardNoInteger >= min && cardNoInteger <= max){//判断自选卡号区间是否正确
//						cardNo = tmProduct.getBin()+cardNo;//拼成N位自选卡号		
//						/**
//						 * 返回原卡号/原卡号+校验位的cardNo
//						 * 原卡号:表示该自选卡号已使用
//						 * 原卡号+校验位:表示该自选卡号未使用
//						 */
//						Integer attno = StringUtils.stringToInteger(attachNo);
//						String cardNoValidBit = applyCardNoService.validateByCardNo(cardNo, appNo, attno, bscSuppInd);
//						int cardNoLen = cardNo.length();
//						if(StringUtils.isNotEmpty(cardNoValidBit)){
//							if(cardNoValidBit.length() == cardNoLen){//卡号长度没变表示该自选卡号已使用
//								json.setMsg("false");
//								json.setFail("该卡号"+cardNo+"已使用");
//								logger.info("该卡号[{}]已使用",cardNo);
//							}
//							if(cardNoValidBit.length() == cardNoLen + 1){//卡号长度+1表示该自选卡号未使用
//								json.setMsg(cardNoValidBit.substring(15));//取校验位
//							}
//						}else {
//							json.setS(false);
//							json.setFail("自选卡号"+cardNo+"验证失败！");
//						}
//					}else {
//						json.setMsg("false");
//						json.setFail("该卡号"+cardNo+"不在该卡产品范围内");
//						logger.info("该卡号[{}]不在该卡产品范围内",cardNo);
//					}
//				} catch (Exception e) {
//					json.setS(false);
//					json.setFail("自选卡号"+cardNo+"验证失败！");
//					logger.error("自选卡号"+cardNo+"验证失败！",e);
//				}
//			}
//		}
//		return json;
//	}
//		
//	/**
//	 * 由于卡产品改变导致自选卡号解锁
//	 * @param cardNo
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/cardNoDelete")
//	public Json cardNoDelete(String cardNo, String appNo, String attachNo, String validBit,String bscSuppInd){
//		Json json = Json.newSuccess();
//		if(StringUtils.isEmpty(appNo)){
//			logger.info("自选卡号"+cardNo+"解锁appNo为空");
//			json.setS(false);
//			return json;
//		}
//		if(StringUtils.isNotEmpty(cardNo)){
//			try {
//				Integer attno = StringUtils.stringToInteger(attachNo);
//				//核心解锁、申请人信息表更新卡号、更新锁表释放卡号
//				applyCardNoService.unlockSelectedCardNo(cardNo+validBit, appNo, attno, bscSuppInd);
//				json.setS(true);
//				logger.info("自选卡号"+cardNo+"解锁成功！");
//			} catch (Exception e) {
//				json.setS(false);
//				json.setFail(e.getMessage());
//				logger.error("自选卡号"+cardNo+"解锁失败！",e);
//			}
//		}
//		return json;
//	}
//	
//	/**
//	 * 根据姓名获取姓名拼音
//	 * @param name
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/getEmbLogo")
//	public Json getEmbLogo(String name){
//		Json json = Json.newSuccess();
//		try {
//			String embLogo = ChineseToPinYin.getFullSpell(name);
//			json.setMsg(embLogo);
//			logger.info("姓名"+name+"转换拼音成功！");
//		} catch (Exception e) {
//			json.setFail(e.getMessage());
//			logger.error("姓名"+name+"转换拼音失败！",e);
//		}
//		
//		return json;
//	}
//	
//	/**
//	 * 验证身份证号的正确性
//	 * @param idNo
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/idNoValid")
//	public Json idNoValid(String idNo){
//		Json json = Json.newSuccess();
//		try {
//			Boolean flag = IdentificationCodeUtil.isIdentityCode(idNo);
//			json.setS(flag);			
//			logger.info("身份号"+idNo+"验证成功！");
//		} catch (Exception e) {
//			json.setFail(e.getMessage());
//			logger.error("身份号"+idNo+"不正确！",e);
//		}
//		
//		return json;
//	}
//	
//	/**
//	 * 工号验证，返回用户信息
//	 * @param userId
//	 * @return
//	 */
//	@RequestMapping("/userIdValid")
//	@ResponseBody
//	public Json userIdValid(String userId){
//		Json json = Json.newSuccess();
//		logger.info("工号验证开始。。。。。。，userId="+userId);
//		if(StringUtils.isNotBlank(userId)){
//			try {
//				TmDitDic useuUserInfo = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_useUserInfo);//是否开启推广人信息验证,A-使用用户信息,B-强制使用用户信息,C-使用推广人信息,D-强制使用推广人信息
//				if (useuUserInfo != null && StringUtils.isNotEmpty(useuUserInfo.getRemark())) {
//					/**
//					 * 推广人校验有根据用户信息校验和推广人信息校验
//					 */
//					if (useuUserInfo.getRemark().equals("A") || useuUserInfo.getRemark().equals("B")) {//使用用户信息验证
//						TmAclUser user = userService.getUserByUserNo(userId);
//						if (user != null) {//如果用户信息存在，则带出用户相关信息
//							json.setObj(user);
//							json.setCode("user");
//							TmAclBranch tmAclBranch = commonService.getTmAclBranch(user.getBranchCode());// 查询上级机构
//							if (tmAclBranch != null && StringUtils.isNotEmpty(tmAclBranch.getParentCode())) {
//								json.setMsg(tmAclBranch.getParentCode());// 上级分行编号.getParentMagBranch()
//							}else {
//								json.setMsg("");
//							}
//						} else {
//							if (useuUserInfo.getRemark().equals("A")) {//如果没开启强制验证（A-使用户用信息），则不会清空数据，可继续录入
//								json.setCode("A");
//								json.setMsg("用户["+userId+"]不存在");
//							} else if (useuUserInfo.getRemark().equals("B")) {//如果开启强制验证（B-强制使用用户信息）,则清空数据
//								json.setCode("B");
//								json.setMsg("用户["+userId+"]不存在,请添加该用户");
//							}
//						}
//					}else if (useuUserInfo.getRemark().equals("C") || useuUserInfo.getRemark().equals("D")) {//使用推广人信息验证
//						TmAppSprePerBank spreader = new TmAppSprePerBank();
//						spreader.setSpreaderNum(userId);
//						List<TmAppSprePerBank> spreaders = applySpreaderInfoService.getSpreaderByParam(spreader);
//						if (CollectionUtils.sizeGtZero(spreaders)) {//如果推广人存在，则带出相关推广人信息
//							spreader = spreaders.get(0);
//							json.setCode("spreader");
//							json.setObj(spreader);// 推广人信息
//							TmAclBranch tmAclBranch = commonService.getTmAclBranch(spreader.getSpreaderBankId());// 查询上级机构
//							if (tmAclBranch != null && StringUtils.isNotEmpty(tmAclBranch.getParentCode())) {
//								json.setMsg(tmAclBranch.getParentCode());// 上级分行编号.getParentMagBranch()
//							}else {
//								json.setMsg("");
//							}
//							logger.info("推广人/客户经理工号验证成功，工号为"+userId+","+"姓名为"+spreader.getSpreaderName());
//						}else {
//							if (useuUserInfo.getRemark().equals("C")) {//如果没开启强制验证（C-使用推广人信息），则不会清空数据，可继续录入
//								json.setCode("C");
//								json.setMsg("推广人/客户经理["+userId+"]信息不存在");
//							} else if (useuUserInfo.getRemark().equals("D")) {//如果开启强制验证（D-强制使用推广人信息）,则清空数据
//								json.setCode("D");
//								json.setMsg("推广人/客户经理["+userId+"]信息不存在，请新增该推广人/客户经理");
//							}
//						}
//					}
//				} else {
//					logger.info("未配置工号验证，请在进阶参数管理页面中进行配置！");
//				}
//			} catch (Exception e) {
//				json.setFail(e.getMessage());
//				logger.error("推广人/客户经理工号验证失败，userId="+userId, e);
//			}
//		}
//		
//		return json;
//	}
//	
//	/**
//	 * 导入历史信息按钮
//	 * @param 历史申请件编号historyAppNo
//	 * @param 历史申请类型historyAppType
//	 * @param 当前申请件编号appNo
//	 * @param 当前申请类型appType
//	 */
//	@RequestMapping("/importHistoryInfo")
//	public String importHistoryInfo(String historyAppNo ,String historyAppType ,String appNo ,String appType){
//		if(StringUtils.isBlank(appType)){
//			throw new ProcessException("导入历史信息时,申请类型为空!");
//		}
//							
// 		ApplyInfoDto hisApplyInfoDto = applyQueryService.getApplyInfoByAppNo(historyAppNo);
// 		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
// 		applyInfoDto.setAppNo(appNo);
// 		TmAppMain hisTmAppMain = hisApplyInfoDto.getTmAppMain();
// 		TmAppMain tmAppMain = hisTmAppMain; //历史信息覆盖当前申请信息
// 		//申请信息更新
// 		tmAppMain.setAppNo(appNo);	//申请件编号更新
// 		tmAppMain.setAppType(appType);//申请类型更新
// 		tmAppMain.setRtfState(RtfState.A05.name());  //审批状态更新
// 		
// 		TmAppAudit auditHist = hisApplyInfoDto.getTmAppAudit();
// 		TmAppAudit audit = auditHist;
// 		
// 		audit.setIsInstalment(null);//更新是否申请分期
// 		audit.setIsRealtimeIssuing(auditHist.getIsRealtimeIssuing());//更新是否实时发卡
// 		
// 		//无论是主卡还是独立附卡，都设置默认的受理网点
//		TmAclUser tmAclUser = commonService.getUserByUserNo(OrganizationContextHolder.getUserNo());
//		if(tmAclUser != null){
//			tmAppMain.setOwningBranch(tmAclUser.getBranchCode());//设置默认的受理网点
//		}
//		ApplyInfoDto newApplyInfoDto = new ApplyInfoDto();
//		newApplyInfoDto.setAppNo(appNo);
//		Integer attachNum = 0;
//		if(appType.equals(AppType.A.name()) || appType.equals(AppType.B.name())){
//			//卡产品更新
//			tmAppMain.setProductCd(applyInfoDto.getTmAppMain().getProductCd());
//
//			//主附同申时附卡信息处理
////			if(appType.equals(AppType.A.name())){
////				TmAppAttachApplierInfo attachCust = new TmAppAttachApplierInfo();
////				attachCust.setAppNo(appNo);
////				attachCust.setAttachNo(5);
////				attachCust.setCreateUser(OrganizationContextHolder.getUserNo());
////				attachCust.setCreateDate(new Date());
////				
////				Map<String, TmAppAttachApplierInfo> tmAppAttachInfoMap = new HashMap<String, TmAppAttachApplierInfo>();
////				tmAppAttachInfoMap.put(AppConstant.ATTATCH_TABS+"5",attachCust);
////				newApplyInfoDto.setTmAppAttachInfoMap(tmAppAttachInfoMap);
////				attachNum = 1;
////			}
////			//主卡信息处理
////			TmAppPrimApplicantInfo primCust = null;
////			if(historyAppType.equals(AppType.S.name())){
////				//主卡信息赋值
////				primCust = AppCommonUtil.setPrimAppData(hisApplyInfoDto, applyInfoDto);					
////			}else {
////				TmAppPrimApplicantInfo hisTmAppPrimApplicantInfo = hisApplyInfoDto.getTmAppPrimApplicantInfo();
////				primCust = hisTmAppPrimApplicantInfo;
////				TmAppPrimApplicantInfo priAppInfo = applyInfoDto.getTmAppPrimApplicantInfo();
////				if(priAppInfo != null){
////					primCust.setAppNo(appNo);
////					String name = priAppInfo.getName();
////					primCust.setName(name);
////					String embLogo = ChineseToPinYin.getFullSpell(name);
////					primCust.setEmbLogo(embLogo);//根据名字自动填充姓名拼音
////			 		String idType = priAppInfo.getIdType(); //获得证件类型
////			 		String idNo = priAppInfo.getIdNo();	//获得证件号码
////					primCust.setIdNo(idNo);
////					primCust.setIdType(idType);
////					if(StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name()) && StringUtils.isNotEmpty(idNo)){
////						//计算生日
////						primCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
////						//计算性别
////						primCust.setGender(IdentificationCodeUtil.getGender(idNo));
////						//计算年龄
////						primCust.setAge(IdentificationCodeUtil.getAge(idNo));
////					}else {
////						primCust.setBirthday(null);
////						primCust.setGender(null);
////						primCust.setAge(null);
////					}
////					primCust.setIfSelectedCard(null);//初始化自选卡号
////					primCust.setCardNo(null);
////					primCust.setCellphone(priAppInfo.getCellphone());	
////					primCust.setCreateUser(OrganizationContextHolder.getUserNo());
////					primCust.setCreateDate(new Date());				
////				}
////			}
////			newApplyInfoDto.setTmAppPrimApplicantInfo(primCust);
//			
//			if(hisApplyInfoDto.getTmAppContactMap() != null && hisApplyInfoDto.getTmAppContactMap().size() > 0){
//				newApplyInfoDto.setTmAppContactMap(hisApplyInfoDto.getTmAppContactMap());
//			}
//			if(hisApplyInfoDto.getTmAppPrimAnnexEvi() != null){
//				newApplyInfoDto.setTmAppPrimAnnexEvi(hisApplyInfoDto.getTmAppPrimAnnexEvi());
//			}
//		}else if(appType.equals(AppType.S.name())){
//			//附卡信息处理
////			TmAppAttachApplierInfo attachCust = null;
////			if(historyAppType.equals(AppType.S.name())){
////				TmAppAttachApplierInfo hisTmAppAttachApplierInfo = hisApplyInfoDto.getTmAppCustInfoMap().get(AppConstant.ATTATCH_TABS+5);//获取第一张附卡信息
////				attachCust = hisTmAppAttachApplierInfo;
////				TmAppAttachApplierInfo attachApplierInfo = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.ATTATCH_TABS+5);
////				if(attachApplierInfo != null){
////					attachCust.setAppNo(appNo);
////					String name = attachApplierInfo.getName();
////					attachCust.setName(name);
////					String embLogo = ChineseToPinYin.getFullSpell(name);
////					attachCust.setEmbLogo(embLogo);//根据姓名自动填充姓名拼音
////					String idType = attachApplierInfo.getIdType(); //获得证件类型
////			 		String idNo = attachApplierInfo.getIdNo();	//获得证件号码
////					attachCust.setIdType(idType);
////					attachCust.setIdNo(idNo);
////					if(StringUtils.isNotEmpty(idNo) && StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name())){
////						//计算生日
////						attachCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
////						//计算性别
////						attachCust.setGender(IdentificationCodeUtil.getGender(idNo));
////						//计算年龄
////						attachCust.setAge(IdentificationCodeUtil.getAge(idNo));
////					}else {
////						attachCust.setBirthday(null);
////						attachCust.setGender(null);
////						attachCust.setAge(null);
////					}
////					attachCust.setIfSelectedCard(null);//初始化自选卡号
////					attachCust.setCardNo(null);
////					attachCust.setCellphone(attachApplierInfo.getCellphone());
////					attachCust.setPrimCardNo(attachApplierInfo.getPrimCardNo());
////					attachCust.setRelationshipToBsc(null);
////					attachCust.setAttachNo(5);
////					attachCust.setCreateUser(OrganizationContextHolder.getUserNo());
////					attachCust.setCreateDate(new Date());
////				}
////			}else {
////				//附属卡信息赋值
////				attachCust = AppCommonUtil.setAttachAppData(hisApplyInfoDto, applyInfoDto);
////			}
////			Map<String, TmAppAttachApplierInfo> tmAppAttachInfoMap = new HashMap<String, TmAppAttachApplierInfo>();
////			tmAppAttachInfoMap.put(AppConstant.ATTATCH_TABS+"5",attachCust);
////			newApplyInfoDto.setTmAppAttachInfoMap(tmAppAttachInfoMap);
////			
////			//设置回显主卡人主要信息
////			String primCardNo = attachCust.getPrimCardNo();
////			if(StringUtils.isNotEmpty(primCardNo)){
////				applyInfoDto = commonService.queryPrimApplicantInfoByPrimCardNo(primCardNo, applyInfoDto);
////				newApplyInfoDto.setTmAppPrimApplicantInfo(applyInfoDto.getTmAppPrimApplicantInfo());
////				newApplyInfoDto.setTmAppCardFaceInfoMap(applyInfoDto.getTmAppCardFaceInfoMap());
////			}
//			attachNum = 1;
//		}
//		setAttr("attachNum", attachNum);//附卡数目
//		setAttr("appNo", appNo);
//		setAttr("appType", appType);
//		setAttr("showBtnFlag", true);//是否显示附卡添加/删除按钮
//		newApplyInfoDto.setTmAppMain(tmAppMain);
//		
//		//银行栏公共信息
//		TmAppPrimCardInfo primCardInfo = hisApplyInfoDto.getTmAppPrimCardInfo();
//		TmAppPrimCardInfo tmAppPrimCardInfo = new TmAppPrimCardInfo();
//		tmAppPrimCardInfo.setInputName(OrganizationContextHolder.getUserNo());
//		tmAppPrimCardInfo.setInputDate(new Date());
//		if (primCardInfo != null) {
//			tmAppPrimCardInfo.setBillingCycle(primCardInfo.getBillingCycle());
//		}
//		newApplyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);
//		// 获取卡产品信息
//		TmProduct tmProduct =  cacheContext.getProduct(tmAppMain.getProductCd());
//		//申请录入页面必输项字段配置,默认只有一张附卡
//		HashMap<String,List<ValidFieldInfoDto>> validFieldMap = commonService.validateField(appType, tmProduct, 1);
//		setAttr("validFieldMap", validFieldMap);
//		//页面字段配置
//		setAttr("pageFieldMap", applyInfoUtils.getPageFieldMap(tmProduct, appType,
//				AmsPagePathConstant.APPLICATION_INPUT_MODIFY, 0, attachNum, newApplyInfoDto));
//		
//		//是否显示分期信息
//		if("Y".equals(audit.getIsInstalment())){
//			setAttr("defaultInstalChoose", "Y");
//			setAttr("ifNotApplyInstal", "false");
//		}else {
//			setAttr("defaultInstalChoose", "N");
//			setAttr("ifNotApplyInstal", "true");
//		}
//		if("Y".equals(tmProduct.getIsInstalment())){
//			setAttr("ifNotShowInstal", "false");
//			setAttr("defaultInstalChoose", "Y");
//		}else{
//			setAttr("ifNotShowInstal", "true");
//		}
//		//是否使用自定义费率
//		if("Y".equals(tmProduct.getIfUseCustomRate())){
//			setAttr("ifNotUseCustomRate", "false");
//		}else {
//			setAttr("ifNotUseCustomRate", "true");
//		}
//		//是否readonly公务卡公司信息相关栏位
//		if("O".equals(tmProduct.getSubCardType())){
//			setAttr("ifCorpReadonly", "true");
//		}else {
//			setAttr("ifCorpReadonly", "false");
//		}
//		setAttr("isEditMemo", true);//录入页面标志，用于推广注记是否可编辑（预录入和历史导入可编辑）
//		
//		return cacheContext.getPageConfig(AmsPagePathConstant.APPLICATION_INPUT_MODIFY);
//	}
//	
//	/**
//	 * 添加主附同申附卡部分
//	 * @return
//	 */
//	@RequestMapping("/addAttachCard")
//	public String addAttachCard(Integer num, String productCd){
//		// 获取卡产品信息
//		TmProduct tmProduct =  cacheContext.getProduct(productCd);
//		setAttr("pageFieldMap", applyInfoUtils.getPageFieldMap(tmProduct, AppType.A.name(), 
//				AmsPagePathConstant.APPLICATION_INPUT_MODIFY, 1, num, null));
//		setAttr("atId", num);
//		setAttr("showBtnFlag", true);//设置是否显示新增/删除附卡按钮的标志
//		setAttr("addAttachFlag", true);//设置添加附卡的标志
//
//		return cacheContext.getPageConfig(AmsPagePathConstant.applyAddAttachCard);
//	}
//
//	
//	/**
//	 * set录入界面数据模型对象方法setApplyInfoDto()
//	 * 
//	 * @param applyInfoDto 录入界面数据模型对象
//	 * @param retrialFlag 重审标志
//	 * @param oldAppNo 重审件原appNo
//	 * @param saveFlag 保存标示（区分提交和删除）
//	 * @param rtfState 审批状态
//	 * @throws InvocationTargetException 
//	 * @throws IllegalAccessException 
//	 * @throws NoSuchMethodException 
//	 * @throws IllegalArgumentException 
//	 * @throws SecurityException 
//	 */
//	public void setApplyInfoDto(ApplyInfoDto applyInfoDto, String retrialFlag,
//			String oldAppNo, String saveFlag, String rtfState, Json json)
//			throws SecurityException, IllegalArgumentException,
//			NoSuchMethodException, IllegalAccessException,
//			InvocationTargetException {
//		TmAppMain tmAppMain	= getBean(TmAppMain.class);
//		TmAppAudit tmAppAudit = getBean(TmAppAudit.class);
//		TmAppCustInfo tmAppCust = getBean(TmAppCustInfo.class);
//		if(tmAppAudit==null){
//			tmAppAudit = new TmAppAudit();
//		}
//		String appNo = tmAppMain.getAppNo();
//		String appType = tmAppMain.getAppType();
//		if(StringUtils.isEmpty(rtfState)){
//			logger.error("录入保存/提交操作审批状态为空");
//			throw new ProcessException("申请件["+appNo+"]录入保存/提交操作审批状态为空");
//		}
//		String oldRtfState = rtfState;
////		String productCd = tmAppMain.getProductCd(); //卡产品代码
////		TmProduct tmProduct = null;
////		if(StringUtils.isNotEmpty(productCd)){
////			tmProduct = cacheContext.getProduct(productCd);
////			throw new ProcessException("产品["+productCd+"]无效，请确认该产品是否存在并有效");
////		}
//		//保存节点数据	
//		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
//		tmAppMain.setRtfState(rtfState);
//		applyInfoDto.setAppNo(appNo);
//		//推广人备忘
//		TmAppMemo tmAppMemo1 = getBean(TmAppMemo.class,"tmAppMemoSpr");
//		Map<String,TmAppMemo> tmAppMemoMapLast= new HashMap<String, TmAppMemo>();
//		if(tmAppMemo1!=null){
//			tmAppMemo1.setAppNo(appNo);
//			tmAppMemo1.setMemoType(AppConstant.APP_MEMO);
//			tmAppMemo1.setMemoVersion(0);
//			tmAppMemo1.setRtfState(tmAppMain.getRtfState());
//			tmAppMemo1.setTaskKey(EcmsAuthority.INPUT.name());
//			tmAppMemo1.setTaskDesc(EcmsAuthority.INPUT.lab);
//			tmAppMemo1.setCreateUser(OrganizationContextHolder.getUserNo());
//			tmAppMemoMapLast.put(tmAppMemo1.getMemoType()+"-"+tmAppMemo1.getTaskKey(), tmAppMemo1);
//		}
//		//预审人备注
//		TmAppMemo tmAppMemo2 = getBean(TmAppMemo.class,"tmAppMemoPre");
//		if(tmAppMemo2!=null){
//			tmAppMemo2.setAppNo(appNo);
//			tmAppMemo2.setMemoType(AppConstant.APP_REMARK);
//			tmAppMemo2.setMemoVersion(0);
//			tmAppMemo2.setRtfState(tmAppMain.getRtfState());
//			tmAppMemo2.setTaskKey(EcmsAuthority.INPUT.name());
//			tmAppMemo2.setTaskDesc(EcmsAuthority.INPUT.lab);
//			tmAppMemo2.setCreateUser(OrganizationContextHolder.getUserNo());
//			tmAppMemoMapLast.put(tmAppMemo2.getMemoType()+"-"+tmAppMemo2.getTaskKey(), tmAppMemo2);
//			tmAppMain.setRemark(tmAppMemo2.getMemoInfo());
//		}
//		applyInfoDto.setTmAppMemoMapLast(tmAppMemoMapLast);
//		
//		Map<String,TmAppCustInfo> custMap = new HashMap<String, TmAppCustInfo>();
//		//主附同申、独立主卡
//		if(tmAppCust!=null){
//			tmAppCust.setAppNo(appNo);
//			//去除页面输入的姓名拼音首尾空格
//			if(StringUtils.isNotBlank(tmAppCust.getEmbLogo())){
//				tmAppCust.setEmbLogo(tmAppCust.getEmbLogo().trim());
//			}
//			custMap.put(tmAppCust.getBscSuppInd()+tmAppCust.getAttachNo(), tmAppCust);
//			applyInfoDto.setTmAppCustInfoMap(custMap);
//		}
//		//后期优化，待办任务和申请进度查询只查TM_APP_MAIN一张表
//		//姓名，证件号码，移动电话,上一任务人......
//		if(StringUtils.isNotEmpty(tmAppCust.getName())){
//			tmAppMain.setName(tmAppCust.getName());
//		}
//		if(StringUtils.isNotEmpty(tmAppCust.getIdNo())){
//			tmAppMain.setIdNo(tmAppCust.getIdNo());
//		}
//		if(StringUtils.isNotEmpty(tmAppCust.getIdType())){
//			tmAppMain.setIdType(tmAppCust.getIdType());
//		}
//		if(StringUtils.isNotEmpty(tmAppCust.getCellphone())){
//			tmAppMain.setCellphone(tmAppCust.getCellphone());
//		}
//		if(StringUtils.isNotEmpty(tmAppCust.getCorpName())){
//			tmAppMain.setCorpName(tmAppCust.getCorpName());
//		}
//		if(StringUtils.isNotEmpty(tmAppCust.getEmpPhone())){
//			tmAppMain.setEmpPhone(tmAppCust.getEmpPhone());
//		}
//		tmAppMain.setTaskOwner(OrganizationContextHolder.getUserNo());
////		重审暂时不做
////		if(StringUtils.isNotEmpty(retrialFlag) && retrialFlag.equals(Indicator.Y.name())){
////			ApplyInfoDto oldApplyInfoDto = applyQueryService.getApplyInfoByAppNo(oldAppNo);
////	 		TmAppMain oldTmAppMain = oldApplyInfoDto.getTmAppMain();
////	 		if (oldTmAppMain != null) {
////	 			tmAppMain.setSugLmt(oldTmAppMain.getSugLmt());
////	 			tmAppMain.setPointResult(oldTmAppMain.getPointResult());
////	 		}
////		}
//		applyInfoDto.setTmAppMain(tmAppMain);
//		//保存历史节点(历史节点只在提交操作中更新)
//		if (StringUtils.isNotEmpty(rtfState) && (rtfState.equals(RtfState.A10.name())
//						|| rtfState.equals(RtfState.A20.name())
//						|| rtfState.equals(RtfState.A30.name()) 
//						|| rtfState.equals(RtfState.A40.name()))) { // 提交操作
//			List<TmAppHistory> tmAppHistoryList = new ArrayList<TmAppHistory>();
//			TmAppHistory tmAppHistory = new TmAppHistory();
//			//封装历史操作数据			
//			tmAppHistory = AppCommonUtil.insertApplyHist(tmAppMain.getAppNo(), 
//					OrganizationContextHolder.getUserNo(), RtfState.valueOf(rtfState), null, tmAppMemo2.getMemoInfo());
//			tmAppHistory.setName(tmAppMain.getName());
//			tmAppHistory.setIdType(tmAppMain.getIdType());
//			tmAppHistory.setIdNo(tmAppMain.getIdNo());
//			tmAppHistoryList.add(tmAppHistory);
//			applyInfoDto.setTmAppHistoryList(tmAppHistoryList); //主附历史申请记录只保存主卡信息记录
//		}
//		
//		
//		Map<String , TmAppContact> tmAppContactInfoMap = new HashMap<String, TmAppContact>();
//		List<TmAppContact> tmAppContactList = getList(TmAppContact.class, "tmAppContact");
//		//验证并赋值联系信息
//		tmAppContactInfoMap = AppCommonUtil.validAppContactEntityIsNull(tmAppContactInfoMap, tmAppContactList);
//		applyInfoDto.setTmAppContactMap(tmAppContactInfoMap);
//		//附件证明
//		TmAppPrimAnnexEvi tmAppPrimAnnexEvi = getBean(TmAppPrimAnnexEvi.class);
//		applyInfoDto.setTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
//		
//		//主卡卡片信息(银行专用栏信息)
//		TmAppPrimCardInfo tmAppPrimCardInfo = getBean(TmAppPrimCardInfo.class);
//		tmAppPrimCardInfo.setAppNo(appNo);
//		//推广方式
//		String[] spreaderMode = getParas("tmAppPrimCardInfo.spreaderMode[]");
//		if(spreaderMode == null || spreaderMode.length == 0){
//			spreaderMode = getParas("tmAppPrimCardInfo.spreaderMode");
//		}
//		if(spreaderMode != null && spreaderMode.length > 0){
//			tmAppPrimCardInfo.setSpreaderMode(commonService.arrayToString(spreaderMode,","));
//		}
//		applyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);
//		
//		//节点公共数据 赋值
//		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMain);
//		Map<String , Serializable> tmAppNodeInfoRecordMap = new HashMap<String, Serializable>();
//		tmAppNodeInfoRecordMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
//		applyInfoDto.setTmAppNodeInfoRecordMap(tmAppNodeInfoRecordMap);
//		
//		//TODO 虚拟卡申请
//		TmAppCreditLoan tmAppCreditLoan = getBean(TmAppCreditLoan.class);
//		tmAppCreditLoan.setAppNo(appNo);
//		tmAppCreditLoan.setStatus(rtfState);
//		tmAppCreditLoan.setProductCd(tmAppMain.getProductCd());
//		applyInfoDto.setTmAppCreditLoan(tmAppCreditLoan);
//		
//		TmAppOrderInfo tmAppOrderInfo = getBean(TmAppOrderInfo.class);
//		tmAppOrderInfo.setAppNo(appNo);
//		applyInfoDto.setTmAppOrderInfo(tmAppOrderInfo);
//		
//		TmAppMain queryTmAppMain = new TmAppMain();
// 		queryTmAppMain.setAppNo(appNo);
// 		TmAppMain dbMain = applyQueryService.getTmAppMainByAppNo(appNo);
// 		if(dbMain!=null) {
// 			applyInfoService.updateApplyInput(applyInfoDto);
// 		}else {
// 			applyInfoService.saveApplyInput(applyInfoDto);
//			applyInfoService.updateCheatInfoFromOld(oldAppNo, appNo);//拷贝重申件的欺诈和114信息到新件
// 		}
//		//只有提交时才会发起工作流（非保存）
//		if(Indicator.N.name().equals(saveFlag)) {
//			if(StringUtils.isNotEmpty(rtfState) && (rtfState.equals(RtfState.A10.name())||rtfState.equals(RtfState.A30.name())||rtfState.equals(RtfState.A40.name()))){ //提交操作
//				activityStart(applyNodeCommonData ,appNo ,tmAppMain);  //启动工作流
//			}
//		}
//	}
//
//	/**
//	 * 发起工作流的方法
//	 * 
//	 */
//	public void activityStart(ApplyNodeCommonData applyNodeCommonData ,String appNo ,TmAppMain tmAppMain) {
//		
//		Map<String, Serializable> vars = new HashMap<String, Serializable>();
//		vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
//		Task task = taskService.createTaskQuery().processInstanceBusinessKey(appNo).singleResult();
//		if(task!=null){
//			activitiService.completeTask(task.getId(), vars,appNo);
//		} else{
//			TmProduct tmProduct = cacheContext.getProduct(tmAppMain.getProductCd());
//			if(tmProduct != null && StringUtils.isNotBlank(tmProduct.getProcdefKey())){
//				activitiService.startNewProcess(tmProduct.getProcdefKey(), appNo, vars);
//			} else {
//				String processKey = activitiService.getDefProcess();
//				if(StringUtils.isNotEmpty(processKey)){
//					activitiService.startNewProcess(processKey, appNo, vars);
//				}else {
//					logger.info("没有获取到默认的流程定义，请检查流程！"+LogPrintUtils.printAppNoLog(appNo, null,null));
//				}
//			}
//		}
//	}
//	
//	/**
//	 * 	申请录入信息更新(保存按钮)
//	 *
//	 * @return
//	 */
//	 
//	@ResponseBody
//	@RequestMapping("/updateApplyInputInfo")
//	public Json updateApplyInputInfo(){
//		long start = System.currentTimeMillis();
//		
//		String retrialFlag=getPara("retrialFlag");//重审标志
//		String oldAppNo=getPara("oldAppNo");//重审件原件appNo
//		logger.info("保存客户申请数据开始........"+LogPrintUtils.printAppNoLog(oldAppNo, start,null));
//		
//		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
//		Json json = Json.newSuccess();
//		try {
//			setApplyInfoDto(applyInfoDto ,retrialFlag ,oldAppNo ,Indicator.Y.name(),RtfState.A05.name(),json);
//			
//			//  申请贷款金额和商户额度的判断
//			if(applyInfoDto.getTmAppAudit()!=null && "Y".equals(applyInfoDto.getTmAppAudit().getIsInstalment())){
//			TmAppInstalLoan appInstalLoan = applyInfoDto.getTmAppInstalLoan();
//			out:if(appInstalLoan!=null){
//				TmAppInstalMerchant appInstalMerchant = new TmAppInstalMerchant();
//				if(appInstalLoan.getMccNo()==null || appInstalLoan.getCashAmt()==null){
//					break out;
//				}
//				appInstalMerchant.setMerId(appInstalLoan.getMccNo());
//				appInstalMerchant = appInstalMerchantDao.queryForOne(appInstalMerchant);
//				//授信总额
//				BigDecimal merAmt = new BigDecimal(0);
//				//分期借款金额
//				BigDecimal instalAmt = appInstalLoan.getCashAmt()==null?BigDecimal.ZERO:appInstalLoan.getCashAmt();
//				if(appInstalMerchant!=null){
//					merAmt = appInstalMerchant.getMerLmt()==null?BigDecimal.ZERO:appInstalMerchant.getMerLmt();
//					if (merAmt.compareTo(instalAmt)<0) {
//						json.setMsg("保存成功,警告:申请金额超出授信总额:"+merAmt.subtract(instalAmt));
//					}
//				}
//			}
//			}
//		} catch (Exception e) {
//			logger.error("保存客户申请数据失败!"+LogPrintUtils.printAppNoLog(oldAppNo, start,null), e);
//			json.setFail(e.getMessage());
//		}
//		logger.info("保存客户申请数据结束["+json.getMsg()+"]........"+LogPrintUtils.printAppNoEndLog(oldAppNo, start,null));
//		return json;
//	}
//	
//	/**
//	 * 申请录入信息更新到下一个节点(提交按钮)
//	 * 
//	 * @return
//	 */
//	 
//	@ResponseBody
//	@RequestMapping("/updateAndNextnode")
//	public Json updateAndNextnode(){
//		String retrialFlag=getPara("retrialFlag");//重审标志
//		String oldAppNo=getPara("oldAppNo");//重审件原件appNo
//		
//		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
//		Json json = Json.newSuccess();
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
//
//		try {
//			setApplyInfoDto(applyInfoDto ,retrialFlag ,oldAppNo ,Indicator.N.name(),RtfState.A10.name(),json);
//			logger.info("录入申请数据提交成功！");
//		} catch (Exception e) {
//			logger.error("=========》》》录入申请数据提交异常！《《《《=========",e);
//			json.setFail(e.getMessage());
//			return json;
//		}
//
//		if("Y".equals(audit.getIsInstalment()) && appInstalMerchant != null){
//			appInstalMerchant.setInAuditLmt(inAuditLmt.add(cashAmt));
//			logger.info("审批中金额:{}",inAuditLmt.add(cashAmt));
//			appInstalMerchantDao.updateNotNullable(appInstalMerchant);
//		}		
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
}