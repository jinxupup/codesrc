package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmAppExcePool;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

import java.util.List;

/**
 * 1.申请件异常清单
 * @author hejn
 *
 */
public interface TmAppExcePoolDao extends BaseDao<TmAppExcePool> {
	/**
	 * 1.分页查询异常申请件列表数据
	 * @param page
	 * @return
	 */
	public Page<TmAppExcePool> getTmAppExcePoolPage(Page<TmAppExcePool> page);
	
	/**
	 * 根据申请件编号获取所有异常申请件列表
	 * 
	 * @param TmAppExcePool
	 * @return
	 */
	public List<TmAppExcePool> getTmAppExcePoolList(TmAppExcePool tmAppExcePool);

	public void saveTmAppExcePool(TmAppExcePool tmAppExcePool);

	public void updateTmAppExcePool(TmAppExcePool tmAppExcePool);
	
	/**
	 * 根据申请件编号删除异常申请件列表
	 * 
	 * @param TmAppExcePool
	 * @return
	 */
	public void deleteTmAppExcePool(TmAppExcePool tmAppExcePool);
}