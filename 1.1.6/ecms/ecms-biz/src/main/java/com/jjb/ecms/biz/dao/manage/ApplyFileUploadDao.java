/**
 * 
 */
package com.jjb.ecms.biz.dao.manage;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppUpload;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 申请资料上传
 * @author JYData-R&D-L.L
 * @date 2016年9月20日 上午10:59:46
 * @version V1.0  
 */
public interface ApplyFileUploadDao {
	/**
	 * 保存申请资料信息
	 * @param tmAppUpload
	 */
	public void saveTmAppUpload(TmAppUpload tmAppUpload);
	/**
	 * 查询上传记录
	 * @return
	 */
	public Page<TmAppUpload> getTmAppUploadPage(Page<TmAppUpload> page);
	/**
	 * 删除某一条上传记录
	 * @param id
	 */
	public void deleteTmAppUpload(int id);
	/**
	 * 根据id查询上传资料
	 * @param id
	 */
	public TmAppUpload getTmAppUploadByKey(int id);
	
	/**
	 * 根据文件名查询上传文件
	 * @return
	 */
	public List<TmAppUpload> getTmAppUploadByName(String fileName);
	/**
	 * 通过文件名或上传时间获取上传记录
	 * @param page
	 * @return
	 */
	Page<TmAppUpload> getTmAppUploadPageByFnOrData(Page<TmAppUpload> page);
}

