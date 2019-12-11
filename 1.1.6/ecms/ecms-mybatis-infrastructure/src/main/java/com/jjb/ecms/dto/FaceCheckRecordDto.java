package com.jjb.ecms.dto;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: shiminghong
 * @Data: 2019/6/19 18:34
 * @Version 1.0
 */

public class FaceCheckRecordDto {
    private String appNo;//申请编号
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String finalResult;//最终验证结果

    private String currCnt; //当前验证次数
    private String checkRs;//当前验证结果
    private String checkRsDesc;//当前验证结果描述

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public String getCurrCnt() {
        return currCnt;
    }

    public void setCurrCnt(String currCnt) {
        this.currCnt = currCnt;
    }

    public String getCheckRs() {
        return checkRs;
    }

    public void setCheckRs(String checkRs) {
        this.checkRs = checkRs;
    }

    public String getCheckRsDesc() {
        return checkRsDesc;
    }

    public void setCheckRsDesc(String checkRsDesc) {
        this.checkRsDesc = checkRsDesc;
    }
}
