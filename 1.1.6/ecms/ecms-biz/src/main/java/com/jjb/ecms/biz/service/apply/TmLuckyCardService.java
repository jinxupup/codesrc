
package com.jjb.ecms.biz.service.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2016年10月24日 下午2:17:04
 * @version V1.0  
 */

public interface TmLuckyCardService {

	/**
	 * 解锁卡号
	 * @param cardNo
	 */
	public Page<TmLuckyCard> getUnlockCardPage(Page<TmLuckyCard> page);
	/**
	 * 保存TmLuckyCard
	 * @param tmLuckyCard
	 */
	public void saveTmLuckyCard(TmLuckyCard tmLuckyCard);
	
	/**
	 * 根据cardNo查询被锁的卡信息
	 */
	public List<TmLuckyCard> getByCardNo(String cardNo,String appNo);

	/**
	 * 解锁卡号+ 核心解锁卡号
	 * @param cardNo
	 * @param appNo
	 * @throws Exception
	 */
	public void unlock(String cardNo,String appNo,Integer attchNo,String bscSuppInd) throws Exception;
	
	/**
	 * 更新操作
	 * @param tmLuckyCard
	 */
	public void updateTmLuckyCard(TmLuckyCard tmLuckyCard);
}
