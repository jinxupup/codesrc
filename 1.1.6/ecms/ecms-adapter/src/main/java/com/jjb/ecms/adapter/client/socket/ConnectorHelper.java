package com.jjb.ecms.adapter.client.socket;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.socket.YakNioConnectorHelper;
import com.jjb.unicorn.socket.codec.LVMessageDecoder;
import com.jjb.unicorn.socket.codec.LVMessageEncoder;
import com.jjb.unicorn.socket.codec.XmlDecoderFilter;
import com.jjb.unicorn.socket.codec.XmlEncoderFilter;
import com.jjb.unicorn.socket.codec.YakDecodeFilter;
import com.jjb.unicorn.socket.codec.YakDecodeFilterChain;
import com.jjb.unicorn.socket.codec.YakEncodeFilter;
import com.jjb.unicorn.socket.codec.YakEncodeFilterChain;

/**
 *@author H.N
 *@date:2016-7-6下午3:24:22
 *@description:
 *@version 1.0*
 *@parameter
 *@return 
 */
@Service
public class ConnectorHelper implements BeanFactoryAware{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private BeanFactory beanFactory = null;
	
	@Autowired
	private Properties env;
	
	@Autowired
	private ShortTermClientIoHandler ioHandler;
	
	@Resource(name="shortTermSocketClient")
	private ShortTermSocketClient shortTermSocketClient;
	
	@Value("#{env['connectTimeOutMillis']?:30000}")
	private Integer connectTimeOutMillis;
	
	@Value("#{env['lvMsgLength']?:8}")
	private Integer lvMsgLength;
	
	@Value("#{env['idleTime']?:65}")
	private int idleTime;
  
	private static final String charSet = "UTF-8";

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		
	}
	
	@SuppressWarnings("rawtypes")
	public  ShortTermSocketClient createConnector(String serviceId,
			String extHostIp,int extPort,String charset1,
			Integer connectTO, Integer lvLength){
		
		String listerIp = extHostIp;
		int listerPort = extPort;
		String charset =  charset1;
		
		if(StringUtils.isEmpty(extHostIp)){
			String[] listenAdress = createServerAdres(serviceId);
			if(StringUtils.isEmpty(extHostIp)){
				if(listenAdress.length != 3){
					logger.error("联机调用的服务端IP["+extHostIp+"],端口["+extPort+"]");
					throw new ProcessException(SysConstants.ERRS015_CODE, SysConstants.ERRS015_MES);
				}
				listerIp = listenAdress[0];
				listerPort = Integer.valueOf(listenAdress[1]);
				charset =  listenAdress[2];
				if(StringUtils.isBlank(charset)){
					charset = charSet;				
				}
			}
		}
		if(connectTO==null){
			connectTO = connectTimeOutMillis;
		}
		if(lvLength==null){
			lvLength = lvMsgLength;
		}
		logger.info("交易["+serviceId+"],IP["+extHostIp+"],端口["+extPort+"],"
				+ "编码格式["+charset+"],超时时间设置["+connectTO+"]");
		if(shortTermSocketClient.connector != null){
			shortTermSocketClient.connector.dispose();
		}
		NioSocketConnector connector = new NioSocketConnector();
		List<IoFilter> ioFilters = getIoFilters();
		if (ioFilters != null) {
			for (IoFilter filter : ioFilters) {
				
				if(filter instanceof ProtocolCodecFilter){
					try {
						ProtocolCodecFilter f = (ProtocolCodecFilter)filter;
						Class clazz = f.getClass();
						Field field = clazz.getDeclaredField("factory");
						field.setAccessible(true);
						ProtocolCodecFactory factory = (ProtocolCodecFactory) field.get(f);
						
						LVMessageDecoder decoder = (LVMessageDecoder)factory.getDecoder(null);
						decoder.setLengthSize(lvLength);
						YakDecodeFilterChain chain = decoder.getFilterChain();
						Class clazzDchain = chain.getClass();
						Field fieldChain = clazzDchain.getField("chain");
						fieldChain.setAccessible(true);
						LinkedList<YakDecodeFilter> chainList = (LinkedList<YakDecodeFilter>) fieldChain.get(chain);
						for(YakDecodeFilter decodeFilter : chainList){
							if(decodeFilter instanceof XmlDecoderFilter){
								XmlDecoderFilter xmlfFilter = (XmlDecoderFilter)decodeFilter;
								xmlfFilter.setCharset(charset);
							}
						}
						
						LVMessageEncoder encoder = (LVMessageEncoder)factory.getEncoder(null);
						encoder.setLengthSize(lvLength);
						YakEncodeFilterChain eChain = encoder.getFilterChain();
						Class clazzEchain = eChain.getClass();
						Field fieldEchain = clazzEchain.getField("chain");
						fieldEchain.setAccessible(true);
						LinkedList<YakEncodeFilter> eChainList = (LinkedList<YakEncodeFilter>) fieldEchain.get(eChain);
						for(YakEncodeFilter encodeFilter : eChainList){
							if(encodeFilter instanceof XmlEncoderFilter){
								XmlEncoderFilter xmlfFilter = (XmlEncoderFilter)encodeFilter;
								xmlfFilter.setCharset(charset);
							}
						}
					}  catch (Exception e) {
						throw new ProcessException("连接异常");
					}
					
				}
				connector.getFilterChain().addLast(filter.getClass().getName(),
						filter);
			}
		}
		InetSocketAddress socketAddress = new InetSocketAddress(listerIp, listerPort);
		connector.setDefaultRemoteAddress(socketAddress);
		connector.setConnectTimeoutMillis(connectTO);
		connector.setHandler(ioHandler);
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);
		shortTermSocketClient.setConnector(connector);

		return shortTermSocketClient;
	}
	
	private List<IoFilter> getIoFilters() {
		YakNioConnectorHelper helper=(YakNioConnectorHelper) beanFactory.getBean("clientConnectorHelper");
		return helper.getIoFilters();
	}
	
	
	public String[] createServerAdres(String serviceId){
		String listerAddress = env.getProperty("txn_"+serviceId);
		logger.debug("交易码：{}，连接信息：{}", serviceId, listerAddress);
		if(StringUtils.isBlank(listerAddress)){
			return null;
		}
		return listerAddress.split("\\|");
	}
	//如果txn_交易码后续有charset的话那么就用给定的charset，没有的话那么就默认为utf-8
	public String getcharset(String serviceId){
		String[] listenAddress = createServerAdres(serviceId);
		if(listenAddress != null && listenAddress.length >= 3){
			String charset = listenAddress[2];
			if(StringUtils.isNotBlank(charset)){
			   return charset;
			}else{
				return charSet;
			}
	}
		return charSet;
	}
	

}
