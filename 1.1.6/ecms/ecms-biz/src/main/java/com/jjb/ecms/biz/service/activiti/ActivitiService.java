package com.jjb.ecms.biz.service.activiti;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

import com.jjb.ecms.facility.dto.DeploymentDto;
import com.jjb.ecms.facility.dto.ProcessDefinitionDto;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * @description 工作流专用service
 * @author hn
 * @date 2016年8月27日13:49:23  
 */
public interface ActivitiService {
		
	/**
	 * 发起工作流
	 */
	String startNewProcess(String processKey, String businessKey ,Map<String, Serializable> vars);
	
	/**
	 * 重启工作流
	 */
	String restartNewProcess(String processKey, String businessKey ,Map<String, Serializable> vars);
	
	/**
	 * 执行任务时，根据taskId获取formKey
	 */
	String handleTask(String taskId,String appNo) throws ProcessException;
	
	/**
	 * 获取任务 
	 */
	void claimTask(String taskId,String appNo) throws ProcessException;
	/**
	 * 完成任务
	 * @param taskId
	 * @param vars
	 * @throws ProcessException
	 */

	void completeTask(String taskId, Map<String, Serializable> vars, String appNo) throws ProcessException;
	 
	 /**
	  * 获取流程节点图片路径 
	  */
	 InputStream getProImgPath(String taskId);
	 
	 /**
	  * 获取流程定义列表
	  * @return
	  */
	 List<ProcessDefinitionDto> getProcessDefinitionDtos();
	 /**
	  * 获取部署信息
	  * @return
	  */
	 List<DeploymentDto> getDeploymentDtos();
	 /**
	  * 保存流程图
	  * @param diagramFile
	  */
	 void saveDeployment(MultipartFile diagramFile, String proName);
	 /**
	  * 获取流程图
	  * @param diagramResourceName
	  * @param deploymentId
	  * @return
	  */
	 InputStream getProImgPathByDeploymentId(String diagramResourceName, String deploymentId);
	 /**
	  * 删除该流程部署
	  * @param deploymentId
	  */
	 void deleteDeployment(String deploymentId);
	 /**
	  * 设置默认流程图
	  * @param procdefKey
	  */
	 void initDeployment(String procdefKey, String deploymentId);
	 
	 /**
	  * 获取默认流程图
	  * @return
	  */
	 public String getDefProcess();
	 
	 /**
	  * 获取默认流程图的部署ID
	  * @return
	  */
	 public String getDefProcessDepId();
	 
	 /**
	  * 根据申请件编号获取taskId
	  * @param appNo
	  * @return
	  */
	 public Task getTaskId(String appNo) throws ProcessException;
	/**
	 * 认领与解锁任务
	 * @param taskId
	 * @param appNo
	 * @param opUser
	 * @throws ProcessException
	 */
	public void claimOrCancelTask(Task task,String appNo,String opUser,String opType) throws ProcessException;

	/**
	 * 分配任务赋值到工作流里面
	 * @param task 工作流任务数据表
	 * @param appMain 申请主表
	 * @param taskTran 任务转移记录
	 * @param source =AppConstant.Claim（获取）、Cancel（取消）、Complete（完成）;
	 * @throws ProcessException
	 */
	public void assingneeTask(Task task,TmAppMain appMain,TmTaskTransfer taskTran,String source) throws ProcessException ;
	
}
