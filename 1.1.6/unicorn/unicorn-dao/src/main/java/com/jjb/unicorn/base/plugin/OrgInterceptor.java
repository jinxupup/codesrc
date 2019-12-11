package com.jjb.unicorn.base.plugin;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;

@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class OrgInterceptor implements Interceptor {

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object obj = invocation.getArgs()[1];
		if (obj != null && Map.class.isAssignableFrom(obj.getClass())) {
			Map<String,Object> param = (Map<String,Object>) invocation.getArgs()[1];
			if(StrKit.isBlank((String)param.get("org"))){
				param.put("org", OrganizationContextHolder.getOrg());
			}
			if (param.get("_SORT_NAME") != null && param.get("_SORT_NAME").toString().length()>0) {
				param.put("_SORT_NAME", propNameToColName(param.get("_SORT_NAME").toString()));
			}
		} 
		
		return invocation.proceed();
	}
	
	private String propNameToColName (String propName) {
		StringBuilder colName = new StringBuilder ("");
		int len = propName.length();
		
		char c;
		for (int i=0;i<len;i++) {
			c = propName.charAt(i);
			if (c>=97 && c <=122) {
				colName.append((char)(c-32));
			} else {
				if(c>=65 && c<=90){
					colName.append("_");
				}
				colName.append((char)c);
			}
		}
		
		return colName.toString();
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

}
