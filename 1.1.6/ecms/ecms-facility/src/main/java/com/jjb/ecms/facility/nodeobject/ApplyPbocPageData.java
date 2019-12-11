/**
 * 
 */
package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: 人行征信页面数据实体类,该对象要存放到节点对象里
 * @author JYData-R&D-BigK.K
 * @date 2016年9月9日 下午4:13:29
 * @version V1.0
 */
public class ApplyPbocPageData implements Serializable {

	private static final long serialVersionUID = 1;
	private String localHtml;
	private boolean isOk = false;
	private List<Map<String, String>> uploadList;
	private String returnCode;
	private String returnMsg;
	private String pbocMobile;

	public String getPbocMobile() {
		return pbocMobile;
	}

	public void setPbocMobile(String pbocMobile) {
		this.pbocMobile = pbocMobile;
	}

	public String getLocalHtml() {
		return localHtml;
	}

	public void setLocalHtml(String localHtml) {
		this.localHtml = localHtml;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public List<Map<String, String>> getUploadList() {
		return uploadList;
	}

	public void setUploadList(List<Map<String, String>> uploadList) {
		this.uploadList = uploadList;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
