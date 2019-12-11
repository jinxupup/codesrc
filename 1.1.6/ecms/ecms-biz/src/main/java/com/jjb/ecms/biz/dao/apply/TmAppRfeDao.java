package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppRfe;
import com.jjb.unicorn.base.dao.BaseDao;
/**
 * 
 * @Description: 补件
 * @author JYData-R&D-HN
 * @date 2016年10月21日 下午11:48:03
 * @version V1.0
 */
public interface TmAppRfeDao extends BaseDao<TmAppRfe>{
	
	/**
	 * 保存补件信息
	 * @param tmAppRfe
	 */
	public void saveTmAppRfe(TmAppRfe tmAppRfe);
	
	/**
	 * 删除补件信息
	 * @param tmAppRfe
	 */
	public void deleteTmAppRfe(TmAppRfe tmAppRfe);
	/**
	 * 获取补件列表
	 * @param tmAppRfe
	 * @return List
	 */
	public List<TmAppRfe> getTmAppRfeByParam(TmAppRfe tmAppRfe); 
	
	
	public TmAppRfe getTmAppRfeByKey(Integer key);

}