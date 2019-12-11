package com.jjb.ecms.biz.dao.apply.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
  * @Description: 卡信息表实现类
  * @author JYData-R&D-Big Star
  * @date 2016年9月5日 下午7:18:28
  * @version V1.0
 */
@Repository("tmMirCardDao")
public class TmMirCardDaoImpl extends AbstractBaseDao<TmMirCard> implements TmMirCardDao {

	private static final String selectAll = "TmMirCard.selectAll";
	private static final String selectCpsSystemAppByParam = "com.jjb.ecms.biz.ApplyTmMirCard.selectCpsSystemAppByParam";
//	private static final String selectMirCardByParam = "com.jjb.ecms.biz.ApplyTmMirCard.selectMirCardByParam";
	
	@Override
	public Page<TmMirCard> getPage(Page<TmMirCard> page) {
		// TODO Auto-generated method stub
		Page<TmMirCard> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}

	/*
	 * 根据卡号查询卡表信息
	 */
	@Override
	public TmMirCard getByCardNo(String cardNo) {
		if(cardNo==null){
			return null;
		}
		TmMirCard tmMirCard = new TmMirCard();
		tmMirCard.setCardNo(cardNo);	
		
		return queryByKey(tmMirCard);
	}
	
	
	/**
	 * 获取卡信息
	 */
	@Override
	public List<TmMirCard> getTmMirCards(String productCd, String idType, String idNo) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("productCd", productCd);
		queryMap.put("idType", idType);
		queryMap.put("idNo", idNo);
		List<TmMirCard> list = queryForList(selectAll, queryMap);
		
		return list;
	}

	/**
	 * 根据逻辑卡号获取卡片信息
	 * @param carNo 卡号
	 * @return
	 */
	@Override
	public TmMirCard getBylogicalCardNo(String carNo) {
		TmMirCard tmMirCard = new TmMirCard();
		
		return queryForList(tmMirCard).get(0);
	}
	
	/**
	 * 查询 核心返回的mir 表中数据，查找客户在核心已存在的卡片信息
	 * @param map
	 * @return
	 */
	@Override
	public List<TmMirCard> getCpsSystemApply(Map<String, Object> map) {
		return queryForList(selectCpsSystemAppByParam, map);
	}
	
	/**
	 * 根据map中的参数查询TmMirCard
	 */
	@Override
	public List<TmMirCard> getTmMirCardList(Map<String,Object> map) {
		if(map==null || map.size()==0){
			throw new ProcessException("核心卡片查询条件不能为空");
		}
		return queryForList(new TmMirCard(), map);
	}

	@Override
	public TmMirCard getTmMirCardByCardNo(String cardNo) {
		TmMirCard tmMirCard = new TmMirCard();
		tmMirCard.setCardNo(cardNo);
		List<TmMirCard> tmMirCards = queryForList(tmMirCard);
		TmMirCard mirCardinfo = null;
		if (tmMirCards != null && tmMirCards.size() > 0) {
			mirCardinfo = tmMirCards.get(0);
		}
		return mirCardinfo;
	}

	/*
	 * 
	 */
	@Override
	public void saveTmMirCard(TmMirCard tmMirCard) {
		if(tmMirCard!=null) {
			tmMirCard.setUpdateTime(new Date());
			save(tmMirCard);
		}
	}

	@Override
	public void updateTmMirCard(TmMirCard tmMirCard) {
		if(tmMirCard!=null) {
			tmMirCard.setUpdateTime(new Date());
			update(tmMirCard);
		}
	}
	
}
