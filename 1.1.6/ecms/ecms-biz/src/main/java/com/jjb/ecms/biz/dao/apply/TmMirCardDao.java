package com.jjb.ecms.biz.dao.apply;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;
/**
  * @Description: 卡信息dao
  * @author JYData-R&D-Big Star
  * @date 2016年9月5日 下午7:19:37
  * @version V1.0
 */
/**
  * @Description: 卡信息dao
  * @author JYData-R&D-Big Star
  * @date 2016年9月5日 下午7:19:37
  * @version V1.0
 */
public interface TmMirCardDao  extends BaseDao<TmMirCard>{
	
	public Page<TmMirCard> getPage(Page<TmMirCard> page);

	/**
	 * @param cardNo 卡号
	 * @return
	 */
	public TmMirCard getByCardNo(String carNo);
	
	/**
	 * 根据逻辑卡号获取卡片信息
	 * @param carNo 卡号
	 * @return
	 */
	public TmMirCard getBylogicalCardNo(String carNo);
	
	public void saveTmMirCard(TmMirCard tmMirCard);

	public void updateTmMirCard(TmMirCard tmMirCard);

	/**
	 * @param appNo
	 * @param idNo 
	 * @param idType 
	 * @return
	 */
	public List<TmMirCard> getTmMirCards(String productCd, String idType, String idNo); 
	
	/**
	 * 查询 核心返回的mir 表中数据，查找客户在核心已存在的卡片信息
	 * @param map
	 * @return
	 */
	public List<TmMirCard> getCpsSystemApply(Map<String,Object> map); 
	
	/**
	 * 根据参数查询TmMirCard表数据
	 * @param map
	 * @return
	 */
	public List<TmMirCard> getTmMirCardList(Map<String,Object> map);

	public TmMirCard getTmMirCardByCardNo(String cardNo);
		
}