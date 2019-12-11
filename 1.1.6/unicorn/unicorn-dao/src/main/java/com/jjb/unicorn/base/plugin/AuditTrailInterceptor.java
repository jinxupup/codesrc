package com.jjb.unicorn.base.plugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;


@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class AuditTrailInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType commandType = mappedStatement.getSqlCommandType();
		
		if (commandType == SqlCommandType.INSERT) {
			String sqlString = mappedStatement.getSqlSource().getBoundSql(invocation.getArgs()[1]).getSql();
		}
		Object object = invocation.proceed();
		return object;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	public String saveAuditLog (BoundSql boundSql,Configuration configuration,Object parameterObject) throws SQLException {
	   StringBuilder sb = new StringBuilder ("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
	   sb.append("<audit><current>");
	   List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	   if (parameterMappings != null) {
		   for (int i = 0; i < parameterMappings.size(); i++) {
			   ParameterMapping parameterMapping = parameterMappings.get(i);
			   if (parameterMapping.getMode() != ParameterMode.OUT) {
				   Object value;
				   String propertyName = parameterMapping.getProperty();
	          
				   if (boundSql.hasAdditionalParameter(propertyName)) { //
					   value = boundSql.getAdditionalParameter(propertyName);
				   } else if (parameterObject == null) {
					   value = null;
				   } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
					   value = parameterObject;
				   } else {
					   MetaObject metaObject = configuration.newMetaObject(parameterObject);
					   value = metaObject.getValue(propertyName);
				   }
	          
				   sb.append("<");
				   sb.append(propertyName);
				   sb.append(">");
				   sb.append(value);
				   sb.append("</");
				   sb.append(propertyName);
				   sb.append(">");
			   }
	       }
	   }
	    
	   sb.append("</current></audit>");
	   
	   return sb.toString();
	}
	
	public String updateAuditLog (BoundSql boundSql,Configuration configuration,Object parameterObject) throws SQLException {
		boundSql.getSql();
	    StringBuilder sb = new StringBuilder ("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
	    sb.append("<audit><current>");
	    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	    if (parameterMappings != null) {
	    	for (int i = 0; i < parameterMappings.size(); i++) {
			   ParameterMapping parameterMapping = parameterMappings.get(i);
			   if (parameterMapping.getMode() != ParameterMode.OUT) {
				   Object value;
				   String propertyName = parameterMapping.getProperty();
	          
				   if (boundSql.hasAdditionalParameter(propertyName)) { //
					   value = boundSql.getAdditionalParameter(propertyName);
				   } else if (parameterObject == null) {
					   value = null;
				   } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
					   value = parameterObject;
				   } else {
					   MetaObject metaObject = configuration.newMetaObject(parameterObject);
					   value = metaObject.getValue(propertyName);
				   }
	          
				   sb.append("<");
				   sb.append(propertyName);
				   sb.append(">");
				   sb.append(value);
				   sb.append("</");
				   sb.append(propertyName);
				   sb.append(">");
			   }
	        }
	    }
	    
	    sb.append("</current></audit>");
	   
	    return sb.toString();
	}
	
	public String deleteAuditLog (BoundSql boundSql,Configuration configuration,Object parameterObject) throws SQLException {
		String deleteSql = boundSql.getSql();
//		String selectSql = "select *  "+ whereSelect(deleteSql);
//		
	    StringBuilder sb = new StringBuilder ("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
	    sb.append("<audit><original>");
	    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	    if (parameterMappings != null) {
	    	for (int i = 0; i < parameterMappings.size(); i++) {
			   ParameterMapping parameterMapping = parameterMappings.get(i);
			   if (parameterMapping.getMode() != ParameterMode.OUT) {
				   Object value;
				   String propertyName = parameterMapping.getProperty();
	          
				   if (boundSql.hasAdditionalParameter(propertyName)) { //
					   value = boundSql.getAdditionalParameter(propertyName);
				   } else if (parameterObject == null) {
					   value = null;
				   } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
					   value = parameterObject;
				   } else {
					   MetaObject metaObject = configuration.newMetaObject(parameterObject);
					   value = metaObject.getValue(propertyName);
				   }
	          
				   sb.append("<");
				   sb.append(propertyName);
				   sb.append(">");
				   sb.append(value);
				   sb.append("</");
				   sb.append(propertyName);
				   sb.append(">");
			   }
	        }
	    }
	    
	    sb.append("</original></audit>");
	   
	    return sb.toString();
	}
	
	protected String whereSelect(String sql) {
      String tempSql = sql.replaceAll("\t", " ");
      tempSql = tempSql.replaceAll("\n", " ");
      String strs[] = tempSql.split(" ");
      int i = 0;
      for (;i<strs.length;i++) {
      	if (StringUtils.isNotEmpty(strs[i]) && strs[i].equalsIgnoreCase("from")) {
      		break;
      	}
      }
      StringBuilder builder = new StringBuilder("");
      for (;i<strs.length;i++) {
      	builder.append(strs[i]);
      	builder.append(" ");
      }
      
      return builder.toString();
  }

}
