package com.jjb.dmp.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.dao.TmDmpStrategyDao;
import com.jjb.dmp.biz.dao.TmDmpVarDao;
import com.jjb.dmp.biz.service.DrlRuleService;
import com.jjb.dmp.biz.service.RulesetService;
import com.jjb.dmp.biz.service.StrategyCategoryService;
import com.jjb.dmp.biz.service.StrategyFunctionService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.biz.service.StrategyVarService;
import com.jjb.dmp.engine.kit.StragegyVarInstance;
import com.jjb.dmp.engine.model.AbstractRuleSetVar;
import com.jjb.dmp.engine.model.DecisionTableVar;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.FunctionVar;
import com.jjb.dmp.engine.model.SimpleRuleSetVar;
import com.jjb.dmp.engine.model.StragegyVar;
import com.jjb.dmp.engine.model.enums.DataType;
import com.jjb.dmp.engine.model.enums.RuleSetType;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.dmp.infrastructure.TmDmpStrategyCategory;
import com.jjb.dmp.infrastructure.TmDmpStrategyFunction;
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
@Service("strategyService")
public class StrategyServiceImpl implements StrategyService {

	@Autowired
	private TmDmpVarDao tmDmpVarDao;
	
	@Autowired
	private TmDmpStrategyDao tmDmpStrategyDao;
	
	@Autowired
	private StrategyVarService strategyVarService;
	
	@Autowired
	private StrategyFunctionService strategyFunctionService;
	
	@Autowired
	private DrlRuleService drlRuleService;
	
	@Autowired
	private RulesetService rulesetService;
	
	@Autowired
	private StrategyCategoryService strategyCategoryService;
	
	@Override
	public TmDmpStrategy getTmDmpStrategy(String stId){
		return tmDmpStrategyDao.getByKey(stId);
	}

	@Override
	public Page<TmDmpStrategy> getPage(Page<TmDmpStrategy> page) {
		
		page = tmDmpStrategyDao.queryPage(page);
	
		return page;
	}

	@Override
	@Transactional
	public void saveTmDmpStrategy(TmDmpStrategy tmDmpStrategy) {
		TmDmpStrategy tmDmpStrategyDb = new TmDmpStrategy();
		tmDmpStrategyDb.setStName(tmDmpStrategy.getStName());
		List<TmDmpStrategy> list = tmDmpStrategyDao.queryForList(tmDmpStrategyDb);
		if(StrKit.notBlankList(list)){
			throw new RuntimeException("已存在此名称的策略方案");
		}
		tmDmpStrategyDao.save(tmDmpStrategy);
	}

	@Override
	public void editTmDmpStrategy(TmDmpStrategy tmDmpStrategy) {
		
		TmDmpStrategy tmDmpStrategyDb = tmDmpStrategyDao.getByKey(tmDmpStrategy.getStId());
		if(tmDmpStrategyDb==null){
			throw new BizException("找不到该id的策略方案");
		}
		tmDmpStrategy.setJpaVersion(tmDmpStrategyDb.getJpaVersion());
		tmDmpStrategy.setStClass(tmDmpStrategyDb.getStClass());
		tmDmpStrategy.setStObject(tmDmpStrategyDb.getStObject());
		BeanUtil.merge(tmDmpStrategyDb, tmDmpStrategy);
		tmDmpStrategyDao.update(tmDmpStrategyDb);
		
		//更新drl
		updateStrategyDrl(tmDmpStrategy.getStId());
	}

	@Override
	public void deleteTmDmpStrategy(String stId) {
		TmDmpStrategy tmDmpStrategy = new TmDmpStrategy();
		tmDmpStrategy.setStId(stId);
		tmDmpStrategyDao.deleteByKey(tmDmpStrategy);
	}

	/**
	 * 产生决策变量
	 * @param tmDmpStrategy
	 * @param inputFieldVars
	 * @param outputFieldVars
	 */
	@Override
	public void exchangeFieldVar(TmDmpStrategy tmDmpStrategy, List<FieldVar> inputFieldVars, List<FieldVar> outputFieldVars){
		
		if(tmDmpStrategy==null||StrKit.isBlank(tmDmpStrategy.getStClass())){
			return;
		}
		
//		List<TmDmpStrategyVar> strategyVars = strategyVarService.getVarList(tmDmpStrategy.getStClass());
//		
//		List<TmDmpStrategyVar> inputVars = new ArrayList<TmDmpStrategyVar>();
//		List<TmDmpStrategyVar> onputVars = new ArrayList<TmDmpStrategyVar>();
//		
//		for(TmDmpStrategyVar v:strategyVars){
//			if("Y".equals(v.getIfUsed())&&"Y".equals(v.getIsInput())){
//				inputVars.add(v);
//			}
//			if("Y".equals(v.getIfUsed())&&"Y".equals(v.getIsOutput())){
//				onputVars.add(v);
//			}
//		}
		
		List<TmDmpVar> inputVars =  tmDmpVarDao.selectByStClass(tmDmpStrategy.getStClass(),true,false);
		List<TmDmpVar> onputVars =  tmDmpVarDao.selectByStClass(tmDmpStrategy.getStClass(),false,true);
		
		inputFieldVars.addAll(strategyCategoryService.generateFieldVar(inputVars));
		outputFieldVars.addAll(strategyCategoryService.generateFieldVar(onputVars));
		
	}
	
	@Override
	public List<FieldVar> exchangeFunctionVar(TmDmpStrategy tmDmpStrategy){
		
		List<FieldVar> functionFieldVars = new ArrayList<FieldVar>();
		if(tmDmpStrategy==null||StrKit.isBlank(tmDmpStrategy.getStClass())){
			return functionFieldVars;
		}
		
		List<TmDmpStrategyFunction> strategyFunctions = strategyFunctionService.getFunctionList(tmDmpStrategy.getStClass());
		
		for(TmDmpStrategyFunction f:strategyFunctions){
			if("Y".equals(f.getIfUsed())){
				FieldVar fieldVar = new FieldVar();
				fieldVar.setVarCd(f.getFunCd());
				fieldVar.setVarName(f.getFunName());
				fieldVar.setDataType(f.getDataType());
				fieldVar.setOptionType("A");
				functionFieldVars.add(fieldVar);
			}
		}
		return functionFieldVars;
	}
	
	/**
	 * 产生策略对象
	 * @param stId
	 * @return
	 */
	@Override
	public StragegyVar genStragegyVar(String stId){
		
		StragegyVar stragegyVar = new StragegyVar();
		
		TmDmpStrategy tmDmpStrategy = getTmDmpStrategy(stId);
		if(tmDmpStrategy==null||StrKit.isBlank(tmDmpStrategy.getStClass())){
			return null;
		}
		String stClass = tmDmpStrategy.getStClass();
		
		TmDmpStrategyCategory tmDmpStrategyCategory = strategyCategoryService.getTmDmpStrategyCategory(stClass);
		if(tmDmpStrategyCategory==null){
			return null;
		}
		
		List<TmDmpStrategyFunction> strategyFunctionss = strategyFunctionService.getFunctionList(stClass);
		List<TmDmpRuleset> rulesets = rulesetService.queryByStId(stId);
		
		//设置规则集
		List<AbstractRuleSetVar> ruleSetVars = new ArrayList<AbstractRuleSetVar>();
		for(TmDmpRuleset rs : rulesets){
			String ruleSetObject = rs.getRuleSetObject();
			
			if(RuleSetType.D.name().equals(rs.getRuleSetType())){
				DecisionTableVar decisionTableVar = JSONObject.parseObject(ruleSetObject,DecisionTableVar.class);
				ruleSetVars.add(decisionTableVar);
			}else if(RuleSetType.S.name().equals(rs.getRuleSetType())){
				SimpleRuleSetVar simpleRuleSetVar = JSONObject.parseObject(ruleSetObject,SimpleRuleSetVar.class);
				ruleSetVars.add(simpleRuleSetVar);
			}
		}
		
		//决策变量
		List<FieldVar> inputFieldVarList = new ArrayList<FieldVar>();
		List<FieldVar> outputFieldVarList = new ArrayList<FieldVar>();
		exchangeFieldVar(tmDmpStrategy,inputFieldVarList,outputFieldVarList);
		
		Map<String,FieldVar> inputFieldVars = new HashMap<String, FieldVar>();
		Map<String,FieldVar> outputFieldVars = new HashMap<String, FieldVar>();
		for(FieldVar f:inputFieldVarList){
			inputFieldVars.put(f.getVarCd(), f);
		}
		for(FieldVar f:outputFieldVarList){
			outputFieldVars.put(f.getVarCd(), f);
		}
		
		//设置自定义函数
		Map<String,FunctionVar> functionVars = new HashMap<String, FunctionVar>();
		for(TmDmpStrategyFunction rs : strategyFunctionss){
			FunctionVar functionVar = new FunctionVar();
			functionVar.setFunCd(rs.getFunCd());
			functionVar.setFunName(rs.getFunName());
			functionVar.setFunContent(rs.getFunContent());
			functionVar.setFunParam(rs.getFunParam());
			functionVar.setDataType(rs.getDataType());
			
			String dType = funcDataType(rs.getDataType());
			StringBuilder sb = new StringBuilder("function ").append(" "+dType+" ");
			
			StringBuilder funParam = new StringBuilder(rs.getFunCd()).append("(");
			StringBuilder funParamRealCall = new StringBuilder();
			StringBuilder funParamRealActionCall = new StringBuilder();
			if(StrKit.notBlank(rs.getFunParam())){
				String[] params = rs.getFunParam().split(",");
				
				for(int i=0;i<params.length;i++){
					
					String param = params[i].trim();
					
					FieldVar inputFieldVar = inputFieldVars.get(param);
					if(inputFieldVar==null){
						throw new BizException("自定义方法"+rs.getFunCd()+"入参错误");
					}
					
					if(i!=0){
						funParam.append(", ");
						funParamRealCall.append(" , ");
						funParamRealActionCall.append(" , ");
					}
					String funParamDataType = funcDataType(inputFieldVar.getDataType());
					funParam.append(funParamDataType + param);
					
					funParamRealCall.append(funcCallDataType(inputFieldVar.getDataType(),param));
					funParamRealActionCall.append(funcCallDataTypeAction(inputFieldVar.getDataType(),param));
				}
			}
			funParam.append(")");
			sb.append(funParam).append("{ \n").append(rs.getFunContent()).append("\n};\n");
			functionVar.setRealFunction(sb.toString());
			
			String realFunctionCall = " "+rs.getFunCd() + "("+funParamRealCall.toString()+")";
			functionVar.setRealFunctionCall(realFunctionCall);
			String realFunctionActionCall =  " "+rs.getFunCd() + "("+funParamRealActionCall.toString()+")";
			functionVar.setRealFunctionActionCall(realFunctionActionCall);
			
			functionVars.put(rs.getFunCd(), functionVar);
		}
		
		stragegyVar.setStClass(tmDmpStrategy.getStClass());
		stragegyVar.setName(tmDmpStrategy.getStName());
		stragegyVar.setDesc(tmDmpStrategy.getRemark());
		stragegyVar.setActive(tmDmpStrategy.getIfUsed());
		stragegyVar.setFunctionVars(functionVars);
		stragegyVar.setInputFieldVars(inputFieldVars);
		stragegyVar.setOutputFieldVars(outputFieldVars);
		stragegyVar.setRuleSets(ruleSetVars);
		
		return stragegyVar;
	}
	
	private String funcDataType(String dataType){
		String dataTypeStr = "";
		DataType d = DataType.valueOf(dataType);
		switch (d) {
		case string:
			dataTypeStr = "String ";
			break;
		case decimal:
			dataTypeStr = "BigDecimal " ;
			break;
		case e:
			break;
		case bool:
			dataTypeStr = "boolean ";
			break;
		case date:
			dataTypeStr = "Date ";
			break;
		default:
			dataTypeStr = "Object ";
			break;
		}
		return dataTypeStr;
	}
	
	private String funcCallDataType(String dataType,String inputField){
		String dataTypeStr = "";
		DataType d = DataType.valueOf(dataType);
		switch (d) {
		case string:
			dataTypeStr = inputField;
			break;
		case decimal:
			dataTypeStr = "FunctionKit.toDecimal("+inputField+") " ;
			break;
		case e:
			break;
		case bool:
			dataTypeStr = "FunctionKit.toBoolean("+inputField+") " ;
			break;
		case date:
			dataTypeStr = "FunctionKit.toDate("+inputField+") " ;
			break;
		default:
			dataTypeStr = "null ";
			break;
		}
		return dataTypeStr;
	}
	
	private String funcCallDataTypeAction(String dataType,String inputField){
		String dataTypeStr = "";
		DataType d = DataType.valueOf(dataType);
		switch (d) {
		case string:
			dataTypeStr = "$t.get(\""+inputField+"\")";
			break;
		case decimal:
			dataTypeStr = "FunctionKit.toDecimal($t.get(\""+inputField+"\")) " ;
			break;
		case e:
			break;
		case bool:
			dataTypeStr = "FunctionKit.toBoolean($t.get(\""+inputField+"\")) " ;
			break;
		case date:
			dataTypeStr = "FunctionKit.toDate($t.get(\""+inputField+"\")) " ;
			break;
		default:
			dataTypeStr = "null ";
			break;
		}
		return dataTypeStr;
	}
	
	/**
	 * 更新策略的drl字段stObject
	 */
	@Override
	public void updateStrategyDrl(String stId){
		StragegyVar stragegyVar = genStragegyVar(stId);
		StragegyVarInstance instance = new StragegyVarInstance(stragegyVar);
		String drl = "";
		try{
			drl = instance.toDrl();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		TmDmpStrategy tmDmpStrategyDb = getTmDmpStrategy(stId);
		tmDmpStrategyDb.setStObject(drl);
		tmDmpStrategyDao.update(tmDmpStrategyDb);
	}

	@Override
	public TmDmpStrategy getDefaultStrategy(String stClass) {
		TmDmpStrategy tmDmpStrategy = new TmDmpStrategy();
		tmDmpStrategy.setStClass(stClass);
		tmDmpStrategy.setIfUsed("Y");
		Map<String, Object> params = new HashMap<String, Object>();
		List<TmDmpStrategy> list = tmDmpStrategyDao.queryForList(tmDmpStrategy, params);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}