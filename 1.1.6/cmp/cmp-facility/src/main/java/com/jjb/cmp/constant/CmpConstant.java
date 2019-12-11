package com.jjb.cmp.constant;

import org.springframework.stereotype.Component;

/**
 * @Description: 内容平台使用的用常量及配置化变量
 * @author JYData-R&D-HN
 * @date 2018年12月1日 上午11:43:00
 * @version V1.0
 */
@Component
public class CmpConstant{
	// 公共log信息
	public static final String BEGINING = "_begin..."; // 开始
	public static final String END = "_end..."; // 结束
	public static final String SEPARATOR = "/";// 路径分隔符
	public static final String POINT = ".";// Point
	public static final String DEF_SYSTEM_ID = "CMP"; // 默认系统ID-CMP（内容管理平台）
	public static final String DEF_BRANCH_CODE = "06101"; // 默认机构网点
	public static final String CONTENT_STATUS = "A"; // 内容状态-A:有效

	public static final String FILENAME = "filename";// 文件名称Key
	public static int maxFileSize = 100 * 1024 * 1024;// 文件最大的大小
	
	/**
	 * 文件服务器地址
	 */
	public static String PROP_KEY_SERVER_ADDR;
	/**
	 * 最大连接数 并发量较大的话可加大该连接数
	 *fdMaxStorageConnection
	 */
	public static int PROP_KEY_MAX_STORAGE_CONNECTION;
	/**
	 * 连接超时时间
	 */
	public static String PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS;
	/**
	 * 运行超时时间
	 */
	public static String PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS;
	/**
	 * 编码格式
	 */
	public static String PROP_KEY_CHARSET;
	/**
	 * Token 防盗链功能
	 */
	public static String PROP_KEY_HTTP_ANTI_STEAL_TOKEN;
	/**
	 * 密钥
	 */
	public static String PROP_KEY_HTTP_SECRET_KEY;
	/**
	 * Trackerserver Port 端口<br>
	 */
	public static String PROP_KEY_HTTP_TRACKER_HTTP_PORT;
	/**
	 * Tracker Server, 可配置多个或一个，若多服务器则已分号","隔开</br>
	 * 如：fastdfs.trackerServers=10.0.11.201:22122,10.0.11.202:22122,10.0.11.203:22122
	 */
	public static String PROP_KEY_TRACKER_SERVERS;
	
	
}
