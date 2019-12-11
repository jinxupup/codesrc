package com.jjb.ecms.service.dto.Trans0005;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 实时短信发送
 * @author hp
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Trans0005Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="2004800005";

	private String Tel;//电话号码
	private String SndCntnt;//短信内容

}
