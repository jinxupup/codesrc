package com.jjb.ecms.biz.dao.manage;

import java.util.List;

import com.jjb.ecms.infrastructure.TmBatchUpload;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 风险名单上传
 * @author JYData-R&D-BIG.W.W
 * @date 2017年11月23日 下午17:03:46
 * @version V1.0
 */
public interface RiskListUploadDao {
	/**
	 * 保存风险名单信息
	 * @param
	 */
	public void saveTmRiskUpload(TmBatchUpload TmRiskUpload);
	/**
	 * 查询上传记录
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

