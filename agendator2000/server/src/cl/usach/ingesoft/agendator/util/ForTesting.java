package cl.usach.ingesoft.agendator.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies a piece of code (variable or method) that should not be used unless for testing (i.e. is made available for
 * only testing purposes, but it should be treated as a private variable/method).
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface ForTesting {
}
