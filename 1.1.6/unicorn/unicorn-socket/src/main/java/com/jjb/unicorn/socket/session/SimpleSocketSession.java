package com.jjb.unicorn.socket.session;

import org.apache.mina.core.session.IoSession;

public class SimpleSocketSession implements WriteableSocketSession {

	private IoSession ioSession;

	public SimpleSocketSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}

	@Override
	public void connect() {
	}

	@Override
	public void write(Object message) {
		ioSession.write(message);
	}

	@Override
	public void close() {
		ioSession.close(false);
	}

	@Override
	public IoSession getSession() {
		return ioSession;
	}

}
