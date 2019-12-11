package com.jjb.unicorn.socket;

import java.util.List;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * NioAcceptor构造辅助类
 * 
 * @author BIG.CPU
 * 
 */
public class YakNioAcceptorHelper implements InitializingBean {
	
	@Value("#{env['nioPoolSize'] ?: 0}")
	private int nioPoolSize;
	@Value("#{env['nioBacklog'] ?: 0}")
	private int nioBacklog;

	private NioSocketAcceptor acceptor;
	
	private List<IoFilter> ioFilters;

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public List<IoFilter> getIoFilters() {
		return ioFilters;
	}

	public void setIoFilters(List<IoFilter> ioFilters) {
		this.ioFilters = ioFilters;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(nioPoolSize > 0) {
			acceptor = new NioSocketAcceptor(nioPoolSize);
		} else {
			acceptor = new NioSocketAcceptor();
		}
		
		if (nioBacklog > 0) {
			acceptor.setBacklog(nioBacklog);
		}
		
		if (ioFilters != null) {
			for (IoFilter filter : ioFilters) {
				acceptor.getFilterChain().addLast(filter.getClass().getName(),
						filter);
			}
		}
	}
}
