package com.jjb.drl.web.test;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;


public class Test {

	@org.junit.Test
	public void test(){

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("drl/rule.drl"),ResourceType.DRL);
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		
		StatelessKnowledgeSession statelessKnowledgeSession = kbase.newStatelessKnowledgeSession();
		
		
		
		Map<String,Object> fact = new HashMap<String,Object>();
		fact.put("txnType", "B1");
		fact.put("txnChanel", "1");
		
		statelessKnowledgeSession.execute(fact);

		System.out.println(fact);
	}
	
}
