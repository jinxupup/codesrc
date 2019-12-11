package com.jjb.ecms.biz.ext.cmp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.http.HttpClientUtil2;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.AESUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 与内容管理平台交互支持
 *
 * @author hn
 * @version 1.0
 */
@Component
public class CmpSystemSupport {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;

	/**
	 * 获取cmp内容平台URl-进行影像调阅</br>
	 * 1.判断申请件是否存在影像批次号</br>
	 * 2.如果不存在影像批次号，则调用cmp系统获取一个影像批次号</br>
	 * 3.组装打开cmp内容平台的url地址返回
	 *
	 * @param appNo
	 * @param sysId
	 * @param start
	 * @return
	 */
	public String getCmpSystemShowContentUrl(String appNo, String sysId, long start) {

		String userNo = StringUtils.setValue(OrganizationContextHolder.getUserNo(), AppConstant.SYS_AUTO);
		String showContentUrl = "";
		if (StringUtils.isBlank(appNo)) {
			throw new ProcessException(sysId + "参数错误，申请件编号为空");
		}
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
		if (tmAppMain == null) {
			throw new ProcessException("未获取到有效申请件[" + appNo + "]信息");
		}
		Map<String, String> picParam = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_cmp_conf);
		String sysUrl = "";
		if (picParam != null && picParam.containsKey(AppDitDicConstant.CMP_cmpSysUrl)) {
			if (picParam.containsKey(AppDitDicConstant.CMP_cmpSysUrl)) {
				sysUrl = picParam.get(AppDitDicConstant.CMP_cmpSysUrl);
				if (!sysUrl.endsWith("/")) {
					sysUrl = sysUrl + "/";
				}
			}
		} else {
			throw new ProcessException("系统未查询到内容管理平台相关网络地址配置");
		}
		String showContentPath = "";
		if (picParam.containsKey(AppDitDicConstant.CMP_showContentPathParm)) {
			showContentPath = picParam.get(AppDitDicConstant.CMP_showContentPathParm);
		} else {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "系统未配置["
					+ AppDitDicConstant.CMP_showContentPathParm + "]有效路径");
			throw new ProcessException("系统未查询到内容管理平台内调阅相关配置");
		}

		//判断是否存在影像批次号,准备影像批次号
		if (StringUtils.isEmpty(tmAppMain.getImageNum())) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "影像批次号不存在，准备生成影像批次号");
			Map<String, Object> params = new HashMap<>();
			params.put("org", tmAppMain.getOrg());
			params.put("name", tmAppMain.getName());
			params.put("id_no", tmAppMain.getIdNo());
			params.put("id_type", tmAppMain.getIdType());
			params.put("sys_id", sysId);
			params.put("operator_id", userNo);

			String getBatchNumPath = "";
			if (picParam.containsKey(AppDitDicConstant.CMP_getBatchNumPath)) {
				getBatchNumPath = picParam.get(AppDitDicConstant.CMP_getBatchNumPath);
			} else {
				throw new ProcessException("系统未查询到内容管理平台获取批次号相关配置");
			}
			String httpResult = null;
			if (StringUtils.isNotEmpty(getBatchNumPath)) {
				String url = sysUrl + getBatchNumPath;
				httpResult = HttpClientUtil2.getBatchNo(url, params);
			} else {
				logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "系统未配置有效路径");
			}
			JSONObject jsrs = JSON.parseObject(httpResult);
			if (jsrs == null || jsrs.get("IMAGE_NO") == null) {
				logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "http请求影像批次号失败，未返回影像批次号 ！！！");
				throw new ProcessException("[" + appNo + "]请求影像批次号失败，内容管理平台未返回有效影像批次号！");
			}
			String imageNum = StringUtils.valueOf(jsrs.get("IMAGE_NO"));
			// TODO存入影像批次号到main表
			tmAppMain.setImageNum(imageNum);
			try {
				applyInputService.updateTmAppMain(tmAppMain);
			} catch (ProcessException e) {
				logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "影像批次号存入TM_APP_MAIN表失败 ！！！" + e);
				throw new ProcessException("更新申请件[" + appNo + "]批次号[" + imageNum + "]失败");
			}

		}

		String isUseCustInfo = picParam.get(AppDitDicConstant.CMP_isUseCustInfo);
		showContentPath = picParam.get(AppDitDicConstant.CMP_showContentPathParm);
		JSONObject jsonObject = new JSONObject();
		//判断批次调用影像开关是否开启;
		jsonObject.put("imageNum", tmAppMain.getImageNum());
		if (StringUtils.equals(isUseCustInfo, "Y")) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "开关开启，加密传输，加密传输 idNo , name ");
			jsonObject.put("idNo", tmAppMain.getIdNo());
			jsonObject.put("name", tmAppMain.getName());
		} else {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "开关闭合，加密传输 影像批次号ImageNum");
		}
		try {
			String decrypt = AESUtils.aesEncrypt(jsonObject.toJSONString(), picParam.get(AppDitDicConstant.CMP_cmpAesKey));
			showContentUrl = sysUrl + showContentPath + decrypt;
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null) + "影像调阅--参数加密失败~~~" + e);
			throw new ProcessException("影像调阅--参数加密失败~~~");
		}
		return showContentUrl;
	}
}