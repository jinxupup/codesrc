package com.jjb.ecms.biz.activiti;

import java.util.Map;

import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;

public class RuntimeServiceImplExt extends RuntimeServiceImpl implements
		RuntimeServiceExt {
	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String activitiId) {
		return commandExecutor
				.execute(new RestartProcessInstanceCmd<ProcessInstance>(
						processDefinitionKey, null, null, activitiId, null));
	}

	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String businessKey, String activitiId) {
		return commandExecutor
				.execute(new RestartProcessInstanceCmd<ProcessInstance>(
						processDefinitionKey, null, businessKey, activitiId,
						null));
	}

	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String activitiId,
			Map<String, Object> variables) {
		return commandExecutor
				.execute(new RestartProcessInstanceCmd<ProcessInstance>(
						processDefinitionKey, null, null, activitiId, variables));
	}

	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String businessKey, String activitiId,
			Map<String, Object> variables) {
		return commandExecutor
				.execute(new RestartProcessInstanceCmd<ProcessInstance>(
						processDefinitionKey, null, businessKey, activitiId,
						variables));
	}
}
