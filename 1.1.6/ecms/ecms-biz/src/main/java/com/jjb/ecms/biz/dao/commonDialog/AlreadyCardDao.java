package com.jjb.ecms.biz.dao.commonDialog;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;

/**
 * @description: 已有卡DAO
 * @author -BigZ.Y
 * @date 2016年9月19日 上午11:02:11 
 */
public interface AlreadyCardDao {

	/**
	 * 获取已有卡的数据
	 * @param idType
	 * @param idNo
	 * @return
	 */
	List<AlreadyCardsCardInfoDto> getAlreadyCardsCardInfoDtos(String idType,String idNo);
	
	List<AlreadyCardsCardInfoDto> getCardInfo(Map<String,Object> param);

}
