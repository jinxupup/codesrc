package com.jjb.ecms.biz.dao.node.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.dao.node.ApplyCheatCheckDao;
import com.jjb.ecms.facility.dto.ApplyCheatDetailDto;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description: TODO
 * @author -BigZ.Y
 * @date 2016年9月11日 下午5:33:05 
 */
@Repository("applyCheatCheckDao")
public class ApplyCheatCheckDaoImpl extends AbstractBaseDao<ApplyCheatDetailDto> implements ApplyCheatCheckDao {

	public static final String selectByCellPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByCellPhone";//按主卡申请人手机号查询
	public static final String selectByIdTypeAndIdNo = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByIdTypeAndIdNo";//按主卡申请人证件查询
	public static final String selectByHomePhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByHomePhone";//按主卡申请人家庭电话查询
	public static final String selectByEmpPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByEmpPhone";//按主卡申请人单位电话查询（单位电话对应多个申请人）
	public static final String selectByEmpPhoneForApp = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByEmpPhoneForApp";//单位电话对应多个单位名称
	public static final String selectByHomeAdd = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByHomeAdd";//按主卡申请人家庭地址查询
	public static final String selectByEmpAdd = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByEmpAdd";//按主卡申请人单位地址查询
	public static final String selectByApplyCreateDate = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByApplyCreateDate";//半年内申请次数(包括各种状态)
	public static final String selectByApplySuccess = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByApplySuccess";//半年内申请成功次数
	public static final String selectByApplyFail = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByApplyFail";//半年内申请失败次数
	public static final String selectByInApsSystem = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByInApsSystem";//征审系统中存在
	public static final String selectByContactPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByContactPhone";//联系人多人匹配
	public static final String selectByBlackListName = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListName";//怀疑个人黑名单
	public static final String selectByBlackListCellPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListCellPhone";//怀疑个人手机黑名单
	public static final String selectByEmail = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByEmail";//申请人邮箱多人匹配
	public static final String selectByEmpAddHomeAdd = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByEmpAddHomeAdd";//同一笔申请件，单位地址填写与住宅地址一致
	public static final String selectByHomePhoneNEmpPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByHomePhoneNEmpPhone";//申请人宅电对单电多人匹配
	public static final String selectByContactName= "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByContactName";//申请人联系人姓名多人匹配
	public static final String selectByMailerInd= "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByMailerInd";//申请人邮寄住宅地址（不为空）多人匹配
	public static final String selectByEmpPhoneAndContact = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByEmpPhoneAndContact";//同一单电联系人电话对应多人匹配 
	public static final String selectByHomePhoneAndContact = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByHomePhoneAndContact";//同一宅电联系人电话对应多人匹配
	public static final String selectByCellPhone7DigitDup = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByCellPhone7DigitDup";//申请人手机号连续7位与其他人的重复
	public static final String countByApplyBasicProps = "com.jjb.ecms.biz.ApplyCheatDetailDto.countByApplyBasicProps";//根据申请基本属性查询已存在申请数量
	
	/**
	 *根据主卡申请人手机号查找
	 * @param cellPhone
	 * @param idType
	 * @param idNo
	 * @return 
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByCellPhone(String cellPhone ,String appNo,String idType,String idNo) {
		if(StringUtils.isEmpty(cellPhone)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cellPhone", cellPhone);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType_ne", idType);
		}
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo_ne", idNo);
		}
		return queryForList(selectByCellPhone, parameter);
	}

	
	/**
	 * 根据主卡申请人证件类型、号码查找
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @param name
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByIdTypeAndIdNo(String idType, String idNo, String appNo,String name) {
		if(StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("idNo", idNo);
		parameter.put("idType", idType);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		if(StringUtils.isNotEmpty(name)){
			parameter.put("name_ne", name);
		}
		return queryForList(selectByIdTypeAndIdNo, parameter);
	}

	/**
	 * 根据申请人家庭号码查找（排除证件号码）
	 * @param homePhone
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomePhone(String homePhone, String idType, String idNo, String appNo) {
		if(StringUtils.isEmpty(homePhone)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		
		parameter.put("homePhone", homePhone);
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType", idType);
		}
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo", idNo);
		}
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		
		return queryForList(selectByHomePhone, parameter);
	}

	/**
	 * 半年内申请成功次数
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByApplySuccess(String idType, String idNo, String appNo) {
		if(StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)){
			return null;
		}
		//获取半年时间点
		Date date = DateUtils.getNextDay(new Date(), -180);
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("idNo", idNo);
		parameter.put("idType", idType);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		parameter.put("date", date);
		
		return queryForList(selectByApplySuccess, parameter);
	}
	
	/**
	 * 半年内申请次数(包括所有状态)
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByApplyCreateDate(String idType, String idNo, String appNo) {
		if(StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)){
			return null;
		}
		//获取半年时间点
		Date date = DateUtils.getNextDay(new Date(), -180);
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("idNo", idNo);
		parameter.put("idType", idType);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		parameter.put("date", date);
		
		return queryForList(selectByApplyCreateDate, parameter);
	}
	
	/**
	 * 半年内申请失败次数
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByApplyFail(String idType, String idNo, String appNo) {
		if(StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)){
			return null;
		}
		//获取半年时间点
		Date date = DateUtils.getNextDay(new Date(), -180);
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("idNo", idNo);
		parameter.put("idType", idType);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		parameter.put("date", date);
		
		return queryForList(selectByApplyFail, parameter);
	}
	

	/**
	 * 查找是否在征审系统中存在
	 * @param idType
	 * @param idNo
	 * @param productCd
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoInApsSystem(Map<String, Object> map) {
		if(map==null || !map.containsKey("appType")){
			return null;
		}
		return queryForList(selectByInApsSystem, map);
	}

	/**
	 * 申请人联系人多人匹配(姓名、电话)
	 * @param tmAppContactMap
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByContactPhone(Map<String, TmAppContact> tmAppContactMap,
			String appNo, String idType,String idNo) {
		// TODO Auto-generated method stub
		List<ApplyCheatDetailDto> listAll =  new ArrayList<ApplyCheatDetailDto>();
		
		//定义一个map，避免重复联系人查询
		Map<String, String> contactMap = new HashMap<String, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(tmAppContactMap==null){
			return listAll;
		}
		for(TmAppContact entry : tmAppContactMap.values()){
			if(!(contactMap.containsKey(entry.getContactName()) && contactMap.containsValue(entry.getContactMobile()))
					&& StringUtils.isNotBlank(entry.getContactName())
					&& StringUtils.isNotBlank(entry.getContactMobile())){
				
				contactMap.put(entry.getContactName(), entry.getContactMobile());//如果临时map中没有这个联系人，那就放进去，并且去执行sql；
				//遍历联系人
				parameter = new HashMap<String, Object>();
				parameter.put("contactName", entry.getContactName());
				parameter.put("contactMobile", entry.getContactMobile());
				parameter.put("appNo", appNo);
				parameter.put("idType_ne", idType);
				parameter.put("idNo_ne", idNo);
				
				List<ApplyCheatDetailDto> list = queryForList(selectByContactPhone, parameter);
				if(list != null ){
					listAll.addAll(list);//把查询的结果放在里面
				}
			}
		}
		return listAll;
	}

	/**
	 * 根据家庭地址查找
	 * @param homeAdd
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomeAdd(String homeAdd, String appNo,String idType,String idNo) {
		if(StringUtils.isEmpty(homeAdd)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("homeAdd", homeAdd);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType_ne", idType);
		}
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo_ne", idNo);
		}
		return queryForList(selectByHomeAdd, parameter);
	}

	/**
	 * 根据单位地址查找
	 * @param empAdd
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpAdd(String empAdd, String appNo,String idType,String idNo) {
		if(StringUtils.isEmpty(empAdd)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("empAdd", empAdd);
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType_ne", idType);
		}
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo_ne", idNo);
		}
		return queryForList(selectByEmpAdd, parameter);
	}

	/**
	 * 根据申请人单位电话查找（排除证件号码）
	 * @param empPhone
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpPhone(String empPhone, String idType, String idNo, String appNo) {
		if(StringUtils.isEmpty(empPhone)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("empPhone", empPhone);
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo", idNo);
		}
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType", idType);
		}
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		return queryForList(selectByEmpPhone, parameter);
	}

	/**
	 * 单位电话对应多个单位名称
	 * @param empPhone
	 * @param corpName
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpPhoneForCorpName(String empPhone, String corpName, String appNo,String idType,String idNo) {
		if(StringUtils.isEmpty(empPhone) || StringUtils.isEmpty(corpName)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("empPhone", empPhone);
		parameter.put("corpName_ne", corpName);
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo", idNo);
		}
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType", idType);
		}
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		return queryForList(selectByEmpPhone, parameter);
	}
	
	/**
	 * 申请人邮箱多人匹配
	 * @param email
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmail(String email, String appNo,String idNo, String idType){
		if(StringUtils.isEmpty(email)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("email", email);
		if(StringUtils.isNotEmpty(idNo)){
			parameter.put("idNo", idNo);
		}
		if(StringUtils.isNotEmpty(idType)){
			parameter.put("idType", idType);
		}
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		
		return queryForList(selectByEmail, parameter);
	}

	/**
	 * 同一笔申请件，单位地址填写与住宅地址一致
	 * @param empAdd
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @param homeAdd
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpAddHomeAdd(String appNo, String idNo, String idType) {
		if(StringUtils.isEmpty(idNo) || StringUtils.isEmpty(idType)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		parameter.put("idType", idType);
		parameter.put("idNo", idNo);
		
		return queryForList(selectByEmpAddHomeAdd, parameter);
	}

	/**
	 * 申请人宅电对单电多人匹配
	 * 可以排除同证件的件
	 * @param homePhone
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @param empPhone
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomePhoneNEmpPhone(String appNo, String idType, String idNo, String homePhone) {
		if(StringUtils.isEmpty(idNo) || StringUtils.isEmpty(idType) || StringUtils.isEmpty(homePhone)){
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(appNo)){
			parameter.put("appNo", appNo);
		}
		parameter.put("idType", idType);
		parameter.put("idNo", idNo);
		parameter.put("homePhone", homePhone);
		
		return queryForList(selectByHomePhoneNEmpPhone, parameter);
	}

	/**
	 * 申请人联系人姓名多人匹配（申请人联系人手机相同的情况下联系人姓名对应多个申请人的联系人姓名）（电话匹配而姓名不匹配）
	 * 可以排除同证件的件
	 * @param tmAppContactInfoMap
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByContactName(
			Map<String, TmAppContact> tmAppContactInfoMap,
			String appNo, String idType, String idNo) {
		List<ApplyCheatDetailDto> listAll =  new ArrayList<ApplyCheatDetailDto>();
		
		//定义一个map，避免重复联系人查询
		Map<String, String> contactMap = new HashMap<String, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(tmAppContactInfoMap==null){
			return listAll;
		}
		for(TmAppContact entry : tmAppContactInfoMap.values()){
			if(!(contactMap.containsKey(entry.getContactName()) && contactMap.containsValue(entry.getContactMobile()))
					&& StringUtils.isNotBlank(entry.getContactName())
					&& StringUtils.isNotBlank(entry.getContactMobile())){
				
				contactMap.put(entry.getContactName(), entry.getContactMobile());//如果临时map中没有这个联系人，那就放进去，并且去执行sql；
				//遍历联系人
				parameter = new HashMap<String, Object>();
				parameter.put("contactName", entry.getContactName());
				parameter.put("contactMobile", entry.getContactMobile());
				parameter.put("appNo", appNo);
				parameter.put("idType_ne", idType);
				parameter.put("idNo_ne", idNo);
				
				List<ApplyCheatDetailDto> list = queryForList(selectByContactName, parameter);
				if(list != null ){
					listAll.addAll(list);//把查询的结果放在里面
				}
			}
		}
		return listAll;
	}

	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpPhoneAndContact(
			Map<String, TmAppContact> tmAppContactInfoMap,
			String empPhone, String appNo, String idType, String idNo) {
		List<ApplyCheatDetailDto> listAll =  new ArrayList<ApplyCheatDetailDto>();
		
		//定义一个map，避免重复联系人查询
		Map<String, String> contactMap = new HashMap<String, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(tmAppContactInfoMap==null){
			return listAll;
		}
		for(TmAppContact entry : tmAppContactInfoMap.values()){
			if(!(contactMap.containsKey(entry.getContactName()) && contactMap.containsValue(entry.getContactMobile()))
					&& StringUtils.isNotBlank(entry.getContactName())
					&& StringUtils.isNotBlank(entry.getContactMobile())){
				
				contactMap.put(entry.getContactName(), entry.getContactMobile());//如果临时map中没有这个联系人，那就放进去，并且去执行sql；
				parameter = new HashMap<String, Object>();
				parameter.put("contactName", entry.getContactName());
				parameter.put("contactMobile", entry.getContactMobile());
				parameter.put("empPhone", empPhone);
				parameter.put("appNo", appNo);
				parameter.put("idType_ne", idType);
				parameter.put("idNo_ne", idNo);
				
				List<ApplyCheatDetailDto> list = queryForList(selectByEmpPhoneAndContact, parameter);
				if(list != null ){
					listAll.addAll(list);
				}
			}
		}
		return listAll;
	}

	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomePhoneAndContact(
			Map<String, TmAppContact> tmAppContactInfoMap,
			String homePhone, String appNo, String idType, String idNo) {
		List<ApplyCheatDetailDto> listAll =  new ArrayList<ApplyCheatDetailDto>();
		
		//定义一个map，避免重复联系人查询
		Map<String, String> contactMap = new HashMap<String, String>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(tmAppContactInfoMap==null){
			return listAll;
		}
		for(TmAppContact entry : tmAppContactInfoMap.values()){
			if(!(contactMap.containsKey(entry.getContactName()) && contactMap.containsValue(entry.getContactMobile()))
					&& StringUtils.isNotBlank(entry.getContactName())
					&& StringUtils.isNotBlank(entry.getContactMobile())){
				
				contactMap.put(entry.getContactName(), entry.getContactMobile());//如果临时map中没有这个联系人，那就放进去，并且去执行sql；
				parameter = new HashMap<String, Object>();
				parameter.put("contactName", entry.getContactName());
				parameter.put("contactMobile", entry.getContactMobile());
				parameter.put("homePhone", homePhone);
				parameter.put("appNo", appNo);
				parameter.put("idType_ne", idType);
				parameter.put("idNo_ne", idNo);
				
				List<ApplyCheatDetailDto> list = queryForList(selectByHomePhoneAndContact, parameter);
				if(list != null ){
					listAll.addAll(list);
				}
			}
		}
		return listAll;
	}


	/* 
	 * 申请人邮寄住宅地址（不为空）多人匹配
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByMailerInd(String cardMailerInd, String appNo, String idType, String idNo) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("appNo", appNo);
		parameter.put("idType", idType);
		parameter.put("idNo", idNo);
		parameter.put("cardMailerInd", cardMailerInd);
		
		return queryForList(selectByMailerInd, parameter);
	}


	/* 
	 * 手机号连续7位与其他人的重复
	 */
	@Override
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByCellPhoneCellPhone7DigitDup(String cellPhone, String appNo, String idType, String idNo) {
		if(StringUtils.isNotEmpty(cellPhone) && cellPhone.length() == 11){
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("appNo", appNo);
			parameter.put("idType_ne", idType);
			parameter.put("idNo_ne", idNo);
			for (int i = 0; i < 5; i++) {
				parameter.put("cellPhone", cellPhone.substring(i, i+7));
				List<ApplyCheatDetailDto> list = queryForList(selectByCellPhone7DigitDup, parameter);
				if(CollectionUtils.sizeGtZero(list)){
					return list;
				}
			}
		}
		
		return null;
	}

	@Override
	public Integer countByApplyBasicProps(Set<RtfState> states, Set<RtfState> neStates, String idType, String idNo, String appNo, String neName,
                                          String cellPhone, Date date) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("states", states);
		parameter.put("neStates", neStates);
		parameter.put("neName", neName);
		parameter.put("appNo", appNo);
		parameter.put("idType", idType);
		parameter.put("idNo", idNo);
		parameter.put("cellPhone", cellPhone);
		parameter.put("date", date);
		return queryForOne(countByApplyBasicProps, parameter);
	}

}
