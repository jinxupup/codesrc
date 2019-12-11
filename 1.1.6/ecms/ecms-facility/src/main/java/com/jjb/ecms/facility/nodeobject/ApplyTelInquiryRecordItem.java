package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 初审调查-电话基本信息调查
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:59:12
 * @version V1.0
 */
public class ApplyTelInquiryRecordItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3604321259146447904L;
	private String telType;
	private String phone;
	private Date telDate;
	private String telResult;
	private String memo;

	public String getTelType() {
		return telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getTelDate() {
		return telDate;
	}

	public void setTelDate(Date telDate) {
		this.telDate = telDate;
	}

	public String getTelResult() {
		return telResult;
	}

	public void setTelResult(String telResult) {
		this.telResult = telResult;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
