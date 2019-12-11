package com.jjb.unicorn.base.dao.impl;

import static org.springframework.util.Assert.notNull;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.github.pagehelper.PageHelper;
import com.jjb.unicorn.base.Constants;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.exception.UnicornException;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author jjb
 *
 * @param <T>
 */
class BaseDaoImpl<T> implements BaseDao<T>,InitializingBean {
	
	private static final String INSERT_STATEMENT = ".insert";
	
	private static final String UPDATE_STATEMENT = ".updateByPrimaryKey";
	
	private static final String UPDATE_NOT_NULL_STATEMENT = ".updateNotNullByPrimaryKey";
	
	private static final String DELETE_STATEMENT = ".deleteByPrimaryKey";
	
	private static final String SELECT_STATEMENT = ".selectByPrimaryKey";
	
	private static final String SELECT_ALL_STATEMENT = ".selectAll";
	
	private static final String SELECT_KEY_LIST = ".loadKeyList";
	
	@Autowired
	private SqlSessionTemplate sqlSession; 
	
	private Resource[] mapperLocations;
	
	private Map<String,String> typeNameSpaceMapping = new HashMap<String,String> ();
	
	private static final String SUFFIX  = "Mapper.xml";
	
	private String suffix = SUFFIX;
	

	public  final int  save(T entity) {
		
		return sqlSession.insert(obtainNameSpace (entity.getClass()) + INSERT_STATEMENT, entity);
	}

	
	public final void update(T entity) {
		
		sqlSession.update(obtainNameSpace (entity.getClass()) + UPDATE_STATEMENT, entity);
	}

	
	public final int deleteByKey(T entity) {
		
		return sqlSession.delete(obtainNameSpace (entity.getClass()) + DELETE_STATEMENT, buildMap(entity.getClass(),entity));
	}

	
	public final T queryByKey (T entity) {
		
		return sqlSession.selectOne(obtainNameSpace (entity.getClass()) + SELECT_STATEMENT , buildMap(entity.getClass(),entity));
	}
	
	
	public final List<T> queryForList(String sqlId, T parameter) {
		
		return sqlSession.selectList(sqlId, parameter);
	}

	
	public final SqlSessionTemplate getSqlSession() {
		
		return sqlSession;
	}

	
	@SuppressWarnings("rawtypes")
	private String obtainNameSpace (Class clazz)  {
		String modelName = clazz.getSimpleName();
		
		return obtainNamespace(modelName);
	}
	
	
	public void setMapperLocations(Resource[] mapperLocations) {
		
	    this.mapperLocations = mapperLocations;
	}
	
	private String obtainNamespace (String modelName) {
		String namespace = typeNameSpaceMapping.get(modelName);
		
		if (namespace != null && namespace.length () >0) {
			return namespace;
		}
		
		for (Resource mapperLocation : this.mapperLocations) {
	        if (mapperLocation == null) {
	            continue;
	        }
	        
	        String mappingFileName = mapperLocation.getFilename();
	        String keyName = null;
	        if ((mappingFileName != null && mappingFileName.length()>0) && mappingFileName.endsWith(suffix)) {
	        	int pos = mappingFileName.lastIndexOf(suffix);
	        	if (pos > 0) {
	        		keyName = mappingFileName.substring(0, pos);
	        	}
	        }
	        
	        if (keyName== null || "".equals(keyName)) {
	        	continue;
	        }
	        
	        if (!modelName.equals(keyName)) {
	        	continue;
	        }
	       	       
	        try {
	        	Document document = createDocument(new InputSource(mapperLocation.getInputStream()));
	        	Element root = document.getDocumentElement();
	        	String nameSpace = root.getAttribute("namespace");
	        	
	        	typeNameSpaceMapping.put(keyName, nameSpace);
	        	
	        	return nameSpace;
	        	
	        } catch (Exception e) {
	        	throw new RuntimeException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
	        } finally {
	        	try {
	        		mapperLocation.getInputStream().close();
	        	} catch (Exception e) {
	        		;
	        	}
	        }
	   }
		
	   throw new RuntimeException ("not found mapping file for "+modelName);
	}
	
	

	
	final public void afterPropertiesSet() throws Exception {
		notNull(mapperLocations, "Property 'mapperLocations' is required");
	}
	
	private Document createDocument(InputSource inputSource) {
	    // important: this must only be called AFTER common constructor
	    try {
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      factory.setValidating(false);

	      factory.setNamespaceAware(false);
	      factory.setIgnoringComments(true);
	      factory.setIgnoringElementContentWhitespace(false);
	      factory.setCoalescing(false);
	      factory.setExpandEntityReferences(true);

	      DocumentBuilder builder = factory.newDocumentBuilder();
	      builder.setEntityResolver(new XMLMapperEntityResolver());
	      builder.setErrorHandler(new ErrorHandler() {
	        public void error(SAXParseException exception) throws SAXException {
	          throw exception;
	        }

	        public void fatalError(SAXParseException exception) throws SAXException {
	          throw exception;
	        }

	        public void warning(SAXParseException exception) throws SAXException {
	        }
	      });
	      return builder.parse(inputSource);
	    } catch (Exception e) {
	      throw new BuilderException("Error creating document instance.  Cause: " + e, e);
	    }
	}
	
	
	@SuppressWarnings("rawtypes")
	private Map<String,Object> buildMap (Class clazz,Object beanInstance) {
		Map<String,Object> map = null;
		try {
			map = buildMapFromBean(clazz,beanInstance);
		} catch (Exception e) {
			map = new HashMap<String,Object> ();
		}
		
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String,Object> buildMapWithParams (Class clazz,Object beanInstance , Map<String,Object> params) {
		Map<String,Object> map = null;
		try {
			map = buildMapFromBean(clazz,beanInstance);
		} catch (Exception e) {
			map = new HashMap<String,Object> ();
		}
		if (params != null && params.size()>0) {
			map.putAll(params);
		}
		
		return map;
	}
	
	
	@SuppressWarnings("rawtypes")
	private Map<String,Object> buildMapFromBean (Class clazz,Object beanInstance) throws Exception {
		Map<String,Object> context = new HashMap<String,Object> ();
		
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor []pds = beanInfo.getPropertyDescriptors();
		
		if (null == pds) {
			return context;
		}
		
		for (PropertyDescriptor pd : pds) {
			context.put(pd.getName(),pd.getReadMethod().invoke(beanInstance, new Object[] {}));
		}
				
		return context;
	}

	
	public String getSuffix() {
		
		return suffix;
	}

	
	public void setSuffix(String suffix) {
		
		this.suffix = suffix;
	}

	
	public Page<T> queryForPageList(String sqlId, T parameter, Page<T> page) {
		
		com.github.pagehelper.Page<T> pageTemp = PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
		queryForList(sqlId, parameter);
		
		page.setTotal(pageTemp.getTotal());
		page.setRows(pageTemp.getResult());
		
		return page;
	}

	
	public int save(String sqlId, T entity) {
		
		return sqlSession.insert(sqlId, entity);
	}

	
	public void update(String sqlId, T entity) {
		
		sqlSession.update(sqlId, entity);
		
	}

	
	public int delete(String sqlId, T entity) {
		
		return sqlSession.delete(sqlId, entity);
	}


	public Map<String, Object> queryForMap(String sqlId, Object parameter,String mapKey) {
		
		return sqlSession.selectMap(sqlId, parameter, mapKey);
	}


	@Override
	public List<T> queryForList(T entity, Map<String,Object> params) {
		
		return sqlSession.selectList(obtainNameSpace (entity.getClass()) + SELECT_ALL_STATEMENT , buildMapWithParams(entity.getClass(),entity,params));
	}


	@Override
	public Page<T> queryForPageList(T entity, Map<String,Object> params, Page<T> page) {
		com.github.pagehelper.Page<T> pageTemp = PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
		if (params != null) {
			params.putAll(page.getQuery());
		} else {
			params = page.getQuery();
		}
		
		queryForList(entity,params);
		
		page.setTotal(pageTemp.getTotal());
		page.setRows(pageTemp.getResult());
		
		return page;
	}


	@Override
	public List<T> queryForList(String sqlId,Map<String, Object> parameter) {
		
		return sqlSession.selectList(sqlId , parameter);
	}


	@Override
	public Page<T> queryForPageList(String sqlId,
			Map<String, Object> parameter,Page<T> page) {
		
		com.github.pagehelper.Page<T> pageTemp = PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
		if (parameter != null) {
			parameter.putAll(page.getQuery());
		} else {
			parameter = page.getQuery();
		}
		queryForList(sqlId, parameter);
		
		page.setTotal(pageTemp.getTotal());
		page.setRows(pageTemp.getResult());
		return page;
	}


	@Override
	public List<T> queryForList(T entity) {
		
		return sqlSession.selectList(obtainNameSpace (entity.getClass()) + SELECT_ALL_STATEMENT , buildMap(entity.getClass(),entity));
	}


	@Override
	public Page<T> queryForPageList(T entity, Page<T> page) {
		
		com.github.pagehelper.Page<T> pageTemp = PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
		queryForList(entity);
		
		page.setTotal(pageTemp.getTotal());
		page.setRows(pageTemp.getResult());
		
		return page;
	}


	@Override
	public void savePlain(Object obj) {
		
		sqlSession.insert(obtainNameSpace (obj.getClass()) + INSERT_STATEMENT, obj);
	}


	@Override
	public List<Object> queryList(Object obj, Map<String, Object> param) {
		
		return sqlSession.selectList(obtainNameSpace (obj.getClass()) + SELECT_ALL_STATEMENT , buildMapWithParams(obj.getClass(),obj,param));
	}


	@Override
	public Object queryOne(Object obj, Map<String, Object> param) {
		
		return sqlSession.selectOne(obtainNameSpace (obj.getClass()) + SELECT_STATEMENT, buildMapWithParams(obj.getClass(),obj,param));
	}


	@Override
	public void updatePlain(Object obj) {
		
		sqlSession.update(obtainNameSpace (obj.getClass()) + UPDATE_STATEMENT, obj);
	}


	@Override
	public void updateNotNullable(T entity) {
		
		sqlSession.update(obtainNameSpace (entity.getClass()) + UPDATE_NOT_NULL_STATEMENT, entity);
	}


	@Override
	public <E> List<E> loadKeyList(T entity, Map<String, Object> params) {
		
		return sqlSession.selectList(obtainNameSpace (entity.getClass()) + SELECT_KEY_LIST , buildMapWithParams(entity.getClass(),entity,params));
	}


	@Override
	public <E> List<E> loadKeyList(T entity) {
		
		return sqlSession.selectList(obtainNameSpace (entity.getClass()) + SELECT_KEY_LIST , buildMap(entity.getClass(),entity));
	}


	@Override
	public <E> List<E> loadKeyList(String sqlId, Map<String, Object> params) {
		
		return sqlSession.selectList(sqlId, params);
	}


	@Override
	public T queryForOne(T entity) {
		List<T> lists = this.queryForList(entity);
		if (lists == null || lists.size() == 0) {
			return null;
		}
		
		if (lists.size() >1) {
			throw new UnicornException(Constants.ERROR_CODE_110001,"存在多条数据");
		}
		
		return lists.get(0);
	}


	@Override
	public <E> E queryForOne(String sqlId, Map<String, Object> params) {
		List<E> lists =sqlSession.selectList(sqlId, params);
		if (lists == null || lists.size() == 0) {
			return null;
		}
		
		if (lists.size() >1) {
			throw new UnicornException(Constants.ERROR_CODE_110001,"存在多条数据");
		}
		
		return lists.get(0);
	}
}
