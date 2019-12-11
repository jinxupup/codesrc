package com.jjb.cas.biz.process;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.risk.CellBjjRiskSysSupport;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.biz.service.scale.TmLargeScaleStagingService;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.ecms.service.dto.T1011.T1011Req;
import com.jjb.ecms.service.dto.T1011.T1011Resp;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 大额分期准入申请
 *
 * @author wxl
 */
@Component
public class T1011ProcessUtils {
    private Logger logger = LoggerFactory.getLogger(T1011ProcessUtils.class);
    public static final String StrL = "L";
    @Autowired
    private CellBjjRiskSysSupport cellBjjRiskSupport;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private TmLargeScaleStagingService tmLargeScaleStagingService;
	@Autowired
	private TmRiskListService tmRiskListService;

    @Transactional
    public T1011Resp executeT1011(T1011Req req) throws ProcessException {

        T1011Resp t1011Resp = new T1011Resp();
        t1011Resp.setResult(false);
        JSONObject resJs = null;
        if (req == null) {
            logger.error("调用[ProcessT1011]接口服务 req 为空");
            throw new ProcessException("调用[ProcessT1011]接口服务 req 为空");
        }
        String name = req.getName();
        String appNo = req.getAppNo();
        String idType = req.getIdType();
        String idNo = req.getIdNo();
        String cellphone = req.getCellphone();
        String maritalStatus = req.getMaritalStatus();
        String appProducts = req.getAppProducts();
        BigDecimal appAmount = req.getAppAmount();
        String companyName = req.getCompanyName();
        String firstContactName = req.getFirstContactName();
        String firstContactPhone = req.getFirstContactPhone();
        String imageNum = req.getImageNum();
        String weCode = req.getWeCode();
        String pptyProvince = req.getPptyProvince();
        String pptyCity = req.getPptyCity();
        String pptyArea = req.getPptyArea();
        String pptyAreaCode = req.getPptyAreaCode();
        String channelType = req.getChannelType();
        TmLargeScaleStaging tmLargeScaleStaging = new TmLargeScaleStaging();
        if (StringUtils.isEmpty(name) | StringUtils.isEmpty(idType) | StringUtils.isEmpty(idNo) | StringUtils.isEmpty(cellphone)) {
            logger.error("调用[ProcessT1011]接口服务 req 字段出现空值" + req.toString());
            throw new ProcessException("调用[ProcessT1011]接口服务四要素有空值，调用失败");
        } else {

            tmLargeScaleStaging.setName(name);
            tmLargeScaleStaging.setIdType(idType);
            tmLargeScaleStaging.setIdNo(idNo);
            tmLargeScaleStaging.setCellphone(cellphone);
            tmLargeScaleStaging.setMaritalStatus(maritalStatus);
            tmLargeScaleStaging.setAppProducts(appProducts);
            tmLargeScaleStaging.setAppAmount(appAmount);
            tmLargeScaleStaging.setCompanyName(companyName);
            tmLargeScaleStaging.setFirstContactName(firstContactName);
            tmLargeScaleStaging.setFirstContactPhone(firstContactPhone);
            tmLargeScaleStaging.setImageNum(imageNum);
            tmLargeScaleStaging.setWeCode(weCode);
            tmLargeScaleStaging.setPptyProvince(pptyProvince);
            tmLargeScaleStaging.setPptyCity(pptyCity);
            tmLargeScaleStaging.setPptyArea(pptyArea);
            tmLargeScaleStaging.setPptyAreaCode(pptyAreaCode);
            tmLargeScaleStaging.setCreateDate(new Date());
            tmLargeScaleStaging.setJpaVersion(0);

                //车主贷保存结果,免掉风控
            if (StringUtils.equals(channelType, "C")) {
                //保存车主贷类型
                tmLargeScaleStaging.setChannelType(channelType);
            } else {
                //其他默认大额审批为L
                tmLargeScaleStaging.setChannelType(StrL);
                //大额审批调用决策
                //根据身份证号码获取年龄;
                String userAge = IdentificationCodeUtil.getAge(idNo);
                Map<String, String> bjjRiskConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_bjj_risk_conf);
                if (bjjRiskConf == null || bjjRiskConf.size() == 0) {
                    throw new ProcessException("未查询到风控系统网络配置，调用失败！");
                }
                JSONObject jsReq = new JSONObject();
                JSONObject custJs = new JSONObject();
			//准入调用风控决策时，先查询风险名单库，并将命中情况上送决策系统做相关决策
			String spreadPersonalRisk = "N";  //默认N(未名中)  身份信息风险名单情况
			String spreadCellPhoneRisk = "N";  //手机风险名单情况
			String spreadVipRisk = "N";            //白名单情况
			//身份信息风险名单情况以及白名单情况
			if (StringUtils.isNotBlank(req.getIdNo())) {
				List<TmRiskList> custSpreadRiskList = tmRiskListService.getTmRiskListInfo("", "", req.getIdNo());
				if (custSpreadRiskList.size() > 0) {
					for (TmRiskList spreadRiskList : custSpreadRiskList) {
						if (StringUtils.isNotBlank(spreadRiskList.getActType())) {
							if (!StringUtils.equals(spreadPersonalRisk, "B") && !StringUtils.equals(spreadRiskList.getActType(), "W")) {
								spreadPersonalRisk = spreadRiskList.getActType();
							}
							if (StringUtils.equals(spreadRiskList.getActType(), "W")) {
								spreadVipRisk = "V";
							}
						}
					}
				}
			}
			//手机风险名单情况
			if (StringUtils.isNotBlank(req.getName()) && StringUtils.isNotBlank(req.getCellphone())) {
				List<TmRiskList> spreadCellPhoneRiskList = tmRiskListService.getTmRiskListInfo(req.getName(), req.getCellphone(), "");
				if (spreadCellPhoneRiskList.size() > 0) {
					for (TmRiskList phoneRiskList : spreadCellPhoneRiskList) {
						if (StringUtils.isNotBlank(phoneRiskList.getActType())) {
							if (!StringUtils.equals(spreadCellPhoneRisk, "B") && !StringUtils.equals(phoneRiskList.getActType(), "W")) {
								spreadCellPhoneRisk = phoneRiskList.getActType();
							}
						}
					}
				}
			}
                custJs.put("spreadPersonalRisk", spreadPersonalRisk);
                custJs.put("spreadCellPhoneRisk", spreadCellPhoneRisk);
                custJs.put("spreadVipRisk", spreadVipRisk);
                custJs.put("app_no", appNo);
                custJs.put("name", name);
                custJs.put("idType", idType);
                custJs.put("id_card", idNo);
                custJs.put("phone_no", cellphone);
                custJs.put("MaritalStatus", maritalStatus);
                custJs.put("CardType", appProducts);
                custJs.put("appLmt", appAmount);
                custJs.put("companyName", companyName);
                custJs.put("firstContactName", firstContactName);
                custJs.put("firstContactMobile", firstContactPhone);
                custJs.put("age", userAge);
                custJs.put("home_state", pptyProvince);
                custJs.put("home_city", pptyCity);
                custJs.put("pptyArea", pptyArea);
                custJs.put("pptyAreaCode", pptyAreaCode);

                //查询业务条线
                String remark = bjjRiskConf.get("riskT1011ProCodeOC");
                if (StringUtils.isNotEmpty(remark)) {
                    jsReq.put("productCode", remark);
                } else {
                    logger.error("调用[ProcessT1011]接口服务 riskT1011ProCodeOC 查询异常 {" + remark + "}");
                    throw new ProcessException("调用[ProcessT1011]接口服务 riskT1011ProCodeOC 查询异常 {" + remark + "}");
                }
                jsReq.put("requestParam", custJs);
                try {
                    resJs = cellBjjRiskSupport.sendBjjRiskSys("decision", jsReq, bjjRiskConf);
                } catch (IOException e) {
                    logger.error("调用[ProcessT1011]接口服务 调用风控系统失败 ");
                    return t1011Resp;
                }
                if (resJs != null) {
                    JSONObject resoultJs = resJs.getJSONObject("decisionResult");
                    tmLargeScaleStaging.setPolicyResult(resoultJs.getString("result"));//决策结果
                    tmLargeScaleStaging.setRuleList(resoultJs.getJSONObject("resultMap").toJSONString());//人行规则命中结果清单
                    tmLargeScaleStaging.setRefuseCode(resJs.getString("reasonDesc"));//拒绝原因
                }
            }
            try {
                tmLargeScaleStagingService.saveTmLargeScaleStagingService(tmLargeScaleStaging);
                //返回成功结果
                t1011Resp.setResult(true);
                return t1011Resp;
            } catch (Exception e) {
                logger.error("调用[ProcessT1011]接口服务 调阅决策后保存异常");
                throw new ProcessException("调用[ProcessT1011]接口服务 调阅决策后保存异常 "+e.getMessage());
            }
        }
    }

    /**
	 * T1011交易请求参数验证
	 * @param req
	 */
	public void checkT1011Req(T1011Req req) {
		if (req == null) {
			throw new ProcessException("T1011-请求参数不能为空");
		}
		if (StringUtils.isEmpty(req.getName())) {
			throw new ProcessException("T1011-请求参数-[客户姓名]不能为空");
		}
		if (StringUtils.isEmpty(req.getIdNo())) {
			throw new ProcessException("T1011-请求参数-[证件号码]不能为空");
		}
		if (StringUtils.isEmpty(req.getCellphone())) {
			throw new ProcessException("T1011-请求参数-[手机号码]不能为空");
		}
        if (StringUtils.isEmpty(req.getIdType())) {
            throw new ProcessException("T1011-请求参数-[证件类型]不能为空");
        }
	}
}
