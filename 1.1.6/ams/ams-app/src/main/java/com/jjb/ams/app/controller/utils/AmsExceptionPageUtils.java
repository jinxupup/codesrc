package com.jjb.ams.app.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

@Service
public class AmsExceptionPageUtils extends BaseController{
	
	private final static Logger logger = LoggerFactory.getLogger(AmsExceptionPageUtils.class);
	
	/**
	 *  执行错误界面的方法,返回错误页面，展示对应的错误信息
	 *  
	 *  @param String errMessage  错误原因
	 *
	 *  @return String 错误页面地址
	 */
	
	public String doExcepiton(String errMessage,String appNo){

		/*logger.info("ExceptionPage----系统错误，系统跳转错误页面:" + errMessage);

		this.setAttr("imageErrMessage", "ERR MESSAGE:"+errMessage);

		return "/apply/common/excepitonPageErr.ftl";*/
		logger.info("ExceptionPage----系统错误，系统跳转错误页面:" + errMessage);
		String errMsg = "";
		if(StringUtils.isNotEmpty(appNo) && !StringUtils.concat(errMessage, "申请件")) {
			errMsg = "您当前操作的申请件["+appNo+"]异常!<br/>";
		}
		errMsg = errMsg+errMessage;
		this.setAttr("imageErrMessage", errMsg);

		return "/amsTask/applyCC/common/excepitonPageErr.ftl";
	
	}
	
}
