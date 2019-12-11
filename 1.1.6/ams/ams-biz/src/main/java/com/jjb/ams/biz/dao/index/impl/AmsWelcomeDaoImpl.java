package com.jjb.ams.biz.dao.index.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ams.biz.dao.index.AmsWelcomeDao;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;

/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
@Repository("amsWelcomeDao")
public class AmsWelcomeDaoImpl extends AbstractBaseDao<IndexWorkCountDto> implements AmsWelcomeDao {

	@Override
	public List<IndexWorkCountDto> getWorkCount() {
		String operatorId = OrganizationContextHolder.getUserNo();
		IndexWorkCountDto indexWorkCountDto = new IndexWorkCountDto();
		indexWorkCountDto.setOperatorId(operatorId);
		return queryForList("com.jjb.ecms.biz.IndexWorkCountDto.selectAll", indexWorkCountDto);
	}

	@Override
	public List<IndexWorkCountDto> getWorkUntreatedCount() {
		String operatorId = OrganizationContextHolder.getUserNo();
		IndexWorkCountDto indexWorkCountDto = new IndexWorkCountDto();
		indexWorkCountDto.setOperatorId(operatorId);
		return queryForList("com.jjb.ecms.biz.IndexWorkCountDto.selectWorkUntreatedCount", indexWorkCountDto);
	}

}
