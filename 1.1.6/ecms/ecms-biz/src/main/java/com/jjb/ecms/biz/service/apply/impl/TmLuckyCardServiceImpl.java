
package com.jjb.ecms.biz.service.apply.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmLuckyCardDao;
import com.jjb.ecms.biz.service.apply.ApplyCardNoService;
import com.jjb.ecms.biz.service.apply.TmLuckyCardService;
import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 自选卡号锁表
 * @author JYData-R&D-Big T.T
 * @date 2016年10月24日 下午2:20:40
 * @version V1.0  
 */

@Service("tmLuckyCardService")
public class TmLuckyCardServiceImpl implements TmLuckyCardService {
	
	@Autowired
	private TmLuckyCardDao tmLuckyCardDao;
	@Autowired
	private ApplyCardNoService applyCardNoService;
	@Autowired
	private TmAppMainDao appMainDao;
	
	@Override
	@Transactional
	public Page<TmLuckyCard> getUnlockCardPage(Page<TmLuckyCard> page) {
		// TODO Auto-generated method stub
		
		page = tmLuckyCardDao.getUnlockCardPage(page);
		
		return page;
	}
	/**
	 * 保存tmLuckyCard
	 */
	@Override
	@Transactional
	public void saveTmLuckyCard(TmLuckyCard tmLuckyCard) {
		// TODO Auto-generated method stub		
		tmLuckyCard.setOperId(OrganizationContextHolder.getUserNo());
//		tmLuckyCard.setJpaVersion(0);
		tmLuckyCard.setUpdateTime(new Date());
		
		tmLuckyCardDao.saveTmLuckyCard(tmLuckyCard);
	}
	
	/**
	 * 根据cardNo查询被锁的卡信息
	 */
	@Override
	@Transactional
	public List<TmLuckyCard> getByCardNo(String cardNo,String appNo) {
		return tmLuckyCardDao.getByCardNo(cardNo,appNo);
	}
	/**
	 * 解锁卡号
	 * @param cardNo
	 */
	@Override
	@Transactional
	public void unlock(String cardNo,String appNo,Integer attachNo,String bscSuppInd) throws Exception{
		applyCardNoService.unlockSelectedCardNo(cardNo, appNo, attachNo, bscSuppInd);
	}
	
	/**
	 * 更新操作
	 * @param tmLuckyCard
	 */
	@Override
	@Transactional
	public void updateTmLuckyCard(TmLuckyCard tmLuckyCard) {
		if(tmLuckyCard != null && tmLuckyCard.getId() != null){
			tmLuckyCardDao.update(tmLuckyCard);
		}
	}
}
