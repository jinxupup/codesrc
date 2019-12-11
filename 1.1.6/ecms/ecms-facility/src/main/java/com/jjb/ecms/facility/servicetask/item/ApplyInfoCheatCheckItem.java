package com.jjb.ecms.facility.servicetask.item;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jjb.acl.facility.enums.bus.ApplyCheatCheckWarnCode;

import lombok.Data;

/**
 * @Description: 申请欺诈用ITEM
 * @author JYData-R&D-Big Star
 * @date 2016年1月28日 上午10:51:12
 * @version V1.0
 */
@Data
public class ApplyInfoCheatCheckItem implements Serializable {


	private static final long serialVersionUID = -1605722559473815355L;

	private ApplyCheatCheckWarnCode result; // 返回结果

	private Boolean ifOneCellPhoneToNapply; // 申请人手机号码是否对应多个申请

	private Boolean ifOneIdToNname; // 申请人证件是否对应多个姓名

	private Boolean ifOneHomePhoneToNapply; // 申请人住宅电话是否对应多个申请人

	private Integer applyNum180Day; // 半年申请次数

	private Integer applyFailNum180Day; // 半年内失败申请次数

	private Boolean pBlackList; // 怀疑个人黑名单

	private Boolean ifCellPhoneBlackList;// 怀疑个人移动电话黑名单

	private Boolean ifpHomePhoneBlackList;// 怀疑个人家庭电话黑名单

	private Boolean ifpEmpPhoneBlackList;// 怀疑个人所在公司电话黑名单

	private Boolean ifpHomeAddrBlackList;// 怀疑个人家庭地址黑名单

	private Boolean ifpEmpAddrBlackList;// 怀疑个人所在公司名称黑名单
	
	private Boolean ifpEmailBlackList;//怀疑电子邮箱黑名单

	private String productCd; // 产品编号

	private Boolean existInAps; // 申请卡产品在信审系统中是否存在

	private Boolean isCorpAddBl;// 怀疑个人所在公司地址黑名单
	
	private Boolean ifOneHomeAddToApp;// 申请人家庭地址多人匹配（申请人的家庭地址对应多个申请人的家庭地址）
	
	private Boolean ifOneCorpAddToApp;// 申请人单位地址多人匹配（申请人的单位地址对应多个申请人的单位地址）
	
	private Boolean ifOneContactsToApp;// 申请人联系人多人匹配（申请人的联系人（包括直系和其他联系人）对应多个申请人的联系人）（姓名和联系电话同时匹配）
	
	private Boolean ifOneCorpPhoneToCorpName;// 同一单位电话对应多个单位名称

	private Boolean ifOneCorpPhoneToNapply;// 同一单位电话对应多个申请件
	
	private Boolean ifOneEmpAddToOneHomeAdd;//同一笔申请件，单位地址填写与住宅地址一致
	
	private Boolean ifOneEmailToApp;//申请人邮箱多人匹配（申请人的邮箱对应多个申请人的邮箱）
	
	private Boolean ifOneHomePhoneToNEmpPhone;//申请人宅电对单电多人匹配（申请人的住宅电话对应多个申请人的单位电话）
	
	private Boolean ifOneContMobileToNContName;//申请人联系人姓名多人匹配（申请人联系人手机相同的情况下联系人姓名对应多个申请人的联系人姓名）（电话匹配而姓名不匹配）
	
	private Boolean ifOneMailerIndToApp;//申请人邮寄住宅地址（不为空）多人匹配
	
	private Boolean ifOneEmpPhoneAndContactToApp;//申请人单位电话和联系人多人匹配
	
	private Boolean ifOneHomePhoneAndContactToApp;//申请人家庭电话和联系人多人匹配

	private Map<String, ApplyCheatCheckWarnCode> pplyNodeCheatCheckData = new HashMap<String, ApplyCheatCheckWarnCode>();

}
