package com.jjb.dmp.biz.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.access.service.AccessTableService;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.dmp.biz.dao.TmDmpStrategyCategoryDao;
import com.jjb.dmp.biz.dao.TmDmpStrategyFunctionDao;
import com.jjb.dmp.biz.dao.TmDmpStrategyVarDao;
import com.jjb.dmp.biz.dao.TmDmpVarDao;
import com.jjb.dmp.biz.service.StrategyCategoryService;
import com.jjb.dmp.engine.bean.TableOptionBean;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.enums.OptionType;
import com.jjb.dmp.infrastructure.TmDmpStrategyCategory;
import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Transactional
@Service("strategyCategoryService")
public class StrategyCategoryServiceImpl implements StrategyCategoryService {

	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private AccessTableService accessTableService;
	
	@Autowired
	private TmDmpStrategyCategoryDao tmDmpStrategyCategoryDao;
	@Autowired
	private TmDmpStrategyVarDao tmDmpStrategyVarDao;
	@Autowired
	private TmDmpStrategyFunctionDao tmDmpStrategyFunctionDao;
	@Autowired
	private TmDmpVarDao tmDmpVarDao;
	
	
	@Override
	public TmDmpStrategyCategory getTmDmpStrategyCategory(String stClass){
		return tmDmpStrategyCategoryDao.getByClass(stClass);
	}

	@Override
	public Page<TmDmpStrategyCategory> getPage(Page<TmDmpStrategyCategory> page) {
		
		page = tmDmpStrategyCategoryDao.queryPage(page);
	
		return page;
	}

	@Override
	@Transactional
	public void saveTmDmpStrategyCategory(TmDmpStrategyCategory tmDmpStrategyCategory) {
		TmDmpStrategyCategory tmDmpStrategyCategoryDb = tmDmpStrategyCategoryDao.queryByKey(tmDmpStrategyCategory);
		if(tmDmpStrategyCategoryDb!=null){
			throw new RuntimeException("已存在此类的策略方案类别");
		}
		tmDmpStrategyCategoryDao.save(tmDmpStrategyCategory);
	}

	@Override
	public void editTmDmpStrategyCategory(TmDmpStrategyCategory tmDmpStrategyCategory) {
		
		TmDmpStrategyCategory tmDmpStrategyCategoryDb = tmDmpStrategyCategoryDao.getByClass(tmDmpStrategyCategory.getStClass());
		if(tmDmpStrategyCategoryDb==null){
			throw new BizException("找不到该类的策略方案类别");
		}
		tmDmpStrategyCategory.setJpaVersion(tmDmpStrategyCategoryDb.getJpaVersion());
		BeanUtil.merge(tmDmpStrategyCategoryDb, tmDmpStrategyCategory);
		
		tmDmpStrategyCategoryDao.update(tmDmpStrategyCategoryDb);
	}

	@Override
	public void deleteTmDmpStrategyCategory(String stClass) {
		TmDmpStrategyCategory tmDmpStrategyCategory = new TmDmpStrategyCategory();
		tmDmpStrategyCategory.setStClass(stClass);
		tmDmpStrategyCategoryDao.deleteByKey(tmDmpStrategyCategory);
	}

	@Override
	public Map<String, FieldVar> getInputFieldVars(String stClass) {
		
		Map<String, FieldVar> fieldVarMap = new HashMap<String, FieldVar>();
		
//		TmDmpStrategyVar tmDmpStrategy = new TmDmpStrategyVar();
//		tmDmpStrategy.setIfUsed("Y");
//		tmDmpStrategy.setIsInput("Y");
//		tmDmpStrategy.setStClass(stClass);
//		List<TmDmpStrategyVar> stVars = tmDmpStrategyVarDao.queryForList(tmDmpStrategy);
//		List<FieldVar> fieldVars = generateFieldVar(stVars);
		
		List<TmDmpVar> stVars =  tmDmpVarDao.selectByStClass(stClass,true,false);
		List<FieldVar> fieldVars = generateFieldVar(stVars);
		
		
		for(FieldVar v:fieldVars){
			fieldVarMap.put(v.getVarCd(), v);
		}
		return fieldVarMap;
	}
	
	@Override
	public Map<String, FieldVar> getOutputFieldVars(String stClass) {
		
		Map<String, FieldVar> fieldVarMap = new HashMap<String, FieldVar>();
		
//		TmDmpStrategyVar tmDmpStrategy = new TmDmpStrategyVar();
//		tmDmpStrategy.setIfUsed("Y");
//		tmDmpStrategy.setIsOutput("Y");
//		tmDmpStrategy.setStClass(stClass);
//		List<TmDmpStrategyVar> stVars = tmDmpStrategyVarDao.queryForList(tmDmpStrategy);
//		List<FieldVar> fieldVars = generateFieldVar(stVars);
		
		List<TmDmpVar> stVars =  tmDmpVarDao.selectByStClass(stClass,false,true);
		List<FieldVar> fieldVars = generateFieldVar(stVars);
		
		for(FieldVar v:fieldVars){
			fieldVarMap.put(v.getVarCd(), v);
		}
		return fieldVarMap;
	}
	
	@Override
	public List<FieldVar> generateFieldVar(List<TmDmpVar> vars ){
		List<FieldVar> fieldVars = new ArrayList<FieldVar>();
		for(TmDmpVar sv : vars){
			FieldVar fieldVar = new FieldVar();
			
//			fieldVar.setStClass(sv.getStClass());
			fieldVar.setVarCd(sv.getVarCd());
			fieldVar.setVarName(sv.getVarName());
			fieldVar.setDataType(sv.getDataType());
			fieldVar.setOptionType(sv.getOptionType());
			fieldVar.setOptionParam(sv.getOptionParam());
			
			List<Map<String, Object>> options = new ArrayList<Map<String,Object>>();
			
			
			if(OptionType.A.name().equals(sv.getOptionType())){
				
			}else if(OptionType.D.name().equals(sv.getOptionType())){
				List<TmAclDict> dicts = accessDictService.getByType(fieldVar.getOptionParam());
				if(StrKit.notBlankList(dicts)){
					options = new ArrayList<Map<String,Object>>();
					for(TmAclDict d:dicts){
						Map<String, Object> option = new HashMap<String, Object>();
						option.put("cd", d.getCode());
						option.put("name", d.getCodeName());
						options.add(option);
					}
				}
			}else if(OptionType.E.name().equals(sv.getOptionType())){
				//TODO 枚举处理
			}else if(OptionType.T.name().equals(sv.getOptionType())){
				//从表取
				String optionParam = sv.getOptionParam();
				TableOptionBean tableOptionBean = JSONObject.parseObject(optionParam,TableOptionBean.class);
				try {
					Class<?> c = Class.forName(tableOptionBean.getModel());
					Object entity = c.newInstance();
					BeanUtils.populate(entity, tableOptionBean.getFilter());
					
					List<?> list = accessTableService.getTable(entity);
					
					for(int i=0;i<list.size();i++ ){
						Object obj = list.get(i);
						Method getKeyMethod = obj.getClass().getDeclaredMethod("get" + StrKit.firstCharToUpperCase(tableOptionBean.getKeyField()));
						Method getValueMethod = obj.getClass().getDeclaredMethod("get" + StrKit.firstCharToUpperCase(tableOptionBean.getValueField()));
						String key = getKeyMethod.invoke(obj).toString();
						Object value = getValueMethod.invoke(obj);
						
						Map<String, Object> option = new HashMap<String, Object>();
						option.put("cd", key);
						option.put("name", value);
						options.add(option);
					}
					
				} catch (Exception e){
					e.printStackTrace();
					throw new BizException("决策变量类别"+sv.getVarCd()+"，选项参数配置错误："+e.getMessage());
				}
			}
			
			fieldVar.setOptions(options);
			fieldVars.add(fieldVar);
		}
		return fieldVars;
	}
	
	@Override
	public List<TmDmpStrategyVar> getFieldVar(String stClass){
		
		TmDmpStrategyVar tmDmpStrategy = new TmDmpStrategyVar();
//		tmDmpStrategy.setIfUsed("Y");
		tmDmpStrategy.setIsInput("Y");
		tmDmpStrategy.setStClass(stClass);
		List<TmDmpStrategyVar> stVars = tmDmpStrategyVarDao.queryForList(tmDmpStrategy);
		
		return stVars;
	}
}