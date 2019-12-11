package com.jjb.unicorn.base.plugin;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.jjb.unicorn.facility.ErrorConstants;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.UnicornException;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.util.ReflectUtil;

@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class UpdateInterceptor implements Interceptor {
	
	private static final String UPDATE_NOT_NULL_BY_PRIMARY_KEY = "updateNotNullByPrimaryKey";
	
	private static final String UPDATE_BY_PRIMARY_KEY = "updateByPrimaryKey";

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		Object param = invocation.getArgs()[1];
		if (param != null && Map.class.isAssignableFrom(param.getClass())) {
			Map<String,Object> hp = (Map<String,Object>) invocation.getArgs()[1];
			if(StrKit.isBlank((String)hp.get("org"))){
				hp.put("org", OrganizationContextHolder.getOrg());
			}
			hp.put("createDate", new Date ());
			hp.put("createBy", OrganizationContextHolder.getUserNo());
			hp.put("updateDate", new Date ());
			hp.put("updateBy", OrganizationContextHolder.getUserNo());
			return invocation.proceed();
			
		} 
		
		Class<?> clazz = param.getClass();
		Method method = ReflectUtil.getMethod(clazz,"getOrg", new Class<?>[]{});
		if (method != null) {
			Object org = method.invoke(param, new Object[]{});
			if (org == null || StrKit.isBlank(org.toString())) {
				method = ReflectUtil.getMethod(clazz,"setOrg", String.class);
				if (method != null) {
					method.invoke(param, OrganizationContextHolder.getOrg());
				}
			}
		}
		
		Method methodCreate = ReflectUtil.getMethod(clazz,"setCreateDate", Date.class);
		if (methodCreate != null) {
			methodCreate.invoke(param, new Date ());
		}
		
		Method methodCreateUser = ReflectUtil.getMethod(clazz,"setCreateBy", String.class);
		if (methodCreateUser != null) {
			methodCreateUser.invoke(param, OrganizationContextHolder.getUserNo());
		}
		
		Method methodUpdateDate = ReflectUtil.getMethod(clazz,"setUpdateDate", Date.class);
		if (methodUpdateDate != null) {
			methodUpdateDate.invoke(param, new Date ());
		}
		
		Method methodUpdateUser = ReflectUtil.getMethod(clazz,"setUpdateBy", String.class);
		if (methodUpdateUser != null) {
			methodUpdateUser.invoke(param, OrganizationContextHolder.getUserNo());
		}
		
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType commandType = mappedStatement.getSqlCommandType();
		if (commandType == SqlCommandType.INSERT) {
			Method methodUpdateVersion = ReflectUtil.getMethod(clazz,"setJpaVersion", Integer.class);
			if (methodUpdateVersion != null) {
				methodUpdateVersion.invoke(param, 0);
			}
		}
		if (commandType == SqlCommandType.UPDATE) {
			String sqlId = mappedStatement.getId();
			if (sqlId.endsWith(UPDATE_NOT_NULL_BY_PRIMARY_KEY) || sqlId.endsWith(UPDATE_BY_PRIMARY_KEY)) {
				Integer effectCnt = (Integer) invocation.proceed();
				if (effectCnt != null && effectCnt.intValue() == 0) {
					throw new UnicornException(ErrorConstants.ERROR_CODE_110002,"存在版本冲突，请稍后再试!!");
				}else {
					Method methodUpdateVersion = ReflectUtil.getMethod(clazz,"setJpaVersion", Integer.class);
					if (methodUpdateVersion != null) {//不推荐使用
						Method methodGetVersion = ReflectUtil.getMethod(clazz,"getJpaVersion");
						if (methodGetVersion != null) {
							Integer nowVersion = (Integer) methodGetVersion.invoke(param, new Object[]{});
							if (nowVersion != null) {
								methodUpdateVersion.invoke(param, nowVersion+1);
							}
						}
					}
				}
				
				return effectCnt;
			}
			
		}
		
		return invocation.proceed();
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
		// TODO Auto-generated method stub

	}

}
