package com.douzone.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 메소드, 타입을 선언하는 구간에 붙여서 사용하면 어노테이션이 적용됨
@Target({ElementType.METHOD, ElementType.TYPE})
// 어노테이션의 범위(JVM이 어노테이션 정보를 참조)
@Retention(RetentionPolicy.RUNTIME) // 어느 시점에 사용할 것인가?
public @interface Auth {
	public enum Role {ADMIN, USER}
	Role value() default Role.USER;
	/* test */
//	String[] value() default "";
//	int method() default 1;
}
