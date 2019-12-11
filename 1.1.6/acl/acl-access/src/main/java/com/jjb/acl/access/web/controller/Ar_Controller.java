package com.jjb.acl.access.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.access.service.AccessTableService;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/ar_")
public class Ar_Controller extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    AccessDictService accessDictService;
	@Autowired
    AccessTableService accessTableService;
	
	@ResponseBody
	@RequestMapping("/dict")
	public Json dict(){
		
		String dicttype = getPara("dicttype");
		Json j = Json.newSuccess();
		try{
			List<TmAclDict> list = accessDictService.getByType(dicttype);
			j.setObj(list);
		}catch(Exception e){
			j.setFail("获取业务字典值失败，字典类型["+dicttype+"]");
		}
		
		return j;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/table")
	public Json table(){
		String tableClass = getPara("tableClass");
		String json = getPara("json");
		
		String _PARENT_KEY = getPara("_PARENT_KEY");
		String _PARENT_VALUE = getPara("_PARENT_VALUE");
		if(StrKit.notBlank(_PARENT_KEY)&&StrKit.notBlank(_PARENT_VALUE)){

			String prefix = json.substring(0, json.lastIndexOf("}"));
			String suffix = json.substring(json.lastIndexOf("}"));
			
			String quote = "\""; 
			if(json.matches("\\{\\s*'.*")){  //判断是单引号还是双引号
				quote = "'";
			}
			
			if(json.indexOf(":")>0){
				prefix = prefix+",";
			}
			
			json = prefix + quote + _PARENT_KEY.trim() + quote + ":" + quote + _PARENT_VALUE.trim() + quote + suffix;
		}
		
		Json j = Json.newSuccess();
		try{
			List list = new ArrayList();
			Class c = null;
			try {
				c = Class.forName(tableClass);
				Object entity = JSON.parseObject(json, c);
				list = accessTableService.getTable(entity);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("获取表信息失败,表["+tableClass+"],参数", e);
			}
			
			j.setObj(list);
		}catch(Exception e){
			logger.error("获取表信息失败,表["+tableClass+"],参数", e);
			j.setFail("获取表信息失败,表[" + tableClass + "],参数" + json);
		}
		
		return j;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/table2")
	public Json table2(){
		String tableClass = getPara("tableClass");
		String req = getPara("json");
		String json = "{";
		String _PARENT_KEY = getPara("_PARENT_KEY");
		String _PARENT_VALUE = getPara("_PARENT_VALUE");
		if(StrKit.notBlank(_PARENT_KEY)&&StrKit.notBlank(_PARENT_VALUE)){
//			String prefix = json.substring(0, json.lastIndexOf("}"));
//			String suffix = json.substring(json.lastIndexOf("}"));
//			
//			String quote = "\""; 
//			if(json.matches("\\{\\s*'.*")){  //判断是单引号还是双引号
//				quote = "'";
//			}
//			
//			if(json.indexOf(":")>0){
//				prefix = prefix+",";
//			}
			String quote = "\""; 
			json = json + req +","+ quote + _PARENT_KEY.trim() + quote + ":" + quote + _PARENT_VALUE.trim() + quote ;
		}
		json = json + "}";
		Json j = Json.newSuccess();
		try{
			List list = new ArrayList();
			Class c = null;
			try {
				c = Class.forName(tableClass);
				json = json.replace("'", "\"");
				Object entity = JSON.parseObject(json, c);
				list = accessTableService.getTable(entity);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("获取表信息失败,表["+tableClass+"],参数 json ["+json+"]", e);
			}
			j.setObj(list);
		}catch(Exception e){
			logger.error("获取表信息失败,表["+tableClass+"],参数 json ["+json+"]", e);
			j.setFail("获取表信息失败,表["+tableClass+"],参数"+json);
		}
		
		return j;
	}


}
