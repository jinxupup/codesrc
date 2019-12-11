package com.jjb.ecms.biz.dao.param.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.AppInstalProgramMerchantDao;
import com.jjb.ecms.facility.dto.AppInstalProgramMerchantDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;



@Repository("AppInstalProgramMerchantDao")
public class AppInstalProgramMerchantDaoImpl extends AbstractBaseDao<AppInstalProgramMerchantDto> implements AppInstalProgramMerchantDao {

	public static final String selectMain = "com.jjb.ecms.biz.InstalProgramMerchantMapper.selectAll";
	
	@Override
	public Page<AppInstalProgramMerchantDto> getPage(Page<AppInstalProgramMerchantDto> page,AppInstalProgramMerchantDto appInstalProgramMerchantDto) {
		return super.queryForPageList(selectMain, page.getQuery(), page);
	}


}
