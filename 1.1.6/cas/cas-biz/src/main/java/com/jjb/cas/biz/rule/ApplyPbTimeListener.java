package com.jjb.cas.biz.rule;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jjb.ecms.constant.AppConstant;

/**
 * 补件超时监听
 * 
 * @author D.C
 * 
 */
@Component

@Service("applyPbTimeListener")
public class ApplyPbTimeListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.setVariable(AppConstant.APPLY_PB_STTIME, delegateTask.getVariable(AppConstant.APPLY_PB_STTIME)); // 补件开始日期
		delegateTask.setVariable(AppConstant.APPLY_PB_TIMEWAIT, delegateTask.getVariable(AppConstant.APPLY_PB_TIMEWAIT)); // 补件等待天数
	}
}