package com.jjb.dmp.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.dmp.biz.service.StrategyCategoryService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.biz.service.StrategyTrialService;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.TrailVar;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 规则试算
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/strategyTrial")
public class StrategyTrialController extends BaseController{
	
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private StrategyCategoryService strategyCategoryService;
	@Autowired
	private StrategyTrialService strategyTrialService;
	
	@RequestMapping("/page")
	public String page(String stId){
		
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
		setAttr("tmDmpStrategy", tmDmpStrategy);
		
		List<FieldVar> inputFieldVars = new ArrayList<FieldVar>();
		List<FieldVar> outputFieldVars = new ArrayList<FieldVar>();
		
		//决策变量
		strategyService.exchangeFieldVar(tmDmpStrategy,inputFieldVars,outputFieldVars);
				
		setAttr("inputFieldVars", inputFieldVars);
		setAttr("outputFieldVars", outputFieldVars);
		
		return "dmp/strategy/trial/trial.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/trial")
	public Json trial(String stId){
		
		Json j = Json.newSuccess();
		
		Map<String,Object> input = getMap("input");
		
		try{
			
			Map<String,Object> fact = input;
			//调用试算
			TrailVar trailVar = strategyTrialService.trail(stId, fact);
			j.setObj(trailVar);
			
			//错误处理
			if(!trailVar.isSuccess()){
				trailVar.setOut(null);
				throw new BizException(trailVar.getErrMsg());
			}
			
			Map<String,Object> out = trailVar.getOut();
			if(out!=null){
				for(Map.Entry<String, Object> o:out.entrySet()){
					if(o.getValue()==null){
						o.setValue("");
					}else{
						if(o.getValue() instanceof Date){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
							String v = sdf.format(o.getValue());
							o.setValue(v);
						}
					}
				}
			}
			
		}catch(Exception e){
			j.setFail(e.getMessage());
			e.printStackTrace();
		}
		
		return j;
	}
	
}