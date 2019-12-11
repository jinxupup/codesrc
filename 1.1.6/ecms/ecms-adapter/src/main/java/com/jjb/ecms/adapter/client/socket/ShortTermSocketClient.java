package com.jjb.ecms.adapter.client.socket;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.jjb.unicorn.facility.service.YakMessage;

/**
 * 
 * 消息处理
 * @author hn
 *
 */
public class ShortTermSocketClient  extends IoHandlerAdapter{

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
	 * @param message  要发送的报文
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
			if (session.containsAttribute("Exception")) {
				throw (Throwable) session.getAttribute("Exception");
			}
			// 得到响应
			YakMessage result = (YakMessage) session.getAttribute("result");
			// 重置响应属性
			session.setAttribute("result", null);
			// 返回响应报文
			return result;
		} catch (Throwable e) {
			throw new RuntimeException("报文传输过程中出现异常,请检查日志以及代码", e);
		}
	}
}
