package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 推广人信息表
 * @author jjb
 */
public class TmAppSprePerBank implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String spreaderNum;

    private String spreaderName;

    private String spreaderPhone;

    private String spreaderPerformance;

    private String spreaderBankId;

    private String spreaderBankName;

    private String spreaderOrg;

    private String sprUserType;

    private String spreaderStatus;

    private Date createDate;

    private Date updateDate;

    private Integer jpaVersion;

    private String createUser;

    private String updateUser;

    /**
     * <p>ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>机构</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>机构</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>推广人编号</p>
     */
    public String getSpreaderNum() {
        return spreaderNum;
    }

    /**
     * <p>推广人编号</p>
     */
    public void setSpreaderNum(String spreaderNum) {
        this.spreaderNum = spreaderNum;
    }

    /**
     * <p>推广人姓名</p>
     */
    public String getSpreaderName() {
        return spreaderName;
    }

    /**
     * <p>推广人姓名</p>
     */
    public void setSpreaderName(String spreaderName) {
        this.spreaderName = spreaderName;
    }

    /**
     * <p>推广人手机号码</p>
     */
    public String getSpreaderPhone() {
        return spreaderPhone;
    }

    /**
     * <p>推广人手机号码</p>
     */
    public void setSpreaderPhone(String spreaderPhone) {
        this.spreaderPhone = spreaderPhone;
    }

    /**
     * <p>推广人绩效号</p>
     */
    public String getSpreaderPerformance() {
        return spreaderPerformance;
    }

    /**
     * <p>推广人绩效号</p>
     */
    public void setSpreaderPerformance(String spreaderPerformance) {
        this.spreaderPerformance = spreaderPerformance;
    }

    /**
     * <p>推广人所属分行ID</p>
     */
    public String getSpreaderBankId() {
        return spreaderBankId;
    }

    /**
     * <p>推广人所属分行ID</p>
     */
    public void setSpreaderBankId(String spreaderBankId) {
        this.spreaderBankId = spreaderBankId;
    }

    /**
     * <p>推广人所属分行名称</p>
     */
    public String getSpreaderBankName() {
        return spreaderBankName;
    }

    /**
     * <p>推广人所属分行名称</p>
     */
    public void setSpreaderBankName(String spreaderBankName) {
        this.spreaderBankName = spreaderBankName;
    }

    /**
     * <p>推广机构</p>
     */
    public String getSpreaderOrg() {
        return spreaderOrg;
    }

    /**
     * <p>推广机构</p>
     */
    public void setSpreaderOrg(String spreaderOrg) {
        this.spreaderOrg = spreaderOrg;
    }

    /**
     * <p>推广人类型</p>
     */
    public String getSprUserType() {
        return sprUserType;
    }

    /**
     * <p>推广人类型</p>
     */
    public void setSprUserType(String sprUserType) {
        this.sprUserType = sprUserType;
    }

    /**
     * <p>推广人状态</p>
     */
    public String getSpreaderStatus() {
        return spreaderStatus;
    }

    /**
     * <p>推广人状态</p>
     */
    public void setSpreaderStatus(String spreaderStatus) {
        this.spreaderStatus = spreaderStatus;
    }

    /**
     * <p>记录创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>记录创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>记录修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>记录修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>乐观锁</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>创建人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>创建人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * <p>更新人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("spreaderNum", spreaderNum);
        map.put("spreaderName", spreaderName);
        map.put("spreaderPhone", spreaderPhone);
        map.put("spreaderPerformance", spreaderPerformance);
        map.put("spreaderBankId", spreaderBankId);
        map.put("spreaderBankName", spreaderBankName);
        map.put("spreaderOrg", spreaderOrg);
        map.put("sprUserType", sprUserType);
        map.put("spreaderStatus", spreaderStatus);
        map.put("createDate", createDate);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        map.put("createUser", createUser);
        map.put("updateUser", updateUser);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("spreaderNum")) this.setSpreaderNum(DataTypeUtils.getStringValue(map.get("spreaderNum")));
        if (map.containsKey("spreaderName")) this.setSpreaderName(DataTypeUtils.getStringValue(map.get("spreaderName")));
        if (map.containsKey("spreaderPhone")) this.setSpreaderPhone(DataTypeUtils.getStringValue(map.get("spreaderPhone")));
        if (map.containsKey("spreaderPerformance")) this.setSpreaderPerformance(DataTypeUtils.getStringValue(map.get("spreaderPerformance")));
        if (map.containsKey("spreaderBankId")) this.setSpreaderBankId(DataTypeUtils.getStringValue(map.get("spreaderBankId")));
        if (map.containsKey("spreaderBankName")) this.setSpreaderBankName(DataTypeUtils.getStringValue(map.get("spreaderBankName")));
        if (map.containsKey("spreaderOrg")) this.setSpreaderOrg(DataTypeUtils.getStringValue(map.get("spreaderOrg")));
        if (map.containsKey("sprUserType")) this.setSprUserType(DataTypeUtils.getStringValue(map.get("sprUserType")));
        if (map.containsKey("spreaderStatus")) this.setSpreaderStatus(DataTypeUtils.getStringValue(map.get("spreaderStatus")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", spreaderNum="+spreaderNum+
        "spreaderNum="+spreaderNum+
        ", spreaderName="+spreaderName+
        "spreaderName="+spreaderName+
        ", spreaderPhone="+spreaderPhone+
        "spreaderPhone="+spreaderPhone+
        ", spreaderPerformance="+spreaderPerformance+
        "spreaderPerformance="+spreaderPerformance+
        ", spreaderBankId="+spreaderBankId+
        "spreaderBankId="+spreaderBankId+
        ", spreaderBankName="+spreaderBankName+
        "spreaderBankName="+spreaderBankName+
        ", spreaderOrg="+spreaderOrg+
        "spreaderOrg="+spreaderOrg+
        ", sprUserType="+sprUserType+
        "sprUserType="+sprUserType+
        ", spreaderStatus="+spreaderStatus+
        "spreaderStatus="+spreaderStatus+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (spreaderNum == null) spreaderNum = "";
        if (spreaderName == null) spreaderName = "";
        if (spreaderPhone == null) spreaderPhone = "";
        if (spreaderPerformance == null) spreaderPerformance = "";
        if (spreaderBankId == null) spreaderBankId = "";
        if (spreaderBankName == null) spreaderBankName = "";
        if (spreaderOrg == null) spreaderOrg = "";
        if (sprUserType == null) sprUserType = "";
        if (spreaderStatus == null) spreaderStatus = "";
        if (createDate == null) createDate = new Date();
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (createUser == null) createUser = "";
        if (updateUser == null) updateUser = "";
    }
}