package com.jjb.ecms.biz.ext.identify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;

/**
 * 行内身份核查接口
 * 
 * @Description:
 * @author hh
 * @date 2017年7月24日 下午2:58:04
 * @version V1.0
 */
@Component
public class IdentifyCheck {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private IdentifyCheckService identifyCheckService;
//	@Autowired
//	private TmCreditHistoryDao tmCreditHistoryDao;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyQueryService applyQueryService;
// 	
//	/**
//	 * 调用其他系统联网核查身份信息</br> 路由各个机构是否需要身份核身以及调用身份核身的渠道
//	 * 
//	 * @param req
//	 * @return
//	 * @throws ProcessException
//	 */
//	public IdentifyCheckResp checkForNetWork(String appNo)
//			throws ProcessException {
//		long start = System.currentTimeMillis();
//		IdentifyCheckResp resp = null;
//		IdentifyCheckReq req = new IdentifyCheckReq();
//		TmAppMain main = null;
//		try {
//			String org = OrganizationContextHolder.getOrg();
//			main = applyQueryService.getTmAppMainByAppNo(appNo);
//			if(main==null){
//				throw new ProcessException("["+appNo+"]信息不存在!");
//			}
//			TmDitDic onLinOffNciic = cacheContext
//					.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_isCallNciic);// 联机调用开关参数-是否开启调用-身份核身
//			// 是否开启调用内部身份核身
//			if (onLinOffNciic != null
//					&& StringUtils.isNotEmpty(onLinOffNciic.getRemark())
//					&& onLinOffNciic.getRemark().equals("Y")) {
//				// AppDitDicConstant.pic_PicShow //系统影像
//				IdentifyCheckReq idCheckReq = new IdentifyCheckReq();
//				identifyCheckService.getIndentifyCheck("", idCheckReq);
//			}else {
//				logger.error("该机构[" + org + "]未开启身份核身渠道");
//				throw new ProcessException("未开启身份核身渠道");
//			}
//		} catch (Exception e) {
//			logger.error("身份核身失败:", e);
//			throw new ProcessException(e.getMessage());
//		} finally {
//			logger.info("身份核身" + AppConstant.END + ",appNo-"
//					+ appNo + "处理完成 - 耗时:" + (System.currentTimeMillis() - start));
//			saveIdentifyCheck(req, resp, appNo);
//		}
//		return resp;
//	}
//	
//	/**
//	 * 保存核身历史记录
//	 * @param req
//	 * @param resp
//	 * @param main
//	 */
//	public void saveIdentifyCheck(IdentifyCheckReq req,
//			IdentifyCheckResp resp, String appNo) {
//		TmCreditHistory tmCreditHistory = new TmCreditHistory();
//		tmCreditHistory.setAppNo(appNo);
//		tmCreditHistory.setOpType(AppConstant.IDFY01);
//		tmCreditHistory.setIdType(IdType.I.name());//目前核身只支持身份证，其他证件不支持
//		if(req!=null){
//			tmCreditHistory.setBnsType(req.getBusiType());
//			tmCreditHistory.setOpUserNo(OrganizationContextHolder.getUserNo());//操作人
//			tmCreditHistory.setCustName(req.getCustName());
//			tmCreditHistory.setIdNo(req.getIdNo());
//			tmCreditHistory.setRequestMap(req.toString());
//		}
//		tmCreditHistory.setQueryTime(new Date());
//		if (resp != null) {
//			tmCreditHistory.setUrlStr(resp.getPhotoBase());
//			tmCreditHistory.setRetCode(resp.getCheckResult());
//			tmCreditHistory.setRetMsg(resp.getRspMsg());
//		}
//		
//		tmCreditHistoryDao.saveTmCreditHistory(tmCreditHistory);
//	}
}
