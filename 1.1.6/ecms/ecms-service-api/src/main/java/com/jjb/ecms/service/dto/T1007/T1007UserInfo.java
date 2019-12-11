package com.jjb.ecms.service.dto.T1007;

import java.io.Serializable;

import lombok.Data;

/**
 * 推广人(系统用户)信息
 * 
 * @author hp
 *
 */
@Data
public class T1007UserInfo implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;
	public String userNo;// 用户工号
	public String userNmae;// 用户姓名
	public String phoneNo;// 用户电话
	public String supBranch;// 上级网点
	public String branch;// 所属分支行
	public String userState;//推广人状态
	public String userType;//推广人类型
		
}
