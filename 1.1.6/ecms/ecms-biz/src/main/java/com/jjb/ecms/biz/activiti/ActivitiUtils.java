package com.jjb.ecms.biz.activiti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.unicorn.facility.exception.ActivitiException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 工作流工具类
 * 
 * @author hp
 *
 */
@Component
public class ActivitiUtils {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ActivitiService activitiService;

	/**
	 *  根据产品代码与申请渠道获取默认工作流配置
	 * @param appNo
	 * @param start
	 * @param productCd
	 * @param appSource
	 * @param servId
	 * @return
	 */
	public String getTaskProcessKeyByProductAndAppSource(String appNo, Long start, String productCd, 
			String appSource,String servId) {
		String procdefKey = "";
		if (StringUtils.isNotBlank(productCd)) {
			String key = productCd + "-" + appSource;// 每个产品+申请渠道
			String defKey = productCd + "-def";// 每个产品默认的流程图
			if (StringUtils.isEmpty(appSource)) {
				key = productCd + "-def";
			}
			logger.info("开始查询产品[" + productCd + "]与申请渠道[" + appSource + "]配置的流程图"
					+ LogPrintUtils.printAppNoLog(appNo, start, servId));
			TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
			if (proProcess == null || StringUtils.isEmpty(proProcess.getProcdefKey())) {
				logger.info("系统未配置产品[" + productCd + "]与申请渠道[" + appSource + "]配置的流程图，故查询产品默认流程图，KEY:" + defKey);
				proProcess = cacheContext.getProductProcessByProduct(defKey);
				if (proProcess != null && StringUtils.isNotEmpty(proProcess.getProcdefKey())) {
					procdefKey = proProcess.getProcdefKey();
				}
			} else {
				procdefKey = proProcess.getProcdefKey();
			}
			if (proProcess == null || StringUtils.isEmpty(proProcess.getProcdefKey())) {
				procdefKey = activitiService.getDefProcess();// 系统设置总的默认流程图
				logger.info("系统未配置产品[" + productCd + "]与申请渠道[" + appSource + "]配置的流程图，故查询系统默认流程图，KEY:" + procdefKey);
			}
			logger.info("已查询到产品[" + productCd + "]与申请渠道[" + appSource + "]配置的流程图:" + procdefKey);
		} else {
			// 设置默认流程
			procdefKey = activitiService.getDefProcess();
		}
		return procdefKey;
	}
	
	/**
	 * 根据产品+申请渠道获取业务条线1
	 * @param appNo
	 * @param start
	 * @param productCd
	 * @param appSource
	 * @param servId
	 * @return
	 */
	public String getRiskProCd1ByProductAndAppSource(String appNo, Long start, String productCd, 
			String appSource,String servId) throws Exception {
		String riskPro = "";
		if(StringUtils.isNotBlank(productCd)){
			String key = productCd+"-"+appSource;//每个产品+申请渠道
			String defKey = productCd+"-def";//每个产品默认的流程图
			if(StringUtils.isEmpty(appSource)) {
				key = productCd+"-def";
			}
			logger.info("开始查询产品["+productCd+"]与申请渠道["+appSource+"]配置的决策产品"+LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
			TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
			if(proProcess==null){
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]配置的流程图，故查询产品默认流程图，KEY:"+defKey);
				proProcess=cacheContext.getProductProcessByProduct(defKey);
			}
			String riskPro1 = proProcess.getRiskproduct1();
			riskPro = riskPro1;//默认赋第一个风控业务条线
			String riskPro3 = proProcess.getRiskproduct3();//A、B-test的时候使用风控业务条线3
			//如果AppSource等于01,且申请件编号和业务条线3等均不为空，则需要AB-test
			if(StringUtils.equals(appSource, "01") 
					&& StringUtils.isNotEmpty(riskPro3)
					&& StringUtils.isNotEmpty(appNo)) {
				String lastNum = StringUtils.valueOf(appNo.charAt(appNo.length()-1));
				Integer lastnum_ = StringUtils.stringToIntegerNotNull(lastNum);
				// lastNum 是 0-4 则走风控业务条线1 ；lastNum 是 5-9 则走风控业务条线1
				if(lastnum_>4) {
					riskPro = riskPro3;
				}
			}
			if(StringUtils.isEmpty(riskPro)) {
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]的风控业务条线");
				throw new ActivitiException("系统未配置有效的风险决策产品跳线信息，调用失败，请联系管理员！");
			}
			logger.info("已查询到产品["+productCd+"]与申请渠道["+appSource+"]对应的风控业务条线:"+riskPro);
		}
		return riskPro;
	}

	/**
	 * 根据产品+申请渠道获取业务条线2
	 * @param appNo
	 * @param start
	 * @param productCd
	 * @param appSource
	 * @param servId
	 * @return
	 */
	public String getRiskProCd2ByProductAndAppSource(String appNo, Long start, String productCd, 
			String appSource,String servId) throws Exception {
		String riskPro = "";
		if(StringUtils.isNotBlank(productCd)){
			String key = productCd+"-"+appSource;//每个产品+申请渠道
			String defKey = productCd+"-def";//每个产品默认的流程图
			if(StringUtils.isEmpty(appSource)) {
				key = productCd+"-def";
			}
			logger.info("开始查询产品["+productCd+"]与申请渠道["+appSource+"]配置的决策产品"+LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
			TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
			if(proProcess==null){
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]配置的流程图，故查询产品默认流程图，KEY:"+defKey);
				proProcess=cacheContext.getProductProcessByProduct(defKey);
			}
			riskPro = proProcess.getRiskproduct2();
			if(StringUtils.isEmpty(riskPro)) {
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]的风控业务条线");
				throw new ActivitiException("系统未配置有效的风险决策产品跳线信息，调用失败，请联系管理员！");
			}
			logger.info("已查询到产品["+productCd+"]与申请渠道["+appSource+"]对应的风控业务条线:"+riskPro);
		}
		return riskPro;
	}

	/**
	 * 根据产品+申请渠道获取业务条线3
	 * @param appNo
	 * @param start
	 * @param productCd
	 * @param appSource
	 * @param servId
	 * @return
	 */
	public String getRiskProCd3ByProductAndAppSource(String appNo, Long start, String productCd, 
			String appSource,String servId) throws Exception {
		String riskPro = "";
		if(StringUtils.isNotBlank(productCd)){
			String key = productCd+"-"+appSource;//每个产品+申请渠道
			String defKey = productCd+"-def";//每个产品默认的流程图
			if(StringUtils.isEmpty(appSource)) {
				key = productCd+"-def";
			}
			logger.info("开始查询产品["+productCd+"]与申请渠道["+appSource+"]配置的决策产品"+LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
			TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
			if(proProcess==null){
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]配置的流程图，故查询产品默认流程图，KEY:"+defKey);
				proProcess=cacheContext.getProductProcessByProduct(defKey);
			}
			riskPro = proProcess.getRiskproduct3();
			if(StringUtils.isEmpty(riskPro)) {
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]的风控业务条线");
				throw new ActivitiException("系统未配置有效的风险决策产品跳线信息，调用失败，请联系管理员！");
			}
			logger.info("已查询到产品["+productCd+"]与申请渠道["+appSource+"]对应的风控业务条线:"+riskPro);
		}
		return riskPro;
	}
	/**
	 * 根据产品+申请渠道获取业务条线4
	 * @param appNo
	 * @param start
	 * @param productCd
	 * @param appSource
	 * @param servId
	 * @return
	 */
	public String getRiskProCd4ByProductAndAppSource(String appNo, Long start, String productCd, 
			String appSource,String servId) throws Exception {
		String riskPro = "";
		if(StringUtils.isNotBlank(productCd)){
			String key = productCd+"-"+appSource;//每个产品+申请渠道
			String defKey = productCd+"-def";//每个产品默认的流程图
			if(StringUtils.isEmpty(appSource)) {
				key = productCd+"-def";
			}
			logger.info("开始查询产品["+productCd+"]与申请渠道["+appSource+"]配置的决策产品"+LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
			TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
			if(proProcess==null){
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]配置的流程图，故查询产品默认流程图，KEY:"+defKey);
				proProcess=cacheContext.getProductProcessByProduct(defKey);
			}
			riskPro = proProcess.getRiskproduct4();
			if(StringUtils.isEmpty(riskPro)) {
				logger.info("系统未配置产品["+productCd+"]与申请渠道["+appSource+"]的风控业务条线");
				throw new ActivitiException("系统未配置有效的风险决策产品跳线信息，调用失败，请联系管理员！");
			}
			logger.info("已查询到产品["+productCd+"]与申请渠道["+appSource+"]对应的风控业务条线:"+riskPro);
		}
		return riskPro;
	}
}
