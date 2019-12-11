package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
  *@ClassName SysHead
  *@Description TODO
  *@Author lixing
  *Date 2018/10/23 10:25
  *Version 1.0
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"TranDate","TranTime","TranTellerNo","TranSeqNo","GlobalSeqNo","BranchId"})
public class AppHead {

    public String TranDate;
    public String TranTime;
    public String TranTellerNo;
    public String TranSeqNo;
    public String GlobalSeqNo;
    public String BranchId;

    public String getTranDate() {
        return TranDate;
    }

    public void setTranDate(String tranDate) {
        TranDate = tranDate;
    }

    public String getTranTime() {
        return TranTime;
    }

    public void setTranTime(String tranTime) {
        TranTime = tranTime;
    }

    public String getTranTellerNo() {
        return TranTellerNo;
    }

    public void setTranTellerNo(String tranTellerNo) {
        TranTellerNo = tranTellerNo;
    }

    public String getTranSeqNo() {
        return TranSeqNo;
    }

    public void setTranSeqNo(String tranSeqNo) {
        TranSeqNo = tranSeqNo;
    }

    public String getGlobalSeqNo() {
        return GlobalSeqNo;
    }

    public void setGlobalSeqNo(String globalSeqNo) {
        GlobalSeqNo = globalSeqNo;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }
}
