package com.jjb.ecms.service.dto.Trans0004;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;
/**
 *@ClassName Trans0004Req
 *@Description 约定还款请求
 *@Author lixing
 *Date 2018/12/31 14:32
 *Version 1.0
 */
@Data
public class Trans0004Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="2004800005";

    private String ProtocolNo;
    private String DebitCardNo;
	private String CardNm;
	private String CertType;
	private String CertNo;
	private String CardTel;
	private String CardBankNm;
	private String CardBankNo;

}
