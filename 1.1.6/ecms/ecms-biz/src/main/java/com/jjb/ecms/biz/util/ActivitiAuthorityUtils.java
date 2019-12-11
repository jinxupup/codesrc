package com.jjb.ecms.biz.util;


/**
 * @Description: 目前就用于封装一个工作流usertask与权限字符串的对应算法 
 * 	从老的bmp 中拷贝过来的 ，你懂的
 * @author JYData-R&D-HN
 * @date 2016年9月23日 下午3:59:22
 * @version V1.0
 */
public abstract class ActivitiAuthorityUtils {

	/**
 	 * 封装一个工作流usertask与权限字符串的对应算法
	 * @param processKey
	 * @param taskKey
	 * @return
	 */
	public static String getAuthorityByProcessTaskKey(String processKey, String taskKey)
	{
		return "activiti/" + processKey + "/" + taskKey; 
	}
	
	public static String getAuthorityOfProcessStart(String processKey)
	{
	    return "activiti/" + processKey + "/_start";
	}

	public static String getAuthorityOfProcessAssign(String processKey)
	{
		return "activiti/" + processKey + "/_assign";
	}

	public static String getAuthorityOfProcessAdmin(String processKey)
	{
		return "activiti/" + processKey + "/_admin";
	}
	
	public static String getCandidateGroupByRoleId(Integer roleId)
	{
		return "ROLE-" + roleId;
	}
}
