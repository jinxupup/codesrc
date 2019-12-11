package com.jjb.unicorn.maven.plugin;

import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.maven.plugin.logging.Log;

import com.jjb.unicorn.maven.meta.Database;
import com.jjb.unicorn.maven.meta.Table;


/**
 *为实现类提供默认空实现
 * @author jjb
 *
 */
public abstract class AbstractGenerator implements Generator
{
	protected Log logger;

	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{
		return null;
	}

	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		return null;
	}
	
	public void afterEntityGenerated(TopLevelClass entityClass, Table table)
	{
	}

	public void afterKeyGenerated(TopLevelClass keyClass)
	{
	}
	
	public List<GeneralFileContent> generateAdditionalFiles(Database database)
	{
		return null;
	}

	public List<GeneralFileContent> generateAdditionalFiles(Table table, Database database)
	{
		return null;
	}
	
	public void setLogger(Log logger) {
		this.logger = logger;
	}

}
