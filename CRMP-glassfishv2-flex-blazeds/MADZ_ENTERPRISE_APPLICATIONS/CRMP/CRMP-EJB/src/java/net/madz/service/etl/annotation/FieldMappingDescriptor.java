package net.madz.service.etl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.madz.service.etl.IMappingStrategy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldMappingDescriptor {

	Class<?> entityClass();

	Type type();

	String mappingKey() default "";

	String mapToField();

	boolean unsupport() default false;

	Class<?> mappingStrategyClass() default IMappingStrategy.class;

	String unspecifiedProviderMethod() default "";

	public static enum Type {
		Relationship, Field
	}
}
