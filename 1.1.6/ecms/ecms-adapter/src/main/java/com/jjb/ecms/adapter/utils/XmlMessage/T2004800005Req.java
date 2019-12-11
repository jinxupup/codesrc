package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jjb.ecms.service.dto.Trans0005.Trans0005Req;

/**
  *@ClassName ImmediateXmlReq
  *@Description TODO
  *@Author lixing
  *Date 2018/10/23 10:23
  *Version 1.0
  */
//@XmlType(propOrder = {"sysHead","apphead","trans0005Req"})
@XmlType(propOrder = {"trans0005Req"})
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class T2004800005Req extends OnlineBasicReq{
	
    @XmlElement(name ="BODY")
    public Trans0005Req trans0005Req = new Trans0005Req();

    public Trans0005Req getTrans0005Req() {
        return trans0005Req;
    }

    public void setTrans0005Req(Trans0005Req trans0005Req) {
        this.trans0005Req = trans0005Req;
    }


}
