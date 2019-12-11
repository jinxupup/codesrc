package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;

import com.jjb.acl.facility.enums.bus.ApplyItemValidType;

/**
 * @Description: 初审调查确认信息记录
 * @author JYData-R&D-HN
 * @date 2016年11月23日 上午10:34:32
 * @version V1.0
 */
public class ApplyCheckAffirmItem implements Serializable {

	private static final long serialVersionUID = 4201527246197710479L;
	private String name; // 申请人姓名
	private ApplyItemValidType ifautograph; // 申请人姓名是否有效
	private ApplyItemValidType ifInfo; // 证件信息是否有效
	private ApplyItemValidType ifworkprove;// 工作证明是否有效
	private ApplyItemValidType ifincomeprove;// 收入证明是否有效
	private ApplyItemValidType ifcardtocard;// 以卡办卡是否有效
	private ApplyItemValidType ifacctList;// 以卡办卡账单是否有效
	private ApplyItemValidType ifsocialprove; // 社保证明是否有效
	private ApplyItemValidType ifemplegal; // 企业法人证明是否有效
	private ApplyItemValidType ifhomeprove;// 房产证明是否有效
	private ApplyItemValidType ifcardprove; // 产证明是否有效是否有效
	private ApplyItemValidType ifdepositprove; // 存款证明是否有效
	private ApplyItemValidType iffinancialprove; // 理财证明是否有效
	private ApplyItemValidType ifpledgeprove; // 抵押证明是否有效
	private ApplyItemValidType ifcollateralprove; // 质押证明是否有效
	private ApplyItemValidType ifpsurety; // 个人担保证明是否有效
	private ApplyItemValidType ifempsurety; // 企业担保证明是否有效
	private ApplyItemValidType ifotherInfo; // 其他证明是否有效
	private ApplyItemValidType ifloadprove; // 贷款证明是否有效

	public ApplyItemValidType getIfloadprove() {
		return ifloadprove;
	}

	public void setIfloadprove(ApplyItemValidType ifloadprove) {
		this.ifloadprove = ifloadprove;
	}

	public ApplyItemValidType getIfotherInfo() {
		return ifotherInfo;
	}

	public void setIfotherInfo(ApplyItemValidType ifotherInfo) {
		this.ifotherInfo = ifotherInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ApplyItemValidType getIfautograph() {
		return ifautograph;
	}

	public void setIfautograph(ApplyItemValidType ifautograph) {
		this.ifautograph = ifautograph;
	}

	public ApplyItemValidType getIfInfo() {
		return ifInfo;
	}

	public void setIfInfo(ApplyItemValidType ifInfo) {
		this.ifInfo = ifInfo;
	}

	public ApplyItemValidType getIfworkprove() {
		return ifworkprove;
	}

	public void setIfworkprove(ApplyItemValidType ifworkprove) {
		this.ifworkprove = ifworkprove;
	}

	public ApplyItemValidType getIfincomeprove() {
		return ifincomeprove;
	}

	public void setIfincomeprove(ApplyItemValidType ifincomeprove) {
		this.ifincomeprove = ifincomeprove;
	}

	public ApplyItemValidType getIfcardtocard() {
		return ifcardtocard;
	}

	public void setIfcardtocard(ApplyItemValidType ifcardtocard) {
		this.ifcardtocard = ifcardtocard;
	}

	public ApplyItemValidType getIfacctList() {
		return ifacctList;
	}

	public void setIfacctList(ApplyItemValidType ifacctList) {
		this.ifacctList = ifacctList;
	}

	public ApplyItemValidType getIfsocialprove() {
		return ifsocialprove;
	}

	public void setIfsocialprove(ApplyItemValidType ifsocialprove) {
		this.ifsocialprove = ifsocialprove;
	}

	public ApplyItemValidType getIfemplegal() {
		return ifemplegal;
	}

	public void setIfemplegal(ApplyItemValidType ifemplegal) {
		this.ifemplegal = ifemplegal;
	}

	public ApplyItemValidType getIfhomeprove() {
		return ifhomeprove;
	}

	public void setIfhomeprove(ApplyItemValidType ifhomeprove) {
		this.ifhomeprove = ifhomeprove;
	}

	public ApplyItemValidType getIfcardprove() {
		return ifcardprove;
	}

	public void setIfcardprove(ApplyItemValidType ifcardprove) {
		this.ifcardprove = ifcardprove;
	}

	public ApplyItemValidType getIfdepositprove() {
		return ifdepositprove;
	}

	public void setIfdepositprove(ApplyItemValidType ifdepositprove) {
		this.ifdepositprove = ifdepositprove;
	}

	public ApplyItemValidType getIffinancialprove() {
		return iffinancialprove;
	}

	public void setIffinancialprove(ApplyItemValidType iffinancialprove) {
		this.iffinancialprove = iffinancialprove;
	}

	public ApplyItemValidType getIfpledgeprove() {
		return ifpledgeprove;
	}

	public void setIfpledgeprove(ApplyItemValidType ifpledgeprove) {
		this.ifpledgeprove = ifpledgeprove;
	}

	public ApplyItemValidType getIfcollateralprove() {
		return ifcollateralprove;
	}

	public void setIfcollateralprove(ApplyItemValidType ifcollateralprove) {
		this.ifcollateralprove = ifcollateralprove;
	}

	public ApplyItemValidType getIfpsurety() {
		return ifpsurety;
	}

	public void setIfpsurety(ApplyItemValidType ifpsurety) {
		this.ifpsurety = ifpsurety;
	}

	public ApplyItemValidType getIfempsurety() {
		return ifempsurety;
	}

	public void setIfempsurety(ApplyItemValidType ifempsurety) {
		this.ifempsurety = ifempsurety;
	}

}
