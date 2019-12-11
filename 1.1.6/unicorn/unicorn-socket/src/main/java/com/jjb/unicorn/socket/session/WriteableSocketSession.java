package com.jjb.unicorn.socket.session;

import java.io.IOException;

import org.apache.mina.core.session.IoSession;

public interface WriteableSocketSession {

	public void connect() throws IOException;

	public void write(Object message) throws IOException;

	public void close() throws IOException;

	public IoSession getSession();

}
