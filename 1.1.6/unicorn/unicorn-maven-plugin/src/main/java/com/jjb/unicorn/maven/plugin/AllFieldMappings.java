package com.jjb.unicorn.maven.plugin;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.jjb.unicorn.maven.meta.Column;
import com.jjb.unicorn.maven.meta.Database;
import com.jjb.unicorn.maven.meta.Table;


public class AllFieldMappings extends AbstractGenerator
{
	
	private String targetPackage;
	
	private static final String NAME_PREFIX = "M";
	
	public AllFieldMappings(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}
	
	@Override
	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{
		FullyQualifiedJavaType fqjtEntity = table.getJavaClass();

		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(targetPackage + "." + NAME_PREFIX + fqjtEntity.getShortName()));
		clazz.setVisibility(JavaVisibility.PUBLIC);
		
		FullyQualifiedJavaType fqjtQ = new FullyQualifiedJavaType(fqjtEntity.getPackageName() + ".Q" + fqjtEntity.getShortName());
		
		clazz.addImportedType(fqjtQ);
		
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setStatic(true);
		method.setName("getAllMappings");
		
		FullyQualifiedJavaType fqjtMap = new FullyQualifiedJavaType("java.util.Map");
		FullyQualifiedJavaType fqjtExpression = new FullyQualifiedJavaType("com.mysema.query.types.Expression");
		fqjtMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		fqjtMap.addTypeArgument(fqjtExpression);
		clazz.addImportedType(fqjtMap);
		clazz.addImportedType(fqjtExpression);
		method.setReturnType(fqjtMap);
		
		clazz.addImportedType(new FullyQualifiedJavaType("java.util.HashMap"));
		
		method.addBodyLine("HashMap<String, Expression> result = new HashMap<String, Expression>();");
		method.addBodyLine(MessageFormat.format("{0} q = {0}.{1};", fqjtQ.getShortName(), StringUtils.uncapitalize(fqjtEntity.getShortName())));
		
		for (Column col : table.getColumns())
			method.addBodyLine(MessageFormat.format("result.put(\"{0}\", q.{0});", col.getPropertyName()));

		method.addBodyLine("return result;");
		
		clazz.addMethod(method);

		List<CompilationUnit> result = new ArrayList<CompilationUnit>();
		result.add(clazz);
		return result;
	}
}
