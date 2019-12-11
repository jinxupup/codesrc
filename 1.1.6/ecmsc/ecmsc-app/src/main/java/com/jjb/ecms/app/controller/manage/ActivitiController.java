package com.jjb.ecms.app.controller.manage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.facility.dto.ProcessDefinitionDto;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 *  流程管理
 * @author hp
 *
 */
@Controller
@RequestMapping("/activiti")
public class ActivitiController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ActivitiService activitiService;
	
	/*
	 * 获取流程定义图片(通过taskId获取)
	 */
	@RequestMapping("/showProDefImg")
	public ModelAndView showProDefImg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String taskId = getPara("taskId");// 从页面上获取taskId
		InputStream is = activitiService.getProImgPath(taskId);// 从service中获取流
		if (is == null) {
			throw new ProcessException("未获取到对应的流程图！");
		}
		OutputStream os = response.getOutputStream();
		try {
			IOUtils.copy(is, os);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
		response.setContentType("image/png");// 设置输出类型

		return null;

	}

	/**
	 * 跳转到工作流程部署页面
	 * 
	 * @return
	 */
	@RequestMapping("/activitiDeployePage")
	public String activitiDeployePage() {
		// 获取默认流程图的部署ID
		String deploymentId = activitiService.getDefProcessDepId();
		setAttr("defDeploymentId", deploymentId);
//		return "activitiDeploy/activitiDeployPage.ftl";
		return "/applyManage/applyActivitiDeploy/applyActivitiDeployPage_V1.ftl";
	}

	/**
	 * 上传提交流程图
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitDiagrams")
	public Json submitDiagrams() {
		Json j = Json.newSuccess();
		try {
			MultipartFile diagramFile = getFile("file");
			String fileName = (diagramFile == null) ? null : diagramFile.getOriginalFilename();
			if(fileName!=null){
				String suffix = fileName.substring(fileName.indexOf(".") + 1);// 没有“.”也不会抛异常
				if (!"zip".equals(suffix)) {
					j.setFail("流程图上传失败，必须压缩为zip格式");
					return j;
				}
				String proName = getPara("proName");
				activitiService.saveDeployment(diagramFile, proName);
			}
		} catch (Exception e) {
			j.setFail("流程图解析失败，请检查流程图");
		}
		return j;
	}
	
	/**
	 * 流程定义列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/processDefList")
	public Page<ProcessDefinitionDto> processDefList(){
		// 获取到所有的流程定义信息
		Page<ProcessDefinitionDto> page = new Page<ProcessDefinitionDto>();
		List<ProcessDefinitionDto> processDefinitionDtos = activitiService.getProcessDefinitionDtos();
		page.setRows(processDefinitionDtos);
		page.setTotal(processDefinitionDtos==null ? 0 : processDefinitionDtos.size());
		return page;
	}

	/*
	 * 获取流程定义图片(通过deploymentId和图片路径获取)
	 */
	@RequestMapping("/getProDefImg")
	public ModelAndView getProDefImg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String diagramResourceName = getPara("diagramResourceName");// 从页面上获取diagramResourceName
		String deploymentId = getPara("deploymentId");// 从页面上获取deploymentId

		InputStream is = activitiService.getProImgPathByDeploymentId(
				diagramResourceName, deploymentId);// 从service中获取流
		if (is == null) {
			logger.info("未获取到对应的流程图！");
		}
		OutputStream os = response.getOutputStream();
		try {
			IOUtils.copy(is, os);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
		response.setContentType("image/png");// 设置输出类型

		return null;
	}

	/*
	 * 删除流程图
	 */
	@ResponseBody
	@RequestMapping("/deleteDeployment")
	public Json deleteDeployment(String deploymentId) {
		Json j = Json.newSuccess();
		try {
			activitiService.deleteDeployment(deploymentId);
		} catch (Exception e) {
			j.setFail("流程图删除出现异常");
		}
		return j;
	}

	/**
	 * 设置默认流程
	 * 
	 * @param procdefKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initDeployment")
	public Json initDeployment(String procdefKey, @RequestParam(required=false) String deploymentId) {
		Json j = Json.newSuccess();
		try {
			activitiService.initDeployment(procdefKey, deploymentId);
		} catch (Exception e) {
			j.setFail(e.getMessage());
		}
		return j;
	}
}