package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jjb.ecms.service.dto.T9000.T9000Req;

/**
  *@ClassName 推送审批结论
  *@Description TODO
  *@Author lixing
  *Date 2018/10/23 10:23
  *Version 1.0
  */
@XmlType(propOrder = {"trans0067Req"})
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class T2004800067Req extends OnlineBasicReq{
	
    @XmlElement(name ="BODY")
    public T9000Req trans0067Req = new T9000Req();

	public T9000Req getTrans0067Req() {
		return trans0067Req;
	}

	public void setTrans0067Req(T9000Req trans0067Req) {
		this.trans0067Req = trans0067Req;
	}

}
