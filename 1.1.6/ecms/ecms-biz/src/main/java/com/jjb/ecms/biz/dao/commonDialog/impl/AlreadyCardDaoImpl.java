package com.jjb.ecms.biz.dao.commonDialog.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.commonDialog.AlreadyCardDao;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @description: 已有卡DAO实现类
 * @author -BigZ.Y
 * @date 2016年9月19日 上午11:04:25 
 */
@Repository("alreadyCardDao")
public class AlreadyCardDaoImpl extends AbstractBaseDao<AlreadyCardsCardInfoDto> implements AlreadyCardDao{

	public static final String selectAll = "com.jjb.ecms.biz.AlreadyCardsCardInfoDto.selectAll";//查找已有卡信息
	
	public static final String selectCardInfo = "com.jjb.ecms.biz.AlreadyCardsCardInfoDto.selectCardInfo";//查找已有卡信息

	/**
	 * 获取已有卡的数据
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@Override
	public List<AlreadyCardsCardInfoDto> getAlreadyCardsCardInfoDtos(
			String idType, String idNo) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("idNo", idNo);
		parameter.put("idType", idType);
		return queryForList(selectAll, parameter);
	}

	@Override
	public List<AlreadyCardsCardInfoDto> getCardInfo(Map<String,Object> param) {
		return queryForList(selectCardInfo, param);

	}
	
	

}
