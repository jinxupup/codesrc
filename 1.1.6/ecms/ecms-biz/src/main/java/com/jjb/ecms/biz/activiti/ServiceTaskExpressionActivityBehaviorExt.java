package com.jjb.ecms.biz.activiti;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskExpressionActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

/**
 * @author BIG.2Spring
 *
 */
public class ServiceTaskExpressionActivityBehaviorExt extends
		ServiceTaskExpressionActivityBehavior {

	/**
	 * @param expression
	 * @param resultVariable
	 */
	public ServiceTaskExpressionActivityBehaviorExt(Expression expression,
			String resultVariable) {
		super(expression, resultVariable);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void leave(ActivityExecution execution) {


		int trueResult = ActivityBehaviorPreCheck.check(execution);

		if (trueResult == 0) {
			throw new ActivitiException("��������û�����������ķ�֧");
		}

		if (trueResult > 1) {
			throw new ActivitiException("������������ķ�֧�����1");
		}

		super.leave(execution);
	}

}
