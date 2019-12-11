package com.jjb.ecms.service.dto.T1013;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 *@ClassName T1013Resp
 *@Description 合伙人准入申请结果响应实体类
 *@Author 何嘉能
 *Version 1.0
 */
@Data
public class T1013Resp extends BasicResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String RiskResult;//风控准入结果; P：通过 ;R：拒绝
}
