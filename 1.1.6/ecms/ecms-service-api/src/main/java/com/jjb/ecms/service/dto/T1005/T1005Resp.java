package com.jjb.ecms.service.dto.T1005;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

@Data
public class T1005Resp  extends BasicResponse implements Serializable {
	private static final long serialVersionUID = -1301468749747928037L;

	/**
	 * 申请件编号 appNo
	 */
	public String appNo;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("T1005Resp [appNo=");
		builder.append(appNo);
		builder.append("]");
		return builder.toString();
	}
	
}
