package com.jjb.cmp.app.controller.content;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jjb.cmp.app.controller.utils.CmpPageConstant;
import com.jjb.cmp.biz.util.LogPrintUtils;
import com.jjb.cmp.constant.CmpConstant;
import com.jjb.cmp.dto.TmCmpContentDto;
import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.fastdfs.client.FastDFSException;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName CmpProcessController
 * @Description 内容管理系统自身对影像内容的处理 Controller</br>
 *              包括 1.进入内容管理清单主页面；2.内容调阅；3.内容上传；
 * @Author smh Date 2018/12/31 11:47 Version 1.0
 */
@Controller
@RequestMapping("/cmp_")
public class CmpProcessController extends CommonController implements CmpPageConstant {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 进入内容管理清单列表主页面
	 *
	 * @return
	 */
	@RequestMapping("/contentListPage")
	public String contentListPage() {
		return contentListPage;
	}

	/**
	 * 影像列表展示查询
	 *
	 * @return
	 */
	@RequestMapping("/queryContentList")
	@ResponseBody
	public Page<TmCmpContentDto> queryImageList() {
		return commonQryContentList();
	}

	/**
	 * @Author smh
	 * @Description 跳转到影像平台, 显示存在的所有类型的有效影像
	 * @Date 2018/12/28 14:56
	 */
	@RequestMapping("/showContent")
	public String showContent() {
		long tokenId = System.currentTimeMillis();
//		String remark = null;
//		remark = "已有批次号; " + "创建于: ";
		String batchNo = getPara("batchNo");
		logger.info("CMP系统开始....调阅内容信息" + LogPrintUtils.printBatchNoLog(batchNo, tokenId, null));
		String userNo = OrganizationContextHolder.getUserNo();
		String brach = CmpConstant.DEF_BRANCH_CODE;// 本系统内部操作默认机构号06101-信用卡中心
		String sysId = CmpConstant.DEF_SYSTEM_ID;// 本系统内部操作默认系统Id：cmp
		String patch = showContent(batchNo, userNo, brach, sysId, tokenId);
		if (StringUtils.isEmpty(patch)) {
			patch = contentView;
		}
		logger.info("CMP系统结束...调阅内容信息" + LogPrintUtils.printBatchNoEndLog(batchNo, tokenId, null));
		setAttr("isAccets", "false");
		return patch;
	}

	/**
	 * 跳转到上传页面
	 *
	 * @return
	 */
	@RequestMapping("/uploadPage")
	public String uploadPage() {
		return commonUploadPage();
	}

	/**
	 * @Author smh
	 * @Description TODO 上传或者更新当前影像
	 * @Date 2018/12/28 14:56
	 */
	@Transactional
	@RequestMapping("uploadImage")
	@ResponseBody
	// 用于区分是跟新还是上传,传一个标志,update更新upload为上传
	public Json uploadImage(String sgin) {
		return commonUploadImage(sgin);
	}

	/**
	 * 删除 TODO 暂时没开发好，待继续跟进
	 *
	 * @return
	 */
	@Transactional
	@RequestMapping("deleteImage")
	@ResponseBody
	public Json deleteImage() {
		Json json = Json.newSuccess();
		// 获取参数,这里用待定用Query
		Query query = null;
		try {
			// 删除时的操作,先使原来的变为无效
			TmCmpContent tmCmpContent = new TmCmpContent();
			// 获取当前影像的BatchNo,supType,subType
			tmCmpContent.setBatchNo("");
			tmCmpContent.setSubType("");
			tmCmpContent.setSupType("");
//			sdasda cmpContent = tmCmpContentService.getTmCmpContent(tmCmpContent);
//			cmpContent.setUpdateDate(new Date());
//			// 怎么取维护人,后续补充
//			cmpContent.setUpdateUser(OrganizationContextHolder.getUserNo());
//			// 改变当前影像的状态
//			cmpContent.setContStatus("B");
			// 跟新内容清单表
//			tmCmpContentService.updateTmCmpContent(cmpContent);
		} catch (Exception e) {
			logger.error("影像删除失败", e);
			json.setFail("影像删除失败");
			json.setS(false);
		}
		return json;
	}
//	/**
//	 * 生成随机的4位数
//	 *
//	 * @return
//	 */
//	private Integer getContSort() {
//		char[] nonceChars = new char[4];
//		for (int index = 0; index < nonceChars.length; ++index) {
//			nonceChars[index] = symbolss.charAt(randoms.nextInt(symbolss.length()));
//		}
//		Integer contSort = Integer.valueOf(new String(nonceChars));
//		return contSort;
//	}
	/*
	 * @RequestMapping("/delectContent") public Json delectContent() { Json json =
	 * Json.newSuccess(); long tokenId = System.currentTimeMillis(); // String
	 * remark = null; // remark = "已有批次号; " + "创建于: "; String batchNo =
	 * getPara("batchNo"); try { logger.info("CMP系统开始....删除信息" +
	 * LogPrintUtils.printBatchNoLog(batchNo, tokenId, null)); String userNo =
	 * OrganizationContextHolder.getUserNo(); String brach = "06101";//
	 * 本系统内部操作默认机构号06101-信用卡中心 String sysId = "cmp";// 本系统内部操作默认系统Id：cmp String
	 * patch = delectContent(batchNo, userNo, brach, sysId, tokenId); if
	 * (StringUtils.isEmpty(patch)) { patch = contentView; }
	 * logger.info("CMP系统结束...信息删除成功" + LogPrintUtils.printBatchNoEndLog(batchNo,
	 * tokenId, null)); setAttr("isAccets", "false"); } catch (Exception e) {
	 *
	 *
	 * logger.error("影像删除失败", e); json.setFail("影像删除失败"); json.setS(false); } return
	 * json; }
	 */


	/**
	 * @Author wxl
	 * @Description TODO 删除当前影像
	 * @Date 2019/4/24 14:56
	 */
	@Transactional
	@RequestMapping("deleteFile")
	@ResponseBody
	public String deleteFile(String batchNo, String path,String FILEID) {
	//                 data: {"batchNo": batchNo, "path": Path},
		logger.info(String.format("开始删除影像文件，batchNo=%s,path=%s,id=%s", batchNo,path,FILEID));
		Integer i = 1;
		HashMap<String, Object> map = new HashMap<>();
		try {
			//logger.info(path + "影像系统开始删除影像");
			if(StringUtils.isBlank(FILEID)) {
        		logger.info("删除影像参数错误id为空");
        		map.put("imageNum", false);
                return new JSONObject(map).toJSONString();
        	}
			i = deleteFileWithMultipart(FILEID);
			if (i == 0) {
				//HashMap<String, Object> map = new HashMap<>();
				map.put("imageNum", true);
				logger.info(path + "影像系统开始删除影像成功 ~~~");
				return new JSONObject(map).toJSONString();
			} else {
				//HashMap<String, Object> map = new HashMap<>();
				map.put("imageNum", false);
				logger.info(path + "影像系统删除影像失败 ！！！FastDFSException 删除异常 ！！！" );
				return new JSONObject(map).toJSONString();
			}
		} catch (FastDFSException e) {
			logger.error(path + "影像系统删除影像失败 ！！！FastDFSException 删除异常 ！！！",e);
		}
		//HashMap<String, Object> map = new HashMap<>();
		map.put("imageNum", false);
		return new JSONObject(map).toJSONString();
	}


}
