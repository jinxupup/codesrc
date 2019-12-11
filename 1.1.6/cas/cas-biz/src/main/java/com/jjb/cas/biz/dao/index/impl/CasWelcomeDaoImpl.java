package com.jjb.cas.biz.dao.index.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.cas.biz.dao.index.CasWelcomeDao;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;

/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
@Repository("casWelcomeDao")
public class CasWelcomeDaoImpl extends AbstractBaseDao<IndexWorkCountDto> implements CasWelcomeDao {

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
