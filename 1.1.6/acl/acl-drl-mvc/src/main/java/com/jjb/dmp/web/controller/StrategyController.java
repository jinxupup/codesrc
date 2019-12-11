package com.jjb.dmp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 策略方案
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/strategy")
public class StrategyController extends BaseController{

	@Autowired
	private StrategyService strategyService	;
	
	@RequestMapping("/page")
	public String page(){
		return "dmp/strategy/strategy.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmDmpStrategy> list(){
		
		Page<TmDmpStrategy> page = getPage(TmDmpStrategy.class);
		page = strategyService.getPage(page);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addPage(){
		return "dmp/strategy/categorySelect.ftl";
	}
	
	@RequestMapping("/addFormPage")
	public String addFormPage(String stClass){
		setAttr("stClass", stClass);
		return "dmp/strategy/strategy-add.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmDmpStrategy tmDmpStrategy){
		Json j = Json.newSuccess();
		
		try{
			strategyService.saveTmDmpStrategy(tmDmpStrategy);
			j.setObj(tmDmpStrategy);
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(String stId){
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
		setAttr("tmDmpStrategy", tmDmpStrategy);
		setEdit();
		return "dmp/strategy/strategy-edit.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmDmpStrategy tmDmpStrategy){
		Json j = Json.newSuccess();
		
		try{
			strategyService.editTmDmpStrategy(tmDmpStrategy);
		}catch(Exception e){
			j.setFail(e.getMessage());
			e.printStackTrace();
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(String stId){
		Json j = Json.newSuccess();
		try{
			strategyService.deleteTmDmpStrategy(stId);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 方案配置tab页
	 * @param stId
	 * @return
	 */
	@RequestMapping("/strategyDefinePage")
	public String strategyDefinePage(String stId){
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
		setAttr("tmDmpStrategy", tmDmpStrategy);
		setEdit();
		return "dmp/strategy/strategy-edit-define.ftl";
	}
	
}	