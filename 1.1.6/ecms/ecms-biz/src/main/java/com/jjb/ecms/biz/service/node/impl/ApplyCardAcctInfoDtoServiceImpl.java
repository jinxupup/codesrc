/**
 * 
 */
package com.jjb.ecms.biz.service.node.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.node.ApplyCardAcctInfoDtoDao;
import com.jjb.ecms.biz.service.node.ApplyCardAcctInfoDtoService;
import com.jjb.ecms.facility.dto.ApplyCardAcctInfoDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 客户、账户和已发卡信息关联查询
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:51:27
 * @version V1.0  
 */
@Service("applyCardAcctInfoDtoService")
public class ApplyCardAcctInfoDtoServiceImpl implements	ApplyCardAcctInfoDtoService {
	
	@Autowired
	public ApplyCardAcctInfoDtoDao applyCardAcctInfoDtoDao ;

	@Override
	@Transactional
	public Page<ApplyCardAcctInfoDto> getApplyCardAcctInfoDtoPage(Page<ApplyCardAcctInfoDto> page) {
		// TODO Auto-generated method stub
		Page<ApplyCardAcctInfoDto> p = applyCardAcctInfoDtoDao.getApplyCardAcctInfoDtoPage(page);
		
		return p;
	}

	/**
	 * 根据条件获取对象列表
	 * @param applyCardAcctInfoDto
	 * @return
	 */
	@Override
	@Transactional
	public List<ApplyCardAcctInfoDto> getApplyCardAcctInfoDtoList(ApplyCardAcctInfoDto applyCardAcctInfoDto) {
		// TODO Auto-generated method stub
		if(applyCardAcctInfoDto == null || StringUtils.isEmpty(applyCardAcctInfoDto.getIdNo())){
			return null;
		}
		applyCardAcctInfoDto.setOrg(OrganizationContextHolder.getOrg());
		List<ApplyCardAcctInfoDto> list = applyCardAcctInfoDtoDao.getApplyCardAcctInfoDtoList(applyCardAcctInfoDto);
		
		return list;
	}
}
