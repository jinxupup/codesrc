package com.jjb.ecms.biz.service.manage;

import java.util.List;

import com.jjb.ecms.infrastructure.TmBatchUpload;
import com.jjb.unicorn.facility.model.Page;

/**@Description:风险名单上传接口类--->改为批量上传接口类
 * @Description: 风险名单上传接口类
 * @author JYData-R&D-BIG.W.W
 * @date 2017年11月13日 下午17:00:24
 * @version V1.0
 */
public interface RiskListUploadService {
	/**
	 * 保存申请文件信息
	 * @param
	 */
	public void saveTmRiskUpload(TmBatchUpload tmBatchUpload);
	/**
	 * 获取上传记录
	 * @return
	 */
	public Page<TmBatchUpload> getTmRiskUploadPage(Page<TmBatchUpload> page);
	/**
	 * 删除某一条上传记录
	 * @param id
	 */
	public void deleteTmRiskUpload(int id);
	/**
	 * 根据id查询上传资料
	 * @param id
	 */
	public TmBatchUpload getTmRiskUploadByKey(int id);
	/**
	 * 根据文件名查询上传文件
	 * @return
	 */
	public List<TmBatchUpload> getTmRiskUploadByName(String fileName);

}
