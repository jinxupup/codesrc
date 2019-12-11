package com.jjb.cas.biz.pbocQuery;

import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.pbocQuery.PbocQuerySupport;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;
//import com.jjccb.uniqueid.GUIDHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Description: TODO 中联惠捷征信查询接口
 * @Author: shiminghong
 * @Data: 2019/6/4 11:47
 * @Version 1.0
 */
@Component
public class PbocQueryService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PbocQuerySupport pbocQuerySupport;
    @Autowired
    private CacheContext cacheContext;
    /**
     * 中联惠捷征信查询接口
     */
    public String pbocQuery(String name,String idNo){

        if (StringUtils.isBlank(name)||StringUtils.isBlank(idNo)){
            throw new ProcessException("请填写完整的请求参数");
        }
            JSONObject reqJs = new JSONObject();
            JSONObject head = new JSONObject();
            JSONObject request = new JSONObject();
            String txDate="";
                //格式化时间  yyyyMMdd
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                try {
                    txDate = simpleDateFormat.format(date);
                } catch (Exception e) {
                    throw new ProcessException("格式化时间转换失败");
                }
                //生成交易流水号UUID 32 位
//                String transSeq = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                /**
                 *查询相应的网络配置
                 */
                Map<String, String> ccifConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_ccif_conf);
                if (ccifConf == null || ccifConf.size() == 0) {
                    throw new ProcessException("未查询到中联惠捷征信查询接口的系统网络配置，调用失败！");
                }
                head.put("txCode", ccifConf.get("200480000801txCode"));
                head.put("txDate", txDate);
                head.put("consumerId", ccifConf.get("200480000801consumerId"));
//                head.put("transSeq", GUIDHelperelper.getGUID(ccifConf.get("200480000801consumerId")));

                request.put("idCard", idNo);
                request.put("name", name);

                reqJs.put("head", head);
                reqJs.put("request", request);

                String filePath = pbocQuerySupport.pbocQuerySys(ccifConf.get("200480000801txCode"), reqJs, ccifConf);
                if (StringUtils.isBlank(filePath)){
                    throw new ProcessException("联机异常，返回结果为空");
                }
                return  filePath;
    }

}
