package com.jjb.cmp.app.controller.content;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assets/cmp_/download")
public class DownLoadController {
	
	 private Logger logger = LoggerFactory.getLogger(DownLoadController.class);
	
	/**
     * 根据url下载文件
	 * @throws Exception 
     */
    @RequestMapping(value = "/gf")
	public void downLoadFile(String url, HttpServletResponse response) throws Exception {
		try {
			URL urlF = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlF.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.connect();

			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buff = new byte[4096];
			int lent = -1;
			while ((lent = bis.read(buff)) != -1) {
				baos.write(buff, 0, lent);
			}
			bis.close();
			response.setHeader("Content-Disposition", "attachment;filename=" + url.substring(1 + url.lastIndexOf("/")));
			response.setHeader("Content-Length", String.valueOf(baos.size()));
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			out.write(baos.toByteArray());
			out.flush();
			out.close();
		} catch (Exception e) {

			logger.error("下载文件错误",e);
		}
	}

}
