package com.jjb.dmp.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.service.DmpVarService;
import com.jjb.dmp.biz.service.StrategyCategoryService;
import com.jjb.dmp.biz.service.StrategyFunctionService;
import com.jjb.dmp.biz.service.StrategyVarService;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.FunctionVar;
import com.jjb.dmp.infrastructure.TmDmpStrategyCategory;
import com.jjb.dmp.infrastructure.TmDmpStrategyFunction;
import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 方案类别
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/strategyCategory")
public class StrategyCategoryController extends BaseController{

	@Autowired
	private StrategyCategoryService strategyCategoryService	;
	@Autowired
	private StrategyVarService strategyVarService;
	@Autowired
	private StrategyFunctionService strategyFunctionService;
	
	@Autowired
	private DmpVarService dmpVarService;
	
	@RequestMapping("/page")
	public String page(){
		return "dmp/strategyCategory/strategyCategory.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmDmpStrategyCategory> list(){
		
		Page<TmDmpStrategyCategory> page = getPage(TmDmpStrategyCategory.class);
		page = strategyCategoryService.getPage(page);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addPage(){
		return "dmp/strategyCategory/strategyCategory-add.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmDmpStrategyCategory tmDmpStrategyCategory){
		Json j = Json.newSuccess();
		
		try{
			strategyCategoryService.saveTmDmpStrategyCategory(tmDmpStrategyCategory);
			j.setObj(tmDmpStrategyCategory);
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(){
		String stClass  = getPara("stClass");
		TmDmpStrategyCategory tmDmpStrategyCategory = strategyCategoryService.getTmDmpStrategyCategory(stClass);
		setAttr("tmDmpStrategyCategory", tmDmpStrategyCategory);
		setEdit();
		return "dmp/strategyCategory/strategyCategory-edit.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmDmpStrategyCategory tmDmpStrategyCategory){
		Json j = Json.newSuccess();
		
		try{
			strategyCategoryService.editTmDmpStrategyCategory(tmDmpStrategyCategory);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(String stClass){
		Json j = Json.newSuccess();
		try{
			strategyCategoryService.deleteTmDmpStrategyCategory(stClass);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	
	
	/**自定义函数*/
	@RequestMapping("/varPage")
	public String varPage(String stClass){
		setAttr("stClass", stClass);
		return "dmp/strategyCategory/strategyVar.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/varList")
	public Page<TmDmpStrategyVar> varList(String stClass){
		List<TmDmpStrategyVar> list = strategyVarService.getVarList(stClass);
		Page<TmDmpStrategyVar> page = new Page<TmDmpStrategyVar>();
		page.setRows(list);
		return page;
	}
	
	@RequestMapping("/addVarPage")
	public String addVarPage(String stClass){
		
		setAttr("stClass", stClass);
		List<TmDmpVar> varList = dmpVarService.getVarList(new TmDmpVar());
		setAttr("varList", varList);
		return "dmp/strategyCategory/dialog/strategyVarAdd.ftl";
	}
	
	/**
	 * 为决策类别添加变量
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addVar")
	public Json addVar(){
		Json j = Json.newSuccess();
		try{
			String stClass = getPara("stClass");
			
			String strategyVars = getPara("strategyVars");
			Map<String, Object> varMap = JSONObject.parseObject(strategyVars);
			
			List<TmDmpStrategyVar> varList = new ArrayList<TmDmpStrategyVar>();
			for(Map.Entry<String, Object> entry:varMap.entrySet()){
				TmDmpStrategyVar var = JSONObject.toJavaObject((JSON)entry.getValue(), TmDmpStrategyVar.class);
				varList.add(var);
			}
			
			strategyVarService.addTmDmpStrategyVars(stClass,varList);
			
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
//	@ResponseBody
//	@RequestMapping("/addVar")
//	public Json addVar(){
//		Json j = Json.newSuccess();
//		TmDmpStrategyVar tmDmpStrategyVar = getBean(TmDmpStrategyVar.class);
//		tmDmpStrategyVar.setIsInput(tmDmpStrategyVar.getIsInput()==null?"N":tmDmpStrategyVar.getIsInput());
//		tmDmpStrategyVar.setIsOutput(tmDmpStrategyVar.getIsOutput()==null?"N":tmDmpStrategyVar.getIsOutput());
//		try{
//			strategyVarService.saveTmDmpStrategyVar(tmDmpStrategyVar);;
//			j.setObj(tmDmpStrategyVar);
//		}catch(Exception e){
//			e.printStackTrace();
//			j.setFail(e.getMessage());
//		}
//		
//		return j;
//	}
	
	@RequestMapping("/editVarPage")
	public String editVarPage(String stClass,String varType,String varCd){
		TmDmpStrategyVar tmDmpStrategyVar = strategyVarService.getTmDmpStrategyVar(stClass,varType, varCd);
		setAttr("tmDmpStrategyVar", tmDmpStrategyVar);
		setEdit();
		return "dmp/strategyCategory/dialog/strategyVarForm.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/editVar")
	public Json editVar() {
		Json j = Json.newSuccess();
		TmDmpStrategyVar tmDmpStrategyVar = getBean(TmDmpStrategyVar.class);
		tmDmpStrategyVar.setIsInput(tmDmpStrategyVar.getIsInput()==null?"N":tmDmpStrategyVar.getIsInput());
		tmDmpStrategyVar.setIsOutput(tmDmpStrategyVar.getIsOutput()==null?"N":tmDmpStrategyVar.getIsOutput());
		
//		String optionType = tmDmpStrategyVar.getOptionType();
//		if(OptionType.T.name().equals(optionType)){
//			String optionParam = tmDmpStrategyVar.getOptionParam();
//			TableOptionBean tableOptionBean = JSONObject.parseObject(optionParam, TableOptionBean.class);
//			try {
//				Class<?> c = Class.forName(tableOptionBean.getModel());
//				Object entity = c.newInstance();
//				BeanUtils.populate(entity, tableOptionBean.getFilter());
//			}catch (Exception e) {
//				j.setFail("选项参数配置错误："+e.getMessage());
//			}
//		}
		
		if(!j.isS()){
			return j;
		}
		
		try{
			strategyVarService.editTmDmpStrategyVar(tmDmpStrategyVar);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deleteVar")
	public Json deleteVar(String stClass,String varType,String varCd){
		Json j = Json.newSuccess();
		try{
			TmDmpStrategyVar tmDmpStrategyVar = new TmDmpStrategyVar();
			tmDmpStrategyVar.setStClass(stClass);
			tmDmpStrategyVar.setVarType(varType);
			tmDmpStrategyVar.setVarCd(varCd);
			
			strategyVarService.deleteTmDmpStrategyVar(tmDmpStrategyVar);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	
	/**自定义函数*/
	@RequestMapping("/funPage")
	public String funPage(String stClass){
		setAttr("stClass", stClass);
		return "dmp/strategyCategory/strategyFun.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/funList")
	public Page<TmDmpStrategyFunction> funList(String stClass){
		List<TmDmpStrategyFunction> list = strategyFunctionService.getFunctionList(stClass);
		Page<TmDmpStrategyFunction> page = new Page<TmDmpStrategyFunction>();
		page.setRows(list);
		return page;
	}
	
	@RequestMapping("/addFunPage")
	public String addFunPage(String stClass){
		
		TmDmpStrategyFunction tmDmpStrategyFunction = new TmDmpStrategyFunction();
		tmDmpStrategyFunction.setStClass(stClass);
		setAttr("tmDmpStrategyFunction", tmDmpStrategyFunction);
		setFuntionPageReq(stClass);
		
		return "dmp/strategyCategory/dialog/strategyFunForm.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/addFun")
	public Json addFun(){
		Json j = Json.newSuccess();
		TmDmpStrategyFunction tmDmpStrategyFunction = getBean(TmDmpStrategyFunction.class);
		try{
			//函数名加前缀
			tmDmpStrategyFunction.setFunCd(FunctionVar.FUNCTION_PREFIX+tmDmpStrategyFunction.getFunCd());
			strategyFunctionService.saveTmDmpStrategyFunction(tmDmpStrategyFunction);;
			j.setObj(tmDmpStrategyFunction);
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editFunPage")
	public String editFunPage(String stClass,String funCd){
		TmDmpStrategyFunction tmDmpStrategyFunction = strategyFunctionService.getTmDmpStrategyFunction(stClass, funCd);
		setAttr("tmDmpStrategyFunction", tmDmpStrategyFunction);
		setFuntionPageReq(stClass);
		setEdit();
		return "dmp/strategyCategory/dialog/strategyFunForm.ftl";
	}
	
	//设置function 页面需要的模型
	private void setFuntionPageReq(String stClass){
		Map<String, FieldVar> inputVarMap = strategyCategoryService.getInputFieldVars(stClass);
		Map<String, String> inputVars = new HashMap<String, String>();
		if(inputVarMap!=null && inputVarMap.size()>0){
			for(Map.Entry<String, FieldVar> var:inputVarMap.entrySet()){
				inputVars.put(var.getKey(), var.getValue().getVarName());
			}
		}
		setAttr("inputVars", inputVars);
	}
	
	@ResponseBody
	@RequestMapping("/editFun")
	public Json editFun(){
		Json j = Json.newSuccess();
		TmDmpStrategyFunction tmDmpStrategyFunction = getBean(TmDmpStrategyFunction.class);
		try{
			strategyFunctionService.editTmDmpStrategyFunction(tmDmpStrategyFunction);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deleteFun")
	public Json deleteFun(String stClass,String funCd){
		Json j = Json.newSuccess();
		try{
			strategyFunctionService.deleteTmDmpStrategyFunction(stClass,funCd);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
}	