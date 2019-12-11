package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
  *@ClassName ApplyQueryRankingDto
  *@Description TODO
  *@Author lixing
  *Date 2018/12/24 16:25
  *Version 1.0
  */
public class ApplyQueryRankingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //查询类型
    private String quyType;
    //查询渠道
    private String appSource;
    //查询名次总数
    private String totalCnt;
    // 排名名次
    private String ranking;
    // 客户姓名
    private String name;
    // 客户证件号码
    private String idNo;
    // 申请产品
    private String productCd;
    // 手机号码
    private String cellphone;
    // 成功申请数量
    private String succCnt;
	//核卡情况
	private String cardStatus;
	//是否新户
	private String ifNewUser;
	//是否完成首刷
	private String ifSwiped;
    
    // 推广人工号
    private String spreaderNo;
    //当前推广人推广数量
    private String spreadCnt;
    //当前推广人已核卡数量
    private String succApprovalCnt;    
    //当前推广人有效核卡数量
    private String succEffCnt;
    //拒绝数量
    private String refuseCnt;
    //审批中数量
    private String approveCnt;
    //待预审数量
    private String preCnt;
    
    //排行榜开始统计日期
    private String startDate;
    //排行榜结束统计日期
    private String endDate;
    
    
	public String getQuyType() {
		return quyType;
	}
	public void setQuyType(String quyType) {
		this.quyType = quyType;
	}
	public String getAppSource() {
		return appSource;
	}
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}
	public String getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getSuccCnt() {
		return succCnt;
	}
	public void setSuccCnt(String succCnt) {
		this.succCnt = succCnt;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getIfNewUser() {
		return ifNewUser;
	}
	public void setIfNewUser(String ifNewUser) {
		this.ifNewUser = ifNewUser;
	}
	public String getIfSwiped() {
		return ifSwiped;
	}
	public void setIfSwiped(String ifSwiped) {
		this.ifSwiped = ifSwiped;
	}
	public String getSpreaderNo() {
		return spreaderNo;
	}
	public void setSpreaderNo(String spreaderNo) {
		this.spreaderNo = spreaderNo;
	}
	public String getSpreadCnt() {
		return spreadCnt;
	}
	public void setSpreadCnt(String spreadCnt) {
		this.spreadCnt = spreadCnt;
	}
	public String getSuccApprovalCnt() {
		return succApprovalCnt;
	}
	public void setSuccApprovalCnt(String succApprovalCnt) {
		this.succApprovalCnt = succApprovalCnt;
	}
	public String getSuccEffCnt() {
		return succEffCnt;
	}
	public void setSuccEffCnt(String succEffCnt) {
		this.succEffCnt = succEffCnt;
	}
	public String getRefuseCnt() {
		return refuseCnt;
	}
	public void setRefuseCnt(String refuseCnt) {
		this.refuseCnt = refuseCnt;
	}
	public String getApproveCnt() {
		return approveCnt;
	}
	public void setApproveCnt(String approveCnt) {
		this.approveCnt = approveCnt;
	}
	public String getPreCnt() {
		return preCnt;
	}
	public void setPreCnt(String preCnt) {
		this.preCnt = preCnt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
