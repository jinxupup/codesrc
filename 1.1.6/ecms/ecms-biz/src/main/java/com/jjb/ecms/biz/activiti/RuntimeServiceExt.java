package com.jjb.ecms.biz.activiti;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

public interface RuntimeServiceExt extends RuntimeService {
	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String activitiId);

	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String businessKey, String activitiId);

	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String activitiId,
			Map<String, Object> variables);

	public ProcessInstance restartProcessInstanceByKey(
			String processDefinitionKey, String businessKey, String activitiId,
			Map<String, Object> variables);
}
