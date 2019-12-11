package com.jjb.ecms.biz.activiti;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskJavaDelegateActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

/**
 * @author BIG.2Spring
 *
 */
public class ServiceTaskJavaDelegateActivityBehaviorExt extends
		ServiceTaskJavaDelegateActivityBehavior {

	private static final long serialVersionUID = 1L;

	public ServiceTaskJavaDelegateActivityBehaviorExt(JavaDelegate javaDelegate) {
		super(javaDelegate);
	}

	@Override
	protected void leave(ActivityExecution execution) {

		int trueResult = ActivityBehaviorPreCheck.check(execution);

		if (trueResult == 0) {
			throw new ActivitiException("条件错误，没有满足条件的分支");
		}

		if (trueResult > 1) {
			throw new ActivitiException("条件错误，满足的分支数大于1");
		}

		super.leave(execution);
	}

}
