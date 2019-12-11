package com.jjb.unicorn.base.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.jjb.unicorn.facility.model.Page;


/**
 * dao
 * @author jjb
 *
 * @param <T>
 */
public interface BaseDao <T> {
	
	
	/**
	 *保存对象
	 * @param entity
	 * @return
	 */
	public int save (T entity);
	
	
	/**
	 *保存对象
	 * @param sqlId
	 * @param entity
	 * @return
	 */
	public int save (String sqlId,T entity);
	
	
	/**
	 *更新对象
	 * @param entity
	 */
	public void update (T entity);
	
	
	/**
	 * 更新对象，不能更新空值
	 * @param entity
	 */
	public void updateNotNullable (T entity);
	
	
	
	/**
	 *更新对象
	 * @param sqlId
	 * @param entity
	 */
	public void update (String sqlId,T entity);
	
	
	
	/**
	 *通过主键删除
	 * @param key
	 * @return
	 */
	public int deleteByKey (T entity);
	
	
	/**
	 *删除
	 * @param sql
	 * @param entity
	 * @return
	 */
	public int delete (String sqlId ,T entity);
	
	
	/**
	 *通过主键查询
	 * @param key
	 * @return
	 */
	public T queryByKey (T entity);
	
	
	/**
	 *通用查询
	 * @param sqlId
	 * @param parameter
	 * @return
	 */
	public List<T> queryForList (String sqlId,T parameter);
	
	
	/**
	 * 通用查询
	 * @param sqlId
	 * @param parameter
	 * @return
	 */
	public List<T> queryForList (String sqlId,Map<String,Object> parameter);
	
		
	/**
	 * 查询对象列表
	 * @param entity
	 * @return
	 */
	public List<T> queryForList (T entity,Map<String,Object> params);
	
	
	/**
	 * 查询对象列表
	 * @param entity
	 * @return
	 */
	public List<T> queryForList (T entity);
	
	
	/**
	 * 
	 * @Description 返回单条数据，如果数据库查询多条，则抛异常
	 * @param entity
	 * @return
	 */
	public T queryForOne (T entity);
	
	
	/**
	 * 
	 * @Description 返回单条数据，如果数据库查询多条，则抛异常
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public <E> E queryForOne (String sqlId,Map<String,Object> params);
	
	
	/**
	 *查询返回一个map对象
	 * 
	 * @param sqlId
	 * @param parameter
	 * @param mapKey
	 * @return
	 */
	public Map<String,Object> queryForMap (String sqlId,Object parameter,String mapKey);
	

	/**
	 *分页对象
	 * @param entity
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Page<T> queryForPageList (String sqlId,T parameter,Page<T> page);
	
	
	/**
	 * 通用分页查询
	 * @param sqlId
	 * @param parameter
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<T> queryForPageList (String sqlId,Map<String,Object> parameter ,Page<T> page);
	
	
	/**
	 * 分页查询
	 * @param entity
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<T> queryForPageList (T entity,Map<String,Object> params,Page<T> page);
	
	/**
	 * 分页查询
	 * @param entity
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<T> queryForPageList (T entity,Page<T> page);
	
		
	/**
	 * 获取mybatis的sqlSession对象
	 * @return
	 */
	public SqlSessionTemplate getSqlSession() ;
	
	
	/**
	 * 
	 * @Description 通用保存
	 * @param obj
	 */
	public void savePlain (Object obj);
	
	
	/**
	 * 
	 * @Description 通用更新
	 * @param obj
	 */
	public void updatePlain (Object obj);
	
	
	/**
	 * 
	 * @Description 通用查询列表
	 * @param obj
	 * @param param
	 * @return
	 */
	public List<Object> queryList (Object obj,Map<String,Object> param);
	
	
	/**
	 * 
	 * @Description 通用查询单个对象
	 * @param obj
	 * @param param
	 * @return
	 */
	public Object queryOne (Object obj,Map<String,Object> param);
	
	
	public <E> List<E> loadKeyList (T entity,Map<String,Object> params);
	
	
	public <E> List<E> loadKeyList (T entity);
	
	
	public <E> List<E> loadKeyList(String sqlId,Map<String,Object> params);
}
