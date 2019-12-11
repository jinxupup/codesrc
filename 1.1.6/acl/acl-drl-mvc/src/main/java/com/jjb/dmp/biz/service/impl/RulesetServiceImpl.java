package com.jjb.dmp.biz.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.dao.TmDmpRuleDao;
import com.jjb.dmp.biz.dao.TmDmpRulesetDao;
import com.jjb.dmp.biz.service.DrlRuleService;
import com.jjb.dmp.biz.service.RulesetService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.engine.kit.RuleComparator;
import com.jjb.dmp.engine.model.ActionColumnVar;
import com.jjb.dmp.engine.model.ConditionColumnVar;
import com.jjb.dmp.engine.model.DecisionRowVar;
import com.jjb.dmp.engine.model.DecisionTableVar;
import com.jjb.dmp.engine.model.SimpleRuleSetVar;
import com.jjb.dmp.engine.model.SimpleRuleVar;
import com.jjb.dmp.engine.model.enums.RuleSetType;
import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Transactional(readOnly=false)
@Service("rulesetService")
public class RulesetServiceImpl implements RulesetService {

	@Autowired
	private TmDmpRulesetDao tmDmpRulesetDao;
	
	@Autowired
	private TmDmpRuleDao tmDmpRuleDao;
	
	@Autowired
	private DrlRuleService drlRuleService;
	
	@Autowired
	private StrategyService strategyService;
	
	@Override
	public TmDmpRuleset getTmDmpRuleset(String rsId){
		return tmDmpRulesetDao.getByKey(rsId);
	}

	@Override
	public List<TmDmpRuleset> queryByStId(String stId) {
		return tmDmpRulesetDao.queryByStId(stId);
	}

	/**
	 * 新增规则集
	 */
	@Override
	@Transactional
	public void saveTmDmpRuleset(TmDmpRuleset tmDmpRuleset) {
		TmDmpRuleset tmDmpRulesetDb = new TmDmpRuleset();
		tmDmpRulesetDb.setStId(tmDmpRuleset.getStId());
		tmDmpRulesetDb.setRuleSetName(tmDmpRuleset.getRuleSetName());
		List<TmDmpRuleset> list = tmDmpRulesetDao.queryForList(tmDmpRulesetDb);
		if(StrKit.notBlankList(list)){
			throw new RuntimeException("已存在此名称的规则集");
		}
		
		if(RuleSetType.S.name().equals(tmDmpRuleset.getRuleSetType())){
			//更新对象信息，简单规则集
			SimpleRuleSetVar simpleRuleSetVar = JSONObject.parseObject(tmDmpRuleset.getRuleSetObject(), SimpleRuleSetVar.class);
			if(simpleRuleSetVar==null){
				simpleRuleSetVar = new SimpleRuleSetVar();
			}
			simpleRuleSetVar.setName(tmDmpRuleset.getRuleSetName());
			simpleRuleSetVar.setDesc(tmDmpRuleset.getRemark());
			simpleRuleSetVar.setEnabled(tmDmpRuleset.getRuleSetEnabled());
			simpleRuleSetVar.setExclusive(tmDmpRuleset.getIsExclusive());	
			tmDmpRuleset.setRuleSetObject(JSONObject.toJSONString(simpleRuleSetVar));
		}else if(RuleSetType.D.name().equals(tmDmpRuleset.getRuleSetType())){
			//更新对象信息，决策表
			DecisionTableVar decisionTableVar = JSONObject.parseObject(tmDmpRuleset.getRuleSetObject(), DecisionTableVar.class);
			if(decisionTableVar==null){
				decisionTableVar = new DecisionTableVar();
			}
			decisionTableVar.setName(tmDmpRuleset.getRuleSetName());
			decisionTableVar.setDesc(tmDmpRuleset.getRemark());
			decisionTableVar.setEnabled(tmDmpRuleset.getRuleSetEnabled());
			decisionTableVar.setExclusive(tmDmpRuleset.getIsExclusive());	
			tmDmpRuleset.setRuleSetObject(JSONObject.toJSONString(decisionTableVar));
		}else{
			throw new RuntimeException("规则集类型错误");
		}
		
		//设置uuid
		tmDmpRuleset.setRsId(UUID.randomUUID().toString());
		tmDmpRulesetDao.save(tmDmpRuleset);
		
		//更新drl
		strategyService.updateStrategyDrl(tmDmpRuleset.getStId());
	}

	/**
	 * 编辑规则集基本信息
	 */
	@Override
	public void editTmDmpRuleset(TmDmpRuleset tmDmpRuleset) {
		
		TmDmpRuleset tmDmpRulesetDb = tmDmpRulesetDao.getByKey(tmDmpRuleset.getRsId());
		if(tmDmpRulesetDb==null){
			throw new BizException("找不到该规则集");
		}
		
		//判断名称
		TmDmpRuleset tmDmpRulesetUnique = new TmDmpRuleset();
		tmDmpRulesetUnique.setRuleSetName(tmDmpRuleset.getRuleSetName());
		tmDmpRulesetUnique.setStId(tmDmpRuleset.getStId());
		List<TmDmpRuleset> listUniqe = tmDmpRulesetDao.queryForList(tmDmpRulesetUnique);
		if(StrKit.notBlankList(listUniqe)){
			for(TmDmpRuleset r:listUniqe){
				if(r.getRsId()!=tmDmpRuleset.getRsId()){
					throw new RuntimeException("已存在此名称的规则集");
				}
			}
		}
		tmDmpRuleset.setJpaVersion(tmDmpRulesetDb.getJpaVersion());
		BeanUtil.merge(tmDmpRulesetDb, tmDmpRuleset);
		
		if(RuleSetType.S.name().equals(tmDmpRulesetDb.getRuleSetType())){
			//更新对象信息，简单规则集
			SimpleRuleSetVar simpleRuleSetVar = JSONObject.parseObject(tmDmpRulesetDb.getRuleSetObject(), SimpleRuleSetVar.class);
			if(simpleRuleSetVar==null){
				simpleRuleSetVar = new SimpleRuleSetVar();
			}
			
			simpleRuleSetVar.setName(tmDmpRulesetDb.getRuleSetName());
			simpleRuleSetVar.setDesc(tmDmpRulesetDb.getRemark());
			simpleRuleSetVar.setEnabled(tmDmpRulesetDb.getRuleSetEnabled());
			simpleRuleSetVar.setExclusive(tmDmpRulesetDb.getIsExclusive());	
			tmDmpRulesetDb.setRuleSetObject(JSONObject.toJSONString(simpleRuleSetVar));
		}else if(RuleSetType.D.name().equals(tmDmpRulesetDb.getRuleSetType())){
			//更新对象信息，决策表
			DecisionTableVar decisionTableVar = JSONObject.parseObject(tmDmpRulesetDb.getRuleSetObject(), DecisionTableVar.class);
			if(decisionTableVar==null){
				decisionTableVar = new DecisionTableVar();
			}
			
			decisionTableVar.setName(tmDmpRulesetDb.getRuleSetName());
			decisionTableVar.setDesc(tmDmpRulesetDb.getRemark());
			decisionTableVar.setEnabled(tmDmpRulesetDb.getRuleSetEnabled());
			decisionTableVar.setExclusive(tmDmpRulesetDb.getIsExclusive());	
			tmDmpRulesetDb.setRuleSetObject(JSONObject.toJSONString(decisionTableVar));
		}else{
			throw new RuntimeException("规则集类型错误");
		}
		
		tmDmpRulesetDao.update(tmDmpRulesetDb);
		
		//更新drl
		strategyService.updateStrategyDrl(tmDmpRuleset.getStId());
	}
	
	@Override
	public void deleteTmDmpRuleset(String rsId) {
		TmDmpRuleset tmDmpRuleset = new TmDmpRuleset();
		tmDmpRuleset.setRsId(rsId);
		
		tmDmpRuleset = tmDmpRulesetDao.getByKey(rsId);
		if(tmDmpRuleset==null){
			throw new BizException("找不到该规则集");
		}
		String stId = tmDmpRuleset.getStId();
		
		tmDmpRulesetDao.deleteByKey(tmDmpRuleset);
		
		List<TmDmpRule> rules = drlRuleService.queryByRsId(rsId);
		if(rules!=null && rules.size()>0){
			for(TmDmpRule tmDmpRule:rules){
				tmDmpRuleDao.deleteByKey(tmDmpRule);
			}
		}
		
		//更新drl
		strategyService.updateStrategyDrl(stId);
		
	}

	//统一简单规则集
	/**
	 * 根据优先级排序
	 * @param simpleRuleSetVar
	 */
	public void sortSimpleRules(SimpleRuleSetVar simpleRuleSetVar){
		List<SimpleRuleVar> rules = simpleRuleSetVar.getRules();
		Collections.sort(rules,new RuleComparator());
	}
	/**
	 * 
	 * @param tmDmpRule
	 * @param simpleRuleVar
	 * @param dbOperator 可选值 update insert delete
	 */
	@Override
	public void uniforSimpleRuleVar(TmDmpRule tmDmpRule,SimpleRuleVar simpleRuleVar,String dbOperator){
		TmDmpRuleset tmDmpRuleset = getTmDmpRuleset(tmDmpRule.getRsId());
		String ruleSetObject = tmDmpRuleset.getRuleSetObject();
		
		SimpleRuleSetVar simpleRuleSetVar = JSONObject.parseObject(ruleSetObject, SimpleRuleSetVar.class);
		if(simpleRuleSetVar==null){
			simpleRuleSetVar = new SimpleRuleSetVar();
		}
		
		if("insert".equalsIgnoreCase(dbOperator)){
			simpleRuleSetVar = addRow(simpleRuleSetVar, simpleRuleVar);
		}else if("update".equalsIgnoreCase(dbOperator)){
			simpleRuleSetVar = updateRow(simpleRuleSetVar, simpleRuleVar);
		}else if("delete".equalsIgnoreCase(dbOperator)){
			simpleRuleSetVar = deleteRow(simpleRuleSetVar, simpleRuleVar);
		}else{
			throw new BizException("错误的dbOperator");
		}
		sortSimpleRules(simpleRuleSetVar);
		simpleRuleSetVar.setName(tmDmpRuleset.getRuleSetName());
		simpleRuleSetVar.setDesc(tmDmpRuleset.getRemark());
		simpleRuleSetVar.setEnabled(tmDmpRuleset.getRuleSetEnabled());
		simpleRuleSetVar.setExclusive(tmDmpRuleset.getIsExclusive());
		String destRetObject = JSONObject.toJSONString(simpleRuleSetVar);
		tmDmpRuleset.setRuleSetObject(destRetObject);
		tmDmpRulesetDao.update(tmDmpRuleset);
		
		//更新drl
		strategyService.updateStrategyDrl(tmDmpRuleset.getStId());
	}
	
	/**
	 * 新增一条简单规则，对规则集对象的操作
	 * @param simpleRuleSetVar
	 * @param simpleRuleVar
	 * @return
	 */
	public SimpleRuleSetVar addRow(SimpleRuleSetVar simpleRuleSetVar, SimpleRuleVar simpleRuleVar){
		simpleRuleSetVar.getRules().add(simpleRuleVar);
		return simpleRuleSetVar;
	}
	
	/**
	 * 删除一条简单规则，对规则集对象的操作
	 * @param simpleRuleSetVar
	 * @param simpleRuleVar
	 * @return
	 */
	public SimpleRuleSetVar deleteRow(SimpleRuleSetVar simpleRuleSetVar, SimpleRuleVar simpleRuleVar){
		List<SimpleRuleVar> rules = simpleRuleSetVar.getRules();
		if(rules!=null && rules.size()>0){
			for(int i=0;i<rules.size();i++){
				if(rules.get(i).getRuleId().equals(simpleRuleVar.getRuleId())){
					rules.remove(i);
					break;
				}
			}
		}
		return simpleRuleSetVar;
	}
	
	/**
	 * 更新一条简单规则，对规则集对象的操作
	 * @param simpleRuleSetVar
	 * @param simpleRuleVar
	 * @return
	 */
	public SimpleRuleSetVar updateRow(SimpleRuleSetVar simpleRuleSetVar, SimpleRuleVar simpleRuleVar){
		List<SimpleRuleVar> rules = simpleRuleSetVar.getRules();
		boolean has = false;
		if(rules!=null && rules.size()>0){
			for(int i=0;i<rules.size();i++){
				if(rules.get(i).getRuleId().equals(simpleRuleVar.getRuleId())){
					has = true;
					rules.set(i, simpleRuleVar);
					break;
				}
			}
		}
		if(!has){
			//找不到更新的记录，新增一条
			addRow(simpleRuleSetVar,simpleRuleVar);
		}
		return simpleRuleSetVar;
	}
	
	
	//决策表
	/**
	 * 根据优先级排序
	 * @param simpleRuleSetVar
	 */
	public void sortDecisionTableRows(DecisionTableVar diDecisionTableVar){
		List<DecisionRowVar> rules = diDecisionTableVar.getRows();
		Collections.sort(rules,new RuleComparator());
	}
	
	/**
	 * 同步条件、动作和决策行
	 * @param oldDecisionTableVar
	 * @param conditions
	 * @param actions
	 * @param rows
	 * @return
	 */
	private DecisionTableVar mergeDecisionTableVar(DecisionTableVar oldDecisionTableVar,List<ConditionColumnVar> conditions,List<ActionColumnVar> actions,List<DecisionRowVar> rows){
		DecisionTableVar decisionTableVar = new DecisionTableVar();
		
		decisionTableVar.setConditions(conditions);
		decisionTableVar.setActions(actions);
		
		//同步rows
		List<DecisionRowVar> newRows = new ArrayList<DecisionRowVar>();
		if(rows==null){
			rows = new ArrayList<DecisionRowVar>();
		}
		for(DecisionRowVar r:rows){
			Map<String,Object> conditionData = new HashMap<String, Object>();
			if(r.getConditionData()==null){
				r.setConditionData(new HashMap<String, Object>());
			}
			if(conditions!=null){
				for(ConditionColumnVar c:conditions){
					if(r.getConditionData().get(c.getUuid())!=null){
						conditionData.put(c.getUuid(),r.getConditionData().get(c.getUuid()));
					}else{
						conditionData.put(c.getUuid(),"");
					}
				}
			}
			
			
			Map<String,Object> actionData = new HashMap<String, Object>();
			if(r.getActionData()==null){
				r.setActionData(new HashMap<String, Object>());
			}
			if(actions!=null){
				for(ActionColumnVar a:actions){
					if(r.getActionData().get(a.getUuid())!=null){
						actionData.put(a.getUuid(),r.getActionData().get(a.getUuid()));
					}else{
						actionData.put(a.getUuid(),"");
					}
				}
			}
			
			DecisionRowVar newRow = new DecisionRowVar();
			newRow.setConditionData(conditionData);
			newRow.setActionData(actionData);
			newRow.setPriority(r.getPriority());
			newRow.setEnabled(r.getEnabled());
			newRow.setDesc(r.getDesc());
			newRows.add(newRow);
		}
		
		decisionTableVar.setRows(newRows);
		return decisionTableVar;
	}
	
	//保存决策表信息
	@Override
	public void saveDecisionTableRows(TmDmpRuleset tmDmpRuleset,List<DecisionRowVar> rows) {
		TmDmpRuleset tmDmpRulesetDb = getTmDmpRuleset(tmDmpRuleset.getRsId());
		DecisionTableVar decisionTableVarDb = JSONObject.parseObject(tmDmpRulesetDb.getRuleSetObject(),DecisionTableVar.class);
		if(decisionTableVarDb==null){
			decisionTableVarDb = new DecisionTableVar();
		}
		
		DecisionTableVar decisionTableVar = mergeDecisionTableVar(decisionTableVarDb,decisionTableVarDb.getConditions(),decisionTableVarDb.getActions(),rows);
		decisionTableVar.setDesc(tmDmpRulesetDb.getRemark());
		decisionTableVar.setName(tmDmpRulesetDb.getRuleSetName());
		decisionTableVar.setExclusive(tmDmpRulesetDb.getIsExclusive());
		
		sortDecisionTableRows(decisionTableVar);
		drlRuleService.saveDecisionTableRows(tmDmpRulesetDb, decisionTableVar.getRows());
		tmDmpRulesetDb.setRuleSetObject(JSON.toJSONString(decisionTableVar));
		tmDmpRulesetDao.update(tmDmpRulesetDb);
		
		//更新drl
		strategyService.updateStrategyDrl(tmDmpRuleset.getStId());
	}
	
	@Override
	public void saveDecisionTableConfig(TmDmpRuleset tmDmpRuleset,List<ConditionColumnVar> conditions, List<ActionColumnVar> actions){
		TmDmpRuleset tmDmpRulesetDb = getTmDmpRuleset(tmDmpRuleset.getRsId());
		
		DecisionTableVar decisionTableVarDb = JSONObject.parseObject(tmDmpRulesetDb.getRuleSetObject(),DecisionTableVar.class);
		if(decisionTableVarDb==null){
			decisionTableVarDb = new DecisionTableVar();
		}
		
		DecisionTableVar decisionTableVar = mergeDecisionTableVar(decisionTableVarDb,conditions,actions,decisionTableVarDb.getRows());
		decisionTableVar.setDesc(tmDmpRulesetDb.getRemark());
		decisionTableVar.setName(tmDmpRulesetDb.getRuleSetName());
		decisionTableVar.setExclusive(tmDmpRulesetDb.getIsExclusive());
		
		sortDecisionTableRows(decisionTableVar);
		drlRuleService.saveDecisionTableRows(tmDmpRulesetDb, decisionTableVar.getRows());
		tmDmpRulesetDb.setRuleSetObject(JSON.toJSONString(decisionTableVar));
		tmDmpRulesetDao.update(tmDmpRulesetDb);
		
		//更新drl
		strategyService.updateStrategyDrl(tmDmpRuleset.getStId());
	}
}