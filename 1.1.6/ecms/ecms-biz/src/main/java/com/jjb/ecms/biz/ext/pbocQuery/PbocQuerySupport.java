package com.jjb.ecms.biz.ext.pbocQuery;

import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.util.HttpClientUtil;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: TODO  联机综合前置 中联惠捷征信查询
 * @Author: shiminghong
 * @Data: 2019/6/4 14:40
 * @Version 1.0
 */
@Component
public class PbocQuerySupport {
    private static Logger logger = LoggerFactory.getLogger(PbocQuerySupport.class);

    public String  pbocQuerySys(String servId,JSONObject reqJs, Map<String,String> ccifConf){
        long start = System.currentTimeMillis();
        if(reqJs==null){
            logger.warn(LogPrintUtils.printCommonStartLog(start,servId)+"服务编码["+servId+"]请求失败，请求参数为空");
            throw new ProcessException("联机["+servId+"]失败，请求参数为空");
        }
        String filePath="";
        String resp = "";
        try {
            logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"征信查询:{}",reqJs.toJSONString());
            HttpPost post = new HttpPost(ccifConf.get("200480000801pbocQueryUrl"));
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Connection", "close");
            RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(65000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(65000).build();
            post.setConfig(reqConfig);
            String reqJsToString=reqJs.toJSONString();
            StringEntity entity = new StringEntity(reqJs.toJSONString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            resp = HttpClientUtil.execute(post);
            JSONObject jsonObject = JSON.parseObject(resp);
            JSONObject js = jsonObject.getJSONObject("response");
            String resultInfo = js.getString("resultInfo");
            if (StringUtils.isNotEmpty(resultInfo)){
                JSONObject json = JSON.parseObject(resultInfo);
                filePath=json.getString("file_path");
            }else{
                throw new ProcessException("征信查询链接返回值为空");
            }
/*            String result = jsonObject1.getString("result");
             String url= RSAUtils.RSADecoder(result);
            logger.info(url)
            String rsaStr =  RSAUtils.RSADecoder(result);
            System.out.println(rsaStr);
            CreditInfo creditInfo= new CreditInfo();
            String a  = creditInfo.creditInfoTheParsing(rsaStr);
            System.out.println(a);
*/
        } catch (Exception e) {
            logger.error("联机交易["+servId+"]发生异常."+e.getMessage());
            throw new ProcessException("联机交易["+servId+"]发生异常."+e.getMessage());
        }finally{
            logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"，响应数据["+resp+"]");
        }
        return filePath;
    }
}
