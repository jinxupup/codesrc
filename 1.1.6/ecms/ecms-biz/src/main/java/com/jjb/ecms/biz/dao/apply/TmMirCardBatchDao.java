package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmMirCardBatch;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

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
public interface TmMirCardBatchDao extends BaseDao<TmMirCardBatch>{

	public List<TmMirCardBatch> getTmMirCardBatchList();

	public void saveTmMirCardBatch(TmMirCardBatch tmMirCardBatch);

	Page<TmMirCardBatch> queryTmMirCardBatchListPage(Page<TmMirCardBatch> page);

}