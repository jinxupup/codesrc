package com.jjb.unicorn.socket.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.socket.PooledSocketClient;
import com.jjb.unicorn.socket.session.SessionKey;

/**
 * 双工长连接I/O处理句柄实现类，必须作为{@link PooledSocketClient}的IO处理句柄，可继承此类完成定制的功能
 * 
 * @author BIG.CPU
 * 
 */
public class DuplexLongTermClientIoHandler extends IoHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("连接闲置");
		// 等待响应超时， 唤醒等待的线程
		/*synchronized (session) {
			Thread t = (Thread) session.getAttribute(SessionKey.WAIT_THREAD);
			if (t != null) {
				t.interrupt();
			}
		}*/
	}
 
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// 将异常放在session的属性中
		session.setAttribute(SessionKey.EXCEPTION_KEY_NAME, cause);
		// 唤醒等待的线程
		synchronized (session) {
			session.setAttribute(SessionKey.NOTIFY_FLAG, new Object());
			session.notifyAll();
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// 将响应放在session的属性中
		session.setAttribute(SessionKey.RESPONSE_KEY_NAME, message);
		// 唤醒等待的线程
		synchronized (session) {
			session.setAttribute(SessionKey.NOTIFY_FLAG, new Object());
			session.notifyAll();
		}
	}
	
}
