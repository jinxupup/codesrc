package com.jjb.cas.quartz;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.sms.SendSmsSupport;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmAppMsgSendService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.util.MapBuilder;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @author JYData-R&D-HN
 * @version V1.0
 * @Description: 批量短信定时发送
 * @date 2016年9月23日 下午3:56:12
 */
@Component
public class AutoSmsSendQuartz implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    private TmAppMsgSendService tmAppMsgSendService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private SendSmsSupport sendSmsSupport;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public void messageSend() throws Exception {
        String condition="W";
        List<TmAppMsgSend> list= tmAppMsgSendService.getTmAppMsgSendListByCondition(condition);
        if(StringUtils.isNotEmpty(list)) {
            for (TmAppMsgSend tmAppMsgSend1 : list) {
                try {
                    //判断是否为待发送状态或发送失败的短信，防止重复发送
                    if (StringUtils.equals(tmAppMsgSend1.getCondition(), "W") || StringUtils.equals(tmAppMsgSend1.getCondition(), "F")) {
                        String appNo = tmAppMsgSend1.getAppNo();
                        String org = tmAppMsgSend1.getOrg();
                        TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
                        if (tmAppMain == null) {
                            tmAppMain = new TmAppMain();//预防空指针
                        }

                        String productCd = tmAppMain.getProductCd();//获取卡产品代码
                        String description = "";
                        TmProduct product = cacheContext.getProduct(productCd);
                        if (product != null) {
                            description = product.getProductDesc();
                        } else {
                            logger.info("未获取到卡产品代码[" + productCd + "]对应的卡产品！");
                        }
                        Map<String, Object> params = new MapBuilder<String, Object>().add("productCd", productCd).add("description", description).build();
                        String name = tmAppMain.getName();
                        String idNo = tmAppMain.getIdNo();
                        String cellPhone = tmAppMain.getCellphone();
                        String smsType=tmAppMsgSend1.getMsgType();
                        Gender gender = AppCommonUtil.getGender(appNo, null, idNo);
                        String msgContent=tmAppMsgSend1.getMsgContent();

                        params.put(AppConstant.name, name);
                        params.put(AppConstant.gender, gender);
                        params.put(AppConstant.cellPhone, cellPhone);
                        params.put(AppConstant.sms_type, smsType);
                        params.put(AppConstant.app_no, appNo);
                        params.put(AppConstant.org, org);
                        params.put(AppConstant.msgContent,msgContent);

                        sendSmsSupport.sendMessage(params, false,tmAppMsgSend1);

                        logger.info("申请编号[{}]，申请类型[{}]", tmAppMain.getAppNo(), tmAppMain.getAppType());
                    }
                } catch (Exception e) {
                    logger.error("短信发送出现异常，发送失败,", e);
                    tmAppMsgSend1.setCondition("F");
                    if (StringUtils.isBlank(tmAppMsgSend1.getMsgSendTimes())) {
                        tmAppMsgSend1.setMsgSendTimes("1");
                    } else {
                        int t = Integer.parseInt(tmAppMsgSend1.getMsgSendTimes()) + 1;
                        String s = String.valueOf(t);
                        tmAppMsgSend1.setMsgSendTimes(s);
                    }
                    tmAppMsgSend1.setRemark("短信发送出现异常");
                    tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend1);
                }
            }
        }
    }

}