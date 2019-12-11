package com.jjb.cmp.app.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 统一样式的错误提示页面
 * @author hp
 *
 */
@Service
public class CmpExceptionPageUtils extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(CmpExceptionPageUtils.class);

	/**
	 * 执行错误界面的方法,返回错误页面，展示对应的错误信息
	 * @param String errMessage 错误原因
	 * @return String 错误页面地址
	 */

	public String doExcepiton(String errMessage, String batchNo) {
		logger.info("ExceptionPage----系统错误，系统跳转错误页面:" + errMessage);
		String errMsg = "";
		if (StringUtils.isNotEmpty(batchNo) && !StringUtils.concat(errMessage, "内容批次")) {
			errMsg = "您当前操作的内容批次[" + batchNo + "]异常!<br/>";
		}
		errMsg = errMsg + errMessage;
		this.setAttr("imageErrMessage", errMsg);
		return "/common/error/excepitonPageErr.ftl";
	}
}
