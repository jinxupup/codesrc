package com.jjb.cmp.app.controller.content;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cmp.biz.cache.controller.CmpCacheContext;
import com.jjb.cmp.dto.T40000req;
import com.jjb.fastdfs.client.FastDFSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jjb.cmp.app.controller.fastdfs.FastDFSClient;
import com.jjb.cmp.app.controller.utils.IsEmpty;
import com.jjb.cmp.biz.service.content.TmCmpContentService;
import com.jjb.cmp.biz.service.content.TmCmpMainService;
import com.jjb.cmp.constant.CmpConstant;
import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.cmp.infrastructure.TmCmpMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName RestFulController RESTFul风格接口
 * @Description 外部系统通过免密登录打开本系统页面
 * 打开内容调阅、内容上传
 * @Author smh Date 2019/2/21 16:50 Version 1.0
 */
@Controller
@RequestMapping("/assets/cmp_/move")
public class ImageMoveController extends CommonController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TmCmpMainService tmCmpMainService;
    @Autowired
    private TmCmpContentService tmCmpContentService;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private CmpRestfulController cmpRestfulController;
    @Autowired
    private CmpCacheContext cmpCacheContext;

    /**
     * @Author wxl
     * @Description 影像转移
     * @Date 2019/4/17
     */
    @RequestMapping(value = "/moveImage", method = RequestMethod.POST)
    public void moveImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> map = request.getParameterMap();
        String json = null;
        String imageNo = "";
        Map<String, String> responseMap = new HashMap<>();
        logger.info("系统开始....处理HTTP请求---影像转移");
        try {
            IsEmpty.ObjectIsEmpty(map);
            String org = Arrays.toString(map.get("org")).replace("[", "").replace("]", "");
            String name = Arrays.toString(map.get("name")).replace("[", "").replace("]", "");
            String idNo = Arrays.toString(map.get("id_no")).replace("[", "").replace("]", "");
            String idType = Arrays.toString(map.get("id_type")).replace("[", "").replace("]", "");
            String updateUser = Arrays.toString(map.get("update_user")).replace("[", "").replace("]", "");

            //生成影像批次号
            T40000req t40000req = new T40000req();
            t40000req.setOrg(org);
            t40000req.setId_type(idType);
            t40000req.setId_no(idNo);
            t40000req.setName(name);
            t40000req.setSys_id("CAS");
            t40000req.setOperator_id(updateUser);
            //返回影像批次号
            try {
                imageNo = cmpRestfulController.getImageNo(t40000req);
            } catch (Exception e) {
                logger.error("返回影像批次号-->未获取到有效参数,或者参数不完整"+"idNo+name+idType+updateUser["+idNo+"--"+name+"--"+idType+"--"+updateUser+"]");
            }
            if(StringUtils.isBlank(imageNo))
            		throw new ProcessException("获取批次号失败,idNo="+idNo);
            //TODO 上传图片
            //遍历出影像信息LinkedHashMap< String , String > --> < 0-1-身份证件正面 , http://10.109.2.82:18089/wechat/img//id001.jpg  >
            String imageMap = Arrays.toString(map.get("imageMap")).replace("[", "").replace("]", "").replace("{", "").replace("}", "");
         //判断是否存在图片,不存在者返回批次号,不做其他操作;
            if (imageMap != null) {
                String[] one = imageMap.split(",");
                for (String s : one) {
                    String[] two = s.split("=");
                    //调用上传接口  0为小分类   1为图地址;一张影像调一次
                    String rurl=moveImageUploadImage(imageNo, two[0].trim(), two[1].trim());
                    if(StringUtils.isBlank(rurl))
                    	throw new ProcessException("获取文件上传后返回的url失败,idNo="+idNo);
                }
            }
        } catch (Exception ee) {           
            responseMap.put("boo", "false");
            json = JSON.toJSONString(responseMap);
            response.getWriter().write(json);
            logger.error("http请求返回false！！！！" , ee);
            //throw new ProcessException("http请求返回false!!!");
        }
        responseMap.put("boo", "true");
        responseMap.put("imageNo", imageNo);
        json = JSON.toJSONString(responseMap);
        response.getWriter().write(json);
    }


    /**
     * 批量上传影像转移
     *
     * @param batchNo     影像批次号
     * @param subTypeDesc 影像小类型
     * @param addr        影像地址
     * @return
     */
    private String moveImageUploadImage(String batchNo, String subTypeDesc, String addr) {
        String userNo = "ecmpc -> cmp"; // 操作人
        String systemId = "ecmsc";//操作系统
        String branchCode = "08101";//08101内容所属网点
        String url=null;
        String fileName = addr.split("\\.")[4];          
            // 必验证的参数 batchNo
        if (StringUtils.isEmpty(batchNo)) {
                throw new ProcessException("未获取到有效批次号");
            }
       
        try {
            if (addr.isEmpty()) {
                throw new ProcessException("上传的文件不存在");
            }
            
            URL streamUrl = new URL(addr);        
                //根据URL上传
            url = uploadImage(streamUrl);
          
            if(null == url)
            	throw new ProcessException("获取文件上传后url失败");
            TmCmpContent tmCmpContent = new TmCmpContent();
            tmCmpContent.setBatchNo(batchNo);
            tmCmpContent.setConsSysId(StringUtils.setValue(systemId, CmpConstant.DEF_SYSTEM_ID));
            tmCmpContent.setSupType("picType");//图片内容
//                tmCmpContent.setSubType(subType);//<p>内容子类型</p>
            tmCmpContent.setSubTypeDesc(subTypeDesc);//图片小;类型
            tmCmpContent.setBranch(StringUtils.setValue(branchCode, OrganizationContextHolder.getBranchCode()));
            tmCmpContent.setUpdateUser(StringUtils.setValue(userNo, OrganizationContextHolder.getUserNo()));
            tmCmpContent.setContFmt(FastDFSClient.getFilenameSuffix(fileName));// 内容格式
            tmCmpContent.setContAbsPath(url);
            saveNewContentInfo(batchNo, tmCmpContent);
        } catch (Exception e) {
            logger.error("内容上传失败", e);
            throw new ProcessException("内容上传失败");
        }
        return url;
    }


    /**
     * 上传方法
     *
     * @param streamUrl 文件的网络路径
     * @return 存入的影像地址
     * @throws IOException
     * @throws FastDFSException
     */
    private String uploadImage(URL streamUrl) throws IOException, FastDFSException {
        String url = null;
        File file;
        InputStream is=null;
        BufferedInputStream uploadStream=null;
        try {
            URLConnection connection = streamUrl.openConnection();
            // 设置10秒的相应时间
//            connection.setConnectTimeout(10 * 1000);
             is = connection.getInputStream();
            if (is == null || is.available() <= 0) {
                return null;
            }
            //得到临时文件
            //file = getTemplateFile(is);
            // 开始上传所有的内容
            String sn=streamUrl.toString();
            String fn=sn.substring(1+sn.lastIndexOf("/"));
            uploadStream= new BufferedInputStream(readConnectionStream(is));
            url = fastDFSClient.upload(uploadStream,fn, null);
            if (url == null) {
                throw new ProcessException("上传文件未返回地址");
            }
           
        } catch (IOException e) {
            logger.error("上传文件未返回地址", e);
        }finally {
        	if(is != null) {
        		try {
        			 is.close();
        		}catch(Exception e) {}
        	}
        	if(uploadStream != null) {
        		try {
        			uploadStream.close();
        		}catch(Exception e) {}
        	}
        }
        return url;
    }

    public static File getTemplateFile(InputStream inputStream) {
        File file = null;
        try {
            file = File.createTempFile("temp_image", null);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        inputStreamToFile(inputStream, file);
        if (file.exists() && file.length() <= 0) {
            return null;
        }
        return file;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            try {
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            } finally {
                if (os != null) {
                    os.close();
                }
                if (ins != null) {
                    ins.close();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }
    
    public static InputStream readConnectionStream(InputStream ins) {
        try {
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
                ByteArrayInputStream  bais=new ByteArrayInputStream(os.toByteArray());
                return bais;
            } finally {
                if (os != null) {
                    os.close();
                }
                if (ins != null) {
                    ins.close();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;

    }


    /**
     * 保存一个新的内容信息至数据库
     *
     * @param batchNo      批次号
     * @param TmCmpContent 单条内容信息
     * @return
     */
    @Transactional
    public boolean saveNewContentInfo(String batchNo, TmCmpContent tmCmpContent) {
        try {
            if (tmCmpContent == null) {
                throw new ProcessException("内容数据为空，请确认批次号[" + batchNo + "]上传有效内容信息");
            }
            // 更新内容管理主表信息
            TmCmpMain cmpMain = tmCmpMainService.getTmCmpMainByBatchNo(batchNo);
            if (cmpMain == null) {
                throw new ProcessException("根据批次号[" + batchNo + "]系统未查询到有效内容主体信息，请核实相关批次信息");
            }
            cmpMain.setUpdateUser(StringUtils.setValue(tmCmpContent.getUpdateUser(), OrganizationContextHolder.getUserNo()));
            cmpMain.setUpdateDate(new Date());
            tmCmpMainService.updateTmCmpMain(cmpMain);
            // 更新内容清单
            String supTypeDesc = tmCmpContent.getSupTypeDesc();
            String subTypeDesc = tmCmpContent.getSubTypeDesc();
            String supType = tmCmpContent.getSupType();
            TmAclDict supDict = cmpCacheContext.getAclDictByCode("fileBigType", supType);
            if (supDict != null) {
                supTypeDesc = supDict.getCodeName();
            }
            String subType = tmCmpContent.getSubType();
            TmAclDict subDict = cmpCacheContext.getAclDictByCode("ApplyPatchBoltType", subType);
            if (subDict != null) {
                subTypeDesc = subDict.getCodeName();
            }
            if (StringUtils.isEmpty(supTypeDesc) || StringUtils.isEmpty(subTypeDesc)) {
                throw new ProcessException("未获取到有效的参数");
            }
            tmCmpContent.setBatchNo(batchNo);
            tmCmpContent.setSupType(supType);
            tmCmpContent.setSupTypeDesc(supTypeDesc);
            tmCmpContent.setSubType(subType);
            tmCmpContent.setSubTypeDesc(subTypeDesc);
//			tmCmpContent.setContRelPath("http://10.109.3.205:80/");//采用动态ip，不写死
            tmCmpContent.setContStatus("A");

            tmCmpContent.setUpdateDate(new Date());
            tmCmpContentService.saveTmCmpContent(tmCmpContent);
        } catch (Exception e) {
            logger.error("内容信息入库失败", e);
            return false;
        }
        return true;
    }
}
