package com.jjb.unicorn.maven.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CChar {
	int value() default 1;

	boolean zeroPadding() default true;
	
	
	String formatPattern() default "";
	

	boolean leftPadding() default false;
	

	boolean autoTrim() default true;
	

	int precision() default 0;
	
	
	RoundingMode rounding() default RoundingMode.HALF_UP;
	
	
	String datePattern() default "";
	
	
	boolean required() default false;
	

	int order();
}
