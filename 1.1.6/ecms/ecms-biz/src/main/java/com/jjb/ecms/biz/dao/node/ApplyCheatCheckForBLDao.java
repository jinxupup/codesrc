package com.jjb.ecms.biz.dao.node;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyBlackListDetailDto;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @description: 欺诈调查(黑名单专用)DAO
 * @author -BigZ.Y
 * @date 2016年9月11日 下午5:21:13 
 */
public interface ApplyCheatCheckForBLDao extends BaseDao<ApplyBlackListDetailDto> {

	/**
	 * 怀疑个人身份黑名单
	 * @param map
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListName(Map<String, Object> parameter);
	/**
	 * 怀疑个人移动电话黑名单
	 * @param cellPhone
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListCellPhone(String cellPhone);
	/**
	 * 怀疑个人家庭电话黑名单
	 * @param homePhone
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListHomePhone(String homePhone);
	/**
	 * 怀疑公司电话黑名单
	 * @param empPhone
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListEmpPhone(String empPhone);
	/**
	 * 怀疑家庭地址黑名单
	 * @param homeAdd
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListHomeAdd(String homeAdd);
	/**
	 * 怀疑公司地址黑名单
	 * @param empAdd
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListEmpAdd(String empAdd);
	/**
	 * 怀疑个人所在公司黑名单
	 * @param CorpName
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListCorpName(String corpName);
	/**
	 * 怀疑个人电子邮箱黑名单
	 * @param email
	 * @return
	 */
	List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListEmail(String email);
}
