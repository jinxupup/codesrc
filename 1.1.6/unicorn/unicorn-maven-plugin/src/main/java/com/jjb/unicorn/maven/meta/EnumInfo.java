package com.jjb.unicorn.maven.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author jjb
 *
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface EnumInfo
{
	 String[] value();
}
