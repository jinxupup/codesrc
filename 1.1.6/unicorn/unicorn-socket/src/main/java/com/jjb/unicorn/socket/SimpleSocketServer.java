package com.jjb.unicorn.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.socket.codec.YakDecodeFilterChain;

/**
 * 简单报文处理服务器端，对应每个端口单线程收发，将消息交由{@link YakDecodeFilterChain}和{@link IoHandler}
 * 来处理。对于复杂些的多线程场景，可使用不同的{@link IoHandler}实现达到效果。
 * 
 * @author BIG.CPU
 * 
 */
public class SimpleSocketServer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 监听端口
	private String[] ports;

	public NioSocketAcceptor acceptor;

	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public String[] getPorts() {
		return ports;
	}

	public void setPorts(String[] ports) {
		this.ports = ports;
	}

	/**
	 * 启动服务，监听指定端口
	 */
	public void bind() {
		try {
			for (String port : ports) {
				acceptor.setReuseAddress(true);
				acceptor.bind(new InetSocketAddress(Integer.valueOf(port)));
				logger.info("服务已启动，监听端口：{},地址重用状态：{}",port,acceptor.isReuseAddress());
			}
		} catch (IOException e) {
			logger.error("端口被占用或其他I/O异常，服务启动失败，请重试", e);
			System.exit(1);
		}
	}

	/**
	 * 解除端口监听
	 */
	public void unbind() {
		for (String port : ports) {
			acceptor.unbind(new InetSocketAddress(Integer.valueOf(port)));
			logger.info("服务已从端口：" + port + "解除监听");
		}
	}

	/**
	 * 服务关闭，释放资源
	 */
	public void shutDown() {
		acceptor.dispose(true);
		logger.info("服务已关闭");
	}

}
