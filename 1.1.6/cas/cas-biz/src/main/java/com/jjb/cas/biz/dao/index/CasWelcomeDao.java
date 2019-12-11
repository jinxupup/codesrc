package com.jjb.cas.biz.dao.index;

import java.util.List;

import com.jjb.ecms.facility.dto.IndexWorkCountDto;

/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
public interface CasWelcomeDao {

	public List<IndexWorkCountDto> getWorkCount();

	public List<IndexWorkCountDto> getWorkUntreatedCount();
}
