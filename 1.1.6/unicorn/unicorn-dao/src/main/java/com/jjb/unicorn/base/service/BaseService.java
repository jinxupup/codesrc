package com.jjb.unicorn.base.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public abstract class BaseService <T> {
	
	@Autowired
	private BaseDao<T> baseDao;
	

	/**
	 *保存
	 * @param entity
	 * @return
	 */
	@Transactional
	public final int save (T entity) {

		return getBaseDao().save(entity);
	}
	
	
	/**
	 *保存
	 * @param entity
	 */
	@Transactional
	public final void update (T entity) {
		
		getBaseDao().update(entity);
	}
	
	
	/**
	 *保存
	 * @param key
	 * @return
	 */
	@Transactional
	public final int deleteByKey (T entity) {
		
		return getBaseDao().deleteByKey(entity);
	}
	
	
	/**
	 *保存
	 * @param key
	 * @return
	 */
	public final T queryByKey (T entity) {
		
		return getBaseDao().queryByKey(entity);
	}
	
	
	/**
	 * 分页查询
	 * @param sqlId
	 * @param parameter
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<T> queryForPageList (String sqlId,T parameter,Page<T> page) {
		
		return getBaseDao().queryForPageList(sqlId,parameter,page);
	}
	
	
	public Page<T> queryForPageList (String sqlId,Map<String,Object> parameter ,Page<T> page) {
		
		return getBaseDao().queryForPageList(sqlId, parameter, page);
	}
	
	
	/**
	 * 通用查询
	 * @param sqlId
	 * @param parameter
	 * @return
	 */
	public final List<T> queryForList(String sqlId, T parameter) {
		
		return getBaseDao().queryForList(sqlId, parameter);
	}
	
	
	public List<T> queryForList (String sqlId,Map<String,Object> parameter) {
		
		return getBaseDao().queryForList(sqlId, parameter);
	}

	
	public final List<T> queryForList(T parameter) {
		return getBaseDao().queryForList( parameter);
	}
	
	public final List<T> queryForList(T parameter,Map<String,Object> map) {
		return getBaseDao().queryForList( parameter,map);
	}
	
	protected BaseDao<T> getBaseDao() {
		return baseDao;
	}
	
	
	protected SqlSessionTemplate getSqlSession () {
		
		return getBaseDao().getSqlSession();
	}


	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
}
