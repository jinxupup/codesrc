package com.jjb.unicorn.maven.plugin;

import java.text.MessageFormat;

import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.jjb.unicorn.maven.meta.Table;

public class ToString extends AbstractGenerator {
	
	@Override
	public void afterEntityGenerated(TopLevelClass entityClass, Table table)
	{
		Method toString = new Method();
		entityClass.addMethod(toString);
		toString.setName("toString");
		toString.setVisibility(JavaVisibility.PUBLIC);
		toString.setReturnType(FullyQualifiedJavaType.getStringInstance());
		toString.addAnnotation("@Override");
		
		toString.addBodyLine("return getClass().getName() + \"@\" + Integer.toHexString(hashCode())+\"[\"+");
		int i = 1;
		for (Field field : entityClass.getFields())
		{
			if (i>1) {
				i++;
				toString.addBodyLine(MessageFormat.format("\", {0}=\"+{0}+", field.getName()));
			}
			toString.addBodyLine(MessageFormat.format("\"{0}=\"+{0}+", field.getName()));
			i++;
		}
		toString.addBodyLine("\"]\";");
	}

	@Override
	public void afterKeyGenerated(TopLevelClass keyClass) {

		
	}
}
