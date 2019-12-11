package com.jjb.cmp.app.controller.content;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.cmp.app.controller.fastdfs.FastDFSClient;
import com.jjb.cmp.app.controller.utils.CheckUtil;
import com.jjb.cmp.biz.service.content.TmCmpContentService;
import com.jjb.cmp.biz.service.content.TmCmpMainService;
import com.jjb.cmp.biz.service.content.TmCmpSeqService;
import com.jjb.cmp.constant.CmpConstant;
import com.jjb.cmp.dto.T40000req;
import com.jjb.cmp.dto.T40001req;
import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.cmp.infrastructure.TmCmpMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ImageActionController Company jydata-tech
 * @Description 外部系统通过restful 进行内容的上传、查询、批次号获取等操作 
 * @Author shiminghong Date 2019/3/21 15:39 Version 1.0
 */
@RestController
@Configuration
public class CmpRestfulController extends CommonController {
	@Autowired
	private TmCmpSeqService tmCmpSeqService;
	@Autowired
	private TmCmpMainService tmCmpMainService;
	@Autowired
	private TmCmpContentService tmCmpContentService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private CheckUtil checkUtil;
	/**
	 * 影像转移 临时 生产批次号
	 */
	public String getImageNo(T40000req t40000req) {
		logger.info("T40000-影像批次号申请");
		String newBatchNo = "";
		try {
			String org = t40000req.getOrg();
			String idType = t40000req.getId_type();
			String idNo = t40000req.getId_no();
			String name = t40000req.getName();
			String sysId = t40000req.getSys_id();
			String operateId = t40000req.getOperator_id();
			if (StringUtils.isEmpty(org) || StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)
					|| StringUtils.isEmpty(name) || StringUtils.isEmpty(sysId) || StringUtils.isEmpty(operateId)) {
				throw new Exception("未获取到有效参数");
			}
			// 开始获取新的批次号
			// TODO Auto-generated method stub
			String dateNo = DateUtils.dateToString(DateUtils.getCurrentDate(), DateUtils.DAY_YMD);
			String zeroAppno = "";
			String seqNo = tmCmpSeqService.getSeqNo(org);
			Long len = new  Long(seqNo);
			zeroAppno = String.format("%010d", (len));
			String sysType="CMP_";
			// 设置八位数
			if (10 == zeroAppno.length()) {
				newBatchNo = sysType + dateNo + zeroAppno;
			} else {
				for (int i = 1; i < (10 - zeroAppno.length()); i++) {
					zeroAppno = "0" + zeroAppno;
				}
			}
			newBatchNo = sysType +dateNo + zeroAppno;
			TmCmpMain tmCmpMain = new TmCmpMain();
			tmCmpMain.setOrg(StringUtils.setValue(org, "00006454000"));
			tmCmpMain.setIdType(idType);
			tmCmpMain.setIdNo(idNo);
			tmCmpMain.setName(name);
			tmCmpMain.setBatchNo(newBatchNo);
			tmCmpMain.setUpdateDate(new Date());
			tmCmpMain.setUpdateUser(operateId);
			tmCmpMainService.saveTmCmpMain(tmCmpMain);
		} catch (Exception e) {
			logger.error("获取批次号异常", e);
		}
		return newBatchNo;
	}






	/**
	 * T40000 40000-影像批次号申请
	 * 
	 * @param request
	 * @param response
	 * @param t40000req
	 * @return
	 */
	@RequestMapping(value = "/assets/api/v1/img/id", method = RequestMethod.POST)
	public JSON T40000(HttpServletRequest request, HttpServletResponse response, T40000req t40000req) {
		logger.info("T40000-影像批次号申请");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RET_CODE", "S");
		jsonObject.put("RET_MSG", "响应成功");
		String newBatchNo = "";
		try {
			String org = t40000req.getOrg();
			String idType = t40000req.getId_type();
			String idNo = t40000req.getId_no();
			String name = t40000req.getName();
			String sysId = t40000req.getSys_id();
			String operateId = t40000req.getOperator_id();
			if (StringUtils.isEmpty(org) || StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)
					|| StringUtils.isEmpty(name) || StringUtils.isEmpty(sysId) || StringUtils.isEmpty(operateId)) {
				throw new Exception("未获取到有效参数");
			}
//			
//			TmCmpMain cmpMain = tmCmpMainService.getTmCmpMain(tmCmpMain);
//			if (cmpMain != null && StringUtils.isNotBlank(cmpMain.getBatchNo())) {
//				throw new Exception("该客户批次号已存在,已有批次号为" + cmpMain.getBatchNo());
//			}
			// 开始获取新的批次号
			// TODO Auto-generated method stub
			String dateNo = DateUtils.dateToString(DateUtils.getCurrentDate(), DateUtils.DAY_YMD);
			String zeroAppno = "";
			String seqNo = tmCmpSeqService.getSeqNo(org);
			Long len = new  Long(seqNo);
			zeroAppno = String.format("%010d", (len));
			String sysType="CMP_";
			// 设置八位数
			if (10 == zeroAppno.length()) {
				newBatchNo = sysType + dateNo + zeroAppno;
			} else {
				for (int i = 1; i < (10 - zeroAppno.length()); i++) {
					zeroAppno = "0" + zeroAppno;
				}
			}
			newBatchNo = sysType +dateNo + zeroAppno;
			TmCmpMain tmCmpMain = new TmCmpMain();
			tmCmpMain.setOrg(StringUtils.setValue(org, "00006454000"));
			tmCmpMain.setIdType(idType);
			tmCmpMain.setIdNo(idNo);
			tmCmpMain.setName(name);
			tmCmpMain.setBatchNo(newBatchNo);
			tmCmpMain.setUpdateDate(new Date());
			tmCmpMain.setUpdateUser(operateId);
			tmCmpMainService.saveTmCmpMain(tmCmpMain);
		} catch (Exception e) {
			logger.error("获取批次号异常", e);
			jsonObject.put("RET_CODE", "F");
			jsonObject.put("RET_MSG", "响应失败:" + e.getMessage());
		}
		jsonObject.put("IMAGE_NO", newBatchNo);
		return jsonObject;
	}

	/**
	 * T40001 影像新增\补录
	 * 
	 * @param request
	 * @param response
	 * @param t40001req
	 * @return
	 */
	@RequestMapping(value = "/assets/api/v1/img/upload", method = RequestMethod.POST)
	public JSON T40001(HttpServletRequest request, HttpServletResponse response, T40001req t40001req) {
		logger.info("T40001-影像新增/补录");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("RET_CODE", "S");
		jsonObject.put("RET_MSG", "响应成功");
		String image_no = "";
		try {
			String org = t40001req.getOrg();
			String sys_id = t40001req.getSys_id();
			image_no = t40001req.getImage_no();// 批次号
			String operator_id = t40001req.getOperator_id();// 操作人
			JSONArray jsonArray= JSON.parseArray(t40001req.getImages_list());
			if (StringUtils.isEmpty(org) || StringUtils.isEmpty(sys_id) || StringUtils.isEmpty(image_no)
					|| StringUtils.isEmpty(operator_id)||!(jsonArray.size()>0)) {
				throw new Exception("未获取到有效参数");
			}
			checkUtil.checkAll(jsonArray);
			for (int i =0;i<jsonArray.size();i++){
				JSONObject jobject = jsonArray.getJSONObject(i);
				String sup_type = StringUtils.valueOf(jobject.get("sup_type"));
				String sub_type =StringUtils.valueOf(jobject.get("sub_type"));
				String branch_code = StringUtils.valueOf(jobject.get("branch_code"));
				String upload_sys_id = StringUtils.valueOf(jobject.get("upload_sys_id"));
				String format = StringUtils.valueOf(jobject.get("format"));
				String content = StringUtils.valueOf(jobject.get("content"));
				String name = StringUtils.valueOf(jobject.get("name"));
				/*if (StringUtils.isEmpty(sup_type) || StringUtils.isEmpty(sub_type)
						|| StringUtils.isEmpty(upload_sys_id) || StringUtils.isEmpty(branch_code)
						|| StringUtils.isEmpty(format) || StringUtils.isEmpty(content)) {
					throw new Exception("未获取到有效参数(T400010req)");
				}*/
				// 开始上传
//				MultipartFile file = Base64UtilMultipart.base64ToMultipart(content);
//				if (file.isEmpty()) {
//					throw new ProcessException("上传的文件不存在");
//				}
				String url = fastDFSClient.upload_ISO_8859_1(content, name, null);
				if (!StringUtils.isEmpty(url)) {
					
					TmCmpContent tmCmpContent = new TmCmpContent();
					tmCmpContent.setBatchNo(image_no);
					tmCmpContent.setConsSysId(StringUtils.setValue(sys_id, CmpConstant.DEF_SYSTEM_ID));
					tmCmpContent.setSupType(sup_type);
					tmCmpContent.setSubType(sub_type);
					tmCmpContent.setBranch(StringUtils.setValue(branch_code, OrganizationContextHolder.getBranchCode()));
					tmCmpContent.setUpdateUser(StringUtils.setValue(operator_id, OrganizationContextHolder.getUserNo()));
//					String fileName = file.getOriginalFilename();
//					String filen1 = file.getName();
//					tmCmpContent.setContFmt(FastDFSClient.getFilenameSuffix(fileName));
					tmCmpContent.setContFmt(format);
					tmCmpContent.setContAbsPath(url);
					boolean b=tmCmpContentService.saveNewContentInfo(image_no, tmCmpContent);
					if(!b) {
						throw new Exception("保存文件信息出错");
					}
				}else if (StringUtils.isBlank(url)){
					throw  new Exception("上传失败，未获取到返回值");
				}
			}
		} catch (Exception e) {
			logger.error("上传失败"+e);
			jsonObject.put("RET_CODE", "F");
			jsonObject.put("RET_MSG", "响应失败:" + e.getMessage());
		}
		jsonObject.put("IMAGE_NO", image_no);
		return jsonObject;
	}

}
