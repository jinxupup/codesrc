package com.jjb.acl.gmp.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.gmp.TmProcessDao;
import com.jjb.acl.biz.gmp.TmSysStatusDao;
import com.jjb.acl.facility.enums.sys.ProcessRunningStatus;
import com.jjb.acl.gmp.api.ManagedComponentService;
import com.jjb.acl.gmp.api.ParameterRefreshRequest;
import com.jjb.acl.infrastructure.TmProcess;
import com.jjb.acl.infrastructure.TmSysStatus;
import com.jjb.acl.infrastructure.dto.TmProInstance;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.AESCryptoConverter;

import jline.ConsoleReader;

/**
 * 全系统初始启动程序
 * TODO 代码清理
 * @author LI.J
 *
 */
public class Bootstrap {

	private Logger logger = LoggerFactory.getLogger(getClass());

/*	@Autowired
	private RTmProcess rProcess;
	
	@Autowired
	private RTmSysStatus rTmSysStatus;*/
	@Autowired
	private TmSysStatusDao tmSysStatusDao;

	@Autowired
	private ManagedComponentService managedComponentService;
	@Autowired
	private TmProcessDao tmProcessDao;

	@Resource(name="instanceRouteRefreshTemplate")
	private AmqpTemplate instanceRouteRefreshTemplate;
	
	@Resource(name="hspKeyRefreshTemplate")
	private AmqpTemplate hspKeyRefreshTemplate;
	
	@Resource(name="parameterRefreshExchange")
	private AmqpTemplate parameterRefreshExchange;
	
	public void list(){
		System.out.println("id\tartifact\tversion\tnode\tmemo");
		for (TmProcess process : tmProcessDao.findAll())
		{
			String line = MessageFormat.format(
				"{0}\t{1}\t{2}\t{3}\t{4}",
				process.getProcessId().toString(),
				process.getArtifactId(),
				process.getArtifactVersion(),
				process.getNodeCode(),
				process.getMemo());
			System.out.println(line);
		}
	}
	
	public void startupByProcessId(String ... processIds)
	{
		for (String processId : processIds)
		{
			try
			{
				managedComponentService.startProcess(Integer.parseInt(processId));
			}
			catch (ProcessException e)
			{
				logger.error("[" + processId + "]启动出错:" + e.getMessage(), e);
			}
		}
	}
	
	public void stopByProcessId(String ... processIds)
	{
		for (String processId : processIds)
		{
			try
			{
				managedComponentService.stopProcess(Integer.parseInt(processId));
			}
			catch (ProcessException e)
			{
				logger.error("[" + processId + "]停止出错:" + e.getMessage(), e);
			}
		}
	}

	public void stopAllProcesses()
	{
		//停止所有非STOPPED的进程
//		QTmProcess q = QTmProcess.tmProcess;
//		for (TmProcess process : tmProcessDao.findAll(q.processStatus.ne(ProcessRunningStatus.STOPPED)))
//		{
//			System.out.println("停止进程：{}" + process.getProcessId());
//			stopByProcessId(process.getProcessId().toString());
//		}
		for (TmProcess process : tmProcessDao.findAll()) {
			if (process.getProcessStatus() != ProcessRunningStatus.STOPPED.name()) {
				System.out.println("停止进程：{}" + process.getProcessId());
				stopByProcessId(process.getProcessId().toString());
			}
		}
	}
	
	public void startupBySystem(String ... systems)
	{
//		QTmProcess qProcess = QTmProcess.tmProcess;
//		QTmInstance qInstance = QTmInstance.tmInstance;
//
//		JPAQuery query = new JPAQuery(em);
//		query.from(qProcess, qInstance).where(
//				qProcess.instanceId.eq(qInstance.instanceId)
//				.and(qInstance.systemType.in(systems)));
//		for (TmProcess process : query.list(qProcess))
//		{
//			try
//			{
//				managedComponentService.startProcess(process.getProcessId());
//			}
//			catch (ProcessException e)
//			{
//				logger.error("[" + process.getProcessId() + "]启动出错:" + e.getMessage(), e);
//			}
//		}
		List<TmProInstance> tmProInstanceList =tmProcessDao.selectProEqInstance(systems);
		for (TmProInstance tmProInstance:tmProInstanceList){
			try
			{
				managedComponentService.startProcess(tmProInstance.getProcessId());
			}
			catch (ProcessException e)
			{
				logger.error("[" + tmProInstance.getProcessId() + "]启动出错:" + e.getMessage(), e);
			}
		}
	}
	
	public void refreshRoute()
	{
		logger.info("刷新路由");
		instanceRouteRefreshTemplate.convertAndSend(new Object());	//不需要内容
	}
	
	public void refreshHspKeys(String ... keys) {
		logger.info("刷新加密机索引: " + keys);
		
		for (String key : keys) {
			try {
				hspKeyRefreshTemplate.convertAndSend(key);
			} catch (ProcessException e) {
				logger.error("[" + key + "]刷新加密机索引出错:" + e.getMessage(), e);
			}
		}
		
	}
	
	/**
	 * 刷新bmp参数
	 */
	public void refreshBmpParams(String... paramClazzNames) {
		logger.info("收到参数刷新请求");
		
		if(paramClazzNames.length == 1 && paramClazzNames[0].equals("@ALL")) {
			logger.info("发送刷新所有参数消息"); //空对象表明全局刷新
			parameterRefreshExchange.convertAndSend(new ParameterRefreshRequest());
			return;
		}
		
		for (String paramClazzName : paramClazzNames) {
			try {
				ParameterRefreshRequest request = new ParameterRefreshRequest();
				request.setKey("*");
				request.setParamClazzName(paramClazzName);
				logger.info("发送刷新参数[{}]消息", request.getParamClazzName());
				parameterRefreshExchange.convertAndSend(request);
			} catch (ProcessException e) {
				logger.error("[" + paramClazzName + "]刷新业务参数出错:" + e.getMessage(), e);
			}
		}
		
	}
	
	/**
	 */
	@Transactional
	public void setAutoStartAllBatch(String value)
	{
		TmSysStatus TmSysStatus = tmSysStatusDao.selectById(1);
		if ("Y".equals(value) || "y".equals(value)) {
			TmSysStatus.setIsAutoStartAllBatch(value);
		}
		else {
			TmSysStatus.setIsAutoStartAllBatch("N");
		}
	}

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ParseException, IOException, InterruptedException {

		Options opts = new Options();
		opts.addOption("help", false, "显示此帮助");
		opts.addOption("list", false, "显示所有进程定义");
		opts.addOption("start", false, "启动指定进程");
		opts.addOption("stop", false, "停止指定进程");
		opts.addOption("stopall", false, "停止所有运行中的进程");
		opts.addOption("refreshroute", false, "刷新全局机构路由");
		opts.addOption("refreshhsp", false, "刷新指定加密机索引");
		opts.addOption("refreshbmp", false, "刷新指定业务参数");
		opts.addOption("pwdenc", false, "表示密码都为密文格式");
		
		opts.addOption("pids", true, "指定进程编号，使用逗号分隔的数字，如 1,4,53");
		opts.addOption("sys", true, "指定所有对应子系统的相应进程定义，如 GMP,BMP，仅能在启动进程时指定");
		
		opts.addOption("keys", true, "指定加密机索引，使用逗号分隔，如bank1.mak.cup,bank1.pik.cup");
		opts.addOption("classes", true, "指定业务参数类名，使用逗号分隔，如com.jjb.cps.param.def.AuthTxnCode,com.jjb.cps.param.def.BlockCode");
		
		opts.addOption("mqaddress", true, "消息服务器地址，逗号分隔的地址列表，如：host1,host2:4567,host3");
		opts.addOption("mqvhost", true, "VirtualHost，默认值为 /dev");
		opts.addOption("mquser", true, "RabbitMQ用户，默认为guest");
		opts.addOption("mqpass", true, "RabbitMQ密码，默认为guest");
		opts.addOption("jdbcdriver", true, "数据库驱动类名，默认值为com.ibm.db2.jcc.DB2Driver");
		opts.addOption("jdbcurl", true, "数据库URL");
		opts.addOption("jdbcuser", true, "数据库用户名");
		opts.addOption("jdbcpass", true, "数据库密码");
		opts.addOption("silent", false, "不等待输入确认直接执行");
		opts.addOption("autoBatch", true, "指定是否启动自动跑批，Y|启动，其他不启动");
		
        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse(opts, args);

        System.out.println("jyd共享银行卡系统 - GMP全局服务启动程序");
        
        if (cmd.hasOption("help"))
        {
        	HelpFormatter formatter = new HelpFormatter();
        	formatter.printHelp("java -cp ... " + Bootstrap.class.getCanonicalName() + " [options]", opts);
        	return;
        }
        
        Properties props = System.getProperties();
        
		props.put("mqAddresses", getOpt("mqaddress", "消息服务器地址", "", false, cmd));
		props.put("mqVHost", getOpt("mqvhost", "Virtual Host", "/dev", false, cmd));
		props.put("mqUser", getOpt("mquser", "RabbitMQ用户", "guest", false, cmd));
		props.put("mqPassword", getOpt("mqpass", "RabbitMQ密码", "guest", true, cmd));
		props.put("jdbcDriver", getOpt("jdbcdriver", "JDBC驱动类", "com.ibm.db2.jcc.DB2Driver", false, cmd));
		props.put("jdbcUrl", getOpt("jdbcurl", "数据库URL", "jdbc:db2://jyddb:50000/gmp01", false, cmd));
		props.put("jdbcUsername", getOpt("jdbcuser", "数据库用户", "", false, cmd));
		props.put("jdbcPassword", getOpt("jdbcpass", "数据库密码", "", true, cmd));
		
		if (!cmd.hasOption("silent"))
		{
			System.out.println("请按回车键开始……");
			new ConsoleReader().readLine();
		}		
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("service-context.xml");
		ctx.registerShutdownHook();
		
		Bootstrap bootstrap = new Bootstrap();
		ctx.getAutowireCapableBeanFactory().autowireBean(bootstrap);
		if (cmd.hasOption("list")) {
			bootstrap.list();
		} else if (cmd.hasOption("refreshroute")) {
			bootstrap.refreshRoute();
		} else if (cmd.hasOption("refreshhsp")) {
			if (cmd.hasOption("keys")) {
				bootstrap.refreshHspKeys(StringUtils.split(cmd.getOptionValue("keys"), ","));
			} else {
				System.err.println("刷新指定加密机索引时必须指定keys");
			}
		} else if (cmd.hasOption("refreshbmp")) {
			if (cmd.hasOption("classes")) {
				bootstrap.refreshBmpParams(StringUtils.split(cmd.getOptionValue("classes"), ","));
			} else {
				System.err.println("刷新指定参数时必须指定classes");
			}
		} else if (cmd.hasOption("start"))
		{
			if (cmd.hasOption("pids"))
				bootstrap.startupByProcessId(StringUtils.split(cmd.getOptionValue("pids"), ","));
			else if (cmd.hasOption("sys"))
				bootstrap.startupBySystem(StringUtils.split(cmd.getOptionValue("sys"), ","));
			else
				System.err.println("启动时必须指定pids或sys");
		}
		else if (cmd.hasOption("stopall"))
		{
			bootstrap.stopAllProcesses();
		}
		else if (cmd.hasOption("stop"))
		{
			if (!cmd.hasOption("pids"))
				System.err.println("必须指定pids");
			else
			{
				bootstrap.stopByProcessId(StringUtils.split(cmd.getOptionValue("pids"), ","));
			}
		} 
		else if (cmd.hasOption("autoBatch"))
		{
			  bootstrap.setAutoStartAllBatch(cmd.getOptionValue("autoBatch"));
			  System.out.println("设置autoBatch为"+cmd.getOptionValue("autoBatch")+"成功");
		}
		else
		{
			System.err.println("没有操作被指定");
		}

		if (!cmd.hasOption("silent"))
		{
			System.out.println("目前GMP服务还在运行，按任意键退出。");
			new ConsoleReader().readLine();
		}
		
		ctx.close();
	}
	
	private static String getOpt(String name, String desc, String defaultValue, boolean password, CommandLine cmd) throws IOException
	{
		String value;
		if (cmd.hasOption(name))
		{
			value = cmd.getOptionValue(name);
			if(password && cmd.hasOption("pwdenc")) {
				value = AESCryptoConverter.decrypt(value);
			}
			
			System.out.println(desc + ":" + (password ? "******" : value));
		}
		else
		{
			String prompt = desc + "[" + defaultValue + "]?";
			if (password)
				value = new ConsoleReader().readLine(prompt, '*');
			else
				value = new ConsoleReader().readLine(prompt);
			if (StringUtils.isEmpty(value))
				value = defaultValue;
		}
		return value;
	}
	
}
