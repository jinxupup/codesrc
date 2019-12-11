package com.jjb.acl.mvc.web.controller;

import java.util.List;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.acl.infrastructure.TmAclUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.DictService;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("/acl/dict")
public class DictController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DictService dictService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;

//	@Autowired
//	private UserService	userService;
	
	@RequestMapping("/page")
	public String page(){
		
		setAttr("types", dictService.selectGroupType());
		
		return "acl/dict/dict.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclDict> list(){
		
		String notinType[] = getParas("notinType");
		Page<TmAclDict> page = getPage(TmAclDict.class);
		
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("notinType", notinType);
		page = dictService.getPage(page);
		
		return page;
	}
	
	
	@RequestMapping("/addpage")
	public String addpage(){
		
		Integer id = getParaToInt("id");
		if(id!=null){
			setAttr("dict", dictService.getTmAclDict(id));
		}
		
		return "acl/dict/dict-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclDict tmAclDict){
		
		Json j = Json.newSuccess();
		try{
			dictService.saveTmAclDict(tmAclDict);
			//添加审计历史
			systemAuditHistoryUtils.saveSystemAudit("字典类型: "+tmAclDict.getType(),"业务字典","SAVE","",tmAclDict.convertToMap().toString());
		}catch(Exception e){
			logger.error("tmAclDict ����ʧ�� tmAclDict ["+tmAclDict+"]", e);
			j.setFail(e.getMessage());
		}
		
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(int id){

		TmAclDict tmAclDict = dictService.getTmAclDict(id);
		setAttr("dict", tmAclDict);
		setEdit();
		return "acl/dict/dict-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmAclDict tmAclDict){
		Json j = Json.newSuccess();
		
		try{
			TmAclDict OdeAclDict = dictService.getTmAclDict(tmAclDict.getId());
			dictService.editTmAclDict(tmAclDict);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("字典类型: "+tmAclDict.getType(),"业务字典","UPDATE",OdeAclDict.convertToMap().toString(),tmAclDict.convertToMap().toString());
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(int id){
		
		Json j = Json.newSuccess();
		
		try{
			TmAclDict tmAclDict = dictService.getTmAclDict(id);
			dictService.deleteTmAclDict(id);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("字典类型: "+tmAclDict.getType(),"业务字典","DELETE",tmAclDict.convertToMap().toString(),"");
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
			dictService.deleteBatchTmAclDict(ids);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
}
