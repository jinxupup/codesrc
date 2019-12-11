
package com.jjb.ecms.biz.dao.node.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.node.ApplyCardAcctInfoDtoDao;
import com.jjb.ecms.facility.dto.ApplyCardAcctInfoDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 客户、账户和已发卡信息关联查询
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:20:06
 * @version V1.0  
 */

@Repository("applyCardAcctInfoDtoDao")
public class ApplyCardAcctInfoDtoDaoImpl extends AbstractBaseDao<ApplyCardAcctInfoDto> implements ApplyCardAcctInfoDtoDao {

	private static final String selectAll = "com.jjb.ecms.biz.ApplyCardAcctInfoDtoMapper.selectAll";
	
	@Override
	public Page<ApplyCardAcctInfoDto> getApplyCardAcctInfoDtoPage(Page<ApplyCardAcctInfoDto> page) {
		// TODO Auto-generated method stub
		Page<ApplyCardAcctInfoDto> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}

	@Override
	public List<ApplyCardAcctInfoDto> getApplyCardAcctInfoDtoList(ApplyCardAcctInfoDto applyCardAcctInfoDto) {
		// TODO Auto-generated method stub
		List<ApplyCardAcctInfoDto> list = queryForList(applyCardAcctInfoDto);
		
		return list;
	}
}
