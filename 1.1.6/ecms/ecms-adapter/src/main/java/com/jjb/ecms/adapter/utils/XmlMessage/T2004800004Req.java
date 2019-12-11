package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jjb.ecms.service.dto.Trans0004.Trans0004Req;

/**
  *@ClassName T2004800004Req
  *@Description 约定还款账户合理性验证
  *@Author lixing
  *Date 2018/10/23 10:23
  *Version 1.0
  */
@XmlType(propOrder = {"trans0004Req"})
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class T2004800004Req extends OnlineBasicReq{
	
    @XmlElement(name ="BODY")
    public Trans0004Req trans0004Req = new Trans0004Req();


}
