package com.jjb.ecms.biz.service.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmField;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 页面字段管理service
 * @author H.N
 * @date 2017年9月4日10:34:01
 */
public interface TmFieldService {
	/**
	 * 获取字段列表
	 * @param page
	 * @return
	 */
	public Page<TmField> getTmFieldPage(Page<TmField> page);
	
	/**
	 * 根据主键id获取TmField信息
	 * @param id
	 * @return
	 */
	public TmField getTmFieldById(Integer id);
	
	/**
	 * 保存字段
	 * @param tmField
	 */
	public void saveTmField(TmField tmField);
	
	/**
	 * 根据条件获取字段配置信息list
	 * @param tmField
	 * @return
	 */
	public  List<TmField> getTmFieldList(TmField tmField);
	
	/**
	 * 根据id更新字段
	 * @param tmField
	 * @throws Exception
	 */
	public void updateTmField(TmField tmField) throws Exception;

	/**
	 * 更改启用状态
	 * @param id
	 * @param ifUsed
	 * @throws Exception
	 */
	public void updateByIfUsed(Integer id, String ifUsed) throws Exception;
	
	/**
	 * 删除操作
	 * @param id
	 */
	public void deleteTmField(Integer id);
}
