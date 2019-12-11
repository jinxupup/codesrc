package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmAppnoSeq;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 获取申请件编号
 * @Description: TODO
 * @author JYData-R&D-HN
 * @date 2016年9月2日 下午4:56:56
 * @version V1.0
 */
public interface TmAppnoSeqDao extends BaseDao<TmAppnoSeq> {
	
	public String getAppNo(String org);
}