package com.jjb.ecms.biz.dao.apply;


import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: 申请主卡卡片信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */
public interface TmAppPrimCardInfoDao extends BaseDao<TmAppPrimCardInfo> {
	
	/**
	 * 根据申请件编号appNo获取申请主卡卡片信息
	 * @param appNo
	 * @return
	 */	
	public TmAppPrimCardInfo getTmAppPrimCardInfoByAppNo(String appNo);
	
	/**
	 * 保存预录入申请主卡卡片信息
	 * @param tmAppPrimCardInfo
	 */
	public void saveTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) ;
	
	/**
	 * 更新预录入申请主卡卡片信息
	 * @param tmAppPrimCardInfo
	 */
	public void updateTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) ;

	/**
	 * 删除预录入申请主卡卡片信息
	 * @param tmAppPrimCardInfo
	 */
	public void deleteTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) ;
	
	public void updateNotNullTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) ;
}