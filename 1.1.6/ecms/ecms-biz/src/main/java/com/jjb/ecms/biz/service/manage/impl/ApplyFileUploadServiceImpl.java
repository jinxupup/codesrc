/**
 * 
 */
package com.jjb.ecms.biz.service.manage.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.manage.ApplyFileUploadDao;
import com.jjb.ecms.biz.service.manage.ApplyFileUploadService;
import com.jjb.ecms.infrastructure.TmAppUpload;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请资料上传
 * @author JYData-R&D-L.L
 * @date 2016年9月20日 上午10:56:00
 * @version V1.0  
 */
@Service("applyFileUploadService")
public class ApplyFileUploadServiceImpl implements ApplyFileUploadService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    ApplyFileUploadDao applyFileUploadDao;
	/**
	 * 保存上传文件信息
	 * @param tmAppUpload
	 */
	@Override
	@Transactional
	public void saveTmAppUpload(TmAppUpload tmAppUpload) {
		applyFileUploadDao.saveTmAppUpload(tmAppUpload);
		
	}
	/**
	 * 查询上传记录  分页查询
	 * @param
	 */
	@Override
	@Transactional
	public Page<TmAppUpload> getTmAppUploadPage(Page<TmAppUpload> page) {

		return applyFileUploadDao.getTmAppUploadPage(page);
	}

	/**
	 * 通过文件名或上传时间获取上传记录
	 * @param page
	 * @return
	 */
	@Override
	@Transactional
	public Page<TmAppUpload> getTmAppUploadPageByFnOrData(Page<TmAppUpload> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			if(StringUtils.isNotEmpty((String)page.getQuery().get("beginDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("beginDate"));
					page.getQuery().put("beginDate", DateUtils.getDateStart(date));
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}
			if(StringUtils.isNotEmpty((String)page.getQuery().get("endDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("endDate"));
					page.getQuery().put("endDate",DateUtils.getDateEnd(date));
				} catch (ParseException e) {
					logger.error("案件转分配时间格式转换发生异常", e);
				}
			}
		}
		return applyFileUploadDao.getTmAppUploadPageByFnOrData(page);
	}

	/**
	 * 删除某一条上传记录
	 * @param id
	 */
	@Override
	@Transactional
	public void deleteTmAppUpload(int id) {
		applyFileUploadDao.deleteTmAppUpload(id);
		
	}
	/**
	 * 根据id查询上传资料
	 * @param id
	 */
	@Override
	@Transactional
	public TmAppUpload getTmAppUploadByKey(int id) {
		
		return applyFileUploadDao.getTmAppUploadByKey(id);
	}
	/**
	 * 根据文件名查询上传文件
	 * @return
	 */
	@Override
	@Transactional
	public List<TmAppUpload> getTmAppUploadByName(String fileName) {
		return applyFileUploadDao.getTmAppUploadByName(fileName);
	}

}
