package com.jjb.ecms.biz.service.manage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.manage.ApplyEcssHistoryDao;
import com.jjb.ecms.biz.service.manage.ApplyEcssHistoryService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.infrastructure.TmAppImageHistory;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 影像调阅记录查询
 * @author JYData-R&D-L.L
 * @date 2016年8月31日 下午6:44:04
 * @version V1.0  
 */
@Service("applyEcssHistoryService")
public class ApplyEcssHistoryServiceImpl implements ApplyEcssHistoryService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyEcssHistoryDao tmAclImageDao;

	/**
	 * 影像调阅历史记录 分页查询
	 * @param page
	 * @return 影像调阅查询记录
	 */
	@Override
	@Transactional
	public Page<TmAppImageHistory> getImagePage(Page<TmAppImageHistory> page) {		
		return tmAclImageDao.getImagePage(page);
	}

	/**
	 * 保存客户影像调阅记录
	 * @param imageHistory
	 */
	@Override
	@Transactional
	public void save(TmAppImageHistory imageHistory){
		if(imageHistory==null){
			logger.info("保存客户影像调阅记录失败，实体对象为空"+ LogPrintUtils.printCommonEndLog(null,null));
			return;
		}
		long start1 = System.currentTimeMillis();
		logger.info("保存客户影像调阅记录开始"+LogPrintUtils.printAppNoLog(imageHistory.getAppNo(),start1,null));
//		imageHistory.setJpaVersion(0);
		tmAclImageDao.save(imageHistory);
		logger.info("保存客户影像调阅记录结束"+LogPrintUtils.printAppNoEndLog(imageHistory.getAppNo(),start1,null));
	}
	
}
