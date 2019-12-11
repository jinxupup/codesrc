package com.jjb.ecms.biz.service.commonDialog;

import java.util.List;

import com.jjb.ecms.facility.dto.PersonCheckResultDto;

/**
 * @description: 人工核查结果弹窗service
 * @author -BigZ.Y
 * @date 2016年9月18日 下午7:00:56
 */
public interface PersonCheckService {

	/**
	 * 获取人工核查结果集
	 * 
	 * @param appNo
	 * @return
	 */
	List<PersonCheckResultDto> getPersonCheckResultDtos(String appNo);

}
