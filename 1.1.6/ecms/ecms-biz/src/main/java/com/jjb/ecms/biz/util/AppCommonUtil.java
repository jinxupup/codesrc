package com.jjb.ecms.biz.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.access.service.AccessParamService;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.util.ApplyInfoValidityUtil;
import com.jjb.ecms.util.ChineseToPinYin;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 工作流自动节点-数据处理公用方法
 * @author JYData-R&D-Big Star
 * @date 2016年9月8日 上午10:02:54
 * @version V1.0  
 */
@Component
public class AppCommonUtil extends CodeMarkUtils implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AppCommonUtil.class);
	@Autowired
	private AccessParamService accessParamService;
	@Autowired
	private CacheContext cacheContext;



	public static String processTemplate(String template, Map<String, Object> params){
		StringBuffer sb = new StringBuffer();
		Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
		while (m.find()) {
			String param = m.group();
			Object value = params.get(param.substring(2, param.length() - 1));
			m.appendReplacement(sb, value==null ? "" : value.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 插入历史信息
	 */
	public static TmAppHistory insertApplyHist(String appNo
			, String operId, RtfState rtfState
			, String refuseReason, String remark) {
		
		TmAppHistory tmAppHistory = new TmAppHistory();
		tmAppHistory.setOrg(OrganizationContextHolder.getOrg());
		tmAppHistory.setAppNo(appNo);
		
		tmAppHistory.setProNum(appNo);
		if(StringUtils.isNotEmpty(remark) && remark.length()>200) {
			remark = remark.substring(0, 199);
		}
		tmAppHistory.setRemark(remark);
		if(rtfState!=null){
			tmAppHistory.setRtfState(rtfState.name()); // 设置申请状态
			tmAppHistory.setProName(rtfState.taskName);
		}
		tmAppHistory.setRefuseCode(refuseReason);// 拒绝原因
		if(StringUtils.isEmpty(operId)){
			operId = OrganizationContextHolder.getUserNo();
			if(StringUtils.isEmpty(operId)){
				operId = AppConstant.SYS_AUTO;
			}
		}
		tmAppHistory.setOperatorId(operId); // 操作员ID
		tmAppHistory.setCreateDate(new Date()); // 日期
		
		return tmAppHistory;
	}
	/**
	 * 反射
	 * 
	 * @param ruleEngObjs
	 * @param iterm
	 * @return
	 */
	/*public Object getMethod(ApplyNodeRuleEngineObj ruleEngObjs, String item) {

		Object outPut = null;
		try {
			Class clazz = ruleEngObjs.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(item, clazz);
			Method getMethod = pd.getReadMethod(); // 获得get方法
			outPut = getMethod.invoke(ruleEngObjs); // 执行get方法返回一个Object

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return outPut;
	}*/
	/**
	 * 转换BigDecimal
	 * @param value
	 * @return
	 */
	/*public static BigDecimal toBigDecimal(String value) {
		
		return StringUtils.stringToBigDecimal(value);
	}*/
	
	/**
	 * 将历史信息中主申请人信息拷贝到附属卡实体类中
	 * @param hisApplyInfoDto
	 * @param applyInfoDto
	 * @return
	 */
	public static TmAppCustInfo setAttachAppData(ApplyInfoDto hisApplyInfoDto, ApplyInfoDto applyInfoDto) {
		TmAppCustInfo histCust = hisApplyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_B_1);
		TmAppCustInfo attachCust = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S_1);//获取第一张附卡信息
				
		attachCust.setAppNo(applyInfoDto.getAppNo());
		String name = attachCust.getName();
		String embLogo = ChineseToPinYin.getFullSpell(name);
		attachCust.setEmbLogo(embLogo);//根据姓名自动填充姓名拼音
		String idType = attachCust.getIdType(); //获得证件类型
 		String idNo = attachCust.getIdNo();	//获得证件号码
		if(StringUtils.isNotEmpty(idNo) && StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name())){
			//计算生日
			attachCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
			//计算性别
			attachCust.setGender(IdentificationCodeUtil.getGender(idNo));
			//计算年龄
			attachCust.setAge(IdentificationCodeUtil.getAge(idNo));
		}else {
			attachCust.setBirthday(null);
			attachCust.setGender(null);
			attachCust.setAge(null);
		}
		attachCust.setIfSelectedCard(null);//初始化自选卡号
		attachCust.setCardNo(null);
		attachCust.setMaritalStatus(histCust.getMaritalStatus());
//		attachCust.setPrOfCountry(histCust.getPrOfCountry());
		attachCust.setResidencyCountryCd(histCust.getResidencyCountryCd());
		attachCust.setNationality(histCust.getNationality());
		attachCust.setQualification(histCust.getQualification());
		attachCust.setIdIssuerAddress(histCust.getIdIssuerAddress());
		attachCust.setYearIncome(histCust.getYearIncome());
		attachCust.setEmail(histCust.getEmail());
//		attachCust.setHomeStandFrom(histCust.getHomeStandFrom());
		attachCust.setBankmemFlag(histCust.getBankmemFlag());
		attachCust.setBankmemberNo(histCust.getBankmemberNo());
		attachCust.setCorpName(histCust.getCorpName());
		attachCust.setEmpAddrCtryCd(histCust.getEmpAddrCtryCd());
		attachCust.setEmpProvince(histCust.getEmpProvince());
		attachCust.setEmpCity(histCust.getEmpCity());
		attachCust.setEmpZone(histCust.getEmpZone());
		attachCust.setEmpStructure(histCust.getEmpStructure());
		attachCust.setEmpType(histCust.getEmpType());
		attachCust.setEmpAdd(histCust.getEmpAdd());
		attachCust.setEmpPhone(histCust.getEmpPhone());
		attachCust.setEmpPostcode(histCust.getEmpPostcode());
//		attachCust.setEmpStandFrom(histCust.getEmpStandFrom());
//		attachCust.setCorpFax(histCust.getCorpFax());
		attachCust.setEmpDepartment(histCust.getEmpDepartment());
		attachCust.setOccupation(histCust.getOccupation());
		attachCust.setTitleOfTechnical(histCust.getTitleOfTechnical());
		attachCust.setEmpPost(histCust.getEmpPost());
		attachCust.setPhotoUseFlag(histCust.getPhotoUseFlag());
		attachCust.setPosPinVerifyInd(histCust.getPosPinVerifyInd());
//		attachCust.setEmpStatus(histCust.getEmpStatus());
//		attachCust.setEmpStability(histCust.getEmpStability());
		attachCust.setHomeAddrCtryCd(histCust.getHomeAddrCtryCd());
		attachCust.setHomeState(histCust.getHomeState());
		attachCust.setHomeCity(histCust.getHomeCity());
		attachCust.setHomeZone(histCust.getHomeZone());
		attachCust.setHomePostcode(histCust.getHomePostcode());
		attachCust.setHomeAdd(histCust.getHomeAdd());
		attachCust.setHomePhone(histCust.getHomePhone());
//		attachCust.setFamilyAvgeVenue(histCust.getFamilyAvgeVenue());
		attachCust.setRaiseNum(histCust.getRaiseNum());
		attachCust.setJobGrade(histCust.getJobGrade());
//		attachCust.setOldCorpName(histCust.getOldCorpName());
		attachCust.setSmAmtVerifyInd(histCust.getSmAmtVerifyInd());
				
		return attachCust;
	}
	/**
	 * 将历史数据中附属卡数据拷贝到主卡申请人中
	 * @param hisApplyInfoDto
	 * @param applyInfoDto
	 * @return
	 */
	public static TmAppCustInfo setPrimAppData(ApplyInfoDto hisApplyInfoDto, ApplyInfoDto applyInfoDto) {
		TmAppCustInfo hisAttach = hisApplyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S_1);//获取第一张附卡信息
		TmAppCustInfo primCust = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S_1);
		primCust.setAppNo(applyInfoDto.getAppNo());
		String name = primCust.getName();
		String embLogo = ChineseToPinYin.getFullSpell(name);
		primCust.setEmbLogo(embLogo);//根据姓名自动填充姓名拼音
		String idType = primCust.getIdType(); //获得证件类型
 		String idNo = primCust.getIdNo();	//获得证件号码
		if(StringUtils.isNotEmpty(idNo) && StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name())){
			//计算生日
			primCust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
			//计算性别
			primCust.setGender(IdentificationCodeUtil.getGender(idNo));
			//计算年龄
			primCust.setAge(IdentificationCodeUtil.getAge(idNo));
		}else {
			primCust.setBirthday(null);
			primCust.setGender(null);
			primCust.setAge(null);
		}
		primCust.setIfSelectedCard(null);//初始化自选卡号
		primCust.setCardNo(null);
		primCust.setMaritalStatus(hisAttach.getMaritalStatus());
//		primCust.setPrOfCountry(hisAttach.getPrOfCountry());
		primCust.setResidencyCountryCd(hisAttach.getResidencyCountryCd());
		primCust.setNationality(hisAttach.getNationality());
		primCust.setQualification(hisAttach.getQualification());
		primCust.setIdIssuerAddress(hisAttach.getIdIssuerAddress());
		primCust.setYearIncome(hisAttach.getYearIncome());
		primCust.setEmail(hisAttach.getEmail());
//		primCust.setHomeStandFrom(hisAttach.getHomeStandFrom());
		primCust.setBankmemFlag(hisAttach.getBankmemFlag());
		primCust.setBankmemberNo(hisAttach.getBankmemberNo());
		primCust.setCorpName(hisAttach.getCorpName());
		primCust.setEmpAddrCtryCd(hisAttach.getEmpAddrCtryCd());
		primCust.setEmpProvince(hisAttach.getEmpProvince());
		primCust.setEmpCity(hisAttach.getEmpCity());
		primCust.setEmpZone(hisAttach.getEmpZone());
		primCust.setEmpStructure(hisAttach.getEmpStructure());
		primCust.setEmpType(hisAttach.getEmpType());
		primCust.setEmpAdd(hisAttach.getEmpAdd());
		primCust.setEmpPhone(hisAttach.getEmpPhone());
		primCust.setEmpPostcode(hisAttach.getEmpPostcode());
//		primCust.setEmpStandFrom(hisAttach.getEmpStandFrom());
//		primCust.setCorpFax(hisAttach.getCorpFax());
		primCust.setEmpDepartment(hisAttach.getEmpDepartment());
		primCust.setOccupation(hisAttach.getOccupation());
		primCust.setTitleOfTechnical(hisAttach.getTitleOfTechnical());
		primCust.setEmpPost(hisAttach.getEmpPost());
		primCust.setPhotoUseFlag(hisAttach.getPhotoUseFlag());
		primCust.setPosPinVerifyInd(hisAttach.getPosPinVerifyInd());
//		primCust.setEmpStatus(hisAttach.getEmpStatus());
//		primCust.setEmpStability(hisAttach.getEmpStability());
		primCust.setHomeAddrCtryCd(hisAttach.getHomeAddrCtryCd());
		primCust.setHomeState(hisAttach.getHomeState());
		primCust.setHomeCity(hisAttach.getHomeCity());
		primCust.setHomeZone(hisAttach.getHomeZone());
		primCust.setHomePostcode(hisAttach.getHomePostcode());
		primCust.setHomeAdd(hisAttach.getHomeAdd());
		primCust.setHomePhone(hisAttach.getHomePhone());
//		primCust.setFamilyAvgeVenue(hisAttach.getFamilyAvgeVenue());
		primCust.setRaiseNum(hisAttach.getRaiseNum());
		primCust.setJobGrade(hisAttach.getJobGrade());
//		primCust.setOldCorpName(hisAttach.getOldCorpName());
		primCust.setSmAmtVerifyInd(hisAttach.getSmAmtVerifyInd());
		return primCust;
	}
	/**
	 * 过滤联系人map对象中联系人实体类对象是否为null
	 * @param tmAppContactInfoMap
	 * @param tmAppContactList
	 * @return
	 */
	public static Map<String, TmAppContact> validAppContactEntityIsNull(
			Map<String, TmAppContact> tmAppContactInfoMap,
			List<TmAppContact> tmAppContactList) {
		if(tmAppContactInfoMap==null){
			tmAppContactInfoMap = new HashMap<String, TmAppContact>();
		}
		if(CollectionUtils.sizeGtZero(tmAppContactList)){
			for (int j=0 ;j<2 ;) {
				TmAppContact tmAppContact = tmAppContactList.get(j);
				Field[] fields = tmAppContact.getClass().getDeclaredFields();
				int m = 1;
				for (int f = 0; f < fields.length; f++) {
					fields[f].setAccessible(true);
					try {
						if(fields[f].getName().equals(AppConstant.SERIAL_VERSION_UID)){ //判断是不是序列化标示id
							continue;
						}
						else{
							Object fieldValue = fields[f].get(tmAppContact);
							if(fieldValue != null){
								break;
							}
							else{
								m++;
							}
						}
					} catch (IllegalArgumentException e) {
						logger.error("validAppContactEntityIsNull-异常", e);
					} catch (IllegalAccessException e) {
						logger.error("validAppContactEntityIsNull-异常", e);
					}
				}
				if(m == fields.length){
					tmAppContact = null;
				}
				j++;
				if(tmAppContact != null){
					tmAppContact.setContactType(StringUtils.valueOf(j));
					tmAppContactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+j, tmAppContact);
				}
			}
		}
		return tmAppContactInfoMap;
	}
	
	/**
	 * 设置节点公共数据
	 * @param tmAppMain
	 * @return
	 */
	public static ApplyNodeCommonData setApplyNodeCommonData(ApplyNodeCommonData applyNodeCommonData, TmAppMain tmAppMain) {
		
		if(tmAppMain==null || StringUtils.isEmpty(tmAppMain.getAppNo())){
			return applyNodeCommonData;
		}
		if(applyNodeCommonData==null){
			applyNodeCommonData = new ApplyNodeCommonData();
		}
		applyNodeCommonData.setOrg(OrganizationContextHolder.getOrg());
		applyNodeCommonData.setAppNo(tmAppMain.getAppNo());
		applyNodeCommonData.setAppType(tmAppMain.getAppType());
		applyNodeCommonData.setProductCd(tmAppMain.getProductCd());
		applyNodeCommonData.setName(tmAppMain.getName());
		applyNodeCommonData.setIdType(tmAppMain.getIdType());
		applyNodeCommonData.setIdNo(tmAppMain.getIdNo());
		applyNodeCommonData.setRtfStateType(tmAppMain.getRtfState());
		if(StringUtils.concat(tmAppMain.getAppProperty(), "K")) {
			applyNodeCommonData.setApproveQuickFlag("Y");
		}else {
			applyNodeCommonData.setApproveQuickFlag("N");
		}
		applyNodeCommonData.setAppProperty(tmAppMain.getAppProperty());
		applyNodeCommonData.setApplyStatus(tmAppMain.getCurrOpResult());
		applyNodeCommonData.setDate(new Date());
		applyNodeCommonData.setOperatorId(OrganizationContextHolder.getUserNo()==null ? tmAppMain.getUpdateUser():OrganizationContextHolder.getUserNo());
		applyNodeCommonData.setRejectReason(tmAppMain.getRefuseCode());

		return applyNodeCommonData;
	}

	/**
	 * 性别处理
	 * @param appNo
	 * @param genderStr1
	 * @param idNo
	 * @return
	 */
	public static Gender getGender(String appNo,String genderStr1,String idNo) {
		Gender gender = null;
		if(StringUtils.isNotEmpty(genderStr1)){
			try {
				gender = Gender.valueOf(genderStr1);
			} catch (Exception e) {
				logger.error("现有表中客户性别["+genderStr1+"]转换异常,需通过身份证重新转换"+LogPrintUtils.printAppNoLog(appNo, null,null));
				String genderStr = IdentificationCodeUtil.getGender(idNo);
				try {
					gender = Gender.valueOf(genderStr);
				} catch (Exception e1) {
					logger.error("现有表中客户性别["+idNo+"]转换异常,没办法了...."+LogPrintUtils.printAppNoLog(appNo, null,null));
				}
			}
		}else{
			String genderStr = IdentificationCodeUtil.getGender(idNo);
			try {
				gender = Gender.valueOf(genderStr);
			} catch (Exception e1) {
				logger.error("现有表中客户性别["+idNo+"]转换异常,没办法了...."+LogPrintUtils.printAppNoLog(appNo, null,null));
			}
		}
		return gender;
	}
	/**
	 * days天数后
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date DateAfter(Date date, Integer days) {

		Date returnDate = null;
		SimpleDateFormat dateFormart = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_YEAR, days);//日期加n天
		
        try {
			returnDate = dateFormart.parse(dateFormart.format(rightNow.getTime()));
		} catch (Exception e) {
			logger.error("日期转换失败 returnDate返回["+returnDate+"]"+e.getMessage());
		}
        
		return returnDate;
	}
	
	/**
	 * 封装备注数据
	 * @param appMemoList
	 * @return 
	 */
	public static Map<String, TmAppMemo> setTmAppMemoInfoLast(List<TmAppMemo> appMemoList) {
		Map<String,TmAppMemo> tmAppMemoMap = new HashMap<String, TmAppMemo>();
		if(CollectionUtils.isNotEmpty(appMemoList)){
			for (int i = 0; i < appMemoList.size(); i++) {
				TmAppMemo appMemo2 = appMemoList.get(i);
				String key = appMemo2.getMemoType()+"-"+appMemo2.getTaskKey();
				if(tmAppMemoMap.containsKey(key)){
					TmAppMemo appMemo3 = tmAppMemoMap.get(key);
					if(appMemo3.getMemoVersion()<appMemo2.getMemoVersion()){
						tmAppMemoMap.put(key, appMemo2);
					}
				}else{
					tmAppMemoMap.put(key, appMemo2);
				}
			}
		}
		return tmAppMemoMap;
	}
	/**
	 * CAS初始化节点操作权限
	 * @return
	 */
	public static List<String> initNodeAuthListCas() {
		List<String> nodeAuthList = new ArrayList<String>();//存放节点操作权限
/*		nodeAuthList.add(EcmsAuthority.AMS_APPLY_MODIFY.name());//修改
		nodeAuthList.add(EcmsAuthority.AMS_APPLY_REVIEW.name());//复核*/
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PERSONCHECK.name());//人工核查
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_BASIC_CHECK.name());//初审
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());//补件
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_FINALAUDIT.name());//终审
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_TEL_SURVEY.name());//电调
		/*nodeAuthList.add(EcmsAuthority.APPLYINFO_CREDIT_CHECK.name());*///征询查询审批
		return nodeAuthList;
	}
	/**
	 * 初始化节点操作权限
	 * @return
	 */
	public static List<String> initNodeAuthList(){
		List<String> nodeAuthList = new ArrayList<String>();//存放节点操作权限
		nodeAuthList.add(EcmsAuthority.AMS_APPLY_MODIFY.name());//修改
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PERSONCHECK.name());//人工核查
		nodeAuthList.add(EcmsAuthority.AMS_APPLY_REVIEW.name());//复核
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_BASIC_CHECK.name());//初审
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());//补件
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_FINALAUDIT.name());//终审
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_TEL_SURVEY.name());//电调
		/*nodeAuthList.add(EcmsAuthority.APPLYINFO_CREDIT_CHECK.name());*///征询查询审批
		return nodeAuthList;
	}
	/**
	 * AMS初始化节点操作权限
	 * @return
	 */
	public static List<String> initNodeAuthListAms(){
		List<String> nodeAuthList = new ArrayList<String>();//存放节点操作权限
		nodeAuthList.add(EcmsAuthority.AMS_APPLY_MODIFY.name());//修改
/*
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PERSONCHECK.name());//人工核查
*/
		nodeAuthList.add(EcmsAuthority.AMS_APPLY_REVIEW.name());//复核
/*		nodeAuthList.add(EcmsAuthority.CAS_APPLY_BASIC_CHECK.name());//初审
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());//补件
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_FINALAUDIT.name());//终审
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_TEL_SURVEY.name());//电调*/
		/*nodeAuthList.add(EcmsAuthority.APPLYINFO_CREDIT_CHECK.name());*///征询查询审批
		return nodeAuthList;
	}
	/**
	 * 初始化节点操作权限
	 * @return
	 */
	public static List<String> initNodeAuthListC(){
		List<String> nodeAuthList = new ArrayList<String>();//存放节点操作权限
		nodeAuthList.add(EcmsAuthority.CAS_APPLY_PRE_CHECK.name());//预审
		/*nodeAuthList.add(EcmsAuthority.APPLYINFO_CREDIT_CHECK.name());*///征询查询审批
		return nodeAuthList;
	}
	/**
	 * String转换成枚举类
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T stringToEnum(Class enums,String str,Enum<T> defT){
		T ss = null;
		if(str==null || str.trim().equals("") || str.equals("null")){
			if(defT!=null){
				return (T)defT;
				
			}else{
				return null;
				
			}
			
		}
		try {
			ss = (T) Enum.valueOf(enums, str);
		} catch (Exception e) {
			logger.debug("字符串["+str+"]转化成枚举类["+enums.getClass().getName()+"]失败，错误信息["+e.getMessage()+"]");
		}
		return ss;
	}


	public static String checkType(String inputValue,String fault) {
		if (inputValue != null) {
			return inputValue;
		} else if (inputValue == null) {
			if (fault == null) {
				return inputValue;
			} else {
				return fault;
			}
		}
		return null;
	}



	/**
	 * 处理空字符串
	 * @param src
	 * @return
	 */
	public static String nvl(String src) {
		return nvl(src, "");
	}
	
	/**
	 * 处理空字符串-替换
	 * @param src
	 * @return
	 */
	public static String nvl(String src, String replaceStr) {
		return null == src ? replaceStr : src.trim();
	}
	
	public void setOrg(String org){
		if(StringUtils.isNotEmpty(org)){
			OrganizationContextHolder.setOrg(org);
		}
		if(StringUtils.isEmpty(OrganizationContextHolder.getOrg())){
			String orgId = accessParamService.getDefaultOrg();
			OrganizationContextHolder.setOrg(orgId);
		}
//		com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
	}
	/**
	 * 产生13位流水号（6位年月日 + 6位时分秒 + 1位随机数）
	 * @return
	 */
	public static String getSerialNumber(){
		return DateUtils.dateToString(new Date(), "yyMMddHHmmss") + new Random().nextInt(10);
	}
	/**
	 * 设置编码格式
	 * @param servId
	 * @param charsetName
	 */
	public static String[] setCharset(String servId, String charsetName) {
		String[] charset = null;
		if(StringUtils.isNotBlank(charsetName)){
			charset = charsetName.split("\\|");
		}
		if(charset == null || charset.length != 3){
			if(charset!=null && charset.length==1){
				charset = new String[] {charset[0],"UTF-8","UTF-8"};
			}else if(charset!=null && charset.length==2){
				charset = new String[] {charset[0],charset[1],"UTF-8"};
			}else{
				charset = new String[] {"UTF-8","UTF-8","UTF-8"};
			}
			logger.info(""+servId+"交易报文编码[{}]参数不正确,设置默认utf-8编码格式",charsetName);
		}
		logger.info("交易["+servId+"]交易报文编码格式为"+charset.toString());
		return charset;
	}
	

	/**
	 * 根据影像类型(字母+数字)获取中文描述
	 * @param picType
	 * @return
	 */
	public String getPatchBoltTypeEnumValueByCH(String picType) {
		if (StringUtils.isNotBlank(picType)) {
			//如果是附属卡的补件，则直接返回中文
			if(picType.contains("附卡-")){//搞不懂，为啥这里这么处理
				return picType;
			}
			Map<Object, Object> map = cacheContext.getAclDictByType("ApplyPatchBoltType");
			if(map!=null && map.size()>0 && map.containsKey(picType)){
				return StringUtils.valueOf(map.get(picType));
			}
		}
		return null;
	}
	/**
	 * 根据影像的中文描述获取枚举信息
	 * @param picCnName 影像的中文描述
	 * @return
	 */
	public String getPatchBoltTypeEnumKeyByValue(String picCnName) {
		String values = "";
		if (StringUtils.isNotBlank(picCnName)) {
			//如果是附属卡的补件，则直接返回中文
			if(picCnName.contains("附卡-")){//搞不懂，为啥这里这么处理
				return picCnName;
			}
			String [] ss = picCnName.split("\\|");
			if(ss!=null){
				Map<Object, Object> map = cacheContext.getAclDictByType("ApplyPatchBoltType");
				for (int i = 0; i < ss.length; i++) {
					String value = CollectionUtils.getMapKey(map, ss[i]);
					if(StringUtils.isNotEmpty(value)){
						if(StringUtils.isNotEmpty(values)){
							values = values+"|";
						}
						values = values+value;
					}
				}
			}
		}
		if(values.startsWith("|")){
			values = values.substring(1);
		}
		return values;
	}

	/**
	 * 字段校验项---联机进件、excel导入等调用
	 * 必输项（不包括联动必输项）、正则、区间、字符串长度
	 * 
	 * 返回 null或list
	 */
	public static List<ValidFieldInfoDto> validateFields(String appType, String productCd, CacheContext cacheContext) {
		if(StringUtils.isBlank(appType)){
			logger.error("联机进件必输配置项[申请类型]参数为空");
			throw new ProcessException("联机进件必输项配置的[申请类型]为空!");
		}
		if(StringUtils.isBlank(productCd)){
			logger.error("联机进件必输配置项[卡产品代码]参数为空");
			throw new ProcessException("联机进件必输配置项[卡产品代码]参数为空");
		}
		List<ValidFieldInfoDto> validFieldInfoDtos  = null;
		List<FieldProductDto> fieldProductDtoList = cacheContext.getFieldProductDtosByProductCd(productCd);
		if(CollectionUtils.sizeGtZero(fieldProductDtoList)){
			logger.info("=====>>开始配置必输项字段");
			validFieldInfoDtos = new ArrayList<ValidFieldInfoDto>();
			for (FieldProductDto fieldProductDto : fieldProductDtoList) {
				String field  = fieldProductDto.getFieldEn();
				if(StringUtils.isEmpty(field)){
					logger.info("字段["+fieldProductDto.getFieldName()+"]不存在");
				}else {
					String fieldName = fieldProductDto.getFieldName();
					String tabName = fieldProductDto.getTabName();
					String remark = fieldProductDto.getRemark();//联动的标志
					
					//过滤出有校验项的字段
					String requiredFlag = fieldProductDto.getIfRequiredItem();//必输项标志
					String regexp = fieldProductDto.getFieldRegexp();//正则
					String betweenMin = fieldProductDto.getBetweenMin();//区间最小值
					String betweenMax = fieldProductDto.getBetweenMax();//区间最大值
					String maxLength = fieldProductDto.getMaxLength();//字符串最大长度
					if((StringUtils.isNotBlank(requiredFlag) && requiredFlag.contains(appType)) || StringUtils.isNotBlank(regexp)
							|| (StringUtils.isNotBlank(betweenMin) && StringUtils.isNotBlank(betweenMax))
							|| StringUtils.isNotBlank(maxLength)){//找出有校验项
					
						ValidFieldInfoDto validFieldInfoDto = new ValidFieldInfoDto();						
						//字段名和名称处理
						if (AppConstant.TmAppContact.equals(tabName)) {
							String fieldRemark = fieldProductDto.getFieldRemark();
							if(StringUtils.isNotBlank(fieldRemark) && "1".equals(fieldRemark)) {//区分联系人1还是2(0:亲属；1：其他)
								StringBuilder newField = new StringBuilder ();
								newField.append("contactO");
								newField.append(ApplyInfoValidityUtil.toLowerCaseFirstOne(field.substring(7)));
								validFieldInfoDto.setField(newField.toString());//其他联系人必输项字段做转化处理
							}else {
								logger.debug("配置页面字段[field={}]区分联系人信息标志remark不存在",field);
							}
						}else if (AppConstant.TmAppCustInfo.equals(tabName)) {
//							validFieldInfoDto.setField(prefix + ApplyInfoValidityUtil.toUpperCaseFirstOne(field));//字段名前加前缀
							validFieldInfoDto.setField(field);//字段
							validFieldInfoDto.setFieldName("客户信息-"+fieldName);//字段名称
						}else {
							validFieldInfoDto.setField(field);//字段
							validFieldInfoDto.setFieldName(fieldName);//字段名称
						}
						
						//过滤出必输项（剔除联动部分）
						if(StringUtils.isNotBlank(requiredFlag) && !Indicator.N.name().equals(requiredFlag) && requiredFlag.contains(appType)){
							if(StringUtils.isNotEmpty(remark) && remark.contains("false")){//过滤掉联动必输项
								validFieldInfoDto.setNotEmptyFlag(false);//必输标志位
							}else {
								validFieldInfoDto.setNotEmptyFlag(true);//必输标志位
							}
						}
						
						//字符串正则、区间、长度检验
						String type = fieldProductDto.getComponentType();
						if(StringUtils.isNotEmpty(type) && "input".equals(type)){
							//正则
							if(StringUtils.isNotEmpty(regexp)){
								validFieldInfoDto.setRegexpFlag(true);
								validFieldInfoDto.setRegexp(regexp);
							}
							//区间
							if(StringUtils.isNotEmpty(betweenMin) && StringUtils.isNotEmpty(betweenMax)){
								validFieldInfoDto.setBetweenFlag(true);
								validFieldInfoDto.setBetweenMin(betweenMin);
								validFieldInfoDto.setBetweenMax(betweenMax);
							}
							//长度
							if(StringUtils.isNotEmpty(maxLength)){
								validFieldInfoDto.setLengthFlag(true);
								validFieldInfoDto.setLengthMax(maxLength);
							}
						}
						validFieldInfoDtos.add(validFieldInfoDto);
					}
				}
			}
			logger.info("=====>>必输项字段配置成功,校验字段共有[{}]个<<=====",validFieldInfoDtos==null?0:validFieldInfoDtos.size());
		}else {
			logger.info("未获取到该产品[{}]关联的配置字段",productCd);
			throw new ProcessException("未获取到该产品["+productCd+"关联的配置字段");
		}
		
		return validFieldInfoDtos;
	}
	/**
	 * 通过退回代码映射对应的申请件状态</br>
	 * B1：复核退回修改 - RFT_STATE:B15 </br>
	 * B2：初审退回修改 - RFT_STATE:F07 </br>
	 * B3：初审退回电调 - RFT_STATE:F11 </br>
	 * B4：初审退回核查 - RFT_STATE:F02 </br>
	 * B5：电调退回修改 - RFT_STATE:F19 </br>
	 * B6：电调退回初审 - RFT_STATE:F18 </br>
	 * B7：电调退回核查 - RFT_STATE: </br>
	 * B8：终审退回修改 - RFT_STATE: </br>
	 * B9：终审退回电调 - RFT_STATE:K08 </br>
	 * B10：终审退回初审 - RFT_STATE:K18 </br>
	 * B11：终审退回核查 - RFT_STATE: </br>
	 * B12：核查退回修改 - RFT_STATE:E15 </br>
	 * B13：初审退回预审 - RFT_STATE:F03 </br>
	 * @param pageOpRs
	 * @param rtfState
	 * @return
	 */
	public static RtfState setReturnRrtStateByPageOp(String pageOpRs,RtfState rtfState){

		if("B1".equals(pageOpRs)){
			rtfState = RtfState.B15;
		}else if ("B2".equals(pageOpRs)) {
			rtfState = RtfState.F07;
		}else if ("B3".equals(pageOpRs)) {
			rtfState = RtfState.F11;
		}else if ("B4".equals(pageOpRs)) {
			rtfState = RtfState.F02;
		}else if ("B5".equals(pageOpRs)) {
			rtfState = RtfState.F19;
		}else if ("B6".equals(pageOpRs)) {
			rtfState = RtfState.F18;
		}/*else if ("B7".equals(pageOpRs)) {
//			defState = RtfState.;
		}else if ("B8".equals(pageOpRs)) {
			defState = RtfState.;
		}*/else if ("B9".equals(pageOpRs)) {
			rtfState = RtfState.K08;
		}else if ("B10".equals(pageOpRs)) {
			rtfState = RtfState.K18;
		}/*else if ("B11".equals(pageOpRs)) {
			defState = RtfState.;
		}*/else if ("B12".equals(pageOpRs)) {
			rtfState = RtfState.E15;
		}else if ("B13".equals(pageOpRs)) {
			rtfState = RtfState.F03;
		}
		return rtfState;
	}
	/**
	 * 通过退回代码获取退回发生的任务节点
	 * @param pageOpRs
	 * @param defState
	 * @return
	 */
	public static String getReturnCnStateByCode(String returnCode){
		String returnDesc = null;
		
		if("B1".equals(returnCode)){
			returnDesc = RtfState.B15.taskName;
		}else if ("B2".equals(returnCode)) {
			returnDesc = RtfState.F07.taskName;
		}else if ("B3".equals(returnCode)) {
			returnDesc = RtfState.F11.taskName;
		}else if ("B4".equals(returnCode)) {
			returnDesc = RtfState.F02.taskName;
		}else if ("B5".equals(returnCode)) {
			returnDesc = RtfState.F19.taskName;
		}else if ("B6".equals(returnCode)) {
			returnDesc = RtfState.F18.taskName;
		}/*else if ("B7".equals(pageOpRs)) {
//			defState = RtfState.F02.taskName;
		}else if ("B8".equals(pageOpRs)) {
			defState = RtfState.F02.taskName;
		}*/else if ("B9".equals(returnCode)) {
			returnDesc = RtfState.K08.taskName;
		}else if ("B10".equals(returnCode)) {
			returnDesc = RtfState.K18.taskName;
		}/*else if ("B11".equals(pageOpRs)) {
			defState = RtfState.F02;
		}*/else if ("B12".equals(returnCode)) {
			returnDesc = RtfState.E15.taskName;
		}else if ("B13".equals(returnCode)) {
			returnDesc = RtfState.F03.taskName;
		}
		return returnDesc;
	}


}
