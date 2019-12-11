package com.jjb.unicorn.maven.plugin;

import java.text.MessageFormat;

import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.jjb.unicorn.maven.meta.Column;
import com.jjb.unicorn.maven.meta.Table;


public class FillDefaultValues extends AbstractGenerator {
	@Override
	public void afterEntityGenerated(TopLevelClass entityClass, Table table) {
		Method method = new Method();
		method.setName("fillDefaultValues");
		method.setVisibility(JavaVisibility.PUBLIC);
		
		for (Column col : table.getColumns())
		{
			if (col.isIdentity())
				continue;
			String type = col.getJavaType().getShortName();
			String value = "null";
			if (type.equals("String"))
				value = "\"\"";
			else if (type.equals("BigDecimal"))
				value = "BigDecimal.ZERO";
			else if (type.equals("Integer"))
				value = "0";
			else if (type.equals("Long"))
				value = "0l";
			else if (type.equals("Date"))
				value = "new Date()";
			//其它就不管了，出现问题再说
			method.addBodyLine(MessageFormat.format("if ({0} == null) {0} = {1};",
				col.getPropertyName(),
				value));
		}

		if (!method.getBodyLines().isEmpty())	//处理那些没有可以fill字段的情况
			entityClass.addMethod(method);
	}
}
