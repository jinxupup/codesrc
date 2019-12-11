package com.jjb.cas.quartz;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.cas.biz.rule.utils.OnlineMakeCardSupport;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.AbnormalProcessAppService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;

/**
 * @author JYData-R&D-HN
 * @version V1.0
 * @Description: 异常件处理
 * @date 2016年9月23日 下午3:56:12
 */
@Component
public class AutoAbnQuartz implements Serializable {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private OnlineMakeCardSupport makeCardSupport;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private AbnormalProcessAppService abnormalProcessAppService;
	@Autowired
	/**
	 * 1.修改状态异常的申请件的状态</br>
	 * 2.主要修改核心系统已经成功建账制卡，然后而审批系统状态非P05的申请件</br>
	 * 3.根据核心返还的卡表(tmmircard)关联申请件主表，且审批件主表中申请状态非P05的申请件，
	 * 	均改成P05，并且删除工作流数据 4.记录审批历史记录信息
	 */
	public void abnApplyChangeRtfState() {
		long start = System.currentTimeMillis();
		// 设置系统机构号
		appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
		logger.info("PID-[" + start + "]开始处理制卡成功状态错误的申请清单");
		// TODO 使用卡表与申请主表结合查询出异常件列表
		List<TmAppMain> exList=abnormalProcessAppService.getTmAppMainMkCarfEx();
		if (CollectionUtils.isEmpty(exList)) {
			logger.info("PID-[" + start + "]系统不存在待处理的申请件");
			return;
		}
		logger.info("PID-[" + start + "]制卡成功状态错误数量 : " + exList.size());
		// TODO 遍历异常件清单
		for (int i = 0; i < exList.size(); i++) {
			TmAppMain main = exList.get(i);
			String appNo=main.getAppNo();
			try {
				// TODO 修改申请件状态
				main.setRtfState(RtfState.P05.name());
				main.setRemark("[系统备注]修改状态异常的申请件的状态为P05;"+main.getRemark());
				applyInputService.updateTmAppMain(main);
				// TODO 新增审批历史记录
				TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo, "", RtfState.P05, "", "异常件成功自动处理");
				if(tmAppHistory != null){
					if(main != null){
						tmAppHistory.setName(main.getName());
						tmAppHistory.setIdNo(main.getIdNo());
						tmAppHistory.setIdType(main.getIdType());
					}
					applyInputService.saveTmAppHistory(tmAppHistory);
				}
				// TODO 删除工作流数据
				try {
					abnormalProcessAppService.delete(appNo);
				} catch (Exception e) {
					// TODO: 删除工作流数据失败无需终端后续处理，记录错误日志即可
					logger.error("["+appNo+"]"+"删除工作流失败"+e.getMessage());
				}
			} catch (Exception e) {
				// TODO: 单笔处理失败，记录日志即可，无需中断后续处理，记录错误日志即可
				logger.error("["+appNo+"]"+"异常件单笔处理失败"+e.getMessage());
				//TODO 记录历史信息
				RtfState rtf = AppCommonUtil.stringToEnum(RtfState.class, main.getRtfState(), RtfState.P05);
				TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo, "", rtf, "", "异常件自动处理异常");
				if(tmAppHistory != null){
					if(main != null){
						tmAppHistory.setName(main.getName());
						tmAppHistory.setIdNo(main.getIdNo());
						tmAppHistory.setIdType(main.getIdType());
					}
					applyInputService.saveTmAppHistory(tmAppHistory);
				}
			}
		}
		logger.info("PID-[" + start + "]结束处理制卡成功状态错误的申请清单");
	}




	/**
	 * 针对制卡异常的申请件超过两个批量日进行再次发起制卡交易
	 */
	public void abnApplyOnlineMarkCard() {
		long start = System.currentTimeMillis();
		// 设置系统机构号
		appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
		logger.info("PID-[" + start + "]开始处理核心制卡失败申请清单");
		// TODO 查询申请件状态为L05、N05、Q05的申请件且核心的卡帐客返还数据也没有的申请件清单
		List<TmAppMain> list = abnormalProcessAppService.getTmAppMianMkCardAgain();
		if (CollectionUtils.isEmpty(list)) {
			logger.info("PID-[" + start + "]系统不存在待处理的申请件");
			return;
		}
		logger.info("PID-[" + start + "]制卡失败待数量 : " + list.size());
		// TODO 遍历异常件清单
		for (int i = 0; i < list.size(); i++) {
			TmAppMain main = list.get(i);
			try {
				//判断是否需要发起制卡
				Date newDate= new Date();
				Date date= main.getUpdateDate();
				int days=(int)(newDate.getTime()-(date.getTime())/(1000*3600*24));
				//查询申请件数据
				ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(main.getAppNo());
				try {
					if (days>=2) {
						//调用制卡
						makeCardSupport.onlineMakeCard(applyInfoDto);
					}
				} catch (Exception e) {
					// TODO: 记录错误日志，无作终端处理，记录制卡失败数据
					logger.error("["+main.getAppNo()+"]"+"调用制卡失败"+e.getMessage());
					throw new ProcessException("["+main.getAppNo()+"]"+"调用制卡失败");
				}

			} catch (Exception e) {
				// TODO: 单笔处理失败，记录日志即可，无需中断后续处理，记录错误日志即可
				logger.error("["+main.getAppNo()+"]"+"再次制卡失败"+e.getMessage());
				//TODO 记录历史信息
				RtfState rtf = AppCommonUtil.stringToEnum(RtfState.class, main.getRtfState(), RtfState.Q05);
				TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(main.getAppNo(), "", rtf, "", "系统再次发起制卡失败");
				if(tmAppHistory != null){
					if(main != null){
						tmAppHistory.setName(main.getName());
						tmAppHistory.setIdNo(main.getIdNo());
						tmAppHistory.setIdType(main.getIdType());
					}
					applyInputService.saveTmAppHistory(tmAppHistory);
				}
			}
		}
	}
}