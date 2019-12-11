
package com.jjb.ecms.biz.service.apply;



import com.jjb.ecms.infrastructure.TmEcmsBatch;
import com.jjb.ecms.infrastructure.TmMirCardBatch;
import com.jjb.ecms.infrastructure.TmMirCardExce;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

import java.util.List;

/**
 * @Description: 已申请卡数据处理表
 * @author JYData-R&D-Big T.T
 * @date 2019年2月22日 下午7:40:26
 * @version V1.0
 */

public interface TmMirCardBatchService {


	public List<TmMirCardBatch> getTmMirCardBatchList() throws ProcessException;

	//分页查询TmMirCardBatch数据
	public Page<TmMirCardBatch> getTmMirCardBatchListPage(Page<TmMirCardBatch> page) throws ProcessException;

	public TmEcmsBatch getTmEcmsBatch(TmEcmsBatch tmEcmsBatch) throws ProcessException;

	public List<TmEcmsBatch> getTmEcmsBatchList() throws ProcessException;

	public void saveTmMirCardBatch(TmMirCardBatch tmMirCardBatch) throws ProcessException;

	public void saveTmEcmsBatch(TmEcmsBatch tmEcmsBatch) throws ProcessException;

	public void updateTmEcmsBatch(TmEcmsBatch tmEcmsBatch) throws ProcessException;

	public void saveTmExcMirCardError(TmMirCardExce tmExcMirCardError) throws ProcessException;

}
