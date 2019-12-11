package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信信息表
 * @author jjb
 */
public class TmAppMsgSend implements Serializable {
    private static final long serialVersionUID = 1L;

    private String appNo;

    private String org;

    private String name;

    private String cellPhone;

    private String idNo;

    private String msgContent;

    private String condition;

    private String remark;

    private String msgSendTimes;

    private String msgType;

    private String tonkenId;

    private Date createDate;

    private Date updateTime;

    /**
     * <p>申请件编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请件编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>机构号</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>机构号</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>姓名</p>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>电话号码</p>
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * <p>电话号码</p>
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * <p>身份证号码</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>身份证号码</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>短信内容</p>
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * <p>短信内容</p>
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * <p>短信状态</p>
     */
    public String getCondition() {
        return condition;
    }

    /**
     * <p>短信状态</p>
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>短信发送次数</p>
     */
    public String getMsgSendTimes() {
        return msgSendTimes;
    }

    /**
     * <p>短信发送次数</p>
     */
    public void setMsgSendTimes(String msgSendTimes) {
        this.msgSendTimes = msgSendTimes;
    }

    /**
     * <p>短信类型</p>
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * <p>短信类型</p>
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * <p>时间戳</p>
     */
    public String getTonkenId() {
        return tonkenId;
    }

    /**
     * <p>时间戳</p>
     */
    public void setTonkenId(String tonkenId) {
        this.tonkenId = tonkenId;
    }

    /**
     * <p>获取时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>获取时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("appNo", appNo);
        map.put("org", org);
        map.put("name", name);
        map.put("cellPhone", cellPhone);
        map.put("idNo", idNo);
        map.put("msgContent", msgContent);
        map.put("condition", condition);
        map.put("remark", remark);
        map.put("msgSendTimes", msgSendTimes);
        map.put("msgType", msgType);
        map.put("tonkenId", tonkenId);
        map.put("createDate", createDate);
        map.put("updateTime", updateTime);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("cellPhone")) this.setCellPhone(DataTypeUtils.getStringValue(map.get("cellPhone")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("msgContent")) this.setMsgContent(DataTypeUtils.getStringValue(map.get("msgContent")));
        if (map.containsKey("condition")) this.setCondition(DataTypeUtils.getStringValue(map.get("condition")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("msgSendTimes")) this.setMsgSendTimes(DataTypeUtils.getStringValue(map.get("msgSendTimes")));
        if (map.containsKey("msgType")) this.setMsgType(DataTypeUtils.getStringValue(map.get("msgType")));
        if (map.containsKey("tonkenId")) this.setTonkenId(DataTypeUtils.getStringValue(map.get("tonkenId")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", org="+org+
        "org="+org+
        ", name="+name+
        "name="+name+
        ", cellPhone="+cellPhone+
        "cellPhone="+cellPhone+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", msgContent="+msgContent+
        "msgContent="+msgContent+
        ", condition="+condition+
        "condition="+condition+
        ", remark="+remark+
        "remark="+remark+
        ", msgSendTimes="+msgSendTimes+
        "msgSendTimes="+msgSendTimes+
        ", msgType="+msgType+
        "msgType="+msgType+
        ", tonkenId="+tonkenId+
        "tonkenId="+tonkenId+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        "]";
    }

    public void fillDefaultValues() {
        if (appNo == null) appNo = "";
        if (org == null) org = "";
        if (name == null) name = "";
        if (cellPhone == null) cellPhone = "";
        if (idNo == null) idNo = "";
        if (msgContent == null) msgContent = "";
        if (condition == null) condition = "";
        if (remark == null) remark = "";
        if (msgSendTimes == null) msgSendTimes = "";
        if (msgType == null) msgType = "";
        if (tonkenId == null) tonkenId = "";
        if (createDate == null) createDate = new Date();
        if (updateTime == null) updateTime = new Date();
    }
}