package com.jjb.ecms.app.controller.param;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.biz.service.param.SysParamService;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/** 
 * @description cas系统参数配置
 * @author hn
 * @date 2016年8月29日15:15:03
 */

@Controller
@RequestMapping("/sysParam")
public class SysParamController extends BaseController{
	
	
	@Autowired
	private SysParamService paramService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;
	
	/*
	 * 进入参数页面 
	 */
	@RequestMapping("/page")
	public String page(){
		return "/applyParam/sysParam/sysParam_V1.ftl";
	}
	
	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmDitDic> list(TmDitDic ditDic){
		
		Page<TmDitDic> page = getPage(TmDitDic.class);
		page = paramService.getPage(page,ditDic);
		return page;
	}
	
	/*
	 * 进入参数新建或者编辑页面 
	 */
	@RequestMapping("/addpage")
	public String addpage(){
		
		Integer id = getParaToInt("id");
		if(id!=null){
			setAttr("param", paramService.getTmDitDic(id));
		}
		return "/applyParam/sysParam/sysParamForm_V1.ftl";
	}
	/*
	 * 添加参数 
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmDitDic tmDitDic){
		Json j = Json.newSuccess();
		try{
			paramService.saveTmDitDic(tmDitDic);
			//记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("参数类型: "+tmDitDic.getDicType(),"参数列表","SAVE","",tmDitDic.convertToMap().toString());
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	/*
	 * 编辑页面 
	 */
	@RequestMapping("/editpage")
	public String editpage(int id){

		TmDitDic tmDitDic = paramService.getTmDitDic(id);
		setAttr("param", tmDitDic);
		setEdit();
		return "/applyParam/sysParam/sysParamForm_V1.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmDitDic tmDitDic){
		Json j = Json.newSuccess();
		try{
			TmDitDic odeTmDitDic = paramService.getTmDitDic(tmDitDic.getId());
			paramService.editTmDitDic(tmDitDic);
			//记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("参数类型: "+tmDitDic.getDicType(),"参数列表","UPDATE",odeTmDitDic.convertToMap().toString(),tmDitDic.convertToMap().toString());

		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	/*
	 * 删除参数 
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(int id){
		Json j = Json.newSuccess();
		try{
			TmDitDic odeTmDitDic = paramService.getTmDitDic(id);
			paramService.deleteTmDitDic(id);
			//记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("参数类型: "+odeTmDitDic.getDicType(),"参数列表","DELETE",odeTmDitDic.convertToMap().toString(),"");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Json deletes(){
		Json j = Json.newSuccess();
		
		return j;
	}

}
