package com.jjb.cmp.app.controller.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.cmp.app.controller.fastdfs.FileCheck;
import com.jjb.unicorn.facility.util.StringUtils;

@Component("checkUtil")
public class CheckUtil {
	
	@Value("#{env['fileCount']?:'3'}")
	public String fileCount;
	
	@Value("#{env['fileSizeImage']?:'2'}")
	public String size_image;
	
	@Value("#{env['fileSizeDoc']?:'2'}")
	public String size_doc;
	
	@Value("#{env['fileSizeVedio']?:'10'}")
	public String size_vedio;
	
	@Value("#{env['fileSizeCompress']?:'5'}")
	public String size_compress;

	public String   isValied(String  format,String content) {
		int size=content.length();
		if(FileCheck.checkImage("."+format)) {
			if(size>1048576*Integer.valueOf(size_image))
				return "超出图像文件大小限制"+size_image+"M";
		}else if(FileCheck.checkDoc("."+format)) {
			if(size>1048576*Integer.valueOf(size_doc))
				return "超出文档文件大小限制"+size_doc+"M";
		}else if(FileCheck.checkVideo("."+format)) {
			if(size>1048576*Integer.valueOf(size_vedio))
				return "超出音频文件大小限制"+size_vedio+"M";
		}else if(FileCheck.checkCompress("."+format)) {
			if(size>1048576*Integer.valueOf(size_compress))
				return "超出压缩文件大小限制"+size_compress+"M";
		}else {
			return "文件格式不符合";
		}
		return null;
	}
	
	public void checkAll(JSONArray array) throws Exception{
		if(null==array || array.size()==0)
			throw new Exception("文件为空");
		/*if(array.size()>Integer.valueOf(fileCount))
			throw new Exception("文件数量超出最大限制"+fileCount);*/
		for (int i =0;i<array.size();i++){
			JSONObject json = array.getJSONObject(i);
			String sup_type = StringUtils.valueOf(json.get("sup_type"));
			String sub_type =StringUtils.valueOf(json.get("sub_type"));
			String branch_code = StringUtils.valueOf(json.get("branch_code"));
			String upload_sys_id = StringUtils.valueOf(json.get("upload_sys_id"));
			String format = StringUtils.valueOf(json.get("format"));
			String content = StringUtils.valueOf(json.get("content"));
			String name = StringUtils.valueOf(json.get("name"));
			if (StringUtils.isEmpty(sup_type) || StringUtils.isEmpty(sub_type)
					|| StringUtils.isEmpty(upload_sys_id) || StringUtils.isEmpty(branch_code)
					|| StringUtils.isEmpty(format) || StringUtils.isEmpty(content)
					|| StringUtils.isEmpty(name)) {
				throw new Exception("未获取到有效参数(T400010req)");
			}
		 /* String msg=isValied(format,content);
		  if(null!=msg)
			  throw new Exception(msg);*/
		}	
	}
	
	/*public static void main(String[] args) {
		CheckUtil t=new CheckUtil();
		System.out.println(t.isValied(".doc", "hhlsjdflhsd"));
	}*/
}
