package com.jjb.ecms.app.controller.manage;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.SortOrder;

import com.jjb.acl.biz.service.TmSystemAuditService;
import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.acl.infrastructure.TmSystemAudit;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.ByteArrayInputStream;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 
 * @author J.J
 * @date 2017年7月14日下午3:48:05
 */
@Controller
@RequestMapping("/tmSystemAudit")
public class TmSystemAuditController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmSystemAuditService tmSystemAuditService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;

	@RequestMapping("/tmSystemAuditPage")
	public String page(){
		return "/applyManage/systemAudit/systemAuditInfo_V1.ftl";
	}
	
	/*
	 * 修改记录列表
	 */
	@ResponseBody
	@RequestMapping("/tmSystemAuditList")
	public Page<TmSystemAudit> tmSystemAuditList(String operatorId){
		Page<TmSystemAudit> page = getPage(TmSystemAudit.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		page = tmSystemAuditService.getPage(page,operatorId);
		return page;
	}
	
	/*
	 * 获取修改前操作记录
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/getPreRecord")
	public void getPreRecord(HttpServletRequest request,
			HttpServletResponse response) {
		int id = getParaToInt("id");
		TmSystemAudit tmSystemAudit= tmSystemAuditService.getTmSystemAuditById(id);
		String oldObject = tmSystemAudit.getOldObject();
		ByteArrayInputStream is = new ByteArrayInputStream(oldObject.getBytes());
		if (is == null) {
			logger.info("未获取到对应的内容！");
		}
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			IOUtils.copy(is, os);
		} catch (IOException e1) {
			logger.info("获取流发生异常");
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
	} 
	
	
	
	/*
	 * 获取修改后操作记录
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/getAfterRecord")
	public void getAfterRecord(HttpServletRequest request,
			HttpServletResponse response) {
		int id = getParaToInt("id");
		TmSystemAudit tmSystemAudit= tmSystemAuditService.getTmSystemAuditById(id);
		String newObject = tmSystemAudit.getNewObject();
		ByteArrayInputStream is = new ByteArrayInputStream(newObject.getBytes());
		if (is == null) {
			logger.info("未获取到对应的内容！");
		}
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			IOUtils.copy(is, os);
		} catch (IOException e1) {
			logger.info("获取流发生异常");
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * 系统数据修改历史记录页面
	 * @return
	 */
	@RequestMapping("/AllSystemAuditPage")
	public String getPage(){
		return "/applyManage/systemAudit/systemAuditPage.ftl";
	}

	/**
	 *
	 * @param operatorId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/AllSystemAuditList")
	public Page<TmSystemAudit> AllSystemAuditList(String operatorId){
		Page<TmSystemAudit> page = getPage(TmSystemAudit.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		return systemAuditHistoryUtils.getSystemAuditPage(page);
	}

}
