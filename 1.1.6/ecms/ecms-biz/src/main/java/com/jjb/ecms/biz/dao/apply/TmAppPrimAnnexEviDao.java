package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: 申请附件证明信息
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */
public interface TmAppPrimAnnexEviDao extends BaseDao<TmAppPrimAnnexEvi> {
	
	/**
	 * 根据申请件编号appNo获取申请附件证明信息
	 * @param appNo
	 * @return
	 */
	public TmAppPrimAnnexEvi getTmAppPrimAnnexEviByAppNo(String appNo); 
	
	/**
	 * 保存申请附件证明信息
	 * @param tmAppPrimAnnexEvi
	 * @return
	 */
	public void saveTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) ;
	
	/**
	 * 更新申请附件证明信息
	 * @param tmAppPrimAnnexEvi
	 * @return
	 */
	public void updateTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) ;
	
	/**
	 * 删除申请附件证明信息
	 * @param tmAppPrimAnnexEvi
	 * @return
	 */
	public void deleteTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) ;
	
	public void updateNotNullTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) ;
}