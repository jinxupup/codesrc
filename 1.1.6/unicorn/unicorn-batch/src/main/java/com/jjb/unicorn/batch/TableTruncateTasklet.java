package com.jjb.unicorn.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.vendor.Database;

import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.util.PropertyConversionUtil;

/**
 * 通用表清空步骤
 * 
 * 
 */
public class TableTruncateTasklet implements Tasklet {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<Class<?>> entities;

	@Autowired
	private BaseDao baseDao;
	
	
	@Value("#{env['jpaDatabaseType']?:'DEFAULT'}")
	private Database database;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception 
	{
		for (Class<?> entity : entities)
		{
			String tableName = PropertyConversionUtil.classNameToTableNm(entity.getSimpleName());
			
//			logger.info("清空表：" + tableName);
			switch (database)
			{
			case MYSQL:
			case HSQL:
			case H2:
			case ORACLE:
				truncateTable(tableName);
				break;
			case DB2:
				truncateDB2(tableName);
				break;
			default:
				logger.info("没有指定有效的jpaDatabaseType属性，默认执行truncate table命令");
				truncateTable(tableName);
				break;
			}

		}

		return RepeatStatus.FINISHED;
	}
	
	private void truncateTable(String tableName) throws SQLException
	{
		
		SqlSessionTemplate st = (SqlSessionTemplate) baseDao.getSqlSession();
		Connection conn =  SqlSessionUtils.getSqlSession(
                st.getSqlSessionFactory(), st.getExecutorType(),
                st.getPersistenceExceptionTranslator()).getConnection();
		
		PreparedStatement ps = conn.prepareStatement("truncate table " + tableName);
		ps.executeUpdate();
	}

	/**
	 * DB2专用的不记日志清空表的步聚。 这里不使用v9.7以后才用的truncate命令，而使用ALTER TABLE X ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE
	 */
	private void truncateDB2(String tableName)
	{
		// 因为在做alter期间不能rollback，所以最好不要抛异常。rollback后会使表不可用。
		try 
		{
			SqlSessionTemplate st = (SqlSessionTemplate) baseDao.getSqlSession();
			Connection conn =  SqlSessionUtils.getSqlSession(
	                st.getSqlSessionFactory(), st.getExecutorType(),
	                st.getPersistenceExceptionTranslator()).getConnection();
			
			PreparedStatement ps = conn.prepareStatement(MessageFormat.format("ALTER TABLE {0} ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE", tableName));
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			logger.error("清表过程出错", e);
		}
	}

	public List<Class<?>> getEntities() {
		return entities;
	}

	@Required
	public void setEntities(List<Class<?>> entities) {
		this.entities = entities;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}
