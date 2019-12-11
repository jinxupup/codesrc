package com.jjb.cas.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.service.dto.T1000.T1000Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.ecms.service.dto.T1000.T1000Apply;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 转换数据库数据返回给渠道端使用的T1005Req中
 * T1002交易工具类，亦给T1003使用
 * @author hp
 *
 */
public class ProcessApplyUtils {

	private static Logger logger = LoggerFactory.getLogger(ProcessApplyUtils.class);
	
	public static T1005Req packApplyData(ApplyInfoDto dto) {
		if(dto==null) {
			return null;
		}
		TmAppMain main = dto.getTmAppMain();
		
		T1005Req req = new T1005Req();
		if(main!=null) {
			req.setOrg(main.getOrg());// 机构号
			req.setAppType(main.getAppType());// 申请类型
			req.setAppLmt(main.getAppLmt());// 申请额度
			req.setProductCd(main.getProductCd());// 卡产品代码
			req.setOwningBranch(main.getOwningBranch());// 受理网点\发卡网点
			req.setAppSource(main.getAppSource());// 申请渠道
			req.setAppProperty(main.getAppProperty());// 进件属性
			req.setAppnoExternal(main.getAppnoExternal());// 申请编号_外部送入
			req.setTaskNum(main.getTaskNum());// 行内任务编号
			req.setImageNum(main.getImageNum());// 影像批次号
		}
		TmAppAudit audit = dto.getTmAppAudit();
		if(audit!=null) {
			req.setRealtimeIssuingFlag(StringUtils.setValue(audit.getIsRealtimeIssuing(), "Y"));// audit信息中-是否实时发卡标志
		}
		List<TmAppCustInfo> custList = dto.getTmAppCustInfoList();
		List<TCustInfo> cList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(custList)) {
			for (int i = 0; i < custList.size(); i++) {
				TmAppCustInfo cust = custList.get(i);

				TCustInfo retCust = new TCustInfo();
				retCust.setIfSelectedCard(cust.getIfSelectedCard());// 是否自选卡号
				retCust.setCardNo(cust.getCardNo());// 自选卡号
				retCust.setBscSuppInd(cust.getBscSuppInd());// 申请人类型
				retCust.setAttachNo(cust.getAttachNo());// 附卡编号
				retCust.setPrimCardNo(cust.getPrimCardNo());// 申请人主卡卡号
				retCust.setRelationshipToBsc(cust.getRelationshipToBsc());// 与主卡持卡人关系
				retCust.setName(cust.getName());// 姓名
				retCust.setEmbLogo(cust.getEmbLogo());// 凸印姓名
				retCust.setGender(cust.getGender());// 性别
				retCust.setAge(cust.getAge());// 年龄
				retCust.setNationality(cust.getNationality());// 国籍
				retCust.setResidencyCountryCd(cust.getResidencyCountryCd());// 永久居住地国家代码
				retCust.setIdType(cust.getIdType());// 证件类型
				retCust.setIdNo(cust.getIdNo());// 证件号码
				retCust.setIdLastDate(cust.getIdLastDate());// 证件到期日
				retCust.setIdLastAll(cust.getIdLastAll());// 证件是否永久有效
				retCust.setIdIssuerAddress(cust.getIdIssuerAddress());// 发证机关所在地址
				retCust.setBirthday(cust.getBirthday());// 生日
				retCust.setYearIncome(cust.getYearIncome());// 年收入
				retCust.setQualification(cust.getQualification());// 教育状况/学历
				retCust.setDegree(cust.getDegree());// 学位
				retCust.setMaritalStatus(cust.getMaritalStatus());// 婚姻状况
				retCust.setHomeAddrCtryCd(cust.getHomeAddrCtryCd());// 家庭国家代码
				retCust.setHomeState(cust.getHomeState());// 家庭所在省
				retCust.setHomeCity(cust.getHomeCity());// 家庭所在市
				retCust.setHomeZone(cust.getHomeZone());// 家庭所在区县
				retCust.setHouseOwnership(cust.getHouseOwnership());// 住宅状况/住宅持有类型
				retCust.setHomeAdd(cust.getHomeAdd());// 家庭地址
				retCust.setHomePostcode(cust.getHomePostcode());// 家庭住宅邮编
				retCust.setHomePhone(cust.getHomePhone());// 家庭电话
				retCust.setCellphone(cust.getCellphone());// 移动电话
				retCust.setEmail(cust.getEmail());// 电子邮箱
				retCust.setFamilyMember(cust.getFamilyMember());// 家庭人口
				retCust.setBankmemFlag(cust.getBankmemFlag());// 是否行内员工
				retCust.setBankmemberNo(cust.getBankmemberNo());// 本行员工号
				retCust.setCorpName(cust.getCorpName());// 公司名称
				retCust.setEmpStructure(cust.getEmpStructure());// 公司性质
				retCust.setEmpType(cust.getEmpType());// 公司行业类别
				try {
					if(cust.getEmpWorkyears()!=null) {
						retCust.setEmpWorkyears(new BigDecimal(cust.getEmpWorkyears()));// 本单位工作年限
					}
				} catch (Exception e) {
					logger.warn("客户本单位工作年限["+cust.getEmpWorkyears()+"]转换错误");
				}
				retCust.setEmpAddrCtryCd(cust.getEmpAddrCtryCd());// 公司国家代码
				retCust.setEmpProvince(cust.getEmpProvince());// 公司所在省
				retCust.setEmpCity(cust.getEmpCity());// 公司所在市
				retCust.setEmpZone(cust.getEmpZone());// 公司所在区/县
				retCust.setEmpAdd(cust.getEmpAdd());// 公司地址
				retCust.setEmpPostcode(cust.getEmpPostcode());// 公司邮编
				retCust.setEmpPhone(cust.getEmpPhone());// 公司电话
				retCust.setEmpDepartment(cust.getEmpDepartment());// 任职部门
				retCust.setEmpPost(cust.getEmpPost());// 职务
				retCust.setEmpPostName(cust.getEmpPostName());// 职务名称
				retCust.setOccupation(cust.getOccupation());// 职业
				retCust.setTitleOfTechnical(cust.getTitleOfTechnical());// 职称
				retCust.setJobGrade(cust.getJobGrade());// 岗位级别
				retCust.setPosPinVerifyInd(cust.getPosPinVerifyInd());// 是否消费凭密
				retCust.setPhotoUseFlag(cust.getPhotoUseFlag());// 是否彩照卡
				retCust.setSmAmtVerifyInd(cust.getSmAmtVerifyInd());// 小额免密
				retCust.setOtherAsk(cust.getOtherAsk());// 预留问题
				retCust.setOtherAnswer(cust.getOtherAnswer());// 预留答案
				retCust.setBankCustomerId(cust.getBankCustomerId());// 行内客户号
				retCust.setGroupNum(cust.getGroupNum());// 申请团办编号
				retCust.setCustType(cust.getCustType());
				req.setClientType(StringUtils.setValue(cust.getCustType(), req.getClientType()));
				cList.add(retCust);
			}
		}
		req.setCusts(cList);
		Map<String, TmAppContact> contactMap = dto.getTmAppContactMap();
		TmAppContact con1 = contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
		if(con1!=null) {
			req.setContactName(con1.getContactName());// 联系人中文姓名
			req.setContactGender(con1.getContactGender());// 联系人性别
			req.setContactRelation(con1.getContactRelation());// 联系人与申请人关系
			req.setContactMobile(con1.getContactMobile());// 联系人移动电话
			req.setContactTelephone(con1.getContactTelephone());// 联系人联系电话
			req.setContactEmpPhone(con1.getContactEmpPhone());// 联系人公司电话号
		}
		TmAppContact con2 = contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");
		if(con2!=null) {
			req.setContactOname(con2.getContactName());// 其他联系人中文姓名();
			req.setContactOgender(con2.getContactGender());// 其他联系人性别();
			req.setContactOrelation(con2.getContactRelation());// 其他联系人与申请人关系();
			req.setContactOmobile(con2.getContactMobile());// 其他联系人移动电话();
			req.setContactOtelephone(con2.getContactTelephone());// 其他联系人联系电话();
			req.setContactOempPhone(con2.getContactEmpPhone());// 其他联系人公司电话号();
		}
		TmAppPrimCardInfo card = dto.getTmAppPrimCardInfo();
		if(card!=null) {
			req.setCardFace(card.getCardfaceCd());// card信息中-卡面代码
			req.setCardFetchMethod(card.getCardFetchMethod());// 介质卡领取方式
			req.setCardMailerInd(card.getCardMailerInd());// 卡片寄送地址标志
			req.setFetchBranch(card.getFetchBranch());// 领卡网点
			req.setStmtMediaType(card.getStmtMediaType());// 账单介质类型
			req.setStmtMailAddrInd(card.getStmtMailAddrInd());// 账单寄送地址标志
			req.setBillingCycle(card.getBillingCycle());// 账单日
			req.setDdInd(card.getDdInd());// 约定还款类型
			req.setDdBankAcctNo(card.getDdBankAcctNo());// 约定还款扣款账号
			req.setDdBankName(card.getDdBankName());// 约定还款银行名称
			req.setDdBankAcctName(card.getDdBankAcctName());// 约定还款扣款人姓名
			req.setDdBankBranch(card.getDdBankBranch());// 约定还款开户行机构号
			req.setCreditTypeOther(card.getCreditTypeOther());// 已有信用卡类型
			req.setOtherCardNo(card.getOtherCardNo());// 已有信用卡卡号
			req.setIsPrdChange(card.getIsPrdChange());// 是否同意卡片自动降级
			req.setSpreaderNo(card.getSpreaderNo());// 推广人编号
			req.setSpreaderName(card.getSpreaderName());// 推广人姓名
			req.setSpreaderTelephone(card.getSpreaderTelephone());// 推广人手机号
			req.setSpreaderCardNo(card.getSpreaderCardNo());// 推广人卡号
			req.setSpreaderIsBankEmployee(card.getSpreaderIsBankEmployee());// 推广人是否本行员工
			req.setSpreaderBank(card.getSpreaderBranchTwo());// 推广人所属分行
			req.setSpreaderOrg(card.getSpreaderBranchThree());// 推广机构
			req.setSpreaderType(card.getSpreaderType());// 推广渠道\推广方式
			req.setSpreaderMode(card.getSpreaderMode());// 三亲核实
			req.setSpreaderCorpPreNo(card.getSpreaderCorpPreNo());// 推广人预审人编号
			req.setReviewNo(card.getReviewNo());// 复核人编号
			req.setReviewName(card.getReviewName());// 复核人员签名
			req.setReviewTelephone(card.getReviewTelephone());// 复核人员联系方式
			req.setReviewBranchTwo(card.getReviewBranchTwo());// 复核人所属分行
			req.setReviewBranchThree(card.getReviewBranchThree());// 复核人所属网点
			req.setPreNo(card.getPreNo());// 预审人编号
			req.setPreName(card.getPreName());// 预审人姓名
			req.setPreTelephone(card.getPreTelephone());// 预审人联系电话
			req.setPreBranchTwo(card.getPreBranchTwo());// 预审人所属分行
			req.setPreBranchThree(card.getPreBranchThree());// 预审人所属网点
			req.setInputNo(card.getInputNo());// 录入人员编号9
			req.setInputName(card.getInputName());// 录入人员姓名
			req.setInputTelephone(card.getInputTelephone());// 录入人联系电话
			req.setInputBranchTwo(card.getInputBranchTwo());// 录入人所属分行
			req.setInputBranchThree(card.getInputBranchThree());// 录入人所属网点
			req.setInputDate(card.getInputDate());// 录入日期
		}
//
//		req.setExtMultiLoan1M();// 1个月内多头借贷次数
//		req.setExtBillCnt6M();// 6个月内信用卡账单月份数
//		req.setExtCarrierHitBl6M();// 6个月内电话催收库命中个数
//		req.setExtScore();// 外部风控系统评分
//		req.setExtAuditsRisk();// 是否触发电核规则
//		req.setExtIfLmk();// 是否存在联名卡
//		req.setExtLmkLmtTotal();// 已有联名卡总额度
//		req.setExtRefuseCode();// 拒绝原因
//		req.setExtEqBehAbn();// 设备行为异常
//		req.setExtHighRiskBl();// 第三方内部高危黑名单
//		req.setExtContactAbn();// 联系人异常
//		req.setExtBl();// 外部黑名单
//		req.setExtHighRisk();// 外部高风险
//		req.setTelOperatorsAbn();// 通讯录运营商异常
//		req.setInvalidApply();// 无效进件
//		req.setExtReseField1();// 预留字段1
//		req.setExtReseField2();// 预留字段2
//		req.setExtReseField3();// 预留字段3
//		req.setExtReseField4();// 预留字段4
//		req.setExtReseField5();// 预留字段5
		TmAppPrimAnnexEvi evi = dto.getTmAppPrimAnnexEvi();
		if(evi!=null) {
			req.setEstatesNoInstallAmt(StringUtils.valueOf(evi.getEstatesNoInstallAmt()));
			req.setEstatesInstallAmt(StringUtils.valueOf(evi.getEstatesInstallAmt()));
			req.setEstatesInstallLoanAmt(StringUtils.valueOf(evi.getEstatesInstallLoanAmt()));
			req.setBuildingName(StringUtils.valueOf(evi.getBuildingName()));
			req.setHouseMonthyAmt(StringUtils.valueOf(evi.getHouseMonthyAmt()));
			req.setOtherCcBank1(StringUtils.valueOf(evi.getOtherCcBank1()));
			req.setOtherCcLmt1(StringUtils.valueOf(evi.getOtherCcLmt1()));
			req.setOtherCcBank2(StringUtils.valueOf(evi.getOtherCcBank2()));
			req.setOtherCcLmt2(StringUtils.valueOf(evi.getOtherCcLmt2()));
			req.setCarIdNum(StringUtils.valueOf(evi.getCarIdNum()));
			req.setCardEmissions(StringUtils.valueOf(evi.getCardEmissions()));
			req.setCarModel(StringUtils.valueOf(evi.getCarModel()));
			req.setCardPrice(StringUtils.valueOf(evi.getCardPrice()));
		}
		return req;
	}
	

	/**
	 * 设置T1000交易查询赋值
	 * @param applyProcessQueryDto
	 * @return
	 */
	public static T1000Apply setQueryValueToT1000Apply(ApplyProcessQueryDto applyProcessQueryDto,
													   CacheContext cacheContext, T1000Req req,TmAppOrderMainDao tmAppOrderMainDao) {
		final String SEPARATOR = "|";
		T1000Apply apply = new T1000Apply();
		apply.setName(applyProcessQueryDto.getName());

		//如果该件是多卡同申的主件并且状态为B16,F03,B28，则将所有卡产品赋值到 返回报文
		if (StringUtils.equals(req.getStateType(), "P")) {
			String appNo = applyProcessQueryDto.getAppNo();
			TmAppOrderMain tmAppOrderMain = tmAppOrderMainDao.getTmAppOrderMainByAppNoAndRtf(appNo);
			if (tmAppOrderMain != null) {
				//优先使用多卡同申表里面的数据
				apply.setProductCode(StringUtils.setValue(tmAppOrderMain.getAllProductCds(), applyProcessQueryDto.getProductCd()));
				StringBuffer productDesc = new StringBuffer();
				String[] productCds = tmAppOrderMain.getValidProductCds().split(",");
				for (int i = 0; i < productCds.length; i++) {
					TmProduct product = cacheContext.getProduct(productCds[i]);
					if (product != null) {
						productDesc.append(CheckUtil.objToString(product.getProductDesc()));
						if(!StringUtils.equals(StringUtils.valueOf(i),StringUtils.valueOf(productCds.length-1))) {
							productDesc.append( "、");
						}
					}
				}
				apply.setProductName(StringUtils.setValue(productDesc, applyProcessQueryDto.getProductDesc()));
			} else {
				TmProduct product = cacheContext.getProduct(applyProcessQueryDto
						.getProductCd());
				if (product != null) {
					apply.setProductCode(applyProcessQueryDto.getProductCd());
					apply.setProductName(CheckUtil.objToString(product.getProductDesc()));
				}
			}
		}else {
			TmProduct product = cacheContext.getProduct(applyProcessQueryDto
					.getProductCd());
			if (product != null) {
				apply.setProductCode(applyProcessQueryDto.getProductCd());
				apply.setProductName(CheckUtil.objToString(product.getProductDesc()));
			}
		}
		String accLmt = applyProcessQueryDto.getAccLmt();
		String sugLmt = applyProcessQueryDto.getSugLmt();
		String Lmt=StringUtils.setValue(accLmt,sugLmt);
		accLmt=Lmt;
		apply.setAccLmt(accLmt);
		apply.setIdType(applyProcessQueryDto.getIdType());
		apply.setIdNo(applyProcessQueryDto.getIdNo());
		apply.setCardNo(applyProcessQueryDto.getCardNo());
		apply.setBlockCode(applyProcessQueryDto.getBlockCode());
		apply.setAppSource(applyProcessQueryDto.getAppSource());
		apply.setCellphone(applyProcessQueryDto.getCellPhone());
		apply.setImageNum(applyProcessQueryDto.getImageNum());
		apply.setOwningBranch(applyProcessQueryDto.getOwningBranch());
		apply.setApplyDate(applyProcessQueryDto.getInputDate());
		//有效核卡数量
		if(ProcessT1000Utils.isEffSuccApply(applyProcessQueryDto.getRtfState(),
				applyProcessQueryDto.getAppSource(),applyProcessQueryDto.getSpreaderIsEff())
				|| StringUtils.equals(applyProcessQueryDto.getIfNewUser(),"Y") ) {
			apply.setRtfState("P06");
		}else {
			apply.setRtfState(applyProcessQueryDto.getRtfState());
		}
		apply.setAppNo(applyProcessQueryDto.getAppNo());
		if(StringUtils.equals(applyProcessQueryDto.getAppSource(), "51")) {
			apply.setRefuseCode(applyProcessQueryDto.getRefuseCode());
			apply.setRemark(applyProcessQueryDto.getRemark());			
		}
		apply.setCorpName(applyProcessQueryDto.getCorpName());
		apply.setCardStatus(applyProcessQueryDto.getCardStatus());
		return apply;
	}

}
