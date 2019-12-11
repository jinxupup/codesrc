package com.jjb.ecms.biz.service.manage;

import com.jjb.ecms.infrastructure.TmAppImageHistory;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 影像调阅记录查询
 * @author JYData-R&D-L.L
 * @date 2016年8月31日 下午6:44:04
 * @version V1.0  
 */
public interface ApplyEcssHistoryService {

	/**
	 * 影像调阅历史记录 分页查询
	 * @param page
	 * @return 影像调阅查询记录
	 */
	Page<TmAppImageHistory> getImagePage(Page<TmAppImageHistory> page);
	

	/**
	 * 保存客户影像调阅记录
	 * @param imageHistory
	 */
	void save(TmAppImageHistory imageHistory);
}
