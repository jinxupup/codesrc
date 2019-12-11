package com.jjb.unicorn.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public abstract class AbstractBaseDao<T> implements BaseDao<T> {
	
	@Autowired
	private BaseDaoImpl<T> baseDao;

	public int save(T entity) {
		
		return baseDao.save(entity);
	}

	public int save(String sqlId, T entity) {
		
		return baseDao.save(sqlId, entity);
	}

	public void update(T entity) {
		
		baseDao.update(entity);
		
	}

	public void update(String sqlId, T entity) {
		
		baseDao.update(sqlId, entity);
	}

	public int deleteByKey(T entity) {
		
		return baseDao.deleteByKey(entity);
	}

	public int delete(String sqlId, T entity) {
		
		return baseDao.delete(sqlId, entity);
	}

	public T queryByKey(T entity) {
		
		return baseDao.queryByKey(entity);
	}

	public List<T> queryForList(String sqlId, T parameter) {
		
		return baseDao.queryForList(sqlId, parameter);
	}

	public Map<String, Object> queryForMap(String sqlId, Object parameter,
			String mapKey) {
		
		return baseDao.queryForMap(sqlId, parameter, mapKey);
	}

	public Page<T> queryForPageList(String sqlId, T parameter, Page<T> page) {
		
		return baseDao.queryForPageList(sqlId, parameter,page);
	}
	
	
	public final SqlSessionTemplate getSqlSession() {
		
		return baseDao.getSqlSession();
	}

	@Override
	public List<T> queryForList(T entity,Map<String,Object> params) {
		
		return baseDao.queryForList(entity,params);
	}

	@Override
	public Page<T> queryForPageList(T entity, Map<String,Object> params, Page<T> page) {
		
		return baseDao.queryForPageList(entity,params,page);
	}

	@Override
	public List<T> queryForList(String sqlId, Map<String, Object> parameter) {
		
		return baseDao.queryForList(sqlId, parameter);
	}

	@Override
	public Page<T> queryForPageList(String sqlId,
			Map<String, Object> parameter, Page<T> page) {
		
		return baseDao.queryForPageList(sqlId, parameter, page);
	}

	@Override
	public List<T> queryForList(T entity) {
		
		return baseDao.queryForList(entity);
	}

	
	@Override
	public Page<T> queryForPageList(T entity, Page<T> page) {
		
		return baseDao.queryForPageList(entity, page);
	}

	@Override
	public void savePlain(Object obj) {
		
		baseDao.savePlain(obj);
	}

	@Override
	public List<Object> queryList(Object obj, Map<String, Object> param) {
		
		return baseDao.queryList(obj, param);
	}

	@Override
	public Object queryOne(Object obj, Map<String, Object> param) {
		
		return baseDao.queryOne(obj, param);
	}

	@Override
	public void updatePlain(Object obj) {
		
		baseDao.updatePlain(obj);
	}

	@Override
	public void updateNotNullable(T entity) {
		
		baseDao.updateNotNullable(entity);
	}

	@Override
	public <E> List<E> loadKeyList(T entity, Map<String, Object> params) {
		
		return baseDao.loadKeyList(entity, params);
	}

	@Override
	public <E> List<E> loadKeyList(T entity) {
		
		return baseDao.loadKeyList(entity);
	}

	@Override
	public <E> List<E> loadKeyList(String sqlId, Map<String, Object> params) {
		
		return baseDao.loadKeyList(sqlId, params);
	}

	@Override
	public T queryForOne(T entity) {
		
		return baseDao.queryForOne(entity);
	}

	@Override
	public <E> E queryForOne(String sqlId, Map<String, Object> params) {
		
		return baseDao.queryForOne(sqlId, params);
	}
	
}
