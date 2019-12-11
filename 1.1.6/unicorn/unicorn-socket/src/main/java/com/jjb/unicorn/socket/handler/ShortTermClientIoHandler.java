package com.jjb.unicorn.socket.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.socket.ShortTermSocketClient;
import com.jjb.unicorn.socket.session.SessionKey;

/**
 * 短连接客户端所用的IO处理句柄，一般配合{@link ShortTermSocketClient}一起使用
 * 
 * @author BIG.CPU
 * 
 */
public class ShortTermClientIoHandler extends IoHandlerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// 将异常放在session的属性中
		session.setAttribute(SessionKey.EXCEPTION_KEY_NAME, cause);
		// 关闭连接
		session.close(false);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// 将响应放在session的属性中
		session.setAttribute(SessionKey.RESPONSE_KEY_NAME, message);
		// 关闭连接
		session.close(false);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// 超过一定时间没有响应的时候关闭连接
		logger.warn("对方没有响应，等待超时，关闭连接");
		session.close(false);
	}
	
}
