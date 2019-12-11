package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

public class PointRuleDetailDto implements Serializable {
	private static final long serialVersionUID = -1047140042335903841L;
    private Integer id;

    private Integer pointId;

    private Integer pointSetId;

    private Integer pointRuleId;

    private String markType;

    private String pointSetIndexFirst;

    private String pointSetIndexSecond;

    private String pointSet;

    private Date createDate;

    private String createUser;

    private Integer jpaVersion;
    
    /**
     * 页面符号类型下拉是否readonly
     */
    private String readonly;
    /**
     * 页面评分指标默认以是/否下拉显示
     */
    private String indicator;
    
    /**
     * 评分项-描述，只为显示
     */
    private String pointSetSelect;
    
    /**
     * 默认将pointSet评分项的首字母变成大写，由页面加载字典表中的下拉选项
     */
    private String dictType;

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
     * <p>评分规则标识</p>
     */
    public Integer getPointRuleId() {
        return pointRuleId;
    }

    /**
     * <p>评分规则标识</p>
     */
    public void setPointRuleId(Integer pointRuleId) {
        this.pointRuleId = pointRuleId;
    }

    /**
     * <p>符号类型</p>
     */
    public String getMarkType() {
        return markType;
    }

    /**
     * <p>符号类型</p>
     */
    public void setMarkType(String markType) {
        this.markType = markType;
    }

    /**
     * <p>评分指标1</p>
     */
    public String getPointSetIndexFirst() {
        return pointSetIndexFirst;
    }

    /**
     * <p>评分指标1</p>
     */
    public void setPointSetIndexFirst(String pointSetIndexFirst) {
        this.pointSetIndexFirst = pointSetIndexFirst;
    }

    /**
     * <p>评分指标2</p>
     */
    public String getPointSetIndexSecond() {
        return pointSetIndexSecond;
    }

    /**
     * <p>评分指标2</p>
     */
    public void setPointSetIndexSecond(String pointSetIndexSecond) {
        this.pointSetIndexSecond = pointSetIndexSecond;
    }

    /**
     * <p>评分项</p>
     */
    public String getPointSet() {
        return pointSet;
    }

    /**
     * <p>评分项</p>
     */
    public void setPointSet(String pointSet) {
        this.pointSet = pointSet;
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

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getPointSetSelect() {
		return pointSetSelect;
	}

	public void setPointSetSelect(String pointSetSelect) {
		this.pointSetSelect = pointSetSelect;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	

}
