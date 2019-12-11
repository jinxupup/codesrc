package com.jjb.unicorn.socket;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * socket客户端连接工厂，作为连接池的一部分，负责构造和析构连接。
 * 
 * @author BIG.CPU
 * 
 */
public class SocketClientConnectionFactory extends BasePoolableObjectFactory {

	private NioSocketConnector connector;

	public SocketClientConnectionFactory(NioSocketConnector connector) {
		this.connector = connector;
	}

	@Override
	public Object makeObject() throws Exception {
		// 判断connector状态，如不处于活动状态则抛出异常
		if (connector == null || connector.isDisposed()) {
			throw new IllegalStateException("connector状态异常");
		}
		// 创建连接并返回
		return connector.connect().awaitUninterruptibly().getSession();
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		if (obj != null) {
			IoSession session = (IoSession) obj;
			if (session.isConnected()) {
				session.close(false);
			}
		}
	}

	@Override
	public boolean validateObject(Object obj) {
		IoSession session = (IoSession) obj;
		// 判断连接状态
		if (session.isConnected()) {
			return true;
		}
		return false;
	}

}
