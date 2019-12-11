package com.jjb.acl.access.facade;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jjb.unicorn.facility.exception.UnicornException;



/**
 * 以静态变量保存应用上下文, 可在任何代码任何地方任何时候取出应用上下文.
 */
public class ContextHolder implements ApplicationContextAware, DisposableBean {
    
    private static ApplicationContext applicationContext = null;

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (ContextHolder.applicationContext != null) {
//            logger.info("应用上下文被覆盖, 原有应用上下文:" + ContextHolder.applicationContext);
        }
        ContextHolder.applicationContext = applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除ContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
//        logger.debug("清除应用上下文:" + applicationContext);

        applicationContext = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
        	throw new UnicornException ( "应用上下文组件未初始化.");
        }
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        ContextHolder.clearHolder();
    }
}
