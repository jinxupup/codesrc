package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请主表
 * @author jjb
 */
public class TmAppMain implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String appNo;

    private String name;

    private String idType;

    private String idNo;

    private String appType;

    private String rtfState;

    private String currOpResult;

    private BigDecimal appLmt;

    private BigDecimal sugLmt;

    private BigDecimal chkLmt;

    private BigDecimal finalLmt;

    private BigDecimal accLmt;

    private String productCd;

    private String appProperty;

    private Integer pointResult;

    private String refuseCode;

    private String appSource;

    private String owningBranch;

    private String appnoExternal;

    private String remark;

    private String taskNum;

    private String imageNum;

    private String cellphone;

    private String corpName;

    private String empPhone;

    private String taskLastOpUser;

    private String taskOwner;

    private String taskId;

    private String refuseCode2;

    private String refuseCode3;

    private String createUser;

    private Date createDate;

    private Date updateDate;

    private String updateUser;

    private Integer jpaVersion;

    private String fileFlag;

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
     * <p>证件类型</p>
     */
    public String getIdType() {
        return idType;
    }

    /**
     * <p>证件类型</p>
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * <p>证件号码</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>证件号码</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>申请类型</p>
     */
    public String getAppType() {
        return appType;
    }

    /**
     * <p>申请类型</p>
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * <p>审批状态</p>
     */
    public String getRtfState() {
        return rtfState;
    }

    /**
     * <p>审批状态</p>
     */
    public void setRtfState(String rtfState) {
        this.rtfState = rtfState;
    }

    /**
     * <p>最新审批操作</p>
     */
    public String getCurrOpResult() {
        return currOpResult;
    }

    /**
     * <p>最新审批操作</p>
     */
    public void setCurrOpResult(String currOpResult) {
        this.currOpResult = currOpResult;
    }

    /**
     * <p>申请额度</p>
     * <p>申请额度</p>
     */
    public BigDecimal getAppLmt() {
        return appLmt;
    }

    /**
     * <p>申请额度</p>
     * <p>申请额度</p>
     */
    public void setAppLmt(BigDecimal appLmt) {
        this.appLmt = appLmt;
    }

    /**
     * <p>系统建议额度</p>
     */
    public BigDecimal getSugLmt() {
        return sugLmt;
    }

    /**
     * <p>系统建议额度</p>
     */
    public void setSugLmt(BigDecimal sugLmt) {
        this.sugLmt = sugLmt;
    }

    /**
     * <p>初审额度</p>
     */
    public BigDecimal getChkLmt() {
        return chkLmt;
    }

    /**
     * <p>初审额度</p>
     */
    public void setChkLmt(BigDecimal chkLmt) {
        this.chkLmt = chkLmt;
    }

    /**
     * <p>终审额度</p>
     */
    public BigDecimal getFinalLmt() {
        return finalLmt;
    }

    /**
     * <p>终审额度</p>
     */
    public void setFinalLmt(BigDecimal finalLmt) {
        this.finalLmt = finalLmt;
    }

    /**
     * <p>核准额度</p>
     * <p>核准额度</p>
     */
    public BigDecimal getAccLmt() {
        return accLmt;
    }

    /**
     * <p>核准额度</p>
     * <p>核准额度</p>
     */
    public void setAccLmt(BigDecimal accLmt) {
        this.accLmt = accLmt;
    }

    /**
     * <p>卡产品代码</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>卡产品代码</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    /**
     * <p>进件属性</p>
     */
    public String getAppProperty() {
        return appProperty;
    }

    /**
     * <p>进件属性</p>
     */
    public void setAppProperty(String appProperty) {
        this.appProperty = appProperty;
    }

    /**
     * <p>评分值</p>
     */
    public Integer getPointResult() {
        return pointResult;
    }

    /**
     * <p>评分值</p>
     */
    public void setPointResult(Integer pointResult) {
        this.pointResult = pointResult;
    }

    /**
     * <p>拒绝原因码</p>
     */
    public String getRefuseCode() {
        return refuseCode;
    }

    /**
     * <p>拒绝原因码</p>
     */
    public void setRefuseCode(String refuseCode) {
        this.refuseCode = refuseCode;
    }

    /**
     * <p>申请渠道</p>
     */
    public String getAppSource() {
        return appSource;
    }

    /**
     * <p>申请渠道</p>
     */
    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    /**
     * <p>受理网点</p>
     */
    public String getOwningBranch() {
        return owningBranch;
    }

    /**
     * <p>受理网点</p>
     */
    public void setOwningBranch(String owningBranch) {
        this.owningBranch = owningBranch;
    }

    /**
     * <p>申请编号_外部送入</p>
     */
    public String getAppnoExternal() {
        return appnoExternal;
    }

    /**
     * <p>申请编号_外部送入</p>
     */
    public void setAppnoExternal(String appnoExternal) {
        this.appnoExternal = appnoExternal;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>行内任务编号</p>
     * <p>--一般与影像批次号一起使用</p>
     */
    public String getTaskNum() {
        return taskNum;
    }

    /**
     * <p>行内任务编号</p>
     * <p>--一般与影像批次号一起使用</p>
     */
    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * <p>影像批次号</p>
     */
    public String getImageNum() {
        return imageNum;
    }

    /**
     * <p>影像批次号</p>
     */
    public void setImageNum(String imageNum) {
        this.imageNum = imageNum;
    }

    /**
     * <p>移动电话</p>
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * <p>移动电话</p>
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * <p>公司名称</p>
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * <p>公司名称</p>
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * <p>公司电话</p>
     */
    public String getEmpPhone() {
        return empPhone;
    }

    /**
     * <p>公司电话</p>
     */
    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    /**
     * <p>上一任务人</p>
     */
    public String getTaskLastOpUser() {
        return taskLastOpUser;
    }

    /**
     * <p>上一任务人</p>
     */
    public void setTaskLastOpUser(String taskLastOpUser) {
        this.taskLastOpUser = taskLastOpUser;
    }

    /**
     * <p>任务所属人</p>
     */
    public String getTaskOwner() {
        return taskOwner;
    }

    /**
     * <p>任务所属人</p>
     */
    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    /**
     * <p>任务ID</p>
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * <p>任务ID</p>
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * <p>REFUSE_CODE2</p>
     */
    public String getRefuseCode2() {
        return refuseCode2;
    }

    /**
     * <p>REFUSE_CODE2</p>
     */
    public void setRefuseCode2(String refuseCode2) {
        this.refuseCode2 = refuseCode2;
    }

    /**
     * <p>REFUSE_CODE3</p>
     */
    public String getRefuseCode3() {
        return refuseCode3;
    }

    /**
     * <p>REFUSE_CODE3</p>
     */
    public void setRefuseCode3(String refuseCode3) {
        this.refuseCode3 = refuseCode3;
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

    /**
     * <p>归档标志</p>
     */
    public String getFileFlag() {
        return fileFlag;
    }

    /**
     * <p>归档标志</p>
     */
    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("name", name);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("appType", appType);
        map.put("rtfState", rtfState);
        map.put("currOpResult", currOpResult);
        map.put("appLmt", appLmt);
        map.put("sugLmt", sugLmt);
        map.put("chkLmt", chkLmt);
        map.put("finalLmt", finalLmt);
        map.put("accLmt", accLmt);
        map.put("productCd", productCd);
        map.put("appProperty", appProperty);
        map.put("pointResult", pointResult);
        map.put("refuseCode", refuseCode);
        map.put("appSource", appSource);
        map.put("owningBranch", owningBranch);
        map.put("appnoExternal", appnoExternal);
        map.put("remark", remark);
        map.put("taskNum", taskNum);
        map.put("imageNum", imageNum);
        map.put("cellphone", cellphone);
        map.put("corpName", corpName);
        map.put("empPhone", empPhone);
        map.put("taskLastOpUser", taskLastOpUser);
        map.put("taskOwner", taskOwner);
        map.put("taskId", taskId);
        map.put("refuseCode2", refuseCode2);
        map.put("refuseCode3", refuseCode3);
        map.put("createUser", createUser);
        map.put("createDate", createDate);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        map.put("fileFlag", fileFlag);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("appType")) this.setAppType(DataTypeUtils.getStringValue(map.get("appType")));
        if (map.containsKey("rtfState")) this.setRtfState(DataTypeUtils.getStringValue(map.get("rtfState")));
        if (map.containsKey("currOpResult")) this.setCurrOpResult(DataTypeUtils.getStringValue(map.get("currOpResult")));
        if (map.containsKey("appLmt")) this.setAppLmt(DataTypeUtils.getBigDecimalValue(map.get("appLmt")));
        if (map.containsKey("sugLmt")) this.setSugLmt(DataTypeUtils.getBigDecimalValue(map.get("sugLmt")));
        if (map.containsKey("chkLmt")) this.setChkLmt(DataTypeUtils.getBigDecimalValue(map.get("chkLmt")));
        if (map.containsKey("finalLmt")) this.setFinalLmt(DataTypeUtils.getBigDecimalValue(map.get("finalLmt")));
        if (map.containsKey("accLmt")) this.setAccLmt(DataTypeUtils.getBigDecimalValue(map.get("accLmt")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("appProperty")) this.setAppProperty(DataTypeUtils.getStringValue(map.get("appProperty")));
        if (map.containsKey("pointResult")) this.setPointResult(DataTypeUtils.getIntegerValue(map.get("pointResult")));
        if (map.containsKey("refuseCode")) this.setRefuseCode(DataTypeUtils.getStringValue(map.get("refuseCode")));
        if (map.containsKey("appSource")) this.setAppSource(DataTypeUtils.getStringValue(map.get("appSource")));
        if (map.containsKey("owningBranch")) this.setOwningBranch(DataTypeUtils.getStringValue(map.get("owningBranch")));
        if (map.containsKey("appnoExternal")) this.setAppnoExternal(DataTypeUtils.getStringValue(map.get("appnoExternal")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("taskNum")) this.setTaskNum(DataTypeUtils.getStringValue(map.get("taskNum")));
        if (map.containsKey("imageNum")) this.setImageNum(DataTypeUtils.getStringValue(map.get("imageNum")));
        if (map.containsKey("cellphone")) this.setCellphone(DataTypeUtils.getStringValue(map.get("cellphone")));
        if (map.containsKey("corpName")) this.setCorpName(DataTypeUtils.getStringValue(map.get("corpName")));
        if (map.containsKey("empPhone")) this.setEmpPhone(DataTypeUtils.getStringValue(map.get("empPhone")));
        if (map.containsKey("taskLastOpUser")) this.setTaskLastOpUser(DataTypeUtils.getStringValue(map.get("taskLastOpUser")));
        if (map.containsKey("taskOwner")) this.setTaskOwner(DataTypeUtils.getStringValue(map.get("taskOwner")));
        if (map.containsKey("taskId")) this.setTaskId(DataTypeUtils.getStringValue(map.get("taskId")));
        if (map.containsKey("refuseCode2")) this.setRefuseCode2(DataTypeUtils.getStringValue(map.get("refuseCode2")));
        if (map.containsKey("refuseCode3")) this.setRefuseCode3(DataTypeUtils.getStringValue(map.get("refuseCode3")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("fileFlag")) this.setFileFlag(DataTypeUtils.getStringValue(map.get("fileFlag")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", name="+name+
        "name="+name+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", appType="+appType+
        "appType="+appType+
        ", rtfState="+rtfState+
        "rtfState="+rtfState+
        ", currOpResult="+currOpResult+
        "currOpResult="+currOpResult+
        ", appLmt="+appLmt+
        "appLmt="+appLmt+
        ", sugLmt="+sugLmt+
        "sugLmt="+sugLmt+
        ", chkLmt="+chkLmt+
        "chkLmt="+chkLmt+
        ", finalLmt="+finalLmt+
        "finalLmt="+finalLmt+
        ", accLmt="+accLmt+
        "accLmt="+accLmt+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", appProperty="+appProperty+
        "appProperty="+appProperty+
        ", pointResult="+pointResult+
        "pointResult="+pointResult+
        ", refuseCode="+refuseCode+
        "refuseCode="+refuseCode+
        ", appSource="+appSource+
        "appSource="+appSource+
        ", owningBranch="+owningBranch+
        "owningBranch="+owningBranch+
        ", appnoExternal="+appnoExternal+
        "appnoExternal="+appnoExternal+
        ", remark="+remark+
        "remark="+remark+
        ", taskNum="+taskNum+
        "taskNum="+taskNum+
        ", imageNum="+imageNum+
        "imageNum="+imageNum+
        ", cellphone="+cellphone+
        "cellphone="+cellphone+
        ", corpName="+corpName+
        "corpName="+corpName+
        ", empPhone="+empPhone+
        "empPhone="+empPhone+
        ", taskLastOpUser="+taskLastOpUser+
        "taskLastOpUser="+taskLastOpUser+
        ", taskOwner="+taskOwner+
        "taskOwner="+taskOwner+
        ", taskId="+taskId+
        "taskId="+taskId+
        ", refuseCode2="+refuseCode2+
        "refuseCode2="+refuseCode2+
        ", refuseCode3="+refuseCode3+
        "refuseCode3="+refuseCode3+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", fileFlag="+fileFlag+
        "fileFlag="+fileFlag+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (name == null) name = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (appType == null) appType = "";
        if (rtfState == null) rtfState = "";
        if (currOpResult == null) currOpResult = "";
        if (appLmt == null) appLmt = BigDecimal.ZERO;
        if (sugLmt == null) sugLmt = BigDecimal.ZERO;
        if (chkLmt == null) chkLmt = BigDecimal.ZERO;
        if (finalLmt == null) finalLmt = BigDecimal.ZERO;
        if (accLmt == null) accLmt = BigDecimal.ZERO;
        if (productCd == null) productCd = "";
        if (appProperty == null) appProperty = "";
        if (pointResult == null) pointResult = 0;
        if (refuseCode == null) refuseCode = "";
        if (appSource == null) appSource = "";
        if (owningBranch == null) owningBranch = "";
        if (appnoExternal == null) appnoExternal = "";
        if (remark == null) remark = "";
        if (taskNum == null) taskNum = "";
        if (imageNum == null) imageNum = "";
        if (cellphone == null) cellphone = "";
        if (corpName == null) corpName = "";
        if (empPhone == null) empPhone = "";
        if (taskLastOpUser == null) taskLastOpUser = "";
        if (taskOwner == null) taskOwner = "";
        if (taskId == null) taskId = "";
        if (refuseCode2 == null) refuseCode2 = "";
        if (refuseCode3 == null) refuseCode3 = "";
        if (createUser == null) createUser = "";
        if (createDate == null) createDate = new Date();
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (fileFlag == null) fileFlag = "";
    }
}