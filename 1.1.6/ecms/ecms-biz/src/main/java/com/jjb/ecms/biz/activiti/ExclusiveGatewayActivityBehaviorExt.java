package com.jjb.ecms.biz.activiti;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BIG.2Spring
 *
 */
public class ExclusiveGatewayActivityBehaviorExt extends
		ExclusiveGatewayActivityBehavior {

	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory
			.getLogger(ExclusiveGatewayActivityBehaviorExt.class);

	@Override
	protected void leave(ActivityExecution execution) {

		int trueResult = ActivityBehaviorPreCheck.check(execution);

		LOGGER.info("ClassName:" + this.getClass().getName() + ",Execution:"
				+ execution + ",BehaviorPreCheck Result:" + trueResult);
		if (trueResult == 0) {
			throw new ActivitiException("条件错误，没有满足条件的分支");
		}

		if (trueResult > 1) {
			throw new ActivitiException("条件错误，满足的分支数大于1");
		}

		super.leave(execution);
	}

}
