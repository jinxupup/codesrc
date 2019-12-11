package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 联系人信息表
 * @author jjb
 */
public class TmAppContact implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String contactType;

    private String contactName;

    private String contactRelation;

    private String contactMobile;

    private String contactTelephone;

    private String contactEmpPhone;

    private String contactIdType;

    private String contactIdNo;

    private Date contactBirthday;

    private String contactCorpPost;

    private String contactCorpFax;

    private String contactEmpName;

    private String contactGender;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private Integer jpaVersion;

    /**
     * <p>标识</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标识</p>
     */
    public void setId(Integer id) {
        this.id = id;
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
     * <p>联系人类型</p>
     */
    public String getContactType() {
        return contactType;
    }

    /**
     * <p>联系人类型</p>
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    /**
     * <p>联系人中文姓名</p>
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * <p>联系人中文姓名</p>
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * <p>联系人与申请人关系</p>
     */
    public String getContactRelation() {
        return contactRelation;
    }

    /**
     * <p>联系人与申请人关系</p>
     */
    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    /**
     * <p>联系人移动电话</p>
     */
    public String getContactMobile() {
        return contactMobile;
    }

    /**
     * <p>联系人移动电话</p>
     */
    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    /**
     * <p>联系人联系电话</p>
     */
    public String getContactTelephone() {
        return contactTelephone;
    }

    /**
     * <p>联系人联系电话</p>
     */
    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    /**
     * <p>联系人公司电话号</p>
     */
    public String getContactEmpPhone() {
        return contactEmpPhone;
    }

    /**
     * <p>联系人公司电话号</p>
     */
    public void setContactEmpPhone(String contactEmpPhone) {
        this.contactEmpPhone = contactEmpPhone;
    }

    /**
     * <p>联系人身份证件类型</p>
     */
    public String getContactIdType() {
        return contactIdType;
    }

    /**
     * <p>联系人身份证件类型</p>
     */
    public void setContactIdType(String contactIdType) {
        this.contactIdType = contactIdType;
    }

    /**
     * <p>联系人证件号</p>
     */
    public String getContactIdNo() {
        return contactIdNo;
    }

    /**
     * <p>联系人证件号</p>
     */
    public void setContactIdNo(String contactIdNo) {
        this.contactIdNo = contactIdNo;
    }

    /**
     * <p>联系人生日</p>
     */
    public Date getContactBirthday() {
        return contactBirthday;
    }

    /**
     * <p>联系人生日</p>
     */
    public void setContactBirthday(Date contactBirthday) {
        this.contactBirthday = contactBirthday;
    }

    /**
     * <p>联系人公司职务</p>
     */
    public String getContactCorpPost() {
        return contactCorpPost;
    }

    /**
     * <p>联系人公司职务</p>
     */
    public void setContactCorpPost(String contactCorpPost) {
        this.contactCorpPost = contactCorpPost;
    }

    /**
     * <p>联系人公司传真</p>
     */
    public String getContactCorpFax() {
        return contactCorpFax;
    }

    /**
     * <p>联系人公司传真</p>
     */
    public void setContactCorpFax(String contactCorpFax) {
        this.contactCorpFax = contactCorpFax;
    }

    /**
     * <p>联系人公司名称</p>
     */
    public String getContactEmpName() {
        return contactEmpName;
    }

    /**
     * <p>联系人公司名称</p>
     */
    public void setContactEmpName(String contactEmpName) {
        this.contactEmpName = contactEmpName;
    }

    /**
     * <p>联系人性别</p>
     */
    public String getContactGender() {
        return contactGender;
    }

    /**
     * <p>联系人性别</p>
     */
    public void setContactGender(String contactGender) {
        this.contactGender = contactGender;
    }

    /**
     * <p>创建日期</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建日期</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
     * <p>修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>修改人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>修改人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("contactType", contactType);
        map.put("contactName", contactName);
        map.put("contactRelation", contactRelation);
        map.put("contactMobile", contactMobile);
        map.put("contactTelephone", contactTelephone);
        map.put("contactEmpPhone", contactEmpPhone);
        map.put("contactIdType", contactIdType);
        map.put("contactIdNo", contactIdNo);
        map.put("contactBirthday", contactBirthday);
        map.put("contactCorpPost", contactCorpPost);
        map.put("contactCorpFax", contactCorpFax);
        map.put("contactEmpName", contactEmpName);
        map.put("contactGender", contactGender);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("contactType")) this.setContactType(DataTypeUtils.getStringValue(map.get("contactType")));
        if (map.containsKey("contactName")) this.setContactName(DataTypeUtils.getStringValue(map.get("contactName")));
        if (map.containsKey("contactRelation")) this.setContactRelation(DataTypeUtils.getStringValue(map.get("contactRelation")));
        if (map.containsKey("contactMobile")) this.setContactMobile(DataTypeUtils.getStringValue(map.get("contactMobile")));
        if (map.containsKey("contactTelephone")) this.setContactTelephone(DataTypeUtils.getStringValue(map.get("contactTelephone")));
        if (map.containsKey("contactEmpPhone")) this.setContactEmpPhone(DataTypeUtils.getStringValue(map.get("contactEmpPhone")));
        if (map.containsKey("contactIdType")) this.setContactIdType(DataTypeUtils.getStringValue(map.get("contactIdType")));
        if (map.containsKey("contactIdNo")) this.setContactIdNo(DataTypeUtils.getStringValue(map.get("contactIdNo")));
        if (map.containsKey("contactBirthday")) this.setContactBirthday(DataTypeUtils.getDateValue(map.get("contactBirthday")));
        if (map.containsKey("contactCorpPost")) this.setContactCorpPost(DataTypeUtils.getStringValue(map.get("contactCorpPost")));
        if (map.containsKey("contactCorpFax")) this.setContactCorpFax(DataTypeUtils.getStringValue(map.get("contactCorpFax")));
        if (map.containsKey("contactEmpName")) this.setContactEmpName(DataTypeUtils.getStringValue(map.get("contactEmpName")));
        if (map.containsKey("contactGender")) this.setContactGender(DataTypeUtils.getStringValue(map.get("contactGender")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", contactType="+contactType+
        "contactType="+contactType+
        ", contactName="+contactName+
        "contactName="+contactName+
        ", contactRelation="+contactRelation+
        "contactRelation="+contactRelation+
        ", contactMobile="+contactMobile+
        "contactMobile="+contactMobile+
        ", contactTelephone="+contactTelephone+
        "contactTelephone="+contactTelephone+
        ", contactEmpPhone="+contactEmpPhone+
        "contactEmpPhone="+contactEmpPhone+
        ", contactIdType="+contactIdType+
        "contactIdType="+contactIdType+
        ", contactIdNo="+contactIdNo+
        "contactIdNo="+contactIdNo+
        ", contactBirthday="+contactBirthday+
        "contactBirthday="+contactBirthday+
        ", contactCorpPost="+contactCorpPost+
        "contactCorpPost="+contactCorpPost+
        ", contactCorpFax="+contactCorpFax+
        "contactCorpFax="+contactCorpFax+
        ", contactEmpName="+contactEmpName+
        "contactEmpName="+contactEmpName+
        ", contactGender="+contactGender+
        "contactGender="+contactGender+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (contactType == null) contactType = "";
        if (contactName == null) contactName = "";
        if (contactRelation == null) contactRelation = "";
        if (contactMobile == null) contactMobile = "";
        if (contactTelephone == null) contactTelephone = "";
        if (contactEmpPhone == null) contactEmpPhone = "";
        if (contactIdType == null) contactIdType = "";
        if (contactIdNo == null) contactIdNo = "";
        if (contactBirthday == null) contactBirthday = new Date();
        if (contactCorpPost == null) contactCorpPost = "";
        if (contactCorpFax == null) contactCorpFax = "";
        if (contactEmpName == null) contactEmpName = "";
        if (contactGender == null) contactGender = "";
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}