package com.jjb.acl.access.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jjb.acl.access.facade.UserInfoHolder;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.base.service.BaseService;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;

/**
 * 服务
 * @author LSW
 *
 */
@Service
public class AuthenticateService extends BaseService<TmAclUser> {
	
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public TmAclUser queryUser (String orgId,String loginName) {
		TmAclUser user = new TmAclUser ();
		
		user.setOrg(orgId);
		user.setUserNo(loginName);
		List<TmAclUser> userList = super.queryForList("acl.access.TmAclUser.selectByOrgAndUserNo", user);
		
		if (userList == null || userList.size()  != 1) {
			return null;
		}
		
		return userList.get(0);
	}
	
	public void editPassword(String oldPassword,String newPassword){
		
		TmAclUser tmAclUser = queryUser(OrganizationContextHolder.getOrg(), OrganizationContextHolder.getUserNo());
		
		if(!UserInfoHolder.validatePassword(oldPassword, tmAclUser.getPassword())){
			throw new BizException("原密码错误");
		}
		
		tmAclUser.setPassword(UserInfoHolder.entryptPassword(newPassword));
		tmAclUser.setTextObject6(df.format(new Date()));//modify by H.N 20171127 密码最新一次更新的时间
		super.update(tmAclUser);
	}
	
	// 更新用户状态
	public void updateStatus(String status) {
		TmAclUser tmAclUser = queryUser(OrganizationContextHolder.getOrg(), OrganizationContextHolder.getUserNo());
		tmAclUser.setStatus(status);
		super.update(tmAclUser);
	}

}
