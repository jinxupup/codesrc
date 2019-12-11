package com.jjb.cmp.app.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.jjb.cmp.constant.CmpConstant;

/**
 * spring mvc监听器，加载内容管理平台需要的配置项
 * @author hp
 *
 */
/**
 * @author hp
 *
 */
@Service
public class CmPropListener implements ApplicationListener<ContextRefreshedEvent>{

	/**
	 * 文件服务器地址
	 */
	@Value("#{env['fastdfsFileServerAddr']?:'127.0.0.1:80'}")
	public String fdFileServerAddr;

	/**
	 * 最大连接数 并发量较大的话可加大该连接数
	 */
	//@Value("#{env['fastdfs.maxStorageConnection'?:30]}")
	//public int fdMaxStorageConnection;
	@Value("#{env['fastdfsMaxStorageConnection']?:30}")
	public int fdMaxStorageConnection;
	/**
	 * 连接超时时间
	 */
	@Value("#{env['fastdfsConnectTimeoutInSeconds']?:'10'}")
	public String fdConnectTimeoutInSeconds;
	/**
	 * 运行超时时间
	 */
	@Value("#{env['fastdfsNetworkTimeoutInSeconds']?:'30'}")
	public String fdNetworkTimeoutInSeconds;
	/**
	 * 编码格式
	 */
	@Value("#{env['fastdfsCharset']?:'UTF-8'}")
	public String fdCharset;
	/**
	 * Token 防盗链功能
	 */
	@Value("#{env['fastdfsHttpAntiStealToken']?:'false'}")
	public String fdHttpAntiStealToken;
	/**
	 * 密钥
	 */
	@Value("#{env['fastdfsHttpSecretKey']?:''}")
	public String fdHttpSecretKey;
	/**
	 * Trackerserver Port 端口<br>
	 */
	@Value("#{env['fastdfsHttpTrackerHttpPort']?:'20080'}")
	public String fdHttpTrackerHttpPort;
	/**
	 * Tracker Server, 可配置多个或一个，若多服务器则已分号","隔开</br>
	 * 如：fastdfs.trackerServers=10.0.11.201:22122,10.0.11.202:22122,10.0.11.203:22122
	 */
	@Value("#{env['fastdfsTrackerServers']?:'127.0.0.1'}")
	public String fdTrackerServers;

	/**
	 * 启动加载执行</br>
	 * 加载配置项至 CmpPropAndConstant
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()==null) {
			CmpConstant.PROP_KEY_SERVER_ADDR = this.fdFileServerAddr;
			CmpConstant.PROP_KEY_MAX_STORAGE_CONNECTION = this.fdMaxStorageConnection;
			CmpConstant.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS = this.fdConnectTimeoutInSeconds;
			CmpConstant.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS = this.fdNetworkTimeoutInSeconds;
			CmpConstant.PROP_KEY_CHARSET = this.fdCharset;
			CmpConstant.PROP_KEY_HTTP_ANTI_STEAL_TOKEN = this.fdHttpAntiStealToken;
			CmpConstant.PROP_KEY_HTTP_SECRET_KEY = this.fdHttpSecretKey;
			CmpConstant.PROP_KEY_HTTP_TRACKER_HTTP_PORT = this.fdHttpTrackerHttpPort;
			CmpConstant.PROP_KEY_TRACKER_SERVERS = this.fdTrackerServers;
		}

	}
}
