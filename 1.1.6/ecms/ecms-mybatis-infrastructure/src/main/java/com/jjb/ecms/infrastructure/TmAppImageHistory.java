package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 影像调阅信息记录表
 * @author jjb
 */
public class TmAppImageHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String name;

    private String idType;

    private String idNo;

    private String imageNum;

    private String taskNum;

    private String operatorId;

    private Date operateTime;

    private String obText1;

    private String obText2;

    private String obText3;

    private String obText4;

    private BigDecimal obDecimal1;

    private BigDecimal obDecimal2;

    private BigDecimal obDecimal3;

    private BigDecimal obDecimal4;

    private Date obDate1;

    private Date obDate2;

    private Date obDate3;

    private Integer jpaVersion;

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
     * <p>申请编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>客户姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>客户姓名</p>
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
     * <p>任务编号</p>
     */
    public String getTaskNum() {
        return taskNum;
    }

    /**
     * <p>任务编号</p>
     */
    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * <p>调阅人</p>
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * <p>调阅人</p>
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * <p>调阅时间</p>
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * <p>调阅时间</p>
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * <p>预留字段1</p>
     */
    public String getObText1() {
        return obText1;
    }

    /**
     * <p>预留字段1</p>
     */
    public void setObText1(String obText1) {
        this.obText1 = obText1;
    }

    /**
     * <p>预留字段2</p>
     */
    public String getObText2() {
        return obText2;
    }

    /**
     * <p>预留字段2</p>
     */
    public void setObText2(String obText2) {
        this.obText2 = obText2;
    }

    /**
     * <p>预留字段3</p>
     */
    public String getObText3() {
        return obText3;
    }

    /**
     * <p>预留字段3</p>
     */
    public void setObText3(String obText3) {
        this.obText3 = obText3;
    }

    /**
     * <p>预留字段4</p>
     */
    public String getObText4() {
        return obText4;
    }

    /**
     * <p>预留字段4</p>
     */
    public void setObText4(String obText4) {
        this.obText4 = obText4;
    }

    /**
     * <p>预留数字1</p>
     */
    public BigDecimal getObDecimal1() {
        return obDecimal1;
    }

    /**
     * <p>预留数字1</p>
     */
    public void setObDecimal1(BigDecimal obDecimal1) {
        this.obDecimal1 = obDecimal1;
    }

    /**
     * <p>预留数字2</p>
     */
    public BigDecimal getObDecimal2() {
        return obDecimal2;
    }

    /**
     * <p>预留数字2</p>
     */
    public void setObDecimal2(BigDecimal obDecimal2) {
        this.obDecimal2 = obDecimal2;
    }

    /**
     * <p>预留数字3</p>
     */
    public BigDecimal getObDecimal3() {
        return obDecimal3;
    }

    /**
     * <p>预留数字3</p>
     */
    public void setObDecimal3(BigDecimal obDecimal3) {
        this.obDecimal3 = obDecimal3;
    }

    /**
     * <p>预留数字4</p>
     */
    public BigDecimal getObDecimal4() {
        return obDecimal4;
    }

    /**
     * <p>预留数字4</p>
     */
    public void setObDecimal4(BigDecimal obDecimal4) {
        this.obDecimal4 = obDecimal4;
    }

    /**
     * <p>预留时间1</p>
     */
    public Date getObDate1() {
        return obDate1;
    }

    /**
     * <p>预留时间1</p>
     */
    public void setObDate1(Date obDate1) {
        this.obDate1 = obDate1;
    }

    /**
     * <p>预留时间2</p>
     */
    public Date getObDate2() {
        return obDate2;
    }

    /**
     * <p>预留时间2</p>
     */
    public void setObDate2(Date obDate2) {
        this.obDate2 = obDate2;
    }

    /**
     * <p>预留时间3</p>
     */
    public Date getObDate3() {
        return obDate3;
    }

    /**
     * <p>预留时间3</p>
     */
    public void setObDate3(Date obDate3) {
        this.obDate3 = obDate3;
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
        map.put("name", name);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("imageNum", imageNum);
        map.put("taskNum", taskNum);
        map.put("operatorId", operatorId);
        map.put("operateTime", operateTime);
        map.put("obText1", obText1);
        map.put("obText2", obText2);
        map.put("obText3", obText3);
        map.put("obText4", obText4);
        map.put("obDecimal1", obDecimal1);
        map.put("obDecimal2", obDecimal2);
        map.put("obDecimal3", obDecimal3);
        map.put("obDecimal4", obDecimal4);
        map.put("obDate1", obDate1);
        map.put("obDate2", obDate2);
        map.put("obDate3", obDate3);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("imageNum")) this.setImageNum(DataTypeUtils.getStringValue(map.get("imageNum")));
        if (map.containsKey("taskNum")) this.setTaskNum(DataTypeUtils.getStringValue(map.get("taskNum")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
        if (map.containsKey("operateTime")) this.setOperateTime(DataTypeUtils.getDateValue(map.get("operateTime")));
        if (map.containsKey("obText1")) this.setObText1(DataTypeUtils.getStringValue(map.get("obText1")));
        if (map.containsKey("obText2")) this.setObText2(DataTypeUtils.getStringValue(map.get("obText2")));
        if (map.containsKey("obText3")) this.setObText3(DataTypeUtils.getStringValue(map.get("obText3")));
        if (map.containsKey("obText4")) this.setObText4(DataTypeUtils.getStringValue(map.get("obText4")));
        if (map.containsKey("obDecimal1")) this.setObDecimal1(DataTypeUtils.getBigDecimalValue(map.get("obDecimal1")));
        if (map.containsKey("obDecimal2")) this.setObDecimal2(DataTypeUtils.getBigDecimalValue(map.get("obDecimal2")));
        if (map.containsKey("obDecimal3")) this.setObDecimal3(DataTypeUtils.getBigDecimalValue(map.get("obDecimal3")));
        if (map.containsKey("obDecimal4")) this.setObDecimal4(DataTypeUtils.getBigDecimalValue(map.get("obDecimal4")));
        if (map.containsKey("obDate1")) this.setObDate1(DataTypeUtils.getDateValue(map.get("obDate1")));
        if (map.containsKey("obDate2")) this.setObDate2(DataTypeUtils.getDateValue(map.get("obDate2")));
        if (map.containsKey("obDate3")) this.setObDate3(DataTypeUtils.getDateValue(map.get("obDate3")));
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
        ", name="+name+
        "name="+name+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", imageNum="+imageNum+
        "imageNum="+imageNum+
        ", taskNum="+taskNum+
        "taskNum="+taskNum+
        ", operatorId="+operatorId+
        "operatorId="+operatorId+
        ", operateTime="+operateTime+
        "operateTime="+operateTime+
        ", obText1="+obText1+
        "obText1="+obText1+
        ", obText2="+obText2+
        "obText2="+obText2+
        ", obText3="+obText3+
        "obText3="+obText3+
        ", obText4="+obText4+
        "obText4="+obText4+
        ", obDecimal1="+obDecimal1+
        "obDecimal1="+obDecimal1+
        ", obDecimal2="+obDecimal2+
        "obDecimal2="+obDecimal2+
        ", obDecimal3="+obDecimal3+
        "obDecimal3="+obDecimal3+
        ", obDecimal4="+obDecimal4+
        "obDecimal4="+obDecimal4+
        ", obDate1="+obDate1+
        "obDate1="+obDate1+
        ", obDate2="+obDate2+
        "obDate2="+obDate2+
        ", obDate3="+obDate3+
        "obDate3="+obDate3+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (name == null) name = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (imageNum == null) imageNum = "";
        if (taskNum == null) taskNum = "";
        if (operatorId == null) operatorId = "";
        if (operateTime == null) operateTime = new Date();
        if (obText1 == null) obText1 = "";
        if (obText2 == null) obText2 = "";
        if (obText3 == null) obText3 = "";
        if (obText4 == null) obText4 = "";
        if (obDecimal1 == null) obDecimal1 = BigDecimal.ZERO;
        if (obDecimal2 == null) obDecimal2 = BigDecimal.ZERO;
        if (obDecimal3 == null) obDecimal3 = BigDecimal.ZERO;
        if (obDecimal4 == null) obDecimal4 = BigDecimal.ZERO;
        if (obDate1 == null) obDate1 = new Date();
        if (obDate2 == null) obDate2 = new Date();
        if (obDate3 == null) obDate3 = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}