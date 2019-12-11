package com.jjb.ecms.adapter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdapterRunner {
	static ApplicationContext context;
	public static void main(String[] args) throws Exception {
		context = new ClassPathXmlApplicationContext("service-context.xml");
        
        System.err.println("cas adapter running ......");
        
        System.in.read();

	}

}
