package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 合伙人批处理中间表
 * @author jjb
 */
public class TmPartnerBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String appNo;

    private String cardNo;

    private String ifNewUser;

    private String ifSwiped;

    private String activateInd;

    private String spreaderName;

    private String spreaderIdNo;

    private String spreaderPhone;

    private String spreaderCrop;

    private String spreaderDepaertment;

    private String ifLsWorker;

    private Date firstSwipedDate;

    private Date updateTime;

    private Integer jpaVersion;

    /**
     * <p>ID</p>
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * <p>卡号</p>
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * <p>卡号</p>
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * <p>是否新用户</p>
     */
    public String getIfNewUser() {
        return ifNewUser;
    }

    /**
     * <p>是否新用户</p>
     */
    public void setIfNewUser(String ifNewUser) {
        this.ifNewUser = ifNewUser;
    }

    /**
     * <p>是否完成首刷</p>
     */
    public String getIfSwiped() {
        return ifSwiped;
    }

    /**
     * <p>是否完成首刷</p>
     */
    public void setIfSwiped(String ifSwiped) {
        this.ifSwiped = ifSwiped;
    }

    /**
     * <p>是否已激活</p>
     */
    public String getActivateInd() {
        return activateInd;
    }

    /**
     * <p>是否已激活</p>
     */
    public void setActivateInd(String activateInd) {
        this.activateInd = activateInd;
    }

    /**
     * <p>推荐人姓名</p>
     */
    public String getSpreaderName() {
        return spreaderName;
    }

    /**
     * <p>推荐人姓名</p>
     */
    public void setSpreaderName(String spreaderName) {
        this.spreaderName = spreaderName;
    }

    /**
     * <p>推荐人证件号</p>
     */
    public String getSpreaderIdNo() {
        return spreaderIdNo;
    }

    /**
     * <p>推荐人证件号</p>
     */
    public void setSpreaderIdNo(String spreaderIdNo) {
        this.spreaderIdNo = spreaderIdNo;
    }

    /**
     * <p>推荐人手机号码</p>
     */
    public String getSpreaderPhone() {
        return spreaderPhone;
    }

    /**
     * <p>推荐人手机号码</p>
     */
    public void setSpreaderPhone(String spreaderPhone) {
        this.spreaderPhone = spreaderPhone;
    }

    /**
     * <p>推荐人单位</p>
     */
    public String getSpreaderCrop() {
        return spreaderCrop;
    }

    /**
     * <p>推荐人单位</p>
     */
    public void setSpreaderCrop(String spreaderCrop) {
        this.spreaderCrop = spreaderCrop;
    }

    /**
     * <p>推荐人部门</p>
     */
    public String getSpreaderDepaertment() {
        return spreaderDepaertment;
    }

    /**
     * <p>推荐人部门</p>
     */
    public void setSpreaderDepaertment(String spreaderDepaertment) {
        this.spreaderDepaertment = spreaderDepaertment;
    }

    /**
     * <p>是否联盛员工</p>
     */
    public String getIfLsWorker() {
        return ifLsWorker;
    }

    /**
     * <p>是否联盛员工</p>
     */
    public void setIfLsWorker(String ifLsWorker) {
        this.ifLsWorker = ifLsWorker;
    }

    /**
     * <p>首刷日期</p>
     */
    public Date getFirstSwipedDate() {
        return firstSwipedDate;
    }

    /**
     * <p>首刷日期</p>
     */
    public void setFirstSwipedDate(Date firstSwipedDate) {
        this.firstSwipedDate = firstSwipedDate;
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

    /**
     * <p>JPA_VERSION</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("appNo", appNo);
        map.put("cardNo", cardNo);
        map.put("ifNewUser", ifNewUser);
        map.put("ifSwiped", ifSwiped);
        map.put("activateInd", activateInd);
        map.put("spreaderName", spreaderName);
        map.put("spreaderIdNo", spreaderIdNo);
        map.put("spreaderPhone", spreaderPhone);
        map.put("spreaderCrop", spreaderCrop);
        map.put("spreaderDepaertment", spreaderDepaertment);
        map.put("ifLsWorker", ifLsWorker);
        map.put("firstSwipedDate", firstSwipedDate);
        map.put("updateTime", updateTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getLongValue(map.get("id")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("cardNo")) this.setCardNo(DataTypeUtils.getStringValue(map.get("cardNo")));
        if (map.containsKey("ifNewUser")) this.setIfNewUser(DataTypeUtils.getStringValue(map.get("ifNewUser")));
        if (map.containsKey("ifSwiped")) this.setIfSwiped(DataTypeUtils.getStringValue(map.get("ifSwiped")));
        if (map.containsKey("activateInd")) this.setActivateInd(DataTypeUtils.getStringValue(map.get("activateInd")));
        if (map.containsKey("spreaderName")) this.setSpreaderName(DataTypeUtils.getStringValue(map.get("spreaderName")));
        if (map.containsKey("spreaderIdNo")) this.setSpreaderIdNo(DataTypeUtils.getStringValue(map.get("spreaderIdNo")));
        if (map.containsKey("spreaderPhone")) this.setSpreaderPhone(DataTypeUtils.getStringValue(map.get("spreaderPhone")));
        if (map.containsKey("spreaderCrop")) this.setSpreaderCrop(DataTypeUtils.getStringValue(map.get("spreaderCrop")));
        if (map.containsKey("spreaderDepaertment")) this.setSpreaderDepaertment(DataTypeUtils.getStringValue(map.get("spreaderDepaertment")));
        if (map.containsKey("ifLsWorker")) this.setIfLsWorker(DataTypeUtils.getStringValue(map.get("ifLsWorker")));
        if (map.containsKey("firstSwipedDate")) this.setFirstSwipedDate(DataTypeUtils.getDateValue(map.get("firstSwipedDate")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", cardNo="+cardNo+
        "cardNo="+cardNo+
        ", ifNewUser="+ifNewUser+
        "ifNewUser="+ifNewUser+
        ", ifSwiped="+ifSwiped+
        "ifSwiped="+ifSwiped+
        ", activateInd="+activateInd+
        "activateInd="+activateInd+
        ", spreaderName="+spreaderName+
        "spreaderName="+spreaderName+
        ", spreaderIdNo="+spreaderIdNo+
        "spreaderIdNo="+spreaderIdNo+
        ", spreaderPhone="+spreaderPhone+
        "spreaderPhone="+spreaderPhone+
        ", spreaderCrop="+spreaderCrop+
        "spreaderCrop="+spreaderCrop+
        ", spreaderDepaertment="+spreaderDepaertment+
        "spreaderDepaertment="+spreaderDepaertment+
        ", ifLsWorker="+ifLsWorker+
        "ifLsWorker="+ifLsWorker+
        ", firstSwipedDate="+firstSwipedDate+
        "firstSwipedDate="+firstSwipedDate+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (id == null) id = 0l;
        if (appNo == null) appNo = "";
        if (cardNo == null) cardNo = "";
        if (ifNewUser == null) ifNewUser = "";
        if (ifSwiped == null) ifSwiped = "";
        if (activateInd == null) activateInd = "";
        if (spreaderName == null) spreaderName = "";
        if (spreaderIdNo == null) spreaderIdNo = "";
        if (spreaderPhone == null) spreaderPhone = "";
        if (spreaderCrop == null) spreaderCrop = "";
        if (spreaderDepaertment == null) spreaderDepaertment = "";
        if (ifLsWorker == null) ifLsWorker = "";
        if (firstSwipedDate == null) firstSwipedDate = new Date();
        if (updateTime == null) updateTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}