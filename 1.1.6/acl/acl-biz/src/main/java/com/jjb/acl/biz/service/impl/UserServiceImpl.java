package com.jjb.acl.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.access.facade.UserInfoHolder;
import com.jjb.acl.biz.dao.TmAclBranchDao;
import com.jjb.acl.biz.dao.TmAclUserDao;
import com.jjb.acl.biz.dao.TmAclUserRoleDao;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.constant.Literal;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private TmAclUserDao tmAclUserDao;
	@Autowired
	private TmAclUserRoleDao tmAclUserRoleDao;
	@Autowired
	private TmAclBranchDao tmAclBranchDao;
	
	@Override
	public TmAclUser getUserByUserNo(String userNo){
		return tmAclUserDao.getUserByUserNo(userNo);
	}

	@Override
	public TmAclUser getUserByUserId(Integer userId) {
		TmAclUser tmAclUser = new TmAclUser();
		tmAclUser.setUserId(userId);
		tmAclUser.setOrg(OrganizationContextHolder.getOrg());
		return tmAclUserDao.queryByKey(tmAclUser);
	}
	
	@Override
	public Page<TmAclUser> getUserPage(Page<TmAclUser> page){
		return tmAclUserDao.queryPage(page);
	}
	
	@Override
	public TmAclUser getTmAclUser(int userId){
		
		TmAclUser tmAclUser = new TmAclUser();
		tmAclUser.setOrg(OrganizationContextHolder.getOrg());
		tmAclUser.setUserId(userId);
		return tmAclUserDao.queryByKey(tmAclUser);

	}

	@Override
	public Page<TmAclUser> getPage(Page<TmAclUser> page) {
		page = tmAclUserDao.queryPage(page);
		return page;
	}

	@Override
	public List<TmAclUser> selectByRoleId(int roleId){
		return tmAclUserDao.selectByRoleId(roleId);
	}
	
	/**
	 * 查询非此角色ID的用户列表：add by H.N 20171221
	 * @param tmAclUser
	 */
	@Override
	public Page<TmAclUser> selectWithOutRoleId(Page<TmAclUser> page, int roleId){
		page = tmAclUserDao.queryPageWithOutRoleI(page, roleId);
		return page;
	}
	
	@Override
	public void saveTmAclUser(TmAclUser tmAclUser) {
		TmAclUser tmAclUserDb = tmAclUserDao.getUserByUserNo(tmAclUser.getUserNo());
		if(tmAclUserDb!=null){
			throw new BizException("已存在此登陆名的用户");
		}
		
		tmAclUser.setPassword(entryptPassword(Literal.P88888888));
		tmAclUser.setOrg(OrganizationContextHolder.getOrg());  // 机构号必填
		
		//modify by H.N 20171127:密码过期字段
		tmAclUser.setTextObject6(df.format(new Date()));
		
		tmAclUserDao.save(tmAclUser);
	}

	@Override
	public void editTmAclUser(TmAclUser tmAclUser) {
		
		TmAclUser tmAclUserDb = tmAclUserDao.getUserByUserNo(tmAclUser.getUserNo());
		if(tmAclUserDb==null){
			throw new BizException("用户不存在");
		}
		
		//编辑用户信息，不修改密码:将注释内容恢复
		tmAclUser.setPassword(tmAclUserDb.getPassword());
		tmAclUser.setPasswordExpireDate(tmAclUserDb.getPasswordExpireDate());
		tmAclUser.setPasswordUpdate(tmAclUserDb.getPasswordUpdate());
		
		//不修改登录信息
		tmAclUser.setLoginIp(tmAclUserDb.getLoginIp());
		tmAclUser.setLoginState(tmAclUserDb.getLoginState());
		tmAclUser.setLoginTime(tmAclUserDb.getLoginTime());
		tmAclUser.setLogoutTime(tmAclUserDb.getLogoutTime());
		tmAclUser.setLogoutType(tmAclUserDb.getLogoutType());
		tmAclUser.setSessionId(tmAclUserDb.getSessionId());
		
		tmAclUser.setSalt(tmAclUserDb.getSalt());//密码盐
		tmAclUser.setStateUpdate(tmAclUserDb.getStateUpdate());//状态修改时间
		tmAclUser.setWorkStatusUpdate(tmAclUserDb.getWorkStatusUpdate());//工作状态修改
		tmAclUser.setWorkStatusUpdateEnd(tmAclUserDb.getWorkStatusUpdateEnd());//工作状态修改时间
		tmAclUser.setRetry(tmAclUserDb.getRetry());//密码错误次数
		tmAclUser.setTextObject1(tmAclUserDb.getTextObject1());//预留字段1~6
		tmAclUser.setTextObject2(tmAclUserDb.getTextObject2());
		tmAclUser.setTextObject3(tmAclUserDb.getTextObject3());
		tmAclUser.setTextObject4(tmAclUserDb.getTextObject4());
		tmAclUser.setTextObject5(tmAclUserDb.getTextObject5());
		
		//modify by H.N 20171127:密码过期字段
		tmAclUser.setTextObject6(df.format(new Date()));
//		tmAclUser.setTextObject6(tmAclUserDb.getTextObject6());//------------------------------------------预留做为密码过期判断依据
		
		//状态信息
		if(tmAclUserDb.getStatus()!=null&& (!tmAclUserDb.getStatus().equals(tmAclUser.getStatus()))
				|| tmAclUser.getStatus()!=null&& (!tmAclUser.getStatus().equals(tmAclUserDb.getStatus()))){
			tmAclUserDb.setStateUpdate(new Date());
		}
		tmAclUser.setJpaVersion(tmAclUserDb.getJpaVersion());
		BeanUtil.merge(tmAclUserDb, tmAclUser);
		tmAclUserDao.update(tmAclUserDb);
	}

	@Override
	public void deleteTmAclUser(int userId) {
		//删除用户角色关系--modify sy 20171202
		if(Integer.valueOf(userId) == null)
			throw new BizException("未选择用户！");
			
		tmAclUserRoleDao.deleteByUserId(userId);
		
		TmAclUser tmAclUser = new TmAclUser();
		tmAclUser.setUserId(userId);
		tmAclUser.setOrg(OrganizationContextHolder.getOrg());
		tmAclUserDao.deleteByKey(tmAclUser);
	}

	@Override
	public void deleteBatchTmAclUser(List<Integer> ids) {
		for(Integer id : ids){
			deleteTmAclUser(id);
		}
	}
	
	//验证密码
	@Override
	public boolean checkPassword(String userNo,String oldPassord){
		TmAclUser tmAclUserDb = tmAclUserDao.getUserByUserNo(userNo);
		if(tmAclUserDb==null){
			throw new BizException("用户不存在");
		}
		String password = entryptPassword(oldPassord);
		if(password.equals(tmAclUserDb.getPassword())){
			return true;
		}
		return false;
	}
	
	//修改密码
	@Override
	public void editPassword(String userNo,String plainPassword,String resetFlag){
		TmAclUser tmAclUserDb = tmAclUserDao.getUserByUserNo(userNo);
		if(tmAclUserDb==null){
			throw new BizException("用户不存在");
		}
		tmAclUserDb.setPassword(entryptPassword(plainPassword));
		//密码最近更新日期：add by H.N 20171113
		tmAclUserDb.setPasswordUpdate(new Date());
		
		//modify by H.N 20171127:密码过期字段
		tmAclUserDb.setTextObject6(df.format(new Date()));
		//重置密码后登陆需修改密码
		if("Y".equals(resetFlag)){
			tmAclUserDb.setStatus("A");
		}
		
		tmAclUserDao.update(tmAclUserDb);
	}
	
	//重置密码
	@Override
	public void resetPassword(String userNo){
		editPassword(userNo,Literal.P88888888,"Y");
	}
	
	//密码方法
	private String entryptPassword(String plainPassword){
		return UserInfoHolder.entryptPassword(plainPassword);
	}

	@Override
	public List<String> getUserResourceAuthTypeList(String userNo) {
		List<String> list = tmAclUserDao.getUserResourceAuthTypeByUserNo(userNo);
		return list;
	}
}
