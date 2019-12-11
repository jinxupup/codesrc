package com.jjb.acl.mvc.web.controller;

import java.util.List;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.ParamService;
import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/** 
 * @author  ltm 
 * @date 创建时间：2016年8月9日 上午10:36:06 
 */

@Controller
@RequestMapping("/acl/param")
public class ParamController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ParamService paramService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;

//	@Autowired
//	private UserService	userService;
	
	@RequestMapping("/page")
	public String page(){
		return "acl/param/param.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclParam> list(){
		
		Page<TmAclParam> page = getPage(TmAclParam.class);
		page = paramService.getPage(page);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addpage(){
		
		Integer id = getParaToInt("id");
		if(id!=null){
			setAttr("param", paramService.getTmAclParam(id));
		}
		
		return "acl/param/param-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclParam tmAclParam){
		Json j = Json.newSuccess();
		
		try{
			paramService.saveTmAclParam(tmAclParam);
			//添加审计历史
			systemAuditHistoryUtils.saveSystemAudit("参数类型: "+tmAclParam.getType(),"参数管理","SAVE","",tmAclParam.convertToMap().toString());
		}catch(Exception e){
			logger.error("tmAclParam添加失败", e);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(int id){

		TmAclParam tmAclParam = paramService.getTmAclParam(id);
		setAttr("param", tmAclParam);
		setEdit();
		return "acl/param/param-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmAclParam tmAclParam){
		Json j = Json.newSuccess();
		
		try{
			TmAclParam odeTmAclParam = paramService.getTmAclParam(tmAclParam.getId());
			paramService.editTmAclParam(tmAclParam);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("参数类型: "+odeTmAclParam.getType(),"参数管理","UPDATE",odeTmAclParam.convertToMap().toString(),tmAclParam.convertToMap().toString());
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	@ResponseBody
	@RequestMapping("/delete")
	/*public String delete(int id){
		
		paramService.deleteTmAclParam(id);
		
		return "param/param.ftl";
	}*/
	public Json delete(int id){
		Json j = Json.newSuccess();
		
		try{
			TmAclParam odeTmAclParam = paramService.getTmAclParam(id);
			paramService.deleteTmAclParam(id);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("参数类型: "+odeTmAclParam.getType(),"参数管理","DELETE",odeTmAclParam.convertToMap().toString(),"");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Json deletes(){
		Json j = Json.newSuccess();
		
		List<Integer> ids = getList(Integer.class, "ids");
		
		try{
			paramService.deleteBatchTmAclParam(ids);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}

	@RequestMapping("/refresh")
	public String refresh(){
		
		return "acl/refresh/refresh.ftl";
	}
	
}
