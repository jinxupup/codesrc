package com.jjb.unicorn.socket;

import java.rmi.ConnectException;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.Lifecycle;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.session.SessionKey;

/**
 * 报文客户端，需要通过socket连接池来构造
 * 
 * @author BIG.CPU
 * 
 */
public class PooledSocketClient {

	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	private ConfigurableApplicationContext applicationContext;
	/**
	 * Socket连接池
	 */
	private GenericObjectPool pool;

	public GenericObjectPool getPool() {
		return pool;
	}

	public void setPool(GenericObjectPool pool) {
		this.pool = pool;
	}

	/**
	 * 发送报文并接收服务器端的响应
	 * 
	 * @param message
	 *            要发送的报文
	 * 
	 * @return 服务器端的响应报文
	 * 
	 * @throws ConnectException
	 *             Socket连接异常
	 */
	public YakMessage write(YakMessage message) throws ConnectException {
		try {

			// 从连接池中获得连接，使用连接发送消息
			IoSession session;
			try {
				session = (IoSession) pool.borrowObject();
			} catch (Exception e) {
				
				throw new ConnectException("连接池异常", e);
			}
			session.write(message);
			
			// 等待IOHandler唤醒
			try {
				synchronized (session) {
					int count = 0;
					while (session.getAttribute(SessionKey.NOTIFY_FLAG) == null && count < 10) {
						session.setAttribute(SessionKey.WAIT_THREAD, Thread.currentThread());
						session.wait(1000);
						count++;
						session.removeAttribute(SessionKey.WAIT_THREAD);
					}
					session.setAttribute(SessionKey.NOTIFY_FLAG, null);
					if (count >= 10) {
						logger.warn("等待响应报文超时，count={},session={}" , count,session);
						session.closeNow();//脏session，需要废弃
						pool.invalidateObject(session);
						return null;
					}
				}
			} catch (InterruptedException e) {
				logger.warn("等待响应报文超时",e);
				session.removeAttribute(SessionKey.WAIT_THREAD);
				session.removeAttribute(SessionKey.EXCEPTION_KEY_NAME);
//				session.closeNow();
				pool.invalidateObject(session);
				return null;
			}
			
			// 处理异常
			if (session.containsAttribute(SessionKey.EXCEPTION_KEY_NAME)) {
				Throwable throwable = (Throwable) session
						.getAttribute(SessionKey.EXCEPTION_KEY_NAME);
				session.setAttribute(SessionKey.EXCEPTION_KEY_NAME, null);
				session.closeNow();
				pool.invalidateObject(session);
				throw throwable;
			}
			
			// 取得响应
			YakMessage result = (YakMessage) session
					.getAttribute(SessionKey.RESPONSE_KEY_NAME);
			session.setAttribute(SessionKey.RESPONSE_KEY_NAME, null);

			// 将连接放回连接池
			try {
				pool.returnObject(session);
			} catch (Exception e) {
				throw new ConnectException("连接池异常");
			}
			
			// 返回响应报文
			return result;
		} catch (Throwable e) {
			throw new RuntimeException("报文传输过程中出现异常,请检查日志以及代码", e);
		}
	}

	
	public void testConnect(){
		try {
			IoSession session = (IoSession) pool.borrowObject();
			pool.returnObject(session);
		} catch (Exception e) {
			logger.error("加密机连接不上 ，进程即将停止",e);
			((Lifecycle)applicationContext.getBean("gmpHeartbeatProcessor")).stop();
			System.exit(0);
		}
	}
	
	public boolean tryConnect() throws Exception{
		try {
			IoSession session = (IoSession) pool.borrowObject();
			pool.returnObject(session);
			return true;
		} catch (Exception e) {
			logger.warn("尝试连接异常"+e);
			throw e;
		}
	}
}
