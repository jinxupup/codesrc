package com.jjb.ecms.biz.activiti;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BIG.2Spring
 *
 */
public class UserTaskActivityBehaviorExt extends UserTaskActivityBehavior {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserTaskActivityBehaviorExt.class);

	/**
	 * @param taskDefinition
	 */
	public UserTaskActivityBehaviorExt(TaskDefinition taskDefinition) {
		super(taskDefinition);
		// TODO Auto-generated constructor stub
	}

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
