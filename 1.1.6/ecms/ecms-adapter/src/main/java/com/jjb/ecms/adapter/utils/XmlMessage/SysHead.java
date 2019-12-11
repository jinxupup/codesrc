package com.jjb.ecms.adapter.utils.XmlMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
  *@ClassName SysHead
  *@Description TODO
  *@Author lixing
  *Date 2018/10/23 10:25
  *Version 1.0
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"Mac","MsgId","SourceSysId","ConsumerId","ServiceCode","ServiceScene"})
public class SysHead {


    @XmlElement
    public String Mac;
    @XmlElement
    public String MsgId;
    @XmlElement
    public String SourceSysId;
    @XmlElement
    public String ConsumerId;
    @XmlElement
    public String ServiceCode;
    @XmlElement
    public String ServiceScene;

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getSourceSysId() {
        return SourceSysId;
    }

    public void setSourceSysId(String sourceSysId) {
        SourceSysId = sourceSysId;
    }

    public String getConsumerId() {
        return ConsumerId;
    }

    public void setConsumerId(String consumerId) {
        ConsumerId = consumerId;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public String getServiceScene() {
        return ServiceScene;
    }

    public void setServiceScene(String serviceScene) {
        ServiceScene = serviceScene;
    }

}
