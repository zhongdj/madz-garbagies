/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.core.state.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Barry
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReactiveObject {

	String type() default "";
}
