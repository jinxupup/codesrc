package com.jjb.ecms.biz.service.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.manage.ApplyTaskCountDao;
import com.jjb.ecms.biz.service.manage.ApplyTaskCountService;
import com.jjb.ecms.facility.dto.ApplyTaskCountDto;
import com.jjb.unicorn.facility.model.Page;

/**
  * @Description: 申请工作量查询
  * @author JYData-R&D-L.L
  * @date 2016年9月2日 下午2:48:50
  * @version V1.0
 */
@Service("applyTaskCountService")
public class ApplyTaskCountServiceImpl implements ApplyTaskCountService {

	@Autowired
    ApplyTaskCountDao applyTaskCountDao;
	
	/**
	 * 查询申请工作量
	 * @param page 
	 * @return
	 */
	@Override
	@Transactional
	public Page<ApplyTaskCountDto> getTaskCountList(Page<ApplyTaskCountDto> page) {
		
		return applyTaskCountDao.getTaskCountList(page);
	}
	
}
