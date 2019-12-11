package com.jjb.ecms.biz.activiti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.AbstractBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.cfg.DefaultBpmnParseFactory;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.SpringProcessEngineConfiguration;

/**
 * @author BIG.2Spring
 *
 */
public class SpringProcessEngineConfigurationExt extends
		SpringProcessEngineConfiguration {
	
	@Override
	protected void initServices(){
		super.runtimeService = new RuntimeServiceImplExt();
		super.initServices();
	}
	
	
	@Override
	public ProcessEngine buildProcessEngine() {
		init();
//		jobExecutor.setWaitTimeInMillis(60 *60* 1000);//一个小时执行一次
		return new ProcessEngineImpl(this);
	}

	@Override
	protected Collection<? extends Deployer> getDefaultDeployers() {
		List<Deployer> defaultDeployers = new ArrayList<Deployer>();

		BpmnDeployer bpmnDeployer = new BpmnDeployer();
		bpmnDeployer.setExpressionManager(expressionManager);
		bpmnDeployer.setIdGenerator(idGenerator);

		if (bpmnParseFactory == null) {
			bpmnParseFactory = new DefaultBpmnParseFactory();
		}

		if (activityBehaviorFactory == null) {
			DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
			defaultActivityBehaviorFactory
					.setExpressionManager(expressionManager);
			activityBehaviorFactory = defaultActivityBehaviorFactory;
		} else if ((activityBehaviorFactory instanceof AbstractBehaviorFactory)
				&& ((AbstractBehaviorFactory) activityBehaviorFactory)
						.getExpressionManager() == null) {
			((AbstractBehaviorFactory) activityBehaviorFactory)
					.setExpressionManager(expressionManager);
		}

		if (listenerFactory == null) {
			DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
			defaultListenerFactory.setExpressionManager(expressionManager);
			listenerFactory = defaultListenerFactory;
		}

		BpmnParser bpmnParser = new BpmnParser();
		bpmnParser.setExpressionManager(expressionManager);
		bpmnParser.setBpmnParseFactory(bpmnParseFactory);
		bpmnParser.setActivityBehaviorFactory(activityBehaviorFactory);
		bpmnParser.setListenerFactory(listenerFactory);

		List<BpmnParseHandler> parseHandlers = new ArrayList<BpmnParseHandler>();
		if (getPreBpmnParseHandlers() != null) {
			parseHandlers.addAll(getPreBpmnParseHandlers());
		}
		parseHandlers.addAll(getDefaultBpmnParseHandlers());
		if (getPostBpmnParseHandlers() != null) {
			parseHandlers.addAll(getPostBpmnParseHandlers());
		}

		BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
		bpmnParseHandlers.addHandlers(parseHandlers);
		bpmnParser.setBpmnParserHandlers(bpmnParseHandlers);

		bpmnDeployer.setBpmnParser(bpmnParser);

		defaultDeployers.add(bpmnDeployer);
		return defaultDeployers;
	}

}
