package com.jjb.acl.access.web.tags;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.access.facade.UserInfoHolder;
import com.jjb.acl.access.service.AccessBranchService;
import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.access.service.AccessParamService;
import com.jjb.acl.access.service.AccessTableService;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.web.tags.UnicornFunctionRouterTag;


@Component
public class DictTag extends UnicornFunctionRouterTag{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private AccessTableService accessTableService;
	@Autowired
	private AccessParamService accessParamService;
	@Autowired
	private AccessBranchService accessBranchService;
	@SuppressWarnings("rawtypes")
	public boolean buttonAuth(List arguments){
		if(!argsSizeLess(arguments,2)){
			return false;
		}
		
		String resourceCode = getString(arguments.get(1));
		if(StrKit.isBlank(resourceCode)){
			return false;
		}
		return UserInfoHolder.checkPermission(resourceCode);
	}

	/**
	 * 根据类型，获取业务字典列表
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 字典类型
	 * 
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<TmAclDict> list(List arguments){
		if(!argsSizeLess(arguments,2)){
			return null;
		}
		
		String type = arguments.get(1).toString();
		List<TmAclDict> list = accessDictService.getByType(type);
		if(list==null){
			list = new ArrayList<TmAclDict>();
		}
		return list ;
	}
	
	/**
	 * 根据类型，获取业务字典map
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 字典类型
	 * 
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String,String> map(List arguments){
		if(!argsSizeLess(arguments,2)){
			return null;
		}
		
		String type = arguments.get(1).toString();
		List<TmAclDict> list = accessDictService.getByType(type);
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if(list!=null&&list.size()>0){
			for(TmAclDict dict:list){
				map.put(dict.getCode(), dict.getCodeName());
			}
		}
		
		return map ;
	}
	
	/**
	 * 获取单个业务字典
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 字典类型
	 * arguments[2] 字典code
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public TmAclDict dict(List arguments){
		if(!argsSizeLess(arguments,3)){
			return null;
		}
		
		String type = arguments.get(1).toString();
		String code = getString(arguments.get(2));
		TmAclDict tmAclDict = accessDictService.get(type,code);
		return tmAclDict ;
	}
	
	/**
	 * 获取单个业务字典
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 字典类型
	 * arguments[2] 字典code
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String name(List arguments){
		if(!argsSizeLess(arguments,3)){
			return "";
		}
		
		String type = arguments.get(1).toString();
		String code = getString(arguments.get(2));
		TmAclDict tmAclDict = accessDictService.get(type,code);
		return tmAclDict.getCodeName() ;
	}
	
	
	/**
	 * 根据表实体，获取表数据的列表
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> tableList(List arguments){
		if(!argsSizeLess(arguments,3)){
			return null;
		}
		
		String tableClass = arguments.get(1).toString();
		Object json = arguments.get(2);
		List list = new ArrayList();
		
		Class c = null;
		Object entity = null;
		try {
			c = Class.forName(tableClass);
			entity = JSON.parseObject(json.toString(), c);
			list = accessTableService.getTable(entity);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("根据表实体，获取表数据的列表 失败 entity["+entity+"]", e);
		}
		return list ;
	}
	
	/**
	 * 根据表实体，获取表数据的map
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * arguments[3] keyField
	 * arguments[4] valueField
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap<String,Object> tableMap(List arguments){
		if(!argsSizeLess(arguments,3)){
			return null;
		}


		String method= arguments.get(1).toString();
		String filters=arguments.get(2).toString();

		if (method.equals("branchMap")){
			TmAclBranch branch = new TmAclBranch();
			branch.setBranchCode(filters);
			return accessBranchService.getLinkedHashMapBranchByParm(branch);
		}

		String tableClass = arguments.get(1).toString();
		Object json = arguments.get(2);
		String keyField = getString(arguments.get(3));
		String valueField = getString(arguments.get(4));
		
		List list = new ArrayList();
		
		Class c = null;
		Object entity = null;
		try {
			c = Class.forName(tableClass);
			entity = JSON.parseObject(json.toString(), c);
			list = accessTableService.getTable(entity);
		} catch (Exception e) {
			logger.error("根据表实体，获取表数据的map 失败 entity ["+entity+"] ", e);
		}
		LinkedHashMap chMap = listToKeyValueMap(list,keyField,valueField);
		//这样在服务器上才能编译通过
		LinkedHashMap<String,Object> resMap = new LinkedHashMap<String,Object>();
		Iterator iterator = chMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();
			resMap.put(entry.getKey().toString(), entry.getValue());
		}
		
		return resMap;
	}
	
	@SuppressWarnings("rawtypes")
	public TmAclParam logoImg(List arguments){
		return accessParamService.getLogoImg();
	}
	
	@SuppressWarnings("rawtypes")
	public TmAclParam webappTitle(List arguments){
		return accessParamService.getWebappTitle();
	}
	
}
