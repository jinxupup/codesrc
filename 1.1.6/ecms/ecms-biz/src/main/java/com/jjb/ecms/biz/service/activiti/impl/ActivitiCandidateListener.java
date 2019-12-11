package com.jjb.ecms.biz.service.activiti.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

/**
 * 根据当前执行的位置决定候选用户组
 * @author 
 *
 */
@Service("activitiCandidateListener")
public class ActivitiCandidateListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate.DelegateTask)
	 */
	@Override
	public void notify(DelegateTask arg0) {
		// TODO Auto-generated method stub
		
	}
	
}