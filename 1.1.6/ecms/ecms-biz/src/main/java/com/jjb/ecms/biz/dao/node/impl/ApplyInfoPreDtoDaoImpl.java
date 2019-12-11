
package com.jjb.ecms.biz.dao.node.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.node.ApplyInfoPreDtoDao;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;


/**
 * @Description: 预录入申请信息列表
 * @author JYData-R&D-Big T.T
 * @date 2016年9月1日 上午9:26:29
 * @version V1.0  
 */
@Repository("applyInfoPreDtoDao")
public class ApplyInfoPreDtoDaoImpl extends AbstractBaseDao<ApplyInfoPreDto> implements ApplyInfoPreDtoDao {

	private static String selectAll = "com.jjb.ecms.biz.ApplyInfoPreDtoMapper.selectAll";
	
	@Override
	public Page<ApplyInfoPreDto> getApplyInfoPreDtoPage(Page<ApplyInfoPreDto> page) {
		
		// TODO Auto-generated method stub
		
		Page<ApplyInfoPreDto> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}

	@Override
	public List<ApplyInfoPreDto> getApplyInfoPreDtoList(ApplyInfoPreDto applyInfoPreDto) {
		// TODO Auto-generated method stub
		List<ApplyInfoPreDto> applyInfoPreDtoList = queryForList(selectAll, applyInfoPreDto);
		
		return applyInfoPreDtoList;
	}
}
