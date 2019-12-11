package com.jjb.dmp.engine.kit;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.dmp.engine.model.AbstractRuleSetVar;
import com.jjb.dmp.engine.model.ActionColumnVar;
import com.jjb.dmp.engine.model.ConditionColumnVar;
import com.jjb.dmp.engine.model.DecisionRowVar;
import com.jjb.dmp.engine.model.DecisionTableVar;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.FilterCriteriaVar;
import com.jjb.dmp.engine.model.FunctionVar;
import com.jjb.dmp.engine.model.RuleActionVar;
import com.jjb.dmp.engine.model.SimpleRuleSetVar;
import com.jjb.dmp.engine.model.SimpleRuleVar;
import com.jjb.dmp.engine.model.StragegyVar;
import com.jjb.dmp.engine.model.enums.ActionType;
import com.jjb.dmp.engine.model.enums.DataType;
import com.jjb.dmp.engine.model.enums.OperatorId;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.kits.StrKit;

public class StragegyVarInstance {

	public static String RULES_PROP = "_rules";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private StragegyVar stragegyVar;
	private String st = "";
	
	//自定义函数
	private Map<String, FunctionVar> functionVars ;
	private FilterCriteriaVar exclude ;
	//输入变量列表
	private Map<String,FieldVar> inputFieldVars;
	//输出变量列表
	private Map<String,FieldVar> outputFieldVars;
	
	public StragegyVarInstance(StragegyVar stragegyVar){
		this.stragegyVar = stragegyVar;
		this.st = stragegyVar.getStClass() + "_" + stragegyVar.getName();
		
		this.functionVars = stragegyVar.getFunctionVars();
		this.exclude = stragegyVar.getExclude();
		this.inputFieldVars = stragegyVar.getInputFieldVars();
		this.outputFieldVars = stragegyVar.getOutputFieldVars();
	}
	
	public String toDrl(){
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		packageInfo(pw);
		functionInfo(pw);
		ruleSetsInfo(this.stragegyVar.getRuleSets(),pw);
		
		return sw.toString();
	}
	
	//头部信息
	public void packageInfo(PrintWriter pw){
		pw.println("\npackage com.jjb._" + stragegyVar.getName() + ";");
		pw.println("import java.util.Map;");
		pw.println("import org.mvel2.MVEL;");
		pw.println("import java.util.ArrayList;");
		pw.println("import java.util.Calendar;");
		pw.println("import java.util.Date;");
		pw.println("import java.math.BigDecimal;");
		pw.println("import FunctionKit;");
		pw.println("import RuleRuntimeInfo;");
		
		pw.println();
		pw.println("dialect \"mvel\"");
		pw.println();
		
	}
	
	//自定义函数
	public void functionInfo(PrintWriter pw){
		for (Map.Entry<String, FunctionVar> f: functionVars.entrySet()) {
			pw.println(f.getValue().getRealFunction());
		}
	}
	
	//规则集信息
	public void ruleSetsInfo(List<AbstractRuleSetVar> ruleSets, PrintWriter pw){
		
		if(ruleSets==null){
			return;
		}
		
		for(AbstractRuleSetVar rs:ruleSets){
			pw.printf("//ST---start ruleset %s\n", rs.getName());
			if(rs instanceof SimpleRuleSetVar){
				simpleRuleSetVar((SimpleRuleSetVar)rs,pw);
			}else if(rs instanceof DecisionTableVar){
				decisionTableVar((DecisionTableVar)rs,pw);
			}else{
				throw new IllegalArgumentException("无法处理的规则集类型:" + rs.getClass());
			}
			pw.printf("//ST---end ruleset %s\n", rs.getName());
		}
	}
	
	//简单规则集
	public void simpleRuleSetVar(SimpleRuleSetVar simpleRuleSetVar,PrintWriter pw){
		List<SimpleRuleVar> rules = simpleRuleSetVar.getRules();
		if(StrKit.notBlankList(rules)){
			for(SimpleRuleVar simpleRuleVar:rules){
				simpleRuleVar(simpleRuleVar,simpleRuleSetVar,pw);
			}
		}
	}
	
	//决策表
	public void decisionTableVar(DecisionTableVar rs,PrintWriter pw){
		List<DecisionRowVar> rows = rs.getRows();
		if(StrKit.notBlankList(rows)){
			for(DecisionRowVar row:rows){
				decisionRowVar(row,rs,pw);
			}
		}
	}
	
	//决策行
	public void decisionRowVar(DecisionRowVar decisionRowVar,DecisionTableVar decisionTableVar,PrintWriter pw){
		List<ConditionColumnVar> conditions = decisionTableVar.getConditions();
		List<ActionColumnVar> actions = decisionTableVar.getActions();
		
		pw.printf("rule \"%s\"\n", this.st + "_" + decisionTableVar.getName() + "_" + decisionRowVar.getDesc());
		pw.printf("  lock-on-active true\n"); //不设置此属性，可完成推理机功能
		
		if("Y".equals(decisionRowVar.getEnabled())&&"Y".equals(decisionTableVar.getEnabled())){
			pw.printf("  enabled true\n");
		}else{
			pw.printf("  enabled false\n");
		}
		
		if("Y".equals(decisionTableVar.getExclusive())){
			pw.printf("  activation-group \"%s\"\n", decisionTableVar.getName());
		}
		pw.printf("  salience %d\n", decisionRowVar.getPriority());
		
		Map<String, Object> conditionData = decisionRowVar.getConditionData();
		Map<String, Object> actionData = decisionRowVar.getActionData();	 
		
		pw.println("  when");
		pw.print("    $t:Map(");
		
		boolean first = true;
		for(ConditionColumnVar conditionColumnVar:conditions){
			FilterCriteriaVar filterCriteria = new FilterCriteriaVar();
			filterCriteria.setOperator(conditionColumnVar.getCriteria().getOperator());
			filterCriteria.setFieldName(conditionColumnVar.getCriteria().getFieldName());
			Serializable value = (Serializable) conditionData.get(conditionColumnVar.getUuid());
			filterCriteria.setValue(value);
			
			if (!first)
				pw.print(" && ");
			else
				first = false;
			outputCriteria(filterCriteria,pw);
		}
		
		pw.println(")");
		pw.println("  then");
		
		List<RuleActionVar> ruleActions = new ArrayList<RuleActionVar>();
		for(ActionColumnVar actionColumnVar:actions){
			RuleActionVar ruleActionVar = new RuleActionVar();
			ruleActionVar.setActionType(actionColumnVar.getAction().getActionType());
			ruleActionVar.setProperty(actionColumnVar.getAction().getProperty());
			Serializable value = (Serializable)actionData.get(actionColumnVar.getUuid());
			ruleActionVar.setValue(value);
			
			ruleActions.add(ruleActionVar);
		}
		newRuleRuntimeInfo(pw,decisionTableVar.getName(),decisionRowVar.getDesc());
		outputAction(ruleActions,pw,decisionTableVar.getName(),decisionRowVar.getDesc());
		pw.printf("    update($t);\n");
		pw.println("end\n");
	}
	
	
	//简单规则
	public void simpleRuleVar(SimpleRuleVar simpleRuleVar,SimpleRuleSetVar simpleRuleSetVar,PrintWriter pw){
		
		pw.printf("rule \"%s\"\n", this.st + "_" + simpleRuleSetVar.getName() + "_" + simpleRuleVar.getName());
		pw.printf("  lock-on-active true\n"); //不设置此属性，可完成推理机功能
		
		if("Y".equals(simpleRuleVar.getEnabled())&&"Y".equals(simpleRuleSetVar.getEnabled())){
			pw.printf("  enabled true\n");
		}else{
			pw.printf("  enabled false\n");
		}
		
		if("Y".equals(simpleRuleSetVar.getExclusive())){
			pw.printf("  activation-group \"%s\"\n", simpleRuleSetVar.getName());
		}
		pw.printf("  salience %d\n", simpleRuleVar.getPriority());
		pw.println("  when");
			pw.print("    $t:Map(");
			outputCriteria(simpleRuleVar.getCriteria(),pw);
			pw.println(")");
		pw.println("  then");
			newRuleRuntimeInfo(pw,simpleRuleSetVar.getName(),simpleRuleVar.getName());
			outputAction(simpleRuleVar.getActions(),pw,simpleRuleSetVar.getName(),simpleRuleVar.getName());
			pw.printf("    update($t);\n");
		pw.println("end\n");
	}
	
	
	private void outputAction(List<RuleActionVar> actions, PrintWriter pw, String rsName, String ruleName) {
		for(RuleActionVar a : actions){
			
			if(StrKit.isBlank(a.getProperty())){
				continue;
			}
			
			String property = a.getProperty();
			Object value = a.getValue();
			
			String destValue = "";
			
			if(value==null){
				destValue = "null";
			}else if(StrKit.isBlank(value.toString())){
				destValue = "\"\"";
			}else{
				if(ActionType.C.equals(a.getActionType())){
					
					FieldVar outputField = outputFieldVars.get(property); 
					if(outputField==null){
						return;
					}
					
					DataType d = DataType.valueOf(outputField.getDataType());
					if(d!=null){
						switch (d) {
						case string:
							destValue = "\""+value.toString()+"\"";
							break;
						case decimal:
							destValue = String.format("new BigDecimal(%s)", value.toString());  
//							destValue = String.format("FunctionKit.toDecimal(%s)", value);
							break;
						case e:
							break;
						case bool:
//							if("true".equalsIgnoreCase(value.toString())){
//								destValue = " true";
//							}else if("false".equalsIgnoreCase(value.toString())){
//								destValue = " false";
//							}else{
//								destValue = "null";
//							}
//							destValue = String.format("FunctionKit.toBoolean(%s)", value);
							destValue = " FunctionKit.toBoolean(\""+value+"\")";
							break;
						case date:
							destValue = " FunctionKit.toDate(\""+value+"\")";
//							destValue = " FunctionKit.toDate("+value+")";
							break;
						default:
							break;
						}
					}
					
				}else if(ActionType.P.equals(a.getActionType())){
					boolean isFunction = isFunction(value.toString());
					//是否方法
					if(isFunction){
						FunctionVar functionVar = functionVars.get(value.toString());
						if(functionVar==null){
							return;
						}
						destValue = functionVar.getRealFunctionActionCall();
					}else{
						FieldVar inputField = inputFieldVars.get(value.toString()); 
						if(inputField==null){
							return;
						}
						destValue = String.format("$t.get(\"%s\")", inputField.getVarCd()) ;
					}
				}
			}
			
			String oldValue = String.format("$t.get(\"%s\")", property);
			pw.printf("    Object newValue = %s ;\n", destValue);
			updateRuleRuntimeInfo(pw,property,oldValue,"newValue");
			pw.printf("    $t.put(\"%s\", %s);\n", property, "newValue");
			pw.println();
		}
	}

	public boolean checkLeft(FilterCriteriaVar criteria){
		if(StrKit.isBlank(criteria.getFieldName())){
			//左侧不能为空
			return false;
		}
		return true;
	}
	public boolean checkRight(FilterCriteriaVar criteria){
		if(criteria.getValue()==null||StrKit.isBlank(criteria.getValue().toString())){
			return false;
		}
		return true;
	}
	
	public boolean checkLeftAndRight(FilterCriteriaVar criteria){
		if(!checkLeft(criteria)){
			return false;
		}
		if(criteria.getValue()==null||StrKit.isBlank(criteria.getValue().toString())){
			//要左右都有值
			return false;
		}
		return true;
	}
	
	
	/**
	 * 右值为null的比较
	 * @return
	 */
	public String nullRightOperator(FilterCriteriaVar criteria,String destOp){
		if(!checkLeft(criteria)){
			return "( left )";
		}
		String left = criteriaField(criteria.getFieldName());
		return "("+left + destOp+")";
	}
	
	
	/**
	 *左值 比较 右值（右值为常量的比较）
	 * @param criteria
	 * @param destOp
	 * @return
	 */
	public String constantOperator(FilterCriteriaVar criteria,String destOp){
		
		if(!checkLeft(criteria)){
			return " (false) ";
		}
		
		if(!checkRight(criteria)){
			return " (true) ";
		}
		
		String filelName = criteria.getFieldName();
		String left = criteriaField(filelName);
		
		boolean isFunction = isFunction(filelName);
		String dataType = "string";
		
		if(isFunction){
			FunctionVar functionVar = functionVars.get(filelName);
			if(functionVar==null){
				logger.info("策略类别"+this.stragegyVar.getStClass() + "找不到 functionVar"+filelName);
				throw new BizException("策略类别"+this.stragegyVar.getStClass() + "找不到 自定义函数，函数代码"+filelName);
//				return "( false )";
			}
			dataType = functionVar.getDataType();
		}else{
			FieldVar fieldVar = inputFieldVars.get(filelName);
			
			if(fieldVar==null){
				logger.info("策略类别"+this.stragegyVar.getStClass() + "找不到 inputFieldVars"+filelName);
				throw new BizException("策略类别"+this.stragegyVar.getStClass() + "找不到 输入变量，变量代码"+filelName);
//				return "( false )";
			}
			dataType = fieldVar.getDataType();
		}
		
		String rightValue = criteria.getValue().toString();
		
		
		DataType d = DataType.valueOf(dataType);
		if(d!=null){
			switch (d) {
			case string:
				rightValue = "\""+rightValue+"\"";
				break;
			case decimal:
				break;
			case e:
				break;
			case bool:
				rightValue = " FunctionKit.toBoolean(\""+rightValue+"\")";
				break;
			case date:
				rightValue = " FunctionKit.toDate(\""+rightValue+"\")";
				break;
			default:
				break;
			}
		}
		
		
		return "("+left+" "+destOp+" "+rightValue+")";
	}
	
	public String constantMatcheOperator(FilterCriteriaVar criteria,String destOp){
		if(!checkLeft(criteria)){
			return " (false) ";
		}
		
		String filelName = criteria.getFieldName();
		String left = criteriaField(filelName);
		
		String rightValue = "";
		if(checkRight(criteria)){
			rightValue = criteria.getValue().toString();
		}
		
		return "("+left+" "+destOp+" \""+rightValue+"\")";
	}
	
	/**
	 * 字段比较的操作
	 * @param criteria
	 * @param destOp
	 * @return
	 */
	public String fieldOperator(FilterCriteriaVar criteria,String destOp){
		if(!checkLeft(criteria)){
			return " (false) ";
		}
		
		if(!checkRight(criteria)){
			return "( false )";
		}
		String left = criteriaField(criteria.getFieldName());
		String right = criteriaField(criteria.getValue().toString());
		return "("+left + destOp + right+")";
	}
	
	public boolean isFunction(String fieldName){
		return fieldName.startsWith(FunctionVar.FUNCTION_PREFIX);
	}
	
	/**
	 * 判断是变量还是函数,返回最终字段字符
	 * @param fieldName
	 * @return
	 */
	public String criteriaField(String fieldName){
		String field = "";
		
		boolean isFunction = isFunction(fieldName);
		if(isFunction){
			FunctionVar functionVar = functionVars.get(fieldName);
			field = functionVar.getRealFunctionCall();
		}else{
			field = fieldName;
		}
		return field;
	}
	
	//条件生成器
	public void outputCriteria(FilterCriteriaVar criteria,PrintWriter pw){
		
		OperatorId op = criteria.getOperator();
		if(op==null){
			pw.append(" (false) ");
			return;
		}
		switch (op) {
		case equals:
			if(checkRight(criteria)){
				pw.append(constantOperator(criteria," == "));
			}else{
				pw.append(nullRightOperator(criteria," == \"\" ")); //右侧为空，转为空字符串
			}
			break;
		case notEqual:
			if(checkRight(criteria)){
				pw.append(constantOperator(criteria," != "));
			}else{
				pw.append(nullRightOperator(criteria," != \"\" ")); //右侧为空，转为空字符串
			}
			break;
		case iEquals:	
			break;
		case iNotEqual:	
			break;
		case greaterThan:	
			pw.append(constantOperator(criteria,">"));
			break;
		case lessThan:	
			pw.append(constantOperator(criteria,"<"));
			break;
		case greaterOrEqual:	
			pw.append(constantOperator(criteria,">="));
			break;
		case lessOrEqual:	
			pw.append(constantOperator(criteria,"<="));
			break;
		case contains:	
			break;
		case startsWith:	
			break;
		case endsWith:	
			break;
		case iContains:	
			break;
		case iStartsWith:	
			break;
		case iEndsWith:	
			break;
		case notContains:	
			break;
		case notStartsWith:	
			break;
		case notEndsWith:	
			break;
		case iNotContains:	
			break;
		case iNotStartsWith:	
			break;
		case iNotEndsWith:	
			break;
		case regexp:	
			break;
		case iNotRegexp:
			pw.append(constantMatcheOperator(criteria," not matches "));
			break;
		case iregexp:	
			pw.append(constantMatcheOperator(criteria," matches "));
			break;
		case isNull:
			pw.append(nullRightOperator(criteria,"==null"));
			break;
		case notNull:	
			pw.append(nullRightOperator(criteria,"!=null"));
			break;
		case inSet:	
			break;
		case notInSet:	
			break;
		case equalsField:
			pw.append(fieldOperator(criteria,"=="));
			break;
		case notEqualField:	
			pw.append(fieldOperator(criteria,"!="));
			break;
		case greaterThanField:	
			pw.append(fieldOperator(criteria,">"));
			break;
		case lessThanField:	
			pw.append(fieldOperator(criteria,"<"));
			break;
		case greaterOrEqualField:	
			pw.append(fieldOperator(criteria,">="));
			break;
		case lessOrEqualField:	
			pw.append(fieldOperator(criteria,"<="));
			break;
		case containsField:	
			break;
		case startsWithField:	
			break;
		case endsWithField:	
			break;
		case and:{
			//与操作
			boolean first = true;
			for (FilterCriteriaVar subCriteria : criteria.getCriteria()) {
				if (!first)
					pw.print(" && ");
				else
					first = false;
				pw.print(" ( ");
				outputCriteria(subCriteria,pw);
				pw.print(" ) ");
			}
			break;
		}
		case notAnd:{
			pw.print("!( ");
			//与操作
			boolean first = true;
			for (FilterCriteriaVar subCriteria : criteria.getCriteria()) {
				if (!first)
					pw.print(" && ");
				else
					first = false;
				pw.print("( ");
				outputCriteria(subCriteria,pw);
				pw.print(" ) ");
			}
			pw.print(" ) ");
			break;
		}
		case not:	
			break;
		case or:{
			//或操作
			boolean first = true;
			for (FilterCriteriaVar subCriteria : criteria.getCriteria()) {
				if (!first)
					pw.print(" || ");
				else
					first = false;
				pw.print(" ( ");
				outputCriteria(subCriteria,pw);
				pw.print(" ) ");
			}
			break;
		}
		case notOr:{
			pw.print("!( ");
			//与操作
			boolean first = true;
			for (FilterCriteriaVar subCriteria : criteria.getCriteria()) {
				if (!first)
					pw.print(" && ");
				else
					first = false;
				pw.print("( ");
				outputCriteria(subCriteria,pw);
				pw.print(" ) ");
			}
			pw.print(" ) ");
			break;
		}
		
		case between:	
			break;
		case betweenInclusive:
			break;
		default:
			break;
		}
		
	}
	
	
	public void newRuleRuntimeInfo(PrintWriter pw,String rsName,String ruleName){
		pw.printf("    RuleRuntimeInfo ruleRuntimeInfo = new RuleRuntimeInfo(\"%s\", \"%s\", \"%s\" );\n", this.stragegyVar.getStClass(),rsName,ruleName);
		pw.printf("    if(!$t.containsKey(\"%s\")){ $t.put(\"%s\", new ArrayList());}\n", RULES_PROP, RULES_PROP);
		pw.printf("    ((ArrayList)$t.get(\"%s\")).add(ruleRuntimeInfo); \n\n",RULES_PROP);
	}
	
	public void updateRuleRuntimeInfo(PrintWriter pw,String outVarCd,Object oldValue,Object newValue){
		pw.printf("    ruleRuntimeInfo.addOutput(\"%s\", %s, %s);\n", outVarCd,oldValue,newValue);
	}
}
