package com.jjb.acl.access.filter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sun.net.www.protocol.file.FileURLConnection;

/**
 * 
 * @author hp
 *
 */
@SuppressWarnings("restriction")
public class AclTagLibResourceFilter implements Filter {

	private static final String RESOURCE_PACKAGE_PATH = "/assets";

	private static final String RESOURCE_CHARSET = "UTF-8";

	private static final String DEFAULT_MINE_TYPE = "application/octet-stream";

	private static String resourcePath;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ResourceMode resourceMode;

	private static enum ResourceMode {
		Dir, Jar
	}

	private static final Map<String, String> MINE_TYPE_MAP;

	static {
		MINE_TYPE_MAP = new HashMap<String, String>();
		MINE_TYPE_MAP.put("js", "application/javascript;charset=" + RESOURCE_CHARSET);
		MINE_TYPE_MAP.put("css", "text/css;charset=" + RESOURCE_CHARSET);
		MINE_TYPE_MAP.put("gif", "image/gif");
		MINE_TYPE_MAP.put("jpg", "image/jpeg");
		MINE_TYPE_MAP.put("jpeg", "image/jpeg");
		MINE_TYPE_MAP.put("png", "image/png");

	}

	public static String getResourcePath() {
		return AclTagLibResourceFilter.resourcePath;
	}

	private static void setResourcePath(String resourcePath) {
		AclTagLibResourceFilter.resourcePath = resourcePath;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String resPath = filterConfig.getInitParameter("resourcePath");

		if (!resPath.startsWith("/")) {
			resPath = "/" + resPath;
		}

		setResourcePath(resPath);

		String rootPath = filterConfig.getServletContext().getRealPath("/");
		if (rootPath != null) { // 如果web工程是目录方式运行

			String dirPath = filterConfig.getServletContext().getRealPath(resPath);
			if (dirPath == null || dirPath.equals("") || dirPath.toUpperCase().equals("null")) {
				logger.warn("不能根据[" + resPath + "]获取当前应用服务部署目录...");
				dirPath = filterConfig.getServletContext().getRealPath("/");
				dirPath = dirPath + "assets";
			}
			logger.info("创建jar包资源文件相关路径 '" + dirPath + "'");
			File dir = null;
			try {
				dir = new File(dirPath);
				if (dir != null) {

					FileUtils.deleteQuietly(dir); // 清除老资源
					FileUtils.forceMkdir(dir); // 重新创建资源目录
				}
			} catch (Exception e) {
				logger.error("Error creating TagLib Resource dir", e);
			}

			try {
				copyResourcesRecursively(this.getClass().getResource(RESOURCE_PACKAGE_PATH), dir); // 复制classpath中的资源到目录
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			resourceMode = ResourceMode.Dir; // 设置为目录模式
		} else {
			resourceMode = ResourceMode.Jar; // 设置为jar包模式
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ResourceMode:" + resourceMode);
		}
	}

	@Autowired
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		switch (resourceMode) {
		case Dir:
			chain.doFilter(request, response);
			break;
		case Jar: {
			HttpServletRequest req = (HttpServletRequest) request;
			String path = req.getRequestURI().substring(req.getContextPath().length()); // uri去掉web上下文

			HttpServletResponse rep = (HttpServletResponse) response;

			if (path.startsWith(getResourcePath() + "/")) { // resourcePath必须与url-pattern一致

				path = path.substring(getResourcePath().length()); // uri去掉resourcePath

				try {
					URL resource = this.getClass().getResource(RESOURCE_PACKAGE_PATH + path); // 可能存在潜在安全问题
					if (resource == null) { // 如果在类路径中没有找到资源->404
						rep.sendError(HttpServletResponse.SC_NOT_FOUND);
					} else {
						InputStream inputStream = readResource(resource);
						if (inputStream != null) { // 有inputstream说明已经读到jar中内容
							String ext = FilenameUtils.getExtension(path).toLowerCase();
							String contentType = MINE_TYPE_MAP.get(ext);
							if (contentType == null) {
								contentType = DEFAULT_MINE_TYPE;
							}
							rep.setContentType(contentType); // 设置内容类型

							ServletOutputStream outputStream = rep.getOutputStream();
							try {
								int size = IOUtils.copy(inputStream, outputStream); // 向输出流输出内容
								rep.setContentLength(size);
							} finally {
								IOUtils.closeQuietly(inputStream);
								IOUtils.closeQuietly(outputStream);
							}
						} else { // 没有inputstream->404
							rep.sendError(HttpServletResponse.SC_NOT_FOUND);
						}
					}

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					rep.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			} else {
				logger.error("MUST set url-pattern=\"" + resourcePath + "/*\"!!");
				rep.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

		}
			break;
		}

	}

	@Override
	public void destroy() {
	}

	private InputStream readResource(URL originUrl) throws Exception {
		InputStream inputStream = null;
		URLConnection urlConnection = originUrl.openConnection();
		if (urlConnection instanceof JarURLConnection) {
			inputStream = readJarResource((JarURLConnection) urlConnection);
		} else if (urlConnection instanceof FileURLConnection) {
			File originFile = new File(originUrl.getPath());
			if (originFile.isFile()) {
				inputStream = originUrl.openStream();
			}
		} else {
			throw new Exception("URLConnection[" + urlConnection.getClass().getSimpleName()
					+ "] is not a recognized/implemented connection type.");
		}

		return inputStream;
	}

	private InputStream readJarResource(JarURLConnection jarConnection) throws Exception {
		InputStream inputStream = null;
		JarFile jarFile = jarConnection.getJarFile();
		if (!jarConnection.getJarEntry().isDirectory()) { // 如果jar中内容为目录则不返回inputstream
			inputStream = jarFile.getInputStream(jarConnection.getJarEntry());
		}
		return inputStream;
	}

	private void copyResourcesRecursively(URL originUrl, File destination) throws Exception {

		URLConnection urlConnection = originUrl.openConnection();
		if (urlConnection instanceof JarURLConnection) {
			copyJarResourcesRecursively(destination, (JarURLConnection) urlConnection);
		} else if (urlConnection instanceof FileURLConnection) {
			FileUtils.copyDirectory(new File(originUrl.getPath()), destination); // 如果不是jar则采用目录copy
			if (logger.isDebugEnabled()) {
				logger.debug("copy dir '" + originUrl.getPath() + "' --> '" + destination.getPath() + "'");
			}
		} else {
			throw new Exception("URLConnection[" + urlConnection.getClass().getSimpleName()
					+ "] is not a recognized/implemented connection type.");
		}
	}

	private void copyJarResourcesRecursively(File destination, JarURLConnection jarConnection) throws IOException {
		JarFile jarFile = jarConnection.getJarFile();
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) { // 遍历jar内容逐个copy
			JarEntry entry = entries.nextElement();
			if (entry.getName().startsWith(jarConnection.getEntryName())) {
				String fileName = StringUtils.removeStart(entry.getName(), jarConnection.getEntryName());
				File destFile = new File(destination, fileName);
				if (!entry.isDirectory()) {
					InputStream entryInputStream = jarFile.getInputStream(entry);
					FileUtils.copyInputStreamToFile(entryInputStream, destFile);
					if (logger.isDebugEnabled()) {
						logger.debug(
								"copy jarfile to file '" + entry.getName() + "' --> '" + destination.getPath() + "'");
					}
				} else {
					FileUtils.forceMkdir(destFile);
					if (logger.isDebugEnabled()) {
						logger.debug("create dir '" + destFile.getPath() + "'");
					}
				}
			}
		}
	}
}
