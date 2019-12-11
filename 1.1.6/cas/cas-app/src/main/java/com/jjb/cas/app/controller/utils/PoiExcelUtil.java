
package com.jjb.cas.app.controller.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jjb.unicorn.facility.util.StringUtils;


/**
 * excel导出
 * @author hp
 *
 */
public class PoiExcelUtil {

	protected static Logger log = LoggerFactory.getLogger(PoiExcelUtil.class);

	protected static final String BASE_FILE_PATH = "";

	
	public void check(HttpServletRequest request, HttpServletResponse response) {
		try {
			if ("open".equals(request.getSession().getAttribute("state"))) {
				request.getSession().setAttribute("state", null);
				response.getWriter().write("true");
				response.getWriter().flush();
			} else {
				response.getWriter().write("false");
				response.getWriter().flush();
			}
		} catch (IOException e) {
			log.error("导出Excel异常",e);
		}
	}

	public String getUploadPath() {
		return BASE_FILE_PATH;
	}

	public static void exportTempExcelAndDownload(final HttpServletRequest request, final HttpServletResponse response,
			final String filePath, final String fileName, final String head, final String fields, final List<?> datas,
			Map<String, String> fieldMappings, String[] numberCell) throws Exception {
		PoiExcelUtil util = new PoiExcelUtil();
		util.exportTempExcel(head, fields, filePath, fileName, datas, fieldMappings, numberCell);
		util.downloadExcel(request, response, filePath, fileName);
	}
	public static void exportTempExcelAndDownloadWithNoPath(final HttpServletRequest request, final HttpServletResponse response,
			final String fileName, final String head, final String fields, final List<?> datas,
			Map<String, String> fieldMappings, String[] numberCell) throws Exception {
		PoiExcelUtil util = new PoiExcelUtil();
		util.getExcelStright(head, fields, fileName, request, response, fieldMappings, datas, numberCell);
	}
	/**
	 * 新写一个方法,不用转存,直接下载
	 * @param head
	 * @param fields
	 * @param fileName
	 * @param request
	 * @param response
	 * @param fieldMappings
	 * @param datas
	 * @param numberCell
	 * @throws UnsupportedEncodingException
	 */
	public void getExcelStright(String head,String fields,String fileName,HttpServletRequest request,
			HttpServletResponse response,Map<String, String> fieldMappings,List<?> datas,String[] numberCell) throws UnsupportedEncodingException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
//		response.setHeader("Content-Length", String.valueOf(fileLength));
		OutputStream outputStream = null;

		final Map<String, String> filterFields = buildFilterFieldMap(fields, fieldMappings);

		try {
			outputStream = response.getOutputStream();
			final List<Map<String, String>> filterDatas = ExportExcelUtil.getFilterData(filterFields, datas);
			ExportExcelUtil excelWriter = new ExportExcelUtil(outputStream);
			excelWriter.createNormalHead(head, filterFields.size() - 1);
			excelWriter.createColumnTitle(1,filterFields);
			excelWriter.fillDatas(filterFields, filterDatas, numberCell);
			excelWriter.exportExcel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("导出Excel异常",e);
		}
		
		
	}
	/**
	 * <p>生成临时Excel文件</p>
	 */
	public void exportTempExcel(final String head, final String fields, final String filePath, final String fileName, final List<?> datas,
			Map<String, String> fieldMappings, String[] numberCell) throws Exception {

		final Map<String, String> filterFields = buildFilterFieldMap(fields, fieldMappings);

		final List<Map<String, String>> filterDatas = ExportExcelUtil.getFilterData(filterFields, datas);
		File file = new File(filePath);
		if(!file.exists()){
		    file.mkdirs();
		}
		// replaceValueByDesc(filterDatas);
		final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath+fileName));
		final ExportExcelUtil excelWriter = new ExportExcelUtil(out);
		excelWriter.createNormalHead(head, filterFields.size() - 1);
		excelWriter.createColumnTitle(1,filterFields);
		excelWriter.fillDatas(filterFields, filterDatas, numberCell);
		try {
			excelWriter.exportExcel();
		} catch (final IOException ex) {
			log.error("生成临时Excel文件异常!", ex);
			// ex.printStackTrace();
		}
	}

	/**
	 * <p>根据下载页所选择的字段生成映射关系</p>
	 */
	private Map<String, String> buildFilterFieldMap(final String fields, Map<String, String> allFields) {
		Map<String, String> filterFields = new LinkedHashMap<String, String>();
		if (fields != null && fields.length() > 0) {
			final String[] fieldNames = fields.split(",");
			for (final String fieldName : fieldNames) {
				if (allFields.containsKey(fieldName)) {
					filterFields.put(fieldName, allFields.get(fieldName));
				}
			}
		} else {
			filterFields = allFields;
		}
		return filterFields;
	}

	/**
	 * <p>初始化所有字段映射关系(子类重写)</p>
	 */
	public Map<String, String> getFieldMappings() {
		return new LinkedHashMap<String, String>();
	}

	/**
	 * <p>下载Excel文件</p>
	 * 
	 * @throws Exception
	 */
	public void downloadExcel(final HttpServletRequest request, final HttpServletResponse response,
			final String filePath, final String fileName) throws Exception {
		// 下载文件
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		final File file = new File(filePath + fileName);
		try {
			final long fileLength = file.length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(filePath + fileName));
			bos = new BufferedOutputStream(response.getOutputStream());
			final byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final Exception e) {
			// e.printStackTrace();
			log.error("下载Excel文件!", e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			file.delete();
		}
	}

	public static void exportTxtAndDownload(final HttpServletRequest request, final HttpServletResponse response,
			final String fileName, final List<String> datas) throws Exception {
		response.setContentType("text/plain");// 一下两行关键的设置
		request.setCharacterEncoding("GBK");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);// filename指定默认的名字
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		String enter = "\r\n";
		ServletOutputStream outSTr = null;
		try {
			outSTr = response.getOutputStream();// 建立
			buff = new BufferedOutputStream(outSTr);
			for (int i = 0; i < datas.size(); i++) {
				String strout = (String) datas.get(i);
				write.append(strout + enter);
			}
			buff.write(write.toString().getBytes("GBK"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			log.error("导出Excel异常",e);
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				log.warn("导出Excel时关闭流异常",e);
			}
		}
	}
	/**
	 * 功能描述：下载excel模板
	 * @param request
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @param fields
	 * @throws Exception
	 */
	public static void downloadExcelTemplate(final HttpServletRequest request, final HttpServletResponse response,
        final String filePath, final String fileName,final String head, final String[] fields) throws Exception {
        PoiExcelUtil util = new PoiExcelUtil();
        util.getTempExcel(head,fields, filePath,fileName);
        util.downloadExcel(request, response, filePath, fileName);
    }
    
    /**
     * <p>生成临时Excel文件</p>
     */
    public void getTempExcel(final String head,final String[] fields, final String fieldPath,final String fileName) throws Exception {
        File file = new File(fieldPath);
        if(!file.exists()){
            file.mkdirs();
        }
        final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fieldPath+fileName));
        final ExportExcelUtil excelWriter = new ExportExcelUtil(out);
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        for(int i=0;i<fields.length;i++){
            map.put(fields[i], "");
        }
        if(StringUtils.isNotBlank(head)){
            excelWriter.createNormalHead(head, fields.length - 1);
            excelWriter.createColumnTitle(1,map);
        }else{
            excelWriter.createColumnTitle(0,map);
        }
        try {
            excelWriter.exportExcel();
        } catch (final IOException ex) {
            log.error("生成临时Excel文件异常!", ex);
        }
    }
    
    public static boolean validFileType(MultipartFile file,String excelFileType){
        boolean result = false;
        InputStream is = null;
        try {
            is = file.getInputStream();
            byte[] bytes = new byte[4];
            is.read(bytes, 0, bytes.length);
            String fileType = byte2HexStr(bytes);
            log.info("导入文件的文件头信息：" + fileType);
            for (String hexStr : excelFileType.split("/")) {
                if (hexStr.equals(fileType)) {
                    result = true;
                    break;
                }
            } 
        } catch (Exception e) {
            log.error("导入文件校验文件头报错", e);
        }finally{
            if(is != null){
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    log.error("导入文件校验文件头报错", e);
                }
            }
        }
        return result;      
    }
    
    /**
     * bytes转换成十六进制字符串
     * 
     * @param b
     * @return
     */
    private static String byte2HexStr(final byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (final byte element : b) {
            stmp = (Integer.toHexString(element & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0");
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }
}