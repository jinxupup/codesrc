package com.jjb.cas.biz.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.CardFetchMethod;
import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.acl.facility.enums.bus.StmtMediaType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.util.ApplyServiceValidityUtil;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DataTypeUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 校验录入数据逻辑
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:31:48
 * @version V1.0
 */
public class CheckReqT1005 {

	private static Logger logger = LoggerFactory.getLogger(CheckReqT1005.class);
//	@Autowired
//	private CodeMarkUtils codeMarkUtils;

	/**
	 * 数据必输项、长度、区间、格式校验（不包括联动必输情况）
	 * @param req
	 * @param cacheContext
	 * @param logger
	 */
	public static void checkNull(T1005Req req,CacheContext cacheContext,ApplySpreaderInfoService spreaderInfoService) {

		try {
			if (req==null) {

				logger.error("联机交易请求为空，系统处理失败");
				throw new ProcessException("联机交易请求为空，系统处理失败");
			}
			if (StringUtils.isBlank(req.getAppType())) {
				logger.error("申请类型不能为空，ExtAppNo【"+req.getAppnoExternal()
					+"】,TokenId【"+req.getTokenId()+"】");
				throw new ProcessException("申请类型不能为空");
			}
			
			// 卡产品，卡面信息有效性
			String productCd = req.getProductCd();
			LinkedHashMap<Object, Object> productMap = cacheContext.getSimpleProductLinkedMap("A",null);
			if(productMap == null || productMap.isEmpty()){
				logger.error("该申请资料卡产品编号【"+productCd+"】无效");
				throw new ProcessException("该申请资料卡产品编号无效");
			}
			if (StringUtils.isNotEmpty(productCd)) {
				String productDesc = (String) productMap.get(productCd);
				if (productDesc == null) {
					logger.error("该申请资料卡产品编号【"+productCd+"】无效");
					throw new ProcessException("该申请资料卡产品编号无效");
				}
				LinkedHashMap<Object, Object> cardFaceMap = cacheContext.getSimpleProductCardFaceLinkedMap(productCd);
				if(cardFaceMap==null || cardFaceMap.isEmpty()){
					logger.error("该产品【"+productCd+"】未配置卡面信息，请联系管理员!");
					throw new ProcessException("该产品【"+productCd+"】未配置卡面信息，请联系管理员!");
				}
				if (!cardFaceMap.containsKey(req.getCardFace())) {
					String cfDb = "";
					for (Object s : cardFaceMap.values()) {
						logger.debug("可选卡面ID：" + s.toString());
						if(s!=null){
							TmAclDict dict = (TmAclDict) s;
							if(dict!=null && dict.getCode()!=null){
								cfDb = dict.getCode();
								
							}
						}
					}
					if(StringUtils.isNotEmpty(cfDb)){
						req.setCardFace(cfDb);
					}else{
						logger.warn("该申请资料产品【"+productCd+"】卡面信息无效【"+req.getCardFace()+"】无效");
					}
				}
			} else {
				logger.error("未获取到系统配置有效的卡产品,请重试！");
				throw new ProcessException("未获取到系统配置有效的卡产品,请重试！");
			}
			if(StringUtils.isNotEmpty(req.getSpreaderNo())) {
				TmAppSprePerBank spreader = new TmAppSprePerBank();
				spreader.setSpreaderNum(req.getSpreaderNo());
				//推广人处理
				List<TmAppSprePerBank> sprList = spreaderInfoService.getSpreaderByParam(spreader);
				if(CollectionUtils.sizeGtZero(sprList)) {
					spreader = sprList.get(0);
					if(spreader!=null) {
//						String sprStatus = "(无效或离职)";//暂时不这么玩
//						if(!StringUtils.equals(spreader.getSpreaderStatus(), "1") 
//								&& StringUtils.isNotEmpty(spreader.getSpreaderName())) {
//							//因为oracle数据库的话，每个汉字占3个，所以截取下咯
//							spreader.setSpreaderName((spreader.getSpreaderName()+sprStatus).substring(0, 33));
//						}
						req.setSpreaderName(StringUtils.setValue(spreader.getSpreaderName(), req.getSpreaderName()));
						req.setSpreaderBank(StringUtils.setValue(spreader.getSpreaderOrg(), req.getSpreaderBank()));
						req.setSpreaderOrg(StringUtils.setValue(spreader.getSpreaderBankId(), req.getSpreaderOrg()));
						req.setSpreaderTelephone(StringUtils.setValue(spreader.getSpreaderPhone(), req.getSpreaderTelephone()));
						if((StringUtils.equals(spreader.getSprUserType(), "1")
									||StringUtils.equals(spreader.getSprUserType(), "3")
									||StringUtils.equals(spreader.getSprUserType(), "4")) 
							&& StringUtils.equals(spreader.getSpreaderStatus(), "1")) {//是否行员,并且推广人状态有效
							req.setSpreaderIsBankEmployee("Y");
						}
					}
				}
			}
			String appType = req.getAppType();
			List<ValidFieldInfoDto> validFieldInfoDtoList = AppCommonUtil.validateFields(appType, req.getProductCd(), cacheContext);
			if(CollectionUtils.sizeGtZero(validFieldInfoDtoList)){
				Map<String, Serializable> reqMap = req.convertToMap();
				List<Map<String, Serializable>> custMaps = null; 
				if(CollectionUtils.sizeGtZero(req.getCusts())) {
					custMaps = new ArrayList<Map<String, Serializable>>(); 
					for (int i = 0; i < req.getCusts().size(); i++) {
						TCustInfo c = req.getCusts().get(i);
						Map<String, Serializable> cMap = c.convertToMap();
						custMaps.add(cMap);
					}
				}
				for (ValidFieldInfoDto validFieldInfoDto : validFieldInfoDtoList) {
					String field = validFieldInfoDto.getField();
					if(field==null){
						continue;
					}
					Serializable value = reqMap.get(field);
					if(reqMap.containsKey(field)) {
						value = reqMap.get(field);
					}else if(custMaps!=null) {
						for (int i = 0; i < custMaps.size(); i++) {
							Map<String, Serializable> cMap = custMaps.get(i);
							String bscSuppind = DataTypeUtils.getStringValue(cMap.get("bscSuppInd"));
							if(StringUtils.equals(bscSuppind, appType)) {
								value = cMap.get(field);
								continue;
							}
						}
					}
					String fieldValue = value==null?"":value.toString().trim();//去空格
					if(StringUtils.isEmpty(fieldValue)){
						//必输项
						if(validFieldInfoDto.getNotEmptyFlag()) {
							logger.error("联机进件【"+field+"-"+validFieldInfoDto.getFieldName()+"】必填");
							throw new ProcessException("联机进件【"+validFieldInfoDto.getFieldName()+"】必填");
						}
					}else {
						if(validFieldInfoDto.getBetweenFlag()){//区间
							Integer newValue = Integer.valueOf(fieldValue);
							if(newValue.compareTo(Integer.valueOf(validFieldInfoDto.getBetweenMin())) < 0 || newValue.compareTo(Integer.valueOf(validFieldInfoDto.getBetweenMax())) > 0){
								logger.error("联机进件【"+field+"-"+validFieldInfoDto.getFieldName()+"】不在区间【"+validFieldInfoDto.getBetweenMin()+","+validFieldInfoDto.getBetweenMax()+"】范围内");
								throw new ProcessException("联机进件【"+validFieldInfoDto.getFieldName()+"】不在区间【"+validFieldInfoDto.getBetweenMin()+","+validFieldInfoDto.getBetweenMax()+"】范围内");
							}
						}
						if(validFieldInfoDto.getLengthFlag()){//字符串长度
							if(fieldValue.length() > Integer.valueOf(validFieldInfoDto.getLengthMax())){
								logger.error("联机进件【"+field+"-"+validFieldInfoDto.getFieldName()+"】超过【"+validFieldInfoDto.getLengthMax()+"】长度");
								throw new ProcessException("联机进件【"+validFieldInfoDto.getFieldName()+"】超过【"+validFieldInfoDto.getLengthMax()+"】长度");
							}
						}
						
						if(validFieldInfoDto.getRegexpFlag()){//正则
							Pattern p = Pattern.compile(validFieldInfoDto.getRegexp());
							Matcher m = p.matcher(fieldValue);
							if(!m.matches()){
//							if(!fieldValue.matches(validFieldInfoDto.getRegexp())){
								logger.error("联机进件【"+field+"-"+validFieldInfoDto.getFieldName()+"】格式不正确,验证格式【"+validFieldInfoDto.getRegexp()+"】");
								throw new ProcessException("联机进件【"+validFieldInfoDto.getFieldName()+"】格式不正确");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("请求报文字段校验处理失败",e);
			throw new ProcessException("请求校验处理失败["+e.getMessage()+"]");
		}

	}
	
	/**
	 * 数据逻辑校验（包括数据联动验证）
	 * @param applyDto
	 * @param req
	 * @param cacheContext
	 * @param logger
	 * @return
	 */
	public static String logicCheck(ApplyInfoDto applyDto, T1005Req req, CacheContext cacheContext, TmMirCardService tmMirCardService, Logger logger) {
		TmAppMain appMain = applyDto.getTmAppMain();// 申请主表
		if (appMain == null) {
			return "该申请资料缺失";
		}
		String appType = req.getAppType();
		String orgReq = req.getOrg();
		if (!orgReq.equals(OrganizationContextHolder.getOrg())) {
			// 机构号
			return "机构号与当前机构不符";
		}
		Map<String,TmAppCustInfo> custMap = applyDto.getTmAppCustInfoMap();
		if(custMap==null || custMap.isEmpty()){
			return "无有效申请人信息";
		}
		TmAppCustInfo primCust = custMap.get(AppConstant.bscSuppInd_B_1);
		TmAppCustInfo attachCust = custMap.get(AppConstant.bscSuppInd_S_1);
//		TmAppPrimApplicantInfo tmAppPrimApplicantInfo = appData.getTmAppPrimApplicantInfo();// 主卡申请人信息
//		// 附卡申请人信息(独立申请唯一)
//		Map<String, TmAppAttachApplierInfo> tmAppAttachApplierInfos = applyDto.getTmAppAttachInfoMap();
		TmAppPrimCardInfo tmAppPrimCardInfo = applyDto.getTmAppPrimCardInfo();// 主卡申请人卡片信息

		// 申请资料申请网点有效性
		LinkedHashMap<Object, Object> branchMap = cacheContext.getBranchMapByParam(AppConstant.issueInd);
		if (StringUtils.isNotEmpty(appMain.getOwningBranch())) {
			if (!branchMap.isEmpty()) {
				if (!branchMap.containsKey(appMain.getOwningBranch())) {
					return "该申请资料申请网点无效";
				}
			}
		}

		/*if (StringUtils.isNotBlank(req.getSpreaderBank())) {
			TmAclBranch b1 = cacheContext.getTmAclBranchByCode(req.getSpreaderBank());
			if (b1==null || StringUtils.isEmpty(b1.getBranchCode())) {
				return "推广人所属分行不存在";
			}
		}*/

		//主附卡身份证、性别、 生日
		String idType = null;
		String idNo = null;
		String gender = null;
		Date birthday = null;
		
		//主附卡证件是否长期有效、证件到期日
		String idLastAll = null;
		Date idLastDate = null;
		
		//主附卡领卡方式
		String cardFetchMethod = null;
		String cardMailerInd = null;
		String fetchBranch = null;
		
		
		if(appType.equals(AppType.A.name()) || appType.equals(AppType.B.name())){
			if (primCust == null) {
				return "该申请资料无主卡申请人信息";
			}
			if (tmAppPrimCardInfo == null) {
				return "该申请资料无主卡申请人卡片信息";
			}
			// 主卡联系人信息 (第一联系人和其他联系人)
			Map<String, TmAppContact> tmAppContacts = applyDto.getTmAppContactMap(); //TmAppContact
			if (tmAppContacts == null || tmAppContacts.size() == 0) {
				return "联系人信息为空，请填写至少一位联系人信息";
			}
			TmAppContact contactInfo = tmAppContacts.get(AppConstant.M_CON_ITEM_INFO_PREFIX+1);//亲属联系人信息
			TmAppContact oContactInfo = tmAppContacts.get(AppConstant.M_CON_ITEM_INFO_PREFIX+2);//其他联系人信息
			// 校验主卡的直接联系人
			String msgContact = checkContact(contactInfo);
			if (StringUtils.isNotBlank(msgContact))
				return msgContact;

			// 校验主卡的其他联系人
			String msgOContact = checkContact(oContactInfo);
			if (StringUtils.isNotBlank(msgOContact)){
				return msgOContact;
			}
			
			idType = primCust.getIdType();
			idNo = primCust.getIdNo();
			gender = primCust.getGender();
			birthday = primCust.getBirthday();
			//身份证、性别、 生日验证
			String checkPrimIdMsg = checkId(idType,	idNo, gender, birthday);
			if (StringUtils.isNotBlank(checkPrimIdMsg)) {
				return "主卡" + checkPrimIdMsg;
			}
			//证件是否长期有效、证件到期日验证
			idLastAll = primCust.getIdLastAll();
			idLastDate = primCust.getIdLastDate();
			String checkIdLastAllMsg = checkIdLastAll(idLastAll, idLastDate);
			if (StringUtils.isNotBlank(checkIdLastAllMsg)) {
				return "主卡" + checkIdLastAllMsg;
			}
			if(appType.equals(AppType.A.name())){
				if(attachCust == null){
					throw new ProcessException("找不到附卡信息【"+appMain.getAppNo()+"】");
				}else {
					idType = attachCust.getIdType();
					idNo = attachCust.getIdNo();
					gender = attachCust.getGender();
					birthday = attachCust.getBirthday();
					String checkAttachIdMsg = checkId(idType, idNo, gender, birthday);
					if (StringUtils.isNotBlank(checkAttachIdMsg)) {
						return "附卡" + checkAttachIdMsg;
					}
					//证件是否长期有效、证件到期日验证
					idLastAll = attachCust.getIdLastAll();
					idLastDate = attachCust.getIdLastDate();
					String checkAttchIdLastAllMsg = checkIdLastAll(idLastAll, idLastDate);
					if (StringUtils.isNotBlank(checkAttchIdLastAllMsg)) {
						return "附卡" + checkAttchIdLastAllMsg;
					}
				}
			}
			//领卡方式验证
			cardFetchMethod = tmAppPrimCardInfo.getCardFetchMethod();
			cardMailerInd = tmAppPrimCardInfo.getCardMailerInd();
			fetchBranch = tmAppPrimCardInfo.getFetchBranch();
			String checkCardFetchMsg = checkCardFetch(cacheContext, cardFetchMethod, cardMailerInd, fetchBranch);
			if (!"".equals(checkCardFetchMsg)) {
				return "主卡" + checkCardFetchMsg;
			}
			//约定还款类型验证
			String checkDdIndMsg = checkDdInd(tmAppPrimCardInfo.getDdInd(), tmAppPrimCardInfo.getDdBankAcctName(),
					tmAppPrimCardInfo.getDdBankAcctNo());
			if (!"".equals(checkDdIndMsg)) {
				return "主卡" + checkDdIndMsg;
			}
			//账单介质类型验证
			String checkStmtMediaMsg = checkStmtMediaType(tmAppPrimCardInfo.getStmtMediaType(), tmAppPrimCardInfo.getStmtMailAddrInd(),
					primCust.getEmail());
			if (!"".equals(checkStmtMediaMsg)) {
				return "主卡" + checkStmtMediaMsg;
			}
			
		}else if(appType.equals(AppType.S.name())){
			if(attachCust == null){
				throw new ProcessException("找不到独立附卡信息【"+appMain.getAppNo()+"】");
			}else {
				idType = attachCust.getIdType();
				idNo = attachCust.getIdNo();
				gender = attachCust.getGender();
				birthday = attachCust.getBirthday();
				//附卡证件类型、证件号、性别、生日
				String checkAttachIdMsg = checkId(idType, idNo, gender, birthday);
				if (StringUtils.isNotBlank(checkAttachIdMsg)) {
					return "独立附卡" + checkAttachIdMsg;
				}
				//附卡证件是否长期有效、证件到期日验证
				String checkAttchIdLastAllMsg = checkIdLastAll(attachCust.getIdLastAll(), attachCust.getIdLastDate());
				if (StringUtils.isNotBlank(checkAttchIdLastAllMsg)) {
					return "附卡" + checkAttchIdLastAllMsg;
				}
				//附卡领卡方式验证
				String checkCardFetchMsg = checkCardFetch(cacheContext, cardFetchMethod, cardMailerInd, fetchBranch);
				if (!"".equals(checkCardFetchMsg)) {
					return "附卡" + checkCardFetchMsg;
				}
				
				//主卡卡号验证
				String primCardNo = attachCust.getPrimCardNo();
				if(StringUtils.isNotBlank(primCardNo)){
					String flag = tmMirCardService.validateByPrimCardNo(primCardNo);
					if(StringUtils.isNotEmpty(flag) && !flag.equals("true")){
						if(flag.equals("S")){
					     	return "附卡不能申请独立附卡";
					    }else if(flag.equals("V")){
					    	return "主卡卡号无效";
					    }else if(flag.equals("B")){
					    	return "主卡卡产品无效";
					    }else if(flag.equals("O")){
					    	return "公务卡不能申请独立附卡";
					    }else{
					     	return "未检索到有效的主卡持卡人信息,请核实主卡账户和卡片状态";
					    }
					}
				}else {
					return "主卡卡号为必填";
				}
			}
		}
		return "";
	}

	// 三个信息需要控制为要么全部填写，要么全部不填写
	// 其他联系人中文名 其他联系人与申请人关系 其他联系人移动电话
	private static String checkContact(TmAppContact contactInfo) {
		if (contactInfo != null) {
			String name = contactInfo.getContactName();
			Serializable relat = contactInfo.getContactRelation();
			String contactMobile = contactInfo.getContactMobile();

			// 存在为true
			boolean b1 = StringUtils.isNotBlank(name);
			boolean b2 = (relat != null);
			boolean b3 = StringUtils.isNotBlank(contactMobile);
			if (!((b1 && b2 && b3) || (!b1 && !b2 && !b3))) {
				return "其他联系人信息不完整";
			}
		}

		return "";
	}

	/**
	 * 卡片领取方式联动校验
	 * @param cardFetchMethod  卡片领取方式
	 * @param cardMailerInd 卡片寄送地址
	 * @param fetchBranch 领卡网点
	 */
	private static String checkCardFetch(CacheContext cacheContext, String cardFetchMethod,String cardMailerInd, String fetchBranch) {
		if (StringUtils.isNotEmpty(cardFetchMethod)){
			if(cardFetchMethod.equals(CardFetchMethod.B.toString())	&& StringUtils.isBlank(fetchBranch)){
				if(StringUtils.isBlank(fetchBranch)){
					return "领卡网点为空";
				}else {
					LinkedHashMap<Object, Object> branchMap = cacheContext.getBranchMapByParam(AppConstant.cardCollectInd);
					if (!branchMap.isEmpty()) {
						if (!branchMap.containsKey(fetchBranch)) {
							return "该申请资料领卡网点无效";
						}
					}
				}
			}else if((cardFetchMethod.equals(CardFetchMethod.A.toString()) || cardFetchMethod.equals(CardFetchMethod.E.toString()))
					&& StringUtils.isBlank(cardMailerInd)) {
				return "卡片寄送地址为空";
			}else {
				return "";
			}
		}
		return "卡片领取方式为空";
	}

	/**
	 * 证件到期日联动验证
	 * @param idLastAll 证件是否长期有效
	 * @param idLastDate 证件到期日
	 * @return
	 */
	private static String checkIdLastAll(String idLastAll, Date idLastDate){
		if(StringUtils.isNotEmpty(idLastAll)){
			if(idLastAll.equals(Indicator.N.name()) && StringUtils.isEmpty(idLastDate==null?"":idLastDate.toString())){
				return "证件到期日为空";
			}
		}else {
			return "证件是否长期有效为空";
		}
		
		return "";
	}
	
	/**
	 * 约定还款类型联动验证
	 * @param ddInd 约定还款类型
	 * @param ddBankAcctName 约定还款扣款人姓名
	 * @param ddBankAcctNo 约定本行还款扣款账号
	 * @return
	 */
	private static String checkDdInd(String ddInd, String ddBankAcctName, String ddBankAcctNo){
		if(StringUtils.isNotEmpty(ddInd) && !ddInd.equals(Indicator.N.name())){
			if(StringUtils.isBlank(ddBankAcctName)){
				return "约定还款扣款人姓名为空";
			}
			if(StringUtils.isBlank(ddBankAcctNo)){
				return "约定本行还款扣款账号为空";
			}else if(!ddBankAcctNo.matches("^\\d{16,19}")){
				return "约定本行还款扣款账号格式不正确";
			}
		}
		if(StringUtils.isEmpty(ddInd)){
			return "约定还款类型为空";
		}
		
		return "";
	}
	
	
	/**
	 * 账单介质类型联动验证
	 * @param stmtMediaType 账单介质类型
	 * @param stmtMailAddrInd 纸质账单寄送地址
	 * @param email 电子账单接收邮箱
	 * @return
	 */
	private static String checkStmtMediaType(String stmtMediaType, String stmtMailAddrInd, String email){
		if(StringUtils.isNotEmpty(stmtMediaType)){
			if(stmtMediaType.equals(StmtMediaType.P.name()) && StringUtils.isBlank(stmtMailAddrInd)){
				return "纸质账单寄送地址为空";
			}else if(stmtMediaType.equals(StmtMediaType.E.name())){
				if(StringUtils.isBlank(email)){
					return "电子账单接收邮箱为空";
				}/*else if(!email.matches(ApplyInfoBItem.REG_EMAIL)){
					return "电子账单接收邮箱格式不正确";
				}*/
			}else if (stmtMediaType.equals(StmtMediaType.B.name())) {
				if(StringUtils.isBlank(email)){
					return "电子账单接收邮箱为空";
				}/*else if(!email.matches(ApplyInfoBItem.REG_EMAIL)){
					return "电子账单接收邮箱格式不正确";
				}*/
				if(StringUtils.isBlank(stmtMailAddrInd)){
					return "纸质账单寄送地址为空";
				}
			}
		}else {
			return "账单介质类型为空";
		}
		
		return "";
	}
	
	/**
	 * 如果证件类型为身份证或临时身份证，则校验身份证有效性, 性别，生日有效性
	 * 
	 * @param idType
	 * @param idNo
	 * @param gender
	 * @param birthday
	 */
	private static String checkId(String idType, String idNo, String gender, Date birthday) {
		if(StringUtils.isBlank(idType)){
			return "申请资料证件类型为空";
		}else if (IdType.I.name().equals(idType)) {
			if(StringUtils.isBlank(idNo)){
				return "申请资料证件号码为空";
			}else{
				// 证件号码有效性
				if (!IdentificationCodeUtil.isIdentityCode(idNo)) {
					return "申请资料证件号码无效";
				}
				// 性别有效性质
				if (StringUtils.isNotEmpty(gender) && !ApplyServiceValidityUtil.isGender(idNo, gender)) {
					return "申请资料性别有误";
				}
				// 生日有效性
				try {
					if (birthday != null && !ApplyServiceValidityUtil.isBirthday(idNo, birthday)) {
						return "申请资料生日有误";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "申请资料生日有误";
				}
			}
		}
		
		
		return "";
	}
//
//	public static String instalCheck(ApplyInfoDto applyInfoDto, T1005Req req,
//			CacheContext cacheContext, Logger logger) {
//		TmAppMain appMain = applyInfoDto.getTmAppMain();// 申请主表
//		if (appMain == null) {
//			return "该申请资料缺失";
//		}
//		TmAppAudit appAudit = applyInfoDto.getTmAppAudit();
//		TmAppInstalLoan appInstalLoan = applyInfoDto.getTmAppInstalLoan();
//		if(appAudit!=null && StringUtils.equals(appAudit.getIsInstalment(), Indicator.Y.name())){
//			//申请分期信息，验证分期信息的有效性
//			String productCd = appMain.getProductCd();
//			TmProduct product = cacheContext.getProduct(productCd);
//			if(!Indicator.Y.name().equals(product.getIsInstalment()))
//				return "卡产品【"+productCd+"】不支持分期申请";
//			if(appInstalLoan == null)
//				return "申请分期，未填写分期申请信息";
//			
//			if(StringUtils.isBlank(appInstalLoan.getInstalActivityNo()) )
//				return "分期活动编号为空";
//			if(StringUtils.isBlank(appInstalLoan.getMccNo()))
//				return "分期商户号为空";
//			if(appInstalLoan.getCashAmt() == null)
//				return "分期借款金额为空";
//			if(appInstalLoan.getCashAmt().compareTo(BigDecimal.ZERO) == 0)
//				return "分期借款金额为0";
//			if(appInstalLoan.getLoanInitTerm() == null)
//				return "分期期数为空";
//			if(!Indicator.Y.name().equals(product.getIfUseCustomRate())){
//				if(StringUtils.isNotBlank(appInstalLoan.getLoanFeeCalcMethod())
//						|| StringUtils.isNotBlank(appInstalLoan.getLoanFeeMethod())
//						|| appInstalLoan.getAppFeeAmt()  != null
//						|| appInstalLoan.getAppFeeRate() != null)
//					return "产品【"+productCd+"】不支持自定义手续费收取";
//			}
//			if(StringUtils.isNotBlank(appInstalLoan.getLoanFeeCalcMethod())){
//				if(appInstalLoan.getLoanFeeCalcMethod().equals("A") && appInstalLoan.getAppFeeAmt()  == null)
//					return "手续费按固定金额收取，手续费固定金额为空";
//				if(appInstalLoan.getLoanFeeCalcMethod().equals("R") && appInstalLoan.getAppFeeRate()  == null)
//					return "手续费按比例收取，手续费收取比例为空";
//				if(!appInstalLoan.getLoanFeeCalcMethod().equals("A") && !appInstalLoan.getLoanFeeCalcMethod().equals("R"))
//					return "手续费计算方式填写不正确";
//				if(appInstalLoan.getAppFeeAmt()  != null && appInstalLoan.getAppFeeRate() != null)
//					return "手续费收取比例与固定金额只能填写一个";
//			}
//			if(StringUtils.isNotBlank(appInstalLoan.getLoanFeeMethod())){
//				try {
//					LoanFeeMethod.valueOf(appInstalLoan.getLoanFeeMethod());
//				} catch (Exception e) {
//					return "手续费收取方式填写不正确";
//				}
//			}
//			List<TmAppInstalProgram> appInstalProgramList = cacheContext.getInstalProgramByProductCd(productCd);
//			TmAppInstalProgram appInstalProgram = null;
//			for(TmAppInstalProgram program:appInstalProgramList){
//				if(appInstalLoan.getInstalActivityNo().equals(program.getProgramId()) && "A".equals(program.getProgramStatus())){
//					appInstalProgram = program;
//					break;
//				}
//			}
//			if(appInstalProgram == null)
//				return "该产品不支持分期活动【"+appInstalLoan.getInstalActivityNo()+"】";
//			Date statrDate = appInstalProgram.getProgramStartDate();
//			Date endDate= appInstalProgram.getProgramEndDate();
//			Date nowDate = new Date();
//			if(!(DateUtils.truncatedCompareTo(nowDate, statrDate, Calendar.DAY_OF_MONTH) >= 0 
//					&& DateUtils.truncatedCompareTo(nowDate, endDate, Calendar.DAY_OF_MONTH) <= 0))
//				return "分期活动生效日期不在允许范围内";
//			BigDecimal maxAmtActivity = appInstalProgram.getProgramMaxAmount();
//			BigDecimal minAmtActivity = appInstalProgram.getProgramMinAmount();
//			BigDecimal cashAmt = appInstalLoan.getCashAmt();
//			if(maxAmtActivity != null && cashAmt.compareTo(maxAmtActivity) > 0)
//				return "分期申请金额超过活动允许最大申请金额";
//			if(minAmtActivity != null && cashAmt.compareTo(minAmtActivity) < 0)
//				return "分期申请金额小于活动允许最小申请金额";
//			
//			List<TmAppInstalProgramTerms> appInstalProgramTermList = cacheContext.getTmAppInstalProgramTermByProgramId(appInstalProgram.getProgramId());
//			TmAppInstalProgramTerms appInstalProgramTerm = null;
//			for(TmAppInstalProgramTerms terms : appInstalProgramTermList){
//				if(appInstalLoan.getLoanInitTerm().intValue() == terms.getTerm().intValue()){
//					appInstalProgramTerm = terms;
//					break;
//				}
//			}
//			if(appInstalProgramTerm == null)
//				return "申请分期期数不支持";
//			BigDecimal maxTermAmt = appInstalProgramTerm.getTermMaxAmt();
//			BigDecimal minTermAmt = appInstalProgramTerm.getTermMinAmt();
//			if(maxTermAmt != null && cashAmt.compareTo(maxTermAmt) > 0)
//				return "分期申请金额超过该期数允许最大申请金额";
//			if(minTermAmt != null && cashAmt.compareTo(minTermAmt) < 0)
//				return "分期申请金额小于该期数允许最小申请金额";
//			
//			List<TmAppInstalProgramMerchant> appInstalProgramMerchantList = cacheContext.getTmAppInstalProgramMerchantByProgramId(appInstalProgram.getProgramId());
//			TmAppInstalProgramMerchant appInstalProgramMerchant = null;
//			for(TmAppInstalProgramMerchant programMerchant : appInstalProgramMerchantList){
//				if(appInstalLoan.getMccNo().equals(programMerchant.getMerId())){
//					appInstalProgramMerchant = programMerchant;
//					break;
//				}
//			}
//			if(appInstalProgramMerchant == null)
//				return "该分期活动不支持商户号【"+appInstalLoan.getMccNo()+"】";
//			
//			TmAppInstalMerchant merchant = cacheContext.getTmAppInstalMerchantByMerId(appInstalLoan.getMccNo());
//			if(merchant == null)
//				return "商户【"+appInstalLoan.getMccNo()+"】信息不存在";
//			//校验商户可用额度
//			BigDecimal usedAmt = (merchant.getFinishAuditLmt()==null?BigDecimal.ZERO:merchant.getFinishAuditLmt())
//					.add(merchant.getInAuditLmt()==null?BigDecimal.ZERO:merchant.getInAuditLmt())
//					.add(merchant.getPostingLmt()==null?BigDecimal.ZERO:merchant.getPostingLmt());
//			BigDecimal merLmt = merchant.getMerLmt()==null?BigDecimal.ZERO:merchant.getMerLmt();
//			logger.info("分期申请[{}]，商户额度检验，商户号[{}]，商户授信额度：{}，商户已用信额度:{}=审批中金额:{}+每日审批通过金额:{}+已入账未出账单金额(当日之前):{}，申请金额：{}",
//					appMain.getAppNo(),merchant.getMerId(),merLmt,usedAmt,merchant.getInAuditLmt(),merchant.getFinishAuditLmt(),merchant.getPostingLmt(),cashAmt);
//			if(merLmt.subtract(usedAmt).compareTo(cashAmt) < 0){
//				logger.info("商户可用额度不足");
//				return "商户可用额度不足";
//			}
//				
//		}else {
//			if(appInstalLoan != null){
//				if(StringUtils.isNotBlank(appInstalLoan.getInstalActivityNo()) 
//						|| StringUtils.isNotBlank(appInstalLoan.getMccNo())
//						|| StringUtils.isNotBlank(appInstalLoan.getLoanFeeCalcMethod())
//						|| StringUtils.isNotBlank(appInstalLoan.getLoanFeeMethod())
//						|| appInstalLoan.getLoanInitTerm() != null
//						|| (appInstalLoan.getCashAmt() != null 
//							&& appInstalLoan.getCashAmt().compareTo(new BigDecimal(1))>-1)
//						|| appInstalLoan.getAppFeeAmt() != null
//						|| appInstalLoan.getAppFeeRate() != null)
//					return "填写分期信息，但未申请分期";
//			}
//		}
//		return "";
//	}

}
