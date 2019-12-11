package com.jjb.ecms.biz.service.node.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.node.ApplyInfoPreDtoDao;
import com.jjb.ecms.biz.service.node.ApplyInfoPreDtoService;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 预录入申请信息列表
 * @author JYData-R&D-Big T.T
 * @date 2016年9月1日 上午9:13:45
 * @version V1.0  
 */

@Service("applyInfoPreDtoService")
public class ApplyInfoPreDtoServiceImpl implements ApplyInfoPreDtoService {

	@Autowired
	public ApplyInfoPreDtoDao applyInfoPreDtoDao;
	
	@Override
	@Transactional
	public Page<ApplyInfoPreDto> getApplyInfoPreDtoPage(Page<ApplyInfoPreDto> page) {
		// TODO Auto-generated method stub
		
		page = applyInfoPreDtoDao.getApplyInfoPreDtoPage(page);
		
		return page;
	}

	@Override
	@Transactional
	public List<ApplyInfoPreDto> getApplyInfoPreDtoList(ApplyInfoPreDto applyInfoPreDto) {
		// TODO Auto-generated method stub
		List<ApplyInfoPreDto> applyInfoPreDtoList = applyInfoPreDtoDao.getApplyInfoPreDtoList(applyInfoPreDto);
		
		return applyInfoPreDtoList;
	}
}
