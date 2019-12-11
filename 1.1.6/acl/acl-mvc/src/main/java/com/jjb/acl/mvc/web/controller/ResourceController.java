package com.jjb.acl.mvc.web.controller;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.ResourceService;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/** 
 * @author  ltm 
 * @date 创建时间：2016年7月28日 下午2:30:02 
 * @version Aps-5.0 
 * @parameter    
 */
@Controller
@RequestMapping("/acl/resource")
public class ResourceController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceService resourceService	;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;
	
//	@Autowired
//	private RoleResourceService roleResourceService;
	
	@RequestMapping("/page")
	public String page(){
		
		return "acl/resource/resource.ftl";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclResource> list(){
		
		Page<TmAclResource> page = getPage(TmAclResource.class);
		page = resourceService.getPage(page);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addPage(){
		String resourceCode  = getPara("resourceCode");
		String sysType = getPara("sysType");
		if(resourceCode!=null){
			setAttr("resource", resourceService.getTmAclResource(sysType,resourceCode));
		}
		return "acl/resource/resource-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclResource tmAclResource){
		Json j = Json.newSuccess();
		
		try{
			resourceService.saveTmAclResource(tmAclResource);
			//添加审计历史
			systemAuditHistoryUtils.saveSystemAudit("资源代码: "+tmAclResource.getResourceCode(),"资源管理","SAVE","",tmAclResource.convertToMap().toString());
		}catch(Exception e){
			logger.error("tmAclResource 保存失败 ["+tmAclResource+"]", e);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(){
		String resourceCode  = getPara("resourceCode");
		String sysType = getPara("sysType");
		TmAclResource tmAclResource = resourceService.getTmAclResource(sysType,resourceCode);
		setAttr("resource", tmAclResource);
		setEdit();
		return "acl/resource/resource-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmAclResource tmAclResource){
		Json j = Json.newSuccess();
		
		try{
			TmAclResource odeTmAclResource = resourceService.getTmAclResource(tmAclResource.getSysType(), tmAclResource.getResourceCode());
			resourceService.editTmAclResource(tmAclResource);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("资源代码: "+odeTmAclResource.getResourceCode(),"资源管理","UPDATE",odeTmAclResource.convertToMap().toString(),tmAclResource.convertToMap().toString());
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(String resourceCode ,String sysType){
		Json j = Json.newSuccess();
		try{
			TmAclResource odeTmAclResource = resourceService.getTmAclResource(sysType, resourceCode);
			resourceService.deleteTmAclResource(resourceCode,sysType);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("资源代码: "+odeTmAclResource.getResourceCode(),"资源管理","DELETE",odeTmAclResource.convertToMap().toString(),"");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	@ResponseBody
	@RequestMapping("/deletes")
	public Json deletes(){
		Json j = Json.newSuccess();
		
//		List<String> ids = getList(String.class, "ids");
		
		try{
			//resourceService.deleteBatchTmAclResource(ids);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}

}
