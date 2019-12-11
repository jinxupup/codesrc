package com.jjb.ecms.service.dto.T1008;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * @ClassName T1008Req
 * @Description TODO 4.1.8.T1008-受理外部决策结果请求
 * @Author smh
 * Date 2018/11/23 11:32
 * Version 1.0
 */
@Data
public class T1008Req extends BasicRequest implements Serializable{

    private static final long serialVersionUID = -4085333700917194561L;
    @XStreamOmitField
    public static final String servId="T1008";
    private String appNo ;//申请件编号
    private String finalResult ;//终审结论
    private BigDecimal finalCreditLmt ;//授信额度
    private String finalReason ;// 拒绝原因
    private Date finalConfirmTime;// 审批结束日期

}
