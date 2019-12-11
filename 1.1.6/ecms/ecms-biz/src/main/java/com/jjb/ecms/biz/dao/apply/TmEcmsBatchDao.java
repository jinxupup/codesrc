package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmEcmsBatch;
import com.jjb.unicorn.base.dao.BaseDao;

import java.util.List;
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
public interface TmEcmsBatchDao extends BaseDao<TmEcmsBatch>{


	public TmEcmsBatch getTmEcmsBatch(TmEcmsBatch tmEcmsBatch);

	public void saveTmEcmsBatch(TmEcmsBatch tmEcmsBatch);

	public void updateTmEcmsBatch(TmEcmsBatch tmEcmsBatch);

	public List<TmEcmsBatch> getTmEcmsBatchList();
		
}