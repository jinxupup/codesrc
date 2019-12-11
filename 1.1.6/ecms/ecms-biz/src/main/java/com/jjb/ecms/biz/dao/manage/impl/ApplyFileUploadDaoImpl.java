/**
 * 
 */
package com.jjb.ecms.biz.dao.manage.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.ApplyFileUploadDao;
import com.jjb.ecms.infrastructure.TmAppUpload;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: 申请资料上传
 * @author JYData-R&D-L.L
 * @date 2016年9月20日 上午11:01:28
 * @version V1.0  
 */
@Repository("applyFileUploadDao")
public class ApplyFileUploadDaoImpl extends AbstractBaseDao<TmAppUpload> implements ApplyFileUploadDao {
	
	/**
	 * 保存上传的申请资料
	 * @param tmAppinfoUpload
	 */
	@Override
	public void saveTmAppUpload(TmAppUpload tmAppUpload) {
		save(tmAppUpload);
	}

	/**
	 * 获取上传记录
	 * @param
	 */
	@Override
	public Page<TmAppUpload> getTmAppUploadPage(Page<TmAppUpload> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}	
		return queryForPageList("com.jjb.ecms.infrastructure.mapping.TmAppUploadMapper.selectAll", page.getQuery(), page);
	}

	/**
	 * 根据id删除上传记录
	 * @param id
	 */
	@Override
	public void deleteTmAppUpload(int id) {
		TmAppUpload tmAppUpload = new TmAppUpload();
		tmAppUpload.setId(id);
		deleteByKey(tmAppUpload);	
	}

	/**
	 * 根据id查询上传的申请资料
	 * @param id
	 */
	@Override
	public TmAppUpload getTmAppUploadByKey(int id) {
		TmAppUpload tmAppUpload = new TmAppUpload();
		tmAppUpload.setId(id);
		return queryByKey(tmAppUpload);
	}

	/**
	 * 根据文件名查询上传文件
	 * @return
	 */
	@Override
	public List<TmAppUpload> getTmAppUploadByName(String fileName) {
		TmAppUpload tmAppUpload = new TmAppUpload();
		tmAppUpload.setFileName(fileName);
		return queryForList(tmAppUpload);
	}

	@Override
	public Page<TmAppUpload> getTmAppUploadPageByFnOrData(Page<TmAppUpload> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		return queryForPageList("com.jjb.ecms.biz.TmAppUploadDecisionMapper.select", page.getQuery(), page);
	}

}
