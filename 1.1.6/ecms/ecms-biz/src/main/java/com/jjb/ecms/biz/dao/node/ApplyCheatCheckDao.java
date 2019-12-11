package com.jjb.ecms.biz.dao.node;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.facility.dto.ApplyCheatDetailDto;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @description: 欺诈调查DAO
 * @author -BigZ.Y
 * @date 2016年9月11日 下午5:21:13 
 */
public interface ApplyCheatCheckDao extends BaseDao<ApplyCheatDetailDto> {

	/**
	 * 根据主卡申请人手机号查找
	 * 如果传客户证件过来则排除同一个人的
	 * @param cellPhone
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByCellPhone(String cellPhone ,String appNo,String idType,String idNo);
	/**
	 * 根据主卡申请人证件类型、号码查找
	 *  如果传 客户姓名过来则排除同一个人的
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @param name
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByIdTypeAndIdNo(String idType, String idNo ,String appNo,String name);
	
	/**
	 * 根据申请人家庭号码查找（排除证件号码）
	 * @param homePhone
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomePhone(String homePhone, String idType, String idNo ,String appNo);
	/**
	 * 根据申请人单位电话查找（排除证件号码）
	 * @param empPhone
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpPhone(String empPhone, String idType, String idNo ,String appNo);
	/**
	 * 单位电话对应多个单位名称
	 * 可以排除同证件的件
	 * @param empPhone
	 * @param corpName
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpPhoneForCorpName(String empPhone, String corpName,String appNo,String idType, String idNo);
	/**
	 * 根据家庭地址查找
	 * 可以排除同证件的件
	 * @param homeAdd
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomeAdd(String homeAdd, String appNo,String idType,String idNo);
	/**
	 * 根据单位地址查找
	 * 可以排除同证件的件
	 * @param empAdd
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpAdd(String empAdd, String appNo,String idType,String idNo);
	/**
	 * 半年内申请次数(包括所有状态)
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	public List<ApplyCheatDetailDto> getAppPrimApplicantInfoByApplyCreateDate(String idType, String idNo, String appNo);
	/**
	 * 半年内申请成功次数
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByApplySuccess(String idType, String idNo ,String appNo);
	/**
	 * 半年内申请失败次数
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByApplyFail(String idType, String idNo ,String appNo);
	/**
	 * 查找是否在征审系统中存在
	 * @param idType
	 * @param idNo
	 * @param productCd
	 * @param appNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoInApsSystem(Map<String, Object> map);
	
	/**
	 * 申请人联系人多人匹配(姓名、电话)
	 * 可以排除同证件的件
	 * @param tmAppContactMap
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByContactPhone(Map<String, TmAppContact> tmAppContactMap, String appNo,String idType, String idNo);


	/**
	 * 申请人邮箱多人匹配
	 * 可以排除同证件的件
	 * @param email
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmail(String email, String appNo,String idNo, String idType);
	
	/**
	 * 同一笔申请件，单位地址填写与住宅地址一致
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpAddHomeAdd(String appNo,String idNo,String idType);
	
	/**
	 * 申请人宅电对单电多人匹配
	 * 可以排除同证件的件
	 * @param homePhone
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomePhoneNEmpPhone(String appNo,String idType, String idNo, String homePhone);
	
	/**
	 * 申请人联系人姓名多人匹配（申请人联系人手机相同的情况下联系人姓名对应多个申请人的联系人姓名）（电话相同而姓名不同）
	 * 可以排除同证件的件
	 * @param tmAppContactInfoMap
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByContactName(Map<String,TmAppContact> tmAppContactInfoMap, String appNo,String idType, String idNo);

	/**
	 * 申请人单位电话和联系人(姓名，电话)多人匹配
	 * 可以排除同证件的件
	 * @param tmAppContactInfoMap
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @param empPhone
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByEmpPhoneAndContact(Map<String,TmAppContact> tmAppContactInfoMap, String empPhone, String appNo,String idType, String idNo);
	
	/**
	 * 申请人家庭电话和联系人(姓名，电话)多人匹配
	 * 可以排除同证件的件
	 * @param tmAppContactInfoMap
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @param homePhone
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByHomePhoneAndContact(Map<String,TmAppContact> tmAppContactInfoMap, String homePhone, String appNo,String idType, String idNo);
	
	/**
	 * 申请人邮寄住宅地址（不为空）多人匹配
	 * @param cardMailerInd
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByMailerInd(String cardMailerInd, String appNo,String idType, String idNo);
	
	/**
	 * 手机号连续7位与其他人的重复
	 * @param cellPhone
	 * @param appNo
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<ApplyCheatDetailDto> getAppPrimApplicantInfoByCellPhoneCellPhone7DigitDup(String cellPhone, String appNo, String idType, String idNo);
	
	
	/**
	 * 根据下列条件返回匹配到的申请件数量
	 * @param states 申请状态
	 * @param neStates 申请状态(排除)
	 * @param idType 证件类型
	 * @param idNo 证件号码
	 * @param appNo 申请编号(排除)
	 * @param neName 申请人姓名(排除)
	 * @param cellPhone 申请人手机号
	 * @param date 在此日期后的申请件
	 * @return
	 */
	Integer countByApplyBasicProps(Set<RtfState> states, Set<RtfState> neStates, String idType, String idNo, String appNo, String neName, String cellPhone, Date date);
}
