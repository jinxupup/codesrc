package com.jjb.cas.service.impl;


import com.alibaba.fastjson.JSON;
import com.jjb.cas.biz.process.T1005ProcessUtils;
import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.service.api.ApplyProductsService;

import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @Description: 外部风控系统交互
 * @author JYData-R&D-HN
 * @date 2018年4月6日 下午7:52:35
 * @version V1.0
 */
@Service("applyProductsServiceImpl")
public class ApplyproductsServiceImpl implements ApplyProductsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TmAppOrderMainDao tmAppOrderMainDao;

	@Autowired
	private T1005ProcessUtils t1005Process;
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	public void applyExecute(String param,long start) throws Exception{
		List<TmAppOrderMain> tmAppOrderMainList = tmAppOrderMainDao.getTmAppOrderMainByTimerState("P");
		for (TmAppOrderMain tmAppOrderMain : tmAppOrderMainList) {
			if (tmAppOrderMain.getValidProductType().contains("0")) {
				// 待处理类
				char[] productTypes = tmAppOrderMain.getValidProductType().toCharArray();
				String[] productCds = tmAppOrderMain.getValidProductCds().split(",");
				for (int i = 0; i < productTypes.length; i++) {
					if (productTypes[i] == '0') {
						try {
							T1005Req req = JSON.parseObject(tmAppOrderMain.getReqJson(), T1005Req.class);
							if(req!=null) {
								String productCd = productCds[i];
								if(req.getCusts()!=null && req.getCusts().get(0)!=null) {
									String idType= req.getCusts().get(0).getIdType();
									String idNo= req.getCusts().get(0).getIdType();
									String name= req.getCusts().get(0).getIdType();
									String existProduct = t1005Process.isExistProduct(start+"",idType, idNo, name, productCd);
									if (StringUtils.equals(existProduct, "0")) {
										req.setProductCd(productCds[i]);
										req.setTaskNum(tmAppOrderMain.getAppNo());
										t1005Process.execute(req);
										// 等待1秒避免并发
										Thread.sleep(1000L);
									}
								}else {
									productTypes[i] = '2';//重复申请
								}
							}
						} catch (Exception e) {
							logger.error("多卡同申异常"+codeMarkUtils.makeIDCardMask(tmAppOrderMain.getIdNo()), e);
							productTypes[i] = '8';//异常
							tmAppOrderMain.setValidProductType(new String(productTypes));
							tmAppOrderMain.setExceptionMsg(tmAppOrderMain.getExceptionMsg() + e.getMessage() + ",");
							tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
							continue;
						}
						productTypes[i] = '1';//成功处理
						tmAppOrderMain.setValidProductType(new String(productTypes));
						tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
					}
				}
			} else {
				// 处理完毕，修改状态成功
				tmAppOrderMain.setTimerState("S");//正条记录所有卡处理完成
				tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
			}
		}

	}
}
