package com.jjb.unicorn.socket;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.handler.ShortTermClientIoHandler;
import com.jjb.unicorn.socket.session.SessionKey;

/**
 * 短连接的报文客户端实现，适合的工作场景如下：在建立连接后发送报文，并同步等待响应报文，接收到响应后，连接断开。需配合
 * {@link ShortTermClientIoHandler}或其子类一起使用。
 * 
 * @author BIG.CPU
 * 
 */
public class ShortTermSocketClient {

	public NioSocketConnector connector;

	public NioSocketConnector getConnector() {
		return connector;
	}

	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}

	/**
	 * 发送报文
	 * 
	 * @param message
	 *            要发送的报文
	 * 
	 * @return 服务器端返回的消息
	 * 
	 */
	public YakMessage write(YakMessage message) {
		try {
			// 建立连接
			ConnectFuture connectFuture = connector.connect();
			connectFuture.awaitUninterruptibly();
			IoSession session = connectFuture.getSession();
			// 发送消息
			session.write(message);
			// 在连接关闭前阻塞等待
			session.getCloseFuture().awaitUninterruptibly();
			// 判断处理过程中是否有异常
			if (session.containsAttribute(SessionKey.EXCEPTION_KEY_NAME)) {
				throw (Throwable) session
						.getAttribute(SessionKey.EXCEPTION_KEY_NAME);
			}
			// 得到响应
			YakMessage result = (YakMessage) session
					.getAttribute(SessionKey.RESPONSE_KEY_NAME);
			// 重置响应属性
			session.setAttribute(SessionKey.RESPONSE_KEY_NAME, null);
			// 返回响应报文
			return result;
		} catch (Throwable e) {
			throw new RuntimeException("报文传输过程中出现异常,请检查日志以及代码", e);
		}
	}
}
