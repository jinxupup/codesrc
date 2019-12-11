package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
  *@ClassName ApplyQueryRankingDto
  *@Description TODO
  *@Author lixing
  *Date 2018/12/24 16:25
  *Version 1.0
  */
public class ApplyTransferDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //当前客户经理
    private String CurOpUser;
    //操作类型
    private String OpType;
    //接收案件客户经理
    private String AccepUser;
    //备注
    private String Remark;

    public String getCurOpUser() {
        return CurOpUser;
    }

    public void setCurOpUser(String curOpUser) {
        CurOpUser = curOpUser;
    }

    public String getOpType() {
        return OpType;
    }

    public void setOpType(String opType) {
        OpType = opType;
    }

    public String getAccepUser() {
        return AccepUser;
    }

    public void setAccepUser(String accepUser) {
        AccepUser = accepUser;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
