package com.jjb.dmp.web.controller;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.service.DmpVarService;
import com.jjb.dmp.engine.bean.TableOptionBean;
import com.jjb.dmp.engine.model.enums.OptionType;
import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/dmp/dmpVar")
public class DmpVarController extends BaseController{

	@Autowired
	private DmpVarService dmpVarService;
	
	/**自定义函数*/
	@RequestMapping("/page")
	public String varPage(){
		
		BigDecimal bd = new BigDecimal(1.12);
		setAttr("bd", bd);
		
		double d = 123123.22112;
		setAttr("bdd", d);
		
		return "dmp/dmpVar/dmpVar.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/varList")
	public Page<TmDmpVar> varList(){
		Page<TmDmpVar> page = getPage(TmDmpVar.class);
		return dmpVarService.getPage(page);
	}
	
	@RequestMapping("/addVarPage")
	public String addVarPage(){
		TmDmpVar tmDmpVar = new TmDmpVar();
		setAttr("tmDmpVar", tmDmpVar);
		return "dmp/dmpVar/dmpVarForm.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/addVar")
	public Json addVar(){
		Json j = Json.newSuccess();
		TmDmpVar tmDmpVar = getBean(TmDmpVar.class);
		try{
			dmpVarService.saveTmDmpVar(tmDmpVar);;
			j.setObj(tmDmpVar);
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editVarPage")
	public String editVarPage(String varType,String varCd){
		TmDmpVar tmDmpVar = dmpVarService.getTmDmpVar(varType, varCd);
		setAttr("tmDmpVar", tmDmpVar);
		setEdit();
		return "dmp/dmpVar/dmpVarForm.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/editVar")
	public Json editVar() {
		Json j = Json.newSuccess();
		TmDmpVar tmDmpVar = getBean(TmDmpVar.class);
		
		String optionType = tmDmpVar.getOptionType();
		if(OptionType.T.name().equals(optionType)){
			String optionParam = tmDmpVar.getOptionParam();
			TableOptionBean tableOptionBean = JSONObject.parseObject(optionParam, TableOptionBean.class);
			try {
				Class<?> c = Class.forName(tableOptionBean.getModel());
				Object entity = c.newInstance();
				BeanUtils.populate(entity, tableOptionBean.getFilter());
			}catch (Exception e) {
				j.setFail("选项参数配置错误："+e.getMessage());
			}
		}
		
		if(!j.isS()){
			return j;
		}
		
		try{
			dmpVarService.editTmDmpVar(tmDmpVar);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deleteVar")
	public Json deleteVar(String varType,String varCd){
		Json j = Json.newSuccess();
		try{
			dmpVarService.deleteTmDmpVar(varType,varCd);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
}
