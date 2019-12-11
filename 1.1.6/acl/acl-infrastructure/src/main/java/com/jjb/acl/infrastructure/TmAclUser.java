package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TM_ACL_USER用户
 * @author jjb
 */
public class TmAclUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private Integer userId;

    private String password;

    private String salt;

    private String userNo;

    private String status;

    private String userName;

    private String branchCode;

    private Date stateUpdate;

    private String workStatus;

    private Date workStatusUpdate;

    private Date workStatusUpdateEnd;

    private String userType;

    private String loginState;

    private Date loginTime;

    private Date logoutTime;

    private String logoutType;

    private String loginIp;

    private String sessionId;

    private Integer retry;

    private String idNo;

    private String ctryCd;

    private String province;

    private String city;

    private String zone;

    private String empAdd;

    private String phone;

    private String depapment;

    private String post;

    private String email;

    private String cellphome;

    private Date passwordUpdate;

    private Date passwordExpireDate;

    private String remark;

    private String textObject1;

    private String textObject2;

    private String textObject3;

    private String textObject4;

    private String textObject5;

    private String textObject6;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Integer jpaVersion;

    /**
     * <p>ORG</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>ORG</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>用户ID</p>
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * <p>用户ID</p>
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * <p>密码</p>
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>密码</p>
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>密码盐</p>
     */
    public String getSalt() {
        return salt;
    }

    /**
     * <p>密码盐</p>
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * <p>登陆名</p>
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * <p>登陆名</p>
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * <p>用户状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>用户状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>姓名</p>
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>姓名</p>
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * <p>所属分支机构</p>
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * <p>所属分支机构</p>
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * <p>用户状态变更时间</p>
     */
    public Date getStateUpdate() {
        return stateUpdate;
    }

    /**
     * <p>用户状态变更时间</p>
     */
    public void setStateUpdate(Date stateUpdate) {
        this.stateUpdate = stateUpdate;
    }

    /**
     * <p>工作状态</p>
     */
    public String getWorkStatus() {
        return workStatus;
    }

    /**
     * <p>工作状态</p>
     */
    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    /**
     * <p>工作状态变更时间</p>
     */
    public Date getWorkStatusUpdate() {
        return workStatusUpdate;
    }

    /**
     * <p>工作状态变更时间</p>
     */
    public void setWorkStatusUpdate(Date workStatusUpdate) {
        this.workStatusUpdate = workStatusUpdate;
    }

    /**
     * <p>工作状态变更结束时间</p>
     */
    public Date getWorkStatusUpdateEnd() {
        return workStatusUpdateEnd;
    }

    /**
     * <p>工作状态变更结束时间</p>
     */
    public void setWorkStatusUpdateEnd(Date workStatusUpdateEnd) {
        this.workStatusUpdateEnd = workStatusUpdateEnd;
    }

    /**
     * <p>用户类型</p>
     */
    public String getUserType() {
        return userType;
    }

    /**
     * <p>用户类型</p>
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * <p>登录状态</p>
     */
    public String getLoginState() {
        return loginState;
    }

    /**
     * <p>登录状态</p>
     */
    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    /**
     * <p>登陆时间</p>
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * <p>登陆时间</p>
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * <p>登出时间</p>
     */
    public Date getLogoutTime() {
        return logoutTime;
    }

    /**
     * <p>登出时间</p>
     */
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    /**
     * <p>登出类型</p>
     */
    public String getLogoutType() {
        return logoutType;
    }

    /**
     * <p>登出类型</p>
     */
    public void setLogoutType(String logoutType) {
        this.logoutType = logoutType;
    }

    /**
     * <p>登陆IP</p>
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * <p>登陆IP</p>
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * <p>SESSION_ID</p>
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * <p>SESSION_ID</p>
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * <p>密码错误次数</p>
     */
    public Integer getRetry() {
        return retry;
    }

    /**
     * <p>密码错误次数</p>
     */
    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    /**
     * <p>身份证号</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>身份证号</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>国家代码</p>
     */
    public String getCtryCd() {
        return ctryCd;
    }

    /**
     * <p>国家代码</p>
     */
    public void setCtryCd(String ctryCd) {
        this.ctryCd = ctryCd;
    }

    /**
     * <p>省</p>
     */
    public String getProvince() {
        return province;
    }

    /**
     * <p>省</p>
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * <p>市</p>
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>市</p>
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>区/县</p>
     */
    public String getZone() {
        return zone;
    }

    /**
     * <p>区/县</p>
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * <p>地址</p>
     */
    public String getEmpAdd() {
        return empAdd;
    }

    /**
     * <p>地址</p>
     */
    public void setEmpAdd(String empAdd) {
        this.empAdd = empAdd;
    }

    /**
     * <p>办公电话</p>
     */
    public String getPhone() {
        return phone;
    }

    /**
     * <p>办公电话</p>
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * <p>任职部门</p>
     */
    public String getDepapment() {
        return depapment;
    }

    /**
     * <p>任职部门</p>
     */
    public void setDepapment(String depapment) {
        this.depapment = depapment;
    }

    /**
     * <p>职务</p>
     */
    public String getPost() {
        return post;
    }

    /**
     * <p>职务</p>
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * <p>EMAIL</p>
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>EMAIL</p>
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>手机</p>
     */
    public String getCellphome() {
        return cellphome;
    }

    /**
     * <p>手机</p>
     */
    public void setCellphome(String cellphome) {
        this.cellphome = cellphome;
    }

    /**
     * <p>密码最近更新日期</p>
     */
    public Date getPasswordUpdate() {
        return passwordUpdate;
    }

    /**
     * <p>密码最近更新日期</p>
     */
    public void setPasswordUpdate(Date passwordUpdate) {
        this.passwordUpdate = passwordUpdate;
    }

    /**
     * <p>密码到期日</p>
     */
    public Date getPasswordExpireDate() {
        return passwordExpireDate;
    }

    /**
     * <p>密码到期日</p>
     */
    public void setPasswordExpireDate(Date passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    /**
     * <p>说明</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>说明</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>预留字段1</p>
     */
    public String getTextObject1() {
        return textObject1;
    }

    /**
     * <p>预留字段1</p>
     */
    public void setTextObject1(String textObject1) {
        this.textObject1 = textObject1;
    }

    /**
     * <p>预留字段2</p>
     */
    public String getTextObject2() {
        return textObject2;
    }

    /**
     * <p>预留字段2</p>
     */
    public void setTextObject2(String textObject2) {
        this.textObject2 = textObject2;
    }

    /**
     * <p>预留字段3</p>
     */
    public String getTextObject3() {
        return textObject3;
    }

    /**
     * <p>预留字段3</p>
     */
    public void setTextObject3(String textObject3) {
        this.textObject3 = textObject3;
    }

    /**
     * <p>预留字段4</p>
     */
    public String getTextObject4() {
        return textObject4;
    }

    /**
     * <p>预留字段4</p>
     */
    public void setTextObject4(String textObject4) {
        this.textObject4 = textObject4;
    }

    /**
     * <p>预留字段5</p>
     */
    public String getTextObject5() {
        return textObject5;
    }

    /**
     * <p>预留字段5</p>
     */
    public void setTextObject5(String textObject5) {
        this.textObject5 = textObject5;
    }

    /**
     * <p>预留字段6</p>
     */
    public String getTextObject6() {
        return textObject6;
    }

    /**
     * <p>预留字段6</p>
     */
    public void setTextObject6(String textObject6) {
        this.textObject6 = textObject6;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>创建用户</p>
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * <p>创建用户</p>
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * <p>维护时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>维护时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>修改用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>修改用户</p>
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * <p>版本</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>版本</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("userId", userId);
        map.put("password", password);
        map.put("salt", salt);
        map.put("userNo", userNo);
        map.put("status", status);
        map.put("userName", userName);
        map.put("branchCode", branchCode);
        map.put("stateUpdate", stateUpdate);
        map.put("workStatus", workStatus);
        map.put("workStatusUpdate", workStatusUpdate);
        map.put("workStatusUpdateEnd", workStatusUpdateEnd);
        map.put("userType", userType);
        map.put("loginState", loginState);
        map.put("loginTime", loginTime);
        map.put("logoutTime", logoutTime);
        map.put("logoutType", logoutType);
        map.put("loginIp", loginIp);
        map.put("sessionId", sessionId);
        map.put("retry", retry);
        map.put("idNo", idNo);
        map.put("ctryCd", ctryCd);
        map.put("province", province);
        map.put("city", city);
        map.put("zone", zone);
        map.put("empAdd", empAdd);
        map.put("phone", phone);
        map.put("depapment", depapment);
        map.put("post", post);
        map.put("email", email);
        map.put("cellphome", cellphome);
        map.put("passwordUpdate", passwordUpdate);
        map.put("passwordExpireDate", passwordExpireDate);
        map.put("remark", remark);
        map.put("textObject1", textObject1);
        map.put("textObject2", textObject2);
        map.put("textObject3", textObject3);
        map.put("textObject4", textObject4);
        map.put("textObject5", textObject5);
        map.put("textObject6", textObject6);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("userId")) this.setUserId(DataTypeUtils.getIntegerValue(map.get("userId")));
        if (map.containsKey("password")) this.setPassword(DataTypeUtils.getStringValue(map.get("password")));
        if (map.containsKey("salt")) this.setSalt(DataTypeUtils.getStringValue(map.get("salt")));
        if (map.containsKey("userNo")) this.setUserNo(DataTypeUtils.getStringValue(map.get("userNo")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("userName")) this.setUserName(DataTypeUtils.getStringValue(map.get("userName")));
        if (map.containsKey("branchCode")) this.setBranchCode(DataTypeUtils.getStringValue(map.get("branchCode")));
        if (map.containsKey("stateUpdate")) this.setStateUpdate(DataTypeUtils.getDateValue(map.get("stateUpdate")));
        if (map.containsKey("workStatus")) this.setWorkStatus(DataTypeUtils.getStringValue(map.get("workStatus")));
        if (map.containsKey("workStatusUpdate")) this.setWorkStatusUpdate(DataTypeUtils.getDateValue(map.get("workStatusUpdate")));
        if (map.containsKey("workStatusUpdateEnd")) this.setWorkStatusUpdateEnd(DataTypeUtils.getDateValue(map.get("workStatusUpdateEnd")));
        if (map.containsKey("userType")) this.setUserType(DataTypeUtils.getStringValue(map.get("userType")));
        if (map.containsKey("loginState")) this.setLoginState(DataTypeUtils.getStringValue(map.get("loginState")));
        if (map.containsKey("loginTime")) this.setLoginTime(DataTypeUtils.getDateValue(map.get("loginTime")));
        if (map.containsKey("logoutTime")) this.setLogoutTime(DataTypeUtils.getDateValue(map.get("logoutTime")));
        if (map.containsKey("logoutType")) this.setLogoutType(DataTypeUtils.getStringValue(map.get("logoutType")));
        if (map.containsKey("loginIp")) this.setLoginIp(DataTypeUtils.getStringValue(map.get("loginIp")));
        if (map.containsKey("sessionId")) this.setSessionId(DataTypeUtils.getStringValue(map.get("sessionId")));
        if (map.containsKey("retry")) this.setRetry(DataTypeUtils.getIntegerValue(map.get("retry")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("ctryCd")) this.setCtryCd(DataTypeUtils.getStringValue(map.get("ctryCd")));
        if (map.containsKey("province")) this.setProvince(DataTypeUtils.getStringValue(map.get("province")));
        if (map.containsKey("city")) this.setCity(DataTypeUtils.getStringValue(map.get("city")));
        if (map.containsKey("zone")) this.setZone(DataTypeUtils.getStringValue(map.get("zone")));
        if (map.containsKey("empAdd")) this.setEmpAdd(DataTypeUtils.getStringValue(map.get("empAdd")));
        if (map.containsKey("phone")) this.setPhone(DataTypeUtils.getStringValue(map.get("phone")));
        if (map.containsKey("depapment")) this.setDepapment(DataTypeUtils.getStringValue(map.get("depapment")));
        if (map.containsKey("post")) this.setPost(DataTypeUtils.getStringValue(map.get("post")));
        if (map.containsKey("email")) this.setEmail(DataTypeUtils.getStringValue(map.get("email")));
        if (map.containsKey("cellphome")) this.setCellphome(DataTypeUtils.getStringValue(map.get("cellphome")));
        if (map.containsKey("passwordUpdate")) this.setPasswordUpdate(DataTypeUtils.getDateValue(map.get("passwordUpdate")));
        if (map.containsKey("passwordExpireDate")) this.setPasswordExpireDate(DataTypeUtils.getDateValue(map.get("passwordExpireDate")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("textObject1")) this.setTextObject1(DataTypeUtils.getStringValue(map.get("textObject1")));
        if (map.containsKey("textObject2")) this.setTextObject2(DataTypeUtils.getStringValue(map.get("textObject2")));
        if (map.containsKey("textObject3")) this.setTextObject3(DataTypeUtils.getStringValue(map.get("textObject3")));
        if (map.containsKey("textObject4")) this.setTextObject4(DataTypeUtils.getStringValue(map.get("textObject4")));
        if (map.containsKey("textObject5")) this.setTextObject5(DataTypeUtils.getStringValue(map.get("textObject5")));
        if (map.containsKey("textObject6")) this.setTextObject6(DataTypeUtils.getStringValue(map.get("textObject6")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createBy")) this.setCreateBy(DataTypeUtils.getStringValue(map.get("createBy")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateBy")) this.setUpdateBy(DataTypeUtils.getStringValue(map.get("updateBy")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", userId="+userId+
        "userId="+userId+
        ", password="+password+
        "password="+password+
        ", salt="+salt+
        "salt="+salt+
        ", userNo="+userNo+
        "userNo="+userNo+
        ", status="+status+
        "status="+status+
        ", userName="+userName+
        "userName="+userName+
        ", branchCode="+branchCode+
        "branchCode="+branchCode+
        ", stateUpdate="+stateUpdate+
        "stateUpdate="+stateUpdate+
        ", workStatus="+workStatus+
        "workStatus="+workStatus+
        ", workStatusUpdate="+workStatusUpdate+
        "workStatusUpdate="+workStatusUpdate+
        ", workStatusUpdateEnd="+workStatusUpdateEnd+
        "workStatusUpdateEnd="+workStatusUpdateEnd+
        ", userType="+userType+
        "userType="+userType+
        ", loginState="+loginState+
        "loginState="+loginState+
        ", loginTime="+loginTime+
        "loginTime="+loginTime+
        ", logoutTime="+logoutTime+
        "logoutTime="+logoutTime+
        ", logoutType="+logoutType+
        "logoutType="+logoutType+
        ", loginIp="+loginIp+
        "loginIp="+loginIp+
        ", sessionId="+sessionId+
        "sessionId="+sessionId+
        ", retry="+retry+
        "retry="+retry+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", ctryCd="+ctryCd+
        "ctryCd="+ctryCd+
        ", province="+province+
        "province="+province+
        ", city="+city+
        "city="+city+
        ", zone="+zone+
        "zone="+zone+
        ", empAdd="+empAdd+
        "empAdd="+empAdd+
        ", phone="+phone+
        "phone="+phone+
        ", depapment="+depapment+
        "depapment="+depapment+
        ", post="+post+
        "post="+post+
        ", email="+email+
        "email="+email+
        ", cellphome="+cellphome+
        "cellphome="+cellphome+
        ", passwordUpdate="+passwordUpdate+
        "passwordUpdate="+passwordUpdate+
        ", passwordExpireDate="+passwordExpireDate+
        "passwordExpireDate="+passwordExpireDate+
        ", remark="+remark+
        "remark="+remark+
        ", textObject1="+textObject1+
        "textObject1="+textObject1+
        ", textObject2="+textObject2+
        "textObject2="+textObject2+
        ", textObject3="+textObject3+
        "textObject3="+textObject3+
        ", textObject4="+textObject4+
        "textObject4="+textObject4+
        ", textObject5="+textObject5+
        "textObject5="+textObject5+
        ", textObject6="+textObject6+
        "textObject6="+textObject6+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createBy="+createBy+
        "createBy="+createBy+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateBy="+updateBy+
        "updateBy="+updateBy+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (password == null) password = "";
        if (salt == null) salt = "";
        if (userNo == null) userNo = "";
        if (status == null) status = "";
        if (userName == null) userName = "";
        if (branchCode == null) branchCode = "";
        if (stateUpdate == null) stateUpdate = new Date();
        if (workStatus == null) workStatus = "";
        if (workStatusUpdate == null) workStatusUpdate = new Date();
        if (workStatusUpdateEnd == null) workStatusUpdateEnd = new Date();
        if (userType == null) userType = "";
        if (loginState == null) loginState = "";
        if (loginTime == null) loginTime = new Date();
        if (logoutTime == null) logoutTime = new Date();
        if (logoutType == null) logoutType = "";
        if (loginIp == null) loginIp = "";
        if (sessionId == null) sessionId = "";
        if (retry == null) retry = 0;
        if (idNo == null) idNo = "";
        if (ctryCd == null) ctryCd = "";
        if (province == null) province = "";
        if (city == null) city = "";
        if (zone == null) zone = "";
        if (empAdd == null) empAdd = "";
        if (phone == null) phone = "";
        if (depapment == null) depapment = "";
        if (post == null) post = "";
        if (email == null) email = "";
        if (cellphome == null) cellphome = "";
        if (passwordUpdate == null) passwordUpdate = new Date();
        if (passwordExpireDate == null) passwordExpireDate = new Date();
        if (remark == null) remark = "";
        if (textObject1 == null) textObject1 = "";
        if (textObject2 == null) textObject2 = "";
        if (textObject3 == null) textObject3 = "";
        if (textObject4 == null) textObject4 = "";
        if (textObject5 == null) textObject5 = "";
        if (textObject6 == null) textObject6 = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}