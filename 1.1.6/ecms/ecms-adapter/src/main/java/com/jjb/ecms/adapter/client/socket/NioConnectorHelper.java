package com.jjb.ecms.adapter.client.socket;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 
 * NioConnector构造辅助类
 * 
 * @author hn
 *
 */
public class NioConnectorHelper extends IoHandlerAdapter {

	private NioSocketConnector connector = new NioSocketConnector();

	private List<IoFilter> ioFilters;

	public NioSocketConnector getConnector() {
		return connector;
	}

	public List<IoFilter> getIoFilters() {
		return ioFilters;
	}

	public void setIoFilters(List<IoFilter> ioFilters) {
		this.ioFilters = ioFilters;
	}

	@PostConstruct
	public void init() {
		if (ioFilters != null) {
			for (IoFilter filter : ioFilters) {
				connector.getFilterChain().addLast(filter.getClass().getName(),
						filter);
			}
		}
	}
}
