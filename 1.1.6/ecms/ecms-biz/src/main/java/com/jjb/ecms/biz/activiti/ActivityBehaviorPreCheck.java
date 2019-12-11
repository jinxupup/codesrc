package com.jjb.ecms.biz.activiti;

import java.util.List;

import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BIG.2Spring
 *
 */
public class ActivityBehaviorPreCheck {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActivityBehaviorPreCheck.class);

	public static int check(ActivityExecution execution) {

		LOGGER.debug("开始进行流程节点预检查:" + execution.getActivity().getId());
		// Map<String,Object> vars = execution.getVariables();
		//
		// for(Map.Entry<String,Object> var : vars.entrySet()){
		// LOGGER.info("变量名称："+var.getKey()+"   变量值："+(var.getValue()==null?"":var.getValue().toString()));
		// }
		//
		List<PvmTransition> outgoingTransitions = execution.getActivity()
				.getOutgoingTransitions();

		int trueResult = 0;

		for (PvmTransition outgoingTransition : outgoingTransitions) {

			Condition condition = (Condition) outgoingTransition
					.getProperty(BpmnParse.PROPERTYNAME_CONDITION);

			if (condition == null) {
				trueResult++;

			} else {
				if (condition.evaluate(execution)) {
					trueResult++;
				}
			}

		}

		LOGGER.info("流程节点预检查结束：" + trueResult);

		return trueResult;
	}
}
