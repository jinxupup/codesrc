package com.jjb.unicorn.facility.cstruct;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CStruct对应结构体的描述信息，用于出文档
 * @author jjb
 *
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CDescription {
	
	/**
	 * 描述
	 * @return
	 */
	String value();
	
	/**
	 * 备注
	 * @return
	 */
	String memo() default "";
	
}
