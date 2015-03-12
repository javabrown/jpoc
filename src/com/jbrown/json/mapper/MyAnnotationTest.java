package com.jbrown.json.mapper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@MyAnnotation(name = "Raja Khan", age = 32, friends = { "biswa", "rm" })
@BizCacheOperation
public class MyAnnotationTest {

	String name;

	public MyAnnotationTest() {

	}

	public static void main(String[] hello) {
		System.out.println("Hello");

		MyAnnotation annotation = MyAnnotationTest.class
				.getAnnotation(MyAnnotation.class);

		if (annotation != null) {
			System.out.println(annotation.value());
			System.out.println(annotation.name());
			System.out.println(annotation.age());
		}

	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
	String value() default "";

	String name();

	int age();

	String[] friends();
}

@Retention(RetentionPolicy.RUNTIME)
@interface BizCacheOperation {
	boolean cacheIgnore() default false;
}
