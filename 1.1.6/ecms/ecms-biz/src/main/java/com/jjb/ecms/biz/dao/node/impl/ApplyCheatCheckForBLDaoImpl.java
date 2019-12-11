package com.jjb.ecms.biz.dao.node.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.node.ApplyCheatCheckForBLDao;
import com.jjb.ecms.facility.dto.ApplyBlackListDetailDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description: 欺诈信息（黑名单专用）impl
 * @author -BigZ.Y
 * @date 2016年9月11日 下午5:33:05 
 */
@Repository("applyCheatCheckForBlackDao")
public class ApplyCheatCheckForBLDaoImpl extends AbstractBaseDao<ApplyBlackListDetailDto> implements ApplyCheatCheckForBLDao {

	public static final String selectByBlackListName = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListName";//怀疑个人黑名单
	public static final String selectByBlackListCellPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListCellPhone";//怀疑个人电话黑名单
	public static final String selectByBlackListHomePhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListHomePhone";//怀疑个人家庭电话黑名单
	public static final String selectByBlackListEmpPhone = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListEmpPhone";//怀疑个人公司电话黑名单
	public static final String selectByBlackListHomeAdd = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListHomeAdd";//怀疑家庭地址黑名单
	public static final String selectByBlackListEmpAdd = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListEmpAdd";//怀疑公司地址黑名单
	public static final String selectByBlackListCorpName = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListCorpName";//怀疑公司名称黑名单
	public static final String selectByBlackListEmail = "com.jjb.ecms.biz.ApplyCheatDetailDto.selectByBlackListEmail";//怀疑电子邮箱黑名单
	
	
	/**
	 * 怀疑个人身份黑名单
	 * @param name
	 * @param idType
	 * @param idNo
	 * @param appNo
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListName(Map<String, Object> parameter) {
		if(parameter!=null && parameter.size()>0){
			return queryForList(selectByBlackListName, parameter);
		}
		return new ArrayList<ApplyBlackListDetailDto>();
	}

	/**
	 * 怀疑个人移动电话黑名单
	 * @param cellPhone
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListCellPhone(String cellPhone) {
		if (StringUtils.isEmpty(cellPhone)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cellPhone", cellPhone);
		
		return queryForList(selectByBlackListCellPhone, parameter);
	}

	/**
	 * 怀疑个人家庭电话黑名单
	 * @param homePhone
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListHomePhone(String homePhone) {
		if (StringUtils.isEmpty(homePhone)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("homePhone", homePhone);
		
		return queryForList(selectByBlackListHomePhone, parameter);
	}

	/**
	 * 怀疑公司电话黑名单
	 * @param empPhone
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListEmpPhone(String empPhone) {
		if (StringUtils.isEmpty(empPhone)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("empPhone", empPhone);
		
		return queryForList(selectByBlackListEmpPhone, parameter);
	}

	/**
	 * 怀疑家庭地址黑名单
	 * @param homeAdd
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListHomeAdd(String homeAdd) {
		if (StringUtils.isEmpty(homeAdd)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("homeAdd", homeAdd);
		
		return queryForList(selectByBlackListHomeAdd, parameter);
	}

	/**
	 * 怀疑公司地址黑名单
	 * @param empAdd
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListEmpAdd(String empAdd) {
		if (StringUtils.isEmpty(empAdd)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("empAdd", empAdd);
		return queryForList(selectByBlackListEmpAdd, parameter);
	}

	/**
	 * 怀疑个人所在公司黑名单
	 * @param corpName
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListCorpName(String corpName) {
		if (StringUtils.isEmpty(corpName)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("corpName", corpName);
		
		return queryForList(selectByBlackListCorpName, parameter);
	}
	
	/**
	 * 怀疑个人电子邮箱
	 * @param email
	 * @return
	 */
	@Override
	public List<ApplyBlackListDetailDto> getAppPrimApplicantInfoByBlackListEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("email", email);
		
		return queryForList(selectByBlackListEmail, parameter);
	}

}
