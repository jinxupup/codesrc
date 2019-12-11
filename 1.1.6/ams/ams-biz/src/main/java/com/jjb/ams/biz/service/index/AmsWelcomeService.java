package com.jjb.ams.biz.service.index;

import java.util.List;

import com.jjb.ecms.facility.dto.IndexWorkCountDto;

/**
 * 首页工作量统计
 * @author BIG.LU.KL
 *
 */
public interface AmsWelcomeService {

	public List<IndexWorkCountDto> getWorkCount();

	public List<IndexWorkCountDto> getWorkUntreatedCount();
}
