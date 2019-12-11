package com.jjb.cmp.app.controller.content;

import java.util.HashMap;
import java.util.List;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cmp.app.controller.utils.CmpExceptionPageUtils;
import com.jjb.cmp.biz.cache.controller.CmpCacheContext;
import com.jjb.unicorn.facility.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jjb.cmp.biz.util.AESUtil;
import com.jjb.cmp.biz.util.LogPrintUtils;
import com.jjb.cmp.dto.TmCmpContentDto;
import com.jjb.fastdfs.client.FastDFSException;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName RestFulController RESTFul风格接口
 * @Description 外部系统通过免密登录打开本系统页面</br>
 * 打开内容调阅、内容上传
 * @Author smh Date 2019/2/21 16:50 Version 1.0
 */
@Controller
@RequestMapping("/assets/cmp_")
public class CmpAssetsController extends CommonController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private CmpCacheContext cacheContext;
    @Autowired
    private CmpExceptionPageUtils exceptionPageUtils;
    
    @Value("#{env['aesKey']?:'*'}")
	public String aeskey;


    /**
     * 获取表信息--无论外部系统还是内部系统都用这个
     * @return
     */
    @ResponseBody
    @RequestMapping({ "/getTableByBigType" })
    public Json getTable() {
        Json j = Json.newSuccess();
        String type = getPara("type");
//		String _PARENT_KEY = getPara("_PARENT_KEY");
        String _PARENT_VALUE = getPara("_PARENT_VALUE");
        try {
            List<TmAclDict> list = cacheContext.getByTypeAndValue(type, _PARENT_VALUE);

            j.setObj(list);
        } catch (Exception var10) {
            j.setFail("获取表信息失败,表,参数");
        }
        return j;
    }


    /**
     * 返回内容调阅展示页面
     *
     * @return
     */
    @RequestMapping("/showContent")
    public String showContent(String parm) {
        if (null != parm && parm.length() != 0) {
            return contentPath(parm);
        }
        logger.error("CMP系统结束...parm为空,未读取到有效客户信息");
        return exceptionPageUtils.doExcepiton("parm为空,未读取到有效客户信息", "");
    }

    public String contentPath(String parm) {
        setAttr("parm", parm);
        String patch="";
        String imageNum = "";
    	try {
			String jsonstr=AESUtil.decrypt(parm, aeskey);
			JSONObject json=JSONObject.parseObject(jsonstr);
			imageNum=json.getString("imageNum");
			String idNo=json.getString("idNo");
			String name=json.getString("name");
			long tokenId = System.currentTimeMillis();
            if (StringUtils.isEmpty(imageNum) | StringUtils.equals(imageNum, "")) {
                //imageNum为空 外部调阅
                showContentMany(idNo,name,imageNum,tokenId);
                setAttr("imageNum", "多批次调阅");
                patch=contentView2;
                return patch;
            }else {
                //imageNum有值 审批调阅
                if(StringUtils.isNotBlank(idNo)&StringUtils.isNotEmpty(name)) {
                    //多批次调阅
                    showContentMany(idNo,name,imageNum,tokenId);
                    setAttr("imageNum", imageNum);
                    setAttr("isAccets", "true");
                    patch=contentView2;
                    return patch;
                }else{
                    //仅批次号调阅
                    showContent(imageNum, "", "", "",  tokenId);
                    setAttr("isAccets", "true");
                    patch=contentView;
                    return patch;
                }
            }
		} catch (Exception e) {
			logger.error("调阅影像出错 ~~ 内容批次号不能为空调阅影像失败",e);
            return exceptionPageUtils.doExcepiton("内容批次号不能为空调阅影像失败", imageNum);
        }finally {
            logger.info("CMP系统结束...调阅内容信息");
        }
    }

    /**
     * @Author smh
     * @Description 对外提供免密跳转，用于影像内容调阅查看
     * @Date 2019/2/21 16:58
     */
    @RequestMapping(value = "/content/{batchNo}/{sysId}/{userNo}/{branchCode}", method = RequestMethod.GET)
    public String get(@PathVariable("batchNo") String batchNo, @PathVariable("sysId") String systemId,
                      @PathVariable("userNo") String userNo, @PathVariable("branchCode") String branchCode) {
        long start = System.currentTimeMillis();
        logger.info(systemId + "系统开始....调阅内容信息" + LogPrintUtils.printBatchNoLog(batchNo, start, null));
        String path = showContent(batchNo, userNo, branchCode, systemId, start);
        logger.info(systemId + "系统结束...调阅内容信息" + LogPrintUtils.printBatchNoEndLog(batchNo, start, null));
        if (StringUtils.isEmpty(path)) {
            return contentView;
        }
        setAttr("isAccets", "true");
        return path;
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
     * 影像列表展示查询
     *
     * @return
     */
    @RequestMapping("/queryImageList")
    @ResponseBody
    public Page<TmCmpContentDto> queryImageList() {
        return commonQryContentList();
    }

    /**
     * @Author smh
     * @Description TODO上传或者更新当前影像
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
     * @Author wxl
     * @Description TODO删除当前影像
     * @Date 2019/4/24 14:56
     */
    @Transactional
    @RequestMapping("deleteFile")
    @ResponseBody
    public String deleteFile(String batchNo, String path, String isAccets,String FILEID) {
        //                     data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets},
    	logger.info(String.format("开始删除影像文件，batchNo=%s,path=%s,id=%s", batchNo,path,FILEID));
        Integer i = 1;
        HashMap<String, Object> map = new HashMap<>();
        try {
        	if(StringUtils.isBlank(FILEID)) {
        		logger.info("删除影像参数错误id为空");
        		map.put("imageNum", false);
                return new JSONObject(map).toJSONString();
        	}
            logger.info(path + "影像系统开始删除影像");
            i=deleteFileWithMultipart( FILEID);
            //i = deleteFileWithMultipart(batchNo, path);
            if (i == 0) {
                //HashMap<String, Object> map = new HashMap<>();
                map.put("imageNum", true);
                logger.info(path + "影像系统开始删除影像成功 ~~~");
                return new JSONObject(map).toJSONString();
            } else {
                //HashMap<String, Object> map = new HashMap<>();
                map.put("imageNum", false);
                logger.info(path + "影像系统删除影像失败 ！！！FastDFSException 删除异常 ！！！");
                return new JSONObject(map).toJSONString();
            }
        } catch (FastDFSException e) {
            logger.error(path + "影像系统删除影像失败 ！！！FastDFSException 删除异常 ！！！" , e);
        }
        //HashMap<String, Object> map = new HashMap<>();
        map.put("imageNum", false);
        return new JSONObject(map).toJSONString();
    }


    /**
     * @Author wxl
     * @Description TODO 删除原来影像图片,保存修改后的影像
     * @Date 2019/4/24 14:56
     */
    @Transactional
    @RequestMapping("updateFile")
    @ResponseBody
    public String updateFile(String batchNo, String path, String isAccets ,Integer current,String url) {
        //                      data: {"batchNo": batchNo, "path": Path, "isAccets": isAccets, "current": current},
        Integer i = 1;
        try {
            logger.info(path + "影像系统开始修改影像");
            i = updateFileWithMultipart(batchNo, path,current,url);
            if (i == 0) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("imageNum", true);
                logger.info(path + "影像系统开始修改影像成功 ~~~");
                return new JSONObject(map).toJSONString();
            } else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("imageNum", false);
                logger.info(path + "影像系统修改影像失败 ！！！FastDFSException 修改异常 ！！！");
                return new JSONObject(map).toJSONString();
            }
        } catch (FastDFSException e) {
            logger.error(path + "影像系统修改影像失败 ！！！FastDFSException 修改异常 ！！！" , e);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("imageNum", false);
        return new JSONObject(map).toJSONString();
    }


}
