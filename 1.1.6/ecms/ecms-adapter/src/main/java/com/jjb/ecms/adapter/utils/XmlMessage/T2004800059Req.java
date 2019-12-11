package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.jjb.acl.facility.enums.bus.ApplyFileItem;

/**
  *@ClassName T2004800059Req
  *@Description TODO
  *@Author lixing
  *Date 2018/10/23 10:23
  *Version 1.0
  */
//@XmlType(propOrder = {"sysHead","apphead","applyFileItem"})
@XmlType(propOrder = {"applyFileItem"})
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class T2004800059Req extends OnlineBasicReq{

    @XmlElement(name ="BODY")
    public ApplyFileItem applyFileItem = new ApplyFileItem();

    public ApplyFileItem getApplyFileItem() {
        return applyFileItem;
    }

    public void setApplyFileItem(ApplyFileItem applyFileItem) {
        this.applyFileItem = applyFileItem;
    }


}
