package com.jjb.ecms.biz.service.manage.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.manage.RiskListUploadDao;
import com.jjb.ecms.biz.service.manage.RiskListUploadService;
import com.jjb.ecms.infrastructure.TmBatchUpload;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 风险名单上传
 * @author JYData-R&D-BIG.W.W
 * @date 2017年11月13日 下午17:01:24
 * @version V1.0
 */
@Service("blackUploadService")
public class RiskListUploadServiceImpl implements RiskListUploadService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	RiskListUploadDao riskListUploadDao;

	/**
	 * 保存上传文件信息
	 * @param
	 */
	@Override
	@Transactional
	public void saveTmRiskUpload(TmBatchUpload tmBatchUpload) {
		riskListUploadDao.saveTmRiskUpload(tmBatchUpload);

	}
	/**
	 * 查询上传记录  分页查询
	 * @param
	 */
	@Override
	@Transactional
	public Page<TmBatchUpload> getTmRiskUploadPage(Page<TmBatchUpload> page) {
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			Object dateObj1= page.getQuery().get("beginDate");
			Date beginDate = null;
			if(StringUtils.isNotEmpty(dateObj1) && dateObj1 instanceof String) {
				String str = StringUtils.valueOf(dateObj1);
				try {
					if(str.indexOf('-') !=-1) {
						beginDate = DateUtils.getDateStart(DateUtils.stringToDate(str, DateUtils.DAY_YMD_LINE));
					}else{
						beginDate = DateUtils.getDateStart(DateUtils.stringToDate(str, DateUtils.DAY_YMD));
					}
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}else if(StringUtils.isNotEmpty(dateObj1) && dateObj1 instanceof Date) {
				beginDate = DateUtils.getDateStart((Date)dateObj1);
			}
			if(beginDate!=null) {
				page.getQuery().put("beginDate", beginDate);
			}
			Object dateObj2= page.getQuery().get("endDate");
			Date endDate = null;
			if(StringUtils.isNotEmpty(dateObj2) && dateObj2 instanceof String) {
				String str = StringUtils.valueOf(dateObj2);
				try {
					if(str.indexOf('-') !=-1) {
						endDate = DateUtils.getDateEnd(DateUtils.stringToDate(str, DateUtils.DAY_YMD_LINE));
					}else{
						endDate = DateUtils.getDateEnd(DateUtils.stringToDate(str, DateUtils.DAY_YMD));
					}
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}else if(StringUtils.isNotEmpty(dateObj2) && dateObj2 instanceof Date) {
				endDate = DateUtils.getDateEnd((Date)dateObj2);
			}
			if(endDate!=null) {
				page.getQuery().put("endDate", endDate);
			}
		}
		return riskListUploadDao.getTmRiskUploadPage(page);
	}
	/**
	 * 删除某一条上传记录
	 * @param id
	 */
	@Override
	@Transactional
	public void deleteTmRiskUpload(int id) {
		riskListUploadDao.deleteTmRiskUpload(id);

	}
	/**
	 * 根据id查询上传资料
	 * @param id
	 */
	@Override
	@Transactional
	public TmBatchUpload getTmRiskUploadByKey(int id) {

		return riskListUploadDao.getTmRiskUploadByKey(id);
	}
	/**
	 * 根据文件名查询上传文件
	 * @return
	 */
	@Override
	@Transactional
	public List<TmBatchUpload> getTmRiskUploadByName(String fileName) {
		return riskListUploadDao.getTmRiskUploadByName(fileName);
	}

}
