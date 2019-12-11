package com.jjb.ecms.service.dto.Trans0139;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 * 账户资产信息查询结果响应</br>
 * 1.法院被执行人信息查询
 * @author hejn
 *
 */
@Data
public class Trans0139Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public String jexExecut1Datetypeid103;//是否法院被执行人，0：非被执行人；2：被执行人
	public String jexExecut1StatuteClosed;//是否所有案件已结案，0：未结案；1：已结案
}
