package com.persons.Persons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to enable logging for a method.
 * Crea una anotación llamada @LoggerAnnotation
 * que solo pueda ponerse arriba de métodos (@Target)
 * y asegúrate de mantenerla viva en memoria cuando el programa esté corriendo (@Retention),
 * para que Spring AOP la pueda encontrar y procesar.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggerAnnotation {
}
