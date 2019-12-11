package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclAuthDetail;
import com.jjb.acl.infrastructure.TmAclRole;


/**
 * 
 * @ClassName AuthAuditDetailService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author H.N
 * @Date 2017年12月2日 下午3:03:22
 * @version 1.0.0
 */
public interface AuthAuditDetailService {

	void saveTmAuthAuditDetail(int logId, TmAclRole curTmAclRole,
			String sysType, String resource);

	List<TmAclAuthDetail> getTmAclAuthDetailByLogId(Integer logId);

}
