package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
  *@ClassName ImmediateXmlReq
  *@Description TODO
  *@Author lixing
  *Date 2018/10/23 10:23
  *Version 1.0
  */
@XmlType(propOrder = {"sysHead","apphead"})
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.FIELD)
public class OnlineBasicReq {


    @XmlElement(name ="SYS_HEAD")
    public  SysHead sysHead = new SysHead();
    @XmlElement(name ="APP_HEAD")
    public AppHead apphead = new AppHead();

    public SysHead getSysHead() {
        return sysHead;
    }

    public void setSysHead(SysHead sysHead) {
        this.sysHead = sysHead;
    }

    public AppHead getApphead() {
        return apphead;
    }

    public void setApphead(AppHead apphead) {
        this.apphead = apphead;
    }

}
