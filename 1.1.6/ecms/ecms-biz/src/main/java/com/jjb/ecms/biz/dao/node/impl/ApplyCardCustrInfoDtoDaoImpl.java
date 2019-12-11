
package com.jjb.ecms.biz.dao.node.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.node.ApplyCardCustrInfoDtoDao;
import com.jjb.ecms.facility.dto.ApplyCardCustrInfoDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @Description: 客户、账户和已发卡信息关联查询
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:20:06
 * @version V1.0  
 */

@Repository("applyCardCustrInfoDtoDao")
public class ApplyCardCustrInfoDtoDaoImpl extends AbstractBaseDao<ApplyCardCustrInfoDto> implements ApplyCardCustrInfoDtoDao {

	private static final String selectAll = "com.jjb.ecms.biz.ApplyCardCustrInfoDtoMapper.selectAll";
	
	@Override
	public List<ApplyCardCustrInfoDto> getApplyCardCustrInfoDtoList(ApplyCardCustrInfoDto applyCardCustrInfoDto) {
		// TODO Auto-generated method stub
		
		List<ApplyCardCustrInfoDto> list = queryForList(selectAll, applyCardCustrInfoDto);
		
		return list;
	}

	
}
