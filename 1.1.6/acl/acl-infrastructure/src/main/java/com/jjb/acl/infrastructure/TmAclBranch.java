package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 网点机构
 * @author jjb
 */
public class TmAclBranch implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String branchCode;

    private String branchName;

    private String parentCode;

    private String parentPath;

    private String branchLevel;

    private Integer branchSequence;

    private String status;

    private Integer sort;

    private String empAdd;

    private String remark;

    private String branchIssueInd;

    private String cardCollectInd;

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
     * <p>机构代码</p>
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * <p>机构代码</p>
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * <p>机构名称</p>
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * <p>机构名称</p>
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * <p>上级机构代码</p>
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * <p>上级机构代码</p>
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * <p>机构路径</p>
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * <p>机构路径</p>
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    /**
     * <p>层级</p>
     */
    public String getBranchLevel() {
        return branchLevel;
    }

    /**
     * <p>层级</p>
     */
    public void setBranchLevel(String branchLevel) {
        this.branchLevel = branchLevel;
    }

    /**
     * <p>机构序号</p>
     */
    public Integer getBranchSequence() {
        return branchSequence;
    }

    /**
     * <p>机构序号</p>
     */
    public void setBranchSequence(Integer branchSequence) {
        this.branchSequence = branchSequence;
    }

    /**
     * <p>状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>排序</p>
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * <p>排序</p>
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
     * <p>发卡权限标识</p>
     */
    public String getBranchIssueInd() {
        return branchIssueInd;
    }

    /**
     * <p>发卡权限标识</p>
     */
    public void setBranchIssueInd(String branchIssueInd) {
        this.branchIssueInd = branchIssueInd;
    }

    /**
     * <p>领卡分行标识</p>
     */
    public String getCardCollectInd() {
        return cardCollectInd;
    }

    /**
     * <p>领卡分行标识</p>
     */
    public void setCardCollectInd(String cardCollectInd) {
        this.cardCollectInd = cardCollectInd;
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
     * <p>维护用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>维护用户</p>
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
        map.put("branchCode", branchCode);
        map.put("branchName", branchName);
        map.put("parentCode", parentCode);
        map.put("parentPath", parentPath);
        map.put("branchLevel", branchLevel);
        map.put("branchSequence", branchSequence);
        map.put("status", status);
        map.put("sort", sort);
        map.put("empAdd", empAdd);
        map.put("remark", remark);
        map.put("branchIssueInd", branchIssueInd);
        map.put("cardCollectInd", cardCollectInd);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("branchCode")) this.setBranchCode(DataTypeUtils.getStringValue(map.get("branchCode")));
        if (map.containsKey("branchName")) this.setBranchName(DataTypeUtils.getStringValue(map.get("branchName")));
        if (map.containsKey("parentCode")) this.setParentCode(DataTypeUtils.getStringValue(map.get("parentCode")));
        if (map.containsKey("parentPath")) this.setParentPath(DataTypeUtils.getStringValue(map.get("parentPath")));
        if (map.containsKey("branchLevel")) this.setBranchLevel(DataTypeUtils.getStringValue(map.get("branchLevel")));
        if (map.containsKey("branchSequence")) this.setBranchSequence(DataTypeUtils.getIntegerValue(map.get("branchSequence")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("sort")) this.setSort(DataTypeUtils.getIntegerValue(map.get("sort")));
        if (map.containsKey("empAdd")) this.setEmpAdd(DataTypeUtils.getStringValue(map.get("empAdd")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("branchIssueInd")) this.setBranchIssueInd(DataTypeUtils.getStringValue(map.get("branchIssueInd")));
        if (map.containsKey("cardCollectInd")) this.setCardCollectInd(DataTypeUtils.getStringValue(map.get("cardCollectInd")));
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
        ", branchCode="+branchCode+
        "branchCode="+branchCode+
        ", branchName="+branchName+
        "branchName="+branchName+
        ", parentCode="+parentCode+
        "parentCode="+parentCode+
        ", parentPath="+parentPath+
        "parentPath="+parentPath+
        ", branchLevel="+branchLevel+
        "branchLevel="+branchLevel+
        ", branchSequence="+branchSequence+
        "branchSequence="+branchSequence+
        ", status="+status+
        "status="+status+
        ", sort="+sort+
        "sort="+sort+
        ", empAdd="+empAdd+
        "empAdd="+empAdd+
        ", remark="+remark+
        "remark="+remark+
        ", branchIssueInd="+branchIssueInd+
        "branchIssueInd="+branchIssueInd+
        ", cardCollectInd="+cardCollectInd+
        "cardCollectInd="+cardCollectInd+
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
        if (branchCode == null) branchCode = "";
        if (branchName == null) branchName = "";
        if (parentCode == null) parentCode = "";
        if (parentPath == null) parentPath = "";
        if (branchLevel == null) branchLevel = "";
        if (branchSequence == null) branchSequence = 0;
        if (status == null) status = "";
        if (sort == null) sort = 0;
        if (empAdd == null) empAdd = "";
        if (remark == null) remark = "";
        if (branchIssueInd == null) branchIssueInd = "";
        if (cardCollectInd == null) cardCollectInd = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}