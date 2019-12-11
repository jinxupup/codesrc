package com.jjb.ecms.biz.activiti;

import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;

/**
 * @author BIG.2Spring
 * 
 */
public class ActivityBehaviorFactoryExt extends DefaultActivityBehaviorFactory {

	public UserTaskActivityBehavior createUserTaskActivityBehavior(
			UserTask userTask, TaskDefinition taskDefinition) {
		return new UserTaskActivityBehaviorExt(taskDefinition);
	}

	public ExclusiveGatewayActivityBehavior createExclusiveGatewayActivityBehavior(
			ExclusiveGateway exclusiveGateway) {
		return new ExclusiveGatewayActivityBehaviorExt();
	}

	public ClassDelegate createClassDelegateServiceTask(ServiceTask serviceTask) {
		return new ClassDelegateExt(serviceTask.getImplementation(),
				createFieldDeclarations(serviceTask.getFieldExtensions()));
	}

	public ServiceTaskDelegateExpressionActivityBehavior createServiceTaskDelegateExpressionActivityBehavior(
			ServiceTask serviceTask) {
		Expression delegateExpression = expressionManager
				.createExpression(serviceTask.getImplementation());
		return new ServiceTaskDelegateExpressionActivityBehavior(
				delegateExpression,
				createFieldDeclarations(serviceTask.getFieldExtensions()));
	}

	public ServiceTaskExpressionActivityBehavior createServiceTaskExpressionActivityBehavior(
			ServiceTask serviceTask) {
		Expression expression = expressionManager.createExpression(serviceTask
				.getImplementation());
		return new ServiceTaskExpressionActivityBehavior(expression,
				serviceTask.getResultVariableName());
	}
}
