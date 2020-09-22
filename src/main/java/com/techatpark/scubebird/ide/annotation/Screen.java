package com.techatpark.scubebird.ide.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Screen {
	
	int width() default 400;
	int height() default 300;	
	boolean maximizable() default true;
	boolean minimizable() default true;
	boolean resizable() default true;
	boolean closeable() default true;
	boolean iconifiable() default true;
	boolean singleInstance() default false;
	
}
