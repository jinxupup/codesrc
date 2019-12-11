package com.jjb.acl.gmp.sdk;


import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;

import com.jjb.unicorn.facility.util.CommandExecutionUtil;

/**
 * <p>Description: 随机批量命令执行监听器，继承自{@link CommandExecutionListener}对于命令进行特殊定制</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: IndieCommandExecutionListener
 * @author LI.H
 * @date 2016年2月25日 上午10:07:21
 * @version 1.0
 */
public class IndieCommandExecutionListener extends CommandExecutionListener implements InitializingBean {

	private final static Logger logger = LoggerFactory.getLogger(IndieCommandExecutionListener.class);
	
	/**
	 * <p>执行日期类型</p>
	 * <p>true为批量日期</p>
	 * <p>false为业务日期</p>
	 * <p>默认为false</p>
	 */
	private boolean batchDateFlag = false;
	
	private CommandExecutionUtil indieCommandExecutionUtil;
	
	/**
	 * <p>日间批量脚本需要参数</p>
	 */
	private List<String> commandParamKey;
	
	/**
	 * <p>目的：执行决定，决定是否执行命令，同时为命令拼接参数</p>
	 * <p>承诺：当前执行上下文已经执行成功命令或当环境变量中不存在key值对应的value则不执行命令</p>
	 * @param key
	 */
	@Override
	protected void executionOrder(ExecutionContext ec, String excutionKey, String commandKey, JobParameters jobParameters) {
		
		if (!ec.containsKey(excutionKey)) {
			ec.put(excutionKey, BatchStatus.UNKNOWN);
		}
		if (BatchStatus.COMPLETED != ec.get(excutionKey)) {
			try {
				if (indieCommandExecutionUtil.getProps().containsKey(commandKey)) {
					StringBuffer command = new StringBuffer((String) indieCommandExecutionUtil.getProps().get(commandKey));
					command.append(SPACE).append(new SimpleDateFormat(DATE_FOMART).format(batchDateFlag ? batchStatusFacility.getBatchDate() : batchStatusFacility.getSystemStatus().getBusinessDate()));
					for (String paramKey : commandParamKey) {
						command.append(SPACE).append(jobParameters.getString(paramKey));
					}
					
					indieCommandExecutionUtil.runCommand(command.toString());
				}
			} catch (Exception e) {
				throw new RuntimeException("批量监听器执行此命令出现异常，请人工干预检查命令执行结果，之后重启批量。");
			}
			ec.put(excutionKey, BatchStatus.COMPLETED);
		} else {
			logger.info("断点续批，监听器已经执行过环境变量[{}]命令，不再执行。", commandKey);
		}
		
	}

	/**
	 * <p>Title: afterPropertiesSet</p> 
	 * <p>目的：记录Bean初始化属性</p>
	 * <p>承诺：当前仅打印日志</p>
	 * @throws Exception 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("当前使用日期为[{}]", batchDateFlag ? "批量日期" : "业务日期");
		if (null == commandParamKey || 0 == commandParamKey.size()) {
			logger.debug("该监听器无其他命令参数");
		} else {
			for (String key : commandParamKey) {
				logger.debug("该监听器存在以下命令参数Key值：[{}]", key);
			}
		}
	}
	
	public void setBatchDateFlag(boolean batchDateFlag) {
		this.batchDateFlag = batchDateFlag;
	}
	
	public void setIndieCommandExecutionUtil(CommandExecutionUtil indieCommandExecutionUtil) {
		this.indieCommandExecutionUtil = indieCommandExecutionUtil;
	}

	public void setCommandParamKey(List<String> commandParamKey) {
		this.commandParamKey = commandParamKey;
	}

}
