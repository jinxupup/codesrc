package com.jjb.ecms.biz.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.util.ChineseToPinYin;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class ApplyInfoSupport implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ApplyInfoSupport.class);

	@Autowired
	private CommonService commonService;
	/**
	 * 如果是独立附卡申请则根据主卡卡号拷贝主卡信息
	 * @param applyInfoDto
	 * @param tokenId
	 * @return
	 */
	public ApplyInfoDto setPrimApplicantInfo(ApplyInfoDto applyInfoDto,Long tokenId){
		if(applyInfoDto==null){
			logger.error("独立附卡申请则根据主卡卡号拷贝主卡信息失败!ApplyInfoDto is null");
			return applyInfoDto;
		}
		TmAppMain tmAppMain=applyInfoDto.getTmAppMain();//申请业务主表
		if(tmAppMain==null){
			logger.error(LogPrintUtils.printAppNoLog(applyInfoDto.getAppNo(), tokenId,null)+",TmAppMain 表信息为空");
			throw new ProcessException("未查询到相关申请件信息,请刷新后重试!");
		}
		Map<String, TmAppCustInfo> tmAppAttachInfoMap=applyInfoDto.getTmAppCustInfoMap();//申请附卡信息表
		//如果是独立附卡申请，就带出主卡信息
		if(tmAppMain!=null && AppType.S.name().equals(tmAppMain.getAppType())){
			//判断附卡是否存在 
			if (tmAppAttachInfoMap != null && tmAppAttachInfoMap.get(AppConstant.bscSuppInd_S_1)!=null) {
				//获取第一张附卡
				TmAppCustInfo attachCust = tmAppAttachInfoMap.get(AppConstant.bscSuppInd_S_1);
				String primCardNo = attachCust.getPrimCardNo();
				if(StringUtils.isNotEmpty(primCardNo)){
					logger.info("开始设置-独立附卡根据主卡卡号拷贝主卡信息"+LogPrintUtils.printAppNoLog(applyInfoDto.getAppNo(), tokenId,null)+"主卡卡号["+primCardNo+"]");
					applyInfoDto = commonService.queryPrimApplicantInfoByPrimCardNo(primCardNo, applyInfoDto);
					if(tmAppAttachInfoMap.containsKey(AppConstant.bscSuppInd_B_1)){
						logger.info("设置-独立附卡根据主卡卡号拷贝主卡信息-成功"+LogPrintUtils.printAppNoLog(applyInfoDto.getAppNo(), tokenId,null)+"主卡卡号["+primCardNo+"]");
					}
				}
			}
		}
		return applyInfoDto;
	}
	/**
	 * 设置申请人属性，具体如下：
	 * 1.姓名拼音值
	 * 2.生日值
	 * 3.性别
	 * 4.年龄
	 * @param primCust
	 * @param attachApplierInfoMap
	 * @param applyInfoDto
	 * @return ApplyInfoDto
	 */
	public ApplyInfoDto setAppAttributeValue(ApplyInfoDto applyInfoDto, TmProduct tmProduct,int cardNoLen) {
		if(applyInfoDto == null || applyInfoDto.getTmAppMain()==null){
			logger.error("设置申请人生日、年龄等属性操作未获取到申请人信息");
			throw new ProcessException("设置申请人生日、年龄等属性操作未获取到申请人信息");
		}
		if(tmProduct == null){
			logger.error("获取卡产品信息不存在，卡产品代码为[{}]",applyInfoDto.getTmAppMain().getProductCd());
			throw new ProcessException("获取卡产品信息不存在，卡产品代码为"+applyInfoDto.getTmAppMain().getProductCd());
		}
		if(StringUtils.isBlank(applyInfoDto.getTmAppMain().getAppType())){
			logger.error("申请件类型不存在");
			throw new ProcessException("申请件类型不存在");
		}
		if(StringUtils.isBlank(tmProduct.getProductCd())){
			logger.error("卡产品代码不存在");
			throw new ProcessException("卡产品代码不存在");
		}
		
		String cardBin = tmProduct.getBin();//卡bin
		cardNoLen = cardBin.length()+tmProduct.getCardNoRangeCeil().length()+1;
		Map<String,TmAppCustInfo> custMap = applyInfoDto.getTmAppCustInfoMap();
		if(custMap!=null && custMap.size()>0) {
			for (TmAppCustInfo cust : custMap.values()) {
				if(cust==null) {
					continue;
				}
				logger.info("设置申请人["+cust.getName()+"-"+cust.getBscSuppInd()+"]姓名拼音、生日、性别等信息开始《《《《《《《《《");
				Integer attachNo = cust.getAttachNo();
				if(attachNo == null){
					attachNo = 1;
				}
				cust.setAttachNo(attachNo);
				//姓名转拼音
				String embLogo = cust.getEmbLogo();
				if(StringUtils.isEmpty(embLogo)){
					String name = cust.getName();
					if(StringUtils.isNotEmpty(name)){
						embLogo = ChineseToPinYin.getFullSpell(name);
						cust.setEmbLogo(embLogo);
					}
				}
				//计算生日和性别
				Date birthday = cust.getBirthday();
				String gender = cust.getGender();
				if(birthday == null || StringUtils.isEmpty(gender)){
					String idType = cust.getIdType();
					String idNo = cust.getIdNo();
					if(StringUtils.isNotEmpty(idType) && idType.equals(IdType.I.name()) && StringUtils.isNotEmpty(idNo)){
						if(cust.getBirthday() == null){
							//计算生日
							cust.setBirthday(IdentificationCodeUtil.getBirthdayDate(idNo));
						}
						if(StringUtils.isEmpty(cust.getGender())){
							//计算性别
							cust.setGender(IdentificationCodeUtil.getGender(idNo));
						}
						if (StringUtils.isEmpty(cust.getAge())) {
							// 计算年龄
							cust.setAge(IdentificationCodeUtil.getAge(idNo));
						}
					}
				}
				custMap.put(cust.getBscSuppInd()+cust.getAttachNo() ,cust);
			}
		}
		applyInfoDto.setTmAppCustInfoMap(custMap);
		return applyInfoDto;
	}
}
