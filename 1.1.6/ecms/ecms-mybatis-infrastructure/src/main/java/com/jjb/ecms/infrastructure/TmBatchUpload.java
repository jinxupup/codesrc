package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量导入信息表
 * @author jjb
 */
public class TmBatchUpload implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String uploadCategory;

    private String fileName;

    private Integer lineNo;

    private String content;

    private String startBpmn;

    private String failReason;

    private Date batchDate;

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
     * <p>批量上传种类</p>
     * <p>推广人，网点机构，商户，批量上传的区分参数</p>
     */
    public String getUploadCategory() {
        return uploadCategory;
    }

    /**
     * <p>批量上传种类</p>
     * <p>推广人，网点机构，商户，批量上传的区分参数</p>
     */
    public void setUploadCategory(String uploadCategory) {
        this.uploadCategory = uploadCategory;
    }

    /**
     * <p>文件名称</p>
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <p>文件名称</p>
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * <p>记录行号</p>
     */
    public Integer getLineNo() {
        return lineNo;
    }

    /**
     * <p>记录行号</p>
     */
    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    /**
     * <p>文件内容</p>
     */
    public String getContent() {
        return content;
    }

    /**
     * <p>文件内容</p>
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <p>处理状态</p>
     */
    public String getStartBpmn() {
        return startBpmn;
    }

    /**
     * <p>处理状态</p>
     */
    public void setStartBpmn(String startBpmn) {
        this.startBpmn = startBpmn;
    }

    /**
     * <p>失败原因描述</p>
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * <p>失败原因描述</p>
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    /**
     * <p>处理日期</p>
     */
    public Date getBatchDate() {
        return batchDate;
    }

    /**
     * <p>处理日期</p>
     */
    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
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
        map.put("org", org);
        map.put("uploadCategory", uploadCategory);
        map.put("fileName", fileName);
        map.put("lineNo", lineNo);
        map.put("content", content);
        map.put("startBpmn", startBpmn);
        map.put("failReason", failReason);
        map.put("batchDate", batchDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("uploadCategory")) this.setUploadCategory(DataTypeUtils.getStringValue(map.get("uploadCategory")));
        if (map.containsKey("fileName")) this.setFileName(DataTypeUtils.getStringValue(map.get("fileName")));
        if (map.containsKey("lineNo")) this.setLineNo(DataTypeUtils.getIntegerValue(map.get("lineNo")));
        if (map.containsKey("content")) this.setContent(DataTypeUtils.getStringValue(map.get("content")));
        if (map.containsKey("startBpmn")) this.setStartBpmn(DataTypeUtils.getStringValue(map.get("startBpmn")));
        if (map.containsKey("failReason")) this.setFailReason(DataTypeUtils.getStringValue(map.get("failReason")));
        if (map.containsKey("batchDate")) this.setBatchDate(DataTypeUtils.getDateValue(map.get("batchDate")));
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
        ", uploadCategory="+uploadCategory+
        "uploadCategory="+uploadCategory+
        ", fileName="+fileName+
        "fileName="+fileName+
        ", lineNo="+lineNo+
        "lineNo="+lineNo+
        ", content="+content+
        "content="+content+
        ", startBpmn="+startBpmn+
        "startBpmn="+startBpmn+
        ", failReason="+failReason+
        "failReason="+failReason+
        ", batchDate="+batchDate+
        "batchDate="+batchDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (uploadCategory == null) uploadCategory = "";
        if (fileName == null) fileName = "";
        if (lineNo == null) lineNo = 0;
        if (content == null) content = "";
        if (startBpmn == null) startBpmn = "";
        if (failReason == null) failReason = "";
        if (batchDate == null) batchDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}