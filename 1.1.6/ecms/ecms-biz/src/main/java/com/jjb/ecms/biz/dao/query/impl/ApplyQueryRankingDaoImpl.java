package com.jjb.ecms.biz.dao.query.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.query.ApplyQueryRankingDao;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyQueryRankingDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @description 根据类型查询渠道端进件排行榜
 * @author hn
 * @date 2016年9月1日16:52:43
 */

@Repository("applyQueryRankingDao")
public class ApplyQueryRankingDaoImpl extends AbstractBaseDao<ApplyQueryRankingDto> implements ApplyQueryRankingDao {


	public static final String  selectRanking = "com.jjb.ecms.biz.ApplyQueryRankingMapper.selectRanking";//查询该人未审批结束的申请件

	public static final String  selectSpreadNum = "com.jjb.ecms.biz.ApplyQueryRankingMapper.selectSpreadNum";//当前推广人推广数量

	public static final String  selectSuccApprovalNum = "com.jjb.ecms.biz.ApplyQueryRankingMapper.selectSuccApprovalNum";//当前推广人已核卡数量
	public static final String  selectSuccEffNum = "com.jjb.ecms.biz.ApplyQueryRankingMapper.selectSuccEffNum";//当前推广人有效核卡数量
	public static final String  selectSpreSum = "com.jjb.ecms.biz.ApplyQueryRankingMapper.selectSpreSum";//汇总当前推广人各个状态总数量
	public static final String  selectSpreSumNew = "com.jjb.ecms.biz.ApplyQueryRankingMapper.selectSpreSumNew";//针对05渠道汇总当前推广人各个状态总数量

	
	
	
	/**
	 * 排行榜查询
	 */
	@Override
	public List<ApplyQueryRankingDto> queryRankingList(ApplyQueryRankingDto applyQueryRankingDto) {
		// 客户经理确认
		if (AppConstant.ONE.equals(applyQueryRankingDto.getQuyType())) {
			applyQueryRankingDto.setAppSource("02");
		}
		// 合伙人(已有卡客户)排名
		if (AppConstant.TWO.equals(applyQueryRankingDto.getQuyType())) {
			applyQueryRankingDto.setAppSource("05");
		}
		if (null == applyQueryRankingDto.getTotalCnt()) {
			applyQueryRankingDto.setTotalCnt(AppConstant.FIFTY);

		}
		return queryForList(selectRanking, applyQueryRankingDto);
	}

	/**
	 * 推广数量查询 版本1
	 */
	@Override
	public ApplyQueryRankingDto querySpreadNum(ApplyQueryRankingDto applyQueryRankingDto) {
		Map<String, Object> params = new HashMap<>();
		params.put("spreaderNo",applyQueryRankingDto.getSpreaderNo());
		params.put("appSource",applyQueryRankingDto.getAppSource());
		ApplyQueryRankingDto rankingDto = new ApplyQueryRankingDto();
//		ApplyQueryRankingDto allSpreCnt =  queryForOne(selectSpreadNum,params);
//		rankingDto.setSpreadNum(allSpreCnt.getSpreadNum());
		ApplyQueryRankingDto succCnt = queryForOne(selectSuccApprovalNum,params);
		if(succCnt!=null) {
			rankingDto.setSuccApprovalCnt(succCnt.getSuccApprovalCnt());
		}
		ApplyQueryRankingDto succEffCnt = queryForOne(selectSuccEffNum,params);
		if(succEffCnt!=null) {
			rankingDto.setSuccEffCnt(succEffCnt.getSuccEffCnt());
		}
		
		return rankingDto;
	}

    /**
     * 	是否有效推广查询
     * @param applyQueryRankingDto
     * @return
     */
	@Override
	public ApplyQueryRankingDto querySpreadSuccEff(Map<String, Object> map) {
		if(map==null || map.size()==0) {
			return null;
		}
		return queryForOne(selectSuccEffNum,map);
	}

	/**
	 * 推广数量查询 版本2
	 */
	@Override
	public ApplyQueryRankingDto querySpreSum(ApplyQueryRankingDto applyQueryRankingDto) {
		Map<String, Object> params = new HashMap<>();
		params.put("spreaderNo",applyQueryRankingDto.getSpreaderNo());
		params.put("appSource",applyQueryRankingDto.getAppSource());

		params.put("startDate",applyQueryRankingDto.getStartDate());
		params.put("endDate",applyQueryRankingDto.getEndDate());
//		ApplyQueryRankingDto allSpreCnt =  queryForOne(selectSpreadNum,params);
//		rankingDto.setSpreadNum(allSpreCnt.getSpreadNum());
		ApplyQueryRankingDto succCnt = queryForOne(selectSpreSum,params);
		return succCnt;
	}

	/**
	 * 推广数量查询 10渠道
	 */
	@Override
	public ApplyQueryRankingDto querySpreSumNew(ApplyQueryRankingDto applyQueryRankingDto){
		Map<String, Object> params = new HashMap<>();
		params.put("spreaderNo",applyQueryRankingDto.getSpreaderNo());
		params.put("appSource",applyQueryRankingDto.getAppSource());

		params.put("startDate",applyQueryRankingDto.getStartDate());
		params.put("endDate",applyQueryRankingDto.getEndDate());
		ApplyQueryRankingDto succCnt = queryForOne(selectSpreSumNew,params);
		return succCnt;
	}
}
