package com.jjb.dmp.biz.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.dmp.biz.dao.TmDmpRuleDao;
import com.jjb.dmp.biz.service.DrlRuleService;
import com.jjb.dmp.biz.service.RulesetService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.engine.model.DecisionRowVar;
import com.jjb.dmp.engine.model.SimpleRuleVar;
import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Transactional
@Service("drlRuleService")
public class DrlRuleServiceImpl implements DrlRuleService {

	@Autowired
	private TmDmpRuleDao tmDmpRuleDao;
	@Autowired
	private RulesetService rulesetService;
	@Autowired
	private StrategyService strategyService;
	
	@Override
	public TmDmpRule getTmDmpRule(String rsId){
		return tmDmpRuleDao.getByKey(rsId);
	}

	@Override
	public List<TmDmpRule> queryByRsId(String rsId) {
		return tmDmpRuleDao.queryByRsId(rsId);
	}

	@Override
	@Transactional
	public void saveTmDmpRule(TmDmpRule tmDmpRule) {
		TmDmpRule tmDmpRuleDb = new TmDmpRule();
		tmDmpRuleDb.setRsId(tmDmpRule.getRsId());
		tmDmpRuleDb.setRuleName(tmDmpRule.getRuleName());
		List<TmDmpRule> list = tmDmpRuleDao.queryForList(tmDmpRuleDb);
		if(StrKit.notBlankList(list)){
			throw new RuntimeException("已存在此名称的规则");
		}
		
		TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(tmDmpRuleDb.getRsId());
		tmDmpRule.setRuleType(tmDmpRuleset.getRuleSetType());
		
		//设置uuid
		tmDmpRule.setRuleId(UUID.randomUUID().toString());
		
		tmDmpRuleDao.save(tmDmpRule);
	}

	@Override
	public void editTmDmpRule(TmDmpRule tmDmpRule) {
		
		TmDmpRule tmDmpRuleDb = tmDmpRuleDao.getByKey(tmDmpRule.getRuleId());
		if(tmDmpRuleDb==null){
			throw new BizException("找不到该规则");
		}
		
		//判断名称
		TmDmpRule tmDmpRuleUnique = new TmDmpRule();
		tmDmpRuleUnique.setRsId(tmDmpRule.getRsId());
		tmDmpRuleUnique.setRuleName(tmDmpRule.getRuleName());
		List<TmDmpRule> listUniqe = tmDmpRuleDao.queryForList(tmDmpRuleUnique);
		if(StrKit.notBlankList(listUniqe)){
			for(TmDmpRule r:listUniqe){
				if(!r.getRuleId().equals(tmDmpRule.getRuleId())){
					throw new RuntimeException("已存在此名称的规则");
				}
			}
		}
		tmDmpRule.setJpaVersion(tmDmpRuleDb.getJpaVersion());
		BeanUtil.merge(tmDmpRuleDb, tmDmpRule);
		tmDmpRuleDao.update(tmDmpRuleDb);
	}

	@Override
	public void deleteTmDmpRule(String ruleId) {
		TmDmpRule tmDmpRule = new TmDmpRule();
		tmDmpRule.setRuleId(ruleId);
		tmDmpRule = tmDmpRuleDao.getByKey(ruleId);
		
		SimpleRuleVar simpleRuleVar = new SimpleRuleVar();
		simpleRuleVar.setRuleId(ruleId);
		rulesetService.uniforSimpleRuleVar(tmDmpRule,simpleRuleVar,"delete");
		
		tmDmpRuleDao.deleteByKey(tmDmpRule);
	}

	@Override
	public void saveSimpleRule(TmDmpRule tmDmpRule,SimpleRuleVar simpleRuleVar) {
		tmDmpRule.setRuleType("S");
		saveTmDmpRule(tmDmpRule);
		
		simpleRuleVar.setRuleId(tmDmpRule.getRuleId());
		rulesetService.uniforSimpleRuleVar(tmDmpRule,simpleRuleVar,"insert");
	}

	@Override
	public void editSimpleRule(TmDmpRule tmDmpRule,SimpleRuleVar simpleRuleVar) {
		tmDmpRule.setRuleType("S");
		simpleRuleVar.setRuleId(tmDmpRule.getRuleId());
		
		editTmDmpRule(tmDmpRule);
		rulesetService.uniforSimpleRuleVar(tmDmpRule,simpleRuleVar,"update");
	}

	/**
	 * 跟新规则表的规则信息
	 * @param tmDmpRuleset
	 */
	@Override
	public List<DecisionRowVar> saveDecisionTableRows(TmDmpRuleset tmDmpRuleset,List<DecisionRowVar> rows){
		//跟新规则表
		List<TmDmpRule> ruleList = queryByRsId(tmDmpRuleset.getRsId());
		for(TmDmpRule r:ruleList){
			tmDmpRuleDao.deleteByKey(r);
		}
		
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(tmDmpRuleset.getStId());
		if(!StrKit.isBlank(tmDmpRuleset.getRuleSetObject())){
			for(DecisionRowVar r:rows){
				TmDmpRule tmDmpRule = new TmDmpRule();
				tmDmpRule.setStId(tmDmpStrategy.getStId());
				tmDmpRule.setRsId(tmDmpRuleset.getRsId());
				tmDmpRule.setRuleName(r.getDesc());
				tmDmpRule.setRuleEnabled(r.getEnabled());
				tmDmpRule.setPriority(r.getPriority());
				tmDmpRule.setRuleType("D");
				tmDmpRule.setRemark(r.getDesc());
//				tmDmpRule.setRuleObject(JSONObject.toJSONString(r));
				
				//设置uuid
				tmDmpRule.setRuleId(UUID.randomUUID().toString());
				
				tmDmpRuleDao.save(tmDmpRule);
				r.setRuleId(tmDmpRule.getRuleId());
			}
		}
		
		return rows;
	}
}