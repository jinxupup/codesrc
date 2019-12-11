package com.jjb.ecms.biz.service.commonDialog;

import java.util.List;

import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;

/**
 * @description: 已有卡弹窗service
 * @author -BigZ.Y
 * @date 2016年9月19日 上午10:13:59 
 */
public interface AlreadyCardService {

	/**
	 * 获取所有的已有卡信息
	 * @param appNo
	 * @return
	 */
	List<AlreadyCardsCardInfoDto> getAlreadyCardsCardInfoDtos(String appNo);
	
	public AlreadyCardsCardInfoDto getCardInfo(String appNo,String org,String cardNo,String applyType);
}
