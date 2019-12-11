package com.jjb.unicorn.socket.session;

import java.io.IOException;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class ConfigableSocketSession implements WriteableSocketSession {

	private NioSocketConnector connector;

	private IoSession session;

	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}

	public void connect() throws IOException {
		ConnectFuture future = connector.connect();
		future.awaitUninterruptibly();
		if (!future.isConnected()) {
			throw new IOException("连接失败,无法建立连接");
		}
		session = future.getSession();
	}
	
	/**
	 * 发送报文
	 * 
	 * @param message
	 *            要发送的报文
	 * @throws IOException 
	 * 
	 */
	public void write(Object message) throws IOException {
		if (session != null && session.isConnected()) {
			session.write(message);
		} else {
			connect();
			write(message);
		}
	}

	@Override
	public void close() {
		session.close(false);
	}

	@Override
	public IoSession getSession() {
		return session;
	}

}
