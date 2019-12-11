package com.jjb.acl.gmp.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.gmp.TmCommandHistDao;
import com.jjb.acl.biz.gmp.TmNodeDao;
import com.jjb.acl.infrastructure.TmCommandHist;
import com.jjb.acl.infrastructure.TmNode;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

@Service
public class SSHFacility {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("#{env['sshCharset']?:'gbk'}")
	private String charset;
	
	@Value("#{env['sshTimeout']?:5000}")
	private int timeout;
	@Autowired
	private TmCommandHistDao tmCommandHistDao;
	@Autowired
	private TmNodeDao tmNodeDao;
/*	@Autowired
	private RTmNode rNode;
	@Autowired
	private RTmCommandHist commandHist;*/


	public int issueCommand(String nodeCode, String command) throws ProcessException
	{
		return issueCommand(nodeCode, command, null);
	}

	
	/**
	 * @param nodeCode
	 * @param command
	 * @param env 使用 {@link LinkedHashMap}保证顺序
	 * @return
	 * @throws ProcessException
	 */
	@Transactional(noRollbackFor=ProcessException.class)
	public int issueCommand(String nodeCode, String command, LinkedHashMap<String, String> env) throws ProcessException
	{
		TmNode node = tmNodeDao.findOne(nodeCode);
		if (node == null)
			throw new IllegalArgumentException("结点" + nodeCode + "不存在");
		
		//最终需要export的环境变量
		LinkedHashMap<String, String> exports = new LinkedHashMap<String, String>();

		//固定写入APPHOME
		exports.put("APPHOME", node.getAppHome());

		//处理node属性
		if (StringUtils.isNotBlank(node.getProperties()))
		{
			Properties props = new Properties();
			try {
				props.load(new ByteArrayInputStream(node.getProperties().getBytes()));
			} catch (IOException e) {
				//不可能出IO异常的
			}
			for (Entry<Object, Object> entry : props.entrySet())
				exports.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}
		//处理传入的实例环境定义
		if (env != null)
			exports.putAll(env);
		
		Connection conn = new Connection(node.getHost(), node.getPort());
		try {
			logger.info("登录SSH服务器 {}@{}:{}", node.getUsername(), node.getHost(), node.getPort());
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPublicKey(node.getUsername(), node.getPrivateKey().toCharArray(), node.getKeyPassword());

			if (!isAuthenticated)
			{
				logger.warn("登录失败");
				throw new ProcessException("服务器登录失败");
			}
			
			Session sess = conn.openSession();
			//使用StreamGlobber的原因参见它的javadoc
			InputStream stdout = new StreamGobbler(sess.getStdout());
			InputStream stderr = new StreamGobbler(sess.getStderr());
			
			//设置环境
			//FIXME 注入风险，需要编码
			StringBuilder envStr = new StringBuilder();
			for (Entry<String, String> entry: exports.entrySet()) {
				envStr.append(MessageFormat.format("export {0}=\"{1}\" && ", entry.getKey(), entry.getValue()));
			}

			//执行命令
			logger.debug("执行命令(export已隐藏):{}", command);
			sess.execCommand(envStr + command);
			
			String strStdout = readAll(stdout);
			String strStderr = readAll(stderr);
			
			TmCommandHist hist = new TmCommandHist();
			hist.setNodeCode(nodeCode);
			hist.setIssueCommandLine(command);
			hist.setStdout(strStdout);
			hist.setStderr(strStderr);
			hist.setIssueTime(new Date());
			hist.setIssueUserId(OrganizationContextHolder.getUserNo());
			hist.setIssueUserOrg(OrganizationContextHolder.getOrg());
			tmCommandHistDao.saveTmCommandHist(hist);
//			em.persist(hist);

			//等执行结束
			int cond = sess.waitForCondition(ChannelCondition.EXIT_STATUS|ChannelCondition.TIMEOUT, timeout);
			if ((cond & ChannelCondition.TIMEOUT) != 0)
				throw new ProcessException("操作超时");

			hist.setExitCode(sess.getExitStatus());
			
			return hist.getExitCode();
			
		}
		catch (IOException e)
		{
			logger.error("IO失败", e);
			throw new ProcessException("服务器IO失败，请参见服务器日志");
		}
		finally
		{
			conn.close();
		}
	}
	
	private String readAll(InputStream is) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(is, charset);
		
		char buf[] = new char[8];
		while (true)
		{
			int n = isr.read(buf);
			if (n == -1)
				break;
			sb.append(buf, 0, n);
		}
		
		return sb.toString();
	}
	
	/**
	 * 公用的替换变量的函数，以求统一
	 * @param line
	 * @return
	 */
	public static String replaceParameter(String line, String name, Object value)
	{
		return StringUtils.replace(line, "$" + name, String.valueOf(value));
	}
}
