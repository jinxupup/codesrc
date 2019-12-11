package com.jjb.ecms.biz.activiti;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @author T.B
 * @author Joram Barrez
 */
public class RestartProcessInstanceCmd<T> implements Command<ProcessInstance>,
		Serializable {

	private static final long serialVersionUID = 1L;
	protected String processDefinitionKey;
	protected String processDefinitionId;
	protected Map<String, Object> variables;
	protected String businessKey;
	protected String activityId;

	public RestartProcessInstanceCmd(String processDefinitionKey,
			String processDefinitionId, String businessKey, String activityId,
			Map<String, Object> variables) {
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.variables = variables;
		this.activityId = activityId;
	}

	public ProcessInstance execute(CommandContext commandContext) {
		DeploymentManager deploymentCache = Context
				.getProcessEngineConfiguration().getDeploymentManager();

		// Find the process definition
		ProcessDefinitionEntity processDefinition = null;
		if (processDefinitionId != null) {
			processDefinition = deploymentCache
					.findDeployedProcessDefinitionById(processDefinitionId);
			if (processDefinition == null) {
				throw new ActivitiObjectNotFoundException(
						"No process definition found for id = '"
								+ processDefinitionId + "'",
						ProcessDefinition.class);
			}
		} else if (processDefinitionKey != null) {
			processDefinition = deploymentCache
					.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
			if (processDefinition == null) {
				throw new ActivitiObjectNotFoundException(
						"No process definition found for key '"
								+ processDefinitionKey + "'",
						ProcessDefinition.class);
			}
		} else {
			throw new ActivitiIllegalArgumentException(
					"processDefinitionKey and processDefinitionId are null");
		}

		// Do not start process a process instance if the process definition is
		// suspended
		if (processDefinition.isSuspended()) {
			throw new ActivitiException(
					"Cannot start process instance. Process definition "
							+ processDefinition.getName() + " (id = "
							+ processDefinition.getId() + ") is suspended");
		}

		// 获取中断的节点
		ActivityImpl restartActiviti = processDefinition
				.findActivity(activityId);

		// Start the process instance
		ExecutionEntity processInstance = processDefinition
				.createProcessInstance(businessKey, restartActiviti);
		if (variables != null) {
			processInstance.setVariables(variables);
		}
		processInstance.start();

		return processInstance;
	}
}
