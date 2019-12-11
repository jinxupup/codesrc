package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.dao.apply.TmLuckyCardDao;
import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @Description: 锁卡表信息dao
 * @author JYData-R&D-Big T.T
 * @date 2016年10月10日 下午7:19:37
 * @version V1.0
 */
@Repository("tmLuckyCardDao")
public class TmLuckyCardDaoImpl extends AbstractBaseDao<TmLuckyCard> implements TmLuckyCardDao{
	private static String selectAll = "com.jjb.ecms.infrastructure.mapping.TmLuckyCardMapper.selectAll";
	private static final String NS = "com.jjb.ecms.mapping.LuckyCardMapper";
	/**
	 * 解锁卡号
	 * @param cardNo 
	 */
	@Override
	public Page<TmLuckyCard> getUnlockCardPage(Page<TmLuckyCard> page) {
		
		// TODO Auto-generated method stub
		
		Page<TmLuckyCard> p = queryForPageList(NS+".selectAll", page.getQuery(), page);
		
		return p;
	}
	
	@Override
	public List<TmLuckyCard> getByCardNo(String cardNo,String appNo) {
		// TODO Auto-generated method stub
		List<TmLuckyCard> list = null;
		if(StringUtils.isNotEmpty(cardNo) && StringUtils.isNotEmpty(appNo)){
			TmLuckyCard tmLuckyCard = new TmLuckyCard();
			tmLuckyCard.setAppNo(appNo);
			tmLuckyCard.setCardNo(cardNo);	
			tmLuckyCard.setStatus(Indicator.Y.name());
			list = queryForList(tmLuckyCard);
		}
		
		return list;
	}

	@Override
	public void saveTmLuckyCard(TmLuckyCard tmLuckyCard) {
		// TODO Auto-generated method stub
		
		save(tmLuckyCard);
	}

	@Override
	public void unlock(String cardNo,String appNo) throws ProcessException {
		// TODO Auto-generated method stub
		if(StringUtils.isNotEmpty(cardNo)){
			TmLuckyCard tmLuckyCard = new TmLuckyCard();
			tmLuckyCard.setCardNo(cardNo);
			tmLuckyCard.setAppNo(appNo);
			tmLuckyCard.setStatus(Indicator.Y.name());
			List<TmLuckyCard> cards = queryForList(tmLuckyCard);
			if (CollectionUtils.sizeGtZero(cards)) {
				for (TmLuckyCard card : cards) {
					card.setStatus(Indicator.N.name());
					card.setOperId(OrganizationContextHolder.getUserNo());
					card.setUpdateTime(new Date());
					update(card);
				}
			}else {
				throw new ProcessException("没有查到该解锁卡号，请确认后重试");
			}
		}
	}
	
}
