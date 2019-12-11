package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;



/**
  * @Description: 锁卡表信息dao
  * @author JYData-R&D-Big T.T
  * @date 2016年10月10日 下午7:19:37
  * @version V1.0
 */
public interface TmLuckyCardDao extends BaseDao<TmLuckyCard> {
	
	/**
	 * 解锁卡号
	 * @param cardNo
	 */
	public Page<TmLuckyCard> getUnlockCardPage(Page<TmLuckyCard> page);
	/**根据卡号获取卡号信息
	 * @param cardNo
	 * @param appNo
	 * @return
	 */
	public List<TmLuckyCard> getByCardNo(String cardNo,String appNo);
	
	/**
	 * 保存tmLuckyCard
	 * @param tmLuckyCard
	 */
	public void saveTmLuckyCard(TmLuckyCard tmLuckyCard);
		
	/**
	 * 解锁卡号
	 * @param 卡号cardNo
	 */	
	public void unlock(String cardNo,String appNo) throws ProcessException;
}