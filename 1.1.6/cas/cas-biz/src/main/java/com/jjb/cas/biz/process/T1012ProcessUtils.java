package com.jjb.cas.biz.process;


import com.jjb.ecms.biz.service.scale.TmLargeScaleStagingService;
import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.ecms.service.dto.T1012.T1012Req;
import com.jjb.ecms.service.dto.T1012.T1012Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * 大额分期准入查询
 * @author wxl
 *
 */
@Component
public class T1012ProcessUtils {
	private Logger logger = LoggerFactory.getLogger(T1012ProcessUtils.class);


	@Autowired
	private TmLargeScaleStagingService tmLargeScaleStagingService;


	public T1012Resp executeT1012(T1012Req req) throws ProcessException {

		T1012Resp t1012Resp = new T1012Resp();
		String idNo = req.getIdNo();
		String idType = req.getIdType();
		String name = req.getName();
		if (StringUtils.isNotEmpty(idNo) | StringUtils.isNotEmpty(idType) | StringUtils.isNotEmpty(name)) {
			//不为空
			TmLargeScaleStaging tmLargeScaleStaging = new TmLargeScaleStaging();
			tmLargeScaleStaging.setIdNo(idNo);
			tmLargeScaleStaging.setIdType(idType);
			tmLargeScaleStaging.setName(name);
			List<TmLargeScaleStaging> tmLargeScaleStagings = null;
			try {
				tmLargeScaleStagings = tmLargeScaleStagingService.selectTmLargeScaleStagingService(tmLargeScaleStaging);
			} catch (Exception e) {
				logger.error("调用[ProcessT1012]接口服务 查询数据库失败!");
				throw new ProcessException("调用[ProcessT1012]接口服务 查询数据库失败!"+e.getMessage());
			}
			//取得最新的一条
			if (CollectionUtils.isNotEmpty(tmLargeScaleStagings)) {
				tmLargeScaleStaging = tmLargeScaleStagings.get(0);
				Date date = tmLargeScaleStaging.getCreateDate();
				for (TmLargeScaleStaging largeScaleStaging : tmLargeScaleStagings) {
					Date createDate = largeScaleStaging.getCreateDate();
					if (date.getTime() < createDate.getTime()) {
						date = createDate;
						tmLargeScaleStaging = largeScaleStaging;
					}
				}
			}else {
				logger.info("调用[ProcessT1012]接口服务 查询结果为空 ! 返回空值");
			}
			//负值给T1012Resp
			if (tmLargeScaleStaging != null) {
				t1012Resp.setName(tmLargeScaleStaging.getName());
				t1012Resp.setIdType(tmLargeScaleStaging.getIdType());
				t1012Resp.setIdNo(tmLargeScaleStaging.getIdNo());
				t1012Resp.setCellphone(tmLargeScaleStaging.getCellphone());
				t1012Resp.setAppProducts(tmLargeScaleStaging.getAppProducts());
				t1012Resp.setAppAmount(tmLargeScaleStaging.getAppAmount());
				t1012Resp.setCompanyName(tmLargeScaleStaging.getCompanyName());
				t1012Resp.setMaritalStatus(tmLargeScaleStaging.getMaritalStatus());
				t1012Resp.setPolicyResult(tmLargeScaleStaging.getPolicyResult());
				t1012Resp.setRuleList(tmLargeScaleStaging.getRuleList());
				t1012Resp.setRefuseCode(tmLargeScaleStaging.getRefuseCode());
				t1012Resp.setImageNum(tmLargeScaleStaging.getImageNum());
				t1012Resp.setWeCode(tmLargeScaleStaging.getWeCode());
				t1012Resp.setPptyProvince(tmLargeScaleStaging.getPptyProvince());
				t1012Resp.setPptyCity(tmLargeScaleStaging.getPptyCity());
				t1012Resp.setPptyArea(tmLargeScaleStaging.getPptyArea());
				t1012Resp.setPptyAreaCode(tmLargeScaleStaging.getPptyAreaCode());
				logger.info("调用[ProcessT1012]接口服务 Resp 返回结果成功");
				return t1012Resp;
			} else {
				logger.error("调用[ProcessT1012]接口服务 Resp 返回结果为空");
				throw new ProcessException("调用[ProcessT1012]接口服务 Resp 返回结果为空");
			}
		} else {
			logger.error("调用[ProcessT1012]接口服务 req 字段出现空值" + req.toString());
			throw new ProcessException("调用[ProcessT1012]接口服务 req 字段出现空值" + req.toString());
		}
	}


	/**
	 * T1012交易请求参数验证
	 * @param req
	 */
	public void checkT1012Req(T1012Req req) {
		if (req == null) {
			throw new ProcessException("T1012-请求参数不能为空");
		}
		if (StringUtils.isEmpty(req.getName())) {
			throw new ProcessException("T1012-请求参数-[客户姓名]不能为空");
		}
		if (StringUtils.isEmpty(req.getIdNo())) {
			throw new ProcessException("T1012-请求参数-[证件号码]不能为空");
		}
		if (StringUtils.isEmpty(req.getIdType())) {
			throw new ProcessException("T1012-请求参数-[证件类型]不能为空");
		}
	}

}
