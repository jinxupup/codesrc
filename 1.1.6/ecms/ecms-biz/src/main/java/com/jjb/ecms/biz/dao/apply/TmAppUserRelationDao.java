package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;


/**
 * @Description: 附卡申请人信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
public interface TmAppUserRelationDao extends BaseDao<TmAppUserRelation> {

	/**
	 * 根据申请件编号appNo和status获取对应附卡申请人信息
	 * @param msgType status
	 * @return
	 */

	public TmAppUserRelation getTmAppUserRelationByUserNo(String userNo);

	public Page<TmAppUserRelation> getPage(Page<TmAppUserRelation> page);


}