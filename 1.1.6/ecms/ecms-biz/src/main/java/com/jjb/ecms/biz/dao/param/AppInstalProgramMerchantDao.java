package com.jjb.ecms.biz.dao.param;

import com.jjb.ecms.facility.dto.AppInstalProgramMerchantDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface AppInstalProgramMerchantDao extends BaseDao<AppInstalProgramMerchantDto> {
	
	public Page<AppInstalProgramMerchantDto> getPage(Page<AppInstalProgramMerchantDto> page,AppInstalProgramMerchantDto appInstalProgramMerchantDto);



}
