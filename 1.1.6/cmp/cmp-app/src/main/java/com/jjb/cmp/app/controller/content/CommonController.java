package com.jjb.cmp.app.controller.content;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cmp.app.controller.fastdfs.FastDFSClient;
import com.jjb.cmp.app.controller.fastdfs.FileCheck;
import com.jjb.cmp.app.controller.utils.CmpExceptionPageUtils;
import com.jjb.cmp.app.controller.utils.CmpPageConstant;
import com.jjb.cmp.app.controller.utils.IsEmpty;
import com.jjb.cmp.biz.cache.controller.CmpCacheContext;
import com.jjb.cmp.biz.service.content.TmCmpContentService;
import com.jjb.cmp.biz.service.content.TmCmpMainService;
import com.jjb.cmp.biz.util.LogPrintUtils;
import com.jjb.cmp.constant.CmpConstant;
import com.jjb.cmp.dto.TmCmpContentDto;
import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.cmp.infrastructure.TmCmpMain;
import com.jjb.fastdfs.client.FastDFSException;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

import javax.imageio.ImageIO;

/**
 * @ClassName CommonController Company jydata-tech
 * @Description TODO 内容操作的公共方法 用于本系统操作与免密登录的操作 Author smh Date 2019/3/28 16:07
 * Version 1.0
 */
public class CommonController extends BaseController implements CmpPageConstant {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TmCmpMainService tmCmpMainService;
    @Autowired
    private TmCmpContentService tmCmpContentService;
    @Autowired
    private CmpExceptionPageUtils exceptionPageUtils;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private CmpCacheContext cmpCacheContext;

    /**
     * 公共的影像调阅处理方法
     *
     * @param batchNo
     */
    public String showContent(String batchNo, String userNo, String branchCode, String systemId, long tokenId) {
        TmCmpMain cmpMain = new TmCmpMain();
        TmCmpContentDto conDto = new TmCmpContentDto();
        conDto.setBatchNo(batchNo);
        conDto.setContStatus("A");
        List<TmCmpContentDto> list = null;
        LinkedHashMap<String, LinkedHashMap<String, List<TmCmpContentDto>>> allContextMap = new LinkedHashMap<>();
        try {
            // 取到cmpMain 页面显示姓名和身份证号,批次号使用
            if (StringUtils.isEmpty(batchNo)) {
                throw new ProcessException("内容批次号不能为空!");
            }
            cmpMain = tmCmpMainService.getTmCmpMainByBatchNo(batchNo);
            if (cmpMain == null) {
                logger.info(LogPrintUtils.printBatchNoLog(batchNo, tokenId, null) + "cmpMain为空,未查询到相关的信息");
                throw new ProcessException("内容平台未查询到当前批次[" + batchNo + "]信息,请确认批次号重试!");
            }
            // 查询有效的
            list = tmCmpContentService.quyContentByParam(conDto);
            if (list != null) {
                TmAclDict dict = cmpCacheContext.getAclDictByCode("FastdfsUrl", "url");

                for (TmCmpContentDto dto : list) {
                    if (dto == null || StringUtils.isEmpty(dto.getBatchNo())) {
                        continue;
                    }
                    if (dict != null && StringUtils.isNotEmpty(dict.getCodeName())) {
                        dto.setContRelPath(dict.getCodeName());
                    }
                    LinkedHashMap<String, List<TmCmpContentDto>> batchMap = new LinkedHashMap<>();
                    List<TmCmpContentDto> l1 = new ArrayList<>();
                    // 如果存在大批次号大
                    if (allContextMap.get(dto.getBatchNo()) != null
                            && allContextMap.get(dto.getBatchNo()).size() != 0) {
                        batchMap = allContextMap.get(dto.getBatchNo());
                        if (batchMap.get(dto.getSubTypeDesc()) != null
                                && batchMap.get(dto.getSubTypeDesc()).size() > 0) {
                            l1 = batchMap.get(dto.getSubTypeDesc());
                            l1.add(dto);
                        } else {
                            l1 = new ArrayList<>();
                            l1.add(dto);
                        }
                        batchMap.put(dto.getSubTypeDesc(), l1);
                        allContextMap.put(dto.getBatchNo(), batchMap);
                    } else {// 如果不存在大map中
                        l1 = new ArrayList<>();
                        l1.add(dto);
                        batchMap.put(dto.getSubTypeDesc(), l1);
                        allContextMap.put(dto.getBatchNo(), batchMap);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LogPrintUtils.printBatchNoLog(batchNo, tokenId, null) + "CMP中内容信息调阅异常", e);
            return exceptionPageUtils.doExcepiton(e.getMessage(), batchNo);
        }

        List<TmCmpContentDto> newList = new ArrayList<TmCmpContentDto>();
        for (String s : allContextMap.keySet()) {
            LinkedHashMap<String, List<TmCmpContentDto>> stringListLinkedHashMap = allContextMap.get(s);
            for (String j : stringListLinkedHashMap.keySet()) {
                List<TmCmpContentDto> tmCmpContentDtos = stringListLinkedHashMap.get(j);
                newList.addAll(tmCmpContentDtos);
            }
        }

        setAttr("allContextList", newList);
        setAttr("allContextMap", allContextMap);

        // 取到cmpMain 页面显示姓名和身份证号,批次号使用
        setAttr("tmCmpMain", cmpMain);
//		setAttr("remark", remark);
        setAttr("userNo", userNo);
        setAttr("branchCode", branchCode);
        setAttr("systemId", systemId);
        return null;
    }

    /**
     * 跳转到上传页面的公共方法
     *
     * @param isAccets 是否免密打开
     * @return //http://localhost:8082/cmp-app/assets/cmp_/uploadPage?batchNo=IMG2019030516324860095690&userNo=&systemId=cmp&branchCode=06101&isAccets=true
     */
    public String commonUploadPage() {
        String batchNo = getPara("batchNo");
        String userNo = getPara("userNo");
        String systemId = getPara("systemId");
        String branchCode = getPara("branchCode");
        String isAccets = getPara("isAccets");//是否免密打开页面
        String parm = getPara("parm");//是否查询全部
        if (StringUtils.isNotEmpty(parm) && parm != null && !parm.equals("")) {
            setAttr("parm", parm);
        }
        TmCmpMain tmCmpMain = null;
        String page = "";
        try {
            if (StringUtils.isNotEmpty(batchNo)) {
                tmCmpMain = tmCmpMainService.getTmCmpMainByBatchNo(batchNo);
                if (tmCmpMain == null) {
                    logger.error("未查询到批次[" + batchNo + "]的内容主体信息");
                    throw new ProcessException("未查询到批次[" + batchNo + "]的内容主体信息");
                }
            } else {
                logger.error("内容批次号不能为空");
                throw new ProcessException("内容批次号不能为空");
            }
            if (StringUtils.equals(isAccets, "true")) {
                page = contentAssetsUpload;
            } else {
                page = contentUpload;
            }
            // 取到cmpMain 页面显示使用
            setAttr("tmCmpMain", tmCmpMain);
            setAttr("userNo", userNo);
            setAttr("systemId", systemId);
            setAttr("branchCode", branchCode);
            setAttr("isAccets", isAccets);

        } catch (Exception e) {
            logger.error("跳转上传页面失败", e);
            // 返回一个错误页面
            page = exceptionPageUtils.doExcepiton(e.getMessage(), batchNo);
        }
        return page;
    }

    /**
     * 内容列表展示查询
     *
     * @return
     */
    public Page<TmCmpContentDto> commonQryContentList() {
        Long start = System.currentTimeMillis();
        logger.info("====>开始执行内容列表展示查询" + LogPrintUtils.printCommonStartLog(start, null));
        Page<TmCmpContentDto> page = getPage(TmCmpContentDto.class);
        try {
            page = tmCmpContentService.queryImageList(page);
        } catch (Exception e) {
            logger.error("内容清单查询异常", e);
            throw new ProcessException("内容列表展示查询异常[" + e.getMessage() + "]");
        }
        logger.info("====>结束执行内容列表展示查询" + LogPrintUtils.printCommonEndLog(start, null));
        return page;
    }

    /**
     * @param sgin 用于区分是否是免密上传 upload assetsUpload
     * @return
     */
    public Json commonUploadImage(String sgin) {
        Json json = Json.newSuccess();
        // 获取参数,这里用待定用Query
        Query query = getQuery();
        String batchNo = StringUtils.valueOf(query.get("batchNo"));
        String userNo = StringUtils.valueOf(query.get("userNo")); // 操作人
        String systemId = StringUtils.valueOf(query.get("systemId"));
        String branchCode = StringUtils.valueOf(query.get("branchCode"));
        try {
            // 必验证的参数 batchNo
            if (StringUtils.isEmpty(batchNo)) {
                throw new ProcessException("未获取到有效批次号");
            }
            // 免密登录上传时 需要验证的参数 userNo systemId branchCode
            if (StringUtils.equals(sgin, "assetsUpload")) {
                if (StringUtils.isBlank(userNo) || StringUtils.isBlank(systemId) || StringUtils.isBlank(branchCode)) {
                    throw new ProcessException("未获取到有效参数（免密登录上传）");
                }
            }
        } catch (Exception e) {
            logger.error("内容上传失败", e);
            json.setFail("内容上传失败," + e.getMessage());
            json.setS(false);
        }
        int num = 0;
        // 开始上传所有的内容
        while (StringUtils.isNotEmpty(String.valueOf(query.get("supType" + num)))
                && StringUtils.isNotEmpty(String.valueOf(query.get("subType" + num)))) {

            String supType = StringUtils.valueOf(query.get("supType" + num));
            String subType = StringUtils.valueOf(query.get("subType" + num));

            MultipartFile file = getFile("fileName" + num);
            try {
                if (file.isEmpty()) {
                    throw new ProcessException("上传的文件不存在");
                }
                // 上传时的操作
                //Boolean isImage = FileCheck.checkImage(file.getOriginalFilename());
                String url = "";
                if (true) {
                    url = uploadFileWithMultipart(file);
                    if (StringUtils.isBlank(url)) {
                        throw new ProcessException("上传文件未返回地址");
                    }
                } /*else {
                    throw new ProcessException("请上传图片格式的文件");
                }*/
                TmCmpContent tmCmpContent = new TmCmpContent();
                tmCmpContent.setBatchNo(batchNo);
                tmCmpContent.setConsSysId(StringUtils.setValue(systemId, CmpConstant.DEF_SYSTEM_ID));
                tmCmpContent.setSupType(supType);
                tmCmpContent.setSubType(subType);
                tmCmpContent.setBranch(StringUtils.setValue(branchCode, OrganizationContextHolder.getBranchCode()));
                tmCmpContent.setUpdateUser(StringUtils.setValue(userNo, OrganizationContextHolder.getUserNo()));
                String fileName = file.getOriginalFilename();
                tmCmpContent.setContFmt(FastDFSClient.getFilenameSuffix(fileName));
                tmCmpContent.setContAbsPath(url);
                boolean b = tmCmpContentService.saveNewContentInfo(batchNo, tmCmpContent);
                if (b) {
                    json.setS(true);
                } else {
                    json.setFail("内容上传保存失败!");
                    json.setS(false);
                }
            } catch (Exception e) {
                logger.error("内容上传失败", e);
                json.setFail("内容上传失败," + e.getMessage());
                json.setS(false);
            }
            num++;
            //解决删除后无法上传
            if (StringUtils.isEmpty(String.valueOf(query.get("supType" + num))) && StringUtils.isEmpty(String.valueOf(query.get("subType" + num)))) {
                num++;
                if (StringUtils.isEmpty(String.valueOf(query.get("supType" + num))) && StringUtils.isEmpty(String.valueOf(query.get("subType" + num)))) {
                    num++;
                    if (StringUtils.isEmpty(String.valueOf(query.get("supType" + num))) && StringUtils.isEmpty(String.valueOf(query.get("subType" + num)))) {
                        num++;
                        for (int i = 0; i <= 17; i++) {
                            if (StringUtils.isEmpty(String.valueOf(query.get("supType" + num))) && StringUtils.isEmpty(String.valueOf(query.get("subType" + num)))) {
                                num++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return json;
    }

    /**
     * 开始上传文件
     *
     * @return
     */
    public String uploadFileWithMultipart(MultipartFile file) throws FastDFSException {
        String url = fastDFSClient.uploadFileWithMultipart(file);
        return url;
    }


    /**
     * 删除影像图片
     *
     * @return
     */
    public int deleteFileWithMultipart(String batchNo, String path) throws FastDFSException {
        TmCmpContent tmCmpContent = new TmCmpContent();
        tmCmpContent.setContAbsPath(path);
        TmCmpContent tm = null;

        try {
            //检查是否有影像批次号
            TmCmpMain tmCmpMain = tmCmpMainService.getTmCmpMainByBatchNo(IsEmpty.StrIsEmptyRe(batchNo, "影像批次号为空"));
            IsEmpty.ObjectIsEmpty(tmCmpMain);
            //检查是否存在图片地址
            tm = tmCmpContentService.getTmCmpContent(tmCmpContent);
            IsEmpty.ObjectIsEmpty(tm);

//         删除fastdfs地址对应的一行记录
//         删除TM_CMP_CONTENT表里的fastdfs地址;cont abs path
            tmCmpContentService.deleteFile(tm);

            return fastDFSClient.deleteFile(path);

        } catch (Exception e) {
            logger.error("影像批次号不存在！！！无法进行删除操作！！！请刷新一下", e);
        }
        return 1;
    }
    
    public int deleteFileWithMultipart(String id) throws FastDFSException {
    	
    	try {
    		TmCmpContent content=tmCmpContentService.queryById(Integer.valueOf(id));
        	if(content ==null || StringUtils.isBlank(content.getContAbsPath())) {
        		logger.error("影像文件不存在id="+id);
        		return 1;
        	}
        	tmCmpContentService.deleteFile(content);
        	return fastDFSClient.deleteFile(content.getContAbsPath());
    		
    	}catch(Exception e) {
    		logger.error("删除影像文件失败id="+id, e);
    	} 	
    	 return 1;
    }
    
    public String showContentMany(String idNo, String systemId,String imageNum, long tokenId) {
    	
    	TmCmpContentDto conDto = new TmCmpContentDto();
    	conDto.setIdNo(idNo);
    	conDto.setContStatus("A");
    	List<TmCmpContentDto>  list=null;
    	LinkedHashMap<String, LinkedHashMap<String, List<TmCmpContentDto>>> allContextMap = new LinkedHashMap<>();
    	List<TmCmpMain> listTmCmpMain=null;
    	try {
    		 listTmCmpMain=this.tmCmpMainService.getTmCmpMainByIdNo(idNo);
    		 if(null==listTmCmpMain || listTmCmpMain.size()==0) {
    			 logger.info("未查询到批次信息，身份证号="+idNo);
    			 throw new ProcessException("根据身份证号"+idNo+",内容平台未查询到批次信息,请确认批次号重试!");
    		 }
    		 //查询影像内容信息
    		 list = tmCmpContentService.quyContentByParam(conDto);
    		 if (list != null) {
                 TmAclDict dict = cmpCacheContext.getAclDictByCode("FastdfsUrl", "url");
                 LinkedHashMap<String, List<TmCmpContentDto>> batchMapFirst = new LinkedHashMap<>();
                //优先显示该批次号 - 如果是审批调阅imageNum不为空
                 if (StringUtils.isNotEmpty(imageNum)) {
                     for (TmCmpContentDto dto : list) {
                         List<TmCmpContentDto> firstNum = new ArrayList<>();
                         String subTypeDesc = dto.getBatchNo();
                         if (dto == null || StringUtils.isEmpty(subTypeDesc)) {
                             continue;
                         }
                         dto.setContRelPath(dict.getCodeName());

                         if (StringUtils.equals(imageNum, subTypeDesc)) {
                             if (allContextMap.get(dto.getBatchNo()) != null
                                     && allContextMap.get(dto.getBatchNo()).size() != 0) {
                                 batchMapFirst = allContextMap.get(dto.getBatchNo());
                                 if (batchMapFirst.get(dto.getSubTypeDesc()) != null
                                         && batchMapFirst.get(dto.getSubTypeDesc()).size() > 0) {
                                     firstNum = batchMapFirst.get(dto.getSubTypeDesc());
                                     firstNum.add(dto);
                                 } else {
                                     firstNum = new ArrayList<>();
                                     firstNum.add(dto);
                                 }
                                 batchMapFirst.put(dto.getSubTypeDesc(), firstNum);
                                 allContextMap.put(dto.getBatchNo(), batchMapFirst);
                             } else {
                                 firstNum = new ArrayList<>();
                                 firstNum.add(dto);
                                 batchMapFirst.put(dto.getSubTypeDesc(), firstNum);
                                 allContextMap.put(subTypeDesc, batchMapFirst);
                             }
                         }
                     }
                 }

                 for (TmCmpContentDto dto : list) {
                     if (dto == null || StringUtils.isEmpty(dto.getBatchNo())) {
                         continue;
                     }
                     if (StringUtils.isNotEmpty(imageNum) && StringUtils.equals(dto.getBatchNo(), imageNum)) {
                         continue;
                     }
                     String codeName = dict.getCodeName();
                     dto.setContRelPath(dict.getCodeName());
                     
                     LinkedHashMap<String, List<TmCmpContentDto>> batchMap = new LinkedHashMap<>();
                     List<TmCmpContentDto> l1 = new ArrayList<>();
                     // 如果存在大批次号大
                     if (allContextMap.get(dto.getBatchNo()) != null
                             && allContextMap.get(dto.getBatchNo()).size() != 0) {
                         batchMap = allContextMap.get(dto.getBatchNo());
                         if (batchMap.get(dto.getSubTypeDesc()) != null
                                 && batchMap.get(dto.getSubTypeDesc()).size() > 0) {
                             l1 = batchMap.get(dto.getSubTypeDesc());
                             l1.add(dto);
                         } else {
                             l1 = new ArrayList<>();
                             l1.add(dto);
                         }
                         batchMap.put(dto.getSubTypeDesc(), l1);
                         allContextMap.put(dto.getBatchNo(), batchMap);
                     } else {// 如果不存在大map中
                         l1 = new ArrayList<>();
                         l1.add(dto);
                         batchMap.put(dto.getSubTypeDesc(), l1);
                         allContextMap.put(dto.getBatchNo(), batchMap);
                     }
                 }
             }

    		
    	}catch(Exception e) {
//            throw new ProcessException("影像查阅出错 idNo: "+idNo+e);
    	}
        List<TmCmpContentDto> newList = new ArrayList<TmCmpContentDto>();
        for (String s : allContextMap.keySet()) {
            LinkedHashMap<String, List<TmCmpContentDto>> stringListLinkedHashMap = allContextMap.get(s);
            for (String j : stringListLinkedHashMap.keySet()) {
                List<TmCmpContentDto> tmCmpContentDtos = stringListLinkedHashMap.get(j);
                newList.addAll(tmCmpContentDtos);
            }
        }
    	setAttr("allContextList", newList);
        setAttr("allContextMap", allContextMap);

        // 取到cmpMain 页面显示姓名和身份证号,批次号使用
        if (null != listTmCmpMain && listTmCmpMain.size() != 0) {
            setAttr("tmCmpMain", listTmCmpMain.get(0));
        } else {
            setAttr("tmCmpMain", TmCmpMain.class);
        }

//		setAttr("remark", remark);
        setAttr("userNo", idNo);
        setAttr("branchCode", "");
        if (StringUtils.isNotEmpty(systemId)) {
            setAttr("systemId", systemId);
        } else {
            setAttr("systemId", "");
        }
        return null;
    }


    /**
     * 删除原来影像图片,保存修改后的影像
     * @param batchNo 影像批次号
     * @param path 影像路径数据库存储路径
     * @param current 旋转度数
     * @param url 读取图片的路径=服务器地址+path
     * @return 成功 0 失败 1
     * @throws FastDFSException
     */
    public int updateFileWithMultipart(String batchNo, String path,Integer current,String url) throws FastDFSException {
        TmCmpContent tmCmpContent = new TmCmpContent();
        tmCmpContent.setContAbsPath(path);
        TmCmpContent tm = null;
        try {
            //检查是否有影像批次号
            TmCmpMain tmCmpMain = tmCmpMainService.getTmCmpMainByBatchNo(IsEmpty.StrIsEmptyRe(batchNo, "影像批次号为空"));
            IsEmpty.ObjectIsEmpty(tmCmpMain);
            //检查是否存在图片地址
            tm = tmCmpContentService.getTmCmpContent(tmCmpContent);
            IsEmpty.ObjectIsEmpty(tm);
            if (current == 0) {
                throw new ProcessException(" current = 0 不需要修改, 接口调用错误 ");
            } else {
                //从服务器读取图片
                BufferedImage image = ImageIO.read(new URL(url));
                int h = 0;
                int w = 0;
                if (current==90||current==270||current==-90||current==-270) {
                    h = image.getWidth();
                    w = image.getHeight();
                } else if (current==360||current==180||current==-360||current==-180) {
                    w = image.getWidth();
                    h = image.getHeight();
                }
                int type = image.getColorModel().getTransparency();
                BufferedImage newImage;
                newImage = new BufferedImage(w, h, type);
                Graphics2D graphics2D = newImage.createGraphics();
                graphics2D.translate((w-image.getWidth())/2,(h-image.getHeight())/2);
                graphics2D.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                graphics2D.rotate(Math.toRadians(current),image.getWidth()/2,image.getHeight()/2);
                graphics2D.drawImage(image, null, null);
                graphics2D.dispose();
                // newImage 存入服务器
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(newImage, "gif", byteArrayOutputStream);
                String newUrl = fastDFSClient.upload(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), "", null);
                if (newUrl == null) {
                    throw new ProcessException("上传文件未返回地址");
                }
                TmCmpContent content = new TmCmpContent();
                content.setBatchNo(batchNo);
                content.setConsSysId(tm.getConsSysId());
                content.setSupTypeDesc(tm.getSupTypeDesc());
                content.setSubType(tm.getSubType());
                content.setSupType(tm.getSupType());//图片内容
                content.setSubTypeDesc(tm.getSubTypeDesc());//图片小;类型
                content.setBranch(tm.getBranch());
                content.setContFmt(tm.getContFmt());// 内容格式
                content.setContSort(tm.getContSort());
                content.setContRelPath(tm.getContRelPath());
                content.setContAbsPath(newUrl);
                content.setContStatus(tm.getContStatus());
                content.setUpdateDate(tm.getUpdateDate());
                content.setUpdateUser(tm.getUpdateUser());
                content.setJpaVersion(tm.getJpaVersion());

                tmCmpContentService.saveTmCmpContent(content);
            }
            //删除fastdfs地址对应的一行记录
            tmCmpContentService.deleteFile(tm);

            //删除TM_CMP_CONTENT表里的fastdfs地址;cont abs path
            return fastDFSClient.deleteFile(path);
        } catch (Exception e) {
            logger.error("影像批次号不存在,或参数错误！！！无法进行修改操作！！！", e);
        }
        return 1;
    }



}
