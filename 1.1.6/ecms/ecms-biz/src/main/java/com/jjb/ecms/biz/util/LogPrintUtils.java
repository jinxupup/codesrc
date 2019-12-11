/**
 * 
 */
package com.jjb.ecms.biz.util;

import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 日志公共类
 * @author JYData-R&D-HN
 * @date 2016年9月23日 下午3:39:15
 * @version V1.0  
 */
public class LogPrintUtils {
	
	/**
	 * 申请件编号为主的简单日志打印模版-开始日志
	 * @param appNo 申请件编号或业务流水号
	 * @param tokenId = System.currentTimeMillis();
	 * @param servId 交易服务代码
	 * @return
	 */
	public static String printAppNoLog(String appNo,Object tokenId,String servId){
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotEmpty(appNo)){
			sb.append(".[AppNo-");
			sb.append(appNo);
			sb.append("],");
		}
		sb.append(printCommonStartLog(tokenId,servId));
		return sb.toString();
	}
	/**
	 * 申请件编号为主的简单日志打印模版-结尾日志，有耗时时间计算
	 * @param appNo 申请件编号或业务流水号
	 * @param tokenId = System.currentTimeMillis();
	 * @param servId 交易服务代码
	 * @return
	 */
	public static String printAppNoEndLog(String appNo,Object tokenId,String servId){
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotEmpty(appNo)){
			sb.append(".[AppNo-");
			sb.append(appNo);
			sb.append("],");
		}
		sb.append(printCommonEndLog(tokenId,servId));
		return sb.toString();
	}
	/**
	 * 公共日志打印-开始
	 * @param tokenId = System.currentTimeMillis();
	 * @param servId 交易服务代码
	 * @return
	 */
	public static String printCommonStartLog(Object tokenId,String servId){
		StringBuffer sb = new StringBuffer();
//		sb.append("[org-");
//		sb.append(OrganizationContextHolder.getOrg());
//		sb.append("].");
		if(tokenId!=null){
			sb.append("[PID-");
			sb.append(tokenId+"");
			sb.append("].");
		}
		if(StringUtils.isNotEmpty(servId)){
			sb.append("[服务类型-");
			sb.append(servId+"");
			sb.append("].");
		}
		sb.append("[userId-");
		sb.append(OrganizationContextHolder.getUserNo());
		sb.append("].");
		return sb.toString();
	}
	/**
	 * 公共日志打印-结束
	 * @param tokenId = System.currentTimeMillis();
	 * @param servId 交易服务代码
	 * @return
	 */
	public static String printCommonEndLog(Object tokenId,String servId){
		StringBuffer sb = new StringBuffer();
//		sb.append("[org-");
//		sb.append(OrganizationContextHolder.getOrg());
//		sb.append("].");
		sb.append(printCommonStartLog(tokenId, servId));
		if(tokenId!=null){
			Long tokId = new Long(0);
			try {
				tokId = Long.valueOf(StringUtils.stringTolong(tokenId));
			} catch (Exception e) {
			}
			sb.append("[耗时-");
			sb.append(System.currentTimeMillis() - tokId+"毫秒");
			sb.append("].");
		}
		return sb.toString();
	}
}
