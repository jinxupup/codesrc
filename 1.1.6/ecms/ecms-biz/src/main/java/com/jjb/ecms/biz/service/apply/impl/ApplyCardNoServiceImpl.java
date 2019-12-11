package com.jjb.ecms.biz.service.apply.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.loan.LockReason;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmLuckyCardDao;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.service.apply.ApplyCardNoService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.util.LuhnMod10;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;


@Service("applyCardNoService")
public class ApplyCardNoServiceImpl implements ApplyCardNoService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmLuckyCardDao tmLuckyCardDao;
	
	@Autowired
	private TmMirCardDao tmMirCardDao;
	
	@Autowired
	private TmAppMainDao tmAppMainDao;
//	@Autowired
//	private MPSCardService mpsCardService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private TmAppCustInfoDao tmAppCustInfoDao;
	/**
	 * return
	 * 返回值为cardNo
	 * 原卡号:表示该自选卡号已使用
	 * 原卡号+校验位:表示该自选卡号未使用
	 */
	@Override
	@Transactional
	public String validateByCardNo(String cardNo,String appNo, Integer attachNo,String bscSuppInd) {
		List<TmLuckyCard> tmLuckyCardList = tmLuckyCardDao.getByCardNo(cardNo,appNo);//查出所有锁住的卡号
		Boolean luckFlag = false;
		//找出是否有锁卡的记录
		if(CollectionUtils.sizeGtZero(tmLuckyCardList)){
			for (TmLuckyCard tmLuckyCard : tmLuckyCardList) {
				if(StringUtils.isNotEmpty(tmLuckyCard.getStatus()) && tmLuckyCard.getStatus().equals(Indicator.Y.name())){
					luckFlag = true;
					break;
				}
			}
		}
		if(!luckFlag){
//			com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
			String result = StringUtils.valueOf(LuhnMod10.getDigit(cardNo));  //根据原卡号产生一位校验码
			String cardNoNum = cardNo + result;
			TmMirCard tmMirCard = tmMirCardDao.getByCardNo(cardNoNum);
			//如果卡号未使用就要进行锁号操作
			if(tmMirCard == null){
				//核心锁卡
				try {
//					mpsCardService.MS3504(cardNoNum);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("自选卡号"+cardNo+"核心锁卡失败",e);
					return cardNo;  //返回原卡号的值
				}
				try {
					TmLuckyCard tmLuckyCard = new TmLuckyCard();
					tmLuckyCard.setCardNo(cardNoNum); //锁卡表改为保存原卡号+校验位.
					tmLuckyCard.setAppNo(appNo); 
					tmLuckyCard.setOrg(OrganizationContextHolder.getOrg());
					tmLuckyCard.setLockReason(LockReason.S.name());
					tmLuckyCard.setOperId(OrganizationContextHolder.getUserNo());
				
					TmAppCustInfo cust = tmAppCustInfoDao.getTmAppCustInfo(appNo, attachNo, bscSuppInd);
					
					if(cust!=null){
						cust.setIfSelectedCard(Indicator.Y.name());
						cust.setCardNo(cardNoNum);
						cust.setUpdateDate(new Date());
						cust.setUpdateUser(OrganizationContextHolder.getUserNo());
						tmAppCustInfoDao.updateTmAppCustInfo(cust);
						
						tmLuckyCard.setName(cust.getName());
						tmLuckyCard.setCellphone(cust.getCellphone());
						tmLuckyCard.setIdType(cust.getIdType());
						tmLuckyCard.setIdNo(cust.getIdNo());
						tmLuckyCard.setUpdateTime(new Date());
						tmLuckyCard.setStatus(Indicator.Y.name());
						tmLuckyCardDao.saveTmLuckyCard(tmLuckyCard);
					}else{
						throw new ProcessException("申请件[" + appNo + "]"
								+ ",主附卡标志["+bscSuppInd+"-"+attachNo+"],自选卡号["
								+ cardNoNum + " ]保存失败,未找到对应的申请记录");
					}
				} catch (Exception e) {
					logger.error("自选卡号"+cardNo+"更新锁表、卡号信息失败",e);
					try {
//						mpsCardService.MS3505(cardNoNum);//发生异常需解锁
					} catch (Exception e2) {
						logger.error("自选卡号"+cardNo+"更新锁表、卡号信息发生异常核心解锁失败",e2);
					}
				}
				logger.info("自选卡号"+cardNo+"选择成功！");	
				
				return cardNoNum; //返回原卡号+校验位的值
			}
		}
		logger.info("自选卡号"+cardNo+"选择失败！");	
		
		return cardNo;  //返回原卡号的值
	}
	
	/**
	 * 解锁卡号时移除已经保存的卡号
	 * @param cardNo
	 * @param appNo
	 * @param attachNo
	 */
	@Override
	@Transactional

	public void unlockSelectedCardNo(String cardNo, String appNo,
			Integer attachNo, String bscSuppInd) {
		String validBit= null;
		try {
			
			if(StringUtils.isEmpty(validBit)){
				validBit = StringUtils.valueOf(LuhnMod10.getDigit(cardNo));
			}
//			com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
//			mpsCardService.MS3505(cardNo + validBit);//发生异常需解锁
		} catch (Exception e) {
			logger.error("自选卡号"+cardNo+"核心解锁失败",e);
			throw new ProcessException("自选卡号"+cardNo+"核心解锁失败,核心返回信息["+e.getMessage()+"]");
		}
		try {
			tmLuckyCardDao.unlock(cardNo+validBit, appNo);
		} catch (Exception e) {
			logger.error(appNo+"自选卡号"+cardNo+validBit+"本地锁表解锁失败",e);
			throw new ProcessException("自选卡号"+cardNo+validBit+"本地锁表解锁失败");
		}
		try {
			if(StringUtils.isEmpty(cardNo)){
				List<TmAppCustInfo> custList = tmAppCustInfoDao.getTmAppCustInfoList(appNo, null,null);
				for (int i = 0; i < custList.size(); i++) {
					TmAppCustInfo cust = custList.get(i);
					if(cust!=null){
						cust.setIfSelectedCard(Indicator.N.name());
						cust.setCardNo(null);
						cust.setUpdateDate(new Date());
						cust.setUpdateUser(OrganizationContextHolder.getUserNo());
						tmAppCustInfoDao.updateTmAppCustInfo(cust);
					}
				}
			}else {
				if(StringUtils.isEmpty(bscSuppInd)){
					throw new ProcessException("自选卡号"+cardNo+validBit+"本地锁表解锁失败,未指定需要解锁的主附卡标志，请联系管理员");
				}
				TmAppCustInfo cust = tmAppCustInfoDao.getTmAppCustInfo(appNo, attachNo, bscSuppInd);
				if(cust!=null){
					cust.setIfSelectedCard(Indicator.N.name());
					cust.setCardNo(null);
					cust.setUpdateDate(new Date());
					cust.setUpdateUser(OrganizationContextHolder.getUserNo());
					tmAppCustInfoDao.updateTmAppCustInfo(cust);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(appNo+"自选卡号"+cardNo+validBit+"删除卡号信息失败",e);
			throw new ProcessException("自选卡号"+cardNo+validBit+"删除卡号信息失败");
		}
	}
}
