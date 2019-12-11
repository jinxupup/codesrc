package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 补件结果查询信息模型
 * 
 * @author H.N
 */
public class PatchResultDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 证件类型
	 * */
    public String idType;

	/**
	 * 证件号码
	 * */
    public String idNo;

	/**
	 * 姓名
	 * */
    public String name;

	/**
	 * 卡产品名称
	 * */
    public String productName;

	/**
	 * 申请受理日期
	 * */
    public String applyDate;

	/**
	 * 卡申请状态
	 * */
    public String rtfState;

	/**
	 * 申请件编号
	 * */
    public String appNo;

	/**
	 * 补件类型
	 * */
    public String pbType;

	/**
	 * 补件开始时间
	 * */
    public Date pbStartDate;

	/**
	 * 补件超时时间
	 * */
    public Date pbTimeoutDate;

	/**
	 * 补件开始业务时间
	 * */
    public Date pbStBatchDate;

	/**
	 * 补件超时业务时间
	 * */
    public Date pbOutBatchDate;
	
	/**
	 * 影像批次号
	 * @return
	 */
    public String appCode;
	
    /**
	 * 任务所属人
	 * @return
	 */
    public String operatorId;

    /**
	 * 卡产品代码
	 * @return
	 */
    public String productCd;
	
    /**
	 * 机构号
	 * @return
	 */
    public String org;
    
    /**
	 * 当前页面
	 * @return
	 */
    public Integer curPage;
    
    /**
   	 * 每页行数
   	 * @return
   	 */
    public Integer rowCnt;
    
    

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the applyDate
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @param applyDate the applyDate to set
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * @return the rtfState
	 */
	public String getRtfState() {
		return rtfState;
	}

	/**
	 * @param rtfState the rtfState to set
	 */
	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}

	/**
	 * @return the appNo
	 */
	public String getAppNo() {
		return appNo;
	}

	/**
	 * @param appNo the appNo to set
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * @return the pbType
	 */
	public String getPbType() {
		return pbType;
	}

	/**
	 * @param pbType the pbType to set
	 */
	public void setPbType(String pbType) {
		this.pbType = pbType;
	}

	/**
	 * @return the pbStartDate
	 */
	public Date getPbStartDate() {
		return pbStartDate;
	}

	/**
	 * @param pbStartDate the pbStartDate to set
	 */
	public void setPbStartDate(Date pbStartDate) {
		this.pbStartDate = pbStartDate;
	}

	/**
	 * @return the pbTimeoutDate
	 */
	public Date getPbTimeoutDate() {
		return pbTimeoutDate;
	}

	/**
	 * @param pbTimeoutDate the pbTimeoutDate to set
	 */
	public void setPbTimeoutDate(Date pbTimeoutDate) {
		this.pbTimeoutDate = pbTimeoutDate;
	}

	/**
	 * @return the pbStBatchDate
	 */
	public Date getPbStBatchDate() {
		return pbStBatchDate;
	}

	/**
	 * @param pbStBatchDate the pbStBatchDate to set
	 */
	public void setPbStBatchDate(Date pbStBatchDate) {
		this.pbStBatchDate = pbStBatchDate;
	}

	/**
	 * @return the pbOutBatchDate
	 */
	public Date getPbOutBatchDate() {
		return pbOutBatchDate;
	}

	/**
	 * @param pbOutBatchDate the pbOutBatchDate to set
	 */
	public void setPbOutBatchDate(Date pbOutBatchDate) {
		this.pbOutBatchDate = pbOutBatchDate;
	}

	/**
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return the operatorId
	 */
	public String getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the productCd
	 */
	public String getProductCd() {
		return productCd;
	}

	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	/**
	 * @return the org
	 */
	public String getOrg() {
		return org;
	}

	/**
	 * @param org the org to set
	 */
	public void setOrg(String org) {
		this.org = org;
	}
	
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(int rowCnt) {
		this.rowCnt = rowCnt;
	}
	
}    