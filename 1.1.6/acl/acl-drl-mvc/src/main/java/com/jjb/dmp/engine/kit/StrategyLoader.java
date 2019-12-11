package com.jjb.dmp.engine.kit;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.RuleRuntimeInfo;
import com.jjb.dmp.engine.model.enums.DataType;
import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.unicorn.facility.exception.BizException;

public class StrategyLoader{

	private static Logger logger = LoggerFactory.getLogger(StrategyLoader.class);
	
	public static KnowledgeBase loadKnowledgeBase(String drl){
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newReaderResource(new StringReader(drl)), ResourceType.DRL);
		
		logger.info("---------规则信息---------");
		logger.info(drl);
		logger.info("--------------------------");
		if(kbuilder.hasErrors()){
			logger.error("规则中存在错误，错误消息如下： ");
			KnowledgeBuilderErrors kbuidlerErrors=kbuilder.getErrors();
			for(Iterator<KnowledgeBuilderError> iter=kbuidlerErrors.iterator();iter.hasNext();){
				KnowledgeBuilderError error = iter.next();
				logger.error(error.toString());
			}
		}
		
		return kbuilder.newKnowledgeBase();
	}
	
	public static void execute(KnowledgeBase knowledgeBase,Map<String, Object> fact){
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		statelessKnowledgeSession.execute(fact);
	}
	
	/**
	 * 输入事实不存在key时，设置null,
	 * @param fact
	 * @param vars 决策变量
	 */
	public static void inputFactMerge(Map<String,Object> fact,List<TmDmpStrategyVar> strategyVars){
		if(fact==null){
			fact = new HashMap<String, Object>();
		}
		fact.put("_rules", new ArrayList<RuleRuntimeInfo>());
		
		if(strategyVars!=null&&strategyVars.size()>0){
			for(TmDmpStrategyVar tmStrategyVar:strategyVars){
				
				if(!fact.containsKey(tmStrategyVar.getVarCd())){
					fact.put(tmStrategyVar.getVarCd(), null);
				}
			}
		}
	}
	
	
	/**
	 * 重新格式化输入数据
	 * 输入事实不存在key时，设置null,
	 * @param fact
	 * @param inputFieldVars
	 */
	public static void inputFactMerge(Map<String,Object> fact,Map<String, FieldVar> inputFieldVars){
		if(fact==null){
			fact = new HashMap<String, Object>();
		}
		fact.put("_rules", new ArrayList<RuleRuntimeInfo>());
		
		if(inputFieldVars!=null){
			for(Map.Entry<String, FieldVar> f:inputFieldVars.entrySet()){
				Object value = fact.get(f.getKey());
				DataType d = DataType.valueOf(f.getValue().getDataType());
				
				if(value==null|| (value!=null && "".equals(value.toString().trim())) ){
					switch (d) {
					case string:
						fact.put(f.getKey(), value);
						break;
					case decimal:
						fact.put(f.getKey(), null);
						break;
					case bool:
						fact.put(f.getKey(), null);
						break;
					case date:
						fact.put(f.getKey(), null);
						break;
					case e:
						fact.put(f.getKey(), null);
						break;
					default:
						fact.put(f.getKey(), null);
						break;
					}
				}else{
					switch (d) {
					case string:
						fact.put(f.getKey(), value);
						break;
					case decimal:
						fact.put(f.getKey(), value);
						break;
					case bool:
						if(value instanceof String){
							if("false".equalsIgnoreCase((String)value)){
								fact.put(f.getKey(), false);
							}else if("true".equalsIgnoreCase((String)value)){
								fact.put(f.getKey(), true);
							}else{
								throw new BizException("bool 字符串不正确");
							}
						}
						break;
					case date:
						if(value instanceof String){
							fact.put(f.getKey(), FunctionKit.toDate((String)value));
						}else if(value instanceof Date){
							fact.put(f.getKey(), value);
						}else{
							throw new BizException("时间日期格式不正确");
						}
						break;
					case e:
						fact.put(f.getKey(), value);
						break;
					default:
						fact.put(f.getKey(), value);
						break;
					}
				}
			}
		}
	}
}
