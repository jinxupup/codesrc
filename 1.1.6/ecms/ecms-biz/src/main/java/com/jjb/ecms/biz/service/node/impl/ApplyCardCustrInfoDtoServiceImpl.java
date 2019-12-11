/**
 * 
 */
package com.jjb.ecms.biz.service.node.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.node.ApplyCardCustrInfoDtoDao;
import com.jjb.ecms.biz.service.node.ApplyCardCustrInfoDtoService;
import com.jjb.ecms.facility.dto.ApplyCardCustrInfoDto;

/**
 * @Description: 客户、账户和已发卡信息关联查询
 * @author JYData-R&D-Big T.T
 * @date 2016年9月2日 上午9:51:27
 * @version V1.0  
 */
@Service("applyCardCustrInfoDtoService")
public class ApplyCardCustrInfoDtoServiceImpl implements ApplyCardCustrInfoDtoService {
	
	@Autowired
	public ApplyCardCustrInfoDtoDao applyCardCustrInfoDtoDao ;

	@Override
	@Transactional
	public List<ApplyCardCustrInfoDto> getApplyCardCustrInfoDtoList(ApplyCardCustrInfoDto applyCardCustrInfoDto) {
		// TODO Auto-generated method stub
		List<ApplyCardCustrInfoDto> pList = applyCardCustrInfoDtoDao.getApplyCardCustrInfoDtoList(applyCardCustrInfoDto);
		
		return pList;
	}

}
