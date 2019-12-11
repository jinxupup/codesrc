/**
 * 
 */
package com.jjb.ecms.biz.service.commonDialog.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.service.commonDialog.PersonCheckService;
import com.jjb.ecms.facility.dto.PersonCheckResultDto;

/**
 * @description: 人工核查结果弹窗service实现类
 * @author -BigZ.Y
 * @date 2016年9月18日 下午7:25:05 
 */
@Service("personCheckService")
public class PersonCheckServiceImpl implements PersonCheckService{

	
	/**
	 * 获取人工核查结果集
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public List<PersonCheckResultDto> getPersonCheckResultDtos(String appNo) {
		// TODO Auto-generated method stub
//		TmAppPrimCreditResult tmAppPrimCreditResult = new TmAppPrimCreditResult();
//		tmAppPrimCreditResult = tmAppPrimCreditResultDao.getTmAppPrimCreditResultByAppNo(appNo);
		List<PersonCheckResultDto> personCheckResultDtos = new ArrayList<PersonCheckResultDto>();
		PersonCheckResultDto personCheckResultDto = new PersonCheckResultDto();
//		if(tmAppPrimCreditResult != null){
//			//114电话不为空
//			if(StringUtils.isNotBlank(tmAppPrimCreditResult.getResult114Memo())){
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.oneonefourPhoneNum.lab);
//				personCheckResultDto.setCheckResultItem(tmAppPrimCreditResult.getResult114Memo().toString());
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			
//			if(tmAppPrimCreditResult.getResult114P() != null 
//					&& ("N".equals(tmAppPrimCreditResult.getResult114P()))){
//				//114正查结果不正确
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.oneonefourCheckP.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.inconsistent.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			if(tmAppPrimCreditResult.getResult114N() != null 
//					&& ("N".equals(tmAppPrimCreditResult.getResult114N()))){
//				//114反查结果不正确
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.oneonefourCheckN.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.inconsistent.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			if(tmAppPrimCreditResult.getIsFitIdPhoto() != null 
//					&& ("N".equals(tmAppPrimCreditResult.getIsFitIdPhoto()))){
//				//证件照对比结果不正确
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.identifyPhoto.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.inconsistent.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			if(tmAppPrimCreditResult.getIsFitMobil() != null 
//					&& ("N".equals(tmAppPrimCreditResult.getIsFitMobil()))){
//				//与人行手机号对比结果不正确
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.chinaBankMobileCompareTo.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.inconsistent.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			if(tmAppPrimCreditResult.getIsFitFamilyAdd() != null 
//					&& ("N".equals(tmAppPrimCreditResult.getIsFitFamilyAdd()))){
//				//家庭地址信息不完整
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.familyAdd.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.no.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			if(tmAppPrimCreditResult.getIsFitCompanyAdd() != null 
//					&& ("N".equals(tmAppPrimCreditResult.getIsFitCompanyAdd()))){
//				//公司地址信息不完整
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.companyAdd.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.no.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//			if(tmAppPrimCreditResult.getNciicResult() != null 
//					&& ("su001".equals(tmAppPrimCreditResult.getNciicResult()))){
//				//核身结果不正确
//				personCheckResultDto = new PersonCheckResultDto();
//				personCheckResultDto.setCheckTypeItem(PersonCheckResult.identityCheckResult.lab);
//				personCheckResultDto.setCheckResultItem(PersonCheckResult.inconsistent.lab);
//				personCheckResultDtos.add(personCheckResultDto);
//			}
//		}
		
		return personCheckResultDtos;
	}


}
