/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * @Description: 开关参数实体类
 * @author JYData-R&D-Big T.T
 * @date 2018年4月21日 下午3:44:16
 * @version V1.0  
 */

public class ApplyPageOnOffParamDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String isTelToCheck;//电调是否合并到初审的开关参数
	
	private String isUsedRisk;//风控系统是否开启的开关参数

	private String isSaveSkip;//初审、电调页面保存成功是否跳转的开关参数
	
	private String isUsedIdCheck;//是否启用核身页面的开关参数
	
	private String isValidateProductInfo;//是否启用验证卡产品信息
	
	private String isUseFreeTelCheck;//是否启用初审免电话调查
	
	private String isRetrialToFinalPage;//重审完成是否到终审的开关参数
	
	private String isUseInputManageUser;//进件管理员开关参数
	
	private String isContinueReview;//是否继续复核开关参数
	
	private String isExtRisk;//是否开启使用外部(行内)风险决策评分系统
	
	/**
	 * @return the isTelToCheck
	 */
	public String getIsTelToCheck() {
		return isTelToCheck;
	}

	/**
	 * @param isTelToCheck the isTelToCheck to set
	 */
	public void setIsTelToCheck(String isTelToCheck) {
		this.isTelToCheck = isTelToCheck;
	}

	/**
	 * @return the isUsedRisk
	 */
	public String getIsUsedRisk() {
		return isUsedRisk;
	}

	/**
	 * @param isUsedRisk the isUsedRisk to set
	 */
	public void setIsUsedRisk(String isUsedRisk) {
		this.isUsedRisk = isUsedRisk;
	}

	/**
	 * @return the isSaveSkip
	 */
	public String getIsSaveSkip() {
		return isSaveSkip;
	}

	/**
	 * @param isSaveSkip the isSaveSkip to set
	 */
	public void setIsSaveSkip(String isSaveSkip) {
		this.isSaveSkip = isSaveSkip;
	}

	/**
	 * @return the isUsedIdCheck
	 */
	public String getIsUsedIdCheck() {
		return isUsedIdCheck;
	}

	/**
	 * @param isUsedIdCheck the isUsedIdCheck to set
	 */
	public void setIsUsedIdCheck(String isUsedIdCheck) {
		this.isUsedIdCheck = isUsedIdCheck;
	}

	/**
	 * @return the isValidateProductInfo
	 */
	public String getIsValidateProductInfo() {
		return isValidateProductInfo;
	}

	/**
	 * @param isValidateProductInfo the isValidateProductInfo to set
	 */
	public void setIsValidateProductInfo(String isValidateProductInfo) {
		this.isValidateProductInfo = isValidateProductInfo;
	}
	
	/**
	 * @return the isUseFreeTelCheck
	 */
	public String getIsUseFreeTelCheck() {
		return isUseFreeTelCheck;
	}
	
	/**
	 * @param isUseFreeTelCheck the isUseFreeTelCheck to set
	 */
	public void setIsUseFreeTelCheck(String isUseFreeTelCheck) {
		this.isUseFreeTelCheck = isUseFreeTelCheck;
	}

	/**
	 * @return the isRetrialToFinalPage
	 */
	public String getIsRetrialToFinalPage() {
		return isRetrialToFinalPage;
	}

	/**
	 * @param isRetrialToFinalPage the isRetrialToFinalPage to set
	 */
	public void setIsRetrialToFinalPage(String isRetrialToFinalPage) {
		this.isRetrialToFinalPage = isRetrialToFinalPage;
	}

	/**
	 * @return the isUseInputManageUser
	 */
	public String getIsUseInputManageUser() {
		return isUseInputManageUser;
	}

	/**
	 * @param isUseInputManageUser the isUseInputManageUser to set
	 */
	public void setIsUseInputManageUser(String isUseInputManageUser) {
		this.isUseInputManageUser = isUseInputManageUser;
	}

	/**
	 * @return the isContinueReview
	 */
	public String getIsContinueReview() {
		return isContinueReview;
	}

	/**
	 * @param isContinueReview the isContinueReview to set
	 */
	public void setIsContinueReview(String isContinueReview) {
		this.isContinueReview = isContinueReview;
	}
	public String getIsExtRisk() {
		return isExtRisk;
	}

	public void setIsExtRisk(String isExtRisk) {
		this.isExtRisk = isExtRisk;
	}

	public void updateFromMap(Map<String, Object> map) {
        if (map.containsKey("isTelToCheck")) this.setIsTelToCheck(DataTypeUtils.getStringValue(map.get("isTelToCheck")));
        if (map.containsKey("isUsedRisk")) this.setIsUsedRisk(DataTypeUtils.getStringValue(map.get("isUsedRisk")));
        if (map.containsKey("isSaveSkip")) this.setIsSaveSkip(DataTypeUtils.getStringValue(map.get("isSaveSkip")));
        if (map.containsKey("isUsedIdCheck")) this.setIsUsedIdCheck(DataTypeUtils.getStringValue(map.get("isUsedIdCheck")));
        if (map.containsKey("isValidateProductInfo")) this.setIsValidateProductInfo(DataTypeUtils.getStringValue(map.get("isValidateProductInfo")));
        if (map.containsKey("isUseFreeTelCheck")) this.setIsUseFreeTelCheck(DataTypeUtils.getStringValue(map.get("isUseFreeTelCheck")));
        if (map.containsKey("isRetrialToFinalPage")) this.setIsRetrialToFinalPage(DataTypeUtils.getStringValue(map.get("isRetrialToFinalPage")));
        if (map.containsKey("isUseInputManageUser")) this.setIsUseInputManageUser(DataTypeUtils.getStringValue(map.get("isUseInputManageUser")));
        if (map.containsKey("isContinueReview")) this.setIsContinueReview(DataTypeUtils.getStringValue(map.get("isContinueReview")));
        if (map.containsKey("isExtRisk")) this.setIsExtRisk(DataTypeUtils.getStringValue(map.get("isExtRisk")));
    }
}
