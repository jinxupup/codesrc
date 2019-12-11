package com.jjb.ecms.service.dto.Trans0059;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 实时建账制卡
 * @author hp
 *
 */
@Data
public class Trans0059Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="2004800059";
	
	private String contentTemplate;


}
