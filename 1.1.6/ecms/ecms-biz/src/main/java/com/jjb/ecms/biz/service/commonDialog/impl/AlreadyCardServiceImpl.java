package com.jjb.ecms.biz.service.commonDialog.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.ecms.biz.dao.commonDialog.AlreadyCardDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.commonDialog.AlreadyCardService;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description: 已有卡弹窗实现类
 * @author -BigZ.Y
 * @date 2016年9月19日 上午10:16:17 
 */
@Service("alreadyCardService")
public class AlreadyCardServiceImpl implements AlreadyCardService {

	@Autowired
	private ApplyQueryService applyQueryService;
	
	@Autowired
	private AlreadyCardDao alreadyCardDao;
	
	/**
	 * 获取所有的已有卡信息
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public List<AlreadyCardsCardInfoDto> getAlreadyCardsCardInfoDtos(String appNo) {
		List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = new ArrayList<AlreadyCardsCardInfoDto>();
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
		if(tmAppMain==null){
			return alreadyCardsCardInfoDtos;
		}
		String idType = tmAppMain.getIdType();
		String idNo = tmAppMain.getIdNo();
		if(StringUtils.isNotBlank(idType) && StringUtils.isNotBlank(idNo)){
			//查找已有卡信息
			alreadyCardsCardInfoDtos = alreadyCardDao.getAlreadyCardsCardInfoDtos(idType, idNo);
		}
		return alreadyCardsCardInfoDtos;
	}

	@Override
	@Transactional
	public AlreadyCardsCardInfoDto getCardInfo(String appNo, String org,
			String cardNo, String applyType) {

		if(StringUtils.equals(applyType, AppType.S.name()) && StringUtils.isEmpty(cardNo)){
			throw new ProcessException("当申请类型是独立副卡时，必须提供主卡卡号");
		}
		
		if(!StringUtils.equals(applyType, AppType.S.name()) && StringUtils.isEmpty(appNo)){
			throw new ProcessException("当申请类型非独立副卡时，必须提供申请件编号");
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		
		param.put("org", org);
		param.put("appType", applyType);
		param.put("cardNo", cardNo);
		param.put("appNo", appNo);

		
		List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = alreadyCardDao.getCardInfo(param);
		
		if(alreadyCardsCardInfoDtos == null || alreadyCardsCardInfoDtos.size() == 0)return null;
		else{
			return alreadyCardsCardInfoDtos.get(0);
		}
	}

	

}
