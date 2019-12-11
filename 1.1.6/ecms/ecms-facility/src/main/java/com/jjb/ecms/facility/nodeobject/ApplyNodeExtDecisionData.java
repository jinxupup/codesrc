package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName ExtDecision
 * @Description TODO 受理外部决策结果信息确认
 * @Author smh
 * Date 2018/11/23 16:30
 * Version 1.0
 */
public class ApplyNodeExtDecisionData implements Serializable{
    private static final long serialVersionUID = -8766043328145094237L;

    private String finalResult ;//终审结论
    private BigDecimal finalCreditLmt ;//授信额度
    private String finalReason ;// 拒绝原因
    private Date finalConfirmTime;// 审批结束日期

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public BigDecimal getFinalCreditLmt() {
        return finalCreditLmt;
    }

    public void setFinalCreditLmt(BigDecimal finalCreditLmt) {
        this.finalCreditLmt = finalCreditLmt;
    }

    public String getFinalReason() {
        return finalReason;
    }

    public void setFinalReason(String finalReason) {
        this.finalReason = finalReason;
    }

    public Date getFinalConfirmTime() {
        return finalConfirmTime;
    }

    public void setFinalConfirmTime(Date finalConfirmTime) {
        this.finalConfirmTime = finalConfirmTime;
    }
}
