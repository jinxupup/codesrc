package com.jjb.acl.gmp.sdk;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.remoting.support.RemoteInvocation;

import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.AmqpContextHolder;
import com.jjb.unicorn.facility.util.TradeIdWorker;
/**
 * 把机构和用户名作为消息头来传递
 * @author LI.J
 *
 */
public class GMPMessageConverter extends SimpleMessageConverter 
{
	public static final String ORG_HEADER = "jyd.org";
	public static final String USERNAME_HEADER = "jyd.username";
	public static final String BRANCHID_HEADER = "jyd.branchid";
	public static final String BRANCHIDS_HEADER = "jyd.branchids";
	public static final String  CHANNELID_HEADER ="jyd.channelid";
	public static final String jydTRADEID = "jyd.jydtradeid";
	
	
	private static final String filterRpcMethds="updateProcessStatus";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected Message createMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
		messageProperties.setHeader(ORG_HEADER, OrganizationContextHolder.getOrg());
		String username = OrganizationContextHolder.getUserNo();
		String branchId = OrganizationContextHolder.getBranchCode();
		String jydTradeId=AmqpContextHolder.getJydSrcTradeId();
		boolean logFlag=true;
		if(jydTradeId == null){
			jydTradeId=TradeIdWorker.getInstance().getLocTraderId();
			if(object !=null && object.getClass().getName().equals("org.springframework.remoting.support.RemoteInvocation")){
				String  method=((RemoteInvocation)object).getMethodName();
				if(method !=null && filterRpcMethds.indexOf(method) !=-1){
					logFlag=false;
				}
			}
			if(logFlag){
				logger.info("jyd_TRADE_ID："+jydTradeId);
			}
		}
		if (username != null)
			messageProperties.setHeader(USERNAME_HEADER, username);
		if (branchId != null)
			messageProperties.setHeader(BRANCHID_HEADER, branchId);
		if(jydTradeId != null)
			messageProperties.setHeader(jydTRADEID, jydTradeId);
		return super.createMessage(object, messageProperties);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object fromMessage(Message message) throws MessageConversionException
	{
		if (message != null)
		{
			Map<String, Object> headers = message.getMessageProperties().getHeaders();
			OrganizationContextHolder.setOrg((String)headers.get(ORG_HEADER));
			String username = (String)headers.get(USERNAME_HEADER);
			OrganizationContextHolder.setUserNo(username);	//如果没有也要清掉
			String branchId = (String) headers.get(BRANCHID_HEADER);
			OrganizationContextHolder.setBranchCode(branchId);
			String jydTradeId = (String) headers.get(jydTRADEID);
			
			if(jydTradeId == null){
				jydTradeId=TradeIdWorker.getInstance().getLocTraderId();
			}
			AmqpContextHolder.setJydSrcTradeId(jydTradeId);
			AmqpContextHolder.setCurrentOrg((String)headers.get(ORG_HEADER));
			AmqpContextHolder.setUsername(username);

		}		
		return super.fromMessage(message);
	}
}
