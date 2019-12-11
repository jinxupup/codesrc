package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PointRuleDto implements Serializable {
	private static final long serialVersionUID = -2074886113507561322L;
    private Integer id;

    private Integer pointId;

    private Integer pointSetId;

    private String pointRuleNo;

    private String ruleDesc;

    private Integer pointValue;

    private Date createDate;

    private String createUser;

    private Integer jpaVersion;
    
    List<PointRuleDetailDto> pointRuleDetailDto;

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
     * <p>评分表标识</p>
     */
    public Integer getPointId() {
        return pointId;
    }

    /**
     * <p>评分表标识</p>
     */
    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    /**
     * <p>评分项表标识</p>
     */
    public Integer getPointSetId() {
        return pointSetId;
    }

    /**
     * <p>评分项表标识</p>
     */
    public void setPointSetId(Integer pointSetId) {
        this.pointSetId = pointSetId;
    }

    /**
     * <p>评分规则编号</p>
     */
    public String getPointRuleNo() {
        return pointRuleNo;
    }

    /**
     * <p>评分规则编号</p>
     */
    public void setPointRuleNo(String pointRuleNo) {
        this.pointRuleNo = pointRuleNo;
    }

    /**
     * <p>评分规则描述</p>
     */
    public String getRuleDesc() {
        return ruleDesc;
    }

    /**
     * <p>评分规则描述</p>
     */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    /**
     * <p>评分值</p>
     */
    public Integer getPointValue() {
        return pointValue;
    }

    /**
     * <p>评分值</p>
     */
    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    /**
     * <p>CREATE_DATE</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>CREATE_DATE</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>CREATE_USER</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>CREATE_USER</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

	public List<PointRuleDetailDto> getPointRuleDetailDto() {
		return pointRuleDetailDto;
	}

	public void setPointRuleDetailDto(List<PointRuleDetailDto> pointRuleDetailDto) {
		this.pointRuleDetailDto = pointRuleDetailDto;
	}

}
