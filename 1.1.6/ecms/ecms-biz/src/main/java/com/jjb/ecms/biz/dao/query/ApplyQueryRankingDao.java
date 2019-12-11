package com.jjb.ecms.biz.dao.query;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyQueryRankingDto;
import com.jjb.unicorn.base.dao.BaseDao;

/**
  *@ClassName ApplyQueryRankingDao
  *@Description 根据类型查询渠道端进件排行榜
  *@Author lixing
  *Date 2018/12/24 16:30
  *Version 1.0
  */
public interface ApplyQueryRankingDao extends BaseDao<ApplyQueryRankingDto> {

	/**
	 * 	排行榜查询
	 * @param applyQueryRankingDto
	 * @return
	 */
    List<ApplyQueryRankingDto> queryRankingList(ApplyQueryRankingDto applyQueryRankingDto);

    /**
     * 	推广数量查询
     * @param applyQueryRankingDto
     * @return
     */
    ApplyQueryRankingDto querySpreadNum(ApplyQueryRankingDto applyQueryRankingDto);

    /**
     * 	是否有效推广查询
     * @param applyQueryRankingDto
     * @return
     */
    ApplyQueryRankingDto querySpreadSuccEff(Map<String,Object> map);
    
    /**
     * 	推广数量查询 版本2
     * @param applyQueryRankingDto
     * @return
     */
	public ApplyQueryRankingDto querySpreSum(ApplyQueryRankingDto applyQueryRankingDto);

    /**
     * 	推广数量查询 10渠道
     * @param applyQueryRankingDto
     * @return
     */
    public ApplyQueryRankingDto querySpreSumNew(ApplyQueryRankingDto applyQueryRankingDto);
}
